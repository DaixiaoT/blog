package com.example.blog;

import com.example.blog.dao.NodeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashSet;
@SpringBootTest
public class node_Test {
    @Autowired
    private NodeRepository nodeRepository;
    @Test
    void test(){

        HashSet<String> hashSet = new HashSet<String>();
        hashSet=nodeRepository.findLastByFirst("红船精神");
        System.out.println(hashSet);
    }
}
