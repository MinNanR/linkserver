package site.minnan.linkserver.config;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import site.minnan.linkserver.utils.RedisUtil;

@Configuration
public class AliyunOssConfig {

    private Logger logger = LoggerFactory.getLogger(AliyunOssConfig.class);

    @Autowired
    private RedisUtil redisUtil;

    @Bean
    public OSS oss(){
        String endpoint = (String) redisUtil.getValue("endpoint");
        String accessKeyId = (String) redisUtil.getValue("accessKeyId");
        String accessKeySecret = (String) redisUtil.getValue("accessKeySecret");
        if (endpoint == null || accessKeyId == null || accessKeySecret == null) {
            logger.warn("加载阿里云配置信息失败，请检查redis缓存");
        }
        return new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
    }
}
