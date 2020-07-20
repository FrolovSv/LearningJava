/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LearningJava.ThreadConcurrenc;

// Расширить класс Phaser и переопределить
// метод onAdvance() таким образом, чтобы было
// выполнено только определенное количество фаз
import java.util.concurrent.*;
/**
 *
 * @author SusPecT
 */

// Расширить класс MyPhaser, чтобы выполнить
// только определенное количество фаз
public class StudyOnAdvance {
    public static void main(String args[]){
        MyPhaser phsr = new MyPhaser(1, 4);

        System. out. println ("Запуск потоков \n") ;
        Thread th1 = new Thread(new MyThreadAdvance(phsr, "A"));
        th1.start();
        Thread th2 = new Thread(new MyThreadAdvance(phsr, "В"));
        th2.start();
        Thread th3 = new Thread(new MyThreadAdvance(phsr, "С"));
        th3.start();

        // ожидать завершения определенного количества фаз
        while(!phsr.isTerminated()) {
            phsr.arriveAndAwaitAdvance();
            System.out.println("Cинxpoнизaтop фаз завершен");
        }
    }    
}

class MyPhaser extends Phaser {
    int numPhases;
    MyPhaser(int parties, int phaseCount){
        super(parties);
        numPhases = phaseCount - 1;
    }
    // переопределить метод onAdvance(), чтобы
    // выполнить определенное количество фаз
    @Override
    protected boolean onAdvance(int р, int regParties){
        // Следующий вызов метода println() требуется только
        // для целей иллюстрации. Как правило, метод
        // onAdvance() не отображает выводимые данные
        System.out.println("Фaзa " + р + " завершена.\n");
        // возвратить логическое значение true,
        // если все фазы завершены
        if (р == numPhases || regParties == 0) return true;
        // В противном случае возвратить логическое
        // значение false
        return false;
    }    
}

// Поток исполнения, применяющий синхронизатор фаз типа Phaser
class MyThreadAdvance implements Runnable {
    Phaser phsr;
    String name;
    MyThreadAdvance(Phaser р, String n){
        phsr = р;
        name = n;
        phsr.register();
    }
    @Override
    public void run(){
        while(!phsr.isTerminated()){
            System.out.println("Пoтoк " + name + " начинает фазу " + phsr.getPhase());
            phsr.arriveAndAwaitAdvance();
            // Небольшая пауза, чтобы не нарушить
            // порядок вывода. Это сделано только для
            // демонстрации, но совсем не обязательно для
            // правильного функционирования синхронизатора фаз
            try {
                Thread.sleep(10);
            }catch(InterruptedException е){
                System.out.println(е);
            }
        }
    }
}
