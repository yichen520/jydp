package com.jydp.entity.DO.system;

import java.sql.Timestamp;

/**
 * 系统自动开关记录
 * @author whx
 */
public class SystemSwitchRecordDO {

    private int id; //记录Id
    private int switchCode; //开关编码，详见《系统开关编码表》
    private String switchName; //开关名称
    private int switchStatus; //开关状态，1：开启，2：关闭（默认1：开启）
    private String ipAddress; //操作时的ip地址
    private int backerId; //后台管理员Id，系统自动操作填0
    private String backerAccount; //后台管理员帐号，系统自动操作填system
    private Timestamp addTime; //添加时间

    /**
     * 记录Id
     * @return 记录Id
     */
    public int getId() {
        return id;
    }

    /**
     * 记录Id
     * @param id 记录Id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * 开关编码，详见《系统开关编码表》
     * @return 开关编码，详见《系统开关编码表》
     */
    public int getSwitchCode() {
        return switchCode;
    }

    /**
     * 开关编码，详见《系统开关编码表》
     * @param switchCode 开关编码，详见《系统开关编码表》
     */
    public void setSwitchCode(int switchCode) {
        this.switchCode = switchCode;
    }

    /**
     * 开关名称
     * @return 开关名称
     */
    public String getSwitchName() {
        return switchName;
    }

    /**
     * 开关名称
     * @param switchName 开关名称
     */
    public void setSwitchName(String switchName) {
        this.switchName = switchName;
    }

    /**
     * 开关状态，1：开启，2：关闭（默认1：开启）
     * @return 开关状态，1：开启，2：关闭（默认1：开启）
     */
    public int getSwitchStatus() {
        return switchStatus;
    }

    /**
     * 开关状态，1：开启，2：关闭（默认1：开启）
     * @param switchStatus 开关状态，1：开启，2：关闭（默认1：开启）
     */
    public void setSwitchStatus(int switchStatus) {
        this.switchStatus = switchStatus;
    }

    /**
     * 操作时的ip地址
     * @return 操作时的ip地址
     */
    public String getIpAddress() {
        return ipAddress;
    }

    /**
     * 操作时的ip地址
     * @param ipAddress 操作时的ip地址
     */
    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    /**
     * 后台管理员Id，系统自动操作填0
     * @return 后台管理员Id，系统自动操作填0
     */
    public int getBackerId() {
        return backerId;
    }

    /**
     * 后台管理员Id，系统自动操作填0
     * @param backerId 后台管理员Id，系统自动操作填0
     */
    public void setBackerId(int backerId) {
        this.backerId = backerId;
    }

    /**
     * 后台管理员帐号，系统自动操作填system
     * @return 后台管理员帐号，系统自动操作填system
     */
    public String getBackerAccount() {
        return backerAccount;
    }

    /**
     * 后台管理员帐号，系统自动操作填system
     * @param backerAccount 后台管理员帐号，系统自动操作填system
     */
    public void setBackerAccount(String backerAccount) {
        this.backerAccount = backerAccount;
    }

    /**
     * 添加时间
     * @return 添加时间
     */
    public Timestamp getAddTime() {
        return addTime;
    }

    /**
     * 添加时间
     * @param addTime 添加时间
     */
    public void setAddTime(Timestamp addTime) {
        this.addTime = addTime;
    }
}
