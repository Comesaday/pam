package cn.comesaday.platform.core.jpa.repository.impl;

import cn.comesaday.platform.core.jpa.repository.MyRepository;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import javax.persistence.EntityManager;
import java.io.Serializable;
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
    public T findByProperty(String property, Object value) {
        Assert.notNull(property, "the property can not null!");
        return null;
    }

    @Override
    public List<T> findAllByProperty(String property, Object value) {
        Assert.notNull(property, "the property can not null!");
        return null;
    }

    @Override
    public Object[] findOne(String sql) {
        Assert.notNull(sql, "the sql can not null!");
        List<Object[]> result = entityManager.createNativeQuery(sql).getResultList();
        if (null != result && result.size() > 0) {
            return result.get(0);
        }
        return null;
    }

    @Override
    public List<Object[]> findAll(String sql) {
        Assert.notNull(sql, "the sql can not null!");
        return entityManager.createNativeQuery(sql).getResultList();
    }
}
