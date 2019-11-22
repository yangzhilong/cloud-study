package com.longge.common.service.util;

import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.connection.DataType;
import org.springframework.data.redis.connection.RedisConnection;
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

    public RedisUtils() {
        super();
    }

    /**
     * ###################################################################### common command  #################################################################################
     */

    public static Long dbsize() {
        return redisTemplate.execute((RedisConnection connection) -> connection.dbSize(), true);
    }

    public static Properties info() {
        return redisTemplate.execute((RedisConnection connection) -> connection.info(), true);
    }

    public static Properties info(String section) {
        return redisTemplate.execute((RedisConnection connection) -> connection.info(section), true);
    }

    public static Long ttl(String key) {
        byte[] bytes = key.getBytes();
        return redisTemplate.execute((RedisConnection connection) -> connection.ttl(bytes), true);
    }


    /**
     * ###################################################################### string  #################################################################################
     */

    /**
     * @param key
     * @return
     * @desc 获取指定 key 的值
     */
    public static String get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    /**
     * @param key
     * @param value
     * @desc 设置指定 key 的值value
     */
    public static void set(String key, String value) {
        redisTemplate.opsForValue().set(key, value);
    }

    /**
     * @param key
     * @param value
     * @param expiredTime
     * @param expiredTimeUnit
     * @desc 为给定 key 设置过期时间expiredTime
     */
    public static void setEx(String key, String value, long expiredTime, TimeUnit expiredTimeUnit) {
        if (expiredTime <= 0 || expiredTimeUnit == null) {
            redisTemplate.opsForValue().set(key, value);
        } else {
            redisTemplate.opsForValue().set(key, value, expiredTime, expiredTimeUnit);
        }
    }

    /**
     * @param key
     * @param value
     * @return
     * @desc 将给定 key 的值设为 value ，并返回 key 的旧值(old value)
     */
    public static String getAndSet(String key, String value) {
        return redisTemplate.opsForValue().getAndSet(key, value);
    }

    /**
     * @param key
     * @return
     * @desc 检查给定 key 是否存在
     */
    public static Boolean exist(String key) {
        return redisTemplate.hasKey(key);
    }

    /**
     * @param key
     * @desc 在 key 存在时删除 key
     */
    public static void delete(String key) {
        redisTemplate.delete(key);
    }

    /**
     * @param key
     * @return
     * @desc 将 key 中储存的数字值增1
     */
    public static Long autoIncrement(String key) {
        return autoIncrementByCount(key, -1);
    }

    /**
     * @param key
     * @param count
     * @return
     * @desc 将 key 中储存的数字值增count
     */
    public static Long autoIncrementByCount(String key, long count) {
        if (count == -1) {
            return redisTemplate.opsForValue().increment(key, 1);
        }
        return redisTemplate.opsForValue().increment(key, count);
    }

    /**
     * @param key
     * @return
     * @desc 查询redis类型
     */
    public static DataType type(String key) {
        return redisTemplate.type(key);
    }

    /**
     * @return
     * @desc 返回一个随机的key
     */
    public static String randomKey() {
        return redisTemplate.randomKey();
    }

    public static void expire(String key, long expiredTime, TimeUnit expiredTimeUnit) {
        if (expiredTime > 0 && expiredTimeUnit != null) {
            redisTemplate.expire(key, expiredTime, expiredTimeUnit);
        }
    }


    /**
     * ###################################################################### list  #################################################################################
     */

    /**
     * @param key
     * @param value
     */
    public static void listLeftPush(String key, String value) {
        redisTemplate.opsForList().leftPush(key, value);
    }

    /**
     * @param key
     * @param values
     * @desc 在列表头部插入多个元素
     */
    public static void listLeftPushAll(String key, String... values) {
        redisTemplate.opsForList().leftPushAll(key, values);
    }

    /**
     * @param key
     * @param list
     * @desc 在列表头部插入多个元素
     */
    public static void listLeftPushAll(String key, List<String> list) {
        redisTemplate.opsForList().leftPushAll(key, list);
    }

    /**
     * @param key
     * @param value
     * @desc 在列表尾部插入一个元素
     */
    public static void listRightPush(String key, String value) {
        redisTemplate.opsForList().rightPush(key, value);
    }

    /**
     * @param key
     * @param values
     * @desc 在列表尾部插入多个元素
     */
    public static void listRightPushAll(String key, String... values) {
        redisTemplate.opsForList().rightPushAll(key, values);
    }

    /**
     * @param key
     * @param list
     * @desc 在列表尾部插入多个元素
     */
    public static void listRightPushAll(String key, List<String> list) {
        redisTemplate.opsForList().rightPushAll(key, list);
    }

    /**
     * @param key
     * @return
     * @desc 移除并获取列表的第一个元素
     */
    public static String listLeftPop(String key) {
        return listLeftPop(key, null, -1);
    }

    public static String listLeftPop(String key, TimeUnit expiredTimeUnit, long expiredTime) {
        if (expiredTime < 0 || expiredTimeUnit == null) {
            return redisTemplate.opsForList().leftPop(key);
        }
        return redisTemplate.opsForList().leftPop(key, expiredTime, expiredTimeUnit);
    }

    /**
     * @param key
     * @return
     * @desc 移除并获取列表的最后一个元素
     */
    public static String listRightPop(String key) {
        return listRightPop(key, null, -1);
    }


    public static String listRightPop(String key, TimeUnit expiredTimeUnit, long expiredTime) {
        if (expiredTime < 0 || expiredTimeUnit == null) {
            return redisTemplate.opsForList().rightPop(key);
        }
        return redisTemplate.opsForList().rightPop(key, expiredTime, expiredTimeUnit);
    }

    /**
     * @param key
     * @return
     * @desc 获取列表长度
     */
    public static long listSize(String key) {
        return redisTemplate.opsForList().size(key);
    }

    /**
     * @param key
     * @return
     * @desc 获取列表list的所有元素
     */
    public static List<String> listGetAll(String key) {
        return redisTemplate.opsForList().range(key, 0, -1);
    }

    /**
     * @param key
     * @param index
     * @return 通过索引获取列表list中的元素
     */
    public static String listIndex(String key, long index) {
        return redisTemplate.opsForList().index(key, index);
    }

    /**
     * 获取指定范围的list的数据
     * @param key
     * @param start
     * @param end
     * @return
     */
    public static List<String> listRange(String key, long start, long end) {
        return redisTemplate.opsForList().range(key, start, end);
    }

    public static void listTrim(String key, long start, long end) {
        redisTemplate.opsForList().trim(key, start, end);
    }


    /**
     * ###################################################################### hash  #################################################################################
     */

    /**
     * @param key
     * @param hashKey
     * @return
     * @desc 获取存储在哈希表key中指定字段hashKey的值
     */
    public static Object hashGet(String key, String hashKey) {
        return redisTemplate.opsForHash().get(key, hashKey);
    }

    /**
     * @param key
     * @return
     * @desc 获取哈希表key的所有字段和值
     */
    public static Map<Object, Object> hashGetAll(String key) {
        return redisTemplate.opsForHash().getOperations().boundHashOps(key).entries();
    }

    /**
     * @param key
     * @param hashKeys
     * @return
     * @desc 获取哈希表key指定的字段的值
     */
    public static List<Object> hashMultiGet(String key, Set<Object> hashKeys) {
        return redisTemplate.opsForHash().multiGet(key, hashKeys);
    }

    /**
     * @param key
     * @param hashKey
     * @param value
     * @desc 将哈希表 key 中的字段 hashKey 的值设为 value
     */
    public static void hashPut(String key, Object hashKey, Object value) {
        redisTemplate.opsForHash().put(key, hashKey, value);
    }

    /**
     * @param key
     * @param hashKey
     * @param value
     * @desc 当hashKey不存在时，将哈希表 key 中的字段 hashKey 的值设为 value
     */
    public static boolean hashPutIfAbsent(String key, Object hashKey, Object value) {
        return redisTemplate.opsForHash().putIfAbsent(key, hashKey, value);
    }

    /**
     * @param key
     * @param map
     * @desc 同时将多个 hashKey-value (域-值)对设置到哈希表 key 中
     */
    public static void hashMultiSet(String key, Map<Object, Object> map) {
        redisTemplate.opsForHash().putAll(key, map);
    }
    
    /**
     * @param key
     * @param map
     * @param expiredTime
     * @param expiredTimeUnit
     * @desc hashmultiset并设置过期时间
     */
    public static void hashMultiSetEx(String key, Map<Object, Object> map, long expiredTime, TimeUnit expiredTimeUnit) {
        redisTemplate.opsForHash().putAll(key, map);
        if (expiredTime > 0 && expiredTimeUnit != null) {
            redisTemplate.expire(key, expiredTime, expiredTimeUnit);
        }
    }

    /**
     * @param key
     * @return
     * @desc 获取哈希表key中的所有字段hashKey
     */
    public static Set<Object> hsahKeys(String key) {
        return redisTemplate.opsForHash().keys(key);
    }

    /**
     * @param key
     * @return
     * @desc 获取哈希表key中所有值value
     */
    public static List<Object> hashValues(String key) {
        return redisTemplate.opsForHash().values(key);
    }

    /**
     * @param key
     * @return
     * @desc 获取哈希表中字段的数量
     */
    public static Long hashSize(String key) {
        return redisTemplate.opsForHash().size(key);
    }

    /**
     * @param key
     * @param hashKeys
     * @desc 删除哈希表key的字段 hashKeys
     */
    public static void hashDelete(String key, Object... hashKeys) {
        redisTemplate.opsForHash().delete(key, hashKeys);
    }

    /**
     * @param key
     * @param hashKey
     * @return
     * @desc 查看哈希表 key 中，指定的字段hashKey是否存在。
     */
    public static Boolean existHashkey(String key, String hashKey) {
        return redisTemplate.opsForHash().hasKey(key, hashKey);
    }


    /**
     * ###################################################################### set  #################################################################################
     */

    /**
     * @param key
     * @param values
     * @desc 向集合set添加一个或多个成员
     */
    public static void setAdd(String key, String... values) {
        redisTemplate.opsForSet().add(key, values);
    }

    /**
     * @param key
     * @return
     * @desc 获取集合set的成员数
     */
    public static Long setSize(String key) {
        return redisTemplate.opsForSet().size(key);
    }

    /**
     * @param key
     * @return
     * @desc 返回集合set中的所有成员
     */
    public static Set<String> setMembers(String key) {
        return redisTemplate.opsForSet().members(key);
    }

    /**
     * @param key
     * @param value
     * @return
     * @desc 判断 value 元素是否是集合set的成员
     */
    public static Boolean isMember(String key, String value) {
        return redisTemplate.opsForSet().isMember(key, value);
    }

    /**
     * @param key
     * @return
     * @desc 移除并返回集合set中的一个随机元素
     */
    public static String setPop(String key) {
        return redisTemplate.opsForSet().pop(key);
    }

    /**
     * @param key
     * @param values
     * @return
     * @desc 移除集合中一个或多个成员，并返回移除的数量
     */
    public static Long removeValues(String key, Object... values) {
        return redisTemplate.opsForSet().remove(key, values);

    }

    /**
     * @param key
     * @return
     * @desc 返回集合set中1个随机数，不从集合set移除
     */
    public static String setRandom(String key) {
        return redisTemplate.opsForSet().randomMember(key);
    }

    /**
     * @param key
     * @param count
     * @return 返回集合set中count个随机数，不从集合set移除
     */
    public static List<String> setMultiRandom(String key, int count) {
        return redisTemplate.opsForSet().randomMembers(key, count);
    }


    /**
     * ###################################################################### zset  #################################################################################
     */

    /**
     * @param key
     * @param value
     * @param score
     * @desc 向有序集合zset添加一个成员，或者更新已存在成员的分数
     */
    public static void zsetAdd(String key, String value, double score) {
        redisTemplate.opsForZSet().add(key, value, score);
    }

    /**
     * @param key
     * @return
     * @desc 获取有序集合zset的成员数
     */
    public static Long zsetSize(String key) {
        return redisTemplate.opsForZSet().zCard(key);
    }

    /**
     * @param key
     * @param min
     * @param max
     * @return
     * @desc 获取有序集合zset中指定分数区间的成员数, min<= score <=max
     */
    public static Long zsetSizeBetweenMinMax(String key, double min, double max) {
        return redisTemplate.opsForZSet().count(key, min, max);
    }

    /**
     * @param key
     * @param start
     * @param end
     * @return
     * @desc 通过索引区间返回有序集合zset指定区间内的成员，分数从低到高
     */
    public static Set<String> zsetRangeByIndex(String key, long start, long end) {
        return redisTemplate.opsForZSet().range(key, start, end);
    }

    /**
     * @param key
     * @param start
     * @param end
     * @return
     * @desc 通过索引区间返回有序集合zset指定区间内的成员且带分数，分数从低到高
     */
    public static Set<ZSetOperations.TypedTuple<String>> zsetRangeByIndexWithScores(String key, long start, long end) {
        return redisTemplate.opsForZSet().rangeWithScores(key, start, end);
    }

    /**
     * @param key
     * @param start
     * @param end
     * @return
     * @desc 通过索引区间返回有序集合zset指定区间内的成员，分数从高到低
     */
    public static Set<String> zsetReverseRangeByIndex(String key, long start, long end) {
        return redisTemplate.opsForZSet().reverseRange(key, start, end);
    }

    /**
     * @param key
     * @param start
     * @param end
     * @return
     * @desc 通过索引区间返回有序集合zset指定区间内的成员且带分数，分数从高到低
     */
    public static Set<ZSetOperations.TypedTuple<String>> zsetReverseRangeByIndexWithscores(String key, long start, long end) {
        return redisTemplate.opsForZSet().reverseRangeWithScores(key, start, end);
    }

    /**
     * @param key
     * @param min
     * @param max
     * @return
     * @desc 通过分数返回有序集合zset指定区间内的成员, 分数从低到高
     */
    public static Set<String> zsetRangeByScore(String key, double min, double max) {
        return redisTemplate.opsForZSet().rangeByScore(key, min, max);
    }

    /**
     * @param key
     * @param min
     * @param max
     * @return
     * @desc 通过分数返回有序集合zset指定区间内的成员且带分数, 分数从低到高
     */
    public static Set<ZSetOperations.TypedTuple<String>> zsetRangeByScoreWithscores(String key, double min, double max) {
        return redisTemplate.opsForZSet().rangeByScoreWithScores(key, min, max);
    }

    /**
     * @param key
     * @param min
     * @param max
     * @return
     * @desc 通过分数返回有序集合zset指定区间内的成员, 分数从高到低
     */
    public static Set<String> zsetReverseRangeByScore(String key, double min, double max) {
        return redisTemplate.opsForZSet().reverseRangeByScore(key, min, max);
    }

    /**
     * @param key
     * @param min
     * @param max
     * @return
     * @desc 通过分数返回有序集合zset指定区间内的成员且带分数, 分数从高到低
     */
    public static Set<ZSetOperations.TypedTuple<String>> zsetReverseRangeByScoreWithscores(String key, double min, double max) {
        return redisTemplate.opsForZSet().reverseRangeByScoreWithScores(key, min, max);
    }

    /**
     * @param key
     * @param value
     * @return
     * @desc 返回有序集合zset中指定成员的索引
     */
    public static Long zsetIndex(String key, Object value) {
        return redisTemplate.opsForZSet().rank(key, value);
    }

    /**
     * @param key
     * @param values
     * @desc 移除有序集合zset中的一个或多个成员，并返回移除成员的个数
     */
    public static Long zsetRemove(String key, Object... values) {
        return redisTemplate.opsForZSet().remove(key, values);
    }

    /**
     * @param key
     * @param start
     * @param end
     * @return
     * @desc 移除有序集合zset给定的索引区间的所有成员，并返回移除成员的个数
     */
    public static Long zsetRemoveRangeByRank(String key, long start, long end) {
        return redisTemplate.opsForZSet().removeRange(key, start, end);
    }

    /**
     * @param key
     * @param min
     * @param max
     * @return
     * @desc 移除有序集合zset给定的分数区间的所有成员，并返回移除成员的个数
     */
    public static Long zsetRemoveRangeByScore(String key, double min, double max) {
        return redisTemplate.opsForZSet().removeRangeByScore(key, min, max);
    }
}
