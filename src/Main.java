public class Main {
    public static void main(String[] args) {
        testPartA();
//        testPartB();
//        System.out.println("\nAll tests are done!");


    }

    private static void testPartA() {
        System.out.println("Testing part A...");
        testPartA1();
        testPartA2();
    }

    private static void testPartA1() {
        System.out.println("Testing part A1...");
        BinNode<Character> root = new BinNode<>('h');
        root.setLeft(new BinNode<>('e'));
        root.setRight(new BinNode<>('a'));
        root.getLeft().setLeft(new BinNode<>('B', new BinNode<>('k'), null));
        root.getLeft().setRight(new BinNode<>('y', new BinNode<>('M'), new BinNode<>('!')));

        String[] inputs = {"hey", "Hey", "h", "k", "aaa", "hE", "hA", "ha", "@123"};

        for (String input : inputs) {
            boolean result = PathFromRoot.doesPathExist(root, input);
            System.out.println("Path \"" + input + "\"? " + result);
        }

        }




    private static void testPartA2() {
        System.out.println("Testing part A2...");
        BinNode<Integer> root = new BinNode<>(5);
        root.setLeft(new BinNode<>(7));
        root.setRight(new BinNode<>(3));
        root.getLeft().setLeft(new BinNode<>(7, new BinNode<>(5), null));
        root.getLeft().setRight(new BinNode<>(2, new BinNode<>(9), new BinNode<>(5)));

        int[] inputs = {7, 2, -1, 5, 6, 0};

        for (int input : inputs) {
            int result = LevelMostOccurrences.getLevelWithMostOccurrences(root, input);
            System.out.println("Level with most occurrences of " + input + ": " + result);
        }

        System.out.println();
    }
//
//    private static void testPartB() {
//        Thread t = new Thread(Main::testPartBOnThread);
//        t.start();
//        try {
//            t.join(5000);  // Wait at most 5 seconds
//        } catch (InterruptedException e) {
//        }
//
//        if (t.isAlive()) {  // Timeout
//            System.out.println("Timout occurred while testing part B...");
//            t.stop();
//        }
//    }
//
//    private static void testPartBOnThread() {
//        System.out.println("Testing part B...");
//        for (int i = 0; i < 100; i++) {
//            Counter.count = 0;
//            MyReentrantLock myLock = new MyReentrantLock();
//            Thread t1 = new Thread(new OneAcquireWorker(myLock));
//            t1.start();
//            Thread t2 = new Thread(new TryWithResourcesAcquireWorker(myLock));
//            t2.start();
//
//            // Wait for the completion of the workers
//            try {
//                t1.join();
//                t2.join();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//
//            System.out.println("Iteration " + (i + 1) + ", Counter = " + Counter.count);
//        }
//
//        try {
//            Lock lock = new MyReentrantLock();
//            lock.release();
//        } catch (IllegalReleaseAttempt e) {
//            System.out.println("Cannot release the lock!");
//        }
//
//        try {
//            Lock lock = new MyReentrantLock();
//            lock.release();
//        } catch (IllegalMonitorStateException e) {
//            System.out.println("Cannot release the lock!");
//        }
//
//        try (MyReentrantLock lock = new MyReentrantLock()) {
//        } catch (IllegalReleaseAttempt e) {
//            System.out.println("Cannot release the lock!");
//        }
//
//        Lock lock = new MyReentrantLock();
//        boolean result = lock.tryAcquire();
//        if (result) {
//            System.out.println("Locked the lock, now releasing it.");
//            lock.release();
//        } else {
//            System.out.println("You should not reach here!");
//        }
//    }
//}
//
//
//class Counter {
//    public static int count = 0;
//}
//
//abstract class Worker implements Runnable {
//    protected final MyReentrantLock lock;
//
//    public Worker(MyReentrantLock lock) {
//        this.lock = lock;
//    }
//
//    protected abstract void lockAndIncrement();
//
//    @Override
//    public void run() {
//        for (int i = 0; i < 100000; i++) {
//            lockAndIncrement();
//            if (i % 100 == 0) {
//                Thread.yield();  // Give other threads a chance by giving up the current thread's time slice
//            }
//        }
//    }
//}
//
//class OneAcquireWorker extends Worker {
//    public OneAcquireWorker(MyReentrantLock lock) {
//        super(lock);
//    }
//
//    @Override
//    protected void lockAndIncrement() {
//        try {
//            lock.acquire();
//            Counter.count++;
//        } finally {
//            lock.release();
//        }
//    }
//}
//
//
//class TryWithResourcesAcquireWorker extends Worker {
//    public TryWithResourcesAcquireWorker(MyReentrantLock lock) {
//        super(lock);
//    }
//
//    @Override
//    protected void lockAndIncrement() {
//        try (lock) {
//            lock.acquire();
//            try {
//                lock.acquire();
//                Counter.count++;
//            } finally {
//                lock.release();
//            }
//        }
//        //Auto release of the lock by its AutoCloseable (close()) implementation.
//    }
}