package connectn;

/**
 * Created by Akshay on 1/24/2016.
 */
public class IterativeDeepener{

    private final long TIME_LIMIT_BUFFER = 250;
    private final int THREAD_COUNT = 2;

    private Player player;
    private int startDepth;
    private int stepDepth;

    private int maxDepth;

    private int bestActionDepth;
    private Action bestAction;

    private Object lock = new Object();

    private Flag stopFlag = new Flag();

    public IterativeDeepener(Player player, int start, int step){
        this.player = player;
        this.startDepth = start;
        this.stepDepth = step;
        this.maxDepth = start;

    }

    public Action makeMove(Board board){

        stopFlag.set(false);
        Thread[] minimaxThreads = new Thread[THREAD_COUNT];
        for(int i = 0; i < THREAD_COUNT; i++){
            minimaxThreads[i] = new Thread(new MinimaxRunner(board));
            minimaxThreads[i].start();
        }


        try {
            Thread.sleep(player.timeLimit - TIME_LIMIT_BUFFER);
        } catch (Exception e){}

        Action finalAction = bestAction;
        stopFlag.set(true);

        for(int i = 0; i < THREAD_COUNT; i++){
            try {
                minimaxThreads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return finalAction;
    }

    class MinimaxRunner implements Runnable {

        final Board board;

        public MinimaxRunner(Board board){
            this.board = board;
        }

        @Override
        public void run(){
            int depth;

            while(!stopFlag.get()) {
                synchronized (lock) {
                    depth = maxDepth;
                    maxDepth += stepDepth;
                }

                System.out.println("starting with "+depth);
                Stopwatch.start("Minimax("+depth+")");
                Action action = player.minimax(board, depth, stopFlag);

                if(!stopFlag.get()) {
                    synchronized (lock) {
                        if (depth > bestActionDepth) {
                            bestActionDepth = depth;
                            bestAction = action;
                        }
                    }
                }
                Stopwatch.stop("Minimax("+depth+")");
            }

        }
    }

}
