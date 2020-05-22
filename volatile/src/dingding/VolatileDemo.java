package dingding;

import org.junit.Test;

import java.util.concurrent.TimeUnit;

/**
 * @author liudingding
 * @ClassName Demo
 * @description 变量的可见性
 * 1、由于number未用volatile修饰，首先mian线程抢到时间片，将number==0加载到自己的工作内存，
 * 线程A抢到时间片修改number值，但是没有通知mian线程，这样mian线程一直认为number==0处于死循环
 *
 * 2、由于number用volatile修饰，首先mian线程抢到时间片，将number==0加载到自己的工作内存，
 * 线程A抢到时间片修改number值，线程A通知mian线程number已经变更，mian线程不执行循环。
 * @date 2020/5/22 23:01
 * Version 1.0
 */

class MyData{

    volatile int number = 0;
    void change(){
        this.number = 50;
    }
}
public class VolatileDemo {

    public static void main(String[] args) {
        MyData myData = new MyData();
        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "线程 come in");
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            myData.change();
            System.out.println(Thread.currentThread().getName()+"线程更改number为："+myData.number);
        },"A").start();
        while (myData.number == 0){

        }
        System.out.println(Thread.currentThread().getName()+"通知线程number："+myData.number);
    }
}
