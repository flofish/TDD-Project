package ie.version1.workshop.tdd;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.junit.Before;
import org.junit.Test;

public class LunchAlgorithmTest {

	private List<Leader> leaders;
	private List<Member> members;
	private int treshold;
	private LunchAlgorithm objectUnderTest;

	@Before
	public void setup() {
		leaders = new ArrayList<>();
		for(int i = 0; i < 5; i++){
			leaders.add(new Leader());
		}
		members = new ArrayList<>();
		for(int i = 0; i < 35; i++){
			members.add(new Member());
		}
		treshold = 2;
		objectUnderTest = new LunchAlgorithm(leaders, members, treshold);
	}
	
	@Test
	public void getLunches_listNotNull() {

		assertNotNull(objectUnderTest.getLunches());
	}

	@Test
	public void getLunches_listNotEmpty() {

		assertFalse(objectUnderTest.getLunches().isEmpty());
	}

	@Test
	public void getLunches_lunchNotNull() {
		List<Lunch> lunchList = objectUnderTest.getLunches();
		assertFalse(lunchList.isEmpty());
		for(Lunch lunch:lunchList){
			assertNotNull(lunch);
		}
	}
	
	@Test
	public void getLunches_tableNotNull() {
		List<Lunch> lunchList = objectUnderTest.getLunches();
		assertFalse(lunchList.isEmpty());
		for(Lunch lunch:lunchList){
			assertNotNull(lunch.getTables());
		}
	}
	
	@Test
	public void getLunches_tableNotEmpty() {
		List<Lunch> lunchList = objectUnderTest.getLunches();
		assertFalse(lunchList.isEmpty());
		for(Lunch lunch:lunchList){
			assertFalse(lunch.getTables().isEmpty());
		}
	}

	@Test
	public void getLunches_hasAsManyTablesAsLeaders() {
		List<Lunch> lunchList = objectUnderTest.getLunches();
		assertFalse(lunchList.isEmpty());
		for(Lunch lunch:lunchList){
			assertEquals(leaders.size(), lunch.getTables().size());
		}
	}
	
	@Test
	public void getLunches_tableMembersAreNotNull() {
		List<Lunch> lunchList = objectUnderTest.getLunches();
		assertFalse(lunchList.isEmpty());
		for(Lunch lunch:lunchList){
			Map<Leader, List<Member>> tables = lunch.getTables();
			assertFalse(tables.values().isEmpty());
			for(List<Member> members:tables.values()){
				assertNotNull(members);
			}
		}
	}
	
	@Test
	public void getLunches_tableMembersAreEquallyDistributed() {
		List<Lunch> lunchList = objectUnderTest.getLunches();
		int expectedTableSize = members.size() / leaders.size() + 1;
		assertFalse(lunchList.isEmpty());
		for(Lunch lunch:lunchList){
			Map<Leader, List<Member>> tables = lunch.getTables();
			assertFalse(tables.values().isEmpty());
			int membersInLunch = 0;
			for(List<Member> tableMembers:tables.values()){
				membersInLunch += tableMembers.size();
				assertEquals(expectedTableSize, tableMembers.size());
			}
			assertEquals(members.size() + leaders.size(), membersInLunch);
		}
	}
	
	@Test
	public void getLunches_hasAsManyLunchesAsLeaders() {
		assertEquals(leaders.size(), objectUnderTest.getLunches().size());
	}
	
	@Test
	public void getLunches_eachMemberHasLunchWithTwoPreviousPalsMax() {
		Map<Member, Map<Member, Integer>> controlMap = new HashMap<>();
		for(Leader leader:leaders){
			controlMap.put(leader, new HashMap<Member, Integer>());
		}
		for(Member member:members){
			controlMap.put(member, new HashMap<Member, Integer>());
		}
		List<Lunch> lunchList = objectUnderTest.getLunches();
		for(Lunch lunch:lunchList){
			for(Entry<Leader,List<Member>> table:lunch.getTables().entrySet()){
				for(Member member:table.getValue()){
					int countPreviousPals = 0;
					List<Member> previousPals = new ArrayList<>();
					for(Member lunchPal:table.getValue()){
						if(!member.equals(lunchPal)){
							int count = controlMap.get(member).get(lunchPal) == null ? 
									0 : controlMap.get(member).get(lunchPal);
							if(count > 0){
								countPreviousPals++;
								previousPals.add(lunchPal);
							}
							controlMap.get(member).put(lunchPal, count + 1);
						}
					}
					assertFalse(countPreviousPals > treshold);
				}
			}
		}
	}

}
