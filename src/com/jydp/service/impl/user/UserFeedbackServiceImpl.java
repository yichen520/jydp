package com.jydp.service.impl.user;

import com.jydp.dao.IUserFeedbackDao;
import com.jydp.service.IUserFeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Description: 意见反馈
 * Author: hht
 * Date: 2018-02-07 17:23
 */
@Service("userFeedbackService")
public class UserFeedbackServiceImpl implements IUserFeedbackService {

    /** 意见反馈 */
    @Autowired
    private IUserFeedbackDao userFeedbackDao;

}
