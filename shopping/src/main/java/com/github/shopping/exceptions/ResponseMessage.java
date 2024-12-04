package com.github.shopping.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseMessage<C> {

    private String message;
    private Object data;

    // 실패 메시지용 생성자
    public ResponseMessage(String message) {
        this.message = message;
    }
}

