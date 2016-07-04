package dumper.MultiProcessing;

import java.util.concurrent.BlockingQueue;

/**
 * Created by onotole on 10.06.16.
 */
public class Producer implements Runnable {
    private Integer                     startId;
    private Integer                     finishId;
    private Integer                     step;
    private static volatile Integer     currentId;

    public Producer(Integer startId, Integer finishId, Integer step, BlockingQueue<Integer> queue) {
        this.startId = startId;
        this.queue = queue;
        this.finishId = finishId;
        this.step = step;
    }

    private BlockingQueue<Integer>      queue;

    public static Integer getCurrentId() {
        return currentId;
    }

    public void addBackFailedId(Integer id) {
        queue.offer(id);
    }

    public void run() {
        currentId = startId;
        while (currentId < finishId) {
            try {
                queue.put(currentId);
            } catch (InterruptedException e) {
                System.out.println("Finished at " + currentId);
                e.printStackTrace();
            }
            currentId += step;
        }
    }

}
