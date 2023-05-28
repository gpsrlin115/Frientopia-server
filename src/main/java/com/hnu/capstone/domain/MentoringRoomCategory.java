package com.hnu.capstone.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MentoringRoomCategory {
    NOTICE("공지방"),
    REFERENCE("자료실"),
    VIDEO("영상실");

    private final String title;
}
