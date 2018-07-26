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

import com.github.berrywang1996.easy2cache.channel.AbstractEasy2CacheChannel;
import com.lambdaworks.redis.cluster.api.async.RedisAdvancedClusterAsyncCommands;

import java.util.concurrent.ExecutionException;

/**
 * @author BerryWang1996
 * @version V1.0.0
 */
public class Easy2CacheClusterClient extends AbstractEasy2CacheClient {

    public Easy2CacheClusterClient(RedisAdvancedClusterAsyncCommands<String, String> stringStringCommands,
                                   RedisAdvancedClusterAsyncCommands<String, byte[]> stringBytesCommands) {
        super(stringStringCommands, stringBytesCommands);
    }

    @Override
    public void set(AbstractEasy2CacheChannel abstractEasy2CacheChannel) {
        getClusterCommands(abstractEasy2CacheChannel).set(
                abstractEasy2CacheChannel.getRealKey(),
                abstractEasy2CacheChannel.serialize()
        );
    }

    @Override
    public <T> T get(T easy2CacheChannel) {
        AbstractEasy2CacheChannel abstractEasy2CacheChannel = castEasy2CacheChannel(easy2CacheChannel);
        try {
            abstractEasy2CacheChannel.setValue(
                    abstractEasy2CacheChannel.unserialize(
                            getClusterCommands(abstractEasy2CacheChannel)
                                    .get(abstractEasy2CacheChannel.getRealKey()).get(),
                            abstractEasy2CacheChannel.getValue().getClass()
                    )
            );
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return easy2CacheChannel;
    }

}
