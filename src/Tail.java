import java.io.*;
import java.nio.channels.FileChannel;
import java.util.regex.Pattern;

public class Tail {
    static final String DEF_PATH = "c:\\test.log";
    public static void main(String[] args) {

        String path = DEF_PATH;
        System.out.println("先创建好文件");
        System.out.println("只支持如下命令，不同os都用\\做分隔符:");
        System.out.println("tail -n10 c:\\test.log #-n和数字间没有空格");
        System.out.println("tail -f c:\\test.log");

        while (true){
            try {

                /*等待输入tail命令*/
                String comm =  new BufferedReader(new InputStreamReader(System.in)).readLine() ;

                String[] commArgs = comm.split(" ");
                //判断命令是否合法,不合法就继续输入
                if(!checkComm(commArgs)) {
                    System.out.println("error command! retry!");
                    continue;
                }

                //不同系统，替换分隔符
                //todo window正常，未测试Linux系统，如果路径报错，请在这里手动赋值
                commArgs[2] = commArgs[2].replace("\\", File.separator);

                /*启动线程，模拟写日志 */
                WritingLogs.write(commArgs[2]);

                //等2秒，让日志写一会
                System.out.println("日志从0开始写，我们等2秒读");
                Thread.currentThread().sleep(2000l);
                /*读日志，并打印*/
                ReadingLogs.read(commArgs);

                System.out.println("请继续测试!");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    //检查命令是否合法
    private static boolean checkComm(String[] commArgs) {
        if(commArgs.length != 3){
            return false;
        }
        if (!commArgs[0].equals("tail")) {
            return false;
        }
        //判断第二个参数是否为 -f 或者 -n 加数字
        if(!(commArgs[1].equals("-f") || Pattern.matches("-n\\d+",commArgs[1]))) {
            return false;
        }

        return true;
    }
}
