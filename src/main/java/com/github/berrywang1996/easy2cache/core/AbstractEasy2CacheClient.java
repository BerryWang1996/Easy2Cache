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

import com.github.berrywang1996.easy2cache.channel.AbstractEasy2CacheKey;
import com.github.berrywang1996.easy2cache.channel.Easy2CacheByteKey;
import com.github.berrywang1996.easy2cache.channel.Easy2CacheJsonKey;
import com.lambdaworks.redis.api.async.RedisAsyncCommands;
import com.lambdaworks.redis.cluster.api.async.RedisAdvancedClusterAsyncCommands;

import javax.naming.OperationNotSupportedException;

/**
 * @author BerryWang1996
 * @version V1.0.0
 */
public abstract class AbstractEasy2CacheClient implements Easy2CacheCommand {

    private RedisAsyncCommands<String, String> stringStringCommands;

    private RedisAsyncCommands<String, byte[]> stringBytesCommands;

    private RedisAdvancedClusterAsyncCommands<String, String> clusterStringStringCommands;

    private RedisAdvancedClusterAsyncCommands<String, byte[]> clusterStringBytesCommands;

    AbstractEasy2CacheClient(RedisAsyncCommands<String, String> stringStringCommands,
                             RedisAsyncCommands<String, byte[]> stringBytesCommands) {
        this.stringStringCommands = stringStringCommands;
        this.stringBytesCommands = stringBytesCommands;
    }

    AbstractEasy2CacheClient(RedisAdvancedClusterAsyncCommands<String, String> clusterStringStringCommands,
                             RedisAdvancedClusterAsyncCommands<String, byte[]> clusterStringBytesCommands) {
        this.clusterStringStringCommands = clusterStringStringCommands;
        this.clusterStringBytesCommands = clusterStringBytesCommands;
    }

    <T> AbstractEasy2CacheKey castEasy2CacheKey(T easy2CacheKey) {
        return (AbstractEasy2CacheKey) easy2CacheKey;
    }

    RedisAsyncCommands getCommonCommands(AbstractEasy2CacheKey abstractEasy2CacheKey) {
        if (abstractEasy2CacheKey instanceof Easy2CacheJsonKey) {
            return this.stringStringCommands;
        } else if (abstractEasy2CacheKey instanceof Easy2CacheByteKey) {
            return this.stringBytesCommands;
        } else {
            new OperationNotSupportedException().printStackTrace();
        }
        return null;
    }

    RedisAdvancedClusterAsyncCommands getClusterCommands(AbstractEasy2CacheKey abstractEasy2CacheKey) {
        if (abstractEasy2CacheKey instanceof Easy2CacheJsonKey) {
            return this.clusterStringStringCommands;
        } else if (abstractEasy2CacheKey instanceof Easy2CacheByteKey) {
            return this.clusterStringBytesCommands;
        } else {
            new OperationNotSupportedException().printStackTrace();
        }
        return null;
    }

    public void close() {
        if (this.stringStringCommands != null) {
            this.stringStringCommands.close();
        }
        if (this.stringBytesCommands != null) {
            this.stringBytesCommands.close();
        }
        if (this.clusterStringStringCommands != null) {
            this.clusterStringStringCommands.close();
        }
        if (this.clusterStringBytesCommands != null) {
            this.clusterStringBytesCommands.close();
        }
    }
}
