package cn.comesaday.coe.core.basic.service;

import cn.comesaday.coe.common.constant.NumConstant;
import cn.comesaday.coe.core.basic.builder.ConditionBuilder;
import cn.comesaday.coe.core.basic.model.IdEntity;
import cn.comesaday.coe.core.jpa.bean.repository.MyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * <描述> BaseService
 * <详细背景>
 * @author: ChenWei
 * @CreateAt: 2021-01-12 15:40
 */
public class BaseService<T, ID extends Serializable> implements MyRepository<T, ID> {

    @Autowired
    private MyRepository<T, ID> myRepository;

    /**
     * <说明> 获 DomainClass
     * @author ChenWei
     * @date 2021/3/22 18:04
     * @return java.lang.Class<T>
     */
    public Class<T> getCurDomainClass() {
        ParameterizedType pt = (ParameterizedType) this.getClass().getGenericSuperclass();
        Type[] arguments = pt.getActualTypeArguments();
        return  (Class<T>) arguments[NumConstant.I0];
    }

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

    /**
     * <说明> 查询单条数据
     * @param entity S
     * @author ChenWei
     * @date 2021/3/22 17:59
     * @return S
     */
    public <S extends T> S findOne(S entity) {
        Example<S> example = Example.of(entity);
        return this.findOne(example).orElse(null);
    }

    /**
     * <说明> 查询多条数据
     * @param entity S
     * @author ChenWei
     * @date 2021/3/22 18:00
     * @return java.util.List<S>
     */
    public <S extends T> List<S> findAll(S entity) {
        Example<S> example = Example.of(entity);
        return this.findAll(example);
    }

    /**
     * <说明> 分页查询所有
     * @param entity S
     * @param pageable Pageable
     * @author ChenWei
     * @date 2021/3/22 18:00
     * @return org.springframework.data.domain.Page<S>
     */
    public <S extends T> Page<S> findAll(S entity, Pageable pageable) {
        Example<S> example = Example.of(entity);
        return this.findAll(example, pageable);
    }

    /**
     * <说明> 按属性查询所有
     * @param property 属性名
     * @param value 属性值
     * @author ChenWei
     * @date 2021/3/22 18:01
     * @return java.util.List<T>
     */
    public <S extends T> List<T> findAllByProperty(String property, Object value) {
        try {
            Class<T> domainClass = this.getCurDomainClass();
            T entity = domainClass.newInstance();
            // 默认查有效数据
            ConditionBuilder.builder().setNotDeletedAndDisabled(domainClass, entity);
            // 设置属性值
            Field field = null;
            try {
                field = domainClass.getDeclaredField(property);
            } catch (NoSuchFieldException e) {
                if (IdEntity.class.isAssignableFrom(domainClass)) {
                    field = domainClass.getSuperclass().getDeclaredField(property);
                }
            } finally {
                if (null == field) {
                    throw new NoSuchFieldException("property not exist");
                }
            }
            field.setAccessible(Boolean.TRUE);
            field.set(entity, value);
            Example<T> example = Example.of(entity);
            return this.myRepository.findAll(example);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * <说明> 按属性查询单挑数据
     * @param property 属性名
     * @param value 属性值
     * @author ChenWei
     * @date 2021/3/22 18:01
     * @return T
     */
    public <S extends T> T findByProperty(String property, Object value) {
        try {
            Class<T> domainClass = this.getCurDomainClass();
            T entity = domainClass.newInstance();
            // 默认查有效数据
            ConditionBuilder.builder().setNotDeletedAndDisabled(domainClass, entity);
            // 设置属性值
            // 设置属性值
            Field field = null;
            try {
                field = domainClass.getDeclaredField(property);
            } catch (NoSuchFieldException e) {
                if (IdEntity.class.isAssignableFrom(domainClass)) {
                    field = domainClass.getSuperclass().getDeclaredField(property);
                }
            } finally {
                if (null == field) {
                    throw new NoSuchFieldException("property not exist");
                }
            }
            field.setAccessible(Boolean.TRUE);
            field.set(entity, value);
            Example<T> example = Example.of(entity);
            List<T> all = this.myRepository.findAll(example);
            if (CollectionUtils.isEmpty(all)) {
                return null;
            }
            return all.get(NumConstant.I0);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * <说明> 暗属性查询
     * @param datas Map<String, Object>
     * @author ChenWei
     * @date 2021/3/22 18:03
     * @return java.util.List<T>
     */
    public <S extends T> List<T> findAllByProperty(Map<String, Object> datas) {
        try {
            Class<T> domainClass = this.getCurDomainClass();
            if (CollectionUtils.isEmpty(datas)) {
                return null;
            }
            T entity = domainClass.newInstance();
            // 默认查有效数据
            ConditionBuilder.builder().setNotDeletedAndDisabled(domainClass, entity);
            for (Map.Entry<String, Object> entry : datas.entrySet()) {
                // 设置属性值
                Field field = null;
                try {
                    field = domainClass.getDeclaredField(entry.getKey());
                } catch (NoSuchFieldException e) {
                    if (IdEntity.class.isAssignableFrom(domainClass)) {
                        field = domainClass.getSuperclass().getDeclaredField(entry.getKey());
                    }
                } finally {
                    if (null == field) {
                        throw new NoSuchFieldException("property not exist");
                    }
                }
                // 设置属性值
                field.setAccessible(Boolean.TRUE);
                field.set(entity, entry.getValue());
            }
            Example<T> example = Example.of(entity);
            return this.myRepository.findAll(example);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * <说明> 暗属性查询
     * @param datas Map<String, Object>
     * @author ChenWei
     * @date 2021/3/22 18:03
     * @return T
     */
    public <S extends T> T findByProperty(Map<String, Object> datas) {
        try {
            Class<T> domainClass = this.getCurDomainClass();
            if (CollectionUtils.isEmpty(datas)) {
                return null;
            }
            T entity = domainClass.newInstance();
            // 默认查有效数据
            ConditionBuilder.builder().setNotDeletedAndDisabled(domainClass, entity);
            for (Map.Entry<String, Object> entry : datas.entrySet()) {
                // 设置属性值
                Field field = null;
                try {
                    field = domainClass.getDeclaredField(entry.getKey());
                } catch (NoSuchFieldException e) {
                    if (IdEntity.class.isAssignableFrom(domainClass)) {
                        field = domainClass.getSuperclass().getDeclaredField(entry.getKey());
                    }
                } finally {
                    if (null == field) {
                        throw new NoSuchFieldException("property not exist");
                    }
                }
                // 设置属性值
                field.setAccessible(Boolean.TRUE);
                field.set(entity, entry.getValue());
            }
            Example<T> example = Example.of(entity);
            List<T> all = this.myRepository.findAll(example);
            if (CollectionUtils.isEmpty(all)) {
                return null;
            }
            return all.get(NumConstant.I0);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * <说明> 按属性模糊查询
     * @param property 属性
     * @param value 属性值
     * @author ChenWei
     * @date 2021/3/24 10:42
     * @return T
     */
    public <S extends T> List<T> findAllLike(String property, Object value) {
        try {
            Class<T> domainClass = this.getCurDomainClass();
            T entity = domainClass.newInstance();
            // 默认查有效数据
            ConditionBuilder.builder().setNotDeletedAndDisabled(domainClass, entity);
            // 设置属性值
            Field field = null;
            try {
                field = domainClass.getDeclaredField(property);
            } catch (NoSuchFieldException e) {
                if (IdEntity.class.isAssignableFrom(domainClass)) {
                    field = domainClass.getSuperclass().getDeclaredField(property);
                }
            } finally {
                if (null == field) {
                    throw new NoSuchFieldException("property not exist");
                }
            }
            // 设置属性值
            field.setAccessible(Boolean.TRUE);
            field.set(entity, value);
            ExampleMatcher matcher = ExampleMatcher.matching()
                    .withMatcher(property,
                            new ExampleMatcher.GenericPropertyMatcher().contains());
            Example<T> example = Example.of(entity, matcher);
            return myRepository.findAll(example);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * <说明> 按属性模糊查询
     * @param property 属性
     * @param value 属性值
     * @author ChenWei
     * @date 2021/3/24 10:42
     * @return T
     */
    public <S extends T> T findLike(String property, Object value) {
        try {
            Class<T> domainClass = this.getCurDomainClass();
            T entity = domainClass.newInstance();
            // 默认查有效数据
            ConditionBuilder.builder().setNotDeletedAndDisabled(domainClass, entity);
            // 设置属性值
            Field field = null;
            try {
                field = domainClass.getDeclaredField(property);
            } catch (NoSuchFieldException e) {
                if (IdEntity.class.isAssignableFrom(domainClass)) {
                    field = domainClass.getSuperclass().getDeclaredField(property);
                }
            } finally {
                if (null == field) {
                    throw new NoSuchFieldException("property not exist");
                }
            }
            // 设置属性值
            field.setAccessible(Boolean.TRUE);
            field.set(entity, value);
            ExampleMatcher matcher = ExampleMatcher.matching()
                    .withMatcher(property,
                            new ExampleMatcher.GenericPropertyMatcher().contains());
            Example<T> example = Example.of(entity, matcher);
            List<T> all = myRepository.findAll(example);
            if (CollectionUtils.isEmpty(all)) {
                return null;
            }
            return all.get(NumConstant.I0);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        return null;
    }
}
