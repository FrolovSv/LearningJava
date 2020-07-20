/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LearningJava.ThreadConcurrenc;

import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * @author SusPecT
 */
public class StudyLocks {

    public static void main(String args[]) {
        ReentrantLock lock = new ReentrantLock();
        Thread th1 = new Thread(new LockThread(lock, "А"));
        th1.start();
        Thread th2 = new Thread(new LockThread(lock, "В"));
        th2.start();
    }
}

//  Общий ресурс 
class Shared {

    static int count = 0;
}

//  Поток исполнения,  инкрементирующий значение счетчика 
class LockThread implements Runnable {

    String name;
    ReentrantLock lock;

    LockThread(ReentrantLock lk, String n) {
        lock = lk;
        name = n;
    }

    @Override
    public void run() {
        System.out.println("Зaпycк потока" + name);
        try {
            //  сначала заблокировать счетчик 
            System.out.println("Пoтoк " + name + " ожидает блокировки счетчика");
            lock.lock();
            System.out.println("Пoтoк " + name + " блокирует счетчик.");
            Shared.count++;
            //
            // тут может быть реализация метода
            //
            System.out.println("Пoтoк " + name + ":  " + Shared.count);
            //  а  теперь переключить контекст,  если это  возможно         
            System.out.println("Пoтoк " + name + " ожидает");
            Thread.sleep(9000);
        } catch (InterruptedException ехс) {
            System.out.println(ехс);
        } finally {
            //  снять блокировку 
            System.out.println("Пoтoк "+  name  +" разблокирует счетчик");
            lock.unlock();
        }
    }
}
