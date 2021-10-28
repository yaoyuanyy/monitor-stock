package com.skyler.service;

import com.skyler.config.StockAccountComposite;
import com.skyler.dto.futu.Basic;
import com.skyler.util.BussinessContainer;
import com.skyler.util.WXHelper;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Description:
 * <pre>
 *
 * </pre>
 * NB.
 *
 * @author skyler_11@163.com
 * Created by on 2021-10-25 at 18:14
 */
public class SendRateFilter {

    /**
     * 未达到目标涨跌幅 or 一小时内免重复发送 or 只在股票交易时间发送
     *
     * @param accountAndRates
     * @param basic
     * @param account2StockInfosMap
     * @return Map<String, Map<String, Object>> // key:account value:即将要发送的stockInfo
     */
    public static void filter(List<StockAccountComposite.AccountAndRate> accountAndRates,
                                                          Basic basic,
                                                          Map<String, List<Map<String, Object>>> account2StockInfosMap) {

        Map<String, Object> stockInfoMap = WXHelper.toMap(basic);

        for (StockAccountComposite.AccountAndRate accountAndRate : accountAndRates) {
            // pre: 发送时间 + 1小时
            // 一小时内免重复发送
            LocalDateTime pre = BussinessContainer.getValue(accountAndRate.getStockCode());
            LocalDateTime now = LocalDateTime.now();
            if(Objects.nonNull(pre) && pre.isAfter(now)) {
                continue;
            }

            // 未达到目标涨跌幅
            if( Math.abs(basic.getCurPrice()) < accountAndRate.getRate()) {
                continue;
            }

            account2StockInfosMap.compute(accountAndRate.getAccountCode(), (k, v) -> {
                if(CollectionUtils.isEmpty(v)) {
                    v = new ArrayList<>();
                }
                v.add(stockInfoMap);
                return v;
            });
        }
    }

    /**
     * 只在股票交易时间发送
     * @return
     */
    public static boolean golbalFilter() {

        // 只在股票交易时间发送
        LocalTime nowLocalTime = LocalTime.now();
        if(nowLocalTime.isBefore(LocalTime.parse("09:30")) || nowLocalTime.isAfter(LocalTime.parse("16:00"))) {
            return false;
        }

        return true;
    }
}
