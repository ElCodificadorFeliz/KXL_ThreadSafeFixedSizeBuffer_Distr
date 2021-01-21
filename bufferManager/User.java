package bufferManager;


import java.util.Random;
import java.util.concurrent.TimeUnit;


/*
 *------------------------------------------------------------------------------
 * VCS: git@git.HAW-Hamburg.de:shf/Px/LabExercise/KXL_ThreadSafeFixedSizeBuffer_Distr[.git]
 * For further information see ReadMe.txt
 *                                                Michael Schaefers  2021/01/21
 *------------------------------------------------------------------------------
 */
public class User implements Runnable {
    
    private final BufferManager<Long> bm;
    
    
    
    public User( final BufferManager<Long> bm ){
        this.bm = bm;
    }//constructor()
    
    
    
    @Override
    public void run(){
        final Random randomGenerator = new Random();
        try{
            while( ! Thread.interrupted() ){
                final long data = bm.remove();
                if( -1L == data ){
                    //\=> "death pill taken"
                    Herald.proclaimComingDeathOfExecutingThread();
                    return;
                }
                //
                final int randomDelay = 20 + randomGenerator.nextInt( 90 );
                TimeUnit.MILLISECONDS.sleep( randomDelay );
            }//while
        }catch( final InterruptedException ex ){
            final Thread executingThread = Thread.currentThread();              // thread that executes this very code
            final StringBuilder sb = new StringBuilder();                       // (thread) local -> hence StringBuffer is NOT required
            sb.append(
                String.format(
                    "%d:%s received interrupt\n",
                    executingThread.getId(),
                    executingThread.getName()
                )
            );
            Herald.proclaimMessage( sb );
        }finally{
            Herald.proclaimComingDeathOfExecutingThread();
        }//try  
    }//method()
    
}//class
