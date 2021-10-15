package com.qilihui.forum.controller;

import com.qilihui.forum.common.ForumResult;
import com.qilihui.forum.common.LayuiFlowResult;
import com.qilihui.forum.config.ContentNoLegalException;
import com.qilihui.forum.pojo.Comment;
import com.qilihui.forum.pojo.UserInfo;
import com.qilihui.forum.pojo.UserRate;
import com.qilihui.forum.pojo.vo.CommentVO;
import com.qilihui.forum.service.CommentService;
import com.qilihui.forum.service.UserInfoService;
import com.qilihui.forum.service.UserRateService;
import com.qilihui.forum.util.CommonUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 评论Controller
 */
@Controller
public class CommentController {

    private final CommentService commentService;

    private final UserInfoService userInfoService;


    private final UserRateService userRateService;

    @Autowired
    public CommentController(CommentService commentService, UserInfoService userInfoService, UserRateService userRateService) {
        this.commentService = commentService;
        this.userInfoService = userInfoService;
        this.userRateService = userRateService;
    }

    /**
     * 新增评论
     *
     * @param username   评论用户名
     * @param comment    评论内容
     * @param questionId 问题ID
     * @return 封装
     */
    @PostMapping("/comment")
    @ResponseBody
    public ForumResult commentOnQuestion(
            @RequestParam("username") String username,
            @RequestParam("comment") String comment,
            @RequestParam("questionId") Integer questionId) {

        //后端数据校验
        if (username == null)
            return new ForumResult(400, "用户信息不存在", null);
        else if (comment == null || "".equals(comment.trim()))
            return new ForumResult(400, "评论内容不能为空", null);
        else if (questionId == null) {
            return new ForumResult(400, "关联问题信息不存在", null);
        }

        //插入评论信息
        try {
            Comment com = commentService.insertCommentByQuestionId(username, comment, questionId);
            return com == null ? new ForumResult(500, "评论失败，非法操作", null) : new ForumResult(200, "操作成功", com);
        } catch (ContentNoLegalException e) {
            return new ForumResult(400, "评论内容包含敏感词或不合法", null);
        } catch (Exception e) {
            e.printStackTrace();
            return new ForumResult(500, "评论失败，请稍后再试", null);
        }
    }

    /**
     * 根据问题ID查询问题一级评论信息
     *
     * @param questionId 问题ID
     * @return 包装结果
     */
    @GetMapping("/getCommentsByQuestionId")
    @ResponseBody
    public LayuiFlowResult getCommentsByQuestionId(@RequestParam("page") Integer page,
                                                   @RequestParam("questionId") Integer questionId) {
        //后端数据校验
        if (questionId == null) return new LayuiFlowResult(400, "关联问题失效", null, 0);

        //流式加载分页请求
        PageHelper.startPage(page, 5);
        List<Comment> comments = commentService.queryCommentByQuestionId(questionId);
        PageInfo<Comment> pageInfo = new PageInfo<>(comments);

        if (comments.size() == 0) return new LayuiFlowResult(200, "该问题下没有评论", null, 0);

        List<CommentVO> res = new ArrayList<>();
        for (Comment comment : pageInfo.getList()) {
            res.add(convertCommentToVO(comment));
        }

        return new LayuiFlowResult(200, "查询成功", res, pageInfo.getPages());
    }

    /**
     * 将Comment转换成前端VO
     *
     * @param comment 评论
     * @return 前端VO
     */
    private CommentVO convertCommentToVO(Comment comment) {
        CommentVO vo = new CommentVO();
        vo.setUid(comment.getUid());
        vo.setCId(comment.getCId());
        vo.setQuestionId(comment.getQuestionId());
        vo.setTime(comment.getTime());
        vo.setComment(comment.getComment());

        UserInfo info = userInfoService.selectUserInfoByUid(comment.getUid());
        vo.setUname(info.getUsername());
        //获取评论者的头像信息
        vo.setAvatar(info.getAvatar());

        //工作/学校信息
        if (StringUtils.isEmpty(info.getCompany()) && StringUtils.isEmpty(info.getUniversity())) {
            vo.setInfo("&nbsp;");
        }

        if (!StringUtils.isEmpty(info.getCompany()) && !StringUtils.isEmpty(info.getUniversity())) {
            if (!StringUtils.isEmpty(info.getJobTitle())) {
                vo.setInfo(info.getCompany() + "&nbsp;·&nbsp;" + info.getJobTitle());
            } else {
                vo.setInfo(info.getCompany());
            }
        }

        if (StringUtils.isEmpty(info.getCompany())) {
            vo.setInfo(info.getUniversity());
        } else {
            if (!StringUtils.isEmpty(info.getJobTitle())) {
                vo.setInfo(info.getCompany() + "&nbsp;·&nbsp;" + info.getJobTitle());
            } else {
                vo.setInfo(info.getCompany());
            }
        }

        //判断用户积分等级
        UserRate userRate = userRateService.selectRateById(comment.getUid());
        if (userRate == null) {
            vo.setRate("暂无");
            vo.setRateScore(0);
        } else {
            vo.setRateScore(userRate.getRate());
            vo.setRate(CommonUtils.userRate(userRate.getRate()));
        }
        return vo;
    }
}
