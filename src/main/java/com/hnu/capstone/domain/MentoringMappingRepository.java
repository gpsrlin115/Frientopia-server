package com.hnu.capstone.domain;

import com.hnu.capstone.dto.PostsResponseDto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MentoringMappingRepository extends JpaRepository<MentoringMapping, Long> {
    List<MentoringMapping> findAllByRegisteredStudy(PostsResponseDto posts);
}
