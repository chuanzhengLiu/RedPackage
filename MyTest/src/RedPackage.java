import java.util.Random;
import java.util.Scanner;

//抢红包
public class RedPackage {
    public static void main(String[] args) {
        System.out.println("请输入红包金额");
        Scanner scanner = new Scanner(System.in);
        double rest = scanner.nextDouble();//红包金额
        System.out.println("请输入红包个数");
        int count=scanner.nextInt();;//红包个数
        if(count/100>rest){//每人不够分一分钱
            System.out.println("太扣了吧，求求你多发点行不");
            return;
        }
        RedBao redBao = new RedBao(rest,count);//生成红包
        NewUser u1 = new NewUser("小乔",redBao);
        NewUser u2 = new NewUser("大乔",redBao);
        NewUser u3 = new NewUser("妲己",redBao);
        NewUser u4 = new NewUser("王昭君",redBao);

        new Thread(u1,u1.getName()).start();
        new Thread(u2,u2.getName()).start();
        new Thread(u3,u3.getName()).start();
        new Thread(u4,u4.getName()).start();

    }

}
class NewUser implements Runnable{
    private String name;//名字

    private RedBao redBao;//所得红包

    public NewUser() {
    }

    public NewUser(String name, RedBao redBao) {
        this.name = name;
        this.redBao = redBao;
    }

    public String getName() {
        return name;
    }

    @Override
    public void run() {
        double money = redBao.getMoney();
        if(money==0){
            System.out.println(Thread.currentThread().getName() +"来晚了yo，红包已被抢空空！");
        }else {
            System.out.println("恭喜"+Thread.currentThread().getName() + "抢到 " + money + "元");
        }
    }

}
class RedBao{
    private double totalMoney; //总金额

    private  int  num; //红包个数

    private int totalVal; //随机生成整数，将钱化为整数

    public RedBao() {
    }

    public RedBao(double totalMoney, int num) {
        this.totalMoney = totalMoney;
        this.num = num;
        this.totalVal = (int) (totalMoney*100);
    }

    //抢红包
    public synchronized double getMoney(){
        int val;
        if(num!=0&&totalVal/num==1){ //每人刚好一分钱
            totalVal--;
            num--;
            return 0.01;
        }
        if(num<=0){//说明红包已经被抢光了
            val=0;
        }else if (num==1){//最后一个红包了
            val=totalVal;
        }else {
            int temp;//所剩余额
            while (true){
                //随机生成金额 平均点
                val = new Random().nextInt(totalVal/num);
                temp=totalVal-val;
                // 判断生成的金额大于0，且剩余的钱数够剩下人平分到0.01元
                if(temp*1.0/(num-1) >= 1 && val > 0) {
                    //System.out.println("生成金额 ：" + val + "剩余金额 ：" + temp + "剩余人数 ：" + (count-1));
                    break;
                }
            }
            totalVal-=val;
        }
        num--;
        return val/100.0;
    }
}