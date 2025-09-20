

package L3_Thread;


public class Demo {
    public static void main(String[] args) {
        ShareData sd = new ShareData();
//        new Produce().start();
        new Thread(new Consumer(sd)).start();
        new Thread(new Consumer(sd)).start();
        new Thread(new Consumer(sd)).start();
    }
}

class ShareData{
    int data;
    boolean produced = false;
    
    public synchronized void produce(int _data) throws InterruptedException{
        if(produced)
            this.wait(); // khi notify() bị bỏ qua kiểm tra, luồng chạy tiếp sau đoạn dừng đợi.
        
        this.data  =_data;
        System.out.println("produce: "+this.data);
        produced = true;
        this.notify();
    }
}

class Consumer implements Runnable{
    ShareData sharedata;

    public Consumer(ShareData sharedata) {
        this.sharedata = sharedata;
    }

    @Override
    public void run() {
        
    }
    
    
}

class Produce implements Runnable{

    public Produce() {
    }
    
    
    @Override
    public void run() {
        
    }
    
}