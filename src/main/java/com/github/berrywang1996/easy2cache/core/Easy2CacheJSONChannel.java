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

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;

/**
 * @author 王伯瑞
 * @version V1.0.0
 */
@Slf4j
public class Easy2CacheJSONChannel<T> extends AbstractEasy2CacheChannel<T> {

    @Override
    public String serialize() {
        if (log.isDebugEnabled()) {
            log.debug("execute serialize:{}", JSON.toJSONString(this.getValue()));
        }
        return JSON.toJSONString(this.getValue());
    }

    @Override
    public T unserialize(String data, Class<T> clz) {
        if (log.isDebugEnabled()) {
            log.debug("execute unserialize:{}", JSON.parseObject(data, clz));
        }
        return JSON.parseObject(data, clz);
    }

}
