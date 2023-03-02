package com.example.blog.service;

import com.example.blog.dao.NodeRepository;
import org.aspectj.lang.annotation.AfterReturning;
import org.fnlp.PartsOfSpeechTag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@Service
public class NodeServiceImpl implements NodeService {

    @Autowired
    private NodeRepository nodeRepository;

    PartsOfSpeechTag partsOfSpeechTag = new PartsOfSpeechTag();

    List<String> noun = new ArrayList<>();
    @Override
    public void CreateNode(String content) throws Exception {
        PartsOfSpeechTag partsOfSpeechTag = new PartsOfSpeechTag();
        List<String> list = new ArrayList<>();

        List<String> verbAdj=new ArrayList<>();
//        System.out.println(partsOfSpeechTag.PartsOfSpeech(content));
        list= List.of(partsOfSpeechTag.PartsOfSpeech(content).split(" "));
        String first = null,last = null;
        int flag=0;

//        System.out.println(list);
        for(String str:list){

            if(List.of(str.split("/")).size()>=2){
//                System.out.println(List.of(str.split("/")).get(1));
                String head=List.of(str.split("/")).get(1);
                String body=List.of(str.split("/")).get(0);
                if(!isChinese(body)) {
                    continue;
                }
                else if(head.equals("名词")){
                    if (flag==0){
                        noun.add(body);
                        nodeRepository.SaveNode(body);
                        first=body;
                        flag++;
                    }
                    else if (flag==1){
                        noun.add(body);
                        nodeRepository.SaveNode(body);
                        last=body;
                        flag++;
                    }
                    else if (flag==2){
                        String rel="";
                        for(String str1:verbAdj){
                            rel+=str1;
                        }
                        nodeRepository.createRelationWithExistNode(first,rel,last);
                        verbAdj.clear();
                        flag=0;
                    }

                }
                else if(head.equals("形谓词")||head.equals("时态词")||head.equals("时间段语")||head.equals("机构名")||head.equals("实体名")||head.equals("动词")||head.equals("形容词")){
                    verbAdj.add(body);
                }

            }

//            System.out.println(str);

        }

    }
    private Boolean isAbc(String tap) {
                Pattern pattern = Pattern.compile("([a-z]|[A-Z])*");
                return pattern.matcher(tap).matches();
    }
    private Boolean isChinese(String str){
        //判断是否为纯中文，不是返回false
        String regex3 = "[\\u4e00-\\u9fa5]+";
        boolean result5 = str.matches(regex3);
        return result5;
    }
}
