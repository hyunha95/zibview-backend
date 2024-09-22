package com.view.zib.domain.address.entity;

import com.view.zib.domain.address.entity.id.RoadNameCodeId;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

import java.util.List;

@Getter
@Entity
@Table(name = "road_name_code")
public class RoadNameCode {
    @EmbeddedId
    private RoadNameCodeId id;

    @OneToMany(fetch = FetchType.LAZY)
    private List<RoadNameAddress> roadNameAddresses;

    @Size(max = 80)
    @NotNull
    @Column(name = "road_name", nullable = false, length = 80)
    private String roadName;

    @Size(max = 80)
    @Column(name = "road_name_roman", length = 80)
    private String roadNameRoman;

    @Size(max = 20)
    @Column(name = "sido_name", length = 20)
    private String sidoName;

    @Size(max = 40)
    @Column(name = "sido_name_roman", length = 40)
    private String sidoNameRoman;

    @Size(max = 20)
    @Column(name = "sgg_name", length = 20)
    private String sggName;

    @Size(max = 40)
    @Column(name = "sgg_name_roman", length = 40)
    private String sggNameRoman;

    @Size(max = 20)
    @Column(name = "emd_name", length = 20)
    private String emdName;

    @Size(max = 40)
    @Column(name = "emd_name_roman", length = 40)
    private String emdNameRoman;

    @Column(name = "emd_division")
    private Character emdDivision;

    @Size(max = 3)
    @Column(name = "emd_code", length = 3)
    private String emdCode;

    @Column(name = "usage_status")
    private Character usageStatus;

    @Column(name = "change_reason")
    private Character changeReason;

    @Size(max = 14)
    @Column(name = "change_history", length = 14)
    private String changeHistory;

    @Size(max = 8)
    @Column(name = "notification_date", length = 8)
    private String notificationDate;

    @Size(max = 8)
    @Column(name = "cancellation_date", length = 8)
    private String cancellationDate;
}