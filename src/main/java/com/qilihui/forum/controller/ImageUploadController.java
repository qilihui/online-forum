package com.qilihui.forum.controller;

import com.qilihui.forum.common.EasyWebImageUploadResult;
import com.qilihui.forum.common.ForumResult;
import com.qilihui.forum.common.LayEditUploadImageResult;
import com.qilihui.forum.pojo.UploadImage;
import com.qilihui.forum.service.UserInfoService;
import com.qilihui.forum.util.ImageUploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;

/**
 * 图片上传Controller
 */
@Controller
public class ImageUploadController {

    private final UserInfoService userInfoService;
    private final ImageUploadUtil imageUploadUtil;

    @Autowired
    public ImageUploadController(UserInfoService userInfoService, ImageUploadUtil imageUploadUtil) {
        this.userInfoService = userInfoService;
        this.imageUploadUtil = imageUploadUtil;
    }

    @PostMapping("/newUpload")
    @ResponseBody
    public ForumResult newUpload(@RequestParam("file") MultipartFile multipartFile,
                                 HttpServletRequest request, Model model) throws IOException {

        String username = (String) request.getSession().getAttribute("username");

        //获取文件的名称
        String fileName = multipartFile.getOriginalFilename();

        //判断有无后缀
        assert fileName != null;
        if (fileName.lastIndexOf(".") < 0)
            return new ForumResult(500, "上传图片格式不正确", null);

        //获取文件后缀
        String prefix = fileName.substring(fileName.lastIndexOf("."));

        //如果不是图片
        if (!prefix.equalsIgnoreCase(".jpg") && !prefix.equalsIgnoreCase(".jpeg") && !prefix.equalsIgnoreCase(".svg") && !prefix.equalsIgnoreCase(".gif") && !prefix.equalsIgnoreCase(".png")) {
            return new ForumResult(500, "上传图片格式不正确", null);
        }

        //使用uuid作为文件名，防止生成的临时文件重复
        final File excelFile = File.createTempFile("imagesFile-" + System.currentTimeMillis(), prefix);

        //将Multifile转换成File
        multipartFile.transferTo(excelFile);


        //上传文件
        String imageName = null;
        try {
            imageName = imageUploadUtil.upload(excelFile);
        } catch (Exception e) {
            return new ForumResult(500, e.getMessage(), null);
        }

        //程序结束时，删除临时文件
        deleteFile(excelFile);

        //存入图片名称，用于网页显示
        model.addAttribute("imageName", imageName);

        //更新数据库
        userInfoService.updateUserAvatar(imageName, username);

        //返回成功信息
        return new ForumResult(200, "头像更换成功", imageName);
    }

    /**
     * 上传头像
     */
    @PostMapping("/upload")
    @ResponseBody
    public ForumResult upload(@RequestParam("file") MultipartFile multipartFile, @RequestParam("username") String username, Model model) throws Exception {
        //获取文件的名称
        String fileName = multipartFile.getOriginalFilename();

        //判断有无后缀
        assert fileName != null;
        if (fileName.lastIndexOf(".") < 0)
            return new ForumResult(500, "上传图片格式不正确", null);

        //获取文件后缀
        String prefix = fileName.substring(fileName.lastIndexOf("."));

        //如果不是图片
        if (!prefix.equalsIgnoreCase(".jpg") && !prefix.equalsIgnoreCase(".jpeg") && !prefix.equalsIgnoreCase(".svg") && !prefix.equalsIgnoreCase(".gif") && !prefix.equalsIgnoreCase(".png")) {
            return new ForumResult(500, "上传图片格式不正确", null);
        }

        //使用uuid作为文件名，防止生成的临时文件重复
        final File excelFile = File.createTempFile("imagesFile-" + System.currentTimeMillis(), prefix);

        //将Multifile转换成File
        multipartFile.transferTo(excelFile);

        //上传文件
        String imageName = null;
        try {
            imageName = imageUploadUtil.upload(excelFile);
        } catch (Exception e) {
            return new ForumResult(500, e.getMessage(), null);
        }

        //程序结束时，删除临时文件
        deleteFile(excelFile);

        //存入图片名称，用于网页显示
        model.addAttribute("imageName", imageName);

        //更新数据库
        userInfoService.updateUserAvatar(imageName, username);

        //返回成功信息
        return new ForumResult(200, "头像更换成功", imageName);
    }

    /**
     * 删除临时文件
     *
     * @param files 临时文件，可变参数
     */
    private void deleteFile(File... files) {
        for (File file : files) {
            if (file.exists()) {
                file.delete();
            }
        }
    }

    /**
     * 富文本编辑器中上传图片信息
     *
     * @param multipartFile 文件
     * @return 包装结果
     */
    @PostMapping("/uploadImage")
    @ResponseBody
    public LayEditUploadImageResult uploadImage(@RequestParam("file") MultipartFile multipartFile) throws IOException {
        //获取文件的名称
        String fileName = multipartFile.getOriginalFilename();

        //判断有无后缀
        assert fileName != null;
        if (fileName.lastIndexOf(".") < 0)
            return new LayEditUploadImageResult(1, "上传图片格式不正确", null);

        //获取文件后缀
        String prefix = fileName.substring(fileName.lastIndexOf("."));

        //如果不是图片
        if (!prefix.equalsIgnoreCase(".jpg") && !prefix.equalsIgnoreCase(".jpeg") && !prefix.equalsIgnoreCase(".svg") && !prefix.equalsIgnoreCase(".gif") && !prefix.equalsIgnoreCase(".png")) {
            return new LayEditUploadImageResult(1, "图片格式不正确", null);
        }

        //使用uuid作为文件名，防止生成的临时文件重复
        String iName = "imagesFile-" + System.currentTimeMillis();

        final File excelFile = File.createTempFile(iName, prefix);

        //将Multifile转换成File
        multipartFile.transferTo(excelFile);


        //上传文件
        String imageName = null;
        try {
            imageName = imageUploadUtil.upload(excelFile);
        } catch (Exception e) {
            return new LayEditUploadImageResult(-1, e.getMessage(), null);
        }

        //程序结束时，删除临时文件
        deleteFile(excelFile);

        String src = imageName;

        UploadImage uploadImage = new UploadImage();
        uploadImage.setSrc(src);
        uploadImage.setTitle(iName);

        return new LayEditUploadImageResult(0, "图片上传成功", uploadImage);

    }

    /**
     * 问题发布时的图片上传
     *
     * @param multipartFile 文件，限制为图片
     * @return 包装结果
     * @throws IOException 异常
     */
    @PostMapping("/uploadQuestionImages")
    @ResponseBody
    public EasyWebImageUploadResult publishQuestion(@RequestParam("ques") MultipartFile multipartFile) throws IOException {
        //获取文件的名称
        String fileName = multipartFile.getOriginalFilename();

        //获取文件后缀
        assert fileName != null;
        String prefix = fileName.substring(fileName.lastIndexOf("."));

        //使用uuid作为文件名，防止生成的临时文件重复
        String iName = "imagesFile-" + System.currentTimeMillis();

        final File excelFile = File.createTempFile(iName, prefix);

        //将Multifile转换成File
        multipartFile.transferTo(excelFile);

        String imageName;
        try {

            //上传文件
            imageName = imageUploadUtil.upload(excelFile);

        } catch (Exception e) {
            e.printStackTrace();

            return new EasyWebImageUploadResult(null);
        }

        //程序结束时，删除临时文件
        deleteFile(excelFile);


        String[] res = {imageName};

        return new EasyWebImageUploadResult(imageName);
    }
}

