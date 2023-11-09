package com.ljc.blockchain;

import com.google.protobuf.InvalidProtocolBufferException;
import io.grpc.stub.StreamObserver;
import org.chainmaker.pb.common.ChainmakerBlock;
import org.chainmaker.pb.common.ResultOuterClass;
import org.chainmaker.sdk.ChainClient;

public class Subscribe extends InitClient implements Runnable {

    public void run() {
        testSubscribeBlock();
    }

    static public void testSubscribeBlock() {
        StreamObserver<ResultOuterClass.SubscribeResult> responseObserver = new StreamObserver<ResultOuterClass.SubscribeResult>() {
            @Override
            public void onNext(ResultOuterClass.SubscribeResult result) {
                try {
                    ChainmakerBlock.BlockInfo blockInfo = ChainmakerBlock.BlockInfo.parseFrom(result.getData());
                    System.out.println("订阅到了");
                    System.out.println(blockInfo.toString());
                } catch (InvalidProtocolBufferException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable throwable) {
                // just do nothing
                System.out.println(throwable.getMessage());
            }

            @Override
            public void onCompleted() {
                // just do nothing
            }
        };
        try {
            chainClient.subscribeBlock(-1, -1, true, false, responseObserver);
            System.out.println("开始订阅");
            Thread.sleep(500000000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
