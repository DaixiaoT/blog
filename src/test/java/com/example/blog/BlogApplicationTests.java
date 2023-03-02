package com.example.blog;

import com.example.blog.dao.NodeRepository;
import com.example.blog.service.NodeServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BlogApplicationTests {

    @Autowired
    private NodeServiceImpl nodeService;

    @Autowired
    private NodeRepository nodeRepository;
    @Test
    void contextLoads() throws Exception {
//        nodeRepository.init();
//        nodeService.CreateNode("今天学校有很多美食，我吃了一下午的东西");
//        System.out.println("'''");
//        nodeRepository.createRelationWithExistNode("代晓涛","喜欢","计算机");
    }

}
