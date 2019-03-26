package com.qmhd.telecom.aspect;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;
public class DownLoad {
    // ��������·������ɫ����2��
    public static final String PATH = "http://103.249.52.4:9990/upload/dd9d7272-34e6-46bb-923d-83955daacc82.jpg";
    public static int threadCount = 0;// �����߳�����
    public static void main(String[] args) {
        try {
            URL url = new URL(PATH);
            // ��ȡ����
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            // ͨ����ȡ���Ӷ����ļ���
            String[] str = PATH.split("/");
            String fileName = str[4];
            // ��ȡ�����ļ���С
            int fileLength = conn.getContentLength();
            System.out.println(fileName);
            // �ڱ��ش���һ�����������Сһ�µĿ����д���ļ�
            RandomAccessFile raf = new RandomAccessFile(fileName, "rwd");
            System.out.println(fileLength);// ������
            raf.setLength(fileLength);
            // �Զ����߳�����
            threadCount = 3;
            // ����ÿ���߳��������ݵĴ�С
            int blockSize = fileLength / threadCount;
            // �����߳�����
            for (int threadId = 1; threadId <= threadCount; threadId++) {
                // ���Ĵ��룬����ÿ���߳̿�ʼ�Լ�����������λ��
                int startPos = (threadId - 1) * blockSize;// ��ʼ���ص�λ��
                int endPos = (threadId * blockSize) - 1;// �������ص�λ�ã����������һ�飩
                if (threadCount == threadId) {
                    endPos = fileLength;
                }
                new Thread(new DownLoadThread(threadId, startPos, endPos, PATH))
                        .start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ʵ�������߳�
    static class DownLoadThread implements Runnable {
        private int threadId;
        private int startPos;
        private int endPos;
        private String path;
        public DownLoadThread(int threadId, int startPos, int endPos,
                String path) {
            super();
            this.threadId = threadId;
            this.startPos = startPos;
            this.endPos = endPos;
            this.path = path;
        }
        public void run() {
            try {
                URL url = new URL(path);
                String[] str = PATH.split("/");
                String fileName = str[4];
                HttpURLConnection conn = (HttpURLConnection) url
                        .openConnection();
                // ����URL����ķ���������ο�API��
                conn.setRequestMethod("GET");
                // ����500����Ϊ��ʱֵ
                conn.setReadTimeout(5000);
                File file = new File(threadId + ".txt");
                if (file.exists() && file.length() > 0) {
                    BufferedReader br = new BufferedReader(
                            new InputStreamReader(new FileInputStream(file)));
                    String saveStartPos = br.readLine();
                    if (saveStartPos != null && saveStartPos.length() > 0) {
                        startPos = Integer.parseInt(saveStartPos);
                    }
                }
                // ע��˫�����ڵĸ�ʽ�����ܰ����ո񣨵������ַ���������416
                conn.setRequestProperty("Range", "bytes=" + startPos + "-"
                        + endPos);
                RandomAccessFile raf = new RandomAccessFile(fileName, "rwd");// �洢�����ļ������д���ļ�
                raf.seek(startPos);// ���ÿ�ʼ���ص�λ��
                System.out.println("�߳�" + threadId + ":" + startPos + "~~"
                        + endPos);
                InputStream is = conn.getInputStream();
                byte[] b = new byte[1024 * 1024 * 10];
                int len = -1;
                int newPos = startPos;
                while ((len = is.read(b)) != -1) {
                    RandomAccessFile rr = new RandomAccessFile(file, "rwd");// �洢���ر�ǵ��ļ�
                    raf.write(b, 0, len);
                    // �����ر�Ǵ���ָ���ĵ�
                    String savaPoint = String.valueOf(newPos += len);
                    rr.write(savaPoint.getBytes());
                    rr.close();
                }
                is.close();
                raf.close();
                System.out.println("�������");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}