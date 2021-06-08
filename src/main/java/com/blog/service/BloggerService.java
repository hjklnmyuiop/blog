package com.blog.service;

import com.blog.entity.Blogger;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author dev_guo
 * @since 2021-04-27
 */
public interface BloggerService extends IService<Blogger> {


    /**
     * 根据博主名称查询博主信息
     * @param userName
     * @return
     * @throws Exception
     */
    Blogger findBloggerByUserName(String userName) throws Exception;

}

