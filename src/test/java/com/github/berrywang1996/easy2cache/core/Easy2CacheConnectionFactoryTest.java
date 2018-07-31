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

import com.github.berrywang1996.easy2cache.channel.Easy2CacheJsonKey;
import com.github.berrywang1996.easy2cache.domain.Department;
import com.github.berrywang1996.easy2cache.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author BerryWang1996
 * @version V1.0.0
 */
@Slf4j
public class Easy2CacheConnectionFactoryTest {

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
    public void createJsonConnection() {

        // 创建保存对象
        User user = new User();
        user.setId(1L);
        user.setUsername("berry王");
        user.setPassword("123456");

        // 保存数据
        UserCacheKey userKey = new UserCacheKey();
        userKey.setRealKey("user");
        client.set(userKey, user);

        // 获取数据
        User _user = client.get(userKey);
        log.info("get value from redis : {}", _user);

    }

    @Test
    public void testNx() {

        // 创建保存对象
        User user = new User();
        user.setId(1L);
        user.setUsername("berry王");
        user.setPassword("123456");

        // 保存数据nx
        UserCacheKey userKey2 = new UserCacheKey();
        userKey2.setRealKey("testNx");
        Assert.assertTrue(client.setnx(userKey2, user));

        // 保存数据nx
        UserCacheKey userKey3 = new UserCacheKey();
        userKey3.setRealKey("testNx");
        Assert.assertFalse(client.setnx(userKey3, user));

    }

    // @Test
    // TODO 验证未通过
    public void testXx() {

        // 创建保存对象
        User user = new User();
        user.setId(1L);
        user.setUsername("berry王");
        user.setPassword("123456");

        // 保存数据xx
        UserCacheKey userKey1 = new UserCacheKey();
        userKey1.setRealKey("testXx");
        client.set(userKey1, user);

        user.setPassword("new password");

        // 保存数据xx
        UserCacheKey userKey2 = new UserCacheKey();
        userKey2.setRealKey("testXx");
        client.setxx(userKey2, user);
        Assert.assertNotEquals(client.get(userKey2), user);

    }

    @Test
    public void testDel() {

        // 创建保存对象
        User user = new User();
        user.setId(1L);
        user.setUsername("berry王");
        user.setPassword("123456");

        // 保存数据
        UserCacheKey userKey1 = new UserCacheKey();
        userKey1.setRealKey("testDel");
        client.set(userKey1, user);

        // 删除数据
        UserCacheKey userKey2 = new UserCacheKey();
        userKey2.setRealKey("testDel");
        Assert.assertEquals(client.del(userKey2), 1);
        Assert.assertEquals(client.del(userKey2), 0);

    }

    @Test
    public void createFstConnection() {

        // 创建保存对象
        Department department = new Department();
        department.setId(10L);
        department.setName("测试部门 department");

        // 保存数据
        DepartmentCacheKey departmentKey = new DepartmentCacheKey();
        departmentKey.setRealKey("department");
        client.set(departmentKey, department);

        // 获取数据
        Department _department = client.get(departmentKey);
        log.info("get value from redis : {}", _department);

    }

    @After
    public void testCloseConnection() {

        // 关闭客户端
        client.close();

        // 关闭连接
        connection.close();

    }

}