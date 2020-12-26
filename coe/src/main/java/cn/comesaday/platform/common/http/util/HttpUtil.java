package cn.comesaday.platform.common.http.util;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.web.multipart.MultipartFile;
import cn.comesaday.platform.common.http.result.HttpResult;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <Descripe> io操作基础工具类
 *
 * @author: ChenWei
 * @CreateAt: 2020-08-10 16:48
 */
public class HttpUtil {

    /**
     * http请求
     * @param url String url
     * @param params Map<String, String> 参数
     * @param file MultipartFile 文件
     * @return 结果
     */
    private static HttpResult httpRequest(String url,
                                          Map<String, String> params, MultipartFile file) {
        HttpResult clientDto = new HttpResult();
        try {
            CloseableHttpClient client = HttpClients.createDefault();
            HttpPost httpPost = new HttpPost(url);
            httpPost.setHeader("User-Agent",
                    "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:76.0) Gecko/20100101 Firefox/76.0");
            // 连接参数
            httpPost.setConfig(bulidConfig());
            // 请求参数
            HttpEntity entity = bulidParams(params, file);
            httpPost.setEntity(entity);
            CloseableHttpResponse response = client.execute(httpPost);
            int statusCode = response.getStatusLine().getStatusCode();
            String content = null;
            if (statusCode == 200) {
                content = EntityUtils.toString(response.getEntity(), "UTF-8");
            }
            clientDto = new HttpResult(statusCode, content);
            response.close();
            client.close();
        } catch (IOException e) {
        }
        return clientDto;
    }

    /**
     * 参数设置
     * @param params Map<String, String> 参数
     * @param file MultipartFile
     * @return HttpEntity
     * @throws IOException
     */
    private static HttpEntity bulidParams(Map<String, String> params, MultipartFile file)
            throws IOException {
        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        builder.setCharset(Charset.forName("UTF-8"));
        //加上此行代码解决返回中文乱码问题
        builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
        // 文件流
        if (null != file) {
            builder.addBinaryBody("file", file.getInputStream(),
                    ContentType.MULTIPART_FORM_DATA, file.getOriginalFilename());
        }
        if (null != params && params.size() > 0) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                builder.addTextBody(entry.getKey(), entry.getValue());
            }
        }
        return builder.build();
    }

    /**
     * 连接配置
     * @return
     */
    private static RequestConfig bulidConfig() {
        RequestConfig config = RequestConfig.custom()
                .setSocketTimeout(5000)
                .setConnectionRequestTimeout(5000)
                .setConnectTimeout(5000)
                .build();
        return config;
    }

    /**
     * 返回结果
     * @param clientDto HttpResult
     * @return String
     */
    private static String getContent(HttpResult clientDto) {
        if (clientDto.getCode() == 200) {
            return clientDto.getBody();
        }
        return null;
    }

    /**
     * 结果转实体
     * @param url String
     * @param c Class<T>
     * @return
     */
    public static <T> T resForModel(String url, Class<T> c) {
        HttpResult clientDto = httpRequest(url, null, null);
        String content = getContent(clientDto);
        if (StringUtils.isNotBlank(content)) {
            return JSON.parseObject(content, c);
        }
        return null;
    }

    /**
     * 结果转实体
     * @param url String
     * @param c Class<T>
     * @return
     */
    public static <T> T resForModel(String url, Map<String, Object> paramsMap, Class<T> c) {
        Map<String, String> params = initParams(paramsMap);
        HttpResult clientDto = httpRequest(url, params, null);
        String content = getContent(clientDto);
        if (StringUtils.isNotBlank(content)) {
            return JSON.parseObject(content, c);
        }
        return null;
    }

    /**
     * 参数格式化
     * @param paramsMap
     * @return
     */
    private static Map<String, String> initParams(Map<String, Object> paramsMap) {
        if (null != paramsMap && paramsMap.size() > 0) {
            Map<String, String> params = new HashMap<>();
            for (Map.Entry<String, Object> entry : paramsMap.entrySet()) {
                Object object = entry.getValue();
                if (null != object) {
                    params.put(entry.getKey(), JSON.toJSONString(object));
                }
            }
            return params;
        }
        return null;
    }

    /**
     * 结果转实体
     * @param url String
     * @param c Class<T>
     * @return
     */
    public static <T> T resForModel(String url, Map<String, Object> paramsMap,
                                    MultipartFile file, Class<T> c) {
        Map<String, String> params = initParams(paramsMap);
        HttpResult clientDto = httpRequest(url, params, file);
        String content = getContent(clientDto);
        if (StringUtils.isNotBlank(content)) {
            return JSON.parseObject(content, c);
        }
        return null;
    }

    /**
     * 结果转实体
     * @param url String
     * @param c Class<T>
     * @return
     */
    public static <T> T resForModel(String url, MultipartFile file, Class<T> c) {
        HttpResult clientDto = httpRequest(url, null, file);
        String content = getContent(clientDto);
        if (StringUtils.isNotBlank(content)) {
            return JSON.parseObject(content, c);
        }
        return null;
    }

    /**
     * 结果转list
     * @param url String
     * @param c Class<T>
     * @return
     */
    public static <T> List<T> resForList(String url, Class<T> c) {
        HttpResult clientDto = httpRequest(url, null, null);
        String content = getContent(clientDto);
        if (StringUtils.isNotBlank(content)) {
            return JSON.parseArray(content, c);
        }
        return null;
    }

    /**
     * 结果转list
     * @param url String
     * @param c Class<T>
     * @return
     */
    public static <T> List<T> resForList(String url, Map<String, Object> paramsMap, Class<T> c) {
        Map<String, String> params = initParams(paramsMap);
        HttpResult clientDto = httpRequest(url, params, null);
        String content = getContent(clientDto);
        if (StringUtils.isNotBlank(content)) {
            return JSON.parseArray(content, c);
        }
        return null;
    }

    /**
     * 结果转list
     * @param url String
     * @param c Class<T>
     * @return
     */
    public static <T> List<T> resForList(String url, Map<String, Object> paramsMap,
                                         MultipartFile file, Class<T> c) {
        Map<String, String> params = initParams(paramsMap);
        HttpResult clientDto = httpRequest(url, params, file);
        String content = getContent(clientDto);
        if (StringUtils.isNotBlank(content)) {
            return JSON.parseArray(content, c);
        }
        return null;
    }

    /**
     * 结果转list
     * @param url String
     * @param c Class<T>
     * @return
     */
    public static <T> List<T> resForList(String url, MultipartFile file, Class<T> c) {
        HttpResult clientDto = httpRequest(url, null, file);
        String content = getContent(clientDto);
        if (StringUtils.isNotBlank(content)) {
            return JSON.parseArray(content, c);
        }
        return null;
    }

    /**
     * str结果
     * @param url String
     * @return
     */
    public static String resForStr(String url) {
        HttpResult clientDto = httpRequest(url, null, null);
        return getContent(clientDto);
    }

    /**
     * str结果
     * @param url String
     * @return
     */
    public static String resForStr(String url, Map<String, Object> paramsMap) {
        Map<String, String> params = initParams(paramsMap);
        HttpResult clientDto = httpRequest(url, params, null);
        return getContent(clientDto);
    }

    /**
     * str结果
     * @param url String
     * @return
     */
    public static String resForStr(String url, Map<String, Object> paramsMap, MultipartFile file) {
        Map<String, String> params = initParams(paramsMap);
        HttpResult clientDto = httpRequest(url, params, file);
        return getContent(clientDto);
    }

    /**
     * str结果
     * @param url String
     * @return
     */
    public static String resForStr(String url, MultipartFile file) {
        HttpResult clientDto = httpRequest(url, null, file);
        return getContent(clientDto);
    }

}
