package 遍历实体类;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class 遍历实体类 {
	
	<dependency>
	<groupId>commons-beanutils</groupId>
	<artifactId>commons-beanutils</artifactId>

	<version>1.9.3</version>
	</dependency>
	
	//java中遍历实体类，获取属性名和属性值
    public static void testReflect(Object model) throws Exception{
        for (Field field : model.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            System.out.println(field.getName() + ":" + field.get(model) );
            }
    }
    
    public static void  test2(Object obj) {
    	//获取类名
    	System.out.println(obj.class);
        try {
        	//获取参数
            PropertyUtilsBean propertyUtilsBean = new PropertyUtilsBean();
            PropertyDescriptor[] descriptors = propertyUtilsBean.getPropertyDescriptors(obj);
            for (int i = 0; i < descriptors.length; i++) {
                String name = descriptors[i].getName();
                if (!"class".equals(name)) {
                  System.out.println(name+":"+ propertyUtilsBean.getNestedProperty(obj, name));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
}
    
    /**
	 * 
	 * @param obj 实体类
	 * @param value 用于接收属性值  value传过来时为空
	 * @return
	 */
	public static Map<String,Object> getFields(Object obj,Object value){
		Field[] field = obj.getClass().getDeclaredFields(); // 获取实体类的所有属性，返回Field数组
		Map<String,Object> map = new HashMap<>();
        try {  
            for (int j = 0; j < field.length; j++) { // 遍历所有属性  
                String name1 = field[j].getName(); // 获取属性的名字  
                String name = name1.substring(0, 1).toUpperCase() + name1.substring(1); // 将属性的首字符大写，方便构造get，set方法  
                String type = field[j].getGenericType().toString(); // 获取属性的类型  
                if (type.equals("class java.lang.String")) { // 如果type是类类型，则前面包含"class "，后面跟类名  
                    Method m = obj.getClass().getMethod("get" + name);  
                     value = (String) m.invoke(obj); // 调用getter方法获取属性值
                    	map.put(name1, value);
                } else if (type.equals("class java.lang.Integer")) {  
                    Method m = obj.getClass().getMethod("get" + name);  
                     value = (Integer) m.invoke(obj);
                     map.put(name1, value);
//                     if(value == null){
//                    	 value = 0;
//                     }
                } else if (type.equals("class java.lang.Long")) {  
                    Method m = obj.getClass().getMethod("get" + name);  
                     value = (Long) m.invoke(obj);  
                     map.put(name1, value);
                } else if (type.equals("class java.util.Date")) {  
                    Method m = obj.getClass().getMethod("get" + name);  
                     value = (Date) m.invoke(obj);
//                     if(value == null){
//                    	 map.put("netType", 2);
//                     }
                     map.put(name1, value);
                }  // 如果有需要,可以仿照上面继续进行扩充,再增加对其它类型的判断
//                if(!name1.equals("serialVersionUID")){
//                }
            }  
        } catch (NoSuchMethodException e) {  
            e.printStackTrace();  
        } catch (SecurityException e) {  
            e.printStackTrace();  
        } catch (IllegalAccessException e) {  
            e.printStackTrace();  
        } catch (IllegalArgumentException e) {  
            e.printStackTrace();  
        } catch (InvocationTargetException e) {  
            e.printStackTrace();  
        }
        map.remove("serialVersionUID");
//        map.remove("id");
		return map; 
	}
	/**
	 * key 是obj实体类的属性 ，value是值 给根据key给实体类设值
	 * @param obj
	 * @param key
	 * @param value
	 * @return
	 */
	public Object setRoom(Object obj, String key, String value) {
		Field[] field = obj.getClass().getDeclaredFields(); // 获取实体类的所有属性，返回Field数组
		try {  
			for (int j = 0; j < field.length; j++) { // 遍历所有属性  
				String name1 = field[j].getName(); // 获取属性的名字  
				if(name1.equals(key)) {
					String name = name1.substring(0, 1).toUpperCase() + name1.substring(1); // 将属性的首字符大写，方便构造get，set方法  
					String type = field[j].getGenericType().toString(); // 获取属性的类型  
					if (type.equals("class java.lang.String")) { // 如果type是类类型，则前面包含"class "，后面跟类名  
						Method set = obj.getClass().getMethod("set" + name,String.class);
						if(value != null && !"".equals(value)){
							set.invoke(obj, value);
						}
						break;
					} else if (type.equals("class java.lang.Integer")) {  
						Method set = obj.getClass().getMethod("set" + name,Integer.class);
		                if(value != null && !"".equals(value)){
		                	 set.invoke(obj, Integer.parseInt(value));
		                }
		                break;
					} else if (type.equals("class java.lang.Long")) {  
						Method set = obj.getClass().getMethod("set" + name,Long.class); 
						if(value != null && !"".equals(value)){
		                	 set.invoke(obj, Long.parseLong(value));
		                }
						break;
					}   // 如果有需要,可以仿照上面继续进行扩充,再增加对其它类型的判断
	                if(!name1.equals("serialVersionUID")){
	                }
				} else {
					continue;
				} 
			}
		} catch (NoSuchMethodException e) {  
			e.printStackTrace();  
		} catch (SecurityException e) {  
			e.printStackTrace();  
		} catch (IllegalAccessException e) {  
			e.printStackTrace();  
		} catch (IllegalArgumentException e) {  
			e.printStackTrace();  
		} catch (InvocationTargetException e) {  
			e.printStackTrace();  
		}
		return obj;
	}
}
