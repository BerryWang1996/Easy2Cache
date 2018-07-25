package com.github.berrywang1996.easy2cache;

import com.github.berrywang1996.easy2cache.consts.RedisMode;
import org.junit.Test;

/**
 * @author 王伯瑞
 * @version V1.0.0
 */
public class Easy2CacheConnectionFactoryTest {

    @Test
    public void createConnection() {

        // 创建配置文件用于创建连接
        Easy2CacheConfig config = new Easy2CacheConfig();
        config.setRedisMode(RedisMode.CLUSTER);

        // 创建连接对象
        Easy2CacheConnection connection = Easy2CacheConnectionFactory.createConnection(config);

        // 获取操作客户端
        Easy2CacheClient client = connection.getClient();

        // 保存数据
        UserCacheChannel userChannel = new UserCacheChannel();
        userChannel.setRealKey(UserChannel.PREFIX + "123");
        userChannel.setValue(new User());
        client.set(userChannel);

        // 设置数据
        UserCacheChannel userCacheChannel = client.get(userChannel);
        User value = userCacheChannel.getValue();
        System.out.println(value);

        // 关闭客户端
        client.close();

        // 关闭连接
        connection.close();

    }

    private class UserChannel extends Easy2CacheChannel<Object> {

        private static final String PREFIX = "user";

    }

}