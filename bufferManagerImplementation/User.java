// This source code is UTF-8 coded - see https://stackoverflow.com/questions/9180981/how-to-support-utf-8-encoding-in-eclipse
package bufferManagerImplementation;


import java.util.Random;
import java.util.concurrent.TimeUnit;

import untouchableSupportStuff.Herald;


/**
 * User/Task: For information see ReadMe.txt resp. task
 * 
 * @version {@value #encodedVersion}
 * @author  Michael Schäfers ;  P2@Hamburg-UAS.eu  
 */
public class User implements Runnable {
    //
    //--VERSION:-------------------------------#---vvvvvvvvv---vvvv-vv-vv--vv
    //  ========                               #___~version~___YYYY_MM_DD__dd_
    final static private long encodedVersion = 2___00001_001___2022_01_16__01L;
    //-----------------------------------------#---^^^^^-^^^---^^^^-^^-^^--^^
    
    
    
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
                    Herald.proclaimExecutingThreadInformation( "took death pill" );
                    return;
                }//if
                //
                final int randomDelay = 20 + randomGenerator.nextInt( 90 );
                TimeUnit.MILLISECONDS.sleep( randomDelay );
            }//while
        }catch( final InterruptedException ex ){                                // zählt NICHT als crash,..
            Herald.proclaimExecutingThreadInformation( "received interrupt" );  //..sondern ordentliche Terminierung
        }finally{
            Herald.proclaimComingDeathOfExecutingThread();
        }//try  
    }//method()
    
}//class
