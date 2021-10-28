package com.skyler.util;

import com.alibaba.fastjson.JSONObject;
import com.skyler.dto.futu.Basic;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

import static com.skyler.util.Contants.STOCK_CODE;

/**
 * Description:
 * <pre>
 *
 * </pre>
 * NB.
 *
 * @author skyler_11@163.com
 * Created by on 2021-10-25 at 11:07
 */
@Slf4j
public class WXHelper {


    public static String getAccessToken(String content) {
        JSONObject jsonObject = JSONObject.parseObject(content);
        if(Objects.isNull(jsonObject)) {
            log.warn("access_token is null content:{}", content);
            return null;
        }
        return jsonObject.getString("access_token");
    }

    public static Map<String, Object> toMap(Basic basic) {

        Map<String, String> container = StockMetaInfoContainer.getContainer();

        Map<String, Object> map = new LinkedHashMap<>();
        map.put("时间", LocalDateTime.now());
        map.put("股票名称", container.get(basic.getSecurity().getCode()));
        map.put(STOCK_CODE, basic.getSecurity().getCode());
        map.put("开盘价", basic.getOpenPrice());
        map.put("当前价", basic.getCurPrice());

        BigDecimal bigDecimal = new BigDecimal((basic.getCurPrice() - basic.getOpenPrice()) / basic.getOpenPrice());
        double rate = bigDecimal.setScale(3, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100")).doubleValue();
        map.put("涨跌幅", rate + "%");
        return map;
    }

    public static void main(String[] args) {
        // BigDecimal bigDecimal = new BigDecimal((16.39 - 16.28) / 16.28);
        BigDecimal bigDecimal = new BigDecimal((12 - 10) / 10.0);
        double rate = bigDecimal.setScale(3, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100")).doubleValue();
        System.out.println(rate + "%");
    }
}
