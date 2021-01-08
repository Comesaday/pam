package cn.comesaday.coe.core.jpa.util;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.beans.PropertyDescriptor;
import java.util.HashSet;
import java.util.Set;

/**
 * <描述> jpa工具类
 * <详细背景>
 *
 * @author: ChenWei
 * @CreateAt: 2021-01-08 16:57
 */
public class JpaUtil<T> {

    public static String[] getNullProperties(Object object) {
        BeanWrapper beanWrapper = new BeanWrapperImpl(object);
        PropertyDescriptor[] properties = beanWrapper.getPropertyDescriptors();
        Set<String> names = new HashSet<>();
        for (PropertyDescriptor descriptor : properties) {
            String name = descriptor.getName();
            Object value = beanWrapper.getPropertyValue(name);
            if (null == value) {
                names.add(name);
            }
        }
        String[] results = new String[names.size()];
        return names.toArray(results);
    }
}
