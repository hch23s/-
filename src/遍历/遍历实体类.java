package ����;

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
}
