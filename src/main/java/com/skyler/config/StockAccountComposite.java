package com.skyler.config;

import com.alibaba.fastjson.JSON;
import com.futu.openapi.pb.QotCommon;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Description:
 * <pre>
 *
 * </pre>
 * NB.
 *
 * @author skyler_11@163.com
 * Created by on 2021-10-25 at 19:58
 */
@Component
@Slf4j
public class StockAccountComposite {

    @Autowired
    private StockAccount stockAccount;

    List<QotCommon.Security> securityList = new ArrayList<>();
    Map<String, AccountAndRate> stockCode2AccountMap = new ConcurrentHashMap<>();
    Map<String, List<AccountAndRate>> stockCode2AccountsMap = new ConcurrentHashMap<>();

    public List<QotCommon.Security> getSecurityList() {

        if (CollectionUtils.isEmpty(securityList)) {
            synchronized (this) {
                if (CollectionUtils.isEmpty(securityList)) {
                    log.info("stockAccount:{}", JSON.toJSONString(stockAccount));
                    /**
                     * A股是分沪深两市的，沪市主要是数字6开头的股票，那么深市是包含创业板和中小板的，创业板主要是数字3开头，中小板主要是数字0开头的股票代码
                     * A股: 沪市6开头 深市0开头；港股：1开头
                     */
                    stockAccount.getAccount().forEach((accountCode, accountStock) ->
                            accountStock.forEach(o ->
                                    securityList.add(QotCommon.Security.newBuilder()
                                            .setMarket(o.getMarket())
                                            .setCode(o.getCode())
                                            .build()
                                    )
                            ));
                }
            }
        }

        return securityList;
    }

//    public Map<String, AccountAndRate> stockCode2Account() {
//        if (CollectionUtils.isEmpty(stockCode2AccountMap)) {
//            synchronized (this) {
//                if (CollectionUtils.isEmpty(stockCode2AccountMap)) {
//                    stockAccount.getAccount().forEach((accountCode, accountStock) ->
//                            accountStock.forEach(o ->
//                                    stockCode2AccountMap.put(o.getCode(), new AccountAndRate(accountCode, o.getRate(), o.getCode()))
//                            ));
//                }
//            }
//        }
//
//        return stockCode2AccountMap;
//    }

    public Map<String, List<AccountAndRate>> stockCode2AccountsMap() {
        if (CollectionUtils.isEmpty(stockCode2AccountsMap)) {
            synchronized (this) {
                if (CollectionUtils.isEmpty(stockCode2AccountsMap)) {
                    stockAccount.getAccount().forEach((accountCode, accountStockObj) ->
                            accountStockObj.forEach(o -> {
                                        stockCode2AccountsMap.compute(o.getCode(), (k, v) -> {
                                            if (CollectionUtils.isEmpty(v)) {
                                                v = new ArrayList<>();
                                            }
                                            v.add(new AccountAndRate(accountCode, o.getRate(), o.getCode()));
                                            return v;
                                        });
                                    }
                            ));
                }
            }
        }

        return stockCode2AccountsMap;
    }

    public static class AccountAndRate {

        private String accountCode;
        private double rate;
        private String stockCode;

        public AccountAndRate() {
        }

        public AccountAndRate(String accountCode, double rate, String stockCode) {
            this.stockCode = stockCode;
            this.rate = rate;
            this.accountCode = accountCode;
        }

        public String getAccountCode() {
            return accountCode;
        }

        public void setAccountCode(String accountCode) {
            this.accountCode = accountCode;
        }

        public double getRate() {
            return rate;
        }

        public void setRate(double rate) {
            this.rate = rate;
        }

        public String getStockCode() {
            return stockCode;
        }

        public void setStockCode(String stockCode) {
            this.stockCode = stockCode;
        }
    }
}
