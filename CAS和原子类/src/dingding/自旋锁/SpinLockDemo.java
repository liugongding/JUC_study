package dingding.自旋锁;

import java.util.concurrent.atomic.AtomicReference;


/**
 * A come in  A线程先进入myLock方法，现在atomicReference.get()的主存值为Thread，持有这把锁5s(线程A一直没有进行CAS操作)
 * B come in  B线程先进入myLock方法，现在主存为Thread,期望值为null，CAS失败返回false，去反为true，一直自旋，
 * A invoke   A线程进入unMyLock方法，主存值为Thread，期望值为Thread，CAS成功。此时线程B跳出循环
 * B invoke   B线程进入unMyLock方法，程序结束。
 *
 */
public class SpinLockDemo {

    //初始值为null,原子引用线程
    AtomicReference<Thread> atomicReference = new AtomicReference<>();

    public void myLock() {
        Thread thread = Thread.currentThread();
        System.out.println(Thread.currentThread().getName()+"\t come in");

        while (!atomicReference.compareAndSet(null, thread)){
        }
    }

    public void myUnLock() {
        Thread thread = Thread.currentThread();
        atomicReference.compareAndSet(thread, null);
        System.out.println(Thread.currentThread().getName()+"\t invoke myUnLock");
    }

    public static void main(String[] args) throws InterruptedException {
        SpinLockDemo spinLockDemo = new SpinLockDemo();
        new Thread(() -> {
            spinLockDemo.myLock();
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            spinLockDemo.myUnLock();
        },"A").start();

//        Thread.sleep(1000);

        new Thread(() -> {
            spinLockDemo.myLock();
            spinLockDemo.myUnLock();
        },"B").start();
    }
}
