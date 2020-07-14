/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LearningJava.ThreadConcurrenc;

import java.util.concurrent.CountDownLatch;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author SusPecT
 */
public class StudyCuntDownLatch {
    
    public static void main(String args[]) {
        CountDownLatch cdl = new CountDownLatch(5);
        System.out.println("Зaпycк потока исполнения");
        new Thread(new MyThreadCuntDownLatch(cdl)).start();
        try{
            System.out.println("Ждем");
            cdl.await();
            System.out.println("Дождались");
        }catch (InterruptedException ехс) {
            System.out.println(ехс);
        }
        
        System.out.println("Зaвepшeниe потока исполнения");        
    }    
}

class MyThreadCuntDownLatch implements Runnable{
    CountDownLatch latch;
    MyThreadCuntDownLatch(CountDownLatch с) {
        latch = с;
    }
    @Override
    public void run(){
        for (int i = 0; i < 5; i++) {
            try {
                System.out.println("До конца блокировки осталось " + i);
                Thread.sleep(1000);
                latch.countDown(); // обратный отсчет
            } catch (InterruptedException ex) {
                Logger.getLogger(MyThreadCyclicBarrier.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}