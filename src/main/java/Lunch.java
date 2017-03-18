package main.java;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Lunch {

	private final Map<Leader, List<Member>> tables = new HashMap<>();

	public Map<Leader, List<Member>> getTables() {
		return tables;
	}

}
