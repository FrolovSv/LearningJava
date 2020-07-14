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
public class StudyCallable {

    public static void main(String args[]) {
        ExecutorService es = Executors.newFixedThreadPool(3);
        Future<Integer> f;
        Future<Double> f2;
        Future<Integer> f3;
        System.out.println("Запуск");
        f = es.submit(new Sum(10));
        f2 = es.submit(new Hypot(3, 4));
        f3 = es.submit(new Factorial(6));
        try {
            try {
                System.out.println(f.get(1000, TimeUnit.MILLISECONDS));
                System.out.println(f2.get(1000, TimeUnit.MILLISECONDS));
                System.out.println(f3.get(1000, TimeUnit.MILLISECONDS));
            } catch (TimeoutException ex) {
                System.out.println("Не дождались (((( ");
            }
            
//            System.out.println(f.get());
//            System.out.println(f2.get());
//            System.out.println(f3.get());
        } catch (InterruptedException ехс) {
            System.out.println(ехс);
        } catch (ExecutionException ехс) {
            System.out.println(ехс);
        }
        es.shutdown();
        System.out.println("-----------Зaвepшeниe----------");

    }
}

//  Три потока исполнения вычислений 
class Sum implements Callable<Integer> {
    int stop;
    Sum(int v) {
        stop = v;
    }
    public Integer call(){
        int sum = 0;
        System.out.println("Вычисляем сумму");
        for(int i = 1;i  <=  stop ;i++){ 
            try {
                
                Thread.sleep(500);
            } catch (InterruptedException ex) {
                Logger.getLogger(Sum.class.getName()).log(Level.SEVERE, null, ex);
            }
            sum += i; 
            System.out.println("сумма равна: "+sum);
        }
        return sum;
    }
}

class  Hypot  implements  Callable<Double>  { 
    double sidel, side2;

    Hypot(double sl, double s2) {
        sidel = sl;
        side2 = s2;
    }
    public Double call()  {
        System.out.println("Вычисляем гипотенузу, вот и она: " + Math.sqrt((sidel * sidel) + (side2 * side2)));
        return Math.sqrt((sidel * sidel) + (side2 * side2));
    }
}

class  Factorial  implements  Callable<Integer>  { 
    int stop;
    Factorial(int v) {
        stop = v;
    }
    public Integer call() {
        int fact = 1;
        System.out.println("Вычисляем факториал");
        for (int i = 2; i <= stop; i++) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException ex) {
                Logger.getLogger(Factorial.class.getName()).log(Level.SEVERE, null, ex);
            }
            fact *= i;
            System.out.println("Процесс вычисления факториала: "+ fact);
        }
        return fact;
    }
}
