package com.qilihui.forum.pojo.vo;

import lombok.Builder;
import lombok.Data;

/**
 * @author qilihui
 * @date 2021/3/18 12:51
 */
@Data
@Builder
public class ReportNoticeVo {
    private Integer id;
    private Integer uid;
    private Integer questionId;
    private String title;//文章标题
    private Integer status;//状态：0-未处理, 1-同意举报, 2-驳回
    private Boolean isWhistleblowers;//true-举报者, false-被举报者
}
