package dingding.ABA问题;

import jdk.nashorn.internal.runtime.logging.Logger;

import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * @author liudingding
 * @ClassName ABADemo
 * @description 每次修改的时候给他加一个版本号
 * @date 2020/5/23 21:14
 * Version 1.0
 */
public class ABADemo {
    static AtomicStampedReference<Integer> stampedReference = new AtomicStampedReference<>(100,1);
    public static void main(String[] args) throws InterruptedException {
        new Thread(() -> {
            //获取版本号
            System.out.println(Thread.currentThread().getName()+"线程的第一次版本号为："+stampedReference.getStamp());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //交换值
            stampedReference.compareAndSet(100,101,stampedReference.getStamp(),stampedReference.getStamp()+1);
            System.out.println(Thread.currentThread().getName()+"线程的第二次版本号为："+stampedReference.getStamp());
            stampedReference.compareAndSet(101,100,stampedReference.getStamp(),stampedReference.getStamp()+1);
            System.out.println(Thread.currentThread().getName()+"线程的第三次版本号为："+stampedReference.getStamp());
        },"t1").start();

        new Thread(() -> {
            int stamp = stampedReference.getStamp();
            System.out.println(Thread.currentThread().getName()+"线程的第一次版本号为："+stamp);

            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            boolean result = stampedReference.compareAndSet(100,2020,stamp,stamp+1);
            //这里预估版本号和期望版本号不同，不进行交换。
            System.out.println(Thread.currentThread().getName()+"线程的第四次版本号为："+stampedReference.getStamp());
            System.out.println(result);
            System.out.println(stampedReference.getReference());

        },"t2").start();

    }
}
