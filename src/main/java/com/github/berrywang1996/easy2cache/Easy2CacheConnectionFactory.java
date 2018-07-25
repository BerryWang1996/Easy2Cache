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

package com.github.berrywang1996.easy2cache;

import com.github.berrywang1996.easy2cache.consts.RedisMode;
import com.lambdaworks.redis.RedisClient;
import com.lambdaworks.redis.api.StatefulConnection;
import com.lambdaworks.redis.cluster.RedisClusterClient;
import lombok.extern.slf4j.Slf4j;

/**
 * @author 王伯瑞
 * @version V1.0.0
 */
@Slf4j
public class Easy2CacheConnectionFactory {

    private Easy2CacheConnectionFactory() {
    }

    public static Easy2CacheConnection createConnection(Easy2CacheConfig easy2CacheConfig) {
        // 创建真正的连接
        String url = easy2CacheConfig.getUrl();
        // 根据redis模式，创建对应的连接
        StatefulConnection<String, String> connection = null;
        if (RedisMode.CLUSTER == easy2CacheConfig.getRedisMode()) {
            // 集群模式
            RedisClusterClient redisClient = RedisClusterClient.create(url);
            connection = redisClient.connect();
        } else if (RedisMode.SENTINEL == easy2CacheConfig.getRedisMode()) {
            // 哨兵模式
            RedisClient redisClient = RedisClient.create(url);
            connection = redisClient.connect();
        } else {
            // 单机模式
            RedisClient redisClient = RedisClient.create(url);
            connection = redisClient.connect();
        }
        // 创建连接
        return new Easy2CacheConnection(connection);
    }

}
