package cn.comesaday.coe.enhance.jdbc;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * <Description>
 *
 * @author ChenWei
 * @CreateAt 2020-08-29 23:54
 */
@Component
public class JdbcEnhance {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public <T> List<T> queryForList(String sql, Class<T> clazz) {
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<T>(clazz));
    }
}
