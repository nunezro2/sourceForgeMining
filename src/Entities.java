
public enum Entities {
	AUTHOR("author"),
	PAPER("paper"),
	VENUE("venue"),
	PROJECT("project"),
	DEVELOPER("developer"),
	AUDIENCE("audience"),
	LANGUAGE("language"),
	LICENSE("license"),
	SO("so"),
	TOPIC("topic"),
	STATUS("status");
	
	
	private final String name;
	
	Entities(final String entityName){
		this.name = entityName;
	}
	
	@Override
	public final String toString(){
		return this.name;
	}
}
