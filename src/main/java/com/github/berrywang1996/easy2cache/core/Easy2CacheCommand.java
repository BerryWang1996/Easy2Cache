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

/**
 * @author BerryWang1996
 * @version V1.0.0
 */
public interface Easy2CacheCommand {

    <T, ST> void set(AbstractEasy2CacheKey<T, ST> key, T value);

    <T, ST> boolean setnx(AbstractEasy2CacheKey<T, ST> key, T value);

    <T, ST> void setxx(AbstractEasy2CacheKey<T, ST> key, T value);

    long del(AbstractEasy2CacheKey key);

    <T, ST> T get(AbstractEasy2CacheKey<T, ST> key);

}
