package dumper.MultiProcessing;

import dumper.Main;

/**
 * Created by onotole on 10.06.16.
 */
public class Informer implements Runnable {

    private Producer    producer;
    private int         previousId = 0;
    private int         currentId = 0;
    private int         timeout;

    public Informer(Producer producer, int timeout) {
        this.producer = producer;
        this.timeout = timeout;
    }

    @Override
    public void run() {
        System.out.println("Informer ready!");
        while (! Thread.currentThread().isInterrupted()) {
            currentId = producer.getCurrentId();
            String output = "Current speed: " + (currentId - previousId) + " ids/" + timeout/1000 + "s. " +
                    "Current id: " +
                    currentId + ". ";
            if (Main.queue2Repeat != null) {
                output = output +  "Failed ids: " + Main.queue2Repeat.toString();
            }

            Main.LOG.info(output);
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
