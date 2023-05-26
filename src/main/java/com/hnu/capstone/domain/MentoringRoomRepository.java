package com.hnu.capstone.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MentoringRoomRepository extends JpaRepository<MentoringRoom, Long> {
    Optional<MentoringRoom> findByPost(Posts post);
}