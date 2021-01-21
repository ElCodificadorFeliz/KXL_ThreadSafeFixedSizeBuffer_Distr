package bufferManager;


import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;


/*
 *------------------------------------------------------------------------------
 * VCS: git@git.HAW-Hamburg.de:shf/Px/LabExercise/KXL_ThreadSafeFixedSizeBuffer_Distr[.git]
 * For further information see ReadMe.txt
 *                                                Michael Schaefers  2021/01/21
 *------------------------------------------------------------------------------
 */
public class Maker implements Runnable {
    
    final BufferManager<Long> bm;
    final AtomicLong counter;
    
    
    
    public Maker( final BufferManager<Long> bm,  final AtomicLong counter ){
        this.bm = bm;
        this.counter = counter;
    }//constructor()
    
    
    
    @Override
    public void run(){
        final Random randomGenerator = new Random();
        try{
            while( ! Thread.interrupted() ){
                final long data = counter.getAndIncrement();
                //
                final int randomDelay = 20 + randomGenerator.nextInt( 90 );
                TimeUnit.MILLISECONDS.sleep( randomDelay );
                //
                bm.insert(data);
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
