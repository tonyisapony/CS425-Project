
public class Member {
	int Currentid;
	String name;
	double balance;
	int points;
	MemType group1;
	MemType group2;
	
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
	public void setMemTypeG1(MemType t){
		this.group1=t;
	}
	public void setMemTypeG2(MemType t){
		this.group2=t;
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
	public MemType getMemTypeG1(){
		return this.group1;
	}
	public MemType getMeTypeG2(){
		return this.group2;
	}
}
