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

import com.lambdaworks.redis.api.async.RedisAsyncCommands;
import com.lambdaworks.redis.cluster.api.async.RedisAdvancedClusterAsyncCommands;
import lombok.Getter;

/**
 * @author BerryWang1996
 * @version V1.0.0
 */
@Getter
public abstract class AbstractEasy2CacheClient {

    private RedisAsyncCommands commonCommands;

    private RedisAdvancedClusterAsyncCommands clusterAsyncCommands;

    public AbstractEasy2CacheClient(RedisAsyncCommands commonCommands) {
        this.commonCommands = commonCommands;
    }

    public AbstractEasy2CacheClient(RedisAdvancedClusterAsyncCommands clusterAsyncCommands) {
        this.clusterAsyncCommands = clusterAsyncCommands;
    }

    public abstract void set(AbstractEasy2CacheChannel abstractEasy2CacheChannel);

    public abstract <T> T get(T easy2CacheChannel);

    public <T> AbstractEasy2CacheChannel castEasy2CacheChannel(T easy2CacheChannel) {
        return (AbstractEasy2CacheChannel) easy2CacheChannel;
    }

    public void close() {
        if (this.commonCommands != null) {
            this.commonCommands.close();
        }
        if (this.clusterAsyncCommands != null) {
            this.clusterAsyncCommands.close();
        }
    }
}
