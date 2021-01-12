package cn.comesaday.coe.core.jpa.bean.repository.impl;

import cn.comesaday.coe.core.jpa.constant.JpaConstant;
import cn.comesaday.coe.core.jpa.bean.repository.MyRepository;
import cn.comesaday.coe.common.util.BeanUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

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
            Field Id = entity.getClass().getSuperclass()
                    .getDeclaredField(JpaConstant.Field.ID);
            Id.setAccessible(Boolean.TRUE);
            Object val = Id.get(entity);
            if (null != val) {
                // Id字段有值则更新，无则插入
                entity = this.update(entity, val);
            } else {
                // 设置创建时间
                Field createAt = entity.getClass().getSuperclass()
                        .getDeclaredField(JpaConstant.Field.CREATEAT);
                createAt.setAccessible(Boolean.TRUE);
                createAt.set(entity, new Date());
                entity = super.save(entity);
            }
            return entity;
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void delete(T entity) {
        this.delete(entity, Boolean.FALSE);
    }

    @Override
    public <S extends T> List<S> saveAll(Iterable<S> entities) {
        Iterator iterator = entities.iterator();
        List<S> result = new ArrayList();
        while (iterator.hasNext()) {
            S entity = (S) iterator.next();
            result.add(this.save(entity));
        }
        return result;
    }

    private T delete(T entity, Boolean realDel) {
        if (null == entity) return null;
        try {
            if (null == realDel || !realDel) {
                // 逻辑删除
                Field isDeleted = entity.getClass().getSuperclass()
                        .getDeclaredField(JpaConstant.Field.ISDELETED);
                isDeleted.setAccessible(Boolean.TRUE);
                isDeleted.set(entity, Boolean.TRUE);
                entity = this.save(entity);
            } else {
                // 物理删除
                super.delete(entity);
            }
            return entity;
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    public <S extends T> S update(S entity, Object Id) {
        try {
            S tempEntity = (S) entity.getClass().newInstance();
            Field ID = tempEntity.getClass().getSuperclass()
                    .getDeclaredField(JpaConstant.Field.ID);
            ID.setAccessible(Boolean.TRUE);
            ID.set(tempEntity, Id);
            Example<S> example = Example.of(tempEntity);
            S oldEntity = super.findOne(example).orElse(null);
            if (null == oldEntity) {
                return null;
            } else {
                // 设置更新时间
                Field updateAt = entity.getClass().getSuperclass()
                        .getDeclaredField(JpaConstant.Field.UPDATEAT);
                updateAt.setAccessible(Boolean.TRUE);
                updateAt.set(entity, new Date());
                BeanUtils.copyProperties(entity, oldEntity, BeanUtil.getNullProperties(entity));
                return super.save(oldEntity);
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        return null;
    }
}
