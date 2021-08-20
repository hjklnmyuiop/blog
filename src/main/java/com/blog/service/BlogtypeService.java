package com.blog.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.blog.entity.Blogtype;
import com.baomidou.mybatisplus.extension.service.IService;


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


    /**
     * 新增博客
     * @param blogtype
     * @return
     * @throws Exception
     */
    int addBlogtype(Blogtype blogtype) throws Exception;

    IPage<Blogtype> findBlogTypeByPage(IPage<Blogtype> page, Blogtype blogtype);
}
