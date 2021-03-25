package cn.comesaday.coe.core.basic.builder;

import cn.comesaday.coe.core.basic.model.IdEntity;
import cn.comesaday.coe.core.jpa.constant.JpaConstant;

import java.lang.reflect.Field;

/**
 * <描述> 条件设定器
 * <详细背景>
 * @author: ChenWei
 * @CreateAt: 2021-03-24 10:44
 */
public class ConditionBuilder<T> {
    
    public static <T> ConditionBuilder<T> builder() {
        return new ConditionBuilder<>();
    }

    /**
     * <说明> 设置默认查询条件:未删除、未禁用
     * @param domainClass Class<T>
     * @param entity T
     * @author ChenWei
     * @date 2021/3/24 10:33
     * @return void
     */
    public <T> void setNotDeletedAndDisabled(Class<T> domainClass, T entity)
            throws NoSuchFieldException, IllegalAccessException {
        // 默认查有效数据
        Field isDelete = null;
        Field isDisable = null;
        if (IdEntity.class.isAssignableFrom(domainClass)) {
            isDelete =  domainClass.getSuperclass().getDeclaredField(JpaConstant.Field.ISDELETED);
            isDisable =  domainClass.getSuperclass().getDeclaredField(JpaConstant.Field.ISDISABLED);
        } else {
            isDelete =  domainClass.getDeclaredField(JpaConstant.Field.ISDELETED);
            isDisable =  domainClass.getDeclaredField(JpaConstant.Field.ISDISABLED);
        }
        isDelete.setAccessible(Boolean.TRUE);
        isDelete.set(entity, Boolean.FALSE);
        isDisable.setAccessible(Boolean.TRUE);
        isDisable.set(entity, Boolean.FALSE);
    }

    /**
     * <说明> 设置查询条件：查是否删除
     * @param domainClass Class<T>
     * @param entity T
     * @author ChenWei
     * @date 2021/3/24 10:33
     * @return void
     */
    public <T> void setIsDeleted(Class<T> domainClass, T entity, Boolean isDeleted)
            throws NoSuchFieldException, IllegalAccessException {
        // 默认查有效数据
        Field isDelete = null;
        if (IdEntity.class.isAssignableFrom(domainClass)) {
            isDelete =  domainClass.getSuperclass().getDeclaredField(JpaConstant.Field.ISDELETED);
        } else {
            isDelete =  domainClass.getDeclaredField(JpaConstant.Field.ISDELETED);
        }
        isDelete.setAccessible(Boolean.TRUE);
        isDelete.set(entity, isDeleted);
    }

    /**
     * <说明> 设置查询条件:查是否禁用
     * @param domainClass Class<T>
     * @param entity T
     * @author ChenWei
     * @date 2021/3/24 10:33
     * @return void
     */
    public <T> void setIsDisabled(Class<T> domainClass, T entity, Boolean isDisabled)
            throws NoSuchFieldException, IllegalAccessException {
        // 默认查有效数据
        Field isDisable = null;
        if (IdEntity.class.isAssignableFrom(domainClass)) {
            isDisable =  domainClass.getSuperclass().getDeclaredField(JpaConstant.Field.ISDISABLED);
        } else {
            isDisable =  domainClass.getDeclaredField(JpaConstant.Field.ISDISABLED);
        }
        isDisable.setAccessible(Boolean.TRUE);
        isDisable.set(entity, isDisabled);
    }
}
