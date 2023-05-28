package com.hnu.capstone.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MentoringRoomPostRepository extends JpaRepository<MentoringRoomPost, Long> {
    @Query(value = "SELECT p FROM MentoringRoomPost p ORDER BY p.id DESC")
    List<MentoringRoomPost> findAllDesc();
}
