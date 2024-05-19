package com.view.zib.domain.map.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
public class MapController {

    @Value("${api.kakao-map.key}")
    private String appkey;

    @GetMapping("/map")
    public String map(Model model) {
        model.addAttribute("appkey", appkey);

        return "map";
    }
}
