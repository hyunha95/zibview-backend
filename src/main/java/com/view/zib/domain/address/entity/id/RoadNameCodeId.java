package com.view.zib.domain.address.entity.id;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import org.hibernate.Hibernate;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Embeddable
public class RoadNameCodeId implements Serializable {
    private static final long serialVersionUID = -5612233104222652721L;
    @Size(max = 12)
    @NotNull
    @Column(name = "road_name_code", nullable = false, length = 12)
    private String roadNameCode;

    @Size(max = 2)
    @NotNull
    @Column(name = "emd_serial_no", nullable = false, length = 2)
    private String emdSerialNo;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        RoadNameCodeId entity = (RoadNameCodeId) o;
        return Objects.equals(this.roadNameCode, entity.roadNameCode) &&
                Objects.equals(this.emdSerialNo, entity.emdSerialNo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(roadNameCode, emdSerialNo);
    }

}