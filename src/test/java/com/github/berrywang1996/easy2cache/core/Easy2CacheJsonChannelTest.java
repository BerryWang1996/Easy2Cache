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
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author BerryWang1996
 * @version V1.0.0
 */
public class Easy2CacheJsonChannelTest {

    private Easy2CacheJsonChannel<User> jsonChannel = new Easy2CacheJsonChannel<>();

    private Easy2CacheByteChannel<Department> byteChannel = new Easy2CacheByteChannel<>();

    private User user = new User();

    private Department department = new Department();

    @Before
    public void testBefore() {
        user.setId(10L);
        user.setUsername("berry");
        user.setPassword("123");
        jsonChannel.setValue(user);

        department.setId(20L);
        department.setName("123");
        byteChannel.setValue(department);
    }

    @Test
    public void testFastjsonSerialize() {
        String json_result = "{\"id\":10,\"password\":\"123\",\"username\":\"berry\"}";
        Assert.assertEquals(json_result, jsonChannel.serialize());
        Assert.assertEquals(user, jsonChannel.unserialize(json_result, User.class));
    }

    @Test
    public void testFstSerialize() {
        String byte_result = byteChannel.serialize();
        Assert.assertEquals(byte_result, byteChannel.serialize());
        Assert.assertEquals(department, byteChannel.unserialize(byte_result, Department.class));
    }

}