package about_11to12_FixedSizeThreadSafeBufferManager_Distr;


/*
 *------------------------------------------------------------------------------
 * VCS: git@BitBucket.org:schaefers/Prg_Px_K-L_FixedSizeThreadSafeBufferManager_Distr[.git]
 * For further information see ReadMe.txt
 *                                                Michael Schaefers  2017/06/05
 *------------------------------------------------------------------------------
 */
public class BufferManagerV1<T> implements BufferManager<T> {
    
    public BufferManagerV1( final int capacity ){
        // TODO
    }//constructor()
    
    
    
    @Override
    public void insert( final T data ) throws InterruptedException {
        // TODO;
    }//method()
    
    @Override
    public T remove() throws InterruptedException {
        // TODO
        return null;
    }//method()
    
    @Override
    public int getUsage() {
        // TODO
        return 0;
    }//method()
    
    @Override
    public int getRemainingCapacity() {
        // TODO
        return 0;
    }//method()
    
    @Override
    public int getCapacity() {
        // TODO
        return 0;
    }//method()
    
}//class
