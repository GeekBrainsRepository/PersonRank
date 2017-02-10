package mappers;

import beans.Pages;
import org.apache.ibatis.annotations.*;

import java.sql.Date;
import java.util.List;

/**
 * mapper for Pages table
 */
public interface IPagesMapper {

    String SELECT_PAGES = "SELECT ID as id, Url as url, SiteID as siteId, FoundDateTime as " +
            "foundDateTime, LastScanDate as lastScanDate FROM Pages";
    String SELECT_PAGES_WITH_LAST_SCAN_DATE_NULL = SELECT_PAGES + "WHERE LastScanDate = 'null'";
    String INSERT_PAGE = "INSERT INTO Pages (Url, SiteID, FoundDateTime, LastScanDate) VALUES" +
            "(#{url},#{siteId},#{foundDateTime},#{lastScanDate})";
    String UPDATE_LAST_SCAN_DATE = "UPDATE Pages SET LastScanDate = #{lastScanDate}" +
            "WHERE ID = #{id}";

    @Select(SELECT_PAGES)
    List<Pages> getPages();

    @Select(SELECT_PAGES_WITH_LAST_SCAN_DATE_NULL)
    List<Pages> getPagesWhithLastScanNull();

    @Insert(INSERT_PAGE)
    @Options(useGeneratedKeys = true, keyProperty = "ID")
    void insertPage(Pages page);

    @Update(UPDATE_LAST_SCAN_DATE)
    void setUpdateLastScanDate(@Param("id") int id, @Param("lastScanDate")final Date lastScanDate);
}
