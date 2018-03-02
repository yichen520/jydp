package com.jydp.entity.DTO;

/**
 * 系统自动开关记录
 * @author whx
 */
public class SystemSwitchRecordDTO {

    private int id; //记录Id
    private int switchCode; //开关编码，详见《系统开关编码表》
    private int switchStatus; //开关状态，1：开启，2：关闭（默认1：开启）

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

}
