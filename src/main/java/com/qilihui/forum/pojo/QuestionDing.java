package com.qilihui.forum.pojo;

import java.io.Serializable;
import lombok.Data;

/**
 * tb_question_ding
 * @author 
 */
@Data
public class QuestionDing implements Serializable {
    private Integer id;

    /**
     * 问题id
     */
    private Integer qId;

    /**
     * 置顶结束时间
     */
    private String endTime;

    /**
     * 状态 0-正常 1-不查询
     */
    private Integer status;

    private static final long serialVersionUID = 1L;
}