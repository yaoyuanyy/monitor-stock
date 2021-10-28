package com.skyler.schedule;

import com.skyler.service.StockInfoService;
import com.skyler.util.StockMetaInfoContainer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * Description:
 * <pre>
 *
 * </pre>
 * NB.
 *
 * @author skyler_11@163.com
 * Created by on 2021-10-23 at 17:11
 */
@Component
@Slf4j
public class StockInfoLoopSchedule {

    @Resource StockInfoService stockInfoService;

    /**
     * 获取板块内股票列表
     */
    @PostConstruct
    public void init(){
        log.info("StockInfoLoopSchedule getPlateSecurity");
        StockMetaInfoContainer.getContainer().clear();
        stockInfoService.getPlateSecurity();
    }

    /**
     * 获取指定股票详情
     */
    @Scheduled(initialDelay = 1000*2, fixedDelay = 1000*10000)
    public void getStockInfo() {
        log.info("StockInfoLoopSchedule getStockInfo");
        stockInfoService.getStockInfo();
    }
}
