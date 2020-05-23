package dingding.ABA问题;

import java.util.concurrent.atomic.AtomicReference;

/**
 * @author liudingding
 * @ClassName AtomicReferenceDemo
 * @description
 * @date 2020/5/23 21:04
 * Version 1.0
 */

class User{
    String username;
    int age;

    public User(String username, int age) {
        this.username = username;
        this.age = age;
    }
}
public class AtomicReferenceDemo {

    public static void main(String[] args) {
        User z3 = new User("z3", 22);
        User li4 = new User("li4", 25);

        AtomicReference<User> userAtomicReference = new AtomicReference<>();
        userAtomicReference.set(z3);

        //主存是z3，期望是z3，可以修改，返回true， 将li4写入
        userAtomicReference.compareAndSet(z3, li4);
        //主存是li4，期望是z3，不能修改，返回false，还是li4
        userAtomicReference.compareAndSet(z3, li4);
    }
}
