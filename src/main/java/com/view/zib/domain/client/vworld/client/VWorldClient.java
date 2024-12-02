package com.view.zib.domain.client.vworld.client;

import com.view.zib.domain.client.vworld.dto.ApartmentTransactionResponse;
import com.view.zib.domain.client.vworld.dto.OfficeTelTransactionClientDTO;
import com.view.zib.domain.client.vworld.dto.VWorldResponseDto;

import java.util.Optional;

public interface VWorldClient {
    Optional<VWorldResponseDto> searchJibunDetail(String legalDongCode, int jibun, int subJibun);
//    Optional<OfficeTelTransactionClientDTO> getRTMSDataSvcOffiRent(String legalDongCode, String dealYmd);
    Optional<ApartmentTransactionResponse> getRTMSDataSvcAptTradeDev(String sggCode, String dealYmd);
}
