package com.ly.estest.service;

import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * @author ：LY
 * @date ：2021/4/23 10:23
 * @description ：索引基本操作
 */

@Slf4j
@Service
public class IndexService {

    @Autowired
    private RestHighLevelClient client;

    /**
     * 创建索引
     */
    public void createIndex() {
        try {
            //创建mapping
            XContentBuilder mapping = XContentFactory.jsonBuilder()
                    .startObject()
                    .field("dynamic", true)
                    .startObject("properties")
                    .startObject("name")
                    .field("type", "text")
                    .startObject("fields")
                    .startObject("keyword")
                    .field("type", "keyword")
                    .endObject()
                    .endObject()
                    .endObject()
                    .startObject("address")
                    .field("type", "text")
                    .startObject("fields")
                    .startObject("keyword")
                    .field("type", "keyword")
                    .endObject()
                    .endObject()
                    .endObject()
                    .startObject("remark")
                    .field("type", "text")
                    .startObject("fields")
                    .startObject("keyword")
                    .field("type", "keyword")
                    .endObject()
                    .endObject()
                    .endObject()
                    .startObject("age")
                    .field("type", "integer")
                    .endObject()
                    .startObject("salary")
                    .field("type", "float")
                    .endObject()
                    .startObject("birthDate")
                    .field("type", "date")
                    .field("format", "yyyy-MM-dd")
                    .endObject()
                    .startObject("createTime")
                    .field("type", "date")
                    .endObject()
                    .endObject()
                    .endObject();
            //创建索引配置
            Settings settings = Settings.builder()
                    //es的数据分片数，默认是5
                    .put("index.number_of_shards", 1)
                    //数据备份数,如果只有一台机器则设置为0
                    .put("number_of_replicas", 0)
                    .build();
            //实例化创建索引请求对象，然后设置配置和mapping
            CreateIndexRequest request = new CreateIndexRequest("mydlq-user", settings);
            request.mapping("doc", mapping);

            CreateIndexResponse response = client.indices().create(request, RequestOptions.DEFAULT);
            boolean acknowledged = response.isAcknowledged();
            log.info("是否创建成功:{}", acknowledged);
        } catch (IOException e) {
            e.printStackTrace();
            log.error("异常信息:{}", e.getMessage());
        }
    }

    /**
     * 删除索引
     */
    public void deleteIndex() {
        try {
            // 新建删除索引请求对象
            DeleteIndexRequest request = new DeleteIndexRequest("mydlq-user");
            // 执行删除索引
            AcknowledgedResponse acknowledgedResponse = client.indices().delete(request, RequestOptions.DEFAULT);
            // 判断是否删除成功
            boolean siDeleted = acknowledgedResponse.isAcknowledged();
            log.info("是否删除成功：{}", siDeleted);
        } catch (IOException e) {
            log.error("", e);
        }

    }


}