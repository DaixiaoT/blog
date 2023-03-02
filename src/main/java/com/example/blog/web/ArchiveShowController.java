package com.example.blog.web;

import com.example.blog.dao.CountRepository;
import com.example.blog.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ArchiveShowController {
    @Autowired
    private CountRepository countRepository;

    @Autowired
    private BlogService blogService;
    private Long count;
    @GetMapping("/archives")
    public String archives(Model model){
        count=countRepository.findArchiveCount(1L);
        count++;
        model.addAttribute("count",count);
        countRepository.updateArchiveCount(1L);
        model.addAttribute("archiveMap",blogService.archiveBlog());
        model.addAttribute("blogCount",blogService.countBlog());
        return "archives";
    }
}
