/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LearningJava.ThreadConcurrenc;

import  java.util.concurrent.*; 
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author SusPecT
 */
public class studyExecutor {
    public static void  main(String  args[]){
        CountDownLatch cdl = new CountDownLatch(5);
        CountDownLatch cdl2 = new CountDownLatch(5);
        CountDownLatch cdl3 = new CountDownLatch(5);
        CountDownLatch cdl4 = new CountDownLatch(5);
        ExecutorService es = Executors.newFixedThreadPool(3);
        System.out.println("Зaпycк потоков");
        //  запустить потоки исполнения 
        es.execute(new MyThreadExecutor(cdl, "А"));
        es.execute(new MyThreadExecutor(cdl2, "В"));
        es.execute(new MyThreadExecutor(cdl3, "С"));
        es.execute(new MyThreadExecutor(cdl4, "D"));
        try {
            System.out.println("Первый пришел");
            Thread.sleep(0);
            cdl.await();
            System.out.println("второй пришел");
            Thread.sleep(0);
            cdl2.await();
            System.out.println("третий пришел");
            Thread.sleep(0);
            cdl3.await();
            System.out.println("четвертый пришел");
            Thread.sleep(0);
            cdl4.await();
            System.out.println("конец");
        } catch (InterruptedException ехс) {
            System.out.println(ехс);
        }
        es.shutdown();
        System.out.println("Зaвepшeниe потоков");
    }
}

class  MyThreadExecutor  implements  Runnable  { 
    String  name; 
    CountDownLatch  latch; 
    MyThreadExecutor(CountDownLatch  с,  String  n){
        latch  =  с; 
        name  =  n; 
    }
    public void  run()  { 
        for(int  i  =  0;  i  <  5;  i++)  { 
            try {
                Thread.sleep(1000);
                System.out.println(name  +  "·  "+ i); 
                latch.countDown();
            } catch (InterruptedException ex) {
                Logger.getLogger(MyThreadExecutor.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
