package ajeffrey.teaching.util.list;

/**
 * A factory for building list objects.
 * @author Alan Jeffrey
 * @version 1.0.3
 */
public interface MutableListFactory {

    /**
     * Build a new empty list object.
     * @return a new list object
     */
    public MutableList build ();
    
    public OptimisticMutableList build2();  //added build2 method to creat OptimisticMutableList instance
    

}
