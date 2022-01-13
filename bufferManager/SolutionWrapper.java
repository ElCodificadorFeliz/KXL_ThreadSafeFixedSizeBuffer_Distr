package bufferManager;


public class SolutionWrapper<T> implements BufferManager<T> {
    
    private BufferManager<T> chosenImplementation;
    
    
    
    
    
    public SolutionWrapper( final int capacity ){
        final int selectedImplemantation = 1;
        //
        switch( selectedImplemantation ){
            case 1: chosenImplementation = new TemplateStub1<T>( capacity ); break;
            case 2: chosenImplementation = new TemplateStub2<T>( capacity ); break;
            default: assert false : "implementation does NOT exist";
        }//switch
    }//constructor()
    
    
    
    
    
    @Override
    public void insert( final T data ) throws InterruptedException {
        chosenImplementation.insert( data );
    }//method()
    
    @Override
    public T remove() throws InterruptedException {
        return chosenImplementation.remove();
    }//method()
    
    @Override
    public int getUsage() {
        return chosenImplementation.getUsage();
    }//method()
    
    @Override
    public int getRemainingCapacity() {
        return chosenImplementation.getRemainingCapacity();
    }//method()
    
    @Override
    public int getCapacity() {
        return chosenImplementation.getCapacity();
    }//method()
    
}//class

