package com.ljc.common.utils;

import io.minio.GetPresignedObjectUrlArgs;
import io.minio.MinioClient;
import io.minio.PostPolicy;
import io.minio.UploadObjectArgs;
import io.minio.errors.ErrorResponseException;
import io.minio.errors.InsufficientDataException;
import io.minio.errors.InternalException;
import io.minio.errors.InvalidResponseException;
import io.minio.errors.ServerException;
import io.minio.errors.XmlParserException;
import io.minio.http.Method;
import org.springframework.http.MediaType;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.ZonedDateTime;
import java.util.Map;
import java.util.UUID;

/**
 * @author dachengzi
 * @Date 2023/1/30 14:12
 */
public class MinioUtils {
    private static volatile MinioClient MINIO_CLIENT = null;

    /**
     * MinIO 客户端
     */
    private static MinioClient getClient() {
        if (MINIO_CLIENT == null) {
            synchronized (MinioClient.class) {
                if (MINIO_CLIENT == null) {
                    MINIO_CLIENT = MinioClient.builder()
                            // minio地址
                            .endpoint("http://192.168.1.1:9000/")
                            // accessKey秘钥，secretKey秘钥
                            .credentials("fqlVJbYtoHQpo2OK", "VQu6QEDL82nehDyJf70nEI5l4I994JXY")
                            // 构建MinioClient对象
                            .build();
                }
            }
        }
        return MINIO_CLIENT;
    }

    /**
     * 获取上传路径
     *
     * @param extension 文件扩展名
     * @return
     * @throws Exception
     */
    public String getUploadPath(String extension) throws Exception {
        getClient();
        // 文件名为UUID加扩展名
        String fileName = UUID.randomUUID().toString() + "." + extension;
        String uploadPath = MINIO_CLIENT.getPresignedObjectUrl(GetPresignedObjectUrlArgs.builder()
                // 桶名称
                .bucket("bibim")
                // 文件名
                .object(fileName)
                // 上传方法必须为PUT方法
                .method(Method.PUT)
                // 连接过期时间为10分钟
                .expiry(60 * 10).build());
        return uploadPath;
    }


}
