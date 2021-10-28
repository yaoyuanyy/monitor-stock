package com.skyler.web;

import com.google.common.collect.Lists;
import com.skyler.config.StockAccountComposite;
import com.skyler.dto.ResultDto;
import com.skyler.service.StockInfoService;
import com.skyler.util.*;
import com.skyler.service.WXService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
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
 * Created by on 2021-10-23 at 11:00
 */
@RequestMapping("/wx")
@RestController
@Slf4j
public class WXController {

    @Resource private WXService wxService;

    /**
     * 这里与微信公众后台的token保持一致
     */
    private final String token = "skyler";

    /**
     * 微信公众号首次使用服务端配置时调用的接口
     * NOTE: 这里的路径与微信公众后台的url保持一致
     *
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public ResultDto testWX(HttpServletRequest request, HttpServletResponse response) throws IOException {

        log.info("开始签名校验");
        String wXSignature = request.getParameter("signature");
        String timestamp = request.getParameter("timestamp");
        String nonce = request.getParameter("nonce");
        String echostr = request.getParameter("echostr");

        //排序
        String sortString = Decript.sort(token, timestamp, nonce);
        //加密
        String mySignature = Decript.SHA1(sortString);
        //校验签名
        if (StringUtils.isNotBlank(mySignature) && mySignature.equals(wXSignature)) {
            log.info("签名校验通过");
            // 如果检验成功输出echostr，微信服务器接收到此输出，才会确认检验完成。
            response.getWriter().println(echostr);
        } else {
            log.info("签名校验失败");
        }
        return null;
    }

    @RequestMapping(value = "/getAccessTokenTest", method = RequestMethod.GET)
    public ResultDto getAccessTokenTest() throws IOException {
        return wxService.getAccessTokenTest();
    }

    /**
     *
     * @param accountCode
     * @param content
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/sendKeFuMessage", method = RequestMethod.GET)
    public ResultDto sendKeFuMessage(@RequestParam("accountCode") String accountCode, @RequestParam("content") String content) throws IOException {
        Map<String, Object> contentMap = new HashMap<>();
        contentMap.put(accountCode, content);
        Map<String, List<Map<String, Object>>> stringListMap = new HashMap<>();
        stringListMap.compute(accountCode, (k ,v) -> {
            if(CollectionUtils.isEmpty(v)) {
                v = new ArrayList<>();
            }
            v.add(contentMap);
            return v;
        });
        return wxService.sendKeFuMessage(stringListMap);
    }
}
