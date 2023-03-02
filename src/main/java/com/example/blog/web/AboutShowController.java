package com.example.blog.web;

import com.example.blog.dao.CountRepository;
import com.example.blog.dao.NodeRepository;
import com.example.blog.service.BlogService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;

@Controller
public class AboutShowController {
    @Autowired
    private CountRepository countRepository;
    @Autowired
    private BlogService blogService;

    @Autowired
    private NodeRepository nodeRepository;

    private Long count;
    @GetMapping("/about")
    public String about(Model model){
        count=countRepository.findAboutCount(1L);
        count++;
        model.addAttribute("count",count);
        countRepository.updateAboutCount(1L);
        return "about";
    }
    @PostMapping("/about/search")
    public String aboutShow(@RequestParam String name, Model model){
        System.out.println(name);

        List<JSONObject> NodeList = new ArrayList<>();
        Map<String,String> NodeMap=new HashMap<String,String>();
        List<JSONObject> RelationList = new ArrayList<>();
        Map<String,String> RelationMap=new HashMap<String,String>();
        HashSet<String> set = new HashSet<>();
        set = nodeRepository.findAllNode();
        NodeMap.put("name",name);//map加入节点
        JSONObject jsonObjNode=new JSONObject(NodeMap);
        NodeList.add(jsonObjNode);
        HashSet<String> lastSet = new HashSet<>();
        lastSet=nodeRepository.findLastByFirst(name);
        for(String last:lastSet){
            NodeMap.put("name",last);//map加入节点
            JSONObject jsonObjNode1=new JSONObject(NodeMap);
            NodeList.add(jsonObjNode1);
            List<String> stringList=nodeRepository.findRelationByFirstAndLast(name,last);
            String relation=stringList.toString();
            RelationMap.put("source",name);
            RelationMap.put("target",last);
            RelationMap.put("relation",relation);
            JSONObject jsonObjRelation=new JSONObject(RelationMap);
            RelationList.add(jsonObjRelation);
            RelationMap.clear();
        }
        model.addAttribute("list",NodeList.toString());
        model.addAttribute("links",RelationList.toString());
        System.out.println(NodeList.toString());
        System.out.println(RelationList.toString());
        return "about";
    }
//    @GetMapping("/footer/newblog")
//    public String newblog(Model model){
//        model.addAttribute("newblogs",blogService.listRecommendBlogTop(3));
//        return "_fragments :: newblogList";
//    }
}
