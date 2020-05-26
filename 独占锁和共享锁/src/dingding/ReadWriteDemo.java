package dingding;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 共享资源
 */
class MyCache{
    private volatile Map<String, Object> map = new HashMap<>();
    ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();


    //写不共享(独占锁)
    public void put(String key, Object value) throws InterruptedException {
        readWriteLock.writeLock().lock();
        try {
            //这里加了锁之后，写入和写完就是个原子操作
            System.out.println(Thread.currentThread().getName()+"\t 正在写入：" + key);
            Thread.sleep(300);
            map.put(key, value);
            System.out.println(Thread.currentThread().getName()+"\t 写入完成：");
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            readWriteLock.writeLock().unlock();
        }

    }

    //读可共享(共享锁)
    public void get(String key) throws InterruptedException {
        readWriteLock.readLock().lock();
        try {
            System.out.println(Thread.currentThread().getName()+"\t 正在读取：" + key);
            Thread.sleep(300);
            Object result = map.get(key);
            System.out.println(Thread.currentThread().getName()+"\t 读取完成：" + result);
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            readWriteLock.readLock().unlock();
        }


    }

}

public class ReadWriteDemo {

    public static void main(String[] args) {
        MyCache myCache = new MyCache();

        for (int i = 0; i < 5; i++) {
            final int tempInt = i;
            new Thread(() -> {
                try {
                    myCache.put(tempInt+"", tempInt+"");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            },String.valueOf(i)).start();
        }

        for (int i = 0; i < 5; i++) {
            final int tempInt = i;
            new Thread(() -> {
                try {
                    myCache.get(tempInt+"");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            },String.valueOf(i)).start();
        }
    }
}
