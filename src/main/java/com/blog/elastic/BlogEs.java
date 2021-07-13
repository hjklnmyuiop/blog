package com.blog.elastic;

import com.alibaba.fastjson.JSON;
import com.blog.entity.Blog;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.lucene.index.IndexWriter;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.reindex.UpdateByQueryRequestBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.data.elasticsearch.core.query.UpdateQuery;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class BlogEs {
    @Autowired
    @Qualifier("restHighLevelClient")
    private RestHighLevelClient client;

    private String index="blog_index";

    public Object addBlog(Blog blog) throws IOException {
        /* 插入数据 */
        IndexRequest request = new IndexRequest();
        Integer id = blog.getId();
        request.index(index).id(String.valueOf(id));
        // 向ES插入数据，必须将数据转换位JSON格式
        ObjectMapper mapper = new ObjectMapper();
        String userJson = mapper.writeValueAsString(blog);
        request.source(userJson, XContentType.JSON);
        IndexResponse response = client.index(request, RequestOptions.DEFAULT);
        client.close();
        return response.getResult();
    }

    /**
     *
     * @param blog
     * @return
     * @throws IOException
     */
    public void editBlog(Blog blog) throws IOException {
//        // 修改数据
//        UpdateRequest request = new UpdateRequest();
//        request.index(index).id((String.valueOf(blog.getId())));
//        request.doc(blog,XContentType.JSON );
////        request.doc(XContentType.JSON, "sex", "女");
//        UpdateResponse response = client.update(request, RequestOptions.DEFAULT);
//        System.out.println("tttasd");
//
//        client.close();
//        return response.getResult();
        UpdateRequest updateRequest = new UpdateRequest(index,String.valueOf(blog.getId()));
        updateRequest.timeout("1s");
        updateRequest.doc(JSON.toJSONString(blog), XContentType.JSON);
        UpdateResponse updateResponse = client.update(updateRequest, RequestOptions.DEFAULT);
        System.out.println(updateResponse.status());
    }
    /**
     * 删除博客下的索引
     * @param blogId
     * @throws Exception
     */
    public Object deleteIndex(String blogId) throws Exception{

        DeleteRequest deleteRequest = new DeleteRequest();
        deleteRequest.index(index).id(blogId);
        DeleteResponse response = client.delete(deleteRequest, RequestOptions.DEFAULT);
        System.out.println(response.toString());
        client.close();
        return response.getResult();
    }
}
