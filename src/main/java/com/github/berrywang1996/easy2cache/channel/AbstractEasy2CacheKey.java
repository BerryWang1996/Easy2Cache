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

package com.github.berrywang1996.easy2cache.channel;

import com.github.berrywang1996.easy2cache.serialize.Easy2CacheSerializable;

import java.lang.reflect.ParameterizedType;

/**
 * @author BerryWang1996
 * @version V1.0.0
 */
public abstract class AbstractEasy2CacheKey<T, ST> implements Easy2CacheSerializable<T, ST> {
    /**
     * 缓存中真实的key
     */
    private String realKey;

    /**
     * 缓存的过期时间（秒）
     */
    private long expireSecond;

    /**
     * 缓存的过期时间（毫秒）
     */
    private long expireMilliseconds;

    public String getRealKey() {
        return realKey;
    }

    public void setRealKey(String realKey) {
        this.realKey = realKey;
    }

    public long getExpireSecond() {
        return expireSecond;
    }

    public void setExpireSecond(long expireSecond) {
        this.expireSecond = expireSecond;
    }

    public long getExpireMilliseconds() {
        return expireMilliseconds;
    }

    public void setExpireMilliseconds(long expireMilliseconds) {
        this.expireMilliseconds = expireMilliseconds;
    }

    public Class<T> getValueClass() {
        return (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

}
