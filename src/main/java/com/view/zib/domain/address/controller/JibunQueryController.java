package com.view.zib.domain.address.controller;

import com.view.zib.domain.address.controller.response.JibunSearchResponse;
import com.view.zib.domain.address.facade.JibunQueryFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/jibuns")
@RestController
public class JibunQueryController {

    private final JibunQueryFacade jibunQueryFacade;


    @GetMapping("/search-by-utmk")
    public List<JibunSearchResponse> searchByUtmk(BigDecimal utmkX, BigDecimal utmkY, BigDecimal utmkXSpan, BigDecimal utmkYSpan) {
        return jibunQueryFacade.findAddressesInUtmk(utmkX, utmkY, utmkXSpan, utmkYSpan);
    }
}
