/*
 * Copyright 2018 BerryWang1996
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.berrywang1996.easy2cache.core;

import com.github.berrywang1996.easy2cache.User;
import com.github.berrywang1996.easy2cache.UserCacheChannel;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * @author BerryWang1996
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