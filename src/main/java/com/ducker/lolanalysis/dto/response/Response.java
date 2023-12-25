package com.ducker.lolanalysis.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public class Response<T> {
    private int code;
    private String message;
    private T data;

    public static <T> Response<T> with(int code, String message, T data) {
        return Response
                .<T>builder()
                .code(code)
                .message(message)
                .data(data)
                .build();
    }

    public static <T> Response<T> with(int code, String message) {
        return Response
                .<T>builder()
                .code(code)
                .message(message)
                .build();
    }
}
