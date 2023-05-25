package com.hnu.capstone.service;

import com.hnu.capstone.domain.*;
import com.hnu.capstone.dto.PostsListResponseDto;
import com.hnu.capstone.dto.PostsResponseDto;
import com.hnu.capstone.dto.PostsSaveRequestDto;
import com.hnu.capstone.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PostsService {
    private final PostsRepository postsRepository;
    private final MentoringRoomService mentoringRoomService;

    @Transactional
    public Long save(PostsSaveRequestDto requestDto) throws IOException {

        //파일 존재여부 체크
        if(!requestDto.getFile().isEmpty()){
            /* 파일 저장 */
            FileStore fileStore = new FileStore();
            MultipartFile post_file = requestDto.getFile();
            UploadFile uploadFile = fileStore.storeFile(post_file);
            /* 파일명 추가 */
            requestDto.addFileName(uploadFile.getStoreFileName());
        }


        return postsRepository.save(requestDto.toEntity()).getId();
    }

    @Transactional
    public Long update(Long id, PostsUpdateRequestDto requestDto) {
        Posts posts = postsRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다. id="+ id));

        posts.update(requestDto.getTitle(), requestDto.getContent());
        postsRepository.save(posts);

        return id;
    }

    @Transactional
    public void delete(Long id) {
        Posts posts = postsRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다. id=" + id));

        mentoringRoomService.findByPost(posts).setPost(null);
        postsRepository.delete(posts);
    }

    @Transactional(readOnly = true)
    public PostsResponseDto findById (Long id) {
        Posts entity = postsRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다. id=" + id));

        return new PostsResponseDto(entity);
    }

    @Transactional(readOnly = true)
    public List<PostsListResponseDto> findAllDesc() {
        return postsRepository.findAllDesc().stream()
                .map(PostsListResponseDto::new)
                .collect(Collectors.toList());
    }

}
