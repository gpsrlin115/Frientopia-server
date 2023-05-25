package com.hnu.capstone.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MentoringRoomCategory {
    NOTICE("공지"),
    HOMEWORK("과제"),
    VIDEO("영상");

    private final String title;
}
