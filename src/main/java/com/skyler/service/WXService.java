package com.skyler.service;

import com.skyler.config.StockAccountComposite;
import com.skyler.dto.ResultDto;
import com.skyler.util.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static com.skyler.util.Contants.ACCESS_TOKE_TEST_ACCOUNT;
import static com.skyler.util.Contants.STOCK_CODE;

/**
 * Description:
 * <pre>
 *
 * </pre>
 * NB.
 *
 * @author skyler_11@163.com
 * Created by on 2021-10-25 at 11:31
 */
@Service
@Slf4j
public class WXService {

    /**
     * 微信测试账号
     *
     * @return
     */
    public ResultDto<String> getAccessTokenTest() {

        String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=wx8b155c4ec2e938f9&secret=b725e2c33c28c7f97999acd5683eec13";
        OkHttpResponse responseBo = OkHttpUtil.get(url);

        String accessToken = WXHelper.getAccessToken(responseBo.getContent());
        log.info("Container.set -> accessTokenTest:{}", accessToken);

        Container.setToken(ACCESS_TOKE_TEST_ACCOUNT, accessToken);

        return new ResultDto().success(accessToken);
    }

    /**
     * <pre>
     * eg:
     * {
     *     "touser": "owdlp5ikqoleyI40wXc66b4Gu7_A",
     *     "msgtype": "text",
     *     "text": {
     *         "content": "Hello World skyler"
     *     }
     * }
     *
     * refer to:
     * https://mp.weixin.qq.com/debug/cgi-bin/sandboxinfo?action=showinfo&t=sandbox/index
     * https://mp.weixin.qq.com/debug?token=1897559449&lang=zh_CN
     * </pre>
     *
     * @param account2StockInfosMap key:account value:要发送的股票信息
     */
    public ResultDto sendKeFuMessage(Map<String, List<Map<String, Object>>>  account2StockInfosMap) {
        String access_token = Container.getToken(ACCESS_TOKE_TEST_ACCOUNT);
        if (StringUtils.isBlank(access_token)) {
            log.info("not exist access_token 立即重新获取");
            // 检测是否有access_token，否则立即重新获取
            ResultDto<String> data = getAccessTokenTest();
            access_token = data.getData();
        }

        String accessToken = access_token;
        account2StockInfosMap.forEach((accountCode, stockInfoMap) -> {
            String content = this.toContent(stockInfoMap);
            String requestJson = "{\"touser\":\"" + accountCode + "\",\"msgtype\":\"text\",\"text\":{\"content\":\"" + content + "\"}}";
            String url = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=" + accessToken;

            OkHttpResponse responseBo = OkHttpUtil.postJson(url, requestJson);
            log.info("responseBo:{}", responseBo);
        });

        return new ResultDto().success("success");
    }

    private String toContent(List<Map<String, Object>> stockInfoMaps) {
        StringBuilder stringBuilder = new StringBuilder();

        for (Map<String, Object> stockInfoMap : stockInfoMaps) {
            // 放入容器，限频
            BussinessContainer.set((String)stockInfoMap.get(STOCK_CODE), LocalDateTime.now().plusHours(1));

            stockInfoMap.forEach((k, v) -> stringBuilder.append(k).append(":").append(v).append("\n"));
            stringBuilder.append("\n\n");
        }

        return stringBuilder.toString();
    }
}
