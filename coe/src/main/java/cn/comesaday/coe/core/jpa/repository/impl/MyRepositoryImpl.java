package cn.comesaday.coe.core.jpa.repository.impl;

import cn.comesaday.coe.core.jpa.constant.JpaConstant;
import cn.comesaday.coe.core.jpa.repository.MyRepository;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Date;

/**
 * <Description>
 *
 * @author ChenWei
 * @CreateAt 2020-08-30 17:22
 */
@Transactional(readOnly = true)
public class MyRepositoryImpl<T, ID extends Serializable>
        extends SimpleJpaRepository<T, ID> implements MyRepository<T, ID> {

    private EntityManager entityManager;

    public MyRepositoryImpl(Class<T> domainClass, EntityManager entityManager) {
        super(domainClass, entityManager);
        this.entityManager = entityManager;
    }

    @Override
    public <S extends T> S save(S entity) {
        if (null == entity) return null;
        try {
            Example<S> example = Example.of(entity);
            S oldEntity = findOne(example).orElse(null);
            if (null == oldEntity) {
                entity = super.save(entity);
            } else {
                Field updateAt = entity.getClass().getDeclaredField(JpaConstant.Field.UPDATEAT);
                updateAt.setAccessible(Boolean.TRUE);
                updateAt.set(entity, new Date());
                entity = super.save(oldEntity);
            }
            return entity;
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }
}
