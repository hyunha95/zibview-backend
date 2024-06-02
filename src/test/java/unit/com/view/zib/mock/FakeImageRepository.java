package com.view.zib.mock;

import com.view.zib.domain.image.entity.Image;
import com.view.zib.domain.image.repository.ImageRepository;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Function;

public class FakeImageRepository implements ImageRepository {

    AtomicLong atomicLong = new AtomicLong();
    List<Image> images = new CopyOnWriteArrayList<>();

    @Override
    public Optional<Image> findByStoredFilename(String storedFileName) {
        return Optional.empty();
    }

    @Override
    public void deleteByUuid(String imageUuid) {
        
    }

    @Override
    public Optional<Image> findByUuid(String imageUuid) {
        return Optional.empty();
    }

    @Override
    public Image findLatestRepresentativeImage(Long postId) {
        return null;
    }

    @Override
    public List<Image> findByUuidIn(List<String> imageUuids) {
        return List.of();
    }

    @Override
    public void flush() {

    }

    @Override
    public <S extends Image> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends Image> List<S> saveAllAndFlush(Iterable<S> entities) {
        return List.of();
    }

    @Override
    public void deleteAllInBatch(Iterable<Image> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Long> longs) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public Image getOne(Long aLong) {
        return null;
    }

    @Override
    public Image getById(Long aLong) {
        return null;
    }

    @Override
    public Image getReferenceById(Long aLong) {
        return null;
    }

    @Override
    public <S extends Image> List<S> findAll(Example<S> example) {
        return List.of();
    }

    @Override
    public <S extends Image> List<S> findAll(Example<S> example, Sort sort) {
        return List.of();
    }

    @Override
    public <S extends Image> List<S> saveAll(Iterable<S> entities) {
        return List.of();
    }

    @Override
    public List<Image> findAll() {
        return List.of();
    }

    @Override
    public List<Image> findAllById(Iterable<Long> longs) {
        return List.of();
    }

    @Override
    public <S extends Image> S save(S entity) {
        ReflectionTestUtils.setField(entity, "id", atomicLong.incrementAndGet());
        images.add(entity);
        return entity;
    }

    @Override
    public Optional<Image> findById(Long aLong) {
        return images.stream().filter(image -> Objects.equals(image.getId(), aLong)).findAny();
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
    public void delete(Image entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Long> longs) {

    }

    @Override
    public void deleteAll(Iterable<? extends Image> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public List<Image> findAll(Sort sort) {
        return List.of();
    }

    @Override
    public Page<Image> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public <S extends Image> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends Image> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends Image> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends Image> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends Image, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }
}
