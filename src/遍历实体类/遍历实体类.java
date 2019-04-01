package ����ʵ����;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ����ʵ���� {
	
	<dependency>
	<groupId>commons-beanutils</groupId>
	<artifactId>commons-beanutils</artifactId>

	<version>1.9.3</version>
	</dependency>
	
	//java�б���ʵ���࣬��ȡ������������ֵ
    public static void testReflect(Object model) throws Exception{
        for (Field field : model.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            System.out.println(field.getName() + ":" + field.get(model) );
            }
    }
    
    public static void  test2(Object obj) {
    	//��ȡ����
    	System.out.println(obj.class);
        try {
        	//��ȡ����
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
	 * @param obj ʵ����
	 * @param value ���ڽ�������ֵ  value������ʱΪ��
	 * @return
	 */
	public static Map<String,Object> getFields(Object obj,Object value){
		Field[] field = obj.getClass().getDeclaredFields(); // ��ȡʵ������������ԣ�����Field����
		Map<String,Object> map = new HashMap<>();
        try {  
            for (int j = 0; j < field.length; j++) { // ������������  
                String name1 = field[j].getName(); // ��ȡ���Ե�����  
                String name = name1.substring(0, 1).toUpperCase() + name1.substring(1); // �����Ե����ַ���д�����㹹��get��set����  
                String type = field[j].getGenericType().toString(); // ��ȡ���Ե�����  
                if (type.equals("class java.lang.String")) { // ���type�������ͣ���ǰ�����"class "�����������  
                    Method m = obj.getClass().getMethod("get" + name);  
                     value = (String) m.invoke(obj); // ����getter������ȡ����ֵ
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
                }  // �������Ҫ,���Է������������������,�����Ӷ��������͵��ж�
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
	 * key ��objʵ��������� ��value��ֵ ������key��ʵ������ֵ
	 * @param obj
	 * @param key
	 * @param value
	 * @return
	 */
	public Object setRoom(Object obj, String key, String value) {
		Field[] field = obj.getClass().getDeclaredFields(); // ��ȡʵ������������ԣ�����Field����
		try {  
			for (int j = 0; j < field.length; j++) { // ������������  
				String name1 = field[j].getName(); // ��ȡ���Ե�����  
				if(name1.equals(key)) {
					String name = name1.substring(0, 1).toUpperCase() + name1.substring(1); // �����Ե����ַ���д�����㹹��get��set����  
					String type = field[j].getGenericType().toString(); // ��ȡ���Ե�����  
					if (type.equals("class java.lang.String")) { // ���type�������ͣ���ǰ�����"class "�����������  
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
					}   // �������Ҫ,���Է������������������,�����Ӷ��������͵��ж�
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
