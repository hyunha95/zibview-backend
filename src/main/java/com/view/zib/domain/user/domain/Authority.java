package com.view.zib.domain.user.domain;

import com.view.zib.domain.user.enums.Role;
import lombok.Builder;

public record Authority(Role authority) {

    @Builder
    public Authority {
    }
}
