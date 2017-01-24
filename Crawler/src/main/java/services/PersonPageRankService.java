package services;

import beans.PersonPageRank;
import mappers.IPersonPageRankMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * service for PersonPageRank table
 */
public class PersonPageRankService implements IPersonPageRankMapper {
    @Autowired
    private IPersonPageRankMapper iPersonPageRankMapper;

    @Override
    public List<PersonPageRank> getPersonPageRank() {
        return iPersonPageRankMapper.getPersonPageRank();
    }
}
