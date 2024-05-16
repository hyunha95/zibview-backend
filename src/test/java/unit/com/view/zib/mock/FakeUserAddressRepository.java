package com.view.zib.mock;

import com.view.zib.domain.user.entity.UserAddress;
import com.view.zib.domain.user.repository.UserAddressRepository;
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

public class FakeUserAddressRepository implements UserAddressRepository {

    private AtomicLong atomicLong = new AtomicLong();
    private List<UserAddress> userAddresses = new CopyOnWriteArrayList<>();

    @Override
    public void flush() {

    }

    @Override
    public <S extends UserAddress> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends UserAddress> List<S> saveAllAndFlush(Iterable<S> entities) {
        return List.of();
    }

    @Override
    public void deleteAllInBatch(Iterable<UserAddress> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Long> longs) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public UserAddress getOne(Long aLong) {
        return null;
    }

    @Override
    public UserAddress getById(Long aLong) {
        return null;
    }

    @Override
    public UserAddress getReferenceById(Long aLong) {
        return null;
    }

    @Override
    public <S extends UserAddress> List<S> findAll(Example<S> example) {
        return List.of();
    }

    @Override
    public <S extends UserAddress> List<S> findAll(Example<S> example, Sort sort) {
        return List.of();
    }

    @Override
    public <S extends UserAddress> List<S> saveAll(Iterable<S> entities) {
        return List.of();
    }

    @Override
    public List<UserAddress> findAll() {
        return List.of();
    }

    @Override
    public List<UserAddress> findAllById(Iterable<Long> longs) {
        return List.of();
    }

    @Override
    public <S extends UserAddress> S save(S entity) {
        ReflectionTestUtils.setField(entity, "id", atomicLong.incrementAndGet());
        userAddresses.add(entity);
        return entity;
    }

    @Override
    public Optional<UserAddress> findById(Long aLong) {
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
    public void delete(UserAddress entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Long> longs) {

    }

    @Override
    public void deleteAll(Iterable<? extends UserAddress> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public List<UserAddress> findAll(Sort sort) {
        return List.of();
    }

    @Override
    public Page<UserAddress> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public <S extends UserAddress> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends UserAddress> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends UserAddress> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends UserAddress> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends UserAddress, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }
}
