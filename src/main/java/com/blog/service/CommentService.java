package com.blog.service;

import com.blog.entity.Comment;
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
public interface CommentService extends IService<Comment> {

        /**
         * 根据博客id查询该博客下的评论列表
         * @param id
         * @param state
         * @return
         * @throws Exception
         */
        List<Comment> findCommentByBlogId(int id, int state) throws Exception;

        /**
         * 根据博客id删除评论
         * @param blogId
         * @return
         * @throws Exception
         */
        int deleteCommentByBlogId(int blogId) throws Exception;
}
