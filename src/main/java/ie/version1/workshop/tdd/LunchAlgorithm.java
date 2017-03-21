package ie.version1.workshop.tdd;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LunchAlgorithm {

	private final List<Leader> leaders;
	private final List<Member> members;
	
	public LunchAlgorithm(List<Leader> leaders, List<Member> members){
		this.leaders = Collections.unmodifiableList(leaders);
		this.members = Collections.unmodifiableList(members);
	}
	
	public List<Lunch> getLunches() {
		List<Lunch> lunchList = new ArrayList<Lunch>();
		Lunch lunch = new Lunch();
		for(Leader leader:leaders){
			List<Member> members = new ArrayList<Member>();
			lunch.getTables().put(leader, members);
		}
		lunchList.add(lunch);
		return lunchList;
	}

}
