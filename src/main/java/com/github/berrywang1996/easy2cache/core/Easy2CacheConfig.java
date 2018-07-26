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

import com.github.berrywang1996.easy2cache.consts.RedisMode;
import com.github.berrywang1996.easy2cache.util.StringUtil;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author BerryWang1996
 * @version V1.0.0
 */
@Getter
@Setter
public class Easy2CacheConfig {

    @Accessors(chain = true)
    private RedisMode redisMode = RedisMode.STANDALONE;

    @Accessors(chain = true)
    private boolean enableSSL = false;

    @Accessors(chain = true)
    private String host = "localhost";

    @Accessors(chain = true)
    private Integer port = 6379;

    @Accessors(chain = true)
    private String password;

    @Accessors(chain = true)
    private Integer databaseNum = 0;

    @Accessors(chain = true)
    private String sentinelMasterId;

    @Accessors(chain = true)
    private List<SentinelNode> sentinelNodes;

    /**
     * timeout seconds
     */
    @Accessors(chain = true)
    private Integer timeout = 60;

    String getUrl() {
        // 检查配置是否正确
        this.checkConfig();
        return generateUrl();
    }

    private void checkConfig() {
        try {
            if (this.redisMode == null) {
                throw new IllegalArgumentException("redis model need to set!");
            }
            if (RedisMode.SENTINEL != this.redisMode) {
                if (StringUtil.isBlank(host)) {
                    throw new IllegalArgumentException("redis host could not be blank!");
                }
            } else {
                if (this.sentinelNodes == null) {
                    throw new IllegalArgumentException("redis mode is sentinel, please set sentinelNodes");
                }
                if (this.sentinelMasterId == null) {
                    throw new IllegalArgumentException("redis mode is sentinel, please set sentinelMasterId");
                }
                for (SentinelNode sentinelNode : this.getSentinelNodes()) {
                    if (StringUtil.isBlank(sentinelNode.getHost())) {
                        throw new IllegalArgumentException("redis mode is sentinel, some node's host is blank!");
                    }
                }
            }
            if (this.timeout != null && timeout < 0) {
                throw new IllegalArgumentException("redis timeout seconds must greater than 0!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    private String generateUrl() {
        // 生成url
        StringBuilder url = new StringBuilder(50);
        if (RedisMode.SENTINEL == this.getRedisMode()) {
            url.append("redis-sentinel");
        } else {
            if (this.isEnableSSL()) {
                url.append("rediss");
            } else {
                url.append("redis");
            }
        }
        url.append("://");
        if (RedisMode.SENTINEL == this.getRedisMode()) {
            for (Easy2CacheConfig.SentinelNode sentinelNode : this.getSentinelNodes()) {
                if (StringUtil.isNotBlank(sentinelNode.getPassword())) {
                    url.append(sentinelNode.getPassword());
                    url.append("@");
                }
                url.append(sentinelNode.getHost());
                if (sentinelNode.getPort() != null) {
                    url.append(":");
                    url.append(sentinelNode.getPort());
                }
                if (sentinelNode.getDatabaseNum() != null) {
                    url.append("/");
                    url.append(sentinelNode.getDatabaseNum());
                }
                url.append(",");
            }
            url.deleteCharAt(url.length() - 1);
        } else {
            if (StringUtil.isNotBlank(this.getPassword())) {
                url.append(this.getPassword());
                url.append("@");
            }
            url.append(this.getHost());
            if (this.getPort() != null) {
                url.append(":");
                url.append(this.getPort());
            }
            if (RedisMode.CLUSTER != this.getRedisMode() && this.getDatabaseNum() != null) {
                url.append("/");
                url.append(this.getDatabaseNum());
            }
        }
        return url.toString();
    }

    @Getter
    @Setter
    public static class SentinelNode {

        @Accessors(chain = true)
        private String host = "localhost";

        @Accessors(chain = true)
        private Integer port = 6379;

        @Accessors(chain = true)
        private String password;

        @Accessors(chain = true)
        private Integer databaseNum = 0;

    }

}
