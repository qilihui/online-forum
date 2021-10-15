package com.qilihui.forum.admin.controller.VO;

import lombok.Builder;
import lombok.Data;

/**
 * @author qilihui
 * @date 2021/3/18 15:23
 */
@Data
@Builder
public class AdminTagsVo {
    private Integer id;
    private String name;
    private Integer categoryId;//分类id
    private String categoryName;//分类名
    private Boolean isSystemTag;//1-系统内置标签, 0-用户添加的标签
}
