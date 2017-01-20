/*
 * 
 */
package PersonRank.data;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class SyteRepository implements Repository {
    
    private static final SyteRepository INSTANCE = new SyteRepository();
    private ArrayList<Site> sites;
    
    private SyteRepository() {
        sites = new ArrayList<>();
    }
    
    public static SyteRepository getInstance() {
        return INSTANCE;
    }
    
    @Override
    public void add(Object entry) {       
    }

    @Override
    public void remove(Object entry) {      
    }

    @Override
    public void update(Object entry) {       
    }

    @Override
    public List query(Specification specification) {
        ArrayList<Site> newList = new ArrayList<>();
        for (Site site : sites) {
            if(specification.IsSatisfiedBy(site)) {
                newList.add(site);
            }
        }
        return newList;
    }
    
}
