package ���;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class string {

	 
    /**  
     * ��ȡ����������  
     * */  
    private static String[] getFiledName(Object o){  
        Field[] fields=o.getClass().getDeclaredFields();  
        String[] fieldNames=new String[fields.length];  
        for(int i=0;i<fields.length;i++){  
//            System.out.println(fields[i].getType());  
            fieldNames[i]=fields[i].getName();  
        }  
        return fieldNames;  
    }  

    /* ������������ȡ����ֵ  
     * */  
    private static Object getFieldValueByName(String fieldName, Object o) {  
        try {    
            String firstLetter = fieldName.substring(0, 1).toUpperCase();    
            String getter = "get" + firstLetter + fieldName.substring(1);    
            Method method = o.getClass().getMethod(getter, new Class[] {});    
            Object value = method.invoke(o, new Object[] {});    
            return value;    
        } catch (Exception e) {    
          
            return null;    
        }    
    }   
    public static void main(String[] args) {
    	StringBuilder sbName = new StringBuilder();
		StringBuilder sbValue = new StringBuilder();
		Person person = new Person("zhangsan", 30, "��");
		System.out.println(person);
		String[] fieldNames = getFiledName(person);
		 
		  for(int j=0 ; j<fieldNames.length ; j++){     //������������
	               String name = fieldNames[j];    //��ȡ���Ե�����
	               Object value = getFieldValueByName(name,person);
	               sbName.append(name);
	               sbValue.append(value);
	                if(j != fieldNames.length - 1) {
	                	sbName.append("///");
	                	sbValue.append("///");
	                }
	        }
	      
	        System.out.println("attribute name:"+sbName.toString()); 
	        System.out.println("attribute value:"+sbValue.toString()); 

	}

}
