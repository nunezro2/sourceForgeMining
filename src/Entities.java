
public enum Entities {
	AUTHOR("author"),
	PAPER("paper"),
	VENUE("venue");
	
	private final String name;
	
	Entities(final String entityName){
		this.name = entityName;
	}
	
	@Override
	public final String toString(){
		return this.name;
	}
}
