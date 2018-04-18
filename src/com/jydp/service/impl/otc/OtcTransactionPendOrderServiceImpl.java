package com.jydp.service.impl.otc;

import com.iqmkj.utils.DateUtil;
import com.iqmkj.utils.NumberUtil;
import com.iqmkj.utils.StringUtil;
import com.jydp.dao.IOtcTransactionPendOrderDao;
import com.jydp.entity.BO.JsonObjectBO;
import com.jydp.entity.DO.otc.OtcTransactionPendOrderDO;
import com.jydp.entity.DO.otc.UserPaymentTypeDO;
import com.jydp.entity.DTO.OtcTransactionPendOrderDTO;
import com.jydp.entity.VO.OtcTransactionPendOrderVO;
import com.jydp.service.IOtcTransactionPendOrderService;
import com.jydp.service.IUserPaymentTypeService;
import config.SystemCommonConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.sql.Timestamp;

import java.util.List;

/**
 * 场外交易挂单记录
 * @author lgx
 */
@Service("otcTransactionPendOrderService")
public class OtcTransactionPendOrderServiceImpl implements IOtcTransactionPendOrderService {

    @Autowired
    private IOtcTransactionPendOrderDao otcTransactionPendOrderDao;

    /** 付款方式 */
    @Autowired
    private IUserPaymentTypeService userPaymentTypeService;

    /**
     * 新增场外交易挂单记录
     * @return JsonObjectBO 挂单成功与否信息
     */
    @Transactional
    public JsonObjectBO insertPendOrder(OtcTransactionPendOrderDTO otcOrderVO) {
        JsonObjectBO resultJson = new JsonObjectBO();
        Timestamp curTime = DateUtil.getCurrentTime();
        //业务执行状态
        boolean excuteSuccess = true;
        String pendingOrderNo = SystemCommonConfig.TRANSACTION_OTC_PEND_ORDER +
                DateUtil.longToTimeStr(curTime.getTime(), DateUtil.dateFormat10) +
                NumberUtil.createNumberStr(10);
        //插入挂单表
        OtcTransactionPendOrderDO otcTransactionPendOrder = new OtcTransactionPendOrderDO();
        otcTransactionPendOrder.setAddTime(curTime);//添加时间
        otcTransactionPendOrder.setOrderType(otcOrderVO.getOrderType());//挂单类型:1：出售，2：回购
        otcTransactionPendOrder.setOtcPendingOrderNo(pendingOrderNo);//主键 订单号
        otcTransactionPendOrder.setUserId(otcOrderVO.getUserId());//用户id
        otcTransactionPendOrder.setUserAccount(otcOrderVO.getUserAccount());//用户名称
        otcTransactionPendOrder.setCurrencyId(otcOrderVO.getCurrencyId());//币种id
        otcTransactionPendOrder.setCurrencyName(otcOrderVO.getCurrencyName());//币种名称
        otcTransactionPendOrder.setPendingRatio(otcOrderVO.getPendingRatio());//挂单比例
        otcTransactionPendOrder.setMinNumber(otcOrderVO.getMinNumber());//最小限额
        otcTransactionPendOrder.setMaxNumber(otcOrderVO.getMaxNumber());//最大限额
        excuteSuccess = otcTransactionPendOrderDao.insertOtcTransactionPendOrder(otcTransactionPendOrder);
        //插入收款方式表 关联挂单
        //根据填的付款方式数据  决定插入几条数据
        if(otcOrderVO.getOrderType() == 1){
        if (excuteSuccess) {
            if (StringUtil.isNotNull(otcOrderVO.getBankAccount())) {//银行卡
                UserPaymentTypeDO userPaymentTypA = new UserPaymentTypeDO();
                userPaymentTypA.setUserId(otcOrderVO.getUserId());//用户
                userPaymentTypA.setOtcPendingOrderNo(pendingOrderNo);//场外交易挂单号
                userPaymentTypA.setPaymentType(1);//类型 银行卡AA
                userPaymentTypA.setPaymentAccount(otcOrderVO.getBankAccount()); //账号
                userPaymentTypA.setBankBranch(otcOrderVO.getBankBranch());//银行支行
                userPaymentTypA.setBankName(otcOrderVO.getBankName());//银行名称
                userPaymentTypA.setPaymentName(otcOrderVO.getPaymentName());//预留名
                userPaymentTypA.setPaymentPhone(otcOrderVO.getPaymentPhone());//预留手机
                userPaymentTypA.setAddTime(curTime);//添加时间
                UserPaymentTypeDO userPaymentTypeDOA = userPaymentTypeService.insertUserPaymentType(userPaymentTypA);
                if (userPaymentTypeDOA == null) {
                    excuteSuccess = false;
                }
            }
        }
        if (excuteSuccess) {
            if (StringUtil.isNotNull(otcOrderVO.getAlipayAccount())) {//支付宝
                UserPaymentTypeDO userPaymentTypB = new UserPaymentTypeDO();
                userPaymentTypB.setUserId(otcOrderVO.getUserId());//用户
                userPaymentTypB.setOtcPendingOrderNo(pendingOrderNo);//场外交易挂单号
                userPaymentTypB.setPaymentType(2);
                userPaymentTypB.setPaymentAccount(otcOrderVO.getAlipayAccount()); //账号
                userPaymentTypB.setPaymentImage(otcOrderVO.getAlipayImage());//二维码地址
                userPaymentTypB.setAddTime(curTime);//添加时间
                UserPaymentTypeDO userPaymentTypeDOB = userPaymentTypeService.insertUserPaymentType(userPaymentTypB);
                if (userPaymentTypeDOB == null) {
                    excuteSuccess = false;
                }
            }
        }
        if (excuteSuccess) {
            if (StringUtil.isNotNull(otcOrderVO.getWechatAccount())) {//微信
                UserPaymentTypeDO userPaymentTypC = new UserPaymentTypeDO();
                userPaymentTypC.setUserId(otcOrderVO.getUserId());//用户
                userPaymentTypC.setOtcPendingOrderNo(pendingOrderNo);//场外交易挂单号
                userPaymentTypC.setPaymentType(3);
                userPaymentTypC.setPaymentAccount(otcOrderVO.getWechatAccount()); //账号
                userPaymentTypC.setPaymentImage(otcOrderVO.getWechatImage());//二维码地址
                userPaymentTypC.setAddTime(curTime);//添加时间
                UserPaymentTypeDO userPaymentTypeDOC = userPaymentTypeService.insertUserPaymentType(userPaymentTypC);
                if (userPaymentTypeDOC == null) {
                    excuteSuccess = false;
                }
            }
        }
    }
        if(!excuteSuccess){
            //数据回滚
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        resultJson.setCode(1);
        resultJson.setMessage("发布成功");
     return resultJson;
    }

    /**
     * 根据记录号查询挂单记录信息
     * @param orderNo 记录号
     * @return 查询成功：返回记录信息, 查询失败或者没有相应记录：返回null
     */
    public OtcTransactionPendOrderDO getOtcTransactionPendOrderByOrderNo(String orderNo) {
        return otcTransactionPendOrderDao.getOtcTransactionPendOrderByOrderNo(orderNo);
    }

    /**
     * 根据记录号查询挂单记录信息（有经销商名字）
     * @param orderNo 记录号
     * @return 查询成功：返回记录信息, 查询失败或者没有相应记录：返回null
     */
    public OtcTransactionPendOrderVO getOtcTransactionPendOrder(String orderNo){
        return otcTransactionPendOrderDao.getOtcTransactionPendOrder(orderNo);
    }

    /**
     * 按条件查询全部场外交易挂单总数
     * @param currencyId 币种Id
     * @param orderType 挂单类型:1：出售，2：回购
     * @param area 地区
     * @return 查询成功：返回记录总条数，查询失败：返回0
     */
    @Override
    public int countOtcTransactionPendOrder(int currencyId, int orderType, String area){
        return otcTransactionPendOrderDao.countOtcTransactionPendOrder(currencyId, orderType, area);
    }

    /**
     * 查询全部场外交易挂单列表
     * @param currencyId 币种Id
     * @param orderType 挂单类型:1：出售，2：回购
     * @param area 地区
     * @param pageNumber 当前页数
     * @param pageSize 每页条数
     * @return 查询成功：返回记录信息，查询失败：返回null
     */
    @Override
    public List<OtcTransactionPendOrderVO> getOtcTransactionPendOrderlist(int currencyId, int orderType, String area, int pageNumber, int pageSize) {
        return otcTransactionPendOrderDao.getOtcTransactionPendOrderlist(currencyId,orderType,area,pageNumber,pageSize);
    }

    /**
     * 根据用户id查询场外可用交易挂单列表
     * @param userId 用户id
     * @return 查询成功：返回记录信息，查询失败：返回null
     */
    public List<OtcTransactionPendOrderDO> getOtcTransactionPendOrderByUserId(int userId){
        return otcTransactionPendOrderDao.getOtcTransactionPendOrderByUserId(userId);
    }

    /**
     * 根据订单号删除该用户订单
     * @param userId 用户id
     * @param otcPendingOrderNo 订单id
     * @param updateTime 更新时间
     * @return 新增成功：返回true, 新增失败：返回false
     */
    public boolean deleteOtcTransactionPendOrderByOtcPendingOrderNo(int userId, String otcPendingOrderNo, Timestamp updateTime){
        return otcTransactionPendOrderDao.deleteOtcTransactionPendOrderByOtcPendingOrderNo(userId, otcPendingOrderNo, updateTime);
    }

    /**
     * 根据用户id查询场外可用交易挂单列表总数
     * @param userId 用户id
     * @return 查询成功：返回记录信息数，查询失败：返回null
     */
    public int countOtcTransactionPendOrderByUserId(int userId){
        return otcTransactionPendOrderDao.countOtcTransactionPendOrderByUserId(userId);
    }

    /**
     * 根据用户id查询场外可用交易挂单列表
     * @param userId 用户id
     * @param pageNumber 当前页数
     * @param pageSize 每页条数
     * @return 查询成功：返回记录信息，查询失败：返回null
     */
    public List<OtcTransactionPendOrderDO> listOtcTransactionPendOrder(int userId, int pageNumber, int pageSize){
        return otcTransactionPendOrderDao.listOtcTransactionPendOrder(userId, pageNumber, pageSize);
    }
}
