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

import javax.naming.OperationNotSupportedException;

/**
 * @author BerryWang1996
 * @version V1.0.0
 */
public abstract class AbstractEasy2CacheClient {

    private RedisAsyncCommands stringStringCommands;

    private RedisAsyncCommands stringBytesCommands;

    private RedisAdvancedClusterAsyncCommands clusterStringStringCommands;

    private RedisAdvancedClusterAsyncCommands clusterStringBytesCommands;

    AbstractEasy2CacheClient(RedisAsyncCommands stringStringCommands,
                             RedisAsyncCommands stringBytesCommands) {
        this.stringStringCommands = stringStringCommands;
        this.stringBytesCommands = stringBytesCommands;
    }

    AbstractEasy2CacheClient(RedisAdvancedClusterAsyncCommands clusterStringStringCommands,
                             RedisAdvancedClusterAsyncCommands clusterStringBytesCommands) {
        this.clusterStringStringCommands = clusterStringStringCommands;
        this.clusterStringBytesCommands = clusterStringBytesCommands;
    }

    public abstract void set(AbstractEasy2CacheChannel abstractEasy2CacheChannel);

    public abstract <T> T get(T easy2CacheChannel);

    <T> AbstractEasy2CacheChannel castEasy2CacheChannel(T easy2CacheChannel) {
        return (AbstractEasy2CacheChannel) easy2CacheChannel;
    }

    RedisAsyncCommands getCommonCommands(AbstractEasy2CacheChannel abstractEasy2CacheChannel) {
        if (abstractEasy2CacheChannel instanceof Easy2CacheJsonChannel) {
            return this.stringStringCommands;
        } else if (abstractEasy2CacheChannel instanceof Easy2CacheByteChannel) {
            return this.stringBytesCommands;
        } else {
            new OperationNotSupportedException().printStackTrace();
        }
        return null;
    }

    RedisAdvancedClusterAsyncCommands getClusterCommands(AbstractEasy2CacheChannel abstractEasy2CacheChannel) {
        if (abstractEasy2CacheChannel instanceof Easy2CacheJsonChannel) {
            return this.clusterStringStringCommands;
        } else if (abstractEasy2CacheChannel instanceof Easy2CacheByteChannel) {
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
