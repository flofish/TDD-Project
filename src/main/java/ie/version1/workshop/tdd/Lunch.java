package ie.version1.workshop.tdd;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class Lunch {

	private final Map<Leader, List<Member>> tables = new HashMap<>();
	
	public Map<Leader, List<Member>> getTables() {
		return tables;
	}
	
	@Override
	public String toString(){
		StringBuilder sb = new  StringBuilder();
		sb.append("{\"lunch\":[");
		Iterator<Entry<Leader, List<Member>>> tablesIterator = tables.entrySet().iterator();
		while(tablesIterator.hasNext()){
			Entry<Leader, List<Member>> table = tablesIterator.next();
			sb.append("{\"leader\":");
			sb.append("\"").append(table.getKey()).append("\"");
            sb.append(",\"members\":[");
            Iterator<Member> membersIterator = table.getValue().iterator();
            while(membersIterator.hasNext()){
            	sb.append("\"").append(membersIterator.next()).append("\"");
            	if (membersIterator.hasNext()) {
                    sb.append(',');
                }
            }
            sb.append("]}");
            if (tablesIterator.hasNext()) {
                sb.append(',');
            }
        }
		sb.append("]}");
		return sb.toString();
	}

}
