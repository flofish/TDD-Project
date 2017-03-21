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
		populateTableLeaders(lunch, leaders);
		populateTableMembers(lunch, members);
		lunchList.add(lunch);
		return lunchList;
	}
	
	private void populateTableLeaders(Lunch lunch, List<Leader> lunchLeaders){
		for(Leader lunchLeader:lunchLeaders){
			lunch.getTables().put(lunchLeader, new ArrayList<Member>());
		}
	}
	
	private void populateTableMembers(Lunch lunch, List<Member> lunchMembers){
		int expectedTableSize = lunchMembers.size() / lunch.getTables().size();
		for(Member lunchMember:lunchMembers){
			for(Leader leader:lunch.getTables().keySet()){
				List<Member> table = lunch.getTables().get(leader);
				if(table.size() < expectedTableSize){
					table.add(lunchMember);
					break;
				}
			}
		}
	}

}
