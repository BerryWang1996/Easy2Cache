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

/**
 * @author BerryWang1996
 * @version V1.0.0
 */
public abstract class AbstractEasy2CacheChannel<T, ST> implements Easy2CacheSerializable<T, ST> {

    /**
     * 缓存中的key
     */
    private String realKey;

    /**
     * 缓存的value
     */
    private T value;

    public String getRealKey() {
        return realKey;
    }

    public void setRealKey(String realKey) {
        this.realKey = realKey;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

}
