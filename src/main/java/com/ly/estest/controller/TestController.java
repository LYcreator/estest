package com.ly.estest.controller;

import com.ly.estest.service.DocumentService;
import com.ly.estest.service.IndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ：LY
 * @date ：2021/4/23 15:08
 * @description ：TestController
 */

@RestController
@RequestMapping("/es")
public class TestController {

    @Autowired
    private IndexService indexService;

    @Autowired
    private DocumentService documentService;

    @RequestMapping("/createIndex")
    public void createIndex(){
        indexService.createIndex();
    }

    @RequestMapping("/createdoc")
    public void createdoc(){
        documentService.addDocument();
    }
}
