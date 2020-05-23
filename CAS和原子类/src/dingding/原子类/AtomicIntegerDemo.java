package dingding.原子类;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author liudingding
 * @ClassName AtomicIntegerDemo
 * @description
 * @date 2020/5/23 15:31
 * Version 1.0
 */

class MyData{
    //初始值为0
    AtomicInteger atomicInteger = new AtomicInteger();

    void addAtomic(){
        //自增1
        atomicInteger.getAndIncrement();
    }
}
public class AtomicIntegerDemo {
    public static void main(String[] args) {
        MyData myData = new MyData();
        for (int i = 0; i < 20; i++) {
            new Thread(() ->{
                for (int j = 0; j < 1000; j++) {
                    myData.addAtomic();
                }
            },String.valueOf(i)).start();
        }

        while (Thread.activeCount() > 2){
            Thread.yield();
        }
        System.out.println(myData.atomicInteger);
    }
}
