package cn.comesaday.coe.core.jpa.bean.repository.impl;

import cn.comesaday.coe.core.jpa.bean.repository.MyRepository;
import cn.comesaday.coe.core.jpa.constant.JpaConstant;
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
 * @author ChenWei
 * @CreateAt 2020-08-30 17:22
 */
@Transactional(readOnly = true)
public class MyRepositoryImpl<T, ID extends Serializable>
        extends SimpleJpaRepository<T, ID> implements MyRepository<T, ID> {

    private EntityManager entityManager;

    private Class<T> domainClass;

    public MyRepositoryImpl(Class<T> domainClass, EntityManager entityManager) {
        super(domainClass, entityManager);
        this.domainClass = domainClass;
        this.entityManager = entityManager;
    }

    /**
     * <说明> 保存或更新
     * @param entity S
     * @author ChenWei
     * @date 2021/3/22 19:18
     * @return S
     */
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
                entity = this.update(entity);
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

    /**
     * <说明> 删除数据
     * @param entity T
     * @author ChenWei
     * @date 2021/3/22 19:20
     * @return void
     */
    @Override
    public void delete(T entity) {
        this.delete(entity, Boolean.FALSE);
    }

    /**
     * <说明> 保存或更新多条数据
     * @param entities Iterable<S>
     * @author ChenWei
     * @date 2021/3/22 19:20
     * @return java.util.List<S>
     */
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

    /**
     * <说明> 逻辑删除
     * @param entity T
     * @param realDel Boolean
     * @author ChenWei
     * @date 2021/3/22 19:21
     * @return T
     */
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

    /**
     * <说明> 更新数据
     * @param entity S
     * @author ChenWei
     * @date 2021/3/22 19:21
     * @return S
     */
    public <S extends T> S update(S entity) {
        try {
            Class<?> clazz = entity.getClass();
            Field field = clazz.getSuperclass().getDeclaredField(JpaConstant.Field.UPDATEAT);
            field.setAccessible(Boolean.FALSE);
            field.set(entity, new Date());
            return super.save(entity);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }
}
