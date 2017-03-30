package ie.version1.workshop.tdd;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

public class LunchAlgorithm {

	private final List<Leader> leaders;
	private final List<Member> members;
	private final int treshold;
	private final int tableSize;
	private final List<List<Member>> possibleTables;
	
	private static final Logger log = Logger.getLogger(LunchAlgorithm.class);
	
	public LunchAlgorithm(List<Leader> leaders, List<Member> members, int treshold){
		log.debug(String.format("Initializing lunch algorithm with %s leaders and %s members..", leaders.size(), members.size()));
		this.leaders = Collections.unmodifiableList(leaders);
		this.members = Collections.unmodifiableList(members);
		this.treshold = treshold;
		this.tableSize = members.size() / leaders.size();
		this.possibleTables = getPossibleTables(members, tableSize);
		log.debug(String.format("Finished initializing lunch algorithm. %s possible tables.", possibleTables.size()));
	}
	
	public List<Lunch> getLunches() {
		log.debug("Getting lunches..");
		List<Lunch> lunchList = new ArrayList<Lunch>();	
		Map<Member, Map<Member, Integer>> controlMap = newControlMap(leaders, members);
		for(int i=0; i < leaders.size(); i++){
			Lunch lunch = getLunch(controlMap, possibleTables, leaders, members, treshold);
			lunchList.add(lunch);
			updateControlMap(controlMap, lunch);
		}
		if(log.isDebugEnabled()){
			StringBuilder sb = new  StringBuilder();
			sb.append("{\"lunchlist\":[");
			Iterator<Lunch> lunchesIterator = lunchList.iterator();
            while(lunchesIterator.hasNext()){
				sb.append(lunchesIterator.next());
				 if (lunchesIterator.hasNext()) {
	                sb.append(',');
	            }
			}
			sb.append("]}");
			log.debug(sb.toString());
		}
		return lunchList;
	}
	
	private Lunch getLunch(Map<Member, Map<Member, Integer>> controlMap, List<List<Member>> possibleTables, List<Leader> lunchLeaders, List<Member> lunchMembers, int treshold){
		log.debug("Getting lunch..");
		Lunch lunch = new Lunch();
		List<Member> membersAlreadyInLunch = new ArrayList<>();
		for(Leader lunchLeader:lunchLeaders){
			for(List<Member> possibleTable:possibleTables){
				if(test(controlMap, membersAlreadyInLunch, lunch, lunchLeader, possibleTable, possibleTable.size(), treshold)){
					log.debug("Found valid table.");
					membersAlreadyInLunch.addAll(possibleTable);
					List<Member> table = new ArrayList<>(possibleTable);
					table.add(lunchLeader);
					lunch.getTables().put(lunchLeader, table);
					break;
				}
			}
		}
		log.debug(String.format("Finished getting lunch"));
		return lunch;
	}
	
	private boolean test(Map<Member, Map<Member, Integer>> controlMap, List<Member> membersAlreadyInLunch, Lunch lunch, Leader lunchLeader, List<Member> tableMembers, int tableSize, int treshold){
		// checks that table size is correct
		if(tableMembers.size() != tableSize){		
			return false;
		}
		// checks that members are not in lunch already
		for(Member tableMember:tableMembers){
			if(membersAlreadyInLunch.contains(tableMember)){
				return false;
			}
		}
		// builds table to test treshold
		List<Member> tableUnderTest = new ArrayList<>(tableMembers);
		tableUnderTest.add(lunchLeader);
		return testTreshold(tableUnderTest, controlMap, treshold);
	}
	
	private boolean testTreshold(List<Member> tableMembers, Map<Member, Map<Member, Integer>> controlMap, int treshold){
		// checks that members are not having lunch with more that <treshold> previous pals
		for(Member member:tableMembers){
			 Map<Member, Integer> memberPreviousPals = controlMap.get(member);
			 int previousPalsCount = 0;
			 for(Member pal:tableMembers){
				 Integer palCount = memberPreviousPals.get(pal);
				 if(palCount != null && palCount > 0){
					 previousPalsCount++;
					 if(previousPalsCount > treshold){
						 return false;
					 }
				 }
			 }
		}
		return true;
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
	
	private void updateControlMap(Map<Member, Map<Member, Integer>> controlMap, Lunch lunch){
		for(Entry<Leader,List<Member>> table:lunch.getTables().entrySet()){
			for(Member member:table.getValue()){
				for(Member lunchPal:table.getValue()){
					if(!member.equals(lunchPal)){
						int count = controlMap.get(member).get(lunchPal) == null ? 
								0 : controlMap.get(member).get(lunchPal);
						controlMap.get(member).put(lunchPal, count + 1);
					}
				}
			}
		}
	}
	
	private List<List<Member>> getPossibleTables(List<Member> lunchMembers, int tableSize){
	    if (0 == tableSize) {
	        return Collections.singletonList(Collections.<Member>emptyList());
	    }
	    if (lunchMembers.isEmpty()) {
	        return Collections.emptyList();
	    }
	    List<List<Member>> possibleTables = new LinkedList<>();
	    Member member = lunchMembers.iterator().next();
	    List<Member> restOfTheMembers = new LinkedList<Member>(lunchMembers);
	    restOfTheMembers.remove(member);
	    List<List<Member>> possibleCombinations = getPossibleTables(restOfTheMembers, tableSize - 1);
	    for (List<Member> possibleCombination : possibleCombinations) {
	        List<Member> possibleTable = new LinkedList<Member>(possibleCombination);
	        possibleTable.add(0, member);
	        possibleTables.add(possibleTable);
	    }
	    possibleTables.addAll(getPossibleTables(restOfTheMembers, tableSize));
	    return possibleTables;
	}

}
