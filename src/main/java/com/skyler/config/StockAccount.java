package com.skyler.config;

import com.futu.openapi.pb.QotCommon;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Description:
 * <pre>
 *
 * </pre>
 * NB.
 *
 * @author skyler_11@163.com
 * Created by on 2021-10-25 at 16:17
 */
@Component
@ConfigurationProperties(prefix = "stock-account")
public class StockAccount {

    private double defaultRate = 5;
    private Map<String, List<Account>> account;

    public double getDefaultRate() {
        return defaultRate;
    }

    public void setDefaultRate(double defaultRate) {
        this.defaultRate = defaultRate;
    }

    public Map<String, List<Account>> getAccount() {
        return account;
    }

    public void setAccount(Map<String, List<Account>> account) {
        this.account = account;
    }

    public static class Account {

        private int market;
        private String code;
        private double rate;

        public int getMarket() {
            return market;
        }

        public void setMarket(int market) {
            this.market = market;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public double getRate() {
            return rate;
        }

        public void setRate(double rate) {
            this.rate = rate;
        }
    }
}
