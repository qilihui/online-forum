package com.qilihui.forum.admin.controller;

import com.qilihui.forum.admin.common.LayuiTableResult;
import com.qilihui.forum.admin.controller.VO.AdminTagsVo;
import com.qilihui.forum.admin.service.AdminTagsService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qilihui.forum.common.ForumResult;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author qilihui
 * @date 2021/3/17 10:31
 */
@Controller
@RequestMapping("/admin")
public class AdminTagsController {
    private final AdminTagsService adminTagsService;

    @Autowired
    public AdminTagsController(AdminTagsService adminTagsService) {
        this.adminTagsService = adminTagsService;
    }

    @GetMapping("/tags")
    @ResponseBody
    public LayuiTableResult getAllTag(@RequestParam("page") Integer pageNum,
                                      @RequestParam("limit") Integer limit) {
        Page<AdminTagsVo> page = PageHelper.startPage(pageNum, limit);
        List<AdminTagsVo> list = adminTagsService.getAllTag();
        PageInfo<AdminTagsVo> pageInfo = new PageInfo<>(page);
        return new LayuiTableResult(0, "查询成功", (int) pageInfo.getTotal(), list);
    }

    @ResponseBody
    @PostMapping("/setSystemTag")
    public ForumResult setIsOrNo(@RequestParam("id") Integer id, @RequestParam("is") Boolean is) {
        adminTagsService.setTag(id, is);
        return ForumResult.ok();
    }

//    @PostMapping("/setCa")
//    @ResponseBody
//    public ForumResult setCa(@RequestParam("ca") Integer caId, @RequestParam("id") Integer id) {
//        System.out.println(id + " " + caId);
//        return ForumResult.ok();
//    }
}
