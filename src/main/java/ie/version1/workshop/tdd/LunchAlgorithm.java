package ie.version1.workshop.tdd;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class LunchAlgorithm {

	private final List<Leader> leaders;
	private final List<Member> members;
	private final int treshold;
	
	public LunchAlgorithm(List<Leader> leaders, List<Member> members, int treshold){
		this.leaders = Collections.unmodifiableList(leaders);
		this.members = Collections.unmodifiableList(members);
		this.treshold = treshold;
	}
	
	public List<Lunch> getLunches() {
		Map<Member, Map<Member, Integer>> controlMap = newControlMap(members);
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
	
	private void populateTableMembers(Lunch lunch, List<Member> lunchMembers, Map<Member, Map<Member, Integer>> controlMap){
		Set<Member> membersAlreadyInLunch = new HashSet<>();
		int expectedTableSize = lunchMembers.size() / lunch.getTables().size();
		for(Entry<Leader,List<Member>> table:lunch.getTables().entrySet()){
			for(Member lunchMember:lunchMembers){
				if(!membersAlreadyInLunch.contains(lunchMember)){
					if(canSitOnTable(lunchMember, table, controlMap)){
						sitsOnTable(lunchMember, table, controlMap);
						membersAlreadyInLunch.add(lunchMember);
					}
				}
				if(table.getValue().size() >= expectedTableSize){
					break;
				}
			}
		}
	}
	
	private Map<Member, Map<Member, Integer>> newControlMap(List<Member> lunchMembers){
		Map<Member, Map<Member, Integer>> controlMap = new HashMap<>();
		for(Member member:lunchMembers){
			controlMap.put(member, new HashMap<Member, Integer>());
		}
		return controlMap;
	}
	
	private boolean canSitOnTable(Member member, Entry<Leader,List<Member>> table, Map<Member, Map<Member, Integer>> controlMap){
		
		// can't have lunch with same pal more than twice
		for(Member lunchPal:table.getValue()){
			if(controlMap.get(member).keySet().contains(lunchPal)
				&& controlMap.get(member).get(lunchPal) >= 2){
				return false;
			}
			
		}
		
		return true;
	}
	
	private void sitsOnTable(Member member, Entry<Leader,List<Member>> table, Map<Member, Map<Member, Integer>> controlMap){
		
		// adds pals to control
		for(Member lunchPal:table.getValue()){
			Integer count = controlMap.get(member).get(lunchPal);
			controlMap.get(member).put(lunchPal, count == null ? 1 : count + 1);
		}
		
		// adds member to table
		table.getValue().add(member);
		
	}

}
