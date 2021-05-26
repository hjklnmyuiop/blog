package com.blog.service.impl;

import com.alibaba.fastjson.JSON;
import com.blog.entity.Blogtype;
import com.blog.dao.ds1.BlogtypeMapper;
import com.blog.service.BlogtypeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author dev_guo
 * @since 2021-04-27
 */
@Service
public class BlogtypeServiceImpl extends ServiceImpl<BlogtypeMapper, Blogtype> implements BlogtypeService {

    @Resource
    private BlogtypeMapper blogtypeMapper;
    //redis对象
    @Resource
    private RedisTemplate<String,String> redisTemplate;
    @Override
    public String getBlogTypeNameAndBlogCount() throws Exception {
        //redis缓存读取
        String blogTypeInfo = redisTemplate.opsForValue().get("blog_name_count");
        //判断是否为空
        if (blogTypeInfo==null|| blogTypeInfo.equals("") || blogTypeInfo.length()==0){
            List<Blogtype> blogtypeList = blogtypeMapper.getBlogTypeNameAndBlogCount();
            blogTypeInfo = JSON.toJSONString(blogtypeList);
            //计划数据放到缓存
            redisTemplate.opsForValue().set("blog_name_count",blogTypeInfo);
        }
        return blogTypeInfo;
    }
}
