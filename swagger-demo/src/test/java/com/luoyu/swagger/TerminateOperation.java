package com.luoyu.swagger;


import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import net.sf.json.JSONObject;

import java.util.List;

/**
 * @author ${林锋鹏}
 * @Title: TerminateOperation
 * @ProjectName springboot-demo
 * @Description: TODO
 * @date 2021/8/5 21:33
 */
public class TerminateOperation {

    public static void main(String[] args) {

       String str ="\n" +
               "{\n" +
               "    \"error\": 0,\n" +
               "    \"status\": \"success\",\n" +
               "    \"results\": [\n" +
               "        {\n" +
               "            \"currentCity\": \"青岛\",\n" +
               "            \"index\": [\n" +
               "                {\n" +
               "                    \"title\": \"穿衣\",\n" +
               "                    \"zs\": \"较冷\",\n" +
               "                    \"tipt\": \"穿衣指数\",\n" +
               "                    \"des\": \"建议着厚外套加毛衣等服装。年老体弱者宜着大衣、呢外套加羊毛衫。\"\n" +
               "                },\n" +
               "                {\n" +
               "                    \"title\": \"紫外线强度\",\n" +
               "                    \"zs\": \"中等\",\n" +
               "                    \"tipt\": \"紫外线强度指数\",\n" +
               "                    \"des\": \"属中等强度紫外线辐射天气，外出时建议涂擦SPF高于15、PA+的防晒护肤品，戴帽子、太阳镜。\"\n" +
               "                }\n" +
               "            ]\n" +
               " \n" +
               "        }\n" +
               "    ]\n" +
               "}";

        JSONObject jsonObject=JSONObject.fromObject(str);
        JsonObject stu=(JsonObject)JSONObject.toBean(jsonObject, JsonObject.class);
        System.out.println(stu.toString());
        System.out.println("===================================");
        com.alibaba.fastjson.JSONObject jsonObject1 = com.alibaba.fastjson.JSONObject.parseObject(str);
        System.out.println(jsonObject1);
        System.out.println("===================================");
        Gson json = new Gson();
        JsonElement jsonElement = JsonParser.parseString(str);
        JsonObject respBean = json.fromJson(jsonElement, JsonObject.class);
        List<JsonObject.results> results = respBean.getResults();
        System.out.println(respBean);

    }
}
