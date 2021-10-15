package com.qilihui.forum.admin.controller;

import com.qilihui.forum.admin.common.LayuiTableResult;
import com.qilihui.forum.common.ForumResult;
import com.qilihui.forum.pojo.IndexImage;
import com.qilihui.forum.service.IndexImageService;
import com.qilihui.forum.util.ImageUploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Random;

/**
 * @author qilihui
 * @date 2021/4/27 17:08
 */
@Controller
@RequestMapping("/admin/img")
public class AdminImageController {
    private final IndexImageService indexImageService;

    private final ImageUploadUtil uploadUtil;

    @Autowired
    public AdminImageController(IndexImageService indexImageService, ImageUploadUtil uploadUtil) {
        this.indexImageService = indexImageService;
        this.uploadUtil = uploadUtil;
    }


    @RequestMapping("/page")
    public String pageImage() {
        return "admin/shuffling_figure";
    }

    @RequestMapping("/getAll")
    @ResponseBody
    public LayuiTableResult getImages() {
        List<IndexImage> all = indexImageService.getAll();
        if (all == null) {
            return new LayuiTableResult(0, "查询成功", 0, null);
        }
        return new LayuiTableResult(0, "查询成功", all.size(), all);
    }

    @RequestMapping("/del")
    @ResponseBody
    public ForumResult del(@RequestParam("id") Integer id) {
        if (id == null) {
            return ForumResult.build(400, "id不存在");
        }
        indexImageService.del(id);
        return ForumResult.ok();
    }

    @RequestMapping("/upload")
    @ResponseBody
    public ForumResult upload(@RequestParam("file") MultipartFile multipartFile) {
        Random random = new Random();
        String url = null;
        try {
            File file = File.createTempFile("upload", String.valueOf(System.currentTimeMillis() + random.nextInt()));
            multipartFile.transferTo(file);
            url = uploadUtil.upload(file);
        } catch (Exception e) {
            return ForumResult.build(400, e.getMessage());
        }
        if (url == null) {
            return ForumResult.build(400, "服务器错误");
        }
        indexImageService.insert(url);
        return ForumResult.ok(url);
    }
}
