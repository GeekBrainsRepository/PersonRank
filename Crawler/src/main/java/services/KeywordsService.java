package services;

import beans.Keywords;
import mappers.IKeywordsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * service for keywords table
 */
@Service
public class KeywordsService implements IKeywordsMapper {
    @Autowired
    private IKeywordsMapper iKeywordsMapper;

    @Override
    public List<Keywords> getKeywords() {
        return iKeywordsMapper.getKeywords();
    }

    @Override
    public List<String> getKeywordsByPersonId(final int personId) {
        return iKeywordsMapper.getKeywordsByPersonId(personId);
    }
}
