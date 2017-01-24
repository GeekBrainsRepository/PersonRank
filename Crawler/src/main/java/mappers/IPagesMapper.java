package mappers;

import beans.Pages;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * mapper for Pages table
 */
public interface IPagesMapper {

    String SELECT_PAGES = "SELECT id, url, site_id as siteId, found_date_time as " +
            "foundDateTime, last_scan_date as lastScanDate FROM pages";

    @Select(SELECT_PAGES)
    List<Pages> getPages();

}
