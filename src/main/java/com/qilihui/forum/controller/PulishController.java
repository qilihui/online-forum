package com.qilihui.forum.controller;

import com.qilihui.forum.common.ForumResult;
import com.qilihui.forum.config.RateException;
import com.qilihui.forum.pojo.Question;
import com.qilihui.forum.pojo.User;
import com.qilihui.forum.service.*;
import com.qilihui.forum.util.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 发布问题Controller
 */
@Controller
public class PulishController {

    private final UserService userService;

    private final UserInfoService userInfoService;

    private final QuestionService questionService;

    private final TagService tagService;

    private final QuestionTagService questionTagService;

    @Autowired
    public PulishController(UserService userService, QuestionService questionService, TagService tagService, QuestionTagService questionTagService, UserInfoService userInfoService) {
        this.userService = userService;
        this.questionService = questionService;
        this.tagService = tagService;
        this.questionTagService = questionTagService;
        this.userInfoService = userInfoService;
    }

    @GetMapping("/publish")
    public String index() {
        return "user/publish";
    }

    /**
     * 发布问题
     *
     * @param title       标题
     * @param description 描述
     * @param tag         标签
     * @param request     req
     * @return 通用包装结果
     */
    @PostMapping("/publish")
    @ResponseBody
    public ForumResult publish(@RequestParam("title") String title,
                               @RequestParam("description") String description,
                               @RequestParam("tag") String tag,
                               @RequestParam("ding") Integer ding,
                               HttpServletRequest request) {
        /*
            后端校验：
                1.标题不能为空，中间不能有空格，最大字符串不能超过50个
                2.发布问题不能为空，此处需要将html标签去除
                3.标签不能为空，标签不能存在重复，最大标签个数为10个
         */
        if (title == null || "".equals(title)) return new ForumResult(400, "标题不能为空哦", null);
        if (tag == null || "".equals(tag)) return new ForumResult(400, "标签不能为空哦", null);
        if (tag.contains("，")) return new ForumResult(400, "请使用英文逗号分隔标签哦", null);
        if (CommonUtils.Html2Text(description).equals("")) {
            return new ForumResult(400, "内容不能为空哦", null);
        }

        if (title.indexOf(" ") > 0) return new ForumResult(400, "标题中不能存在空格", null);
        if (title.length() > 50) return new ForumResult(400, "标题必须在50个字符以内", null);

        //校验tag中不能存在特殊字符
        String regex = "[+,#-x22]+";
        String reg = "[^x00-xff]";
//        if(!tag.matches(regex) && !tag.matches(reg)) return new ForumResult(400,"请不要输入除+,#-之外的特殊字符哦",null);
        if (tag.contains(",")) {
            String[] tags = tag.split(",");
            if (tags.length > 10) {
                return new ForumResult(400, "标签最大限制为10个哦", null);
            }

            for (int i = 0; i < tags.length - 1; i++) {
                for (int j = i + 1; j < tags.length; j++) {
                    if (tags[i].equalsIgnoreCase(tags[j])) {
                        return new ForumResult(400, "同一个标签不能重复添加哦", null);
                    }
                }
            }
        }

        //发帖
        Question ques = new Question();
        ques.setTitle(title);
        ques.setDescription(description);
        ques.setTag(tag);

        //添加发帖人信息
        String username = (String) request.getSession().getAttribute("username");
        ques.setPublisherId(userInfoService.getUserInfoByName(username).getUId());

        //发帖时间
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        ques.setGmtCreate(sdf.format(date));

        //初始化默认修改时间
        ques.setGmtModified(sdf.format(date));
        ques.setLikeCount(0);
        ques.setViewCount(0);
        ques.setCommentCount(0);

        //标签
        ques.setTag(tag);

        //插入数据库
        boolean b = false;
        try {
            b = questionService.insertQuestion(ques, ding);
        } catch (RateException e) {
            return ForumResult.build(400, e.getMessage());
        }
        if (b) {
            return ForumResult.ok();
        }
        return ForumResult.build(400, "您的标题、文章或标签中包含敏感词汇，请修改后再提交！！！");
    }


    /**
     * 编辑问题信息
     *
     * @param questionId  问题ID
     * @param description 问题内容
     * @param tag         标签信息
     * @return 通用包装结果
     */
    @PutMapping("/editQuestionInfo")
    @ResponseBody
    public ForumResult editQuestionInfo(@RequestParam("id") Integer questionId,
                                        @RequestParam("description") String description,
                                        @RequestParam("tag") String tag) {
        //后台校验不能为空
        if (questionId == null || StringUtils.isEmpty(description) || StringUtils.isEmpty(tag)) {
            return new ForumResult(400, "修改信息不能为空", null);
        }

        //校验问题ID是否存在
        Question question = questionService.selectByPrimaryKey(questionId);
        if (question == null) {
            return new ForumResult(400, "该问题不存在！", null);
        }

        //插入数据库
        int result = questionService.updateQuestionInfoByIdSelective(questionId, description, tag);
        System.out.println(result);

        if (result == -1) {
            return new ForumResult(400, "内容包含敏感词汇!", null);
        } else if (result > 0) {
            return new ForumResult(200, "修改成功", null);
        } else {
            return new ForumResult(500, "系统错误，请稍后再试", null);
        }
    }

    /**
     * 删除问题（status：1）
     *
     * @param questionId 问题ID
     * @return 通用包装结果
     */
    @PostMapping("/deleteQuestion")
    @ResponseBody
    public ForumResult deleteQuestion(@RequestParam("questionId") Integer questionId, HttpSession session) {

        //校验操作用户ID和问题ID是否存在
        Integer userId = (Integer) session.getAttribute("uid");
        if (null == userId) {
            return new ForumResult(400, "非法操作", null);
        }

        User user = userService.getUserByUserId(userId);
        Question question = questionService.selectByPrimaryKey(questionId);
        if (user == null || question == null)
            return new ForumResult(400, "信息不存在", null);

        //校验问题发布者与操作用户是否为同一人
        if (question.getPublisherId() != user.getUid()) {
            return new ForumResult(400, "非法操作", null);
        }

        //执行删除操作
        int i = questionService.deleteQuestion(questionId, userId);
        if (i == 1)
            return new ForumResult(200);
        return new ForumResult(400, "删除失败", null);
    }

}
