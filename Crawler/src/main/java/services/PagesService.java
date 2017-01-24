package services;

import beans.Pages;
import mappers.IPagesMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * crud service for Page table
 */
public class PagesService implements IPagesMapper {
    @Autowired
    private IPagesMapper iPagesMapper;

    @Override
    public List<Pages> getPages() {
        return iPagesMapper.getPages();
    }
}
