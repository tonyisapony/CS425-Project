
public class Member {
	int Currentid;
	String name;
	double balance;
	int points;
	MemType type;
	
	public Member(int id, String n) {
		this.Currentid=id;
		this.name=n;
	}
	
	public void setPoints(int p){
		points=p;
	}
	public void setBalance(double b){
		balance=b;
	}
	public void setMemType(MemType t){
		
	}
	
	public int getPoints(){
		return this.points;
	}
	public double getBalance(){
		return this.balance;
	}
	public int getID(){
		return this.Currentid;
	}

}
