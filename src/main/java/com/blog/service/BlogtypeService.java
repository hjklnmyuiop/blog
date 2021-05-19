package com.blog.service;

import com.blog.entity.Blogtype;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author dev_guo
 * @since 2021-04-27
 */
public interface BlogtypeService extends IService<Blogtype> {
    /**
     * 查询每个分类下的blog总数
     * @return
     * @throws Exception
     */
    String getBlogTypeNameAndBlogCount() throws Exception;
}
