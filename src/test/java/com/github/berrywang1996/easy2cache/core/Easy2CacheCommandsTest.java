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

import com.github.berrywang1996.easy2cache.domain.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author BerryWang1996
 * @version V1.0.0
 */
public class Easy2CacheCommandsTest {

    private Easy2CacheConnection connection;

    private AbstractEasy2CacheClient client;

    @Before
    public void testCreateConnection() {

        // 创建配置文件用于创建连接
        Easy2CacheConfig config = new Easy2CacheConfig();
        config.setHost("192.168.1.233");
        config.setPort(6379);
        config.setDatabaseNum(4);

        // 创建连接对象
        connection = Easy2CacheConnectionFactory.createConnection(config);

        // 获取操作客户端
        client = connection.getClient();

        // 清空当前数据库所有数据
        client.getCommonCommands().flushdb();

    }

    @Test
    public void testSetExpire() throws Exception {
        User user = new User();
        user.setId(156L);
        user.setUsername("user_a");
        user.setPassword("uer_a_password");
        UserCacheKey userCacheKey = new UserCacheKey();
        userCacheKey.setRealKey("testSetExpire");

        userCacheKey.setExpireSecond(60L);
        client.set(userCacheKey, user);
        Assert.assertEquals(Long.valueOf("60"), client.getCommonCommands().ttl("testSetExpire").get());

        userCacheKey.setExpireSecond(null);
        client.set(userCacheKey, user);
        Assert.assertEquals(Long.valueOf("-1"), client.getCommonCommands().ttl("testSetExpire").get());
    }

}
