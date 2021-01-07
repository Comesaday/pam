package cn.comesaday.coe.core.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;

/**
 * <Description>
 *
 * @author ChenWei
 * @CreateAt 2020-08-30 17:21
 */
@NoRepositoryBean
public interface MyRepository<T, ID extends Serializable>
        extends JpaRepository<T, ID> {

    @Override
    <S extends T> S save(S entity);

}
