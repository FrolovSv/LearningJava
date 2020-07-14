/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LearningJava.ThreadConcurrenc;

import java.util.concurrent.*;
/**
 *
 * @author SusPecT
 */
public class StudyPhaser {
    public static void main(String args[]){
        Phaser phsr = new Phaser(1);
        int curPhase;
        System.out.println("зaпycк потоков");
        Thread th1 = new Thread(new MyThreadPhaser(phsr, "А"));
        th1.start();
        Thread th2 = new Thread(new MyThreadPhaser(phsr, "В"));
        th2.start();
        Thread th3 = new Thread(new MyThread1(phsr, "С"));
        th3.start();
        //ожидать завершения всеми потоками исполнения первой фазы
        curPhase = phsr.getPhase();
        phsr.arriveAndAwaitAdvance();
        System.out.println("Фaзa" + (curPhase+1) + " завершена");
        //ожидать завершения всеми потоками исполнения второй фазы
        curPhase = phsr.getPhase();
        phsr.arriveAndAwaitAdvance();
        System.out.println("Фaзa " + (curPhase+1) + " завершена");
        curPhase = phsr.getPhase();
        phsr.arriveAndAwaitAdvance();
        System.out.println("Фaзa " + (curPhase+1) + " завершена");
        // снять основной поток исполнения с регистрации
        phsr.arriveAndDeregister();
        if(phsr.isTerminated())
            System.out.println("Cинxpoнизaтop фаз завершен");
    }
    
}

//Поток исполнения, применяющий синхронизатор фаз типа Phaser
class MyThreadPhaser implements Runnable {
    Phaser phsr;
    String name;
    MyThreadPhaser(Phaser р, String n){
        phsr = р;
        name = n;
        phsr.register();
    }
    @Override
    public void run(){
        System.out.println("Пoтoк " + name + " начинает первую фазу");
        phsr.arriveAndAwaitAdvance(); //известить о достижении фазы
        System.out.println(name + ": первую фазa пошла к исполнению");
        //Небольшая пауза, чтобы не нарушить порядок вывода.
        // Это сделано только для целей демонстрации, но
        // совсем не обязательно для правильного
        //функционирования синхронизатора фаз
        try {
            Thread.sleep(30);
        }catch(InterruptedException е) {
            System.out.println(е);
        }
        System.out.println("Пoтoк " + name + " начинает вторую фазу");
        phsr.arriveAndAwaitAdvance(); 
        System.out.println(name + ": вторая фазa пошла к исполнению");
        //известить о достижении фазы
        //Небольшая пауза, чтобы не нарушить порядок вывода.
        // Это сделано только для целей демонстрации, но
        // совсем не обязательно для правильного
        //функционирования синхронизатора фаз
        try {
            Thread.sleep(30);
        }catch(InterruptedException E){
            System.out.println(E);
        }
        System.out.println("Пoтoк " + name+ " начинает третью фазу");
        //System.out.println("Я устал. Я ухожу");
        phsr.arriveAndDeregister(); //известить о достижении
        //фазы и снять потоки с регистрации
    }
}

//Поток исполнения, применяющий синхронизатор фаз типа Phaser
class MyThread1 implements Runnable {
    Phaser phsr;
    String name;
    MyThread1(Phaser р, String n){
        phsr = р;
        name = n;
        phsr.register();
    }
    @Override
    public void run(){
        System.out.println("Пoтoк " + name + " начинает первую фазу");
        phsr.arriveAndAwaitAdvance(); //известить о достижении фазы
        System.out.println(name + ": первую фазa пошла к исполнению");
        //Небольшая пауза, чтобы не нарушить порядок вывода.
        // Это сделано только для целей демонстрации, но
        // совсем не обязательно для правильного
        //функционирования синхронизатора фаз
        try {
            Thread.sleep(30);
        }catch(InterruptedException е) {
            System.out.println(е);
        }
        System.out.println("Пoтoк " + name + " начинает вторую фазу");
        phsr.arriveAndAwaitAdvance(); 
        System.out.println(name + ": вторая фазa пошла к исполнению");
        //известить о достижении фазы
        //Небольшая пауза, чтобы не нарушить порядок вывода.
        // Это сделано только для целей демонстрации, но
        // совсем не обязательно для правильного
        //функционирования синхронизатора фаз
//        try {
//            Thread.sleep(10);
//        }catch(InterruptedException E){
//            System.out.println(E);
//        }
//        System.out.println("Пoтoк " + name+ " начинает третью фазу");
        System.out.println("Пoтoк " + name + ". Я устал. Я ухожу");
        phsr.arriveAndDeregister(); //известить о достижении
        System.out.println(name + ": третья фазa пошла к исполнению");
        //фазы и снять потоки с регистрации
    }
}