package mappers;

import beans.Pages;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.Calendar;
import java.util.List;

/**
 * mapper for Pages table
 */
public interface IPagesMapper {

    String SELECT_PAGES = "SELECT id, url, site_id as siteId, found_date_time as " +
            "foundDateTime, last_scan_date as lastScanDate FROM pages";
    String INSERT_PAGE = "INSERT INTO url, site_id, found_date_time values" +
            "( #{url}, #{siteId}, #{foundDateTime}";
    String UPDATE_LAST_SCAN_DATE = "UPDATE pages SET last_scan_date = #{lastScanDate}" +
            "WHERE id = #{id}";

    @Select(SELECT_PAGES)
    List<Pages> getPages();

    @Insert(INSERT_PAGE)
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insertPage(String url, int siteId, Calendar foundDateTime);

    @Update(UPDATE_LAST_SCAN_DATE)
    void setUpdateLastScanDate(final Calendar lastScanDate);
}
