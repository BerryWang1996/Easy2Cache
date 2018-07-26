package com.github.berrywang1996.easy2cache;

import com.github.berrywang1996.easy2cache.consts.RedisMode;
import com.github.berrywang1996.easy2cache.core.*;
import com.lambdaworks.redis.SetArgs;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 王伯瑞
 * @version V1.0.0
 */
@Slf4j
public class Easy2CacheConnectionFactoryTest {

    @Test
    public void createConnection() {

        // 创建配置文件用于创建连接
        Easy2CacheConfig config = new Easy2CacheConfig();
        config.setHost("192.168.1.233");
        config.setPort(6379);
        config.setDatabaseNum(4);

        // 创建连接对象
        Easy2CacheConnection connection = Easy2CacheConnectionFactory.createConnection(config);

        // 获取操作客户端
        AbstractEasy2CacheClient client = connection.getClient();

        // 创建保存对象
        User user = new User();
        user.setId(100L);
        user.setUsername("berry");
        user.setPassword("123456");

        // 保存数据
        UserCacheChannel userChannel = new UserCacheChannel();
        userChannel.setRealKey("123");
        userChannel.setValue(user);
        client.set(userChannel);

        // 获取数据
        UserCacheChannel userCacheChannel = client.get(userChannel);
        User value = userCacheChannel.getValue();
        log.info("get value from redis : {}", value);

        // 关闭客户端
        client.close();

        // 关闭连接
        connection.close();

    }

}