package com.qilihui.forum.pojo;

import java.io.Serializable;
import lombok.Data;

/**
 * tb_index_image
 */
@Data
public class IndexImage implements Serializable {
    private Integer id;

    private String url;

    private static final long serialVersionUID = 1L;
}