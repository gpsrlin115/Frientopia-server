package com.hnu.capstone.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {

    ADMIN("ROLE_ADMIN", "관리자"),
    MENTOR("ROLE_MENTOR", "멘토"),
    MENTEE("ROLE_MENTEE", "멘티"),
    USER("ROLE_USER", "일반 사용자"),
    GUEST("ROLE_GUEST", "게스트");

    private final String Key;
    private final String title;
}
