package bufferManager;


/*
 *------------------------------------------------------------------------------
 * VCS: git@BitBucket.org:schaefers/Prg_Px_J-L_FixedSizeThreadSafeBufferManager_Distr[.git]
 * For further information see ReadMe.txt
 *                                                Michael Schaefers  2017/06/06
 *------------------------------------------------------------------------------
 */
public class Herald {
    
    public static void proclaimComingDeathOfExecutingThread(){
        final StringBuffer sb = new StringBuffer( "" );
        sb.append(
            String.format(
                "%d:%s is going down\n",
                Thread.currentThread().getId(),
                Thread.currentThread().getName()
            )
        );
        proclaim( sb.toString() );
    }//method()
    
    public static void proclaim( final String message ){
        System.err.flush();
        System.out.flush();
        System.out.print( message );
        System.out.flush();
    }//method()
    
}//class
