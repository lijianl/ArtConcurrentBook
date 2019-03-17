package chapter08;

import java.util.concurrent.Exchanger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 
 * @author tengfei.fangtf
 * @version $Id: ExchangerTest.java, v 0.1 2015-8-1 ����12:10:37 tengfei.fangtf Exp $
 */
public class ExchangerTest {

    private static final Exchanger<String> exgr       = new Exchanger<String>();

    private static ExecutorService threadPool = Executors.newFixedThreadPool(2);

    public static void main(String[] args) {

        threadPool.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    String A = "A";  // A¼��������ˮ����
                    exgr.exchange(A);
                } catch (InterruptedException e) {
                }
            }
        });

        threadPool.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    String B = "B";  // B¼��������ˮ����
                    String A = exgr.exchange("B");
                    System.out.println("A��B�����Ƿ�һ�£�" + A.equals(B) + "��A¼����ǣ�" + A + "��B¼���ǣ�" + B);
                } catch (InterruptedException e) {
                }
            }
        });

        threadPool.shutdown();

    }
}
