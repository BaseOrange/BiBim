package com.ljc.system.controller.system;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ljc.common.result.Result;
import com.ljc.model.enums.BusinessType;
import com.ljc.system.annotation.Log;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * 区块链控制器
 *
 * @author dachengzi
 * @since 2023/1/29 21:36
 */
@Api(tags = "区块链对接接口")
@RestController
@RequestMapping("/system/blockchain")
@Slf4j
public class BlockChainController {
    @Autowired
    private RestTemplate restTemplate;

    /**
     * 获取区块链信息
     *
     * @return
     */
    @Log(title = "区块链管理", businessType = BusinessType.OTHER)
    @ApiOperation("获取区块链信息")
    @GetMapping("/getBlockchainInfo")
    @PreAuthorize("hasAnyAuthority('ROOT','ADMIN','SCENER','USER')")
    public Result getBlockchainInfo() {
        /**
         * getForObject
         * 参数1 要请求的地址的url  必填项
         * 参数2 响应数据的类型 是String 还是 Map等 必填项
         * 参数3 请求携带参数 选填
         * getForObject 方法的返回值就是 被调用接口响应的数据
         */
        String url = "http://192.168.23.23:9997/chainmaker?cmb=Decimal";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        MultiValueMap map = new LinkedMultiValueMap();
        map.add("ChainId", "bibim");
        HttpEntity requestBody = new HttpEntity(map, headers);


        //先获取返回的字符串，若想获取属性，可以使用gson转化为实体后get方法获取
        String res = restTemplate.exchange(url, HttpMethod.GET, requestBody, String.class).getBody();
        JSONObject jsonObject = JSON.parseObject(res);
        log.info("【获取区块链信息响应】jsonObject：{}", jsonObject);
        return Result.ok(jsonObject);
    }

    /**
     * 获取区块链交易列表
     *
     * @return
     */
    @Log(title = "区块链管理", businessType = BusinessType.OTHER)
    @ApiOperation("获取区块链交易列表")
    @GetMapping("/getTransactionList/{Offset}/{Limit}")
    @PreAuthorize("hasAnyAuthority('ROOT','ADMIN','SCENER','USER')")
    public Result getTransactionList(@PathVariable("Offset") String offset, @PathVariable("Limit") String limit) {
        String url = "http://192.168.23.23:9997/chainmaker?cmb=GetTxList";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        MultiValueMap map = new LinkedMultiValueMap();
        map.add("Offset", offset);
        map.add("Limit", limit);
        HttpEntity requestBody = new HttpEntity(map, headers);


        //先获取返回的字符串，若想获取属性，可以使用gson转化为实体后get方法获取
        String res = restTemplate.exchange(url, HttpMethod.GET, requestBody, String.class).getBody();
        JSONObject jsonObject = JSON.parseObject(res);
        log.info("【获取区块链交易列表响应】jsonObject：{}", jsonObject);
        return Result.ok(jsonObject);
    }

    /**
     * 获取交易信息
     *
     * @return
     */
    @Log(title = "区块链管理", businessType = BusinessType.OTHER)
    @ApiOperation("获取交易信息")
    @GetMapping("/getTransactionInfo/{TxId}")
    @PreAuthorize("hasAnyAuthority('ROOT','ADMIN','SCENER','USER')")
    public Result getTransactionInfo(@PathVariable("TxId") String txId) {
        String url = "http://192.168.23.23:9997/chainmaker?cmb=GetTxDetail";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        MultiValueMap map = new LinkedMultiValueMap();
        map.add("TxId", txId);
        map.add("ChainId", "bibim");
        HttpEntity requestBody = new HttpEntity(map, headers);


        //先获取返回的字符串，若想获取属性，可以使用gson转化为实体后get方法获取
        String res = restTemplate.exchange(url, HttpMethod.GET, requestBody, String.class).getBody();
        JSONObject jsonObject = JSON.parseObject(res);
        log.info("【获取交易信息响应】jsonObject：{}", jsonObject);
        return Result.ok(jsonObject);
    }

    /**
     * 24小时交易数量
     *
     * @return
     */
    @Log(title = "区块链管理", businessType = BusinessType.OTHER)
    @ApiOperation("24小时交易数量")
    @GetMapping("/getTransVolume")
    @PreAuthorize("hasAnyAuthority('ROOT','ADMIN','SCENER','USER')")
    public Result getTransVolume() {
        String url = "http://192.168.23.23:9997/chainmaker?cmb=GetTransactionNumByTime";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        MultiValueMap map = new LinkedMultiValueMap();
        map.add("ChainId", "bibim");
        HttpEntity requestBody = new HttpEntity(map, headers);


        //先获取返回的字符串，若想获取属性，可以使用gson转化为实体后get方法获取
        String res = restTemplate.exchange(url, HttpMethod.GET, requestBody, String.class).getBody();
        JSONObject jsonObject = JSON.parseObject(res);
        log.info("【24小时交易数量】jsonObject：{}", jsonObject);
        return Result.ok(jsonObject);
    }
}
