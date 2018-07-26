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
import com.lambdaworks.redis.SetArgs;
import com.lambdaworks.redis.api.async.RedisAsyncCommands;

import java.util.concurrent.ExecutionException;

/**
 * @author BerryWang1996
 * @version V1.0.0
 */
public class Easy2CacheCommonClient extends AbstractEasy2CacheClient {

    Easy2CacheCommonClient(RedisAsyncCommands<String, String> stringStringCommands,
                           RedisAsyncCommands<String, byte[]> stringBytesCommands) {
        super(stringStringCommands, stringBytesCommands);
    }

    @Override
    public void set(AbstractEasy2CacheChannel easy2CacheChannel) {
        getCommonCommands(easy2CacheChannel).set(
                easy2CacheChannel.getRealKey(),
                easy2CacheChannel.serialize()
        );
    }

    @Override
    public boolean setnx(AbstractEasy2CacheChannel easy2CacheChannel) {
        try {
            return (boolean) getCommonCommands(easy2CacheChannel).setnx(
                    easy2CacheChannel.getRealKey(),
                    easy2CacheChannel.serialize()
            ).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void setxx(AbstractEasy2CacheChannel easy2CacheChannel) {
        try {
            getCommonCommands(easy2CacheChannel).set(
                    easy2CacheChannel.getRealKey(),
                    easy2CacheChannel.serialize(),
                    new SetArgs().xx()
            ).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    @Override
    public long del(AbstractEasy2CacheChannel easy2CacheChannel) {
        try {
            return (long) getCommonCommands(easy2CacheChannel).del(
                    easy2CacheChannel.getRealKey(),
                    easy2CacheChannel.serialize()
            ).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public <T, ST> T get(AbstractEasy2CacheChannel<T, ST> easy2CacheChannel) {
        AbstractEasy2CacheChannel abstractEasy2CacheChannel = castEasy2CacheChannel(easy2CacheChannel);
        try {
            return (T) abstractEasy2CacheChannel.unserialize(
                    getCommonCommands(abstractEasy2CacheChannel)
                            .get(abstractEasy2CacheChannel.getRealKey()).get(),
                    abstractEasy2CacheChannel.getValue().getClass()
            );
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

}
