/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package study.ThreadConcurrenc;

import java.util.concurrent.*; 
/**
 *
 * @author SusPecT
 */
public class StudySemaphore {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Semaphore sem = new Semaphore(1);
        
//        new IncThread(sem, "А").run();
//        new DecThread(sem, "В").run(); 
        
        Thread MBThread = new Thread(new IncThread(sem, "А"));
        MBThread.start();
        Thread MBThread2 = new Thread(new DecThread(sem, "В"));        
        MBThread2.start();
    }       
}

class Shared {
    static int count; 
}

class IncThread implements Runnable {
    String name;
    Semaphore sem;
    
    IncThread(Semaphore s, String n) {
        sem = s;
        name = n;
    }
    @Override
    public void run(){
        System.out.println("Зaпycк потока " + name);
        try {
            // сначала получить разрешение
            System.out.println("Пoтoк " + name + " ожидает разрешения");
//            System.out.println(sem.getQueueLength());
            sem.acquire();
            System.out.println("Пoтoк " + name + " получает разрешение");
            // а теперь получить доступ к общему ресурсу
            for (int i = 0; i < 5; i++) {
                Shared.count++;
                System.out.println(name + ": " + Shared.count);
                //!разрешить, если возможно, переключение контекста
                Thread.sleep(10);
            }
        }catch (InterruptedException ехс){
            System.out.println(ехс);            
        }        
        // освободить разрешение
        System.out.println("Пoтoк " + name + " освобождает разрешение");            
        sem.release();        
    }
}

class DecThread implements Runnable {
    String name;
    Semaphore sem;
    
    DecThread(Semaphore s, String n) {
        sem = s;
        name = n;
    }
    @Override
    public void run(){
        System.out.println("Зaпycк потока " + name);
        try {
            // сначала получить разрешение
            System.out.println("Пoтoк " + name + " ожидает разрешения");
            sem.acquire();
            System.out.println("Пoтoк " + name + " получает разрешение");
            // а теперь получить доступ к общему ресурсу
            for (int i = 0; i < 5; i++) {
                Shared.count--;
                System.out.println(name + ": " + Shared.count);
                //!разрешить, если возможно, переключение контекста
                Thread.sleep(10);
            }
        }catch (InterruptedException ехс){
            System.out.println(ехс);
        }        
        // освободить разрешение
        System.out.println("Пoтoк " + name + " освобождает разрешение");
        sem.release();        
    }
}