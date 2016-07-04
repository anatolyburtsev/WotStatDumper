package dumper.MultiProcessing;

import dumper.QueueMaster;

/**
 * Created by onotole on 10.06.16.
 */
public class Informer implements Runnable {

    private int         previousId = 0;
    private static int  currentId = 0;
    private int         timeout;
    private static int  currentSpeed = 0;

    public static int getCurrentId() {
        return currentId;
    }

    public Informer(int timeout) {
        this.timeout = timeout;
    }

    public static int getCurrentSpeed() {
        return currentSpeed;
    }

    @Override
    public void run() {
        QueueMaster.LOG.info("Informer ready!");
        while (! Thread.currentThread().isInterrupted()) {
            try {
                currentId = Producer.getCurrentId();
            } catch (NullPointerException ex) {
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                continue;
            }
            currentSpeed = currentId - previousId;
            String output = "Current speed: " + currentSpeed + " ids/" + timeout/1000 + "s. " +
                    "Current id: " +
                    currentId + ". ";
//            if (Main.queue2Repeat != null) {
//                output = output +  "Failed ids: " + Main.queue2Repeat.toString();
//            }
//
//            QueueMaster.LOG.info(output);
            previousId = currentId;

            try {
                Thread.sleep(timeout);
            } catch (InterruptedException e) {
                return;
            }
        }
        System.out.println("Informer finished!");
    }
}
