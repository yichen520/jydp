package com.jydp.controller.web;

import com.jydp.service.IJydpUserCoinOutRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * 用户币种提现记录
 * @author yk
 *
 */
@Controller
@RequestMapping("/userWeb/jydpUserCoinOutRecord")
@Scope(value="prototype")
public class JydpUserCoinOutRecordController {

    /** JYDP用户币种转出记录 */
    @Autowired
    private IJydpUserCoinOutRecordService jydpUserCoinOutRecordService;

    /** 查询用户币种转出记录 */
    @RequestMapping("/show.htm")
    public String getJydpUserCoinOutRecord(HttpServletRequest request){
        return "";
    }
}
