package com.blog.dao.ds1;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.blog.entity.Blog;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Dev_guo
 * @since 2019-10-11
 */
@Repository
public interface BlogMapper extends BaseMapper<Blog> {

    /**
     * 查询博客日期和博客数量
     * @return
     * @throws Exception
     */
    List<Blog> findBlogDateAndCount() throws Exception;

    /**
     * 分页查询博客信息
     * @param page
     * @param blog
     * @return
     */
    IPage<Blog> findBlogByPage(@Param("page") IPage<Blog> page,@Param("blog") Blog blog);

    /**
     * 查看博客详情
     * @param id
     * @return
     * @throws Exception
     */
    Blog findBlogById(int id) throws Exception;

    /**
     * 查看上一篇博客
     * @param id
     * @return
     * @throws Exception
     */
    Blog findPrevBlog(int id) throws Exception;
    /**
     * 查看下一篇博客
     * @param id
     * @return
     * @throws Exception
     */
    Blog findNextBlog(int id) throws Exception;
}
