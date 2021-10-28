package com.skyler.service.futu;

import com.futu.openapi.*;
import com.futu.openapi.pb.QotGetPlateSecurity;
import com.futu.openapi.pb.QotGetSecuritySnapshot;
import com.futu.openapi.pb.QotGetStaticInfo;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.util.JsonFormat;
import com.skyler.service.StockInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Description:
 * <pre>
 *
 * </pre>
 * NB.
 *
 * @author skyler_11@163.com
 * Created by on 2021-10-22 at 16:23
 */
@Component
@Slf4j
public class QotDemo implements FTSPI_Qot, FTSPI_Conn {
    FTAPI_Conn_Qot qot = new FTAPI_Conn_Qot();

    @Resource
    private StockInfoService stockInfoService;

    public QotDemo() {
        qot.setClientInfo("javaclient", 1);  //设置客户端信息
        qot.setConnSpi(this);  //设置连接回调
        qot.setQotSpi(this);   //设置交易回调
        qot.initConnect("127.0.0.1", (short)8810, false);
        log.info("QotDemo init");
    }

    public FTAPI_Conn_Qot getQot(){
        return qot;
    }

    @Override
    public void onInitConnect(FTAPI_Conn client, long errCode, String desc) {
        log.info("Qot onInitConnect: ret={} desc={} connID={}\n", errCode, desc, client.getConnectID());
        if (errCode != 0)
            return;
    }

    @Override
    public void onDisconnect(FTAPI_Conn client, long errCode) {
        log.info("Qot onDisConnect: {}\n", errCode);
    }

    @Override
    public void onReply_GetSecuritySnapshot(FTAPI_Conn client, int nSerialNo, QotGetSecuritySnapshot.Response rsp) {
        if (rsp.getRetType() != 0) {
            log.error("QotGetSecuritySnapshot failed: {}\n", rsp.getRetMsg());
        }
        else {
            try {
                String json = JsonFormat.printer().print(rsp);
                log.info("Receive QotGetSecuritySnapshot:\n{}", json);
                stockInfoService.getStockInfo_CallBack(json);
            } catch (InvalidProtocolBufferException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onReply_GetPlateSecurity(FTAPI_Conn client, int nSerialNo, QotGetPlateSecurity.Response rsp) {
        if (rsp.getRetType() != 0) {
            log.warn("GetPlateSecurity failed: {}\n", rsp.getRetMsg());
        }
        else {
            try {
                String json = JsonFormat.printer().print(rsp);
                // log.info("Receive GetPlateSecurity:\n{}", json);
                stockInfoService.GetPlateSecurity_CallBack(json);
            } catch (InvalidProtocolBufferException e) {
                e.printStackTrace();
            }
        }
    }
}

