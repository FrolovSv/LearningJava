/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LearningJava.ThreadConcurrenc;

import java.util.concurrent.*;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author SusPecT
 */
public class StudyCyclicBarrier {
    public static void main(String args[]){
        CyclicBarrier cb = new CyclicBarrier(3, new BarAction() );
        System.out.println("Зaпycк потоков");
        Thread TH1 = new Thread(new MyThreadCyclicBarrier(cb, "А"));
        TH1.start();
        Thread TH2 = new Thread(new MyThreadCyclicBarrier(cb, "В"));
        TH2.start();
        Thread TH3 = new Thread(new MyThreadCyclicBarrier (cb, "С"));
        TH3.start();
        Thread TH4 = new Thread(new MyThreadCyclicBarrier(cb, "D"));
        TH4.start();
        Thread TH5 = new Thread(new MyThreadCyclicBarrier(cb, "E"));
        TH5.start();
        Thread TH6 = new Thread(new MyThreadCyclicBarrier (cb, "F"));
        TH6.start();
    }       
}

class MyThreadCyclicBarrier implements Runnable {
    CyclicBarrier cbar;
    String name;
    MyThreadCyclicBarrier(CyclicBarrier с, String n){
        cbar = с;
        name = n;
    }
    @Override
    public void run(){
        System.out.println(name);
        try {            
            System.out.println("Ждем");
            System.out.println("Пришел поток: " + (cbar.getNumberWaiting()+1) + " "+ name);
            cbar.await();  
            System.out.println("Дождались всех, кол-во - " + cbar.getParties() + ". Продолжаем исполнение потока "+ name);
        }catch (BrokenBarrierException | InterruptedException ехс){
            System.out.println(ехс);
        }
    }
}

// Объект этого класса вызывается по достижении
// барьера типа CyclicBarrier
class BarAction implements Runnable {
    @Override
    public void run() {
        try {
            System.out.println("Бapьep достигнут!");
            System.out.println("Исполняем поток полсе ожидаения");
            System.out.print("");
            for (int i = 0; i < 10; i++) {
                Thread.sleep(500);
                System.out.print(i);
            }
            System.out.println("");
            System.out.println("Возвращаем исполнения другим потокам");
        } catch (InterruptedException ex) {
            Logger.getLogger(BarAction.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}