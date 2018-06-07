package bufferManager;


/*
 *------------------------------------------------------------------------------
 * VCS: git@BitBucket.org:schaefers/Prg_Px_K-L_FixedSizeThreadSafeBufferManager_Distr[.git]
 * For further information see ReadMe.txt
 *                                                Michael Schaefers  2017/06/06
 *------------------------------------------------------------------------------
 */
public class Herald {
    
    public static void proclaimDeath(){
        final StringBuffer sb = new StringBuffer( "" );
        sb.append(
            String.format(
                "%d:%s is going down\n",
                Thread.currentThread().getId(),
                Thread.currentThread().getName()
            )
        );
        System.out.flush();
        System.out.printf( "%s\n",  sb.toString() );
        System.out.flush();
    }//method()
    
}//class
