package dingding.CAS问题;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author liudingding
 * @ClassName CASDemo
 * @description
 * CAS底层原理，自旋锁+unSafe
 * @date 2020/5/23 15:39
 * Version 1.0
 */
public class CASDemo {

    public static void main(String[] args) {
        AtomicInteger atomicInteger = new AtomicInteger(5);
        //当预估值和主存里面的值相同是，就交换更新值和主存值
        atomicInteger.compareAndSet(5, 2020);
        System.out.println(atomicInteger.get());

    }
}
