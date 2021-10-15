package com.qilihui.forum.service;

import com.qilihui.forum.pojo.IndexImage;

import java.util.List;

/**
 * @author qilihui
 * @date 2021/4/21 22:20
 */
public interface IndexImageService {
    List<IndexImage> getImage();

    List<IndexImage> getAll();

    void del(int id);

    IndexImage insert(String url);
}
