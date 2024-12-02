package com.view.zib.domain.transaction.repository.jdbc;

import com.view.zib.domain.address.repository.dto.TransactionApartmentDTO;
import com.view.zib.domain.transaction.entity.TransactionApartment;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import java.sql.PreparedStatement;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Repository
public class TransactionApartmentJdbcTemplate {

    private final JdbcTemplate jdbcTemplate;
    private static final String sql = """
                INSERT INTO transaction_apartment (
                    jibun_id, sgg_code, emd_code, land_code, bonbun,
                    bubun, road_name, road_name_sgg_code, road_name_code, road_name_seq,
                    road_nameb_code, road_name_bonbun, road_name_bubun, legal_dong_name, apartment_name,
                    jibun, exclusive_use_area, deal_year, deal_month, deal_day,
                    deal_amount, floor, built_year, apartment_seq, cancel_deal_type,
                    cancel_deal_day, deal_gbn, estate_agent_sgg_name, registered_date, apartment_dong_name,
                    seller_gbn, buyer_gbn, land_leasehold_gbn, created_at, updated_at)
                VALUES (
                    ?, ?, ?, ?, ?,
                    ?, ?, ?, ?, ?,
                    ?, ?, ?, ?, ?,
                    ?, ?, ?, ?, ?,
                    ?, ?, ?, ?, ?,
                    ?, ?, ?, ?, ?,
                    ?, ?, ?, now(), now())
                """;

    public void bulkInsert(List<TransactionApartment> newTransactionApartments) {
        if (CollectionUtils.isEmpty(newTransactionApartments)) return;

        long start = System.currentTimeMillis();
        log.info("[bulkInsert] TransactionApartment count: {}", newTransactionApartments.size());

        jdbcTemplate.batchUpdate(
                sql,
                newTransactionApartments,
                newTransactionApartments.size(),
                (PreparedStatement ps, TransactionApartment transactionApartment) -> {
                    ps.setLong(1, transactionApartment.getJibun().getId());
                    ps.setString(2, transactionApartment.getSggCode());
                    ps.setString(3, transactionApartment.getEmdCode());
                    ps.setString(4, transactionApartment.getLandCode());
                    ps.setString(5, transactionApartment.getBonbun());
                    ps.setString(6, transactionApartment.getBubun());
                    ps.setString(7, transactionApartment.getRoadName());
                    ps.setString(8, transactionApartment.getRoadNameSggCode());
                    ps.setString(9, transactionApartment.getRoadNameCode());
                    ps.setString(10, transactionApartment.getRoadNameSeq());
                    ps.setString(11, transactionApartment.getRoadNamebCode());
                    ps.setString(12, transactionApartment.getRoadNameBonbun());
                    ps.setString(13, transactionApartment.getRoadNameBubun());
                    ps.setString(14, transactionApartment.getLegalDongName());
                    ps.setString(15, transactionApartment.getApartmentName());
                    ps.setString(16, transactionApartment.getJibunNumber());
                    ps.setBigDecimal(17, transactionApartment.getExclusiveUseArea());
                    ps.setInt(18, transactionApartment.getDealYear());
                    ps.setInt(19, transactionApartment.getDealMonth());
                    ps.setInt(20, transactionApartment.getDealDay());
                    ps.setString(21, transactionApartment.getDealAmount());
                    ps.setInt(22, transactionApartment.getFloor());
                    ps.setString(23, transactionApartment.getBuiltYear());
                    ps.setString(24, transactionApartment.getApartmentSeq());
                    ps.setString(25, transactionApartment.getCancelDealType());
                    ps.setString(26, transactionApartment.getCancelDealDay());
                    ps.setString(27, transactionApartment.getDealGbn());
                    ps.setString(28, transactionApartment.getEstateAgentSggName());
                    ps.setString(29, transactionApartment.getRegisteredDate());
                    ps.setString(30, transactionApartment.getApartmentDongName());
                    ps.setString(31, transactionApartment.getSellerGbn());
                    ps.setString(32, transactionApartment.getBuyerGbn());
                    ps.setString(33, transactionApartment.getLandLeaseholdGbn());
                });

        long end = System.currentTimeMillis();
        log.info("[bulkInsert] Elapsed time: {} ms", end - start);
    }

    public void bulkInsertDTO(List<TransactionApartmentDTO> transactionApartmentDTOs) {
        if (CollectionUtils.isEmpty(transactionApartmentDTOs)) return;

        long start = System.currentTimeMillis();
        log.info("[bulkInsert] TransactionApartmentDTOs count: {}", transactionApartmentDTOs.size());

        jdbcTemplate.batchUpdate(
                sql,
                transactionApartmentDTOs,
                transactionApartmentDTOs.size(),
                (PreparedStatement ps, TransactionApartmentDTO ta) -> {
                    ps.setLong(1, ta.jibunId());
                    ps.setString(2, ta.sggCode());
                    ps.setString(3, ta.emdCode());
                    ps.setString(4, ta.landCode());
                    ps.setString(5, ta.bonbun());
                    ps.setString(6, ta.bubun());
                    ps.setString(7, ta.roadName());
                    ps.setString(8, ta.roadNameSggCode());
                    ps.setString(9, ta.roadNameCode());
                    ps.setString(10, ta.roadNameSeq());
                    ps.setString(11, ta.roadNamebCode());
                    ps.setString(12, ta.roadNameBonbun());
                    ps.setString(13, ta.roadNameBubun());
                    ps.setString(14, ta.legalDongName());
                    ps.setString(15, ta.apartmentName());
                    ps.setString(16, ta.jibunNumber());
                    ps.setBigDecimal(17, ta.exclusiveUseArea());
                    ps.setInt(18, ta.dealYear());
                    ps.setInt(19, ta.dealMonth());
                    ps.setInt(20, ta.dealDay());
                    ps.setString(21, ta.dealAmount());
                    ps.setInt(22, ta.floor());
                    ps.setString(23, ta.builtYear());
                    ps.setString(24, ta.apartmentSeq());
                    ps.setString(25, ta.cancelDealType());
                    ps.setString(26, ta.cancelDealDay());
                    ps.setString(27, ta.dealGbn());
                    ps.setString(28, ta.estateAgentSggName());
                    ps.setString(29, ta.registeredDate());
                    ps.setString(30, ta.apartmentDongName());
                    ps.setString(31, ta.sellerGbn());
                    ps.setString(32, ta.buyerGbn());
                    ps.setString(33, ta.landLeaseholdGbn());
                });

        long end = System.currentTimeMillis();
        log.info("[bulkInsert] Elapsed time: {} ms", end - start);
    }
}
