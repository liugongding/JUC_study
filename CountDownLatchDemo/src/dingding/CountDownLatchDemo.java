package dingding;

import java.util.concurrent.CountDownLatch;

public class CountDownLatchDemo {

    public static void main(String[] args) throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(6);

        for (int i = 0; i < 6; i++) {
            new Thread(() -> {
                System.out.println(Thread.currentThread().getName() + "\t 上完自习，离开教室");
                //每执行一次计数器减一
                countDownLatch.countDown();
            }, String.valueOf(i)).start();
        }
        //这里main线程必须等待计数器减为0才能往下走
        countDownLatch.await();
        System.out.println(Thread.currentThread().getName() + "\t  ########班长最后走人关门");
    }
}
