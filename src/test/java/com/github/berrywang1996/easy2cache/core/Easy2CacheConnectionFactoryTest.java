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

    }

    @Test
    public void createJsonConnection() {

        // 创建保存对象
        User user = new User();
        user.setId(1L);
        user.setUsername("berry王");
        user.setPassword("123456");

        // 保存数据
        UserCacheChannel userChannel = new UserCacheChannel();
        userChannel.setRealKey("user");
        userChannel.setValue(user);
        client.set(userChannel);

        // 获取数据
        User _user = client.get(userChannel);
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
        UserCacheChannel userChannel2 = new UserCacheChannel();
        userChannel2.setRealKey("testNx");
        userChannel2.setValue(user);
        Assert.assertTrue(client.setnx(userChannel2));

        // 保存数据nx
        UserCacheChannel userChannel3 = new UserCacheChannel();
        userChannel3.setRealKey("testNx");
        userChannel3.setValue(user);
        Assert.assertFalse(client.setnx(userChannel3));

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
        UserCacheChannel userChannel1 = new UserCacheChannel();
        userChannel1.setRealKey("testXx");
        userChannel1.setValue(user);
        client.set(userChannel1);

        user.setPassword("new password");

        // 保存数据xx
        UserCacheChannel userChannel2 = new UserCacheChannel();
        userChannel2.setRealKey("testXx");
        userChannel2.setValue(user);
        client.setxx(userChannel2);
        Assert.assertNotEquals(client.get(userChannel2), user);

    }

    @Test
    public void testDel() {

        // 创建保存对象
        User user = new User();
        user.setId(1L);
        user.setUsername("berry王");
        user.setPassword("123456");

        // 保存数据
        UserCacheChannel userChannel1 = new UserCacheChannel();
        userChannel1.setRealKey("testDel");
        userChannel1.setValue(user);
        client.set(userChannel1);

        // 删除数据
        UserCacheChannel userChannel2 = new UserCacheChannel();
        userChannel2.setRealKey("testDel");
        userChannel2.setValue(user);
        Assert.assertEquals(client.del(userChannel2), 1);
        Assert.assertEquals(client.del(userChannel2), 0);

    }

    @Test
    public void createFstConnection() {

        // 创建保存对象
        Department department = new Department();
        department.setId(10L);
        department.setName("测试部门 department");

        // 保存数据
        DepartmentCacheChannel departmentChannel = new DepartmentCacheChannel();
        departmentChannel.setRealKey("department");
        departmentChannel.setValue(department);
        client.set(departmentChannel);

        // 获取数据
        Department _department = client.get(departmentChannel);
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