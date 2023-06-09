package com.example.newsfeedapi.likes;

import com.example.newsfeedapi.likes.dto.LikeDTO;
import com.example.newsfeedapi.likes.dto.LikeDTOMapper;
import com.example.newsfeedapi.likes.request.LikeReq;
import lombok.AllArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class LikeService {
    private final LikeRepository likeRepository;
    private final LikeDTOMapper mapper;
    public LikeDTO createLikeEntity(LikeReq req) {
        Like l = new Like(new ObjectId(req.getUserId()),
                new ObjectId(req.getPostId()));

        return mapper.apply(likeRepository.save(l));
    }
    public String deleteLikeEntity(ObjectId postId, ObjectId userId) {
//        Like o = likeRepository.findByPostIdAndUserId(postId, userId)
//                .orElseThrow();
        likeRepository.deleteByPostIdAndUserId(postId, userId);
        boolean exist = likeRepository.existsByPostIdAndUserId(postId, userId)
                .orElseThrow();
        return  exist ? "Failed" : "Deleted";
    }
    /* read likes */
    public List<LikeDTO> getLikesByPostId(ObjectId postId) {
        return likeRepository.findAllByPostId(postId).orElseThrow()
                .stream()
                .map(mapper)
                .collect(Collectors.toList());
    }
}
