package ���뵼��;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import javax.swing.filechooser.FileSystemView;

public class ��ȡ�̷���С {
	public static String FormetFileSize(long fileS) {
	       DecimalFormat df = new DecimalFormat("#.00");
	       String fileSizeString = "";
	       if (fileS < 1024) {
	           fileSizeString = df.format((double) fileS) + "B";
	       } else if (fileS < 1048576) {
	           fileSizeString = df.format((double) fileS / 1024) + "K";
	       } else if (fileS < 1073741824) {
	           fileSizeString = df.format((double) fileS / 1048576) + "M";
	       } else {
	           fileSizeString = df.format((double) fileS / 1073741824) + "G";
	       }
	       return fileSizeString;
	   }
	
	   /**
	    * ��ȡӲ�̵�ÿ���̷�
	    */
	   public static void driver(){
		   List<Double> list = new ArrayList<>();
		   HashMap<Integer, Double> map =new HashMap<>();
		   
	       // ��ǰ�ļ�ϵͳ��
	       FileSystemView fsv = FileSystemView.getFileSystemView();
	       // �г�����windows ����
	       File[] fs = File.listRoots();
	       // ��ʾ���̾��
	       for (int i = 0; i < fs.length; i++) {
	           System.out.println(fsv.getSystemDisplayName(fs[i]));
	           System.out.print("�ܴ�С" + FormetFileSize(fs[i].getTotalSpace()));
	           System.out.println("ʣ��" + FormetFileSize(fs[i].getFreeSpace()));
	           if(FormetFileSize(fs[i].getFreeSpace()).contains("G")) {
	        	   String[] split = FormetFileSize(fs[i].getFreeSpace()).split("G");
	        	   list.add(Double.parseDouble(split[0]));
	        	   map.put(i,Double.parseDouble(split[0]));
	           }
	       }
	       //��ȡ�����̷�
       try {
	    	   Collections.sort(list);
		       Double double1 = list.get(list.size()-1);
		       List<Integer> list2= new ArrayList<>();
		       for(Integer key: map.keySet()){
		           if(map.get(key).equals(double1)){
		        	   list2.add(key);
		           }
		       }
		       String systemDisplayName = fsv.getSystemDisplayName( fs[list2.get(0)]);
		     
		} catch (Exception e) {
			// TODO: handle exception
		}
	      
	  
	   }
	   public static void main(String[] args) {
	    driver();
	}
}

