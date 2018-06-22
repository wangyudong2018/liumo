package com.yiyun.utils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.util.*;

/**
 * Json与javaBean之间的转换工具类
 * <p>
 * <p>
 * {@code   现使用json-lib组件实现
 * 需要
 * json-lib-2.4-jdk15.jar
 * ezmorph-1.0.6.jar
 * commons-collections-3.1.jar
 * commons-lang-2.0.jar
 * 支持
 * }
 */
public class JsonPluginsUtil {

    /**
     * String转JSON字符串
     *
     * @param key
     * @param value
     * @return
     */
    public static String stringToJson(String key, String value) {
        if (StringUtils.isEmpty(key) || StringUtils.isEmpty(value)) {
            return null;
        }
        Map<String, String> map = new HashMap<String, String>(16);
        map.put(key, value);
        return beanToJson(map);
    }

    /**
     * 从一个JSON 对象字符格式中得到一个java对象
     *
     * @param jsonString
     * @param beanCalss
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> T jsonToBean(String jsonString, Class<T> beanCalss) {

        JSONObject jsonObject = JSONObject.fromObject(jsonString);
        T bean = (T) JSONObject.toBean(jsonObject, beanCalss);

        return bean;

    }

    /**
     * 将java对象转换成json字符串
     *
     * @param bean
     * @return
     */
    public static String beanToJson(Object bean) {

        JSONObject json = JSONObject.fromObject(bean);

        return json.toString();

    }

    /**
     * 将java对象转换成json字符串
     *
     * @param bean
     * @return
     */
    public static String beanToJson(Object bean, String[] _nory_changes,
                                    boolean nory) {

        JSONObject json = null;

        if (nory) {// 转换_nory_changes里的属性

            Field[] fields = bean.getClass().getDeclaredFields();
            StringBuilder str = new StringBuilder();
            for (Field field : fields) {
                // //System.out.println(field.getName());
                str.append(":" + field.getName());
            }
            fields = bean.getClass().getSuperclass().getDeclaredFields();
            for (Field field : fields) {
                // //System.out.println(field.getName());
                str.append(":" + field.getName());
            }
            str.append(":");
            for (String s : _nory_changes) {
                final String tempStr = str.toString();
                str.append(tempStr.replace(":" + s + ":", ":"));
            }
            final String tempStr = str.toString();
            json = JSONObject.fromObject(bean, configJson(tempStr.split(":")));

        } else {// 转换除了_nory_changes里的属性

            json = JSONObject.fromObject(bean, configJson(_nory_changes));
        }

        return json.toString();
    }

    private static JsonConfig configJson(String[] excludes) {

        JsonConfig jsonConfig = new JsonConfig();

        jsonConfig.setExcludes(excludes);
        //
        jsonConfig.setIgnoreDefaultExcludes(false);

        return jsonConfig;

    }

    /**
     * 将java对象List集合转换成json字符串
     *
     * @param beans
     * @return
     */
    @SuppressWarnings("unchecked")
    public static String beanListToJson(List beans) {

        StringBuffer rest = new StringBuffer();

        rest.append("[");

        int size = beans.size();

        for (int i = 0; i < size; i++) {

            rest.append(beanToJson(beans.get(i)) + ((i < size - 1) ? "," : ""));

        }

        rest.append("]");

        return rest.toString();

    }

    /**
     * @param beans
     * @param _nory_changes
     * @return
     */
    @SuppressWarnings("unchecked")
    public static String beanListToJson(List beans, String[] _nory_changes,
                                        boolean nory) {

        StringBuffer rest = new StringBuffer();

        rest.append("[");

        int size = beans.size();

        for (int i = 0; i < size; i++) {
            try {
                rest.append(beanToJson(beans.get(i), _nory_changes, nory));
                if (i < size - 1) {
                    rest.append(",");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        rest.append("]");

        return rest.toString();

    }

    /**
     * 从json HASH表达式中获取一个map，改map支持嵌套功能
     *
     * @param jsonString
     * @return
     */
    @SuppressWarnings({"unchecked"})
    public static Map jsonToMap(String jsonString) {

        JSONObject jsonObject = JSONObject.fromObject(jsonString);
        Iterator keyIter = jsonObject.keys();
        String key;
        Object value;
        Map valueMap = new HashMap();

        while (keyIter.hasNext()) {

            key = (String) keyIter.next();
            value = jsonObject.get(key).toString();
            valueMap.put(key, value);

        }

        return valueMap;
    }

    /**
     * map集合转换成json格式数据
     *
     * @param map
     * @return
     */
    public static String mapToJson(Map<String, ?> map, String[] _nory_changes,
                                   boolean nory) {

        StringBuilder s_json = new StringBuilder().append("{");

//		Set<String> key = map.keySet();
        map.forEach((key, value) -> {
//			String s = (String) it.next();
//            if (value == null) { } else
            if (value instanceof List<?>) {
                s_json.append(key).append(":").append(JsonPluginsUtil.beanListToJson(
                        (List<?>) value, _nory_changes, nory));
            } else {
                JSONObject json = JSONObject.fromObject(map);
                s_json.append(key).append(":").append(json.toString());
            }

            s_json.append(",");
        });
        s_json.substring(0,(s_json.lastIndexOf(",")-1));
        s_json.append("}");
        return s_json.toString();
    }

    /**
     * 从json数组中得到相应java数组
     *
     * @param jsonString
     * @return
     */
    public static Object[] jsonToObjectArray(String jsonString) {

        JSONArray jsonArray = JSONArray.fromObject(jsonString);

        return jsonArray.toArray();

    }

    public static String listToJson(List<?> list) {

        JSONArray jsonArray = JSONArray.fromObject(list);

        return jsonArray.toString();

    }

    /**
     * 从json对象集合表达式中得到一个java对象列表
     *
     * @param jsonString
     * @param beanClass
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> List<T> jsonToBeanList(String jsonString,
                                             Class<T> beanClass) {

        JSONArray jsonArray = JSONArray.fromObject(jsonString);
        JSONObject jsonObject;
        T bean;
        int size = jsonArray.size();
        List<T> list = new ArrayList<T>(size);

        for (int i = 0; i < size; i++) {

            jsonObject = jsonArray.getJSONObject(i);
            bean = (T) JSONObject.toBean(jsonObject, beanClass);
            list.add(bean);

        }

        return list;

    }

    /**
     * 从json数组中解析出java字符串数组
     *
     * @param jsonString
     * @return
     */
    public static String[] jsonToStringArray(String jsonString) {

        JSONArray jsonArray = JSONArray.fromObject(jsonString);
        String[] stringArray = new String[jsonArray.size()];
        int size = jsonArray.size();

        for (int i = 0; i < size; i++) {

            stringArray[i] = jsonArray.getString(i);

        }

        return stringArray;
    }

    /**
     * 从json数组中解析出javaLong型对象数组
     *
     * @param jsonString
     * @return
     */
    public static Long[] jsonToLongArray(String jsonString) {

        JSONArray jsonArray = JSONArray.fromObject(jsonString);
        int size = jsonArray.size();
        Long[] longArray = new Long[size];

        for (int i = 0; i < size; i++) {

            longArray[i] = jsonArray.getLong(i);

        }

        return longArray;

    }

    /**
     * 从json数组中解析出java Integer型对象数组
     *
     * @param jsonString
     * @return
     */
    public static Integer[] jsonToIntegerArray(String jsonString) {

        JSONArray jsonArray = JSONArray.fromObject(jsonString);
        int size = jsonArray.size();
        Integer[] integerArray = new Integer[size];

        for (int i = 0; i < size; i++) {

            integerArray[i] = jsonArray.getInt(i);

        }

        return integerArray;

    }

    /**
     * 从json数组中解析出java Double型对象数组
     *
     * @param jsonString
     * @return
     */
    public static Double[] jsonToDoubleArray(String jsonString) {

        JSONArray jsonArray = JSONArray.fromObject(jsonString);
        int size = jsonArray.size();
        Double[] doubleArray = new Double[size];

        for (int i = 0; i < size; i++) {

            doubleArray[i] = jsonArray.getDouble(i);

        }

        return doubleArray;

    }

    public static void main(String[] args) {

    }

}