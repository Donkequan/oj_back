package com.hdk.oj;

import com.hdk.oj.result.R;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;
import java.util.concurrent.TimeUnit;

@SpringBootTest
class OjApplicationTests {

    @Test
    public void t0() throws IOException, InterruptedException {
        Runtime rt = Runtime.getRuntime();//获得Runtime对象
        String arr[] = {"CLASSPATH=D://IDEA/oj/answer/java","Path=D:\\Environment\\jdk1.8\\bin"};//执行exec时的环境变量

        //exec方法第一个参数是执行的命令，第二个参数是环境变量，第三个参数是工作目录
        Process pr = rt.exec("cmd /c javac Main.java && java Main aaaaa", arr, new File("./answer/java"));

        //获取输出流并转换成缓冲区
        BufferedWriter bout = new BufferedWriter(new OutputStreamWriter(pr.getOutputStream()));
//        bout.write("1 22222222222222222222222222222222");//输出数据
        bout.close();//关闭流

        //SequenceInputStream是一个串联流，能够把两个流结合起来，通过该对象就可以将
        //getInputStream方法和getErrorStream方法获取到的流一起进行查看了，当然也可以单独操作
        SequenceInputStream sis = new SequenceInputStream(pr.getInputStream(), pr.getErrorStream());
        InputStreamReader inst = new InputStreamReader(sis, "utf-8");//设置编码格式并转换为输入流
        BufferedReader br = new BufferedReader(inst);//输入流缓冲区

        String res = null;
        StringBuilder sb = new StringBuilder();
        while ((res = br.readLine()) != null) {//循环读取缓冲区中的数据
            sb.append(res+"\n");
        }
        br.close();
        pr.waitFor();
        pr.destroy();
        System.out.print(sb);//输出获取的数据
    }

    @Test
    public void tp(){
        try {
            List<String> list = new ArrayList<String>();
            ProcessBuilder pb = null;
            Process p = null;
            String line = null;
            BufferedReader stdout = null;

            //list the files and directorys under C:\
            list.add("CMD.EXE");
            list.add("/C");
            list.add("dir");
            pb = new ProcessBuilder(list);
            pb.directory(new File("D:\\"));
            p = pb.start();

            stdout = new BufferedReader(new InputStreamReader(p
                    .getInputStream()));
            while ((line = stdout.readLine()) != null) {
                System.out.println(line);
            }
            stdout.close();

            //echo the value of NAME
            pb = new ProcessBuilder();
//            pb.command(new String[] {"CMD.exe", "/C", "echo %NAME%"});
//            pb.environment().put("NAME", "TEST");
            p = pb.start();

            stdout = new BufferedReader(new InputStreamReader(p
                    .getInputStream()));
            while ((line = stdout.readLine()) != null) {
                System.out.println(line);
            }
            stdout.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void t3() throws ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        // 设置class文件所在根路径
        // 例如/usr/java/classes下有一个test.App类，则/usr/java/classes即这个类的根路径，而.class文件的实际位置是/usr/java/classes/test/App.class
        File clazzPath = new File("D:/IDEA/oj");

        // 记录加载.class文件的数量
        int clazzCount = 0;

        if (clazzPath.exists() && clazzPath.isDirectory()) {
            // 获取路径长度
            int clazzPathLen = clazzPath.getAbsolutePath().length() + 1;

            Stack<File> stack = new Stack<>();
            stack.push(clazzPath);

            // 遍历类路径
            while (stack.isEmpty() == false) {
                File path = stack.pop();
                File[] classFiles = path.listFiles(new FileFilter() {
                    public boolean accept(File pathname) {
                        return pathname.isDirectory() || pathname.getName().endsWith(".class");
                    }
                });
                if (clazzCount ++ == 0) {
                    Method method = URLClassLoader.class.getDeclaredMethod("addURL", URL.class);
                    boolean accessible = method.isAccessible();
                    try {
                        if (accessible == false) {
                            method.setAccessible(true);
                        }
                        // 设置类加载器
                        URLClassLoader classLoader = (URLClassLoader) ClassLoader.getSystemClassLoader();
                        // 将当前类路径加入到类加载器中
                        method.invoke(classLoader, clazzPath.toURI().toURL());
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    } finally {
                        method.setAccessible(accessible);
                    }
                }
                // 加载Class类
                Class<?> main = Class.forName("Main");
                Method main1 = main.getMethod("main");
                main1.invoke(null, new Object[]{});
            }
        }

    }

    @Test
    public void t2() throws InterruptedException {
        while(true) {
            try {
                URL[] urls = new URL[]{new URL("file",null,"D:/IDEA/oj/Main.class")};
                URLClassLoader loader = new URLClassLoader(urls);
                Class c = loader.loadClass("Main");
                c.getMethod("main").invoke(null, new Object[]{});

            } catch (Exception e) {
                e.printStackTrace();
            }
            TimeUnit.SECONDS.sleep(5);
        }
    }
//
//    @Test
//    public void test(){
//        Runtime run =Runtime.getRuntime();
//        try {
//            Process p = run.exec("java -version");
//            InputStream ins= p.getInputStream();
//            InputStream ers= p.getErrorStream();
//            new Thread(new inputStreamThread(ins)).start();
//            p.waitFor();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//    }
//    class inputStreamThread implements Runnable{
//        private InputStream ins = null;
//        private BufferedReader bfr = null;
//        public inputStreamThread(InputStream ins){
//            this.ins = ins;
//            this.bfr = new BufferedReader(new InputStreamReader(ins));
//        }
//        @Override
//        public void run() {
//            String line = null;
//            byte[] b = new byte[100];
//            int num = 0;
//            try {
//                while((num=ins.read(b))!=-1){
//                    System.out.println(new String(b,"gb2312"));
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    @Test
//    void test1() {
//        Runtime runtime = Runtime.getRuntime();
//        try {
//            BufferedReader br = new BufferedReader(new InputStreamReader(runtime.exec("dir").getInputStream()));
//            //StringBuffer b = new StringBuffer();
//            String line=null;
//            StringBuffer b=new StringBuffer();
//            while ((line=br.readLine())!=null) {
//                b.append(line+"\n");
//            }
//            System.out.println(b.toString());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    void writeFileContent(String file , String content) {
//        byte[] b = content.getBytes();
//        FileOutputStream out = null;
//        try {
//            out = new FileOutputStream(file);
//            // 指明要写入数据的文件，如果指定的路径中不存在StudentNew.java这样的文件，则系统会自动创建一个
//            out.write(b);
//                // 调用write(int c)方法把读取到的字符全部写入到指定文件中去
//            out.close();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

}
