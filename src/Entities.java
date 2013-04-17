
public enum Entities {
	AUTHOR("author"),
	PAPER("paper"),
	VENUE("venue"),
	PROJECT("project"),
	DEVELOPER("developer"),
	AUDIENCE("audience"),
	LANGUAJE("languaje"),
	LICENSE("license"),
	SYSTEM("system"),
	TOPIC("topic");
	
	
	private final String name;
	
	Entities(final String entityName){
		this.name = entityName;
	}
	
	@Override
	public final String toString(){
		return this.name;
	}
}
