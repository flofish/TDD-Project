package ie.version1.workshop.tdd;
import java.util.ArrayList;
import java.util.List;

public class LunchAlgorithm {

	public List<Lunch> getLunches() {
		List<Lunch> lunchList = new ArrayList<Lunch>();
		Lunch lunch = new Lunch();
		Leader leader = new Leader();
		lunchList.add(lunch);
		return lunchList;
	}

}
