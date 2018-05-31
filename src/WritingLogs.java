import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/*
在程序启动后模拟一直写日志操作
 */
public class WritingLogs {
    private static String defPath = "c:\\test.log";
    private static ExecutorService threadPool = Executors.newSingleThreadExecutor();

    /*默认路径 c:\test.log*/
    public static void write (String path) {
        if (path != null && path.length() > 0) {
            defPath = path;
        }
        threadPool.submit(new Task());
    }


    static class Task implements Runnable{

        @Override
        public void run() {
            try{
                FileOutputStream fout = new FileOutputStream(defPath);
                FileChannel outChannel = fout.getChannel();
                ByteBuffer buffer = ByteBuffer.allocate(1000);
//                for(long i=0;i<Long.MAX_VALUE;i++) {
                for(long i=0;i<1000000;i++) {
                    buffer.put((i+System.getProperty("line.separator")).getBytes());//写换行
                    buffer.flip();
                    outChannel.write(buffer);
                    buffer.clear();
                    Thread.currentThread().sleep(100l);
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
}
