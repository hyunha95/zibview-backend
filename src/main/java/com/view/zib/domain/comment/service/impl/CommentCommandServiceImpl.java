package com.view.zib.domain.comment.service.impl;

import com.view.zib.domain.auth.service.AuthService;
import com.view.zib.domain.comment.controller.request.CreateCommentRequest;
import com.view.zib.domain.comment.controller.request.LikeCommentRequest;
import com.view.zib.domain.comment.entity.Comment;
import com.view.zib.domain.comment.entity.CommentLike;
import com.view.zib.domain.comment.repository.CommentLikeRepository;
import com.view.zib.domain.comment.repository.CommentRepository;
import com.view.zib.domain.comment.service.CommentCommandService;
import com.view.zib.domain.post.entity.SubPost;
import com.view.zib.domain.post.service.SubPostService;
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
    private final SubPostService subPostService;
    private final CommentRepository commentRepository;
    private final CommentLikeRepository commentLikeRepository;

    @Override
    public Long createComment(CreateCommentRequest request) {
        SubPost subPost = subPostService.getById(request.subPostId());
        Comment parentComment = this.getById(request.parentCommentId());
        Comment newComment = Comment.of(request.comment(), authService.getCurrentUser(), subPost, parentComment);
        commentRepository.save(newComment);
        return newComment.getId();
    }

    /**
     * 동시성 문제가 발생할 가능성이 있음
     */
    @Override
    public void likeComment(LikeCommentRequest request) {
        User currentUser = authService.getCurrentUser();

        Comment comment = this.getByIdForUpdate(request.commentId());

        // 이미 좋아요를 눌렀다면 좋아요를 취소하고, 아니라면 좋아요를 누른다.
        commentLikeRepository.findByUserIdAndCommentIdForUpdate(currentUser.getId(), request.commentId())
                .ifPresentOrElse(updateCommentAndCommentLike(comment), createCommentLike(comment));
    }

    private Runnable createCommentLike(Comment comment) {
        return () -> {
            CommentLike newCommentLike = CommentLike.of(comment, authService.getCurrentUser());
            commentLikeRepository.save(newCommentLike);
            comment.increaseLikeCount();
        };
    }


    private Consumer<CommentLike> updateCommentAndCommentLike(Comment comment) {
        return commentLike -> {
            if (commentLike.isLike()) {
                commentLike.unLike();
                comment.decreaseLikeCount();
            } else {
                commentLike.like();
                comment.increaseLikeCount();
            }
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
