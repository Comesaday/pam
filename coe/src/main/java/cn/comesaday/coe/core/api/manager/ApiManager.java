package cn.comesaday.coe.core.api.manager;

import cn.comesaday.coe.core.api.model.Api;
import cn.comesaday.coe.core.jpa.repository.MyRepository;
import org.springframework.stereotype.Repository;

/**
 * <描述> api
 * <详细背景>
 *
 * @author: ChenWei
 * @CreateAt: 2020-12-31 15:49
 */
@Repository
public interface ApiManager extends MyRepository<Api, Long> {
}
