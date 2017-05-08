package Model;

public class Players {

	/*public String player;
	 * 
	 */
	public String name;
	
	public int num;
	
	/* 
	 *  Get player.
	 
	
	public String getPlayer(){
		return this.player;
	}
	
	/* 
	 *  Set player.
	 
	
	public void setPlayer(){
		this.player = player;
	}
	
	/* 
	 *  Get a player name.
	 */
	
	public String getName(){
		return this.name;
	}
	
	/* 
	 *  Set a name to a player.
	 */
	
	public void setName(String name){
		this.name = name;
	}
	
	public int getNum(){
		return this.num;
	}
	/*
	 *  Constructors 
	 */ 
	
	public Players() {}
	
	public Players (String xname, int xnum)
    {
        name = xname;
        num = xnum;
    }
	
	
}
