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

import com.lambdaworks.redis.api.StatefulConnection;
import com.lambdaworks.redis.api.StatefulRedisConnection;
import com.lambdaworks.redis.cluster.api.StatefulRedisClusterConnection;

import java.rmi.activation.UnknownObjectException;

/**
 * @author BerryWang1996
 * @version V1.0.0
 */
public class Easy2CacheConnection {

    private StatefulConnection<String, String> stringStringconnection;

    private StatefulConnection<String, byte[]> stringBytesconnection;

    Easy2CacheConnection(StatefulConnection<String, String> stringStringconnection,
                         StatefulConnection<String, byte[]> stringBytesconnection) {
        this.stringStringconnection = stringStringconnection;
        this.stringBytesconnection = stringBytesconnection;
    }

    public AbstractEasy2CacheClient getClient() {
        if (this.stringStringconnection instanceof StatefulRedisClusterConnection
                && this.stringBytesconnection instanceof StatefulRedisClusterConnection) {
            // 集群模式
            return new Easy2CacheClusterClient(
                    ((StatefulRedisClusterConnection<String, String>) this.stringStringconnection).async(),
                    ((StatefulRedisClusterConnection<String, byte[]>) this.stringBytesconnection).async()
            );
        } else if (this.stringStringconnection instanceof StatefulRedisConnection
                && stringBytesconnection instanceof StatefulRedisConnection) {
            // 哨兵或单机模式
            return new Easy2CacheCommonClient(
                    ((StatefulRedisConnection<String, String>) this.stringStringconnection).async(),
                    ((StatefulRedisConnection<String, byte[]>) this.stringBytesconnection).async()
            );
        } else {
            new UnknownObjectException("unknown stringStringconnection, the stringStringconnection must instance of " +
                    "\"StatefulRedisClusterConnection\" or \"StatefulRedisConnection\"").printStackTrace();
        }
        return null;
    }

    public void close() {
        if (this.stringStringconnection != null) {
            stringStringconnection.close();
        }
    }
}
