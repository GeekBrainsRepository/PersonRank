package mappers;

import beans.Keywords;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * mapper for keywords table
 */
public interface IKeywordsMapper {

    String SELECT_KEYWORDS = "SELECT id, name, person_id as personId FROM keywords";

    @Select(SELECT_KEYWORDS)
    List<Keywords> getKeywords();
}
