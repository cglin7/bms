package org.zero.bms.service.enums;

import lombok.Getter;

@Getter
public enum UserStatusEnum {
    WAITING_AUDIT(1, "待审核"),
    AUDITED(2, "已审核"),
    LOCKED(3, "已锁定"),
    ;
    private int code;
    private String msg;

    UserStatusEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
