package com.blog.elastic;

import com.alibaba.fastjson.JSON;
import com.blog.entity.Blog;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.lucene.index.IndexWriter;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.index.reindex.UpdateByQueryRequestBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.data.elasticsearch.core.query.UpdateQuery;
import org.springframework.stereotype.Component;
import org.elasticsearch.common.text.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
//        ObjectMapper mapper = new ObjectMapper();
        String userJson = JSON.toJSONString(blog);
        request.source(userJson, XContentType.JSON);
        IndexResponse response = client.index(request, RequestOptions.DEFAULT);
        client.close();
        return response.getResult();
    }

    /**
     * 批量添加
     * @param blogs
     * @return
     * @throws IOException
     */
    public Object bluckBlog(List<Blog> blogs) throws IOException {
        //批量入Es
        BulkRequest bulkRequest = new BulkRequest();
        bulkRequest.timeout("2m");
        for (Blog item:blogs) {
            Integer id = item.getId();
            IndexRequest request = new IndexRequest("blog_index");
            request.id(String.valueOf(id));
            bulkRequest.add(
                    request.source(JSON.toJSONString(item), XContentType.JSON)
            );
        }
        BulkResponse bulkResponse =  client.bulk(bulkRequest, RequestOptions.DEFAULT);
        return !bulkResponse.hasFailures();
    }

    /**
     *
     * @param id
     * @param docs
     * @throws IOException
     */
    public void editBlog(String id, String docs) throws IOException {
//        // 修改数据
        UpdateRequest updateRequest = new UpdateRequest(index,id);
        updateRequest.timeout("1s");
        updateRequest.doc(docs, XContentType.JSON);
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
    /**
     *
     * @param keyword
     * @param pageNo
     * @param pageSize
     * @return 搜索
     * @throws IOException
     */
    public List<Map<String,Object>> searchPage(String keyword, int pageNo, int pageSize) throws IOException {
        if (pageNo<0){
            pageNo=0;
        }
        //搜索
        SearchRequest searchRequest = new SearchRequest("blog_index");
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
//        System.out.println(pageNo);
//        System.out.println(pageSize);
        sourceBuilder.from(pageNo);
        sourceBuilder.size(pageSize);
        //精准匹配
        MatchQueryBuilder termQueryBuilder = QueryBuilders.matchQuery("title", keyword);
        //定义高亮查询
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        //设置需要高亮的字段
        highlightBuilder.field("title")
                // 设置前缀、后缀
                .preTags("<font color='red'>")
                .postTags("</font>");
        //组装
        sourceBuilder.query(termQueryBuilder);
        sourceBuilder.highlighter(highlightBuilder);
        //执行搜索
        searchRequest.source(sourceBuilder);
        SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
        ArrayList<Map<String,Object>> list = new ArrayList<>();
        for (SearchHit documentFields : searchResponse.getHits().getHits()) {
            //高亮
            Map<String, HighlightField> highlightFields = documentFields.getHighlightFields();
            HighlightField nameHighlight = highlightFields.get("title");
            Map<String, Object> sourceAsMap = documentFields.getSourceAsMap();
            if (nameHighlight != null) {
               Text[] fragments = nameHighlight.getFragments();
                String _title = "";
                for (Text text : fragments) {
                    _title += text;
                }
                sourceAsMap.put("title", _title);
                list.add(sourceAsMap);
            }
        }
        return list;
    }
}
