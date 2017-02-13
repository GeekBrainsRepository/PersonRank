package mappers;

import beans.PersonPageRank;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * mapper for table PersonPageRank
 */
public interface IPersonPageRankMapper {

    String SELECT_PERSON_PAGE_RANK = "SELECT ID as id, PersonID as personId, PageID as pageId," +
            "Rank as rank FROM PersonPageRank";
    String INSERT_PERSON_PAGE_RANK = "INSERT INTO PersonPageRank (PersonID, PageID, Rank) " +
            "VALUES (#{personId},#{pageId},#{rank})";
    String UPDATE_PERSON_PAGE_RANK = "UPDATE PersonPageRank SET Rank = #{rank} WHERE " +
            "PersonID = #{personId} AND PageID = #{pageId}";
    String DELETE_ALL = "DELETE FROM PersonPageRank";

    @Select(SELECT_PERSON_PAGE_RANK)
    List<PersonPageRank> getPersonPageRank();

    @Insert(INSERT_PERSON_PAGE_RANK)
    @Options(useGeneratedKeys = true, keyProperty = "ID")
    void setInsertPersonPageRank(PersonPageRank personPageRank);

    @Update(UPDATE_PERSON_PAGE_RANK)
    void setUpdatePersonPageRank(@Param("rank") int rank, @Param("personId") int personId, @Param("pageId") int pageId);

    @Delete(DELETE_ALL)
    void deleteAll();
}
