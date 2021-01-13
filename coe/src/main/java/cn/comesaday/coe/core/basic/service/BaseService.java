package cn.comesaday.coe.core.basic.service;

import cn.comesaday.coe.core.jpa.bean.repository.MyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

/**
 * <描述> BaseService
 * <详细背景>
 *
 * @author: ChenWei
 * @CreateAt: 2021-01-12 15:40
 */
public abstract class BaseService<T, ID extends Serializable> implements MyRepository<T, ID> {

    @Autowired
    private MyRepository<T, ID> myRepository;

    @Override
    public <S extends T> S save(S s) {
        return myRepository.save(s);
    }

    @Override
    public void delete(T t) {
        myRepository.delete(t);
    }

    @Override
    public <S extends T> List<S> saveAll(Iterable<S> iterable) {
        return myRepository.saveAll(iterable);
    }

    @Override
    public List<T> findAll() {
        return myRepository.findAll();
    }

    @Override
    public List<T> findAll(Sort sort) {
        return myRepository.findAll(sort);
    }

    @Override
    public List<T> findAllById(Iterable<ID> iterable) {
        return myRepository.findAllById(iterable);
    }

    @Override
    public void flush() {
        myRepository.flush();
    }

    @Override
    public <S extends T> S saveAndFlush(S s) {
        return myRepository.saveAndFlush(s);
    }

    @Override
    public void deleteInBatch(Iterable<T> iterable) {
        myRepository.deleteInBatch(iterable);
    }

    @Override
    public void deleteAllInBatch() {
        myRepository.deleteAllInBatch();
    }

    @Override
    public T getOne(ID id) {
        return myRepository.getOne(id);
    }

    @Override
    public <S extends T> List<S> findAll(Example<S> example) {
        return myRepository.findAll(example);
    }

    @Override
    public <S extends T> List<S> findAll(Example<S> example, Sort sort) {
        return myRepository.findAll(example, sort);
    }

    @Override
    public Page<T> findAll(Pageable pageable) {
        return myRepository.findAll(pageable);
    }

    @Override
    public Optional<T> findById(ID id) {
        return myRepository.findById(id);
    }

    @Override
    public boolean existsById(ID id) {
        return myRepository.existsById(id);
    }

    @Override
    public long count() {
        return myRepository.count();
    }

    @Override
    public void deleteById(ID id) {
        myRepository.deleteById(id);
    }

    @Override
    public void deleteAll(Iterable<? extends T> iterable) {
        myRepository.deleteAll(iterable);
    }

    @Override
    public void deleteAll() {
        myRepository.deleteAll();
    }

    @Override
    public <S extends T> Optional<S> findOne(Example<S> example) {
        return myRepository.findOne(example);
    }

    @Override
    public <S extends T> Page<S> findAll(Example<S> example, Pageable pageable) {
        return myRepository.findAll(example, pageable);
    }

    @Override
    public <S extends T> long count(Example<S> example) {
        return myRepository.count(example);
    }

    @Override
    public <S extends T> boolean exists(Example<S> example) {
        return myRepository.exists(example);
    }

    public <S extends T> S findOne(S entity) {
        Example<S> example = Example.of(entity);
        return this.findOne(example).orElse(null);
    }

    public <S extends T> List<S> findAll(S entity) {
        Example<S> example = Example.of(entity);
        return this.findAll(example);
    }

    public <S extends T> Page<S> findAll(S entity, Pageable pageable) {
        Example<S> example = Example.of(entity);
        return this.findAll(example, pageable);
    }
}
