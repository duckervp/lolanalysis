package com.ducker.lolanalysis.service.impl;

import com.ducker.lolanalysis.dto.*;
import com.ducker.lolanalysis.enums.ObjectiveType;
import com.ducker.lolanalysis.enums.PerkType;
import com.ducker.lolanalysis.exception.NotFoundException;
import com.ducker.lolanalysis.model.Ban;
import com.ducker.lolanalysis.model.Match;
import com.ducker.lolanalysis.model.MatchLookup;
import com.ducker.lolanalysis.model.Objective;
import com.ducker.lolanalysis.model.Participant;
import com.ducker.lolanalysis.model.Perk;
import com.ducker.lolanalysis.model.PerkStyleSelection;
import com.ducker.lolanalysis.model.Selection;
import com.ducker.lolanalysis.model.Team;
import com.ducker.lolanalysis.repository.BanRepository;
import com.ducker.lolanalysis.repository.MatchLookupRepository;
import com.ducker.lolanalysis.repository.MatchRepository;
import com.ducker.lolanalysis.repository.ObjectiveRepository;
import com.ducker.lolanalysis.repository.ParticipantRepository;
import com.ducker.lolanalysis.repository.PerkStyleSelectionRepository;
import com.ducker.lolanalysis.repository.SelectionRepository;
import com.ducker.lolanalysis.repository.TeamRepository;
import com.ducker.lolanalysis.service.MatchService;
import com.ducker.lolanalysis.service.RiotMatchService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class MatchServiceImpl implements MatchService {
    private final TeamRepository teamRepository;

    private final MatchRepository matchRepository;

    private final MatchLookupRepository matchLookupRepository;

    private final BanRepository banRepository;

    private final ObjectiveRepository objectiveRepository;

    private final ParticipantRepository participantRepository;

    private final SelectionRepository selectionRepository;

    private final PerkStyleSelectionRepository perkStyleSelectionRepository;

    private final RiotMatchService riotMatchService;

    private final ObjectMapper objectMapper;

    @Override
    @Transactional
    public void saveAll(List<MatchDto> matchDtoList) {
        List<String> matchIds = new ArrayList<>(matchDtoList.stream()
                .map(MatchDto::getMetadata)
                .map(MetadataDto::getMatchId).toList());
        List<String> existedMatchIds = matchLookupRepository.findByMatchIdIn(matchIds)
                .stream().map(MatchLookup::getMatchId).toList();

        matchIds.removeAll(existedMatchIds);

        for (MatchDto matchDto : matchDtoList) {
            if (matchIds.contains(matchDto.getMetadata().getMatchId())) {
                save1(matchDto);
            }
        }
    }

    @Override
    @Transactional
    public void save(MatchDto matchDto) {
        Optional<Match> matchOptional = matchRepository.findByMatchId(matchDto.getMetadata().getMatchId());
        if (matchOptional.isPresent()) {
            return;
        }
        save1(matchDto);
    }

    private void save1(MatchDto matchDto) {
        InfoDto matchInfo = matchDto.getInfo();
        Match match = Match.builder()
                .matchId(matchDto.getMetadata().getMatchId())
                .dataVersion(matchDto.getMetadata().getDataVersion())
                .gameCreation(matchInfo.getGameCreation())
                .gameDuration(matchInfo.getGameDuration())
                .gameEndTimestamp(matchInfo.getGameEndTimestamp())
                .gameId(matchInfo.getGameId())
                .gameMode(matchInfo.getGameMode())
                .gameName(matchInfo.getGameName())
                .gameStartTimestamp(matchInfo.getGameStartTimestamp())
                .gameType(matchInfo.getGameType())
                .gameVersion(matchInfo.getGameVersion())
                .mapId(matchInfo.getMapId())
                .platformId(matchInfo.getPlatformId())
                .queueId(matchInfo.getQueueId())
                .tournamentCode(matchInfo.getTournamentCode())
                .build();
        matchRepository.save(match);
        matchLookupRepository.save(new MatchLookup(match.getMatchId()));

        List<Team> teams = new ArrayList<>();
        Map<Integer, TeamDto> teamDtoMap = new HashMap<>();
        matchDto.getInfo().getTeams().forEach(teamDto -> {
            Team team = Team.builder()
                    .teamId(teamDto.getTeamId())
                    .win(teamDto.isWin())
                    .matchId(match.getMatchId())
                    .build();
            teams.add(team);
            teamDtoMap.put(teamDto.getTeamId(), teamDto);
        });
        teamRepository.saveAll(teams);

        List<Ban> teamBans = new ArrayList<>();
        List<Objective> teamObjectives = new ArrayList<>();
        for (Team team : teams) {
            TeamDto teamDto = teamDtoMap.get(team.getTeamId());
            if (Objects.nonNull(teamDto)) {
                teamDto.getBans().forEach(teamBan -> {
                    Ban ban = Ban.builder()
                            .teamId(team.getId())
                            .championId(teamBan.getChampionId())
                            .pickTurn(teamBan.getPickTurn())
                            .build();
                    teamBans.add(ban);
                });
                ObjectivesDto objectivesDto = teamDto.getObjectives();
                TypeReference<Map<String, ObjectiveDto>> objectiveDtoMapType = new TypeReference<>() {
                };
                Map<String, ObjectiveDto> objectiveDtoMap = objectMapper.convertValue(objectivesDto, objectiveDtoMapType);
                objectiveDtoMap.forEach((key, value) -> {
                    ObjectiveType type = ObjectiveType.fromValue(key);
                    if (Objects.nonNull(type)) {
                        ObjectiveDto objectiveDto = objectiveDtoMap.get(key);
                        Objective objective =
                                Objective.builder()
                                        .teamId(team.getId())
                                        .type(type)
                                        .first(objectiveDto.isFirst())
                                        .kills(objectiveDto.getKills())
                                        .build();
                        teamObjectives.add(objective);
                    }
                });
            }
        }
        banRepository.saveAll(teamBans);
        objectiveRepository.saveAll(teamObjectives);

        List<Selection> selections = selectionRepository.findAll();
        Map<String, String> selectionIdMap = new HashMap<>();
        selections.forEach(selection -> selectionIdMap.put(
                genKey(List.of(selection.getPerk(), selection.getVar1(), selection.getVar2(), selection.getVar3())),
                selection.getId()));
        List<Participant> participants = new ArrayList<>();
        List<Selection> selectionsToSave = new ArrayList<>();
        List<PerkStyleSelection> perkStyleSelections = new ArrayList<>();
        for (ParticipantDto participantDto : matchInfo.getParticipants()) {
            String participantId = "ppid-".concat(UUID.randomUUID().toString());
            Participant participant = objectMapper.convertValue(participantDto, Participant.class);
            participant.setId(participantId);
            participant.setMatchId(match.getMatchId());
            participants.add(participant);

            savePerk(participantDto, participantId, selectionIdMap, selectionsToSave, perkStyleSelections);
        }
        participantRepository.saveAll(participants);
        selectionRepository.saveAll(selectionsToSave);
        perkStyleSelectionRepository.saveAll(perkStyleSelections);

    }

    @SneakyThrows
    @Override
    @Cacheable(cacheNames = "match", key = "#matchId")
    public MatchDto findByMatchId(String matchId) {
        MatchDto matchDto = findByMatchId1(matchId);
        if (Objects.isNull(matchDto)) {
            throw new NotFoundException("Not found match with id: " + matchId);
        }
        return matchDto;
    }

    @Override
    @Cacheable(cacheNames = "matches")
    public List<MatchDto> findByMatchIds(List<String> matchIds) {
        return findByMatchIds1(matchIds);
    }

    @Override
    @Cacheable(cacheNames = "matches")
    public List<MatchDto> findMatches(Integer pageNo, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        List<String> matchIds = matchLookupRepository.findMatchLookup(pageable)
                .stream().map(MatchLookup::getMatchId).toList();
        return findByMatchIds1(matchIds);
    }

    private List<MatchDto> findByMatchIds1(List<String> matchIds) {
        List<MatchDto> matchDtoList = new ArrayList<>();
        for (String matchId : matchIds) {
            MatchDto matchDto = findByMatchId1(matchId);
            if (Objects.isNull(matchDto)) {
                matchDto = riotMatchService.crawlMatchById(matchId);
                save1(matchDto);
            }
            matchDtoList.add(matchDto);
        }
        return matchDtoList;
    }

    public MatchDto findByMatchId1(String matchId) {
        Match match = matchRepository.findByMatchId(matchId).orElse(null);
        if (Objects.isNull(match)) {
            return null;
        }

        MatchDto matchDto = new MatchDto();
        MetadataDto metadataDto = MetadataDto.builder()
                .matchId(match.getMatchId())
                .dataVersion(match.getDataVersion())
                .build();

        List<Team> teams = teamRepository.findByMatchId(match.getMatchId());
        List<TeamDto> teamDtoList = new ArrayList<>();
        List<Long> teamIds = teams.stream().map(Team::getId).toList();
        List<Ban> bans = banRepository.findByTeamIdIn(teamIds);
        List<Objective> objectives = objectiveRepository.findByTeamIdIn(teamIds);

        for (Team team : teams) {
            TeamDto teamDto = TeamDto.builder()
                    .teamId(team.getTeamId())
                    .win(team.isWin())
                    .build();
            List<BanDto> teamBans = bans.stream().filter(ban -> team.getId().equals(ban.getTeamId()))
                    .map(ban -> objectMapper.convertValue(ban, BanDto.class)).toList();
            teamDto.setBans(teamBans);
            Map<String, ObjectiveDto> objectiveDtoMap = new HashMap<>();
            objectives.stream()
                    .filter(objective -> team.getId().equals(objective.getTeamId()))
                    .forEach(objective ->
                            objectiveDtoMap.put(objective.getType().getValue(),
                                    objectMapper.convertValue(objective, ObjectiveDto.class)));
            ObjectivesDto objectivesDto = objectMapper.convertValue(objectiveDtoMap, ObjectivesDto.class);
            teamDto.setObjectives(objectivesDto);
            teamDtoList.add(teamDto);
        }

        List<Participant> participants = participantRepository.findByMatchId(match.getMatchId());

        metadataDto.setParticipants(participants.stream().map(Participant::getPuuid).toList());
        matchDto.setMetadata(metadataDto);

        List<ParticipantDto> participantDtoList = new ArrayList<>();

        for (Participant participant : participants) {
            ParticipantDto participantDto = objectMapper.convertValue(participant, ParticipantDto.class);
            participantDtoList.add(participantDto);
        }

        InfoDto infoDto = InfoDto.builder()
                .gameCreation(match.getGameCreation())
                .gameDuration(match.getGameDuration())
                .gameEndTimestamp(match.getGameEndTimestamp())
                .gameStartTimestamp(match.getGameStartTimestamp())
                .gameId(match.getGameId())
                .mapId(match.getMapId())
                .queueId(match.getQueueId())
                .gameMode(match.getGameMode())
                .gameName(match.getGameName())
                .gameType(match.getGameType())
                .gameVersion(match.getGameVersion())
                .participants(participantDtoList)
                .teams(teamDtoList)
                .platformId(match.getPlatformId())
                .tournamentCode(match.getTournamentCode())
                .build();

        matchDto.setInfo(infoDto);

        return matchDto;
    }

    private String genKey(List<Integer> params) {
        return params.stream().map(String::valueOf).collect(Collectors.joining("-"));
    }

    private void savePerk(ParticipantDto participantDto,
                          String participantId,
                          Map<String, String> selectionIdMap,
                          List<Selection> selections,
                          List<PerkStyleSelection> perkStyleSelections) {
        PerksDto perksDto = participantDto.getPerks();

        Perk perk = Perk.builder()
                .id("pid-".concat(UUID.randomUUID().toString()))
                .participantId(participantId)
                .flex(perksDto.getStatPerks().getFlex())
                .offense(perksDto.getStatPerks().getOffense())
                .defense(perksDto.getStatPerks().getDefense())
                .build();

        for (PerkStyleDto perkStyleDto : perksDto.getStyles()) {

            if (perkStyleDto.getDescription().equals(PerkType.PRIMARY_TYPE.getValue())) {
                perk.setPrimaryStylePerk(perkStyleDto.getStyle());
            } else {
                perk.setSubStylePerk(perkStyleDto.getStyle());
            }

            for (PerkStyleSelectionDto perkStyleSelectionDto : perkStyleDto.getSelections()) {
                String key = genKey(List.of(
                        perkStyleSelectionDto.getPerk(),
                        perkStyleSelectionDto.getVar1(),
                        perkStyleSelectionDto.getVar2(),
                        perkStyleSelectionDto.getVar3()));
                PerkStyleSelection perkStyleSelection = new PerkStyleSelection();

                String selectionId = selectionIdMap.get(key);

                if (Objects.isNull(selectionId)) {
                    selectionId = "sid-".concat(UUID.randomUUID().toString());
                    Selection selection = Selection.builder()
                            .id(selectionId)
                            .perk(perkStyleSelectionDto.getPerk())
                            .var1(perkStyleSelectionDto.getVar1())
                            .var2(perkStyleSelectionDto.getVar2())
                            .var3(perkStyleSelectionDto.getVar3())
                            .build();
                    selections.add(selection);
                }

                perkStyleSelection.setPerkId(perk.getId());
                perkStyleSelection.setSelectionId(selectionId);
                perkStyleSelections.add(perkStyleSelection);
            }
        }
    }
}
