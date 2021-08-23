package com.blog;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.blog.elastic.BlogEs;
import com.blog.entity.Blog;
import com.blog.service.BlogService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.*;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.*;

@SpringBootTest
class BlogApplicationTests {
    @Autowired
    @Qualifier("restHighLevelClient")
    private RestHighLevelClient client;
    @Resource
    private BlogService blogService;
    @Resource
    private BlogEs blogEs;
//    @Test
//    public void testCreateIndex() throws IOException {
//        CreateIndexRequest request =  new CreateIndexRequest("blog_index");
//        CreateIndexResponse createIndexResponse =  client.indices().create(request, RequestOptions.DEFAULT);
//        System.out.println(createIndexResponse);
//    }
//    @Test
//    public void testUpdateIndex() throws Exception {
//        Blog blogById = blogService.findBlogById(110);
//        String id=String.valueOf(blogById.getId());
//        blogEs.editBlog(id, JSON.toJSONString(blogById));
//    }
//    @Test
//    public void testDelIndex() throws IOException {
//        DeleteIndexRequest request = new DeleteIndexRequest("blog_index");
//        AcknowledgedResponse response = client.indices().delete(request, RequestOptions.DEFAULT);
//        // 响应状态
//        System.out.println(response.isAcknowledged());
//        client.close();
//    }
//    @Test
//    public void testDocInsert() throws Exception {
//        /* 插入数据 */
//        IndexRequest request = new IndexRequest();
//        //分页查询
//        int current=1;
//        int size =1000;
//        Blog blog = new Blog();
//        Page<Blog> page = new Page<Blog>(current,size);
//        IPage<Blog> blogIPage = blogService.findBlogByPage(page,blog);
//        //获取博客数据列表
//        List<Blog> blogList = blogIPage.getRecords();
//        blogEs.bluckBlog(blogList);
////        for (Blog item:blogList) {
////            System.out.println(item);
////            Integer id = item.getId();
////            request.index("blog_index").id(String.valueOf(id));
////            // 向ES插入数据，必须将数据转换位JSON格式
////            ObjectMapper mapper = new ObjectMapper();
////            String userJson = mapper.writeValueAsString(item);
////            System.out.println(userJson);
////            request.source(userJson, XContentType.JSON);
////            IndexResponse response = client.index(request, RequestOptions.DEFAULT);
////            System.out.println(response.getResult());
////        }
//        //查看详情
////        Blog blog = blogService.findBlogById(39);
////        blog.setReleaseDateStr(dates.format(blog.getReleaseDate(),'yyyy-MM-dd HH:mm:ss'));
//
////        client.close();
//    }
//    @Test
//    public void testGetDoc() throws IOException {
//        // 查询数据
//        GetRequest request = new GetRequest();
//        request.index("blog_index").id("39");
//        GetResponse response = client.get(request, RequestOptions.DEFAULT);
//        System.out.println(response.getSourceAsString());
//        client.close();
//    }
//    @Test
//    public void testSearchLight() throws IOException {
//        // 查询数据
//        String keyword = "java";
//        int pageNo = 4;
//        int pageSize = 2;
//        List<Map<String, Object>> list = blogEs.searchPage(keyword, pageNo, pageSize);
//        System.out.println(list);
//    }
//    @Test
//    public void testSearchDoc() throws IOException {
//        // 1. 查询索引的所有数据
//        SearchRequest request = new SearchRequest();
//        request.indices("blog_index");
//
//        // 构造查询条件
//        SearchSourceBuilder builder = new SearchSourceBuilder();
//        builder.query(QueryBuilders.matchQuery("title","java"));
//        //        SearchSourceBuilder builder = new SearchSourceBuilder();
////        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
////
////        //boolQueryBuilder.must(QueryBuilders.matchQuery("age", 30));
////        //boolQueryBuilder.must(QueryBuilders.matchQuery("sex", "男"));
////        //boolQueryBuilder.mustNot(QueryBuilders.matchQuery("sex", "男"));
////        boolQueryBuilder.should(QueryBuilders.matchQuery("age", 30));
////        boolQueryBuilder.should(QueryBuilders.matchQuery("age", 40));
//        request.source(builder);
//
//        SearchResponse response = client.search(request, RequestOptions.DEFAULT);
//
//        SearchHits hits = response.getHits();
//
//        System.out.println(response.getTook());
//        System.out.println(hits.getTotalHits());
//        Iterator<SearchHit> iterator = hits.iterator();
//        while (iterator.hasNext()) {
//            SearchHit hit = iterator.next();
//            System.out.println(hit.getSourceAsString());
//        }
//
//        client.close();
//
//    }
    @Test
    public void jwt(){
        long time= 1000*60*60*24;
        String signature = "dev_guo";
        JwtBuilder jwtBuilder = Jwts.builder();
        String jwtToken = jwtBuilder.
                //header
                setHeaderParam("typ","JWT").
                setHeaderParam("alg","HS256")
                //payload
                .claim("name","dev_guo")
                .claim("role","admin")
                .setSubject("admin-test")
                .setExpiration(new Date(System.currentTimeMillis()+time))
                .setId(UUID.randomUUID().toString())
                .signWith(SignatureAlgorithm.HS256,signature)
                .compact();
        System.out.println(jwtToken);
    }
    @Test
    public void parse(){
        String token="eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJuYW1lIjoiZGV2X2d1byIsInJvbGUiOiJhZG1pbiIsInN1YiI6ImFkbWluLXRlc3QiLCJleHAiOjE2Mjk1MjgzMjksImp0aSI6IjM3OTZjZjQ3LWM2ZjgtNDlmZC05Y2FjLTEzZjI1MzVlOTdiYiJ9.nBdMRKzryCddL7rENXV11xB14Rpxepi-0t_7GmzCtTc";
        String signature = "dev_guo";
        JwtParser jwtParser = Jwts.parser();
        Jws<Claims> claimsJws = jwtParser.setSigningKey(signature).parseClaimsJws(token);
        Claims claims = claimsJws.getBody();
        System.out.println(claims.get("name"));
        System.out.println(claims.get("role"));
        System.out.println(claims.getId());
        System.out.println(claims.getSubject());
    }
}
