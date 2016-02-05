package lock;

public class SynchronizeLab {

    private Object lockD = new Object();
    private Object lockE = new Object();

    /**
     * Thread holds the lock of the object
     */
    public synchronized void doA() {
        System.out.println(String.format("%s is running doA at %d.", Thread.currentThread().getName(), System.currentTimeMillis()));
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Thread holds the lock of the object
     */
    public synchronized void doB() {
        System.out.println(String.format("%s is running doB at %d.", Thread.currentThread().getName(), System.currentTimeMillis()));
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Thread holds the lock of the class
     */
    public synchronized static void doC() {
        System.out.println(String.format("%s is running doC at %d.", Thread.currentThread().getName(), System.currentTimeMillis()));
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Thread holds the lock of the member variable
     */
    public void doD() {
        synchronized (lockD) {
            System.out.println(String.format("%s is running doD at %d.", Thread.currentThread().getName(), System.currentTimeMillis()));
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Thread holds the lock of the member variable
     */
    public void doE() {
        synchronized (lockE) {
            System.out.println(String.format("%s is running doE at %d.", Thread.currentThread().getName(), System.currentTimeMillis()));
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String args[]) {
        final SynchronizeLab lab = new SynchronizeLab();
        // the later thread has to wait until the previous thread releases the lock
        // threadC don't have to wait as the lock is on object instead of class
        Thread threadA = new Thread(new Runnable() {
            public void run() {
                lab.doA();
            }
        });
        Thread threadB = new Thread(new Runnable() {
            public void run() {
                lab.doB();
            }
        });
        Thread threadC = new Thread(new Runnable() {
            public void run() {
                SynchronizeLab.doC();
            }
        });
        Thread threadD = new Thread(new Runnable() {
            public void run() {
                lab.doD();
            }
        });
        Thread threadE = new Thread(new Runnable() {
            public void run() {
                lab.doE();
            }
        });
        threadA.start();
        threadB.start();
        threadC.start();
        threadD.start();
        threadE.start();
    }
}
