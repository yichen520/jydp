package com.jydp.dao.impl.redis;

import com.iqmkj.utils.LogUtil;
import com.iqmkj.utils.StringUtil;
import com.jydp.dao.IRedisDao;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * redis服务
 * @author whx
 */
@Repository
public class RedisDaoImpl implements IRedisDao {

    @Resource(name = "redisTemplate")
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 判定redis中是否存在该key
     * @param redisKey redis键，不能为空
     * @return 存在：返回true，不存在：返回false（redisKey为空则返回false）
     */
    public boolean hasKey(String redisKey) {
        if (!StringUtil.isNotNull(redisKey)) {
            return false;
        }

        try {
            return redisTemplate.hasKey(redisKey);
        } catch (Exception e) {
            LogUtil.printErrorLog(e);
        }
        return false;
    }

    /**
     * 设置redisKey失效时间
     * @param redisKey redis键，不能为空
     * @param time 时间(秒) time>0
     * @return 操作成功：返回true，操作失败：返回false（redisKey为空或time<=0：返回false）
     */
    public boolean setExpire(String redisKey, long time) {
        if (!StringUtil.isNotNull(redisKey) || time <= 0) {
            return false;
        }

        try {
            redisTemplate.expire(redisKey, time, TimeUnit.SECONDS);
            return true;
        } catch (Exception e) {
            LogUtil.printErrorLog(e);
        }
        return false;
    }

    /**
     * 获取redisKey失效时间
     * @param redisKey redis键，不能为空
     * @return 查询成功:返回失效时间(秒)，返回0：永久有效（redisKey为空则返回-1）
     */
    public long getExpire(String redisKey) {
        if (!StringUtil.isNotNull(redisKey)) {
            return -1;
        }

        try {
            return redisTemplate.getExpire(redisKey, TimeUnit.SECONDS);
        } catch (Exception e) {
            LogUtil.printErrorLog(e);
        }
        return 0;
    }

    /**
     * 新增key-value（时间无限期）
     * @param redisKey redis键，不能为空
     * @param value 值
     * @return 操作成功：返回true，操作失败：返回false（redisKey为空则返回false）
     */
    public boolean addValue(String redisKey, Object value) {
        if (!StringUtil.isNotNull(redisKey)) {
            return false;
        }

        try {
            redisTemplate.opsForValue().set(redisKey, value);
            return true;
        } catch (Exception e) {
            LogUtil.printErrorLog(e);
        }
        return false;
    }

    /**
     * 新增key-value并设置时间
     * @param redisKey redis键，不能为空
     * @param value 值
     * @param time 时间(秒) time>0
     * @return 操作成功：返回true，操作失败：返回false（redisKey为空或time<=0：返回false）
     */
    public boolean addValue(String redisKey,Object value,long time){
        if (!StringUtil.isNotNull(redisKey) || time <= 0) {
            return false;
        }

        try {
            redisTemplate.opsForValue().set(redisKey, value, time, TimeUnit.SECONDS);
            return true;
        } catch (Exception e) {
            LogUtil.printErrorLog(e);
        }
        return false;
    }

    /**
     * 获取redis键对应的值
     * @param redisKey redis键，不能为空
     * @return 操作成功：返回键对应的值，操作失败：返回null（redisKey为空则返回null）
     */
    public Object getValue(String redisKey) {
        if (!StringUtil.isNotNull(redisKey)) {
            return null;
        }

        try {
            return redisTemplate.opsForValue().get(redisKey);
        } catch (Exception e) {
            LogUtil.printErrorLog(e);
        }
        return null;
    }

    /**
     * 删除单个redisKey
     * @param redisKey redis键，不能为空
     * @return 操作成功：返回true，操作失败：返回false（redisKey为空则返回false）
     */
    public boolean deleteValue(String redisKey) {
        if (!StringUtil.isNotNull(redisKey)) {
            return false;
        }

        try {
            redisTemplate.delete(redisKey);
            return true;
        } catch (Exception e) {
            LogUtil.printErrorLog(e);
        }
        return false;
    }

    /**
     * 批量删除redisKey
     * @param redisKeyList 批量redis键，不能为空
     * @return 操作成功：返回true，操作失败：返回false（redisKeyList为空则返回false）
     */
    public boolean deleteValueList(List<String> redisKeyList) {
        if (CollectionUtils.isEmpty(redisKeyList)) {
            return false;
        }

        try {
            redisTemplate.delete(redisKeyList);
            return true;
        } catch (Exception e) {
            LogUtil.printErrorLog(e);
        }
        return false;
    }

    /**
     * 新增map
     * @param redisKey redis键，不能为空
     * @param map HashMap<Object, Object>值
     * @return 操作成功：返回true，操作失败：返回false（redisKey为空则返回false）
     */
    public boolean addMap(String redisKey, Map<Object, Object> map) {
        if (!StringUtil.isNotNull(redisKey)) {
            return false;
        }

        try {
            redisTemplate.opsForHash().putAll(redisKey, map);
            return true;
        } catch (Exception e) {
            LogUtil.printErrorLog(e);
        }
        return false;
    }

    /**
     * 新增map并设置失效时间
     * @param redisKey redis键，不能为空
     * @param map HashMap<Object, Object>值
     * @param time 时间(秒) time>0
     * @return 操作成功：返回true，操作失败：返回false（redisKey为空或time<=0：返回false）
     */
    public boolean addMap(String redisKey, Map<Object, Object> map, long time) {
        if (!StringUtil.isNotNull(redisKey) || time <= 0) {
            return false;
        }

        try {
            redisTemplate.opsForHash().putAll(redisKey, map);
            return setExpire(redisKey, time);
        } catch (Exception e) {
            LogUtil.printErrorLog(e);
        }
        return false;
    }

    /**
     * 新增map值
     * @param redisKey redis键，不能为空
     * @param mapKey map键
     * @param mapValue map值
     * @return 操作成功：返回true，操作失败：返回false（redisKey为空则返回false）
     */
    public boolean addMapValue(String redisKey, Object mapKey, Object mapValue) {
        if (!StringUtil.isNotNull(redisKey)) {
            return false;
        }

        try {
            redisTemplate.opsForHash().put(redisKey, mapKey, mapValue);
            return true;
        } catch (Exception e) {
            LogUtil.printErrorLog(e);
        }
        return false;
    }

    /**
     * 新增map值并设置失效时间
     * @param redisKey redis键，不能为空
     * @param mapKey map键
     * @param mapValue map值
     * @param time 时间(秒) time>0
     * @return 操作成功：返回true，操作失败：返回false（redisKey为空或time<=0：返回false）
     */
    public boolean addMapValue(String redisKey, Object mapKey, Object mapValue, long time) {
        if (!StringUtil.isNotNull(redisKey) || time <= 0) {
            return false;
        }

        try {
            redisTemplate.opsForHash().put(redisKey, mapKey, mapValue);
            return setExpire(redisKey, time);
        } catch (Exception e) {
            LogUtil.printErrorLog(e);
        }
        return false;
    }

    /**
     * 获取Map
     * @param redisKey redis键，不能为空
     * @return 操作成功：返回HashMap值，操作失败：返回null（redisKey为空则返回空）
     */
    public Map<Object, Object> getMap(String redisKey) {
        if (!StringUtil.isNotNull(redisKey)) {
            return null;
        }

        try {
            return redisTemplate.opsForHash().entries(redisKey);
        } catch (Exception e) {
            LogUtil.printErrorLog(e);
        }
        return null;
    }

    /**
     * 获取Map值
     * @param redisKey redis键，不能为空
     * @param mapKey map键
     * @return 操作成功：返回HashMap值，操作失败：返回null（redisKey为空则返回空）
     */
    public Object getMapValue(String redisKey, Object mapKey) {
        if (!StringUtil.isNotNull(redisKey)) {
            return null;
        }
        return redisTemplate.opsForHash().get(redisKey, mapKey);
    }

    /**
     * 删除Map中的mapValue值
     * @param redisKey redis键，不能为空
     * @param mapKey map键，不能为空，可以多个map键
     * @return 操作成功：返回true，操作失败：返回false（redisKey为空或mapKey为null：返回false）
     */
    public boolean deleteMapValue(String redisKey, Object... mapKey) {
        if (!StringUtil.isNotNull(redisKey) || mapKey == null) {
            return false;
        }

        try {
            redisTemplate.opsForHash().delete(redisKey, mapKey);
            return true;
        } catch (Exception e) {
            LogUtil.printErrorLog(e);
        }
        return false;
    }

    /**
     * 判断Map中是否有该mapKey-mapValue
     * @param redisKey redis键，不能为空
     * @param mapKey map键，不能为空
     * @return 存在：返回true，不存在：返回false（redisKey为空或mapKey为空：返回false）
     */
    public boolean hasKeyForMap(String redisKey, String mapKey) {
        if (!StringUtil.isNotNull(redisKey) || !StringUtil.isNotNull(mapKey)) {
            return false;
        }

        try {
            return redisTemplate.opsForHash().hasKey(redisKey, mapKey);
        } catch (Exception e) {
            LogUtil.printErrorLog(e);
        }
        return false;
    }

    /**
     * 获取map长度大小
     * @param redisKey，不能为空
     * @return 查询成功：返回map长度大小，查询失败：返回0（redisKey为空则返回0）
     */
    public long getMapSize(String redisKey) {
        if (!StringUtil.isNotNull(redisKey)) {
            return 0;
        }
        try {
            return redisTemplate.opsForHash().size(redisKey);
        } catch (Exception e) {
            LogUtil.printErrorLog(e);
        }
        return 0;
    }

    /**
     * 新增list
     * @param redisKey redis键，不能为空
     * @param value 值
     * @return 操作成功：返回true，操作失败：返回false（redisKey为空则返回false）
     */
    public boolean addList(String redisKey, Object value) {
        if (!StringUtil.isNotNull(redisKey)) {
            return false;
        }

        try {
            redisTemplate.opsForList().rightPush(redisKey, value);
            return true;
        } catch (Exception e) {
            LogUtil.printErrorLog(e);
        }
        return false;
    }

    /**
     * 新增list并设置失效时间
     * @param redisKey redis键，不能为空
     * @param value 值
     * @param time 时间(秒) time>0
     * @return 操作成功：返回true，操作失败：返回false（redisKey为空或time<=0：返回false）
     */
    public boolean addList(String redisKey, Object value, long time) {
        if (!StringUtil.isNotNull(redisKey) || time <= 0) {
            return false;
        }

        try {
            redisTemplate.opsForList().rightPush(redisKey, value);
            return setExpire(redisKey, time);
        } catch (Exception e) {
            LogUtil.printErrorLog(e);
        }
        return false;
    }

    /**
     * 新增list（追加list）
     * @param redisKey redis键，不能为空
     * @param valueList 值
     * @return 操作成功：返回true，操作失败：返回false（redisKey为空则返回false）
     */
    public boolean addList(String redisKey, List<Object> valueList) {
        if (!StringUtil.isNotNull(redisKey)) {
            return false;
        }

        try {
            redisTemplate.opsForList().rightPushAll(redisKey, valueList);
            return true;
        } catch (Exception e) {
            LogUtil.printErrorLog(e);
        }
        return false;
    }

    /**
     * 新增list并设置失效时间（追加list）
     * @param redisKey redis键，不能为空
     * @param valueList 值
     * @param time 时间(秒)
     * @return 操作成功：返回true，操作失败：返回false（redisKey为空或time<=0：返回false）
     */
    public boolean addList(String redisKey, List<Object> valueList, long time) {
        if (!StringUtil.isNotNull(redisKey) || time <= 0) {
            return false;
        }

        try {
            redisTemplate.opsForList().rightPushAll(redisKey, valueList);
            return setExpire(redisKey, time);
        } catch (Exception e) {
            LogUtil.printErrorLog(e);
        }
        return false;
    }

    /**
     * 获取list
     * @param redisKey redis键，不能为空
     * @return 查询成功：返回list，查询失败：返回null（redisKey为空则返回null）
     */
    public List<Object> getList(String redisKey){
        if (!StringUtil.isNotNull(redisKey)) {
            return null;
        }

        try {
            return redisTemplate.opsForList().range(redisKey, 0, getListSize(redisKey));
        } catch (Exception e) {
            LogUtil.printErrorLog(e);
        }
        return null;
    }

    /**
     * 获取list（通过索引下标）
     * @param redisKey redis键，不能为空
     * @param start 开始位置
     * @param end 结束位置
     * @return 查询成功：返回list，查询失败：返回null（start-end：0到-1代表所有值，redisKey为空则返回null）
     */
    public List<Object> getList(String redisKey,long start, long end){
        if (!StringUtil.isNotNull(redisKey)) {
            return null;
        }

        try {
            return redisTemplate.opsForList().range(redisKey, start, end);
        } catch (Exception e) {
            LogUtil.printErrorLog(e);
        }
        return null;
    }

    /**
     * 获取list中单个值（通过索引下标）
     * @param redisKey redis键，不能为空
     * @param index 索引下标，index>=0时（0：表头，1：第二个元素），index<0时（-1：表尾，-2：倒数第二个元素）
     * @return 查询成功：返回list中单个值，查询失败：返回null（redisKey为空则返回null）
     */
    public Object getListIndex(String redisKey,long index){
        if (!StringUtil.isNotNull(redisKey)) {
            return null;
        }

        try {
            return redisTemplate.opsForList().index(redisKey, index);
        } catch (Exception e) {
            LogUtil.printErrorLog(e);
        }
        return null;
    }

    /**
     * 获取list长度大小
     * @param redisKey redis键，不能为空
     * @return 查询成功：返回list长度大小，查询失败：返回0（redisKey为空则返回0）
     */
    public long getListSize(String redisKey){
        if (!StringUtil.isNotNull(redisKey)) {
            return 0;
        }

        try {
            return redisTemplate.opsForList().size(redisKey);
        } catch (Exception e) {
            LogUtil.printErrorLog(e);
        }
        return 0;
    }

    /**
     * 修改list中的某条数据（通过索引下标）
     * @param redisKey redis键，不能为空
     * @param index 索引下标
     * @param value 新值
     * @return 操作成功：返回true，操作失败：返回false（redisKey为空则返回false）
     */
    public boolean updateListIndex(String redisKey, long index, Object value) {
        if (!StringUtil.isNotNull(redisKey)) {
            return false;
        }

        try {
            redisTemplate.opsForList().set(redisKey, index, value);
            return true;
        } catch (Exception e) {
            LogUtil.printErrorLog(e);
        }
        return false;
    }

    /**
     * 保存list开始下标和结束下标之间的元素（包括开始下标和结束下标）
     * @param redisKey redis键
     * @param start 开始下标，大于0
     * @param end 结束下标，大于0
     * @return 操作成功：返回true，操作失败：返回false（redisKey为空则返回false，start或end小于0返回false）
     */
    public boolean trimList(String redisKey, long start, long end) {
        if (!StringUtil.isNotNull(redisKey) || start < 0 || end < 0) {
            return false;
        }

        try {
            redisTemplate.opsForList().trim(redisKey, start, end);
            return true;
        } catch (Exception e) {
            LogUtil.printErrorLog(e);
        }
        return false;
    }

    /**
     * 保存list开始下标和结束下标之间的元素（包括开始下标和结束下标）
     * @param redisKey redis键
     * @param start 开始下标，大于0
     * @param end 结束下标，大于0
     * @param time 时间(秒)
     * @return 操作成功：返回true，操作失败：返回false（redisKey为空或time<=0，start或end小于0返回false）
     */
    public boolean trimList(String redisKey, long start, long end, long time) {
        if (!StringUtil.isNotNull(redisKey) || start < 0 || end < 0 || time <= 0) {
            return false;
        }

        try {
            redisTemplate.opsForList().trim(redisKey, start, end);
            return setExpire(redisKey, time);
        } catch (Exception e) {
            LogUtil.printErrorLog(e);
        }
        return false;
    }

}
