package ie.version1.workshop.tdd;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class LunchAlgorithm {

	private final List<Leader> leaders;
	private final List<Member> members;
	
	public LunchAlgorithm(List<Leader> leaders, List<Member> members){
		this.leaders = Collections.unmodifiableList(leaders);
		this.members = Collections.unmodifiableList(members);
	}
	
	public List<Lunch> getLunches() {
		Map<Leader, Set<Member>> controlMap = newControlMap(leaders);
		List<Lunch> lunchList = new ArrayList<Lunch>();
		for(int i=0; i < leaders.size(); i++){
			Lunch lunch = new Lunch();
			populateTableLeaders(lunch, leaders);
			populateTableMembers(lunch, members, controlMap);
			lunchList.add(lunch);
		}
		return lunchList;
	}
	
	private void populateTableLeaders(Lunch lunch, List<Leader> lunchLeaders){
		for(Leader lunchLeader:lunchLeaders){
			lunch.getTables().put(lunchLeader, new ArrayList<Member>());
		}
	}
	
	private void populateTableMembers(Lunch lunch, List<Member> lunchMembers, Map<Leader, Set<Member>> controlMap){
		int expectedTableSize = lunchMembers.size() / lunch.getTables().size();
		for(Leader leader:lunch.getTables().keySet()){
			for(Member lunchMember:lunchMembers){
				if(!controlMap.get(leader).contains(lunchMember)){
					List<Member> table = lunch.getTables().get(leader);
					if(table.size() < expectedTableSize){
						table.add(lunchMember);
						controlMap.get(leader).add(lunchMember);
					} else {
						break;
					}
				}
			}
		}
	}
	
	private Map<Leader, Set<Member>> newControlMap(List<Leader> lunchLeaders){
		Map<Leader, Set<Member>> controlMap = new HashMap<>();
		for(Leader lunchLeader:lunchLeaders){
			controlMap.put(lunchLeader, new HashSet<Member>());
		}
		return controlMap;
	}

}
