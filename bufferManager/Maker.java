package bufferManager;


import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;


/*
 *------------------------------------------------------------------------------
 * VCS: git@BitBucket.org:schaefers/Prg_Px_K-L_FixedSizeThreadSafeBufferManager_Distr[.git]
 * For further information see ReadMe.txt
 *                                                Michael Schaefers  2017/06/06
 *------------------------------------------------------------------------------
 */
public class Maker implements Runnable {
    
    @Override
    public void run(){
        final Random randomGenerator = new Random();
        while( ! Thread.interrupted() ){
            try{
                final long data = counter.getAndIncrement();
                //
                final int randomDelay = 20 + randomGenerator.nextInt( 90 );
                TimeUnit.MILLISECONDS.sleep( randomDelay );
                //
                bm.insert(data);
            }catch( final InterruptedException ex ){
                Herald.proclaimComingDeathOfExecutingThread();;
                return;
            }//try
        }//while
    }//method()
    
    
    
    public Maker( final BufferManager<Long> bm,  final AtomicLong counter ){
        this.bm = bm;
        this.counter = counter;
    }//constructor()
    
    
    
    final BufferManager<Long> bm;
    final AtomicLong counter;
    
}//class
