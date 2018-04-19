package com.jydp.service.impl.user;

import com.iqmkj.utils.BigDecimalUtil;
import com.iqmkj.utils.NumberUtil;
import com.jydp.dao.IUserCurrencyNumDao;
import com.jydp.entity.DTO.BackerUserCurrencyNumDTO;
import com.jydp.entity.VO.WapUserCurrencyAssetsVO;
import com.jydp.service.IWebUserCurrencyNumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xushilong3623 on 2018/4/19.
 */

@Service
public class WebUserCurrencyNumServiceImpl implements IWebUserCurrencyNumService{

    @Autowired
    IUserCurrencyNumDao userCurrencyNumDao;

    /**
     * 查询用户币种资产
     *
     * @param userId 用户id
     * @return 查询成功，返回列表，失败返回null
     */
    @Override
    public List<WapUserCurrencyAssetsVO> listWebUserCurrencyAssets(int userId) {
        List<BackerUserCurrencyNumDTO> userCurrencyNum = userCurrencyNumDao.getUserCurrencyNumByUserIdForWeb(userId);
        if (!CollectionUtils.isEmpty(userCurrencyNum)) {
            List<WapUserCurrencyAssetsVO> userCurrencyAssets = new ArrayList<>();
            for (BackerUserCurrencyNumDTO userCurrencyNumDTO : userCurrencyNum) {
                WapUserCurrencyAssetsVO userCurrency = new WapUserCurrencyAssetsVO();

                Double currencyNumber= NumberUtil.doubleFormat(userCurrencyNumDTO.getCurrencyNumber(),4);
                Double currencyNumberLock=NumberUtil.doubleFormat(userCurrencyNumDTO.getCurrencyNumberLock(),4);
                Double totalCurrencyAssets= BigDecimalUtil.add(currencyNumber,currencyNumberLock);

                userCurrency.setCurrencyId(userCurrencyNumDTO.getCurrencyId());
                userCurrency.setCurrencyName(userCurrencyNumDTO.getCurrencyName());
                userCurrency.setCurrencyNumber(currencyNumber);
                userCurrency.setCurrencyNumberLock(currencyNumberLock);
                userCurrency.setTotalCurrencyAssets(totalCurrencyAssets);
                userCurrencyAssets.add(userCurrency);
            }
            return userCurrencyAssets;
        }
        return null;
    }
}
