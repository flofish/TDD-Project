package ie.version1.workshop.tdd;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
	public void getLunches_hasFiveLeaders() {
		List<Lunch> lunchList = objectUnderTest.getLunches();

		assertEquals(5, lunchList.get(0).getTables().size());
	}

}
