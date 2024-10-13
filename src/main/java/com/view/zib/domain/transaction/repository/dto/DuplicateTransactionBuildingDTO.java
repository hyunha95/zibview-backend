package com.view.zib.domain.transaction.repository.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DuplicateTransactionBuildingDTO {
    private String sggCode;
    private int dealYear;
    private int dealMonth;
}
