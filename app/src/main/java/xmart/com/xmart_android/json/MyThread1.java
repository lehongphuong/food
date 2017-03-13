package xmart.com.xmart_android.json;

/**
 * Created by LehongphuongCntt on 2/25/2017.
 */

public class MyThread1 {

    public static void main(String args []){
        Thread1 t1 = new Thread1(); // khởi tạo đối tượng t1
        Thread2 t2 = new Thread2(); // khởi tạo đối tượng t2
        t1.start(); // bắt đầu chạy t1
        t2.start(); // bắt đầu chạy t2
    }
}


class Thread1 extends Thread{
    @Override
    public void run(){
        for(int i = 0; i < 500; i++){
            System.out.println(" thread1 " + i);
        }
    }
}

class Thread2 extends Thread{
    @Override
    public void run(){
        for(int i = 0; i < 500; i++){
            System.out.println(" thread2 " + i);
        }
    }
}