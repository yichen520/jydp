package com.jydp.service.impl.redis;

import com.jydp.dao.IRedisDao;
import com.jydp.service.IRedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * redis服务
 * @author whx
 */
@Service("redisService")
public class RedisServiceImpl implements IRedisService {

    /** redis服务 */
    @Autowired
    private IRedisDao redisDao;

    /**
     * 判定redis中是否存在该key
     * @param redisKey redis键，不能为空
     * @return 存在：返回true，不存在：返回false（redisKey为空则返回false）
     */
    public boolean hasKey(String redisKey) {
        return redisDao.hasKey(redisKey);
    }

    /**
     * 设置redisKey失效时间
     * @param redisKey redis键，不能为空
     * @param time 时间(秒) time>0
     * @return 操作成功：返回true，操作失败：返回false（redisKey为空或time<=0：返回false）
     */
    public boolean setExpire(String redisKey, long time) {
        return redisDao.setExpire(redisKey, time);
    }

    /**
     * 获取redisKey失效时间
     * @param redisKey redis键，不能为空
     * @return 查询成功:返回失效时间(秒)，返回0：永久有效（redisKey为空则返回-1）
     */
    public long getExpire(String redisKey) {
        return redisDao.getExpire(redisKey);
    }

    /**
     * 新增key-value（时间无限期）
     * @param redisKey redis键，不能为空
     * @param value 值
     * @return 操作成功：返回true，操作失败：返回false（redisKey为空则返回false）
     */
    public boolean addValue(String redisKey, Object value) {
        return redisDao.addValue(redisKey, value);
    }

    /**
     * 新增key-value并设置时间
     * @param redisKey redis键，不能为空
     * @param value 值
     * @param time 时间(秒) time>0
     * @return 操作成功：返回true，操作失败：返回false（redisKey为空或time<=0：返回false）
     */
    public boolean addValue(String redisKey,Object value,long time) {
        return redisDao.addValue(redisKey, value, time);
    }

    /**
     * 获取redis键对应的值
     * @param redisKey redis键，不能为空
     * @return 操作成功：返回键对应的值，操作失败：返回null（redisKey为空则返回null）
     */
    public Object getValue(String redisKey) {
        return redisDao.getMap(redisKey);
    }

    /**
     * 删除单个redisKey
     * @param redisKey redis键，不能为空
     * @return 操作成功：返回true，操作失败：返回false（redisKey为空则返回false）
     */
    public boolean deleteValue(String redisKey) {
        return redisDao.deleteValue(redisKey);
    }

    /**
     * 批量删除redisKey
     * @param redisKeyList 批量redis键，不能为空
     * @return 操作成功：返回true，操作失败：返回false（redisKeyList为空则返回false）
     */
    public boolean deleteValueList(List<String> redisKeyList) {
        return redisDao.deleteValueList(redisKeyList);
    }

    /**
     * 新增map
     * @param redisKey redis键，不能为空
     * @param map HashMap<Object, Object>值
     * @return 操作成功：返回true，操作失败：返回false（redisKey为空则返回false）
     */
    public boolean addMap(String redisKey, Map<Object, Object> map) {
        return redisDao.addMap(redisKey, map);
    }

    /**
     * 新增map并设置失效时间
     * @param redisKey redis键，不能为空
     * @param map HashMap<Object, Object>值
     * @param time 时间(秒) time>0
     * @return 操作成功：返回true，操作失败：返回false（redisKey为空或time<=0：返回false）
     */
    public boolean addMap(String redisKey, Map<Object, Object> map, long time) {
        return redisDao.addMap(redisKey, map, time);
    }

    /**
     * 新增map值
     * @param redisKey redis键，不能为空
     * @param mapKey map键
     * @param mapValue map值
     * @return 操作成功：返回true，操作失败：返回false（redisKey为空则返回false）
     */
    public boolean addMapValue(String redisKey, Object mapKey, Object mapValue) {
        return redisDao.addMapValue(redisKey, mapKey, mapValue);
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
        return redisDao.addMapValue(redisKey, mapKey, mapValue, time);
    }

    /**
     * 获取Map
     * @param redisKey redis键，不能为空
     * @return 操作成功：返回HashMap值，操作失败：返回null（redisKey为空则返回空）
     */
    public Map<Object, Object> getMap(String redisKey) {
        return redisDao.getMap(redisKey);
    }

    /**
     * 获取Map值
     * @param redisKey redis键，不能为空
     * @param mapKey map键
     * @return 操作成功：返回HashMap值，操作失败：返回null（redisKey为空则返回空）
     */
    public Object getMapValue(String redisKey, Object mapKey) {
        return redisDao.getMapValue(redisKey, mapKey);
    }

    /**
     * 删除Map中的mapValue值
     * @param redisKey redis键，不能为空
     * @param mapKey map键，不能为空，可以多个map键
     * @return 操作成功：返回true，操作失败：返回false（redisKey为空或mapKey为null：返回false）
     */
    public boolean deleteMapValue(String redisKey, Object... mapKey) {
        return redisDao.deleteMapValue(redisKey, mapKey);
    }

    /**
     * 判断Map中是否有该mapKey-mapValue
     * @param redisKey redis键，不能为空
     * @param mapKey map键，不能为空
     * @return 存在：返回true，不存在：返回false（redisKey为空或mapKey为空：返回false）
     */
    public boolean hasKeyForMap(String redisKey, String mapKey) {
        return redisDao.hasKeyForMap(redisKey, mapKey);
    }

    /**
     * 新增list
     * @param redisKey redis键，不能为空
     * @param value 值
     * @return 操作成功：返回true，操作失败：返回false（redisKey为空则返回false）
     */
    public boolean addList(String redisKey, Object value) {
        return redisDao.addList(redisKey, value);
    }

    /**
     * 新增list并设置失效时间
     * @param redisKey redis键，不能为空
     * @param value 值
     * @param time 时间(秒) time>0
     * @return 操作成功：返回true，操作失败：返回false（redisKey为空或time<=0：返回false）
     */
    public boolean addList(String redisKey, Object value, long time) {
        return redisDao.addList(redisKey, value, time);
    }

    /**
     * 新增list（追加list）
     * @param redisKey redis键，不能为空
     * @param valueList 值
     * @return 操作成功：返回true，操作失败：返回false（redisKey为空则返回false）
     */
    public boolean addList(String redisKey, List<Object> valueList) {
        return redisDao.addList(redisKey, valueList);
    }

    /**
     * 新增list并设置失效时间（追加list）
     * @param redisKey redis键，不能为空
     * @param valueList 值
     * @param time 时间(秒)
     * @return 操作成功：返回true，操作失败：返回false（redisKey为空或time<=0：返回false）
     */
    public boolean addList(String redisKey, List<Object> valueList, long time) {
        return redisDao.addList(redisKey, valueList, time);
    }

    /**
     * 获取list
     * @param redisKey redis键，不能为空
     * @return 查询成功：返回list，查询失败：返回null（redisKey为空则返回null）
     */
    public List<Object> getList(String redisKey) {
        return redisDao.getList(redisKey);
    }

    /**
     * 获取list（通过索引下标）
     * @param redisKey redis键，不能为空
     * @param start 开始位置
     * @param end 结束位置
     * @return 查询成功：返回list，查询失败：返回null（start-end：0到-1代表所有值，redisKey为空则返回null）
     */
    public List<Object> getList(String redisKey,long start, long end) {
        return redisDao.getList(redisKey, start, end);
    }

    /**
     * 获取list中单个值（通过索引下标）
     * @param redisKey redis键，不能为空
     * @param index 索引下标，index>=0时（0：表头，1：第二个元素），index<0时（-1：表尾，-2：倒数第二个元素）
     * @return 查询成功：返回list中单个值，查询失败：返回null（redisKey为空则返回null）
     */
    public Object getListIndex(String redisKey,long index) {
        return redisDao.getListIndex(redisKey, index);
    }

    /**
     * 获取list长度大小
     * @param redisKey redis键，不能为空
     * @return 查询成功：返回list长度大小，查询失败：返回0（redisKey为空则返回0）
     */
    public long getListSize(String redisKey) {
        return redisDao.getListSize(redisKey);
    }

    /**
     * 修改list中的某条数据（通过索引下标）
     * @param redisKey redis键，不能为空
     * @param index 索引下标
     * @param value 新值
     * @return 操作成功：返回true，操作失败：返回false（redisKey为空则返回false）
     */
    public boolean updateListIndex(String redisKey, long index, Object value) {
        return redisDao.updateListIndex(redisKey, index, value);
    }

    /**
     * 保存list开始下标和结束下标之间的元素（包括开始下标和结束下标）
     * @param redisKey redis键
     * @param start 开始下标，大于0
     * @param end 结束下标，大于0
     * @return 操作成功：返回true，操作失败：返回false（redisKey为空则返回false，start或end小于0返回false）
     */
    public boolean trimList(String redisKey, long start, long end) {
        return redisDao.trimList(redisKey, start, end);
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
        return redisDao.trimList(redisKey, start, end, time);
    }

}
