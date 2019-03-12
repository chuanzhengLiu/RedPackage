import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LockDemo05 {

    static class MyThread implements Runnable{
        private int  rest;//红包金额
        private int  count;//红包个数

        public MyThread() {
        }

        public MyThread(int rest, int count) {
            this.rest = rest;
            this.count = count;
        }

        @Override
        public void run() {
            while (count>=0&&rest>0){
                sellTicks(rest,count);//抢红包
            }
        }
    }

    private static synchronized void sellTicks(int rest, int count) {
        if(count<0||rest<=0){
            System.out.println("来晚了，红包已经抢光了哟！");
            return;
        }
        count--;
        Random random = new Random();
        int r=random.nextInt(rest/count);
        if(count==0){//说明是最后一个红包了
            r=rest;
        }
        rest-=r;
        System.out.println(Thread.currentThread().getName()+"抢了"+r+"元,"+"还剩"+count+"个红包");
        try {
           Thread.sleep(1000);
           // Thread.currentThread().join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException {

        System.out.println("请输入红包金额");
        Scanner scanner = new Scanner(System.in);
        int rest = scanner.nextInt();//红包金额
        System.out.println("请输入红包个数");
        int count=scanner.nextInt();;//红包个数

        MyThread r = new MyThread(rest,count);
        /*Thread t0 = new Thread(new MyThread(rest,count),"王昭君");
        Thread t1 = new Thread(new MyThread(rest,count),"貂蝉");
        Thread t2 = new Thread(new MyThread(rest,count),"妲己");
        Thread t3 = new Thread(new MyThread(rest,count),"小乔");
        t0.start();
       // t0.join();
        t1.start();
       // t1.join();
        t2.start();
       // t2.join();
        t3.start();*/
        for(int i=0;i<count;i++) {
            new Thread(r,"韩斌"+i).start();
        }

        /*ExecutorService service= Executors.newSingleThreadExecutor();
        service.submit(t0);
        service.submit(t1);
        service.submit(t2);
        service.submit(t3);*/
    }

}
