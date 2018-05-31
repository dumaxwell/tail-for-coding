import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.ArrayList;

public class ReadingLogs {
    public static void read(String[] commArgs) {
        //-f 命令
        if(commArgs[1].contains("f")){
            readF(commArgs[2]);
        }
        //-n 命令
        else if(commArgs[1].contains("n")){
            Pattern p=Pattern.compile("\\d+");
            Matcher m=p.matcher(commArgs[1]);
            m.find();

            //获取-n后的数字
            int n = Integer.valueOf(m.group());

            readN(n, commArgs[2]);
        }
        else {
            System.out.println("unknow command error.");
        }
    }

    //-f命令
    private static void readF(String path){
        //        String charset = "UTF-8";
        String charset = "GBK";
        boolean end = false;

        try {
            InputStream fileInputStream = new FileInputStream(path);
            File file = new File(path);
            file.length();
            Reader fileReader = new InputStreamReader(fileInputStream, charset);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String singleLine;

            while(true){
                while(!end){
                    if(bufferedReader.readLine() != null)
                        continue;
                    end = true;
                }
                if( end && (singleLine = bufferedReader.readLine()) != null ){
                    System.out.println(singleLine);
                    continue;
                }

                try {
                    Thread.sleep(10L);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }

            bufferedReader.close();
            //bluishoul@gmail.com
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //-n 命令
    private static void readN(int n, String path) {
        try {
            //读取此刻所有文件内容
            ArrayList<String> strings = (ArrayList<String>) Files.readAllLines(Paths.get(path));
            //读取最后n行
            for(int i = strings.size() - Math.min(n, strings.size()); i<strings.size(); i++) {
                System.out.println(strings.get(i));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
