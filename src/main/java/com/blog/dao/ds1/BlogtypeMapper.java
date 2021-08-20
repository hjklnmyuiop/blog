package com.blog.dao.ds1;

import com.baomidou.mybatisplus.core.metadata.IPage;

import com.blog.entity.Blog;
import com.blog.entity.Blogtype;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author dev_guo
 * @since 2021-04-27
 */
@Repository
public interface BlogtypeMapper extends BaseMapper<Blogtype> {




    /**
     * 查询每个分类下的blog总数
     * @return
     * @throws Exception
     */
    List<Blogtype> getBlogTypeNameAndBlogCount() throws Exception;

    /**
     * 分页查询博客信息
     * @param page
     * @param blogtype
     * @return
     */
    IPage<Blogtype> findBlogTypeByPage(@Param("page") IPage<Blogtype> page, @Param("blogtype") Blogtype blogtype);
}
