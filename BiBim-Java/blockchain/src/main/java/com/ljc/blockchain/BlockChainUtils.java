package com.ljc.blockchain;

import com.google.protobuf.ByteString;
import com.ljc.model.vo.DepositoryVo;
import org.chainmaker.pb.common.ResultOuterClass;
import org.chainmaker.pb.discovery.Discovery;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;

import static com.ljc.blockchain.InitClient.inItChainClient;

/**
 * 区块链工具类
 *
 * @author dachengzi
 * @Date 2023/2/11 22:01
 */
@Component
public class BlockChainUtils {

    /**
     * 调用存证合约
     *
     * @param depositoryVo 存证信息Vo
     */
    public static String invokeDepositoryContract(DepositoryVo depositoryVo) throws Exception {
        HashMap<String, byte[]> params = new HashMap<>();
        params.put("userid", depositoryVo.getUserId().getBytes());
        params.put("name", depositoryVo.getName().getBytes());
        params.put("content", depositoryVo.getContent().getBytes());
        params.put("hash", depositoryVo.getHash().getBytes());
        params.put("time", depositoryVo.getTime().getBytes());
        // 创建区块链客户端
        inItChainClient();
        ResultOuterClass.TxResponse responseInfo = null;

        // 调用
        responseInfo = InitClient.chainClient.invokeContract("bibim", "save", null, params, 10000, 10000);
        ByteString result = responseInfo.getContractResult().getResult();
        String res = result.toString(StandardCharsets.UTF_8);

        return res;
    }

    /**
     * 调用查询合约
     *
     * @param hashCode hash代码
     */
    public static String invokeQueryContract(String hashCode) throws Exception {
        HashMap<String, byte[]> param = new HashMap<>();
        param.put("hash", hashCode.getBytes());
        // 创建区块链客户端
        inItChainClient();
        ResultOuterClass.TxResponse responseInfo = null;

        // 调用
        responseInfo = InitClient.chainClient.invokeContract("bibim", "findByHash", null, param, 10000, 10000);
        ByteString result = responseInfo.getContractResult().getResult();
        String res = result.toString(StandardCharsets.UTF_8);

        return res;
    }

    public static String getChainInfo() throws Exception {
        // 创建区块链客户端
        inItChainClient();
        Discovery.ChainInfo responseInfo = null;

        // 调用
        responseInfo = InitClient.chainClient.getChainInfo(10000);
        responseInfo.getBlockHeight();
        return responseInfo.toString();
    }
}
