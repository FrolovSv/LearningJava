/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package study.ThreadConcurrenc;

import java.util.concurrent.Exchanger;
/**
 *
 * @author SusPecT
 */
public class StudyExchanger {
    public static void main(String args[]){
        Exchanger<String> exgr = new Exchanger<String>();
        Thread th1 = new Thread(new UseString(exgr, "A"));
        th1.start();
        Thread th2 = new Thread(new MakeString(exgr, "B"));
        th2.start();
    }
}

// Поток типа Thread, формирующий символьную строку
class MakeString implements Runnable {
    Exchanger<String> ех;
    String str;
    String name;
    MakeString(Exchanger<String> с, String Name){
        ех = с;
        str = new String();
        name = Name;
    }
    @Override
    public void run(){
        System.out.println("Запустили поток: " + name);
        char ch = 'А';
        for(int i = 0; i < 3; i++){
            // заполнить буфер
            for(int j = 0; j < 5; j++)
            str += (char) ch++;
            System.out.println("Заполнили буфер перед отправкой!");
            try{
                // обменять заполненный буфер на пустой
                str = ех.exchange(str);
                System.out.println("Выгрузили буфер!");
            }catch(InterruptedException ехс){
                System.out.println(ехс);
            }
        }
        System.out.println("!Поток завершен: " + name);
    }
}

// Поток типа Thread, использующий символьную строку
class UseString implements Runnable {
    Exchanger<String> ех;
    String str;
    String name;
    UseString(Exchanger<String> с, String Name){
        ех = с;
        name = Name;
    }
    public void run(){
        System.out.println("Запустили поток: " + name);
        for(int i=0; i < 3; i++){
            try {
                // обменять пустой буфер на заполненный
                System.out.println("Ожидаем получение данных");
                str = ех.exchange(new String());
                System.out.println("Получили данные из буфера: " + str);
            }catch(InterruptedException ехс) {
                System.out.println(ехс);
            }
        }
        System.out.println("!Поток завершен: " + name);
    }
}