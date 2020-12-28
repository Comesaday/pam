package cn.comesaday.coe.core.jpa.repository.impl;

import cn.comesaday.coe.core.jpa.repository.MyRepository;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.io.Serializable;

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
}
