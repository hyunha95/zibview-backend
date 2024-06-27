package com.view.zib.domain.comment.service.impl;

import com.view.zib.domain.auth.service.AuthService;
import com.view.zib.domain.comment.CommentAction;
import com.view.zib.domain.comment.controller.request.CreateCommentRequest;
import com.view.zib.domain.comment.controller.request.ToggleLikeRequest;
import com.view.zib.domain.comment.controller.response.ToggleLikeResponse;
import com.view.zib.domain.comment.entity.Comment;
import com.view.zib.domain.comment.entity.CommentLike;
import com.view.zib.domain.comment.repository.CommentLikeRepository;
import com.view.zib.domain.comment.repository.CommentRepository;
import com.view.zib.domain.comment.service.CommentCommandService;
import com.view.zib.domain.log.service.CommentLikeLogService;
import com.view.zib.domain.post.entity.SubPost;
import com.view.zib.domain.post.service.SubPostQueryService;
import com.view.zib.domain.user.entity.User;
import com.view.zib.global.exception.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.function.Consumer;

@Transactional
@RequiredArgsConstructor
@Service
public class CommentCommandServiceImpl implements CommentCommandService {

    private final AuthService authService;
    private final SubPostQueryService subPostQueryService;
    private final CommentLikeLogService commentLikeLogService;

    private final CommentRepository commentRepository;
    private final CommentLikeRepository commentLikeRepository;


    @Override
    public Long createComment(CreateCommentRequest request) {
        SubPost subPost = subPostQueryService.getById(request.subPostId());
        Comment parentComment = this.getById(request.parentCommentId());
        Comment newComment = Comment.of(request.comment(), authService.getCurrentUser(), subPost, parentComment);
        commentRepository.save(newComment);
        return newComment.getId();
    }

    /**
     * 동시성 문제가 발생할 가능성이 있음
     */
    @Override
    public ToggleLikeResponse toggleLike(ToggleLikeRequest request) {
        User currentUser = authService.getCurrentUser();

        Comment comment = this.getByIdForUpdate(request.commentId());

        ToggleLikeResponse response = new ToggleLikeResponse();
        // 이미 좋아요를 눌렀다면 좋아요를 취소하고, 아니라면 좋아요를 누른다.
        commentLikeRepository.findByUserIdAndCommentIdForUpdate(currentUser.getId(), request.commentId())
                .ifPresentOrElse(deleteCommentLike(comment, response), createCommentLike(comment, response));

        return response;
    }

    private Runnable createCommentLike(Comment comment, ToggleLikeResponse response) {
        return () -> {
            CommentLike newCommentLike = CommentLike.of(comment, authService.getCurrentUser());
            commentLikeRepository.save(newCommentLike);
            comment.increaseLikeCount();
            // logging
            commentLikeLogService.log(newCommentLike, CommentAction.CREATE);

            response.setLiked(true);
        };
    }


    private Consumer<CommentLike> deleteCommentLike(Comment comment, ToggleLikeResponse response) {
        return commentLike -> {
            commentLikeRepository.delete(commentLike);
            comment.decreaseLikeCount();

            // logging
            commentLikeLogService.log(commentLike, CommentAction.DELETE);

            response.setLiked(false);
        };
    }

    public Comment getByIdForUpdate(Long commentId) {
        return this.findByIdForUpdate(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment", commentId));
    }

    public Optional<Comment> findByIdForUpdate(Long commentId) {
        return commentRepository.findByIdForUpdate(commentId);
    }

    public Comment getById(Long commentId) {
        return this.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment", commentId));
    }

    public Optional<Comment> findById(Long commentId) {
        return commentRepository.findById(commentId);
    }

}
