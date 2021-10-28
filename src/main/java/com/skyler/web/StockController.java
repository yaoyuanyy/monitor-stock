package com.skyler.web;

import com.skyler.dto.ResultDto;
import com.skyler.service.StockInfoService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * Description:
 * <pre>
 *
 * </pre>
 * NB.
 *
 * @author skyler_11@163.com
 * Created by on 2021-10-23 at 11:00
 */
@RequestMapping("/")
@RestController
public class StockController {

    @Resource StockInfoService stockInfoService;

    @RequestMapping("/getStockInfo")
    public ResultDto getStockInfo(){
        return stockInfoService.getStockInfo();
    }
}
