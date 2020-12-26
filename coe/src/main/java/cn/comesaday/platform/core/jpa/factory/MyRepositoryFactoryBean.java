package cn.comesaday.platform.core.jpa.factory;

import cn.comesaday.platform.core.basic.model.IdEntity;
import cn.comesaday.platform.core.jpa.repository.impl.MyRepositoryImpl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactory;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactoryBean;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.data.repository.core.RepositoryInformation;
import org.springframework.data.repository.core.RepositoryMetadata;
import org.springframework.data.repository.core.support.RepositoryFactorySupport;
import javax.persistence.EntityManager;
import java.io.Serializable;

/**
 * <Description>
 *
 * @author ChenWei
 * @CreateAt 2020-08-30 17:28
 */
public class MyRepositoryFactoryBean<R extends JpaRepository<T, I>, T, I extends Serializable>
        extends JpaRepositoryFactoryBean<R, T, I> {

    public MyRepositoryFactoryBean(Class<? extends R> repositoryInterface) {
        super(repositoryInterface);
    }

    @Override
    protected RepositoryFactorySupport createRepositoryFactory(EntityManager entityManager) {
        return new MyRepositoryFactory(entityManager);
    }

    public static class MyRepositoryFactory<T, I extends Serializable>
            extends JpaRepositoryFactory {

        private final EntityManager entityManager;

        public MyRepositoryFactory(EntityManager entityManager) {
            super(entityManager);
            this.entityManager = entityManager;
        }

        @Override
        protected Object getTargetRepository(RepositoryInformation information) {
            Class<T> domainClass = (Class<T>) information.getDomainType();
            if(IdEntity.class.isAssignableFrom(domainClass)) {
                return new MyRepositoryImpl<>(domainClass, entityManager);
            } else {
                return new SimpleJpaRepository(domainClass, entityManager);
            }
        }

        @Override
        protected Class<?> getRepositoryBaseClass(RepositoryMetadata metadata) {
            return metadata.getDomainType().isAssignableFrom(IdEntity.class) ?
                    MyRepositoryImpl.class : SimpleJpaRepository.class;
        }
    }
}
