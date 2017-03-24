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
		Map<Member, Map<Member, Integer>> controlMap = newControlMap(leaders, members);
		List<Lunch> lunchList = new ArrayList<Lunch>();
		for(int i=0; i < leaders.size(); i++){
			Lunch lunch = new Lunch();
			populateTableLeaders(lunch, leaders);
			populateTableMembers(lunch, members, treshold, controlMap);
			lunchList.add(lunch);
		}
		return lunchList;
	}
	
	private void populateTableLeaders(Lunch lunch, List<Leader> lunchLeaders){
		for(Leader lunchLeader:lunchLeaders){
			List<Member> table = new ArrayList<>();
			table.add(lunchLeader);
			lunch.getTables().put(lunchLeader, table);
		}
	}
	
	private void populateTableMembers(Lunch lunch, List<Member> lunchMembers, int treshold, Map<Member, Map<Member, Integer>> controlMap){
		Set<Member> membersAlreadyInLunch = new HashSet<>();
		int expectedTableSize = lunchMembers.size() / lunch.getTables().size() + 1;
		for(Entry<Leader,List<Member>> table:lunch.getTables().entrySet()){
			while(table.getValue().size() < expectedTableSize){
				int eligibility = -1;
				Member eligibleMember = null;
				for(Member lunchMember:lunchMembers){
					if(!membersAlreadyInLunch.contains(lunchMember)){
						int newEligibility = calculateEligibility(lunchMember, table, treshold, controlMap);
						if(newEligibility > eligibility){
							eligibility = newEligibility;
							eligibleMember = lunchMember;
						}
					}
				}
				if(eligibleMember != null){
					sitsOnTable(eligibleMember, table, controlMap);
					membersAlreadyInLunch.add(eligibleMember);
				} else {
					throw new IllegalStateException("No more eligible members left.");
				}
			}
		}
	}
	
	private Map<Member, Map<Member, Integer>> newControlMap(List<Leader> lunchLeaders, List<Member> lunchMembers){
		Map<Member, Map<Member, Integer>> controlMap = new HashMap<>();
		for(Leader leader:lunchLeaders){
			controlMap.put(leader, new HashMap<Member, Integer>());
		}
		for(Member member:lunchMembers){
			controlMap.put(member, new HashMap<Member, Integer>());
		}
		return controlMap;
	}

	private int calculateEligibility(Member member, Entry<Leader,List<Member>> table, int treshold, Map<Member, Map<Member, Integer>> controlMap){
		
		int eligibility = Integer.MAX_VALUE;
		
		List<Member> previousPals = getPreviousPals(member, table, 0, controlMap);
		
		// can't have lunch with more that x previous pals
		if(previousPals.size() > treshold){
			return -1;
		}
		
		for(Member previousPal:previousPals){
			// checks that if seated, previous pals won't exceed the x previous pals rule
			if(getPreviousPals(previousPal, table, 1, controlMap).size() > (treshold - 1)){
				return -1;
			}
		}
		
		return eligibility;
	}
	
	private List<Member> getPreviousPals(Member member, Entry<Leader, List<Member>> table, int treshold, Map<Member, Map<Member, Integer>> controlMap){
		List<Member> previousPals = new ArrayList<>();
		for(Member pal:table.getValue()){
			if(controlMap.get(member).get(pal) != null
					&& controlMap.get(member).get(pal) > treshold){	
				previousPals.add(pal);
			}
		}
		return previousPals;
	}
	
	private void sitsOnTable(Member member, Entry<Leader,List<Member>> table, Map<Member, Map<Member, Integer>> controlMap){
		
		// adds pals to control
		for(Member lunchPal:table.getValue()){
			if(!lunchPal.equals(member)){
				// adds 1 to member count
				Integer count = controlMap.get(member).get(lunchPal);
				controlMap.get(member).put(lunchPal, count == null ? 1 : count + 1);
				// adds 1 to pal count
				count = controlMap.get(lunchPal).get(member);
				controlMap.get(lunchPal).put(member, count == null ? 1 : count + 1);
			}
		}
		
		// adds member to table
		table.getValue().add(member);
		
	}

}
