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
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PostsService {
    private final PostsRepository postsRepository;
    private final MentoringRoomService mentoringRoomService;
    private final MentoringMappingService mentoringMappingService;
    private final FileStore fileStore;

    @Transactional
    public Long save(PostsSaveRequestDto requestDto) throws IOException {

        //파일 존재여부 체크
        if(!requestDto.getFile().isEmpty()){
            /* 파일 저장 */
            //FileStore fileStore = new FileStore();
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

//        mentoringRoomService.findByPost(posts).setPost(null);
//        List<MentoringMapping> mentoringMappings = mentoringMappingService.SelectAllByPosts(posts);
//        for (MentoringMapping m: mentoringMappings) {
//            m.setPost(null);
//        }
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


    // 기준과 거리를 정해 post filtering
    // maxDistance (단위 : 미터)
    public List<PostsListResponseDto> filterPostsByDistance(double userLatitude, double userLongitude, double maxDistance) {
        List<PostsListResponseDto> allPosts = this.findAllDesc(); // 모든 게시물 가져오기

        List<PostsListResponseDto> filteredPosts = new ArrayList<>();

        for (PostsListResponseDto post : allPosts) {
            double postLatitude = post.getLatitude();
            double postLongitude = post.getLongitude();

            double distance = calculateDistance(userLatitude, userLongitude, postLatitude, postLongitude);

            if (distance <= maxDistance) {
                filteredPosts.add(post);
            }
        }

        return filteredPosts;
    }

    // 두 지점(post의 멘토링 위치와 user) 사이의 거리 계산 (유클리드 거리 공식 사용)
    private double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        double latDiff = Math.toRadians(lat2 - lat1);
        double lonDiff = Math.toRadians(lon2 - lon1);

        double a = Math.sin(latDiff / 2) * Math.sin(latDiff / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDiff / 2) * Math.sin(lonDiff / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        // 지구의 반지름 (단위: 미터)
        double earthRadius = 6371000;

        return earthRadius * c;
    }
}
