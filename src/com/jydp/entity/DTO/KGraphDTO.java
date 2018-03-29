package com.jydp.entity.DTO;

import com.jydp.entity.DO.kgraph.*;

import java.util.Map;

/**
 * k线图统计数据结果集
 * @author whx
 */
public class KGraphDTO {

    private int currencyId;  //币种Id
    private Map<Long, KGraphFiveMinutesDO> fiveMap;//五分钟节点
    private Map<Long, KGraphFifteenMinutesDO> fifteenMap;//十五分钟节点
    private Map<Long, KGraphHalfhourDO> halfhourMap;//三十分钟节点
    private Map<Long, KGraphOneHoursDO> oneHoursMap;//一小时节点
    private Map<Long, KGraphFourHoursDO> fourHoursMap;//四小时节点
    private Map<Long, KGraphOneDayDO> oneDayMap;//一天节点
    private Map<Long, KGraphOneWeekDO> oneWeekMap;//一周节点

    /**
     *
     * @return
     */
    public int getCurrencyId() {
        return currencyId;
    }

    /**
     *
     * @param currencyId
     */
    public void setCurrencyId(int currencyId) {
        this.currencyId = currencyId;
    }

    /**
     *五分钟节点
     * @return 五分钟节点
     */
    public Map<Long, KGraphFiveMinutesDO> getFiveMap() {
        return fiveMap;
    }

    /**
     *五分钟节点
     * @param fiveMap 五分钟节点
     */
    public void setFiveMap(Map<Long, KGraphFiveMinutesDO> fiveMap) {
        this.fiveMap = fiveMap;
    }

    /**
     *十五分钟节点
     * @return 十五分钟节点
     */
    public Map<Long, KGraphFifteenMinutesDO> getFifteenMap() {
        return fifteenMap;
    }

    /**
     *十五分钟节点
     * @param fifteenMap
     */
    public void setFifteenMap(Map<Long, KGraphFifteenMinutesDO> fifteenMap) {
        this.fifteenMap = fifteenMap;
    }

    /**
     *三十分钟节点
     * @return 三十分钟节点
     */
    public Map<Long, KGraphHalfhourDO> getHalfhourMap() {
        return halfhourMap;
    }

    /**
     *三十分钟节点
     * @param halfhourMap
     */
    public void setHalfhourMap(Map<Long, KGraphHalfhourDO> halfhourMap) {
        this.halfhourMap = halfhourMap;
    }

    /**
     *一小时节点
     * @return 一小时节点
     */
    public Map<Long, KGraphOneHoursDO> getOneHoursMap() {
        return oneHoursMap;
    }

    /**
     *一小时节点
     * @param oneHoursMap
     */
    public void setOneHoursMap(Map<Long, KGraphOneHoursDO> oneHoursMap) {
        this.oneHoursMap = oneHoursMap;
    }

    /**
     *四小时节点
     * @return 四小时节点
     */
    public Map<Long, KGraphFourHoursDO> getFourHoursMap() {
        return fourHoursMap;
    }

    /**
     *四小时节点
     * @param fourHoursMap
     */
    public void setFourHoursMap(Map<Long, KGraphFourHoursDO> fourHoursMap) {
        this.fourHoursMap = fourHoursMap;
    }

    /**
     *一天节点
     * @return 一天节点
     */
    public Map<Long, KGraphOneDayDO> getOneDayMap() {
        return oneDayMap;
    }

    /**
     *一天节点
     * @param oneDayMap
     */
    public void setOneDayMap(Map<Long, KGraphOneDayDO> oneDayMap) {
        this.oneDayMap = oneDayMap;
    }

    /**
     *一周节点
     * @return 一周节点
     */
    public Map<Long, KGraphOneWeekDO> getOneWeekMap() {
        return oneWeekMap;
    }

    /**
     *一周节点
     * @param oneWeekMap
     */
    public void setOneWeekMap(Map<Long, KGraphOneWeekDO> oneWeekMap) {
        this.oneWeekMap = oneWeekMap;
    }
}
