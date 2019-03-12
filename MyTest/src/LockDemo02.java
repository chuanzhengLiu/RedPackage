public class LockDemo02 {
    static int count=100;

    static class MyThread implements Runnable{

        @Override
        public void run() {
            while (count>0){
                sellTicks();
            }
        }
    }

    private static synchronized void sellTicks() {
        if(count<=0){
            return;
        }
        count--;
        System.out.println("售票员"+Thread.currentThread().getName()+"售出一张票，剩余票数为"+count);
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        MyThread r = new MyThread();
        Thread t0 = new Thread(r,"王昭君");
        Thread t1 = new Thread(r,"貂蝉");
        Thread t2 = new Thread(r,"妲己");
        Thread t3 = new Thread(r,"小乔");

        t0.start();
        t1.start();
        t2.start();
        t3.start();
    }

}
