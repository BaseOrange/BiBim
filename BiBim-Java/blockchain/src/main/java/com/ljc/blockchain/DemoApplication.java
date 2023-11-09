package com.ljc.blockchain;

import static com.ljc.blockchain.InitClient.inItChainClient;

//@SpringBootApplication
public class DemoApplication {


    public static void main(String[] args) throws Exception {
        //SpringApplication.run(DemoApplication.class, args);
        inItChainClient();
        //查询链配置
        ChainConfig.getChainConfig(InitClient.chainClient);
        //创建合约
        //        Contract.createContract(InitClient.chainClient, InitClient.adminUser1, InitClient.adminUser2, InitClient.adminUser3);
        //调用合约
        Contract.invokeContract(InitClient.chainClient);
    }
}

