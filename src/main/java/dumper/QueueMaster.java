package dumper;

import dumper.MultiProcessing.Consumer;
import dumper.MultiProcessing.Informer;
import dumper.MultiProcessing.Producer;
import org.apache.log4j.Logger;

import java.util.concurrent.*;

/**
 * Created by onotole on 04.07.16.
 */
public class QueueMaster {
    private static final    int SEC_1 = 1000;
    private static final    int BUNCH_SIZE_PER_REQEUST = 100;
    public static           BlockingQueue<Integer>  queue;
//    public static           BlockingQueue<Integer>  queue2Repeat;
    public static final     Logger                  LOG = Logger.getLogger(Main.class.getName());
    private                 int                     startId;
    private                 int                     finishId;
    private                 int                     processCount;
    private                 long                    nanoSecsAtStartTime;
    private                 long                    startTime;
    private                 String                  tempDBName;
    private                 Producer                producer;
    private                 Consumer                consumer;
    private                 Informer                informer;
    private                 Thread                  threadProducer;
    private                 Thread                  threadInformer;
    private                 ExecutorService         executor;


    public QueueMaster() {
        queue = new ArrayBlockingQueue<>(20);
//        queue2Repeat = new ArrayBlockingQueue<>(1000);
        nanoSecsAtStartTime = System.currentTimeMillis();
    }

    public void dump(int startId, int finishId, int processCount, String tempDBName) throws InterruptedException {
        startTime = System.currentTimeMillis();
        this.startId = startId;
        this.finishId = finishId;
        this.tempDBName = tempDBName;
        this.processCount = processCount;
        informer = new Informer(SEC_1);
        threadInformer = new Thread(informer);
        threadInformer.start();

        relaunch(startId);
        while( Informer.getCurrentId() < finishId ) {
            int currentSpeed = Informer.getCurrentSpeed();
            if (currentSpeed == 0 && (System.currentTimeMillis() - nanoSecsAtStartTime >= 0) ) {
                LOG.error("dumping stopped at " + Informer.getCurrentId() + ". Work time: " + (System.currentTimeMillis() - nanoSecsAtStartTime) / 1000 );
                relaunch(Informer.getCurrentId());
            }
            LOG.info("Current ID: " + producer.getCurrentId() + ". Current Speed: " + currentSpeed);
            Thread.sleep(10_000);
        }

        destroyOldStaff();

        LOG.info("Estimate : " + (System.currentTimeMillis() - startTime) );
        Init.init();
        System.exit(0);
    }

    private void relaunch(int startId) throws InterruptedException {
        destroyOldStaff();

        LOG.debug("start launch");
        producer = new Producer(startId, finishId, BUNCH_SIZE_PER_REQEUST, queue);
        threadProducer = new Thread(producer);
        threadProducer.start();

        executor = Executors.newFixedThreadPool(processCount);

        for (int i = 0; i < processCount; i++) {
            LOG.info("start thread № " + i);
            consumer = new Consumer(queue, tempDBName + i + ".db", 100);
            executor.execute(consumer);
            Thread.sleep(1000);
        }

        nanoSecsAtStartTime = System.currentTimeMillis();
        LOG.debug("fihish launching");
    }

    private void destroyOldStaff() {
        LOG.debug("start destroying old staff");
        producer = null;
        if (threadProducer != null) threadProducer.interrupt();
        threadProducer = null;
        if (executor != null) {
            executor.shutdown();
            executor.shutdownNow();
        }
        executor = null;

        System.gc();
        LOG.debug("finish destroying old staff");
    }
}
