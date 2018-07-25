package com.github.berrywang1996.easy2cache;

import com.github.berrywang1996.easy2cache.consts.RedisMode;
import lombok.Getter;
import lombok.Setter;
import org.junit.Test;

import static org.junit.Assert.*;

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
        UserChannel userChannel = new UserChannel();
        userChannel.setRealKey(UserChannel.PREFIX + "123");
        userChannel.setValue(new Object());
        client.set(userChannel);

        // 设置数据
        UserChannel userChannel1 = client.get(userChannel);
        Object value = userChannel1.getValue();

        // 关闭客户端
        client.close();

        // 关闭连接
        connection.close();

    }

    private class UserChannel extends Easy2CacheChannel<Object> {

        private static final String PREFIX = "user";

    }

}