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

    private StatefulConnection<String, String> stringStringConnection;

    private StatefulConnection<String, byte[]> stringBytesConnection;

    Easy2CacheConnection(StatefulConnection<String, String> stringStringConnection,
                         StatefulConnection<String, byte[]> stringBytesConnection) {
        this.stringStringConnection = stringStringConnection;
        this.stringBytesConnection = stringBytesConnection;
    }

    public AbstractEasy2CacheClient getClient() {
        if (this.stringStringConnection instanceof StatefulRedisClusterConnection
                && this.stringBytesConnection instanceof StatefulRedisClusterConnection) {
            // 集群模式
            return new Easy2CacheClusterClient(
                    ((StatefulRedisClusterConnection<String, String>) this.stringStringConnection).async(),
                    ((StatefulRedisClusterConnection<String, byte[]>) this.stringBytesConnection).async()
            );
        } else if (this.stringStringConnection instanceof StatefulRedisConnection
                && stringBytesConnection instanceof StatefulRedisConnection) {
            // 哨兵或单机模式
            return new Easy2CacheCommonClient(
                    ((StatefulRedisConnection<String, String>) this.stringStringConnection).async(),
                    ((StatefulRedisConnection<String, byte[]>) this.stringBytesConnection).async()
            );
        } else {
            new UnknownObjectException("unknown stringStringConnection, the stringStringConnection must instance of " +
                    "\"StatefulRedisClusterConnection\" or \"StatefulRedisConnection\"").printStackTrace();
        }
        return null;
    }

    public void close() {
        if (this.stringStringConnection != null) {
            stringStringConnection.close();
        }
    }
}
