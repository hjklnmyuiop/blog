package com.blog.controller.admin;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.blog.elastic.BlogEs;
import com.blog.entity.Blog;
import com.blog.service.BlogService;
import com.blog.service.CommentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
    private BlogEs blogEs;
    @Resource
    private CommentService commentService;

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
            blogEs.addBlog(blog);
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
    @RequestMapping("blogManage")
    public String blogManage(){
        return "admin/blogManage";
    }
    @ResponseBody
    @RequestMapping("/list")
    public String list(Blog blog,Integer page,Integer rows){
        Map<String,Object> map= new HashMap<>();
        //创建分页对下，指定每页
        Page<Blog> pageInfo = new Page<Blog>(page,rows);
        IPage<Blog> blogIPage =  blogService.findBlogByPage(pageInfo,blog);
        map.put("total",blogIPage.getTotal());
        map.put("rows",blogIPage.getRecords());//数据类别
        return JSON.toJSONString(map);
    }

    @ResponseBody
    @RequestMapping("/delete")
    public String delete(String ids) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            //由于支持批量删除，前台页面传入的参数是一个由多个id拼接成的字符串（例如：87,86,85），需要进行组装成数组
            String [] blogIds =  ids.split(",");
            //循环删除
            for (int i = 0; i < blogIds.length; i++) {
                //调用删除评论的方法
                commentService.deleteCommentByBlogId(Integer.parseInt(blogIds[i]));
                //调用删除博客的方法
                int count = blogService.deleteBlogById(Integer.parseInt(blogIds[i]));
                if(count>0) {
                    map.put("success", true);//成功
                    //调用删除lucene的方法
                    blogEs.deleteIndex(blogIds[i]);
                }
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            map.put("success", false);//失败
        }
        return JSON.toJSONString(map);
    }


    @RequestMapping("/toUpdateBlog")
    public String toUpdateBlog(int blogId, Model model){
        //将当前博客id存放到模型中
        model.addAttribute("blogId",blogId);//因为修改博客页面存在一个隐藏域保存博客id
        return "admin/modifyBlog";
    }

    @ResponseBody
    @RequestMapping("/findBlogById")
    public Object findBlogById(int blogId){
        try {
            Blog blog = blogService.findBlogById(blogId);
            return  blog;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    @ResponseBody
    @RequestMapping("/updateBlog")
    public String updateBlog(Blog blog){
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            //调用修改博客信息的方法
            int count = blogService.updateBlog(blog);
            //更新索引库信息
            String id=String.valueOf(blog.getId());
//            ObjectMapper mapper = new ObjectMapper();
            Blog blog1 = blogService.findBlogById(blog.getId());
            blogEs.editBlog(id, JSON.toJSONString(blog1));
//            blogEs.editBlog(id,JSON.toJSONString(blog1));
            if(count>0){//修改成功
                //保存修改成功的状态
                map.put("success",true);
            }else{
                //保存修改失败的状态
                map.put("success",false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return JSON.toJSONString(map);
    }
}
