package 后端;

public class string转map {
	　　　Gson gson = new Gson();
    Map<String, Object> map = new HashMap<String, Object>();
    map = gson.fromJson(jsonString, map.getClass());#关键
    String goodsid=(String) map.get("goods_id");
    System.out.println("map的值为:"+goodsid);
}
<!-- 配置gson -->
<dependency>
    <groupId>com.google.code.gson</groupId>
    <artifactId>gson</artifactId>
    <version>2.2.4</version>
</dependency>