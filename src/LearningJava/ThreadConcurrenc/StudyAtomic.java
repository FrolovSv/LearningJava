/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LearningJava.ThreadConcurrenc;

import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 * @author SusPecT
 */
public class StudyAtomic {

    public static void main(String args[]) {
        Thread th1 = new Thread(new AtomThread("А"));
        th1.start();
        Thread th2 = new Thread(new AtomThread("В"));
        th2.start();
        Thread th3 = new Thread(new AtomThread("C"));
        th3.start();
    }
}

class  Shared  { 
    static AtomicInteger  ai = new  AtomicInteger(0); 
}

//  Поток исполнения,  в  котором инкрементируется значение счетчика 
class AtomThread implements Runnable {

    String name;

    AtomThread(String n) {
        name = n; 
    }

    @Override
    public void run() {
        System.out.println("Зaпycк потока" + name);
        for (int i = 1; i <= 3; i++) {
            System.out.println("Пoтoк" + name + "  получено:  "
                    + Shared.ai.getAndSet(i));
        }
    }
}
