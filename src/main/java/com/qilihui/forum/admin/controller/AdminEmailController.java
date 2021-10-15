package com.qilihui.forum.admin.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qilihui.forum.admin.common.LayuiTableResult;
import com.qilihui.forum.admin.controller.VO.AdminEmailVo;
import com.qilihui.forum.pojo.UserInfo;
import com.qilihui.forum.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author qilihui
 * @date 2021/4/27 21:02
 */
@RestController
@RequestMapping("/admin/email")
public class AdminEmailController {
    private EmailService emailService;

    @Autowired
    public AdminEmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @GetMapping("/getAll")
    public LayuiTableResult getAll(@RequestParam("page") Integer pageNum,
                                   @RequestParam("limit") Integer limit) {
        PageHelper.startPage(pageNum, limit);
        List<AdminEmailVo> all = emailService.getAll();
        PageInfo<AdminEmailVo> pageInfo = new PageInfo<>(all);
        return new LayuiTableResult(0, "", (int) pageInfo.getTotal(), all);
    }
}
