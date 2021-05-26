package com.blog.service.impl;

import com.blog.entity.Blogger;
import com.blog.dao.ds1.BloggerMapper;
import com.blog.service.BloggerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author dev_guo
 * @since 2021-04-27
 */
@Service
public class BloggerServiceImpl extends ServiceImpl<BloggerMapper, Blogger> implements BloggerService {

}
