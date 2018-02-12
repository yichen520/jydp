package com.jydp.dao;

import java.util.List;
import java.util.Map;

/**
 * redis服务
 * @author whx
 */
public interface IRedisDao {

    /**
     * 判定redis中是否存在该key
     * @param redisKey redis键，不能为空
     * @return 存在：返回true，不存在：返回false（redisKey为空则返回false）
     */
    boolean hasKey(String redisKey);

    /**
     * 设置redisKey失效时间
     * @param redisKey redis键，不能为空
     * @param time 时间(秒) time>0
     * @return 操作成功：返回true，操作失败：返回false（redisKey为空或time<=0：返回false）
     */
    boolean setExpire(String redisKey, long time);

    /**
     * 获取redisKey失效时间
     * @param redisKey redis键，不能为空
     * @return 查询成功:返回失效时间(秒)，返回0：永久有效（redisKey为空则返回-1）
     */
    long getExpire(String redisKey);

    /**
     * 新增key-value（时间无限期）
     * @param redisKey redis键，不能为空
     * @param value 值
     * @return 操作成功：返回true，操作失败：返回false（redisKey为空则返回false）
     */
    boolean addValue(String redisKey, Object value);

    /**
     * 新增key-value并设置时间
     * @param redisKey redis键，不能为空
     * @param value 值
     * @param time 时间(秒) time>0
     * @return 操作成功：返回true，操作失败：返回false（redisKey为空或time<=0：返回false）
     */
    boolean addValue(String redisKey, Object value, long time);

    /**
     * 获取redis键对应的值
     * @param redisKey redis键，不能为空
     * @return 操作成功：返回键对应的值，操作失败：返回null（redisKey为空则返回null）
     */
    Object getValue(String redisKey);

    /**
     * 删除单个redisKey
     * @param redisKey redis键，不能为空
     * @return 操作成功：返回true，操作失败：返回false（redisKey为空则返回false）
     */
    boolean deleteValue(String redisKey);

    /**
     * 批量删除redisKey
     * @param redisKeyList 批量redis键，不能为空
     * @return 操作成功：返回true，操作失败：返回false（redisKeyList为空则返回false）
     */
    boolean deleteValueList(List<String> redisKeyList);

    /**
     * 新增map
     * @param redisKey redis键，不能为空
     * @param map HashMap<Object, Object>值
     * @return 操作成功：返回true，操作失败：返回false（redisKey为空则返回false）
     */
    boolean addMap(String redisKey, Map<Object, Object> map);

    /**
     * 新增map并设置失效时间
     * @param redisKey redis键，不能为空
     * @param map HashMap<Object, Object>值
     * @param time 时间(秒) time>0
     * @return 操作成功：返回true，操作失败：返回false（redisKey为空或time<=0：返回false）
     */
    boolean addMap(String redisKey, Map<Object, Object> map, long time);

    /**
     * 新增map值
     * @param redisKey redis键，不能为空
     * @param mapKey map键
     * @param mapValue map值
     * @return 操作成功：返回true，操作失败：返回false（redisKey为空则返回false）
     */
    boolean addMapValue(String redisKey, Object mapKey, Object mapValue);

    /**
     * 新增map值并设置失效时间
     * @param redisKey redis键，不能为空
     * @param mapKey map键
     * @param mapValue map值
     * @param time 时间(秒) time>0
     * @return 操作成功：返回true，操作失败：返回false（redisKey为空或time<=0：返回false）
     */
    boolean addMapValue(String redisKey, Object mapKey, Object mapValue, long time);

    /**
     * 获取Map
     * @param redisKey redis键，不能为空
     * @return 操作成功：返回HashMap值，操作失败：返回null（redisKey为空则返回空）
     */
    Map<Object, Object> getMap(String redisKey);

    /**
     * 获取Map值
     * @param redisKey redis键，不能为空
     * @param mapKey map键
     * @return 操作成功：返回HashMap值，操作失败：返回null（redisKey为空则返回空）
     */
    Object getMapValue(String redisKey, Object mapKey);

    /**
     * 删除Map中的mapValue值
     * @param redisKey redis键，不能为空
     * @param mapKey map键，不能为空，可以多个map键
     * @return 操作成功：返回true，操作失败：返回false（redisKey为空或mapKey为null：返回false）
     */
    boolean deleteMapValue(String redisKey, Object... mapKey);

    /**
     * 判断Map中是否有该mapKey-mapValue
     * @param redisKey redis键，不能为空
     * @param mapKey map键，不能为空
     * @return 存在：返回true，不存在：返回false（redisKey为空或mapKey为空：返回false）
     */
    boolean hasKeyForMap(String redisKey, String mapKey);

    /**
     * 新增list
     * @param redisKey redis键，不能为空
     * @param value 值
     * @return 操作成功：返回true，操作失败：返回false（redisKey为空则返回false）
     */
    boolean addList(String redisKey, Object value);

    /**
     * 新增list并设置失效时间
     * @param redisKey redis键，不能为空
     * @param value 值
     * @param time 时间(秒) time>0
     * @return 操作成功：返回true，操作失败：返回false（redisKey为空或time<=0：返回false）
     */
    boolean addList(String redisKey, Object value, long time);

    /**
     * 新增list（追加list）
     * @param redisKey redis键，不能为空
     * @param valueList 值
     * @return 操作成功：返回true，操作失败：返回false（redisKey为空则返回false）
     */
    boolean addList(String redisKey, List<Object> valueList);

    /**
     * 新增list并设置失效时间（追加list）
     * @param redisKey redis键，不能为空
     * @param valueList 值
     * @param time 时间(秒)
     * @return 操作成功：返回true，操作失败：返回false（redisKey为空或time<=0：返回false）
     */
    boolean addList(String redisKey, List<Object> valueList, long time);

    /**
     * 获取list
     * @param redisKey redis键，不能为空
     * @return 查询成功：返回list，查询失败：返回null（redisKey为空则返回null）
     */
    List<Object> getList(String redisKey);

    /**
     * 获取list（通过索引下标）
     * @param redisKey redis键，不能为空
     * @param start 开始位置
     * @param end 结束位置
     * @return 查询成功：返回list，查询失败：返回null（start-end：0到-1代表所有值，redisKey为空则返回null）
     */
    List<Object> getList(String redisKey, long start, long end);

    /**
     * 获取list中单个值（通过索引下标）
     * @param redisKey redis键，不能为空
     * @param index 索引下标，index>=0时（0：表头，1：第二个元素），index<0时（-1：表尾，-2：倒数第二个元素）
     * @return 查询成功：返回list中单个值，查询失败：返回null（redisKey为空则返回null）
     */
    Object getListIndex(String redisKey, long index);

    /**
     * 获取list长度大小
     * @param redisKey redis键，不能为空
     * @return 查询成功：返回list长度大小，查询失败：返回0（redisKey为空则返回0）
     */
    long getListSize(String redisKey);

    /**
     * 修改list中的某条数据（通过索引下标）
     * @param redisKey redis键，不能为空
     * @param index 索引下标
     * @param value 新值
     * @return 操作成功：返回true，操作失败：返回false（redisKey为空则返回false）
     */
    boolean updateListIndex(String redisKey, long index, Object value);

}
