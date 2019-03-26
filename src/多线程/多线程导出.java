package ���߳�;

public class ���̵߳��� {

}
package com.qmhd.telecom.common.photo;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * ���߳����غͶϵ�����
 *
 * @Author: wpf
 * @Date: 16:42 2018/6/12
 * @Description: 
 * @param  * @param null  
 * @return   
 */
public class MutiDownloadTest1 {
    private static final int    THREAD_COUNT    = 5;
    private static final String    DOWNLOAD_URL    = "http://down.360safe.com/se/360se9.1.0.426.exe";
    private static final String    fileName        = "E:\\3.exe";
     static final String    filePath        = "E:\\1";

    public static void main(String[] args) {

        long fileSize = 0;
        HttpURLConnection connection = null;
        try {
            connection = (HttpURLConnection)new URL(DOWNLOAD_URL).openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(8000);
            connection.setReadTimeout(8000);

            /**
             * ������ɹ�ʱ,����http״̬��200
             */
            if (connection.getResponseCode() == 200) {
                /**
                 * ��һ��RandomAccessFile�ļ�,�򿪷�ʽΪ��д(rw)
                 * setLength�����ڴ洢�豸ռ��һ��ռ�,��ֹ���ص�һ��ռ䲻��
                 */
                RandomAccessFile randomAccessFile = new RandomAccessFile(fileName , "rw");
                fileSize = connection.getContentLength();
                randomAccessFile.setLength(fileSize);
                randomAccessFile.close();

                long eachSize = fileSize/THREAD_COUNT;
                for(int i=0; i<THREAD_COUNT; i++){
                    long startIndex = i*eachSize;
                    long endIndex = (i+1)*eachSize - 1;
                    /**
                     * ��ʱ���һ���̵߳�ʱ��,endIndex��ֵ�����ļ���С
                     */
                    if(i == THREAD_COUNT - 1){
                        endIndex = fileSize;
                    }

                    Runnable runnable = new DownloadThreadTest1(DOWNLOAD_URL,fileName,i, startIndex, endIndex);
                    new Thread(runnable).start();

                }
            }

        } catch (IOException e) {
            e.printStackTrace();
            // log.info(ExceptionUtils.getFullStackTrace(e));
        } finally {
            if(null != connection){
                connection.disconnect();
            }
        }
    }
}

class DownloadThreadTest1 implements Runnable {

    // private static final Logger log = Logger.getLogger(DownloadThreadTest.class);

    private String url;
    private String fileName;

    private int  threadId;

    private long startIndex;
    private long endIndex;

    private HttpURLConnection httpURLConnection;
    private RandomAccessFile randomAccessFile;
    private InputStream inputStream;

    public DownloadThreadTest1(String url, String fileName, int threadId, long startIndex, long endIndex) {
        super();
        this.url = url;
        this.fileName = fileName;
        this.threadId = threadId;
        this.startIndex = startIndex;
        this.endIndex = endIndex;

    }

    @Override
    public void run() {
        RandomAccessFile downThreadStream = null;
        /*
             * �鿴��ʱ�ļ�
             */
        File downThreadFile = new File(MutiDownloadTest1.filePath, "wpf_thread_"+threadId+".dt");
        try {

            if(downThreadFile.exists()){
                downThreadStream = new RandomAccessFile(downThreadFile,"rwd");
                String startIndex_str = downThreadStream.readLine();
                if(null == startIndex_str || "".equals(startIndex_str)){
                } else {
                    this.startIndex = Long.parseLong(startIndex_str) - 1; // //�����������
                }
            } else {
                downThreadStream = new RandomAccessFile(downThreadFile, "rwd");
            }


            httpURLConnection = (HttpURLConnection) new URL(url + "?ts=" + System.currentTimeMillis()).openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setConnectTimeout(8000);
            httpURLConnection.setReadTimeout(8000);
            /**
             * ��������Χ.
             */
            httpURLConnection.setRequestProperty("RANGE", "bytes=" + startIndex + "-" + endIndex);

            /**
             * �����󲿷����ݳɹ���ʱ��,����http״̬��206
             */
            if (httpURLConnection.getResponseCode() == 206){

                inputStream = httpURLConnection.getInputStream();
                randomAccessFile = new RandomAccessFile(fileName, "rwd");
                /**
                 * �ѿ�ʼд��λ������ΪstartIndex,���������ݵ�λ��һ��
                 */
                randomAccessFile.seek(startIndex);

                byte[] bytes = new byte[1024];
                int len;
                int total = 0;
                while((len = inputStream.read(bytes)) != -1){
                    total += len;
                    //log.info("�߳�" + threadId + ":" + total);
//                    System.out.println("�߳�: " +  threadId + ":" + total);
                    randomAccessFile.write(bytes, 0, len);

                     /*
                         * ����ǰ���ڵ���λ�ñ��浽�ļ���
                         */
                    downThreadStream.seek(0);
                    downThreadStream.write((startIndex + total + "").getBytes("UTF-8"));
                }


            }
        } catch (IOException e) {
            e.printStackTrace();
            // log.info(ExceptionUtils.getFullStackTrace(e));
        } finally {
            try {
                if(null != downThreadStream){
                    downThreadStream.close();

                }
                if(null != httpURLConnection){
                    httpURLConnection.disconnect();
                }
                if(null != inputStream){
                    inputStream.close();
                }
                if(null != randomAccessFile){
                    randomAccessFile.close();
                }
//                cleanTemp(downThreadFile);
            } catch (Exception e) {
                e.printStackTrace();
                //log.info(ExceptionUtils.getFullStackTrace(e));
            }
        }
    }

    //ɾ���̲߳�������ʱ�ļ�
    private  void cleanTemp(File file){
        file.delete();
    }
}