import javax.smartcardio.Card;
import java.util.concurrent.locks.ReentrantLock;

public class LockDemo04 {
    //存钱
    static Runnable r0= new Runnable() {
        @Override
        public void run() {
            while (true){
                //获取单例对象
                Card card = Card.currentInstance();
                //调用storeMoney方法
                card.storeMoney(500);
            }
        }
    };
    //取钱
    static Runnable r1 = new Runnable() {

        @Override
        public void run() {
            while(true) {
                //获取单例对象
                Card card = Card.currentInstance();
                //调用getMoney方法
                card.getMoney(100);
            }
        }
    };

    public static void main(String[] args) {
        //存钱线程
        Thread t0 = new Thread(r0,"亚瑟");
        Thread t1 = new Thread(r0,"韩信");

        //存钱线程
        Thread t2 = new Thread(r1,"后裔");
        Thread t3 = new Thread(r1,"猴子");

        t0.start();
        t1.start();
        t2.start();
        t3.start();
    }

     static class Card{
        // 实例化一个私有的静态的当前类的实例
        private static   Card instance = new Card();

        //余额
        private  int rest=1000;

        ReentrantLock lock =  new ReentrantLock();

        private Card() {

        }

        // 提供一个对外的开放的方法，将实例返回
        public static Card currentInstance() {
            return instance;
        }

        public int getRest() {
            return rest;
        }

        public void setRest(int rest) {
            this.rest = rest;
        }

        //存钱
        public synchronized void storeMoney(int num){

            this.rest+=num;
            System.out.println(Thread.currentThread().getName() + "存了" + num + ",余额为：" + rest);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        //取钱
        public synchronized void getMoney(int num){

            if(num>rest){
                num=rest;
            }
            this.rest -= num;
            System.out.println(Thread.currentThread().getName() + "取了" + num + ",余额为：" + rest);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
