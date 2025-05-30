package com.view.zib.domain.address.controller;

import com.view.zib.domain.address.controller.response.ApartmentResponse;
import com.view.zib.domain.address.controller.response.JibunSearchResponse;
import com.view.zib.domain.address.controller.response.TransactionApartmentResponse;
import com.view.zib.domain.address.facade.JibunQueryFacade;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Slf4j
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
            String anonymousUserUUID,
            @RequestParam(required = false) Integer zoomLevel
    ) {
        return jibunQueryFacade.findAddressesInUtmk(minX, minY, maxX, maxY, zoomLevel, UUID.fromString(anonymousUserUUID));
    }

    @GetMapping("/{jibunId}")
    public ApartmentResponse findJibunById(@PathVariable Long jibunId) {
        return jibunQueryFacade.findJibunById(jibunId);
    }

    @GetMapping("/management-no/{managementNo}")
    public ApartmentResponse findJibunByManagementNo(@PathVariable String managementNo) {
        return jibunQueryFacade.findJibunByManagementNo(managementNo);
    }

    @GetMapping("/{jibunId}/transactions")
    public List<TransactionApartmentResponse> pastYearsTransactions(
            @PathVariable Long jibunId,
            @RequestParam Integer fromYear,
            @RequestParam BigDecimal exclusiveUseArea
    ) {

        // TODO validate from year
        return jibunQueryFacade.fecthPastYearsTransactions(jibunId, fromYear, exclusiveUseArea);
    }
}
