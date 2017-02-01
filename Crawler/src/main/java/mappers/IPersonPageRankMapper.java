package mappers;

import beans.PersonPageRank;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * mapper for table PersonPageRank
 */
public interface IPersonPageRankMapper {

    String SELECT_PERSON_PAGE_RANK = "SELECT person_id as personId, page_id as pageId," +
            "rank FROM person_page_rank";
    String INSERT_PERSON_PAGE_RANK = "INSERT INTO person_page_rank (person_id, page_id, rank) " +
            "VALUES (#{personId},#{pageId},#{rank})";
    String UPDATE_PERSON_PAGE_RANK = "UPDATE person_page_rank SET rank = #{rank} WHERE " +
            "person_id = #{personId} AND page_id = #{pageId}";

    @Select(SELECT_PERSON_PAGE_RANK)
    List<PersonPageRank> getPersonPageRank();

    @Insert(INSERT_PERSON_PAGE_RANK)
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void setInsertPersonPageRank(PersonPageRank personPageRank);

    @Update(UPDATE_PERSON_PAGE_RANK)
    void setUpdatePersonPageRank(@Param("rank") int rank, @Param("personId") int personId, @Param("pageId") int pageId);
}
