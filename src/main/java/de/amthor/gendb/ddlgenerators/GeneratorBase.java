package de.amthor.gendb.ddlgenerators;

public class GeneratorBase {

	public enum Scope  {
		DATABASE ("DATABASE"),
		TABLE ("TABLE"),
		COLUMN ("COLUMN");
		
		public final String scope;
		
		private Scope(String scope) {
			this.scope = scope;
		}
		
		@Override 
		public String toString() { 
		    return this.scope; 
		}
	}
	
	public Scope scope = Scope.DATABASE;
	
	public String dbType = null;
	
	public String getDbType() {
		return this.dbType;
	}

}
