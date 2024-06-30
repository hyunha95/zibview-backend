package com.view.zib.mock;

import com.view.zib.domain.post.entity.SubPost;
import com.view.zib.domain.post.repository.SubPostRepository;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Function;

public class FakeSubPostRepository implements SubPostRepository {

    private AtomicLong atomicLong = new AtomicLong();
    private List<SubPost> data = new CopyOnWriteArrayList<>();

    @Override
    public synchronized Optional<SubPost> findByIdForUpdate(Long subPostId) {
        return data.stream().filter(item -> item.getId().equals(subPostId)).findAny();
    }

    @Override
    public <S extends SubPost> S save(S entity) {
        ReflectionTestUtils.setField(entity, "id", atomicLong.incrementAndGet());
        data.add(entity);
        return entity;
    }

    @Override
    public List<SubPost> findByPostIdAndDeletedFalseOrderByIdDesc(Long postId) {
        return List.of();
    }

    @Override
    public void flush() {

    }

    @Override
    public <S extends SubPost> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends SubPost> List<S> saveAllAndFlush(Iterable<S> entities) {
        return List.of();
    }

    @Override
    public void deleteAllInBatch(Iterable<SubPost> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Long> longs) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public SubPost getOne(Long aLong) {
        return null;
    }

    @Override
    public SubPost getById(Long aLong) {
        return null;
    }

    @Override
    public SubPost getReferenceById(Long aLong) {
        return null;
    }

    @Override
    public <S extends SubPost> List<S> findAll(Example<S> example) {
        return List.of();
    }

    @Override
    public <S extends SubPost> List<S> findAll(Example<S> example, Sort sort) {
        return List.of();
    }

    @Override
    public <S extends SubPost> List<S> saveAll(Iterable<S> entities) {
        return List.of();
    }

    @Override
    public List<SubPost> findAll() {
        return List.of();
    }

    @Override
    public List<SubPost> findAllById(Iterable<Long> longs) {
        return List.of();
    }


    @Override
    public Optional<SubPost> findById(Long aLong) {
        return Optional.empty();
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
    public void delete(SubPost entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Long> longs) {

    }

    @Override
    public void deleteAll(Iterable<? extends SubPost> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public List<SubPost> findAll(Sort sort) {
        return List.of();
    }

    @Override
    public Page<SubPost> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public <S extends SubPost> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends SubPost> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends SubPost> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends SubPost> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends SubPost, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }
}
