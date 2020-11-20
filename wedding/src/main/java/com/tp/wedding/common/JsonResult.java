package com.tp.wedding.common;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Created by agpc on 2017/10/31.
 * Json封装查询结果
 */
public class JsonResult {

    /**
     * 返回结果状态常量：正常
     */
    public static final int STATUS_OK = 200;
    /**
     * 返回结果状态常量：参数错误
     */
    public static final int STATUS_PARAM_ERROR = 400;
    /**
     * 返回结果状态常量：未登录
     */
    public static final int STATUS_NOLOGIN = 401;
    /**
     * 返回结果状态常量：禁止访问
     */
    public static final int STATUS_FORBIDDEN = 403;
    /**
     * 返回结果状态常量：查询出错
     */
    public static final int STATUS_QUERY_ERROR = 500;
    /**
     * 返回结果状态常量：服务器异常
     */
    public static final int STATUS_SERVER_EXCEPTION = 503;

    /**
     * 返回结果状态常量：签名异常
     */
    public static final int STATUS_SIGN_ERROR = 506;

    /**
     * 返回结果状态常量：淘宝未授权
     */
    public static final int STATUS_NOT_AUTHORIZE = 4007;

    /**
     * 返回结果状态常量：拼多多未授权
     */
    public static final int STATUS_PDD_NOT_AUTHORIZE = 60001;


    // 定义jackson对象
    private static final ObjectMapper MAPPER = new ObjectMapper();

    // 响应业务状态
    private Integer status;

    // 响应消息
    private String msg;

    // 响应中的数据
    private Object data;

    public static JsonResult build(Integer status, String msg, Object data) {
        return new JsonResult(status, msg, data);
    }

    public static JsonResult ok(Object data) {
        return new JsonResult(data);
    }

    public static JsonResult ok() {
        return new JsonResult(null);
    }

    public JsonResult() {

    }

    public static JsonResult build(Integer status, String msg) {
        return new JsonResult(status, msg, null);
    }

    public JsonResult(Integer status, String msg, Object data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    public JsonResult(Object data) {
        this.status = 200;
        this.msg = "OK";
        this.data = data;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    /**
     * 将json结果集转化为TaotaoResult对象
     *
     * @param jsonData json数据
     * @param clazz    TaotaoResult中的object类型
     * @return
     */
    public static JsonResult formatToPojo(String jsonData, Class<?> clazz) {
        try {
            if (clazz == null) {
                return MAPPER.readValue(jsonData, JsonResult.class);
            }
            JsonNode jsonNode = MAPPER.readTree(jsonData);
            JsonNode data = jsonNode.get("data");
            Object obj = null;
            if (clazz != null) {
                if (data.isObject()) {
                    obj = MAPPER.readValue(data.traverse(), clazz);
                } else if (data.isTextual()) {
                    obj = MAPPER.readValue(data.asText(), clazz);
                }
            }
            return build(jsonNode.get("status").intValue(), jsonNode.get("msg").asText(), obj);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 没有object对象的转化
     *
     * @param json
     * @return
     */
    public static JsonResult format(String json) {
        try {
            return MAPPER.readValue(json, JsonResult.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Object是集合转化
     *
     * @param jsonData json数据
     * @param clazz    集合中的类型
     * @return
     */
    public static JsonResult formatToList(String jsonData, Class<?> clazz) {
        try {
            JsonNode jsonNode = MAPPER.readTree(jsonData);
            JsonNode data = jsonNode.get("data");
            Object obj = null;
            if (data.isArray() && data.size() > 0) {
                obj = MAPPER.readValue(data.traverse(),
                        MAPPER.getTypeFactory().constructCollectionType(List.class, clazz));
            }
            return build(jsonNode.get("status").intValue(), jsonNode.get("msg").asText(), obj);
        } catch (Exception e) {
            return null;
        }
    }

    public static <T> List<T> getListFromJsonResultData(JsonResult jsonResult, String key, Class<T> beanType) {
        List<T> list;
        String convert = JsonUtils.objectToJson(jsonResult.getData());
        Map dataMap = JSONObject.parseObject(convert, Map.class);
        if (dataMap == null || dataMap.isEmpty()) {
            return Collections.emptyList();
        }
        convert = JsonUtils.objectToJson(dataMap.get(key));
        list = JSONObject.parseArray(convert,beanType);

        if (list == null || list.isEmpty()) {
            return Collections.emptyList();
        }
        return list;
    }

    public static List<Map> getListFromJsonResultDataContent(JsonResult jsonResult,String key) {
        List<Map> list;
        String convert = JsonUtils.objectToJson(jsonResult.getData());
        Map dataMap = JsonUtils.jsonToPojo(convert, Map.class);
        if (dataMap == null || dataMap.isEmpty()) {
            return Collections.emptyList();
        }
        convert = JsonUtils.objectToJson(dataMap.get(key));
        list = JsonUtils.jsonToList(convert, Map.class);
        if (list == null || list.isEmpty()) {
            return Collections.emptyList();
        }

        return list;
    }

    public static void resetJsonResultContent(JsonResult jsonResult, Object object,String key) {
        String data = JsonUtils.objectToJson(jsonResult.getData());
        Map dataMap = JsonUtils.jsonToPojo(data, Map.class);
        if (dataMap != null) {
            dataMap.put(key, object);
        }
        jsonResult.setData(dataMap);
    }
}
