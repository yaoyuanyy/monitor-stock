package com.skyler.util;

import lombok.*;

@Data
@NoArgsConstructor
public class OkHttpResponse {

    private int code;

    private String content;

    public OkHttpResponse(int code, String content) {
        this.code = code;
        this.content = content;
    }
}
