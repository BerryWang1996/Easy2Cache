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

import com.github.berrywang1996.easy2cache.consts.RedisMode;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

/**
 * @author BerryWang1996
 * @version V1.0.0
 */
public class Easy2CacheConfigTest {

    @Test
    public void getUrl() {

        Easy2CacheConfig config = new Easy2CacheConfig();
        Assert.assertEquals("redis://localhost:6379/0", config.getUrl());

        config.setEnableSSL(true);
        Assert.assertEquals("rediss://localhost:6379/0", config.getUrl());

        config.setRedisMode(RedisMode.CLUSTER);
        Assert.assertEquals("rediss://localhost:6379", config.getUrl());

        config.setRedisMode(RedisMode.SENTINEL);
        Easy2CacheConfig.SentinelNode sentinelNode1 = new Easy2CacheConfig.SentinelNode();
        sentinelNode1.setPort(6379);
        Easy2CacheConfig.SentinelNode sentinelNode2 = new Easy2CacheConfig.SentinelNode();
        sentinelNode2.setPort(6380);
        config.setSentinelNodes(Arrays.asList(sentinelNode1, sentinelNode2));
        config.setSentinelMasterId("master");
        Assert.assertEquals("redis-sentinel://localhost:6379/0,localhost:6380/0", config.getUrl());

    }

}