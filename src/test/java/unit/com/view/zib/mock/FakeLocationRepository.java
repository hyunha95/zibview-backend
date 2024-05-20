package com.view.zib.mock;

import com.view.zib.domain.location.entity.Location;
import com.view.zib.domain.user.repository.LocationRepository;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Function;

public class FakeLocationRepository implements LocationRepository {

    private AtomicLong id = new AtomicLong(0);
    private List<Location> data = new ArrayList<>();

    @Override
    public Optional<Location> findByUser_Id(Long userId) {
        return data.stream()
                .filter(item -> Objects.equals(item.getUser().getId(), userId))
                .findAny();
    }

    @Override
    public void flush() {

    }

    @Override
    public <S extends Location> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends Location> List<S> saveAllAndFlush(Iterable<S> entities) {
        return List.of();
    }

    @Override
    public void deleteAllInBatch(Iterable<Location> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Long> longs) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public Location getOne(Long aLong) {
        return null;
    }

    @Override
    public Location getById(Long aLong) {
        return null;
    }

    @Override
    public Location getReferenceById(Long aLong) {
        return null;
    }

    @Override
    public <S extends Location> List<S> findAll(Example<S> example) {
        return List.of();
    }

    @Override
    public <S extends Location> List<S> findAll(Example<S> example, Sort sort) {
        return List.of();
    }

    @Override
    public <S extends Location> List<S> saveAll(Iterable<S> entities) {
        return List.of();
    }

    @Override
    public List<Location> findAll() {
        return List.of();
    }

    @Override
    public List<Location> findAllById(Iterable<Long> longs) {
        return List.of();
    }

    @Override
    public <S extends Location> S save(S entity) {
        if (entity.getId() == null || entity.getId() == 0) {
            ReflectionTestUtils.setField(entity, "id", id.incrementAndGet());
            data.add(entity);
            return entity;
        } else {
            int index = data.indexOf(entity);
            ReflectionTestUtils.setField(data.get(index), "accuracy", entity.getAccuracy());
            ReflectionTestUtils.setField(data.get(index), "altitude", entity.getAltitude());
            ReflectionTestUtils.setField(data.get(index), "altitudeAccuracy", entity.getAltitudeAccuracy());
            ReflectionTestUtils.setField(data.get(index), "heading", entity.getHeading());
            ReflectionTestUtils.setField(data.get(index), "latitude", entity.getLatitude());
            ReflectionTestUtils.setField(data.get(index), "longitude", entity.getLongitude());
            ReflectionTestUtils.setField(data.get(index), "speed", entity.getSpeed());
            ReflectionTestUtils.setField(data.get(index), "timestamp", entity.getTimestamp());

            data.set(index, entity);
            return entity;
        }
    }

    @Override
    public Optional<Location> findById(Long aLong) {
        return data.stream().filter(location -> Objects.equals(location.getId(), aLong)).findFirst();
    }

    @Override
    public boolean existsById(Long aLong) {
        return false;
    }

    @Override
    public long count() {
        return data.size();
    }

    @Override
    public void deleteById(Long aLong) {

    }

    @Override
    public void delete(Location entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Long> longs) {

    }

    @Override
    public void deleteAll(Iterable<? extends Location> entities) {

    }

    @Override
    public void deleteAll() {
        data = new ArrayList();
    }

    @Override
    public List<Location> findAll(Sort sort) {
        return List.of();
    }

    @Override
    public Page<Location> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public <S extends Location> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends Location> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends Location> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends Location> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends Location, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }
}
