package com.view.zib.domain.map.controller;

import com.view.zib.domain.address.domain.Coordinate;
import com.view.zib.domain.address.service.AddressQueryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Slf4j
@Controller
public class MapController {

    private final AddressQueryService addressQueryService;
    private final String appkey;

    public MapController(AddressQueryService addressQueryService, @Value("${api.kakao-map.key}") String appkey) {
        this.addressQueryService = addressQueryService;
        this.appkey = appkey;
    }

    @GetMapping("/map/{postId}")
    public String map(@PathVariable(name = "postId") Long postId, Model model) {
        Coordinate coordinate = addressQueryService.getCoordinateByPostId(postId);

        model.addAttribute("appkey", appkey);
        model.addAttribute("latitude", coordinate.latitude());
        model.addAttribute("longitude", coordinate.longitude());

        return "map";
    }
}
