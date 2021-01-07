package cn.comesaday.coe.core.basic.app;

import cn.comesaday.coe.core.jpa.factory.MyRepositoryFactoryBean;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * <Description>
 *
 * @author ChenWei
 * @CreateAt 2020-08-30 21:55
 */

// 服务注册
@EnableDiscoveryClient
// rpc调用
@EnableFeignClients
// jpa功能扩展
@EnableJpaRepositories(repositoryFactoryBeanClass = MyRepositoryFactoryBean.class)
// 自动装配
@SpringBootApplication(scanBasePackages = {"cn.comesaday.*"})
// 注解扫描
@ComponentScan(value = "cn.comesaday.*")
// 事务控制
@EnableTransactionManagement
public class BasicApplication {

}
