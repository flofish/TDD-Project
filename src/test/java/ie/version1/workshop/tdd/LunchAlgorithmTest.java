package ie.version1.workshop.tdd;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

public class LunchAlgorithmTest {

	private List<Leader> leaders;
	private List<Member> members;
	private LunchAlgorithm objectUnderTest;

	@Before
	public void setup() {
		leaders = new ArrayList<>();
		leaders.add(new Leader());
		leaders.add(new Leader());
		leaders.add(new Leader());
		leaders.add(new Leader());
		members = new ArrayList<>();
		members.add(new Member());
		members.add(new Member());
		members.add(new Member());
		members.add(new Member());
		members.add(new Member());
		members.add(new Member());
		members.add(new Member());
		members.add(new Member());
		objectUnderTest = new LunchAlgorithm(leaders, members);
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
		int expectedTableSizeMin = members.size() / leaders.size();
		int expectedTableSizeMax = expectedTableSizeMin + (members.size() % leaders.size());
		assertFalse(lunchList.isEmpty());
		for(Lunch lunch:lunchList){
			Map<Leader, List<Member>> tables = lunch.getTables();
			assertFalse(tables.values().isEmpty());
			int membersInLunch = 0;
			for(List<Member> tableMembers:tables.values()){
				membersInLunch += tableMembers.size();
				assertTrue(expectedTableSizeMin <= tableMembers.size() && tableMembers.size() <= expectedTableSizeMax);
			}
			assertEquals(members.size(), membersInLunch);
		}
	}
	
	@Test
	public void getLunches_hasAsManyLunchesAsLeaders() {
		assertEquals(leaders.size(), objectUnderTest.getLunches().size());
	}
	
	@Test
	public void getLunches_eachMemberHasLunchWithLeaderOnlyOnce() {
		Map<Leader, Set<Member>> leaderMemberHadLunch = new HashMap<>();
		for(Leader leader:leaders){
			leaderMemberHadLunch.put(leader, new HashSet<Member>());
		}
		List<Lunch> lunchList = objectUnderTest.getLunches();
		for(Lunch lunch:lunchList){
			for(Leader leader:lunch.getTables().keySet()){
				for(Member member:lunch.getTables().get(leader)){
					assertFalse(leaderMemberHadLunch.get(leader).contains(member));
					leaderMemberHadLunch.get(leader).add(member);
				}
			}
		}
	}

}
