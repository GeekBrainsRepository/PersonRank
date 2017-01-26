package infologic.repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FakeUserRepository implements UserRepository {

	private Map<Integer, Common> fakeCommon = new HashMap();
	private List<Integer> fakeDaily = new ArrayList();

	{
		Map<String, Integer> answer_table = new HashMap();
		answer_table.put("Путин", 450);
		answer_table.put("Медведев", 150);
		answer_table.put("Навальный", 250);
		Date date = new Date(100000000);
		fakeCommon.put(1, new Common(date, answer_table));
	}
	{
		Map<String, Integer> answer_table = new HashMap();
		answer_table.put("Путин", 350);
		answer_table.put("Медведев", 50);
		answer_table.put("Навальный", 350);
		Date date = new Date(200000000);
		fakeCommon.put(2, new Common(date, answer_table));
	}

	{
		fakeDaily.add(100);
		fakeDaily.add(200);
		fakeDaily.add(300);
		fakeDaily.add(400);
	}

	public Common get(int siteId) {
		return fakeCommon.get(siteId);
	}

	@Override
	public Daily get(int siteId, int personId, long dateStart, long dateEnd) {
		return new Daily(fakeDaily);
	}
}
