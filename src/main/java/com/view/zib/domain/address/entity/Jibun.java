package com.view.zib.domain.address.entity;

import com.view.zib.domain.client.vworld.dto.ApartmentTransactionResponse;
import com.view.zib.domain.transaction.entity.TransactionApartment;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import org.hibernate.annotations.BatchSize;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Table(name = "jibun")
public class Jibun {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "jibun_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "road_name_address_id")
    private RoadNameAddress roadNameAddress;

    @OneToOne(mappedBy = "jibun", cascade = CascadeType.ALL)
    private JibunDetail jibunDetail;

    @OneToMany(mappedBy = "jibun", fetch = FetchType.LAZY)
    private List<TransactionApartment> transactionApartments = new ArrayList<>();

    @Size(max = 25)
    @NotNull
    @Column(name = "management_no", nullable = false, length = 25, insertable=false, updatable=false)
    private String managementNo;

    @Size(max = 3)
    @NotNull
    @Column(name = "serial_no", nullable = false, length = 3)
    private String serialNo;

    @Size(max = 10)
    @Column(name = "legal_dong_code", length = 10)
    private String legalDongCode;

    @Size(max = 20)
    @Column(name = "sido_name", length = 20)
    private String sidoName;

    @Size(max = 20)
    @Column(name = "sgg_name", length = 20)
    private String sggName;

    @Size(max = 20)
    @Column(name = "emd_name", length = 20)
    private String emdName;

    @Size(max = 20)
    @Column(name = "ri_name", length = 20)
    private String riName;

    @Size(max = 1)
    @Column(name = "mountain_yn", length = 1)
    private String mountainYn;

    @Column(name = "jibun_main")
    private Integer jibunMain;

    @Column(name = "jibun_sub")
    private Integer jibunSub;

    @Size(max = 1)
    @Column(name = "representative_yn", length = 1)
    private String representativeYn;

    /**
     * 시군구 코드
     * @return
     */
    public String getSsgCode() {
        return legalDongCode.substring(0, 5);
    }

    public String getJibunAddress() {
        return String.format("%s %s %s %s %s", sidoName, sggName, emdName, riName, getJibunNumber());
    }

    public String getJibunNumber() {
        if (jibunSub == 0) {
            return String.valueOf(jibunMain);
        }
        return jibunMain + "-" + jibunSub;
    }

    public boolean isSameJibun(ApartmentTransactionResponse.Item item) {
        return this.legalDongCode.equals(item.sggCd() + item.umdCd()) && this.getJibunNumber().equals(item.jibun());
    }

    public void addEntity(JibunDetail jibunDetail) {
        this.jibunDetail = jibunDetail;
    }

    public void addEntity(List<TransactionApartment> newTransactionApartments) {
        this.transactionApartments.addAll(newTransactionApartments);
    }

    public void addEntity(TransactionApartment transactionApartment) {
        this.transactionApartments.add(transactionApartment);
    }
}