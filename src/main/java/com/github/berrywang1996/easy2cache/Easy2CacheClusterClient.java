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

import com.lambdaworks.redis.RedisFuture;
import com.lambdaworks.redis.cluster.api.async.RedisAdvancedClusterAsyncCommands;

import java.util.concurrent.ExecutionException;

/**
 * @author 王伯瑞
 * @version V1.0.0
 */
public class Easy2CacheClusterClient extends Easy2CacheClient {

    public Easy2CacheClusterClient(RedisAdvancedClusterAsyncCommands async) {
        super(async);
    }

    @Override
    public void set(Easy2CacheChannel easy2CacheChannel) {
        super.getClusterAsyncCommands().set(easy2CacheChannel.getRealKey(), easy2CacheChannel.getValue());
    }

    @Override
    public <T> T get(T easy2CacheChannel) {
        return easy2CacheChannel;
    }

}
