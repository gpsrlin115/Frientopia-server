package com.hnu.capstone.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MentoringMappingRepository extends JpaRepository<MentoringMapping, Long> {
    List<MentoringMapping> findAllByPost(Posts post);
    Optional<MentoringMapping> findAllByUser(User user);
    MentoringMapping findByPostAndUser(Posts post, User user);
}
