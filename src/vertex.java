import java.util.*;

public class vertex {

	private String type;
	private String name;
	private long id;
	private Map<String, String> attributes;
	
	public vertex(String type, long id ) {
		
		this.type = type;
		this.name = "";
		this.id = id;
		this.attributes = new HashMap<String, String>();
	
	}
	
	public vertex(String type, String name ) {
		
		this.type = type;
		this.name = name;
		this.id = Long.MAX_VALUE;
		this.attributes = new HashMap<String, String>();
	
	}
	
	public String getType(){
		return this.type;
	}
	
	public void setType(String t){
		this.type = t;
	}
	
	public String getName(){
		return this.name;
	}
	
	public long getId(){
		return this.id;
	}
	
	public void setId(long i){
		this.id = i;
	}
	
	public Map<String, String> getAttributes(){
		return this.attributes;
	}
	
}
