package com.blog.dao.ds1;

import com.blog.entity.Blogtype;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author dev_guo
 * @since 2021-04-27
 */
public interface BlogtypeMapper extends BaseMapper<Blogtype> {
    /**
     * 查询每个分类下的blog总数
     * @return
     * @throws Exception
     */
    List<Blogtype> getBlogTypeNameAndBlogCount() throws Exception;
}
