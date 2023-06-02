package com.hnu.capstone.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MentoringRoomCategory {
    MENTOR("멘토의글"),
    BOARD("자유게시판"),
    VIDEO("영상실");

    private final String title;
}
