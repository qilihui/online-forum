package com.qilihui.forum.service.impl;

import com.qilihui.forum.mapper.IndexImageDao;
import com.qilihui.forum.pojo.IndexImage;
import com.qilihui.forum.service.IndexImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author qilihui
 * @date 2021/4/21 22:20
 */
@Service
public class IndexImageServiceImpl implements IndexImageService {

    private final IndexImageDao indexImageDao;

    @Autowired
    public IndexImageServiceImpl(IndexImageDao indexImageDao) {
        this.indexImageDao = indexImageDao;
    }

    @Override
    public List<IndexImage> getImage() {
        return indexImageDao.selectAll();
    }

    @Override
    public List<IndexImage> getAll() {
        return indexImageDao.selectAll();
    }

    @Override
    public void del(int id) {
        indexImageDao.deleteByPrimaryKey(id);
    }

    @Override
    public IndexImage insert(String url) {
        IndexImage indexImage = new IndexImage();
        indexImage.setUrl(url);
        indexImageDao.insert(indexImage);
        return indexImage;
    }
}
