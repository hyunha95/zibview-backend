package com.view.zib.domain.address.facade;

import com.view.zib.domain.address.entity.Address;
import com.view.zib.domain.address.service.AddressQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@RequiredArgsConstructor
@Component
public class AddressQueryFacade {

    private final AddressQueryService addressQueryService;

    public Address findByLegalDongCodeStartsWithAndSsgAndEmdAndJibunAndSubJibun(String sggCd, String sggNm, String umdNm, String jibun) {
        String originalJibun = jibun;
        String subJibun = "0";
        if (jibun.contains("-")) {
            String[] jibunSubJibun = jibun.split("-");
            jibun = jibunSubJibun[0];
            subJibun = jibunSubJibun[1];
        }

        Optional<Address> addressOptional = addressQueryService.findByLegalDongCodeStartsWithAndSsgAndEmdAndJibunAndSubJibun(sggCd, sggNm, umdNm, jibun, subJibun);

        if (addressOptional.isEmpty()) {
            // TODO: 조회되지 않는 신규 주소 데이터가 있을 경우, 이를 처리하는 로직 추가
            return new Address();
        }

        return addressOptional.get();

    }
}
