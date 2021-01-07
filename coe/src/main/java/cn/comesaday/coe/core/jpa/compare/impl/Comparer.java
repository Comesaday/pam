package cn.comesaday.coe.core.jpa.compare.impl;

import cn.comesaday.coe.core.jpa.compare.Compareable;
import cn.comesaday.coe.core.jpa.constant.JpaConstant;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;

/**
 * <描述> 创建比较器
 * <详细背景>
 *
 * @author: ChenWei
 * @CreateAt: 2021-01-07 19:28
 */
@Service
public class Comparer<T> implements Compareable<T> {

    @Override
    public <S extends T> S create(S entity) {
        try {
            Field createAt = entity.getClass()
                    .getSuperclass().getDeclaredField(JpaConstant.Field.CREATEAT);
            createAt.setAccessible(Boolean.TRUE);
            createAt.set(entity, null);
            return (S) entity;
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }
}
