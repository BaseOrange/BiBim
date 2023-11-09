package com.ljc.blockchain;

import org.chainmaker.pb.common.ContractOuterClass;
import org.chainmaker.pb.common.Request;
import org.chainmaker.pb.common.ResultOuterClass;
import org.chainmaker.sdk.ChainClient;
import org.chainmaker.sdk.User;
import org.chainmaker.sdk.utils.FileUtils;
import org.chainmaker.sdk.utils.SdkUtils;


public class Contract {

    private static final String QUERY_CONTRACT_METHOD = "query";
    private static final String INVOKE_CONTRACT_METHOD = "increase";
    private static final String CONTRACT_NAME = "counter";
    private static final String CONTRACT_FILE_PATH = "rust-fact-1.0.0.wasm";

    /**
     * 创建合约
     *
     * @param chainClient
     * @param adminUser
     */
    public static void createContract(ChainClient chainClient, User adminUser) {
        ResultOuterClass.TxResponse responseInfo = null;
        try {
            byte[] byteCode = FileUtils.getResourceFileBytes(CONTRACT_FILE_PATH);

            // 1. create payload
            Request.Payload payload = chainClient.createContractCreatePayload(CONTRACT_NAME, "1", byteCode, ContractOuterClass.RuntimeType.WASMER, null);
            //2. create payloads with endorsement
            Request.EndorsementEntry[] endorsementEntries = SdkUtils.getEndorsers(payload, new User[]{adminUser});

            // 3. send request
            responseInfo = chainClient.sendContractManageRequest(payload, endorsementEntries, 10000, 10000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(responseInfo);
    }

    public static void invokeContract(ChainClient chainClient) {
        ResultOuterClass.TxResponse responseInfo = null;
        try {
            responseInfo = chainClient.invokeContract(CONTRACT_NAME, INVOKE_CONTRACT_METHOD, null, null, 10000, 10000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(responseInfo);
    }

    public static void queryContract(ChainClient chainClient) {
        ResultOuterClass.TxResponse responseInfo = null;
        try {
            responseInfo = chainClient.queryContract(CONTRACT_NAME, QUERY_CONTRACT_METHOD, null, null, 10000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(responseInfo);
    }
}
