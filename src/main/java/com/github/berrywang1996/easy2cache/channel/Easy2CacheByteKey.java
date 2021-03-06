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

import lombok.extern.slf4j.Slf4j;
import org.nustaq.serialization.FSTConfiguration;

/**
 * @author BerryWang1996
 * @version V1.0.0
 */
@Slf4j
public class Easy2CacheByteKey<T> extends AbstractEasy2CacheKey<T, byte[]> {

    private FSTConfiguration fstConfiguration = FSTConfiguration.createDefaultConfiguration();

    @Override
    public byte[] serialize(T data) {
        if (log.isDebugEnabled()) {
            log.debug("execute fst serialize:{}", fstConfiguration.asByteArray(data));
        }
        return fstConfiguration.asByteArray(data);
    }

    @Override
    public T unserialize(byte[] data, Class<T> clz) {
        if (log.isDebugEnabled()) {
            log.debug("execute fst unserialize:{}", fstConfiguration.asObject(data));
        }
        return (T) fstConfiguration.asObject(data);
    }

}
