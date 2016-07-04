package dumper;

/**
 * Created by onotole on 10.06.16.
 */
public class Main {

//    public static int failedCounter = 0;


    public static void main(String[] args) throws InterruptedException {

        QueueMaster queueMaster = new QueueMaster();
        queueMaster.dump(0, 100_000, 20, "wotbTemp");

//
//        long startTime = System.currentTimeMillis();
//
//        // wait for finish
//
//
//        //repeat failed ids
//        LOG.info("Start processing failed ids");
//        LOG.info("Failed " + failedCounter + " ids");
//        LOG.info(queue2Repeat.toString());
//
//        Consumer consumerFailed = new Consumer(queue2Repeat, tempDBName + (processCount + 1) + ".db", 100);
//        Thread threadFailed = new Thread(consumerFailed);
//        threadFailed.start();
//        while (! queue2Repeat.isEmpty()) {
//            LOG.info("finishing failed ids");
//            Thread.sleep(100);
//
//        }
//        Init.init();

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

//        System.out.println("Estimate : " + (System.currentTimeMillis() - startTime) );
//        threadProducer.interrupt();
//        executor.shutdownNow();

    }
}
