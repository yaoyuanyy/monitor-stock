package com.skyler.util;

import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class OkHttpUtil {

    private static final Logger logger = LoggerFactory.getLogger(OkHttpUtil.class);

    /**
     * 默认的连接对象
     */
    public static final OkHttpClient httpClient = new OkHttpClient.Builder()
            .connectTimeout(5, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .build();

    /**
     * get 请求
     *
     * @param url
     * @return
     */
    public static OkHttpResponse get(String url) {
        logger.info("【get】调用接口：{}", url);
        Request request = new Request.Builder()
                .url(url)
                .build();
        try {
            return getResponseContent(request);
        } catch (IOException e) {
            logger.error("【get】调用接口异常URL：{}", url, e);
            throw new RuntimeException("Http Get请求失败");
        }
    }

    /**
     * get 请求
     *
     * @param getUrl http调用URL
     * @return
     */
    public static OkHttpResponse getWithHeader(String getUrl, Map<String, String> headerMap) {
        Request.Builder builder = new Request.Builder();
        builder.url(getUrl);
        if (headerMap != null && !headerMap.isEmpty()) {
            for (Map.Entry<String, String> entry : headerMap.entrySet()) {
                builder.addHeader(entry.getKey(), entry.getValue());
            }
        }
        Request request = builder.build();
        try {
            return getResponseContent(request);
        } catch (IOException e) {
            logger.error("getWithHeader访问异常", e);
            throw new RuntimeException("Http Get请求失败");
        }
    }

    /**
     * 提交post json请求
     *
     * @param url
     * @param requestBodyStr
     * @return
     */
    public static OkHttpResponse postJson(String url, String requestBodyStr) {
        logger.info("【postJson】调用接口：{}，入参为：{}", url, requestBodyStr);
        try {
            RequestBody requestBody =
                    RequestBody.create(MediaType.parse("application/json; charset=utf-8"), requestBodyStr);
            Request request = new Request.Builder().url(url).post(requestBody).build();

            return getResponseContent(request);
        } catch (Exception e) {
            logger.error("【postJson】调用接口异常URL：{}", url, e);
            throw new RuntimeException("Http Get请求失败");
        }

    }

    /**
     * form-data
     *
     * @param url
     * @param paramMap
     * @return
     */
    public static OkHttpResponse postFormData(String url, Map<String, Object> paramMap) {
        logger.info("【postFormData】调用接口：{}，入参为：{}", url, paramMap);
        try {
            FormBody.Builder builder = new FormBody.Builder();
            for (Map.Entry<String, Object> entry : paramMap.entrySet()) {
                if (entry.getValue() != null) {
                    builder.addEncoded(entry.getKey(), entry.getValue().toString());
                }
            }
            Request request = new Request.Builder().url(url).post(builder.build()).build();

            return getResponseContent(request);
        } catch (Exception e) {
            logger.error("【postFormData】调用接口异常URL：{}", url, e);
            throw new RuntimeException("Http Get请求失败");
        }
    }

    /**
     * 返回响应结果
     *
     * @param request
     * @return
     * @throws IOException
     */
    private static OkHttpResponse getResponseContent(Request request) throws IOException {
        Response response = httpClient.newCall(request).execute();
        ResponseBody body = response.body();
        String bodyContent = body.string();
        return new OkHttpResponse(response.code(), bodyContent);
    }

}