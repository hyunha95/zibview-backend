package com.view.zib.domain.map.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
public class MapController {

    private final String appkey;

    public MapController(@Value("${api.kakao-map.key}") String appkey) {
        this.appkey = appkey;
    }

    @GetMapping("/map")
    public String map(double latitude, double longitude, Model model) {
        model.addAttribute("appkey", appkey);
        model.addAttribute("latitude", latitude);
        model.addAttribute("longitude", longitude);

        return "map";
    }
}
