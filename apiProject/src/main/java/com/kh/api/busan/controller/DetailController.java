package com.kh.api.busan.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class DetailController {

    @PostMapping("food/detail")
    public String detailPage(String title,
                             String lat,
                             String lng,
                             String description,
                             Model model) {
        model.addAttribute("title", title);
        model.addAttribute("lat", lat);
        model.addAttribute("lng", lng);
        String des = description.replaceAll("\n,", "").replaceAll("\r", "");
        model.addAttribute("description", des);
        
        return "busan/detail";
    }
}
