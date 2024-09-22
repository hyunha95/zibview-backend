package com.view.zib.domain.client.kako.service;

import com.view.zib.domain.address.event.publisher.RoadNameAddressEventPublisher;
import com.view.zib.domain.client.kako.client.KakaoAddressClient;
import com.view.zib.domain.client.kako.domain.Coordinate;
import com.view.zib.domain.client.kako.domain.KakaoAddressResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@RequiredArgsConstructor
@Service
public class KakaoClientImpl implements KakaoClient {

    private final KakaoAddressClient kakaoAddressClient;

    private final RoadNameAddressEventPublisher roadNameAddressEventPublisher;

    @Override
    public Coordinate searchCoordinateByRoadAddress(String roadAddress) {
        log.info("kakao Address API Call with roadAddress: [{}]", roadAddress);
        KakaoAddressResponse kakaoAddressResponse = kakaoAddressClient.searchAddress(roadAddress, "", 1, 1);
        return kakaoAddressResponse.getCoordinate();
    }

    @Override
    public Coordinate searchCoordinateByRoadAddress(String roadAddress, String managementNo) {
        Coordinate coordinate = this.searchCoordinateByRoadAddress(roadAddress);
        roadNameAddressEventPublisher.publishUpdateCoordinateEvent(managementNo, coordinate, LocalDateTime.now());
        return coordinate;
    }
}
