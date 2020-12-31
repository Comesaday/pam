package cn.comesaday.coe.core.api.service;

import cn.comesaday.coe.core.api.manager.ApiManager;
import cn.comesaday.coe.core.api.model.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <描述>
 * <详细背景>
 *
 * @author: ChenWei
 * @CreateAt: 2020-12-31 15:50
 */
@Service
public class ApiService {

    @Autowired
    private ApiManager apiManager;

    @Autowired
    private WebApplicationContext webApplicationContext;

    public List<Api> apis() {
        RequestMappingHandlerMapping mapping = webApplicationContext.getBean(RequestMappingHandlerMapping.class);
        List<Api> apis = new ArrayList<>();
        Map<RequestMappingInfo, HandlerMethod> map = mapping.getHandlerMethods();
        for (RequestMappingInfo info : map.keySet()) {
            Set<String> urls = info.getPatternsCondition().getPatterns();
            for (String url : urls) {
                Api api = new Api(url);
                apiManager.saveAndFlush(api);
                apis.add(api);
            }
        }
        return apis;
    }
}
