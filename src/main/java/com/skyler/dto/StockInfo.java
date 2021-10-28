package com.skyler.dto;

import com.skyler.dto.futu.Basic;
import lombok.Builder;
import lombok.Data;

/**
 * Description:
 * <pre>
 *
 * </pre>
 * NB.
 *
 * @author skyler_11@163.com
 * Created by on 2021-10-25 at 12:09
 */
@Data
@Builder
public class StockInfo {

    private String name;
    double curPrice;
    double openPrice;
    double rate;

    public static StockInfo toBeanfrom(Basic basic){
        return StockInfo.builder()
                .name(basic.getVolume())
                .curPrice(basic.getCurPrice())
                .openPrice(basic.getOpenPrice())
                .rate((basic.getCurPrice() - basic.getOpenPrice()) / basic.getOpenPrice())
                .build();
    }
}
