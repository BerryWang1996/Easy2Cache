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

import com.lambdaworks.redis.codec.ByteArrayCodec;
import com.lambdaworks.redis.codec.RedisCodec;
import com.lambdaworks.redis.codec.StringCodec;
import com.lambdaworks.redis.protocol.LettuceCharsets;
import lombok.extern.slf4j.Slf4j;

import java.nio.ByteBuffer;

/**
 * @author BerryWang1996
 * @version V1.0.0
 */
public class Easy2CacheByteArrayCodec implements RedisCodec<String, byte[]> {

    @Override
    public String decodeKey(ByteBuffer bytes) {
        return StringCodec.UTF8.decodeKey(bytes);
    }

    @Override
    public byte[] decodeValue(ByteBuffer bytes) {
        return ByteArrayCodec.INSTANCE.decodeValue(bytes);
    }

    @Override
    public ByteBuffer encodeKey(String key) {
        return StringCodec.UTF8.encodeKey(key);
    }

    @Override
    public ByteBuffer encodeValue(byte[] value) {
        return ByteArrayCodec.INSTANCE.encodeValue(value);
    }

}
