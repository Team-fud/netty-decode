package com.centerm.nettydecode.shiro.cache;


import com.centerm.nettydecode.constant.RedisConstants;
import com.centerm.nettydecode.util.JedisUtil;
import com.centerm.nettydecode.util.JwtUtil;
import com.centerm.nettydecode.util.common.PropertiesUtil;
import com.centerm.nettydecode.util.common.SerializableUtil;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;

import java.util.*;

/**
 *   重写shiro的cache缓存
 * @param <K>
 * @param <V>
 */
public class CustomCache<K,V> implements Cache {

    /**
     * 缓存的key名称获取为shiro:cache:account
     * @param key
     * @return java.lang.String
     * @author dolyw.com
     * @date 2018/9/4 18:33
     */
    private String getKey(Object key) {
        return RedisConstants.PREFIX_SHIRO_CACHE + JwtUtil.getClaim(key.toString(), RedisConstants.ACCOUNT);
    }
    /**
     *  缓存的获取
     * @param o
     * @return
     * @throws CacheException
     */
    @Override
    public Object get(Object o) throws CacheException {
        if (Boolean.FALSE.equals(JedisUtil.exists(this.getKey(o)))){
            return null;
        }
        return JedisUtil.getObject(this.getKey(o));
    }

    /**
     *  保存缓存
     * @param key
     * @param value
     * @return
     * @throws CacheException
     */
    @Override
    public Object put(Object key, Object value) throws CacheException {
        //读取redis的shiro缓存过期时间
        PropertiesUtil.readProperties("config.properties");
        String shiroCacheExpireTime = PropertiesUtil.getProperty("shiroCacheExpireTime");
        //设置redis的shiro缓存
        return JedisUtil.setObject(this.getKey(key),value,Integer.parseInt(shiroCacheExpireTime));
    }

    /**
     * 移除缓存
     * @param key
     * @return
     * @throws CacheException
     */
    @Override
    public Object remove(Object key) throws CacheException {
        if (Boolean.FALSE.equals(JedisUtil.exists(this.getKey(key)))){
            return null;
        }
        JedisUtil.delKey(this.getKey(key));
        return null;
    }

    @Override
    public void clear() throws CacheException {
        Objects.requireNonNull(JedisUtil.getJedis()).flushDB();
    }

    @Override
    public int size() {
       Long size = Objects.requireNonNull(JedisUtil.getJedis().dbSize());
       return size.intValue();
    }

    /**
     * 获取所以key'
     * @return
     */
    @Override
    public Set keys() {
        Set<byte[]> keys = Objects.requireNonNull(JedisUtil.getJedis()).keys("*".getBytes());
        Set<Object> set = new HashSet<>();
        for (byte[] bs:keys){
            set.add(SerializableUtil.unserializable(bs));
        }
        return set;
    }

    /**
     * 获取所有value
     * @return
     */
    @Override
    public Collection values() {
        Set keys = this.keys();
        List<Object> values = new ArrayList<>();
        for (Object key : keys){
            values.add(JedisUtil.getObject(this.getKey(key)));
        }
        return values;
    }
}
