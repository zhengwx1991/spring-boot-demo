package com.sysware.elasticsearch.utils;

import lombok.Getter;
import lombok.Setter;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 描述：
 *
 * @Author: zwx
 * @Company 云迹科技
 * @Date: 2019/10/15 13:37
 */
@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "elasticsearch")
public class EsConfig {

    private String host;

    private Integer port;

    @Bean(destroyMethod = "close")
    public RestHighLevelClient client(){
        return new RestHighLevelClient(RestClient.builder(new HttpHost(host, port, "http")));
    }
}
