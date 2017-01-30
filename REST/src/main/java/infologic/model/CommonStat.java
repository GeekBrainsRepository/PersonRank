package infologic.model;

import java.util.Date;
import java.util.Map;

public class CommonStat {

	private Date date;
	private Map<String, Integer> result;

	public CommonStat(Date date, Map<String, Integer> result) {
		this.result = result;
		this.date = date;
	}

	public Map<String, Integer> getResult() {
		return result;
	}

	public Date getDate() {
		return date;
	}
	public void setDate(Date date){
		this.date=date;
	}

}