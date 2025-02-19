class Super {
    private int item = 0;
    private final int LIMITE_SUPERIOR = 10;

    public synchronized void get() {
        while (item < 1) {
            try {
                System.out.println(Thread.currentThread().getName() + " espera porque no hay items en la bodega.");
                wait();
                Thread.sleep(45000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        item--;
        System.out.println(Thread.currentThread().getName() + " consumio un item. Items en la bodega: " + item);
        notifyAll();
    }

    public synchronized void put() {
        while (item >= LIMITE_SUPERIOR) {
            try {
                System.out.println(Thread.currentThread().getName() + " espera porque la bodega esta llena.");
                wait();
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        item++;
        System.out.println(Thread.currentThread().getName() + " agrego un item. Ahora hay " + item + " items en la bodega");
        notifyAll();
    }
}   

class Empresa implements Runnable {
    private Super super1;
    
    Empresa(Super super1) {
        this.super1 = super1;
    }

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            super1.put();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class Cliente implements Runnable {
    private Super super1;
    
    Cliente(Super super1) {
        this.super1 = super1;
    }

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            super1.get();
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

public class SuperMarket {
    public static void main(String[] args) {
        Super s = new Super();
        Empresa e = new Empresa(s);
        Cliente c = new Cliente(s);
        
        new Thread(e, "Empresa").start();
        new Thread(c, "Cliente").start();
    }
}