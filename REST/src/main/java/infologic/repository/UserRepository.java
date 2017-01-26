package infologic.repository;

public interface UserRepository {
	Common get(int siteId);

	Daily get(int siteId, int personId, long dateStart, long dateEnd);
}