package about_11to12_FixedSizeThreadSafeBufferManager_Distr;


import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;


/*
 *------------------------------------------------------------------------------
 * VCS: git@BitBucket.org:schaefers/Prg_Px_K-L_FixedSizeThreadSafeBufferManager_Distr[.git]
 * For further information see ReadMe.txt
 *                                                Michael Schaefers  2017/06/06
 *------------------------------------------------------------------------------
 */
public class TestFrame {
    
    public static void main( final String... unused ){
        
        //
        //
        //  SETUP
        //  =====
        //
        final int capacity = 4;
        final int numberOfMakers = 10;
        final int numberOfUsers = 10;
        
        
        
        //
        //
        //  action
        //
        final Thread[] maker = new Thread[numberOfMakers];
        final Thread[] user = new Thread[numberOfUsers];
        
        final BufferManager<Long> bm = new BufferManagerV1<Long>( capacity );
        final AtomicLong counter = new AtomicLong( 0 );
        
        
        for( int i=0; i<numberOfMakers; i++ ){
            maker[i] = new Thread( new Maker( bm, counter ) );
            maker[i].setName( String.format( "Maker#%d", i ));
            maker[i].start();
        }//for
        
        for( int i=0; i<numberOfUsers; i++ ){
            user[i] = new Thread( new User( bm ) );
            user[i].setName( String.format( "User#%d", i ));
            user[i].start();
        }//for
        
        
        try{
            TimeUnit.SECONDS.sleep( 10 );
            
            
            for( int i=0; i<numberOfMakers; i++ ){
                maker[i].interrupt();
                maker[i].join();
            }//for
            
            for( int i=0; i<numberOfUsers; i++ ){
                bm.insert( -1L );
            }//for
            for( int i=0; i<numberOfUsers; i++ ){
                user[i].join();
            }//for
            
            Herald.proclaimDeath();
            System.out.printf( "THE END" );
            System.out.flush();
        }catch( InterruptedException ex ){
            ex.printStackTrace();
        }//try
        
    }//method()
    
}//class
