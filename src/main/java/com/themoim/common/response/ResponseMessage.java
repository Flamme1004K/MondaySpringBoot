package com.themoim.common.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;


@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponseMessage<T>{
    private int code;
    private String message;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static <T> ResponseMessage<T> ok(T data){
        return (ResponseMessage<T>)ResponseMessage.builder()
                .code(HttpStatus.OK.value())
                .message("OK")
                .data(data)
                .build();
    }
}
