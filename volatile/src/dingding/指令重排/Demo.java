package dingding.指令重排;

/**
 * @author liudingding
 * @ClassName Demo
 * @description
 * CPU和编译器为了提升程序执行的效率，会按照一定的规则允许进行指令优化。但代码逻辑之间是存在一定的先后顺序，并发执行时按照不同的执行逻辑会得到不同的结果。
 * 在上面的代码中，单线程执行时，read方法能够获得flag的值进行判断，获得预期结果。但在多线程的情况下就可能出现不同的结果。比如，当线程A进行write操作时，由于指令重排，write方法中的代码执行顺序可能会变成下面这样：flag = true;             //2
 * a = 1;                   //1也就是说可能会先对flag赋值，然后再对a赋值。这在单线程中并不影响最终输出的结果。但如果与此同时，B线程在调用read方法，那么就有可能出现flag为true但a还是0，这时进入第4步操作的结果就为0，而不是预期的1了。而volatile关键词修饰的变量，会禁止指令重排的操作，从而在一定程度上避免了多线程中的问题。
 *
 * @date 2020/5/23 11:47
 * Version 1.0
 */
public class Demo {

    int number = 0;
    boolean flag = false;
    void write(){
        number = 1;
        flag = true;
    }
    void read(){
        if (flag){
            int a = number*number;
        }
    }

    public static void main(String[] args) {
        Demo demo = new Demo();
        for (int i = 0; i < 100001; i++) {
            new Thread(() -> {
                demo.write();
                demo.read();
            }).start();
        }

        while (Thread.activeCount() > 2){
            Thread.yield();
        }
        System.out.println(demo.number);
    }
}
