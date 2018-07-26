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

/**
 * @author BerryWang1996
 * @version V1.0.0
 */
public interface Easy2CacheCommand {

    void set(AbstractEasy2CacheChannel easy2CacheChannel);

    boolean setnx(AbstractEasy2CacheChannel easy2CacheChannel);

    void setxx(AbstractEasy2CacheChannel easy2CacheChannel);

    long del(AbstractEasy2CacheChannel easy2CacheChannel);

    <T, ST> T get(AbstractEasy2CacheChannel<T, ST> easy2CacheChannel);

}
