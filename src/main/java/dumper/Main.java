package dumper;

import dumper.MultiProcessing.Consumer;
import dumper.MultiProcessing.Informer;
import dumper.MultiProcessing.Producer;
import org.apache.log4j.Logger;

import java.util.concurrent.*;

/**
 * Created by onotole on 10.06.16.
 */
public class Main {
    public static BlockingQueue<Integer> queue;
    public static BlockingQueue<Integer>  queue2Repeat;
    public static int failedCounter = 0;
    public static final Logger LOG = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) throws InterruptedException {
        BlockingQueue<Integer>  queue = new ArrayBlockingQueue<Integer>(20);
        BlockingQueue<Integer>  queue2Repeat = new ArrayBlockingQueue<Integer>(1000);
        Producer                producer = new Producer(0, 100_000_000, 100, queue);
        int                     processCount = 40;
        Thread                  threadProducer = new Thread(producer);
        ExecutorService         executor = Executors.newFixedThreadPool(processCount);
        String                  tempDBName = "wotbTemp";
        String                  finalDBName = "wotb.db";
        DBManager               dbManager = new DBManager();
        Consumer                consumer;
        Informer                informer = new Informer(producer, 1000);
        Thread threadInformer = new Thread(informer);

        threadProducer.start();

        for (int i = 0; i < processCount; i++) {
            LOG.info("start thread № " + i);
            consumer = new Consumer(queue, tempDBName + i + ".db", 100);
            executor.execute(consumer);
            Thread.sleep(100);
        }

        threadInformer.start();

        long startTime = System.currentTimeMillis();

        // wait for finish
        threadProducer.join();
        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.SECONDS);
        threadInformer.interrupt();

        //repeat failed ids
        LOG.info("Start processing failed ids");
        LOG.info("Failed " + failedCounter + " ids");
        LOG.info(queue2Repeat.toString());

        Consumer consumerFailed = new Consumer(queue2Repeat, tempDBName + (processCount + 1) + ".db", 100);
        Thread threadFailed = new Thread(consumerFailed);
        threadFailed.start();
        while (! queue2Repeat.isEmpty()) {
            LOG.info("finishing failed ids");
            Thread.sleep(100);

        }

        // merge
//        LOG.info("Start merging DBs");
//        dbManager.connect(finalDBName);
//        dbManager.createAccountInfoDB(finalDBName);
//
//        for (int i = 0; i < processCount + 1; i++) {
//            dbManager.connect(finalDBName);
//            System.out.println("start merging with db № " + i);
//            dbManager.mergeWithDB(tempDBName + i + ".db", i);
//            dbManager.close();
//        }
//        dbManager.close();

        System.out.println("Estimate : " + (System.currentTimeMillis() - startTime) );
        threadProducer.interrupt();
        executor.shutdownNow();

    }
}
