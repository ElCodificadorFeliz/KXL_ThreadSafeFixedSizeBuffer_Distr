package bufferManager;


import java.io.Serializable;


/*
 *------------------------------------------------------------------------------
 * Herald
 *
 * "Home"-VCS:  git@git.HAW-Hamburg.de:shf/Px/LabExercise/ZZZ_SupportStuff[.git]
 * 
 * Author:      Michael Schaefers ([UTF-8]:"Michael Schäfers");  javaCode@Hamburg-UAS.eu
 * Version:     2021/01/21  ->  see serialVersionUID
 *------------------------------------------------------------------------------
 */
public class Herald implements Serializable {
    //                                           #__version__YYYY_MM_DD__xxxL   // #__<global-version>__<local/daily-version>      
    static final private long serialVersionUID = 1__0001001__2021_01_21__001L;  // leading 1 to avoid octal system - 7 digits left, should be enough
    
    
    
    
    
    public static void proclaimComingDeathOfExecutingThread(){
        final Thread executingThread = Thread.currentThread();                  // thread that executes this very code
        final StringBuilder sb = new StringBuilder();                           // (thread) local -> hence StringBuffer is NOT required
        sb.append(
            String.format(
                "%d:%s is going down\n",
                executingThread.getId(),
                executingThread.getName()
            )
        );
        proclaimMessage( sb );
    }//method()
    
    
    
    public static void proclaimMessage( final String message ){
        System.err.flush();
        System.out.flush();
        System.out.print( message );
        System.out.flush();
    }//method()
    //
    public static void proclaimMessage( final StringBuilder message ){
        proclaimMessage( message.toString() );
    }//method()
    //
    public static void proclaimMessage( final StringBuffer message ){
        proclaimMessage( message.toString() );
    }//method()
    
    
    public static void proclaimError( final String message ){
        System.out.flush();
        System.err.flush();
        System.err.print( message );
        System.err.flush();
    }//method()
    //
    public static void proclaimError( final StringBuilder message ){
        proclaimError( message.toString() );
    }//method()
    //
    public static void proclaimError( final StringBuffer message ){
        proclaimError( message.toString() );
    }//method()
    
}//class
