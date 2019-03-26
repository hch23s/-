package 导入导出;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class 获取文件夹下所有文件名 {
        /**节点名称**/
        private String text; 
        /**节点路径**/
        private String fileName; 
         /**子节点**/
        private List<获取文件夹下所有文件名> children = new ArrayList<获取文件夹下所有文件名>();
    
    public static 获取文件夹下所有文件名 readfile(获取文件夹下所有文件名 tn){
        try {

            File file = new File(tn.fileName);
            System.out.println( file.getName());
            tn.text = file.getName();
            if (!file.isDirectory()) {
                //System.out.println(file.getName());
                
            } else if (file.isDirectory()) {
                String[] filelist = file.list();
                for (int i = 0; i < filelist.length; i++) {
                    File readfile = new File(tn.fileName + "\\" + filelist[i]);
                    获取文件夹下所有文件名 tn1 = new 获取文件夹下所有文件名();
                    tn1.text = readfile.getName();
                    tn1.fileName = tn.fileName + "\\" + filelist[i];
                    if (!readfile.isDirectory()) {
                        tn.children.add(tn1);
                    } else if (readfile.isDirectory()) {
                        tn.children.add(readfile(tn1));
                    }
                }
            }

        } catch (Exception e) {
            System.out.println("readfile()   Exception:" + e.getMessage());
        }
        return tn;
    }
    
    public static void main(String[] args){
        获取文件夹下所有文件名 tn = new 获取文件夹下所有文件名();
        tn.fileName = "C:\\Users\\Administrator\\Downloads";
        readfile(tn);
        System.out.println(tn);
        System.out.println(tn.text);
    }
}