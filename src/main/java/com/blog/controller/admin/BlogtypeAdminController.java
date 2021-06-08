package com.blog.controller.admin;

import com.blog.service.BlogtypeService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
@RequestMapping("admin/blogtype")
public class BlogtypeAdminController {
    @Resource
    private BlogtypeService blogtypeService;

    /**
     * 查询博客分类下拉列表
     * @return
     */
    @ResponseBody
    @RequestMapping("typeItem")
    public String typeItem(){
        try {
            String typeList = blogtypeService.getBlogTypeNameAndBlogCount();
            return typeList;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
