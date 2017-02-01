package mappers;

import beans.Sites;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * mapper for Sites table
 */
public interface ISitesMapper {

    String SELECT_SITES = "SELECT ID as id, Name as name FROM Sites";

    @Select(SELECT_SITES)
    List<Sites> getSites();
}
