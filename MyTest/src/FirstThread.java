
import java.util.Random;
import java.util.Scanner;

public class FirstThread {
    public static void main(String[] args) {
        // 10个红包，5个人抢
        /*System.out.println("请输入红包金额");
        Scanner scanner = new Scanner(System.in);
        int rest = scanner.nextInt();//红包金额
        System.out.println("请输入红包个数");
        int count=scanner.nextInt();;//红包个数*/

        Bao bao = new Bao(0.04, 2);
        User user = new User(bao);
        for(int i=0;i<10;i++) {
            new Thread(user,"韩斌"+i).start();
        }
    }
}

// 每一个用户都是一个线程
class User implements Runnable{
    private Bao bao;
    public User(Bao bao) {
        this.bao = bao;
    }
    @Override
    public void run() {
        double money = bao.getRandomMoney();
        if(money == 0) {
            System.out.println(Thread.currentThread().getName() + "不好意思，您手慢了！");
        }else {
            System.out.println(Thread.currentThread().getName() + "抢到 " + money + "元");
        }

    }
}

class Bao{
    private double total; // 总钱数
    private int totalVal; // 随机生成整数，将钱数化为整数
    private int count;	  // 红包总数

    public Bao(double total, int count) {
        this.total = total;
        this.count = count;
        this.totalVal = (int)(total * 100);
    }

    public synchronized double getRandomMoney() {
        int val;
        // 当前剩余钱数 0.04 4人
        if(count !=0 && totalVal / count == 1) {  //total*100/count==1 比如说1元红包分100分 也就是说每人得到一分钱
            val = 1;
            totalVal = totalVal - val;
            count--;
            return val/100.0;
        }

        if(count <= 0) {
            val = 0;
        }else if(count == 1) {
            val = totalVal;
        }else {
            int temp; //剩下的金额
            while(true) {
                // 随机生成当前金额的随机数 [0,totalVal/count),尽量平均一点
                val = new Random().nextInt(totalVal/count);
                temp = totalVal - val;
                // 判断生成的金额大于0，且剩余的钱数够剩下人平分到0.01元
                if(temp*1.0/(count-1) >= 1 && val > 0) {
                    //System.out.println("生成金额 ：" + val + "剩余金额 ：" + temp + "剩余人数 ：" + (count-1));
                    break;
                }
            }
            totalVal = totalVal - val;
        }
        count--;
        /*try {
            Thread.sleep(1000);
            // Thread.currentThread().join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
        return val/100.0;
    }
}

