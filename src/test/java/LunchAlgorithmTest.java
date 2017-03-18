package test.java;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Test;

import main.java.Lunch;
import main.java.LunchAlgorithm;

public class LunchAlgorithmTest {

	LunchAlgorithm objectUnderTest = new LunchAlgorithm();

	@Test
	public void getLunches_listNotNull() {

		assertNotNull(objectUnderTest.getLunches());
	}

	@Test
	public void getLunches_listNotEmpty() {

		assertFalse(objectUnderTest.getLunches().isEmpty());
	}

	@Test
	public void getLunches_tableNotEmpty() {

		List<Lunch> lunchList = objectUnderTest.getLunches();

		assertFalse(lunchList.isEmpty());
		assertNotNull(lunchList.get(0));
	}

	@Test
	public void getLunches_hasFiveLeaders() {
		List<Lunch> lunchList = objectUnderTest.getLunches();

		assertEquals(5, lunchList.get(0).getTables().size());
	}

	/*
	 * input list of members and lieutenants output lists
	 * 
	 * asssertions:
	 */

}
