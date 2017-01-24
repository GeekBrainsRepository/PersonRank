package services;

import beans.Sites;
import mappers.ISitesMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * CRUD service for Sites table
 */
@Service
public class SitesService implements ISitesMapper {
    @Autowired
    private ISitesMapper iSitesMapper;

    @Override
    public List<Sites> getSites() {
        return iSitesMapper.getSites();
    }
}
