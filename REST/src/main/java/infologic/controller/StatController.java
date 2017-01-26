package infologic.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import infologic.repository.Common;
import infologic.repository.Daily;
import infologic.repository.FakeUserRepository;
import infologic.repository.UserRepository;

/**
 * Created by Антон Владимирович on 21.01.2017.
 */
@RestController
public class StatController {

	UserRepository repository = new FakeUserRepository(); // создаем репозиторий
															// пока фейковый

	@RequestMapping("/statistic/getpersonlist")
	public Map<Integer, String> getPersonList() {

		// fake data
		// TODO replace with DAO request
		Map<Integer, String> map = new HashMap<>();
		map.put(1, "First");
		map.put(2, "Second");

		return map;

	}

	@RequestMapping("/statistic/getresourcelist")
	public Map<Integer, String> getResourceList() {

		// fake data
		// TODO replace with DAO request
		Map<Integer, String> map = new HashMap<>();
		map.put(1, "lenta.ru");
		map.put(2, "meduza.io");

		return map;

	}
	// @RequestMapping("/statistic/common/{resourceId}")
	// public Map<String,Integer>
	// getCommonResourceStat(@PathVariable("resourceId") Integer resourceId) {
	// //fake data
	// //TODO replace with DAO request
	// Map<String,Integer> map = new HashMap<>();
	// map.put("Putin",450);
	// map.put("Medvedev",370);
	// map.put("Variable",resourceId);
	//
	//
	// return map;

	// }

	// Финальный вид для реализованного интрфейса
	@RequestMapping("/statistic/common/{siteId}")
	public Common getCommon(@PathVariable("siteId") Integer siteId) {
		return repository.get(siteId);
	}

	// @RequestMapping("/statistic/daily/{resourceId}/{personId}/{dateStart}/{dateEnd}")
	// public Map<LocalDate,Integer> getDailyStat(@PathVariable Integer
	// resourceId,@PathVariable Integer personId,
	// @PathVariable String dateStart,@PathVariable String dateEnd) {
	// Map<LocalDate,Integer> map = new HashMap<>();
	//
	// //String parsing example
	// DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd:MM:yyyy");
	// LocalDate dateTime = LocalDate.parse(dateStart, formatter);
	// Date.from(dateTime.atStartOfDay(ZoneId.systemDefault()).toInstant());
	//
	// map.put(dateTime,1000);
	// map.put(dateTime,1001);
	//
	// return map;
	// }

	// Финальный вид для реализованного интрфейса
	@RequestMapping("/statistic/daily/{siteId}/{personId}/{dateStart}/{dateEnd}")
	public Daily getDaily(@PathVariable Integer siteId, @PathVariable Integer personId, @PathVariable Long dateStart, @PathVariable Long dateEnd) {
		return repository.get(siteId, personId, dateStart, dateEnd);
	}
}
