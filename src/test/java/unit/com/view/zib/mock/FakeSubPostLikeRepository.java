package com.view.zib.mock;

import com.view.zib.domain.post.entity.SubPostLike;
import com.view.zib.domain.post.repository.SubPostLikeRepository;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Function;

public class FakeSubPostLikeRepository implements SubPostLikeRepository {

    private AtomicLong atomicLong = new AtomicLong(1);
    private List<SubPostLike> data = new CopyOnWriteArrayList<>();

    @Override
    public Optional<SubPostLike> findById(Long subPostLikeId) {
        return data.stream()
                .filter(item -> item.getId().equals(subPostLikeId))
                .findAny();
    }

    @Override
    public Optional<SubPostLike> findBySubPostIdAndUserId(Long subPostId, Long currentUserId) {
        return data.stream()
                .filter(item -> item.getSubPost().getId().equals(subPostId) && item.getUser().getId().equals(currentUserId))
                .findAny();
    }

    @Override
    public List<SubPostLike> findBySubPostIdInAndUserId(List<Long> subPostIds, Long userId) {
        return data.stream()
                .filter(item -> subPostIds.contains(item.getSubPost().getId()) && item.getUser().getId().equals(userId))
                .toList();
    }

    @Override
    public <S extends SubPostLike> S save(S entity) {
        if (entity.getId() == null || entity.getId() == 0) {
            SubPostLike newSubPostLike = SubPostLike.builder()
                    .id(atomicLong.getAndIncrement())
                    .subPost(entity.getSubPost())
                    .user(entity.getUser())
                    .liked(entity.isLiked())
                    .build();
            data.add(newSubPostLike);
            return (S) newSubPostLike;
        } else {
            data.removeIf(item -> item.getId().equals(entity.getId()));
            data.add(entity);
            return entity;
        }
    }

    @Override
    public void flush() {

    }

    @Override
    public <S extends SubPostLike> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends SubPostLike> List<S> saveAllAndFlush(Iterable<S> entities) {
        return List.of();
    }

    @Override
    public void deleteAllInBatch(Iterable<SubPostLike> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Long> longs) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public SubPostLike getOne(Long aLong) {
        return null;
    }

    @Override
    public SubPostLike getById(Long aLong) {
        return null;
    }

    @Override
    public SubPostLike getReferenceById(Long aLong) {
        return null;
    }

    @Override
    public <S extends SubPostLike> List<S> findAll(Example<S> example) {
        return List.of();
    }

    @Override
    public <S extends SubPostLike> List<S> findAll(Example<S> example, Sort sort) {
        return List.of();
    }

    @Override
    public <S extends SubPostLike> List<S> saveAll(Iterable<S> entities) {
        return List.of();
    }

    @Override
    public List<SubPostLike> findAll() {
        return List.of();
    }

    @Override
    public List<SubPostLike> findAllById(Iterable<Long> longs) {
        return List.of();
    }

    @Override
    public boolean existsById(Long aLong) {
        return false;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(Long aLong) {

    }

    @Override
    public void delete(SubPostLike entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Long> longs) {

    }

    @Override
    public void deleteAll(Iterable<? extends SubPostLike> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public List<SubPostLike> findAll(Sort sort) {
        return List.of();
    }

    @Override
    public Page<SubPostLike> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public <S extends SubPostLike> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends SubPostLike> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends SubPostLike> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends SubPostLike> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends SubPostLike, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }
}
