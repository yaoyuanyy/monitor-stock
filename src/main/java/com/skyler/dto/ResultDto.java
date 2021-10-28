package com.skyler.dto;

import com.alibaba.fastjson.JSON;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Description:
 * <pre>
 *
 * </pre>
 * NB.
 *
 * @author skyler_11@163.com
 * Created by on 2021-10-23 at 11:01
 */
@Data
@NoArgsConstructor
@Builder
public class ResultDto<T> {
    T data;
    String msg;
    int code;
    long time;
    String timeStr;

    public ResultDto(T data, String msg, int code, long time, String timeStr) {
        this.data = data;
        this.msg = msg;
        this.code = code;
        this.time = time;
        this.timeStr = timeStr;
    }

    public ResultDto success(T data){
        this.data = data;
        this.msg = "success";
        this.code = 200;
        this.time = System.currentTimeMillis();
        this.timeStr = JSON.toJSONString(new Date());
        return this;
    }

    public ResultDto fail(String errorMsg){
        this.data = null;
        this.msg = errorMsg;
        this.code = 500;
        this.time = System.currentTimeMillis();
        this.timeStr = JSON.toJSONString(new Date());
        return this;
    }
}
