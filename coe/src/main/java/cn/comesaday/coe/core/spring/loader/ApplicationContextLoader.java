package cn.comesaday.coe.core.spring.loader;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * <描述> ApplicationContextLoader
 * <详细背景>
 * @author: ChenWei
 * @CreateAt: 2021-04-14 15:10
 */
@Component
public class ApplicationContextLoader implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    /**
     * <说明> 获取上下文环境中的bean
     * @param name bean名称
     * @author ChenWei
     * @date 2021/4/14 15:16
     * @return T
     */
    public <T> T getBean(String name) throws BeansException {
        if (null == applicationContext) {
            return null;
        }
        return (T) applicationContext.getBean(name);
    }

    /**
     * <说明> 获取上下文环境中的bean
     * @param clazz bean字节码
     * @author ChenWei
     * @date 2021/4/14 15:16
     * @return T
     */
    public <T> T getBean(Class<T> clazz) throws BeansException {
        if (null == applicationContext) {
            return null;
        }
        return (T) applicationContext.getBean(clazz);
    }
}
