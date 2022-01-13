package bufferManager;


/*
 *------------------------------------------------------------------------------
 * VCS: git@git.HAW-Hamburg.de:shf/Px/LabExercise/KXL_ThreadSafeFixedSizeBuffer_Distr[.git]
 * For further information see ReadMe.txt
 *                                                Michael Schaefers  2021/01/21
 *------------------------------------------------------------------------------
 */
public class TemplateStub2<T> implements BufferManager<T> {
// TemplateStub2 ist ein sehr schlechter Name - es ist Ihre Aufgabe einen besseren zu finden, sofern Sie dieses Template verwenden
    
    
    
    
    
    public TemplateStub2( final int capacity ){
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
