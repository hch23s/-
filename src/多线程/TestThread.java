package ���߳�;
public class TestThread implements Runnable{ 
     
    boolean stop = false; 
    public static void main(String[] args) throws Exception { 
        Thread thread = new Thread(new TestThread(),"My Thread"); 
        System.out.println( "Starting thread..." ); 
        thread.start(); 
        Thread.sleep( 3000 ); 
        System.out.println( "Interrupting thread..." ); 
        thread.interrupt(); 
        System.out.println("�߳��Ƿ��жϣ�" + thread.isInterrupted()); 
        Thread.sleep( 3000 ); 
        System.out.println("Stopping application..." ); 
    } 
    public void run() { 
        while(!stop){ 
            System.out.println( "My Thread is running..." ); 
            // �ø�ѭ������һ��ʱ�䣬ʹ����Ļ���ӡ�����ٵ� 
            long time = System.currentTimeMillis(); 
            while((System.currentTimeMillis()-time < 1000)) { 
            } 
        } 
        System.out.println("My Thread exiting under request..." ); 
    } 
} 