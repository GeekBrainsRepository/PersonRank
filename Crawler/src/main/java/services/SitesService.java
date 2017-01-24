package services;

import beans.Sites;
import mappers.ISitesMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * CRUD service for Sites table
 */
public class SitesService implements ISitesMapper {
    @Autowired
    private ISitesMapper iSitesMapper;

    @Override
    public List<Sites> getSites() {
        return iSitesMapper.getSites();
    }
}
