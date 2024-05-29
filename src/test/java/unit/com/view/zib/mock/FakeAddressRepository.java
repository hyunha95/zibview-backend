package com.view.zib.mock;

import com.view.zib.domain.address.entity.Address;
import com.view.zib.domain.address.repository.AddressRepository;
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

public class FakeAddressRepository implements AddressRepository {

    private AtomicLong atomicLong = new AtomicLong();
    private List<Address> addresses = new CopyOnWriteArrayList<>();

    @Override
    public Optional<Address> findByPostId(Long postId) {
        return Optional.empty();
    }

    @Override
    public void flush() {

    }

    @Override
    public <S extends Address> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends Address> List<S> saveAllAndFlush(Iterable<S> entities) {
        return List.of();
    }

    @Override
    public void deleteAllInBatch(Iterable<Address> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Long> longs) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public Address getOne(Long aLong) {
        return null;
    }

    @Override
    public Address getById(Long aLong) {
        return null;
    }

    @Override
    public Address getReferenceById(Long aLong) {
        return null;
    }

    @Override
    public <S extends Address> List<S> findAll(Example<S> example) {
        return List.of();
    }

    @Override
    public <S extends Address> List<S> findAll(Example<S> example, Sort sort) {
        return List.of();
    }

    @Override
    public <S extends Address> List<S> saveAll(Iterable<S> entities) {
        return List.of();
    }

    @Override
    public List<Address> findAll() {
        return List.of();
    }

    @Override
    public List<Address> findAllById(Iterable<Long> longs) {
        return List.of();
    }

    @Override
    public <S extends Address> S save(S entity) {
        ReflectionTestUtils.setField(entity, "id", atomicLong.incrementAndGet());
        addresses.add(entity);
        return entity;
    }

    @Override
    public Optional<Address> findById(Long aLong) {
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
    public void delete(Address entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Long> longs) {

    }

    @Override
    public void deleteAll(Iterable<? extends Address> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public List<Address> findAll(Sort sort) {
        return List.of();
    }

    @Override
    public Page<Address> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public <S extends Address> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends Address> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends Address> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends Address> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends Address, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }
}
