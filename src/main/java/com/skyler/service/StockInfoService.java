package com.skyler.service;

import com.alibaba.fastjson.JSON;
import com.futu.openapi.FTAPI;
import com.futu.openapi.FTAPI_Conn_Qot;
import com.futu.openapi.pb.QotCommon;
import com.futu.openapi.pb.QotGetPlateSecurity;
import com.futu.openapi.pb.QotGetSecuritySnapshot;
import com.futu.openapi.pb.QotGetStaticInfo;
import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.protobuf.util.JsonFormat;
import com.skyler.config.StockAccountComposite;
import com.skyler.dto.ResultDto;
import com.skyler.dto.futu.Basic;
import com.skyler.dto.futu.SnapshotList;
import com.skyler.dto.futu.StaticInfo;
import com.skyler.dto.futu.StockResponse;
import com.skyler.service.futu.QotDemo;
import com.skyler.util.StockMetaInfoContainer;
import com.skyler.util.WXHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.HashMap;
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
 * Created by on 2021-10-23 at 21:43
 */
@Service
@Slf4j
public class StockInfoService {

    @Autowired
    private QotDemo qotDemo;
    @Resource
    private WXService wxService;
    @Autowired
    private StockAccountComposite stockAccountComposite;

    /**
     * 是否进行过滤
     */
    @Value("#{new Boolean('${send.isFilter}')}")
    private boolean isFilter;

    @PostConstruct
    public void init() {
        FTAPI.init();
    }

    public ResultDto getStockInfo() {
        FTAPI_Conn_Qot qot = qotDemo.getQot();

        List<QotCommon.Security> securityList = stockAccountComposite.getSecurityList();
        QotGetSecuritySnapshot.Request req = QotGetSecuritySnapshot.Request.newBuilder()
                .setC2S(QotGetSecuritySnapshot.C2S.newBuilder()
                        .addAllSecurityList(securityList)
                        .build()
                ).build();
        int seqNo = qot.getSecuritySnapshot(req);
        log.info("Send QotGetSecuritySnapshot: {}\n", seqNo);

        return new ResultDto().success("success");
    }

    public ResultDto getStockInfo_CallBack(String futuJson) {


        if (isFilter && !SendRateFilter.golbalFilter()) {
            log.warn("isFilter:{} 只在股票交易时间发送", isFilter);
            return new ResultDto().success("只在股票交易时间发送");
        }

        // key:stockCode
        Map<String, List<StockAccountComposite.AccountAndRate>> stockCode2AccountsMap = stockAccountComposite.stockCode2AccountsMap();

        // key:account value:要发送的股票信息
        Map<String, List<Map<String, Object>>> account2StockInfosMap = new HashMap<>();

        StockResponse stockResponse = JSON.parseObject(futuJson, StockResponse.class);
        List<SnapshotList> snapshotList = stockResponse.getS2c().getSnapshotList();
        for (SnapshotList snapshot : snapshotList) {
            Basic basic = snapshot.getBasic();
            List<StockAccountComposite.AccountAndRate> accountAndRates = stockCode2AccountsMap.get(basic.getSecurity().getCode());
            if(!CollectionUtils.isEmpty(accountAndRates)) {
                // key:account innerKey:xxx
                SendRateFilter.filter(accountAndRates, basic, account2StockInfosMap);
            }
        }

        log.info("获取股票信息并发送微信公众号 account2StockInfosMap:{}", JSON.toJSONString(account2StockInfosMap));
        // 发向终端 短信 微信公众号 邮箱
        wxService.sendKeFuMessage(account2StockInfosMap);

        return new ResultDto().success("success");
    }


    /**
     * QotMarket_Unknown = 0; //未知市场
     * QotMarket_HK_Security = 1; //香港市场
     * QotMarket_US_Security = 11; //美国市场
     * QotMarket_CNSH_Security = 21; //沪股市场
     * QotMarket_CNSZ_Security = 22; //深股市场
     *
     * @return
     */
    public ResultDto getPlateSecurity() {
        FTAPI_Conn_Qot qot = qotDemo.getQot();
        Map<Integer, String> plateMap = new HashMap<>();
        plateMap.put(QotCommon.QotMarket.QotMarket_HK_Security_VALUE, "BK1910");
        plateMap.put(QotCommon.QotMarket.QotMarket_CNSH_Security_VALUE, "3000005");

        plateMap.forEach((market, code) -> {
            QotGetPlateSecurity.C2S c2s = QotGetPlateSecurity.C2S.newBuilder()
                    .setPlate(QotCommon.Security.newBuilder()
                            .setMarket(market)
                            .setCode(code)
                            .build())
                    .build();
            QotGetPlateSecurity.Request req = QotGetPlateSecurity.Request.newBuilder().setC2S(c2s).build();
            int seqNo = qot.getPlateSecurity(req);
            log.info("Send QotGetPlateSecurity: {}\n", seqNo);
        });


        return new ResultDto().success("success");
    }

    public ResultDto GetPlateSecurity_CallBack(String futuJson) {
        Map<String, String> container = StockMetaInfoContainer.getContainer();

        StockResponse stockResponse = JSON.parseObject(futuJson, StockResponse.class);
        List<StaticInfo> staticInfoList = stockResponse.getS2c().getStaticInfoList();
        for (StaticInfo staticInfo : staticInfoList) {
            container.put(staticInfo.getBasic().getSecurity().getCode(), staticInfo.getBasic().getName());
        }

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        log.info("StockMetaInfoContainer container已存入股票静态数据 container size:{} container:{}", container.size(), gson.toJson(container));


        return new ResultDto().success("success");
    }
}
