package com.hdk.oj.service.impI;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hdk.oj.entity.AnswerSample;
import com.hdk.oj.mapper.AnswerSampleMapper;
import com.hdk.oj.service.AnswerSampleService;
import org.springframework.stereotype.Service;

import java.io.*;

@Service
public class AnswerSampleServiceImpl extends ServiceImpl<AnswerSampleMapper, AnswerSample> implements AnswerSampleService {


    private String javaPath = "D:\\Envoriment\\jdk8\\bin";
    private String basePath = "E:\\oj_grpgram\\oj";
    private String result;

    public String getResult() {
        return result;
    }

    @Override
    public String exe(String filename, String arg){
        try {

            Runtime rt = Runtime.getRuntime();//获得Runtime对象
            String arr[] = {"CLASSPATH="+basePath+"/answer/sample/java/"+filename,"Path="+javaPath};//执行exec时的环境变量

            //exec方法第一个参数是执行的命令，第二个参数是环境变量，第三个参数是工作目录
            Process pr = rt.exec("cmd /c javac Main.java && java Main"+arg,
                    arr,
                    new File("./answer/sample/java/"+filename));

            BufferedWriter bout = new BufferedWriter(new OutputStreamWriter(pr.getOutputStream()));
//        bout.write("1 22222222222222222222222222222222");//输出数据
            bout.close();//关闭流

            //SequenceInputStream是一个串联流，能够把两个流结合起来，通过该对象就可以将
            //getInputStream方法和getErrorStream方法获取到的流一起进行查看了，当然也可以单独操作
            SequenceInputStream sis = new SequenceInputStream(pr.getInputStream(), pr.getErrorStream());
            InputStreamReader inst = new InputStreamReader(sis, "GBK");//设置编码格式并转换为输入流
            BufferedReader br = new BufferedReader(inst);//输入流缓冲区

            String res = null;
            StringBuilder sb = new StringBuilder();
            while ((res = br.readLine()) != null) {
                sb.append(res+"\n");
            }
            br.close();
            pr.waitFor();
            pr.destroy();
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void createFile(String path, String filename, String content) {
        String dicName = path+filename+"/";
        //读取文件夹路径
        File dicfile = new File(dicName);
        File file = new File(dicName  + "Main.java");
        //判断是否存在
        if (!dicfile.exists() && !dicfile.isDirectory()) {
            try {
                System.out.println("文件夹不存在！");
                dicfile.mkdir();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        try {
            if (!file.exists()) {
                file.createNewFile();
                System.out.println(file.getPath());
            }
            wirteFile(file.getPath(),content);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void wirteFile(String path, String content) {
        byte[] b = content.getBytes();
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(path);
            // 指明要写入数据的文件，如果指定的路径中不存在StudentNew.java这样的文件，则系统会自动创建一个
            out.write(b);
            // 调用write(int c)方法把读取到的字符全部写入到指定文件中去
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String readFile(String path) {
        try {
            BufferedReader in = new BufferedReader(new FileReader(path+"/Main.java"));
            String s;
            String content = "";
            while ((s = in.readLine()) != null) {
                content = content + s+"\n";
            }
            System.out.println(content);
            in.close();
            return content;
        } catch (IOException e) {
        }
        return null;
    }

}
