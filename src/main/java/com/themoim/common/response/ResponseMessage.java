package com.themoim.common.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import org.springframework.http.HttpStatus;


@Builder
public class ResponseMessage<T>{
    private int code;
    private String message;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;

    public static <T> ResponseMessage<T> ok(T data){
        return (ResponseMessage<T>)ResponseMessage.builder()
                .code(HttpStatus.OK.value())
                .message("OK")
                .data(data)
                .build();
    }
}
