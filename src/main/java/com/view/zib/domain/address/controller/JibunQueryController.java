package com.view.zib.domain.address.controller;

import com.view.zib.domain.address.controller.response.ApartmentResponse;
import com.view.zib.domain.address.controller.response.JibunSearchResponse;
import com.view.zib.domain.address.facade.JibunQueryFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/jibuns")
@RestController
public class JibunQueryController {

    private final JibunQueryFacade jibunQueryFacade;


    @GetMapping("/search-by-utmk")
    public List<JibunSearchResponse> searchByUtmk(
            BigDecimal minX,
            BigDecimal minY,
            BigDecimal maxX,
            BigDecimal maxY,
            @RequestParam(required = false) List<Long> jibunIds
    ) {
        return jibunQueryFacade.findAddressesInUtmk(minX, minY, maxX, maxY, jibunIds);
    }

    @GetMapping("/{jibunId}")
    public ApartmentResponse findJibunById(@PathVariable Long jibunId) {
        return jibunQueryFacade.findJibunById(jibunId);
    }
}
