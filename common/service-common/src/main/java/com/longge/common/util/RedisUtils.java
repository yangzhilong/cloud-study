package com.longge.common.util;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.DataType;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;

public class RedisUtils {
    private static StringRedisTemplate redisTemplate;
    
    public static StringRedisTemplate getRedisTemplate() {
        return redisTemplate;
    }

    public static void setRedisTemplate(StringRedisTemplate rt) {
        redisTemplate = rt;
    }

    private RedisUtils() {}

    /**
     * ###################################################################### common command  #################################################################################
     */
    /**
     * 是否存在key
     * @param key
     * @return
     */
    public static Boolean hasKey(String key) {
        return redisTemplate.hasKey(key);
    }
    
    /**
     * 设置过期时间
     * @param key
     * @param timeout
     * @return
     */
    public static Boolean expire(String key, long timeout) {
        return expire(key, timeout, TimeUnit.SECONDS);
    }
    
    /**
     * 设置过期时间
     * @param key
     * @param timeout
     * @param unit
     * @return
     */
    public static Boolean expire(String key, long timeout, TimeUnit unit) {
        return redisTemplate.expire(key, timeout, unit);
    }
    
    /**
     * 设置过期的截止日期
     * @param key
     * @param date
     * @return
     */
    public static Boolean expireAt(String key, Date date) {
        return redisTemplate.expireAt(key, date);
    }
    
    /**
     * 获取剩余过期时间
     * @param key
     * @return
     */
    public static Long getExpire(String key) {
        return redisTemplate.getExpire(key);
    }
    
    /**
     * 获取剩余过期时间
     * @param key
     * @param timeUnit
     * @return
     */
    public static Long getExpire(String key, TimeUnit timeUnit) {
        return redisTemplate.getExpire(key, timeUnit);
    }
    
    /**
     * 删除key
     * @param key
     * @return
     */
    public static Boolean delete(String key) {
        return redisTemplate.delete(key);
    }
    
    /**
     * 批量删除key
     * @param keys
     * @return
     */
    public static Long deleteBatch(Collection<String> keys) {
        return redisTemplate.delete(keys);
    }
    
    /**
     * 重命名key的名字到newKey，如果已经存在则覆盖
     * @param oldKey
     * @param newKey
     */
    public static void rename(String oldKey,String newKey) {
        redisTemplate.rename(oldKey, newKey);
    }
    
    /**
     * 如果newKey不存在则重命名
     * @param oldKey
     * @param newKey
     * @return
     */
    public static Boolean renameIfAbsent(String oldKey,String newKey) {
        return redisTemplate.renameIfAbsent(oldKey, newKey);
    }
    
    /**
     * 查询key的redis类型
     * @param key
     * @return
     */
    public static DataType type(String key) {
        return redisTemplate.type(key);
    }
    
    /**
     * 生成一个随机key
     * @return
     */
    public static String randomKey() {
        return redisTemplate.randomKey();
    }
    /**
     * ###################################################################### string  #################################################################################
     */
    
    public static class StringOps {
        /**
         * 获取指定 key 的值
         * @param key
         * @return
         */
        public static String get(String key) {
            return redisTemplate.opsForValue().get(key);
        }

        /**
         * 设置指定 key 的值value
         * @param key
         * @param value
         */
        public static void set(String key, String value) {
            redisTemplate.opsForValue().set(key, value);
        }

        /**
         * 为给定 key设置值并设置过期时间expiredTime
         * @param key
         * @param value
         * @param expiredTime
         * @param expiredTimeUnit
         */
        public static void setEx(String key, String value, long expiredTime, TimeUnit expiredTimeUnit) {
            redisTemplate.opsForValue().set(key, value, expiredTime, expiredTimeUnit);
        }

        /**
         * 将给定 key 的值设为 value ，并返回 key 的旧值(old value)
         * @param key
         * @param value
         * @return
         */
        public static String getAndSet(String key, String value) {
            return redisTemplate.opsForValue().getAndSet(key, value);
        }

        /**
         * 将 key 中储存的数字值增1,返回新值
         * @param key
         * @return
         */
        public static Long increment(String key) {
            return incrementByCount(key, 1);
        }

        /**
         * 将 key 中储存的数字值增count,返回新值
         * @param key
         * @param count
         * @return
         */
        public static Long incrementByCount(String key, long count) {
            return redisTemplate.opsForValue().increment(key, count);
        }
        
        /**
         * 将 key 中储存的数字值减1,返回新值
         * @param key
         * @return
         */
        public static Long decrement(String key) {
            return decrementByCount(key, 1);
        }

        /**
         * 将 key 中储存的数字值减count,返回新值
         * @param key
         * @param count
         * @return
         */
        public static Long decrementByCount(String key, long count) {
            return redisTemplate.opsForValue().decrement(key, count);
        }
        
        /**
         * 批量获取元素
         * @param keys
         * @return
         */
        public static List<String> mulitiGet(Collection<String> keys) {
            return redisTemplate.opsForValue().multiGet(keys);
        }
        
        /**
         * 批量设置值
         * @param map
         */
        public static void mulitiSet(Map<String, String> map) {
            redisTemplate.opsForValue().multiSet(map);
        }
    }

    /**
     * ###################################################################### list  #################################################################################
     */
    
    public static class ListOps {
        /**
         * 获取指定index的值
         * @param key
         * @param index
         * @return
         */
        public static String index(String key, long index) {
            return redisTemplate.opsForList().index(key, index);
        }
        
        /**
         * 对index索引的值进行修改
         * @param key
         * @param index
         * @param value
         */
        public static void set(String key, long index, String value) {
            redisTemplate.opsForList().set(key, index, value);
        }
        
        /**
         * 从左边(列表头部)push数据
         * @param key
         * @param value
         */
        public static Long leftPush(String key, String value) {
            return redisTemplate.opsForList().leftPush(key, value);
        }
        
        /**
         * 从左边（头部）push数据并设置过期时间
         * @param key
         * @param value
         * @param expiredTimeSec
         */
        public static void leftPush(String key, String value, long expiredTimeSec) {
            redisTemplate.executePipelined(new RedisCallback<Long>() {
                @Override
                public Long doInRedis(RedisConnection connection) throws DataAccessException {
                    connection.multi();
                    connection.lPush(key.getBytes(), value.getBytes());
                    connection.expire(key.getBytes(), expiredTimeSec);
                    connection.exec();
                    return null;
                }});
        }

        /**
         * 从左边(列表头部)批量push数据
         * @param key
         * @param values
         */
        public static Long leftPushAll(String key, String... values) {
            return redisTemplate.opsForList().leftPushAll(key, values);
        }

        /**
         * 从左边(列表头部)批量push数据
         * @param key
         * @param list
         */
        public static Long leftPushAll(String key, List<String> list) {
            return redisTemplate.opsForList().leftPushAll(key, list);
        }
        
        /**
         * 当key的list存在时才从左边（头部）push数据
         * @param key
         * @param value
         * @return
         */
        public static Long leftPushIfPresent(String key, String value) {
            return redisTemplate.opsForList().leftPushIfPresent(key, value);
        }

        /**
         * 从右边（列表尾部）push数据
         * @param key
         * @param value
         */
        public static Long rightPush(String key, String value) {
            return redisTemplate.opsForList().rightPush(key, value);
        }
        
        /**
         * 从右边（尾部）push数据并设置过期时间
         * @param key
         * @param value
         * @param expiredTimeSec
         */
        public static void rightPush(String key, String value, long expiredTimeSec) {
            redisTemplate.executePipelined(new RedisCallback<Long>() {
                @Override
                public Long doInRedis(RedisConnection connection) throws DataAccessException {
                    connection.multi();
                    connection.rPush(key.getBytes(), value.getBytes());
                    connection.expire(key.getBytes(), expiredTimeSec);
                    connection.exec();
                    return null;
                }});
        }

        /**
         * 从右边（列表尾部）批量push数据
         * @param key
         * @param values
         */
        public static Long rightPushAll(String key, String... values) {
            return redisTemplate.opsForList().rightPushAll(key, values);
        }

        /**
         * 从右边（列表尾部）批量push数据
         * @param key
         * @param list
         */
        public static Long rightPushAll(String key, List<String> list) {
            return redisTemplate.opsForList().rightPushAll(key, list);
        }

        /**
         * 只有key存在的时候才从右边（尾部）push数据
         * @param key
         * @param value
         * @return
         */
        public static Long rightPushIfPresent(String key, String value) {
            return redisTemplate.opsForList().rightPushIfPresent(key, value);
        }
        
        /**
         * 从左边(列表头部)移除一个数据
         * @param key
         * @return
         */
        public static String leftPop(String key) {
            return redisTemplate.opsForList().leftPop(key);
        }

        /**
         * 从左边(列表头部)移除一个元素，如果没有值则阻塞expiredTime时间
         * @param key
         * @param timeoutTimeUnit
         * @param timeout
         * @return
         */
        public static String leftPop(String key, TimeUnit timeoutTimeUnit, long timeout) {
            return redisTemplate.opsForList().leftPop(key, timeout, timeoutTimeUnit);
        }
        
        /**
         * 把sourceKey队尾（右边）的元素弹出并压入destinationKey的头部（左边）
         * @param sourceKey
         * @param destinationKey
         */
        public static String leftPopAndRightPush(String sourceKey, String destinationKey) {
            List<Object> list = redisTemplate.executePipelined(new RedisCallback<String>() {
                @Override
                public String doInRedis(RedisConnection connection) throws DataAccessException {
                    connection.multi();
                    byte[] value = connection.lPop(sourceKey.getBytes());
                    if(null == value || value.length==0) {
                        connection.exec();
                        return null;
                    }
                    connection.rPush(destinationKey.getBytes(), value);
                    connection.exec();
                    return new String(value);
                }});
            Object rt = list.get(0);
            return null==rt? null : (String)rt;
        }

        /**
         * 从右边（列表尾部）移除一个数据
         * @param key
         * @return
         */
        public static String rightPop(String key) {
            return rightPop(key, null, -1);
        }

        /**
         * 从右边（列表尾部）移除一个元素，如果没有值则阻塞expiredTime时间
         * @param key
         * @param timeoutTimeUnit
         * @param timeout
         * @return
         */
        public static String rightPop(String key, TimeUnit timeoutTimeUnit, long timeout) {
            return redisTemplate.opsForList().rightPop(key, timeout, timeoutTimeUnit);
        }
        
        /**
         * 把sourceKey队尾（右边）的元素弹出并压入destinationKey的头部（左边）
         * @param sourceKey
         * @param destinationKey
         */
        public static String rightPopAndLeftPush(String sourceKey, String destinationKey) {
            return redisTemplate.opsForList().rightPopAndLeftPush(sourceKey, destinationKey);
        }
        
        /**
         * 把sourceKey队尾（右边）的元素弹出并压入destinationKey的头部（左边）, 如果没有元素可以阻塞timeout
         * @param sourceKey
         * @param destinationKey
         */
        public static String rightPopAndLeftPush(String sourceKey, String destinationKey, long timeout, TimeUnit unit) {
            return redisTemplate.opsForList().rightPopAndLeftPush(sourceKey, destinationKey, timeout, unit);
        }

        /**
         * 获取列表长度
         * @param key
         * @return
         */
        public static long size(String key) {
            return redisTemplate.opsForList().size(key);
        }

        /**
         * 获取列表list的所有元素
         * @param key
         * @return
         */
        public static List<String> all(String key) {
            return redisTemplate.opsForList().range(key, 0, -1);
        }

        /**
         * 获取指定范围的list的数据
         * @param key
         * @param start
         * @param end
         * @return
         */
        public static List<String> range(String key, long start, long end) {
            return redisTemplate.opsForList().range(key, start, end);
        }

        /**
         * 截取留下指定下标内的数据
         * @param key
         * @param start  从0开始，包含此下标
         * @param end  从0开始，包含此下标， -1表示到列尾
         */
        public static void trim(String key, long start, long end) {
            redisTemplate.opsForList().trim(key, start, end);
        }
    }

    /**
     * ###################################################################### hash  #################################################################################
     */
    
    public static class HashOps {
        /**
         * 获取存储在哈希表key中指定字段hashKey的值
         * @param key
         * @param hashKey
         * @return
         */
        public static Object get(String key, String hashKey) {
            return redisTemplate.opsForHash().get(key, hashKey);
        }

        /**
         * 获取哈希表key的所有字段和值
         * @param key
         * @return
         */
        public static Map<Object, Object> getAll(String key) {
            return redisTemplate.opsForHash().entries(key);
        }

        /**
         * 批量获取哈希表key指定的字段的值
         * @param key
         * @param hashKeys
         * @return
         */
        public static List<Object> multiGet(String key, Set<Object> hashKeys) {
            return redisTemplate.opsForHash().multiGet(key, hashKeys);
        }

        /**
         * 将哈希表 key 中的字段 hashKey 的值设为 value
         * @param key
         * @param hashKey
         * @param value
         */
        public static void put(String key, String hashKey, Object value) {
            redisTemplate.opsForHash().put(key, hashKey, value);
        }
        
        /**
         * 设值并设置过期时间
         * @param key
         * @param hashKey
         * @param value
         * @param expiredTime
         */
        public static void put(String key,String hashKey, String value, long expiredTime) {
            redisTemplate.executePipelined(new RedisCallback<String>() {
                @Override
                public String doInRedis(RedisConnection connection) throws DataAccessException {
                    connection.multi();
                    connection.hSet(key.getBytes(), hashKey.getBytes(), value.getBytes());
                    connection.expire(key.getBytes(), expiredTime);
                    connection.exec();
                    return null;
                }
            });
        }

        /**
         * 当hashKey不存在时，将key 中的字段 hashKey 的值设为 value
         * @param key
         * @param hashKey
         * @param value
         */
        public static Boolean putIfAbsent(String key, String hashKey, Object value) {
            return redisTemplate.opsForHash().putIfAbsent(key, hashKey, value);
        }

        /**
         * 同时将多个 hashKey-value (域-值)对设置到哈希表 key 中
         * @param key
         * @param map
         */
        public static void putAll(String key, Map<String, String> map) {
            redisTemplate.opsForHash().putAll(key, map);
        }
        
        /**
         * 批量设值并设置过期时间
         * @param key
         * @param map
         * @param expiredTime
         */
        public static void putAll(String key, Map<String, String> map, long expiredTime) {
            Map<byte[], byte[]> hashes = new HashMap<>();
            map.entrySet().forEach(item -> {
                hashes.put(item.getKey().getBytes(), item.getValue().getBytes());
            });
            redisTemplate.executePipelined(new RedisCallback<String>() {
                @Override
                public String doInRedis(RedisConnection connection) throws DataAccessException {
                    connection.multi();
                    connection.hMSet(key.getBytes(), hashes);
                    connection.expire(key.getBytes(), expiredTime);
                    connection.exec();
                    return null;
                }
            });
        }

        /**
         * 获取哈希表key中的所有字段hashKey
         * @param key
         * @return
         */
        public static Set<Object> keys(String key) {
            return redisTemplate.opsForHash().keys(key);
        }

        /**
         * 获取哈希表key中所有值value
         * @param key
         * @return
         */
        public static List<Object> values(String key) {
            return redisTemplate.opsForHash().values(key);
        }

        /**
         * 获取哈希表中字段的数量
         * @param key
         * @return
         */
        public static Long size(String key) {
            return redisTemplate.opsForHash().size(key);
        }

        /**
         * 删除哈希表key的字段 hashKeys
         * @param key
         * @param hashKeys
         */
        public static void delete(String key, Object... hashKeys) {
            redisTemplate.opsForHash().delete(key, hashKeys);
        }

        /**
         * 查看哈希表 key 中，指定的字段hashKey是否存在
         * @param key
         * @param hashKey
         * @return
         */
        public static Boolean hasKey(String key, String hashKey) {
            return redisTemplate.opsForHash().hasKey(key, hashKey);
        }
        
        /**
         * 对hashKey的值加1， 返回新值
         * @param key
         * @param hashKey
         * @return 
         */
        public static Long increment(String key, String hashKey) {
            return redisTemplate.opsForHash().increment(key, hashKey, 1L);
        }
    }
    
    /**
     * ###################################################################### set  #################################################################################
     */

    public static class SetOps {
        /**
         * 向集合set添加一个或多个成员
         * @param key
         * @param values
         */
        public static void add(String key, String... values) {
            redisTemplate.opsForSet().add(key, values);
        }
        
        /**
         * 向set中添加元素并设置过期时间
         * @param key
         * @param expiredTime
         * @param value
         */
        public static void add(String key, long expiredTime, String... values) {
            byte[][] b =  new byte[values.length][1];
            Arrays.asList(values).stream().map(item -> item.getBytes()).collect(Collectors.toList()).toArray(b);
            redisTemplate.executePipelined(new RedisCallback<String>() {
                @Override
                public String doInRedis(RedisConnection connection) throws DataAccessException {
                    connection.multi();
                    connection.sAdd(key.getBytes(), b);
                    connection.expire(key.getBytes(), expiredTime);
                    connection.exec();
                    return null;
                }
            });
        }
        
        /**
         * 获取集合set的成员数
         * @param key
         * @return
         */
        public static Long size(String key) {
            return redisTemplate.opsForSet().size(key);
        }
    
        /**
         * 返回集合set中的所有成员
         * @param key
         * @return
         */
        public static Set<String> members(String key) {
            return redisTemplate.opsForSet().members(key);
        }
    
        /**
         * 判断 value 元素是否是集合set的成员
         * @param key
         * @param value
         * @return
         */
        public static Boolean contain(String key, String value) {
            return redisTemplate.opsForSet().isMember(key, value);
        }
    
        /**
         * 移除并返回集合set中的一个随机元素
         * @param key
         * @return
         */
        public static String pop(String key) {
            return redisTemplate.opsForSet().pop(key);
        }
        
        /**
         * 一次随机移除count个数量的元素
         * @param key
         * @param count
         * @return
         */
        public static List<String> pop(String key, long count) {
            return redisTemplate.opsForSet().pop(key, count);
        }
    
        /**
         * 移除集合中一个或多个成员，并返回移除的数量
         * @param key
         * @param values
         * @return
         */
        public static Long removeValues(String key, Object... values) {
            return redisTemplate.opsForSet().remove(key, values);
    
        }
    
        /**
         * 返回集合set中1个随机数，不从集合set移除
         * @param key
         * @return
         */
        public static String random(String key) {
            return redisTemplate.opsForSet().randomMember(key);
        }
    
        /**
         * 返回集合set中count个随机数，不从集合set移除
         * @param key
         * @param count
         * @return 
         */
        public static List<String> multiRandom(String key, int count) {
            return redisTemplate.opsForSet().randomMembers(key, count);
        }
        
        /**
         * scan某个set，返回最多count个，匹配规则为pattern
         * @param key
         * @param count
         * @param pattern eg: *test*表示包含test字符串
         * @return
         */
        public static Cursor<String> scan(String key, Long count, String pattern) {
            return redisTemplate.opsForSet().scan(key, ScanOptions.scanOptions().count(count).match(pattern).build());
        }
        
        /**
         * -------------------------------差集---------------------------
         */
        
        /**
         * 比较2个set的差集
         * @param key
         * @param otherKey
         * @return
         */
        public static Set<String> difference(String key, String otherKey) {
            return redisTemplate.opsForSet().difference(key, otherKey);
        }
        
        /**
         * 比较2个set的差集，并存储到destKey的set中
         * @param key
         * @param otherKey
         * @param destKey
         * @return
         */
        public static Long differenceAndStore(String key, String otherKey, String destKey) {
            return redisTemplate.opsForSet().differenceAndStore(key, otherKey, destKey);
        }
        
        /**
         * 比较多个set的差集
         * @param key
         * @param otherKeys
         * @return
         */
        public static Set<String> difference(String key, Collection<String> otherKeys) {
            return redisTemplate.opsForSet().difference(key, otherKeys);
        }
        
        /**
         * 比较多个set的差集，并存储到destKey的set中
         * @param key
         * @param otherKeys
         * @param destKey
         * @return
         */
        public static Long differenceAndStore(String key, Collection<String> otherKeys, String destKey) {
            return redisTemplate.opsForSet().differenceAndStore(key, otherKeys, destKey);
        }
        /**
         * -------------------------------差集---------------------------
         */
        /**
         * -------------------------------交集---------------------------
         */
        
        /**
         * 比较2个set的交集
         * @param key
         * @param otherKey
         * @return
         */
        public static Set<String> intersect(String key, String otherKey) {
            return redisTemplate.opsForSet().intersect(key, otherKey);
        }
        
        /**
         * 比较2个set的交集，并存储到destKey的set中
         * @param key
         * @param otherKey
         * @param destKey
         * @return
         */
        public static Long intersectAndStore(String key, String otherKey, String destKey) {
            return redisTemplate.opsForSet().intersectAndStore(key, otherKey, destKey);
        }
        
        /**
         * 比较多个set的交集
         * @param key
         * @param otherKeys
         * @return
         */
        public static Set<String> intersect(String key, Collection<String> otherKeys) {
            return redisTemplate.opsForSet().intersect(key, otherKeys);
        }
        
        /**
         * 比较多个set的交集，并存储到destKey的set中
         * @param key
         * @param otherKeys
         * @param destKey
         * @return
         */
        public static Long intersectAndStore(String key, Collection<String> otherKeys, String destKey) {
            return redisTemplate.opsForSet().intersectAndStore(key, otherKeys, destKey);
        }
        /**
         * -------------------------------交集---------------------------
         */
        /**
         * -------------------------------并集---------------------------
         */
        /**
         * 比较2个set的并集
         * @param key
         * @param otherKey
         * @return
         */
        public static Set<String> union(String key, String otherKey) {
            return redisTemplate.opsForSet().union(key, otherKey);
        }
        
        /**
         * 比较2个set的并集，并存储到destKey的set中
         * @param key
         * @param otherKey
         * @param destKey
         * @return
         */
        public static Long unionAndStore(String key, String otherKey, String destKey) {
            return redisTemplate.opsForSet().unionAndStore(key, otherKey, destKey);
        }
        
        /**
         * 比较多个set的并集
         * @param key
         * @param otherKeys
         * @return
         */
        public static Set<String> union(String key, Collection<String> otherKeys) {
            return redisTemplate.opsForSet().union(key, otherKeys);
        }
        
        /**
         * 比较多个set的并集，并存储到destKey的set中
         * @param key
         * @param otherKeys
         * @param destKey
         * @return
         */
        public static Long unionAndStore(String key, Collection<String> otherKeys, String destKey) {
            return redisTemplate.opsForSet().unionAndStore(key, otherKeys, destKey);
        }
        /**
         * -------------------------------并集---------------------------
         */
    }

    /**
     * ###################################################################### zset  #################################################################################
     */
    public static class ZSetOps {
        /**
         * 向有序集合zset添加一个成员，或者更新已存在成员的分数
         * @param key
         * @param value
         * @param score
         */
        public static void add(String key, String value, double score) {
            redisTemplate.opsForZSet().add(key, value, score);
        }
    
        /**
         * 获取有序集合zset的成员数
         * @param key
         * @return
         */
        public static Long size(String key) {
            return redisTemplate.opsForZSet().zCard(key);
        }
    
        /**
         * 获取有序集合zset中指定分数区间的成员数, min<= score <=max
         * @param key
         * @param min 从0开始
         * @param max 从0开始
         * @return
         */
        public static Long countBetweenMinMax(String key, double min, double max) {
            return redisTemplate.opsForZSet().count(key, min, max);
        }
        
        /**
         * 增加一名成员在排序设置的评分,如果成员不存在，则直接add
         * @param key
         * @param increment
         * @param member
         * @return
         */
        public static Double incrementScore(String key, double increment, String member) {
            return redisTemplate.opsForZSet().incrementScore(key, member, increment);
        }
        
        /**
         * 通过索引区间返回有序集合zset指定区间内的成员，分数从低到高
         * @param key
         * @param start 索引从0开始，包含start
         * @param end 索引从0开始，包含end， -1表示到末尾
         * @return
         */
        public static Set<String> rangeByIndex(String key, long start, long end) {
            return redisTemplate.opsForZSet().range(key, start, end);
        }
    
        /**
         * 通过索引区间返回有序集合zset指定区间内的成员且带分数，分数从低到高
         * @param key
         * @param start 索引从0开始，包含start
         * @param end 索引从0开始，包含end， -1表示到末尾
         * @return
         */
        public static Set<ZSetOperations.TypedTuple<String>> rangeByIndexWithScores(String key, long start, long end) {
            return redisTemplate.opsForZSet().rangeWithScores(key, start, end);
        }
    
        /**
         * 通过索引区间返回有序集合zset指定区间内的成员，分数从高到低
         * @param key
         * @param start 索引从0开始，包含start
         * @param end 索引从0开始，包含end， -1表示到末尾
         * @return
         */
        public static Set<String> reverseRangeByIndex(String key, long start, long end) {
            return redisTemplate.opsForZSet().reverseRange(key, start, end);
        }
    
        /**
         * 通过索引区间返回有序集合zset指定区间内的成员且带分数，分数从高到低
         * @param key
         * @param start 索引从0开始，包含start
         * @param end 索引从0开始，包含end， -1表示到末尾
         * @return
         */
        public static Set<ZSetOperations.TypedTuple<String>> reverseRangeByIndexWithscores(String key, long start, long end) {
            return redisTemplate.opsForZSet().reverseRangeWithScores(key, start, end);
        }
    
        /**
         * 通过分数返回有序集合zset指定区间内的成员, 分数从低到高
         * @param key
         * @param min
         * @param max
         * @return
         */
        public static Set<String> rangeByScore(String key, double min, double max) {
            return redisTemplate.opsForZSet().rangeByScore(key, min, max);
        }
    
        /**
         * 通过分数返回有序集合zset指定区间内的成员且带分数, 分数从低到高
         * @param key
         * @param min
         * @param max
         * @return
         */
        public static Set<ZSetOperations.TypedTuple<String>> rangeByScoreWithscores(String key, double min, double max) {
            return redisTemplate.opsForZSet().rangeByScoreWithScores(key, min, max);
        }
    
        /**
         * 通过分数返回有序集合zset指定区间内的成员, 分数从高到低
         * @param key
         * @param min
         * @param max
         * @return
         */
        public static Set<String> reverseRangeByScore(String key, double min, double max) {
            return redisTemplate.opsForZSet().reverseRangeByScore(key, min, max);
        }
    
        /**
         * 通过分数返回有序集合zset指定区间内的成员且带分数, 分数从高到低
         * @param key
         * @param min
         * @param max
         * @return
         */
        public static Set<ZSetOperations.TypedTuple<String>> reverseRangeByScoreWithscores(String key, double min, double max) {
            return redisTemplate.opsForZSet().reverseRangeByScoreWithScores(key, min, max);
        }
    
        /**
         * 返回有序集合zset中指定成员的索引
         * @param key
         * @param value
         * @return
         */
        public static Long index(String key, Object value) {
            return redisTemplate.opsForZSet().rank(key, value);
        }
    
        /**
         * 移除有序集合zset中的一个或多个成员，并返回移除成员的个数
         * @param key
         * @param values
         */
        public static Long remove(String key, Object... values) {
            return redisTemplate.opsForZSet().remove(key, values);
        }
    
        /**
         * 移除有序集合zset给定的索引区间的所有成员，并返回移除成员的个数
         * @param key
         * @param start 索引从0开始，包含start
         * @param end 索引从0开始，包含end， -1表示到末尾
         * @return
         */
        public static Long removeRangeByRank(String key, long start, long end) {
            return redisTemplate.opsForZSet().removeRange(key, start, end);
        }
    
        /**
         * 移除有序集合zset给定的分数区间的所有成员，并返回移除成员的个数
         * @param key
         * @param min
         * @param max
         * @return
         */
        public static Long removeRangeByScore(String key, double min, double max) {
            return redisTemplate.opsForZSet().removeRangeByScore(key, min, max);
        }
        
        /**
         * 返回元素的分数，如果元素不存在则返回null
         * @param key
         * @param member
         * @return
         */
        public static Double score(String key, String member) {
            return redisTemplate.opsForZSet().score(key, member);
        }
        
        /**
         * 将key和otherKey的数据合并(并集)到新的destKey里
         * @param key
         * @param otherKey
         * @param destKey
         * @return
         */
        public static Long unionAndStore(String key, String otherKey, String destKey) {
            return redisTemplate.opsForZSet().unionAndStore(key, otherKey, destKey);
        }
        
        /**
         * 将key和otherKey的数据的交集存储到新的destKey里
         * @param key
         * @param otherKey
         * @param destKey
         * @return
         */
        public static Long intersectAndStore(String key, String otherKey, String destKey) {
            return redisTemplate.opsForZSet().intersectAndStore(key, otherKey, destKey);
        }
    }
}
