package com.example.blog.web;


import com.example.blog.dao.CountRepository;
import com.example.blog.dao.NodeRepository;
import com.example.blog.po.Blog;
import com.example.blog.service.*;
import org.fnlp.PartsOfSpeechTag;


import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
public class IndexController {
    @Autowired
    private BlogService blogService;
    @Autowired
    private TypeService typeService;
    @Autowired
    private TagService tagService;
    @Autowired
    private CountRepository countRepository;

    @Autowired
    private NodeRepository nodeRepository;

    @Autowired
    private NodeServiceImpl nodeService;

    PartsOfSpeechTag partsOfSpeechTag = new PartsOfSpeechTag();



    //    private Long count=countRepository.findIndexCount(1);
    private Long count;
    private Long count_search;
    @GetMapping("/")
    public String index(@PageableDefault(size = 5,sort = {"updateTime"},
            direction = Sort.Direction.DESC) Pageable pageable,
                        Model model){
        count=countRepository.findIndexCount(1L);
        count++;
        model.addAttribute("count",count);
        countRepository.updateIndexCount(1L);
        model.addAttribute("page",blogService.listBlog(pageable));
        model.addAttribute("types",typeService.listTypeTop(6));
        model.addAttribute("tags",tagService.listTagTop(10));
        model.addAttribute("recommendBlogs",blogService.listRecommendBlogTop(8));
        return "index";
    }
    @PostMapping("/search")
    public String search(@PageableDefault(size = 5,sort = {"updateTime"},
            direction = Sort.Direction.DESC) Pageable pageable,@RequestParam String query,
                         Model model){
        count_search=countRepository.findSearchCount(1L);
        count_search++;
        model.addAttribute("count",count_search);
        countRepository.updateSearchCount(1L);
        model.addAttribute("page",blogService.listBlog("%"+query+"%",pageable));
        model.addAttribute("query",query);

        return "search";
    }
    @GetMapping("/blog/{id}/graph")
    public String graph(@PathVariable Long id,Model model) throws Exception {
        nodeRepository.init();
//
        model.addAttribute("blog",blogService.getAndConvert(id));

        //
        Blog blog=blogService.getBlog(id);
        String content=blog.getContent();

//        nodeService.CreateNode("电脑是工具");
//        System.out.println(partsOfSpeechTag.PartsOfSpeech("电脑是工具"));
        nodeService.CreateNode(content);

        List<JSONObject> NodeList = new ArrayList<>();
        Map<String,String> NodeMap=new HashMap<String,String>();
        List<JSONObject> RelationList = new ArrayList<>();
        Map<String,String> RelationMap=new HashMap<String,String>();
        HashSet<String> set = new HashSet<>();
        set = nodeRepository.findAllNode();
        for(String first:set){
            NodeMap.put("name",first);//map加入节点
            JSONObject jsonObjNode=new JSONObject(NodeMap);
            NodeList.add(jsonObjNode);
            NodeMap.clear();//map清空
            HashSet<String> lastSet = new HashSet<>();
            lastSet=nodeRepository.findLastByFirst(first);
            for(String last:lastSet){
                List<String> stringList=nodeRepository.findRelationByFirstAndLast(first,last);
                String relation=stringList.toString();
                RelationMap.put("source",first);
                RelationMap.put("target",last);
                RelationMap.put("relation",relation);
                JSONObject jsonObjRelation=new JSONObject(RelationMap);
                RelationList.add(jsonObjRelation);
                RelationMap.clear();
            }
        }

        model.addAttribute("list",NodeList.toString());
        model.addAttribute("links",RelationList.toString());
        model.addAttribute("count",blogService.getBlog(id).getViews());
        return "graph";
    }


    @GetMapping("/blog/{id}")
    public String blog(@PathVariable Long id,Model model){
        model.addAttribute("blog",blogService.getAndConvert(id));
        model.addAttribute("count",blogService.getBlog(id).getViews());
        return "blog";
    }

    @GetMapping("/footer/newblog")
    public String newblog(Model model){
        model.addAttribute("newblogs",blogService.listRecommendBlogTop(3));
        return "_fragments :: newblogList";
    }

//    @PostMapping("/text")
//    public String index(@RequestParam String text, Model model){
//        System.out.println(text);
//        List<JSONObject> NodeList = new ArrayList<>();
//        Map<String,String> NodeMap=new HashMap<String,String>();
//        List<JSONObject> RelationList = new ArrayList<>();
//        Map<String,String> RelationMap=new HashMap<String,String>();
//        HashSet<String> set = new HashSet<>();
//        set = nodeRepository.findAllNode();
//        for(String first:set){
//            NodeMap.put("name",first);//map加入节点
//            JSONObject jsonObjNode=new JSONObject(NodeMap);
//            NodeList.add(jsonObjNode);
//            NodeMap.clear();//map清空
//            HashSet<String> lastSet = new HashSet<>();
//            lastSet=nodeRepository.findLastByFirst(first);
//            for(String last:lastSet){
//                List<String> stringList=nodeRepository.findRelationByFirstAndLast(first,last);
//                String relation=stringList.toString();
//                RelationMap.put("source",first);
//                RelationMap.put("target",last);
//                RelationMap.put("relation",relation);
//                JSONObject jsonObjRelation=new JSONObject(RelationMap);
//                RelationList.add(jsonObjRelation);
//                RelationMap.clear();
//            }
//        }
//
//
////        map.put("name","代晓涛");
////        JSONObject jsonObj=new JSONObject(map);
////        list.add(jsonObj);
////        map.clear();
////        map.put("name","张婧仪");
////        jsonObj=new JSONObject(map);
////        list.add(jsonObj);
//
////        System.out.println(list);
////        System.out.println(jsonObj.toString());
//        model.addAttribute("list",NodeList.toString());
//        model.addAttribute("links",RelationList.toString());
//
//        return "index";
//
//    }



}
