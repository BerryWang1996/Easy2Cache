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

package com.github.berrywang1996.easy2cache;

import com.lambdaworks.redis.api.StatefulConnection;
import com.lambdaworks.redis.api.StatefulRedisConnection;
import com.lambdaworks.redis.cluster.api.StatefulRedisClusterConnection;

import java.rmi.activation.UnknownObjectException;

/**
 * @author 王伯瑞
 * @version V1.0.0
 */
public class Easy2CacheConnection {

    private StatefulConnection connection;

    public Easy2CacheConnection(StatefulConnection connection) {
        this.connection = connection;
    }

    public Easy2CacheClient getClient() {
        if (this.connection instanceof StatefulRedisClusterConnection) {
            // 集群模式
            return new Easy2CacheClusterClient(((StatefulRedisClusterConnection) this.connection).async());
        } else if (this.connection instanceof StatefulRedisConnection) {
            // 哨兵或单机模式
            return new Easy2CacheCommonClient(((StatefulRedisConnection) this.connection).async());
        } else {
            new UnknownObjectException("unknown connection, the connection must instance of " +
                    "\"StatefulRedisClusterConnection\" or \"StatefulRedisConnection\"").printStackTrace();
        }
        return null;
    }

    public void close() {
        if (this.connection != null) {
            connection.close();
        }
    }
}