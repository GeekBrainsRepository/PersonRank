package mappers;

import beans.Sites;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * mapper for Sites table
 */
public interface ISitesMapper {

    String SELECT_SITES = "SELECT id, name FROM sites";

    @Select(SELECT_SITES)
    List<Sites> getSites();

}
