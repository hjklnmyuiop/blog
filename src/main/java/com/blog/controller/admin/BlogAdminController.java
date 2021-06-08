package com.blog.controller.admin;

import com.alibaba.fastjson.JSON;
import com.blog.entity.Blog;
import com.blog.lucene.BlogIndex;
import com.blog.service.BlogService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("admin/blog")
public class BlogAdminController {
    @Resource
    private BlogService blogService;
    @Resource
    private BlogIndex blogIndex;

    @RequestMapping("writeBlog")
    public String writeBlog(){
        return "admin/writeBlog";
    }
    /**
     * 发布博客
     * @param blog
     * @return
     */
    @ResponseBody
    @PostMapping("/addBlog")
    public String addBlog(Blog blog){
        Map<String,Object> map = new HashMap<String, Object>();
        try {
            //调用新增博客的方法
            int count = blogService.addBlog(blog);
            blogIndex.addIndex(blog);
            if(count>0){
                map.put("success",true);//发布成功
            }else {
                map.put("success",false);//发布失败
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return JSON.toJSONString(map);
    }
}
