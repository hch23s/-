package ���;

public class stringתmap {
	������Gson gson = new Gson();
    Map<String, Object> map = new HashMap<String, Object>();
    map = gson.fromJson(jsonString, map.getClass());#�ؼ�
    String goodsid=(String) map.get("goods_id");
    System.out.println("map��ֵΪ:"+goodsid);
}
<!-- ����gson -->
<dependency>
    <groupId>com.google.code.gson</groupId>
    <artifactId>gson</artifactId>
    <version>2.2.4</version>
</dependency>