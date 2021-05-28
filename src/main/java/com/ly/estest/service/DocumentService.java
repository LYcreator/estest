package com.ly.estest.service;

import com.alibaba.fastjson.JSON;
import com.ly.estest.entity.UserInfo;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;

/**
 * @author ：LY
 * @date ：2021/4/23 13:55
 * @description ：文档基本操作
 */

@Slf4j
@Service
public class DocumentService {

    @Autowired
    private RestHighLevelClient client;

    /**
     * 增加document
     */
    public void addDocument(){
        try{
            IndexRequest request = new IndexRequest("mydlq-user","doc","1");
            UserInfo user = new UserInfo();
            user.setName("何敬殷");
            user.setAddress("上海");
            user.setRemark("擅长Java");
            user.setAge(24);
            user.setCreateTime(new Date());
            user.setBirthDate("1997-01-01");
            user.setSalary(8500f);
            byte[] bytes = JSON.toJSONBytes(user);
            request.source(bytes, XContentType.JSON);
            IndexResponse response = client.index(request, RequestOptions.DEFAULT);
            log.info("创建状态：{}", response.status());
        }catch(Exception e){
            log.error("", e);
        }
    }
    /**
     * 获取文档信息
     */
    public void getDocument() {
        try {
            // 获取请求对象
            GetRequest getRequest = new GetRequest("mydlq-user", "doc", "1");
            // 获取文档信息
            GetResponse getResponse = client.get(getRequest, RequestOptions.DEFAULT);
            // 将 JSON 转换成对象
            if (getResponse.isExists()) {
                UserInfo userInfo = JSON.parseObject(getResponse.getSourceAsBytes(), UserInfo.class);
                log.info("员工信息：{}", userInfo);
            }
        } catch (IOException e) {
            log.error("", e);
        }
    }

    /**
     * 更新文档信息
     */
    public void updateDocument() {
        try {
            // 创建索引请求对象
            UpdateRequest updateRequest = new UpdateRequest("mydlq-user", "doc", "1");
            // 设置员工更新信息
            UserInfo userInfo = new UserInfo();
            userInfo.setSalary(200.00f);
            userInfo.setAddress("北京市海淀区");
            // 将对象转换为 byte 数组
            byte[] json = JSON.toJSONBytes(userInfo);
            // 设置更新文档内容
            updateRequest.doc(json, XContentType.JSON);
            // 执行更新文档
            UpdateResponse response = client.update(updateRequest, RequestOptions.DEFAULT);
            log.info("创建状态：{}", response.status());
        } catch (Exception e) {
            log.error("", e);
        }
    }

    /**
     * 删除文档信息
     */
    public void deleteDocument() {
        try {
            // 创建删除请求对象
            DeleteRequest deleteRequest = new DeleteRequest("mydlq-user", "doc", "1");
            // 执行删除文档
            DeleteResponse response = client.delete(deleteRequest, RequestOptions.DEFAULT);
            log.info("删除状态：{}", response.status());
        } catch (IOException e) {
            log.error("", e);
        }
    }


}
