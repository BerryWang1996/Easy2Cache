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
    public <T, ST> void set(AbstractEasy2CacheKey<T, ST> key, T value) {
        getCommonCommands(key).set(
                key.getRealKey(),
                key.serialize(value),
                key.getExpireMilliseconds() != null || key.getExpireSecond() != null ?
                        key.getExpireMilliseconds() != null ?
                                new SetArgs().px(key.getExpireMilliseconds()) :
                                new SetArgs().ex(key.getExpireSecond()) :
                        new SetArgs()
        );
    }

    @Override
    public <T, ST> boolean setnx(AbstractEasy2CacheKey<T, ST> key, T value) {
        try {
            return (boolean) getCommonCommands(key).setnx(
                    key.getRealKey(),
                    key.serialize(value)
            ).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public <T, ST> void setxx(AbstractEasy2CacheKey<T, ST> key, T value) {
        try {
            getCommonCommands(key).set(
                    key.getRealKey(),
                    key.serialize(value),
                    new SetArgs().xx()
            ).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    @Override
    public long del(AbstractEasy2CacheKey key) {
        try {
            return (long) getCommonCommands(key).del(
                    key.getRealKey()
            ).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public <T, ST> T get(AbstractEasy2CacheKey<T, ST> key) {
        AbstractEasy2CacheKey abstractEasy2CacheKey = castEasy2CacheKey(key);
        try {
            return (T) abstractEasy2CacheKey.unserialize(
                    getCommonCommands(abstractEasy2CacheKey).get(abstractEasy2CacheKey.getRealKey()).get(),
                    key.getValueClass());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

}
