package com.hnu.capstone.service;

import com.hnu.capstone.domain.*;
import com.hnu.capstone.dto.PostsListResponseDto;
import com.hnu.capstone.dto.PostsResponseDto;
import com.hnu.capstone.dto.PostsSaveRequestDto;
import com.hnu.capstone.dto.PostsUpdateRequestDto;
import com.hnu.capstone.dto.mentoringroom.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class MentoringRoomPostsService {
    private final MentoringRoomPostRepository mentoringRoomPostRepository;

    @Transactional
    public Long save(NoticeSaveRequestDto noticeSaveRequestDto) throws IOException {
        return mentoringRoomPostRepository.save(noticeSaveRequestDto.toEntity()).getId();
    }

    @Transactional
    public Long save(ReferenceSaveRequestDto referenceSaveRequestDto) throws IOException {
        return mentoringRoomPostRepository.save(referenceSaveRequestDto.toEntity()).getId();
    }

    @Transactional
    public Long save(VideoSaveRequestDto videoSaveRequestDto) throws IOException {
        return mentoringRoomPostRepository.save(videoSaveRequestDto.toEntity()).getId();
    }

    @Transactional
    public Long update(Long id, MentoringRoomPostsUpdateRequestDto mentoringRoomPostsUpdateRequestDto) {
        MentoringRoomPost mentoringRoomPost = mentoringRoomPostRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 포스트가 없습니다. id="+ id));

        mentoringRoomPost.update(mentoringRoomPostsUpdateRequestDto.getTitle(), mentoringRoomPostsUpdateRequestDto.getContent());
        mentoringRoomPostRepository.save(mentoringRoomPost);

        return id;
    }

    @Transactional
    public void delete(Long id) {
        MentoringRoomPost posts = mentoringRoomPostRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 포스트가 없습니다. id=" + id));

        mentoringRoomPostRepository.delete(posts);
    }

    @Transactional(readOnly = true)
    public MentoringRoomPost findById(Long id) {
        MentoringRoomPost entity = mentoringRoomPostRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 포스트가 없습니다. id=" + id));

        return entity;
    }

    @Transactional(readOnly = true)
    public List<MentoringRoomPostsListResponseDto> findAllDesc() {
        return mentoringRoomPostRepository.findAllDesc().stream()
                .map(MentoringRoomPostsListResponseDto::new)
                .collect(Collectors.toList());
    }
}
