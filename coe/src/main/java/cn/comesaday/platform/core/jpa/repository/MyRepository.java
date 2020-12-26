package cn.comesaday.platform.core.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import java.io.Serializable;
import java.util.List;

/**
 * <Description>
 *
 * @author ChenWei
 * @CreateAt 2020-08-30 17:21
 */
@NoRepositoryBean
public interface MyRepository<T, ID extends Serializable>
        extends JpaRepository<T, ID> {

    T findByProperty(String property, Object value);

    List<T> findAllByProperty(String property, Object value);

    Object[] findOne(String sql);

    List<Object[]> findAll(String sql);

}
