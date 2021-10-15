package com.qilihui.forum.service.impl;

import com.qilihui.forum.common.ForumResult;
import com.qilihui.forum.mapper.QuestionMapper;
import com.qilihui.forum.mapper.QuestionReportMapper;
import com.qilihui.forum.mapper.UserInfoMapper;
import com.qilihui.forum.pojo.Question;
import com.qilihui.forum.pojo.QuestionReport;
import com.qilihui.forum.pojo.UserInfo;
import com.qilihui.forum.pojo.vo.ReportNoticeVo;
import com.qilihui.forum.service.QuestionReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 问题举报管理Service
 */
@Service
public class QuestionReportServiceImpl implements QuestionReportService {

    @Autowired
    private QuestionReportMapper questionReportMapper;

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private UserInfoMapper userInfoMapper;

    /**
     * 处理问题举报
     *
     * @param username    举报用户名
     * @param rUsername   被举报用户名
     * @param rQuestionId 被举报问题ID
     * @param reason      举报理由
     * @return 通用返回结果
     */
    @Override
    @Transactional
    public ForumResult processReport(String username, String rUsername, Integer rQuestionId, String reason) {
        //数据为空已校验
        UserInfo userInfo = userInfoMapper.selectUserInfoByName(username);
        UserInfo rUserInfo = userInfoMapper.selectUserInfoByName(rUsername);
        Question question = questionMapper.selectByPrimaryKey(rQuestionId);
        if (userInfo == null || rUserInfo == null || question == null) {
            return new ForumResult(400, "举报信息为空", null);
        }

        QuestionReport report = questionReportMapper.selectReportByUserIdAndQuestionId(userInfo.getUId(), question.getId());
        if (report != null) {
            return new ForumResult(500, "您已经举报过该问题，请耐心等待我们的反馈!", null);
        }
        //插入数据库
        QuestionReport questionReport = new QuestionReport();
        questionReport.setUserId(userInfo.getUId());
        questionReport.setRUserId(rUserInfo.getUId());
        questionReport.setRQuestionId(question.getId());
        questionReport.setReportReason(reason);
        questionReport.setIsProcess(0);   //点击举报后，表示未处理
        questionReport.setProcessResult("暂未处理");

        int i = questionReportMapper.insertQuestionReport(questionReport);
        return i > 0 ? ForumResult.ok() : new ForumResult(500, "系统错误，请稍后再试", null);
    }

    /**
     * 用于前台展示举报和被举报信息
     *
     * @param username
     * @return
     */
    @Override
    public List<ReportNoticeVo> showRes(String username) {
        UserInfo userInfo = userInfoMapper.selectUserInfoByName(username);
        int uid = userInfo.getUId();
        List<QuestionReport> questionReports = questionReportMapper.queryAllReportWithUidAndRUid(uid);
        List<ReportNoticeVo> reportNoticeVoArrayList = new ArrayList<>();
        for (QuestionReport q : questionReports) {
            ReportNoticeVo vo = ReportNoticeVo.builder().id(q.getId()).questionId(q.getRQuestionId()).uid(uid).status(q.getIsProcess()).build();
            vo.setIsWhistleblowers(uid == q.getUserId());
            vo.setTitle(questionMapper.selectByPrimaryKeyWithReport(q.getRQuestionId()).getTitle());
            reportNoticeVoArrayList.add(vo);
        }
        return reportNoticeVoArrayList;
    }
}
