package com.qilihui.forum.service;

import com.qilihui.forum.common.ForumResult;
import com.qilihui.forum.pojo.vo.ReportNoticeVo;

import java.util.List;

/**
 * 举报管理Service
 */
public interface QuestionReportService {

    ForumResult processReport(String username, String rUsername, Integer rQuestionId, String reason);

    List<ReportNoticeVo> showRes(String username);
}
