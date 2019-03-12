import java.util.concurrent.locks.ReentrantLock;

public class LockDemo03 {
    static int count = 100;

    static String str = new String();
    static ReentrantLock lock = new ReentrantLock();
    static Runnable  r = new Runnable(){

        @Override
        public void run() {
            while (count>0){
                lock.lock();
                if(count<=0){
                    return;
                }
                count--;
                System.out.println("售票员"+Thread.currentThread().getName()+"售出一张票，剩余票数为"+count);

                    /*try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }*/
                lock.unlock();
            }
        }
    };

    public static void main(String[] args) {
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
