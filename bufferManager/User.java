package bufferManager;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/*
 *------------------------------------------------------------------------------
 * VCS: git@BitBucket.org:schaefers/Prg_Px_K-L_FixedSizeThreadSafeBufferManager_Distr[.git]
 * For further information see ReadMe.txt
 *                                                Michael Schaefers  2017/06/06
 *------------------------------------------------------------------------------
 */
public class User implements Runnable {
    
    @Override
    public void run(){
        final Random randomGenerator = new Random();
        while( ! Thread.interrupted() ){
            try{
                final long data = bm.remove();
                if( -1L == data ){
                    //\=> "death pill taken"
                    Herald.proclaimDeath();
                    return;
                }
                //
                final int randomDelay = 20 + randomGenerator.nextInt( 90 );
                TimeUnit.MILLISECONDS.sleep( randomDelay );
                //
                // TODO
            }catch( final InterruptedException ex ){
                Herald.proclaimDeath();
                return;
            }//try
        }//while
    }//method()
    
    
    
    public User( final BufferManager<Long> bm ){
        this.bm = bm;
    }//constructor()
    
    
    
    private final BufferManager<Long> bm;
    
}//class
