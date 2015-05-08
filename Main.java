
import java.sql.*;

import oracle.jdbc.pool.OracleDataSource;

import java.util.Scanner;

public class Main {
	
	static final String host="jdbc:oracle:thin:@fourier.cs.iit.edu:1521:orcl";
	static final String uName="apatlan1";
	static final String uPass="Noob1995";
	static Member mem=null;
	static boolean admin=false;
			
	public static void main(String[] args) {
		Connection con=null;
		String sql=null;
		Scanner scan=new Scanner(System.in);			
		ResultSet rs=null;
		Statement stmt=null;
		try{	
			
			OracleDataSource ds=new OracleDataSource();
			ds.setURL(host);
			con=ds.getConnection(uName,uPass);
			System.out.println("Connected");
			stmt=con.createStatement();

		    System.out.println("1:Register 2:Login");
		    int in=scan.nextInt();
		    if(in==1){
		    	sql="SELECT MAX(id) AS LastID FROM Membership";
		    	rs=stmt.executeQuery(sql);
		    	rs.next();
		    	int currid=rs.getInt("LastID");
		    	currid++;
		    	System.out.println("Enter new user name:");
		    	String usrname=scan.next();
		    	System.out.println("Enter new password:");
		    	String passwrd=scan.next();
		    	System.out.println("Enter email address");
		    	String email= scan.next();
		    	sql="INSERT INTO Membership (id,name,password,email) VALUES ("+currid+",'"+usrname+"','"+passwrd+"','"+email+"')";
		    	stmt.executeUpdate(sql);
		    	System.out.println("Successfully registered");
		    	mem=new Member(currid,usrname);
		    	
		    }
		    else if(in==2){
		    	boolean logged=false;
		    	System.out.println("Enter user name:");
		    	String usrname=scan.next();
		    	System.out.println("Enter password:");
		    	String passwrd=scan.next();
		    	sql="SELECT id,name,password FROM Membership";
		    	rs=stmt.executeQuery(sql);
		    	if(usrname.equals("admin")&&passwrd.equals("admin")){
		    		System.out.println("You have logged in as an admin");
		    		logged=true;
		    		admin=true;
		    	}
		    	else
		    	while(rs.next()){
		    		String nm=rs.getString("name");
		    		String n[]=nm.split(" ",2);
		    		String ps=rs.getString("password");
		    		String p[]=ps.split(" ",2);
		    		if(n[0].equals(usrname)&&p[0].equals(passwrd)){
		    			System.out.println("You have successfully logged in");
		    			int currid=rs.getInt("id");
		    			mem=new Member(currid,usrname);
		    			logged=true;
		    			break;
		    		}
		    	}
		    	if(logged==false){
		    		System.out.println("Wrong credentials");
			    	System.exit(0);
		    	}
		    }
		    else{
		    	System.out.println("Wrong command");
		    	System.exit(0);
		    }
		    
		    if(admin){
		    	System.out.println("Please chose what would you like to do. \n1:Add Leader 2:Remove Leader 3:View Member list "
		    			+ "4:Look at a Group list 5:Delete Member 6: Logout");
		    	int adin=scan.nextInt();
	    		ModifyMembers mm=new ModifyMembers();
		    	if(adin==1){
		    		System.out.println("Enter which group to add the leader to. 1:Group1 2:Group2");
		    		int g=scan.nextInt();
		    		ModifyLeaders ml=new ModifyLeaders(g);
		    		System.out.println("The current leaders are: ");
		    		ml.ShowLeaders();
		    		System.out.println("\nEnter the ID of the new leader from the following list: ");
		    		mm.displayGroupMembers(g);
		    		int add=scan.nextInt();
		    		ml.AddLeader(add, g);
		    	}
		    	else if(adin==2){
		    		System.out.println("Enter which group to remove the leader from. 1:Group1 2:Group2");
		    		int g=scan.nextInt();
		    		ModifyLeaders ml=new ModifyLeaders(g);
		    		System.out.println("The current leaders are: ");
		    		ml.ShowLeaders();
		    		System.out.println("\nEnter the ID of the leader you want to remove: ");
		    		int del=scan.nextInt();
		    		ml.AddLeader(del, g);
		    	}
		    	else if(adin==3){
		    		mm.displayAllMembers();
		    	}
		    	else if(adin==4){
		    		System.out.println("Enter which group to view. 1:Group1 2:Group2");
		    		int g=scan.nextInt();
		    		mm.displayGroupMembers(g);
		    	}
		    	else if(adin==5){
		    		System.out.println("Enter the id of the member you want to delete: ");
		    		mm.displayAllMembers();
		    		int del=scan.nextInt();
		    		mm.deleteMember(del);
		    	}
		    	System.out.println("Goodbye!");
		    	System.exit(0);
		    }
		    
			System.out.println("You are now logged in please choose what interest group you would like to access. "
					+ "\n1:Restaurants 2:Laptops 3: Delete Account 4: Logout");
				
	    	int in1=scan.nextInt();
	    	if (in1==1)
	    	{
	    		Restaurants();
	    	}
	    	else if(in1==2)
	    	{
	    		Laptops();
	    	}
	    	else if(in1==3){
	    		sql="DELETE FROM MEMBERSHIP WHERE id="+mem.getID();
	    		stmt.executeUpdate(sql);
	    		System.out.println("Successfully deleted your account, goodbye!");
	    		System.exit(0);
	    	}
	    	else
	    		System.out.println("Logging out...");
	    		System.exit(0);
		}
		catch(SQLException err){
			System.out.println(err.getMessage());
		}finally{
		      //finally block used to close resources
		      try{
		            stmt.close();
		      }catch(SQLException se){
		      }// do nothing
		      scan.close();
		      try{
		    	  rs.close();
		 	  }catch(SQLException se){
		 	  }// do nothing
		      }
		      try{
		          con.close();
		      }catch(SQLException se){
		         se.printStackTrace();
		      }//end finally try
	}
	
	
	//TONY WORKING ON THESE METHODS... RESTAURANT AND LAPTOP COMMENTS
	public static void Restaurants()
	{
		System.out.println("You have now been signed into the Restaurant Interest Group.");
		Connection con=null;
		String sql=null;
		Scanner scan=new Scanner(System.in);
		ResultSet rs3=null;
		ResultSet rs2=null;
		ResultSet rs1=null;
		ResultSet rs=null;
		Statement stmt=null;
		boolean newmem=true;
		boolean leader=false;
			try{	
				OracleDataSource ds=new OracleDataSource();
				ds.setURL(host);
				con=ds.getConnection(uName,uPass);
				stmt=con.createStatement();
				sql="SELECT * FROM GROUP1MEMBERS";
				rs2=stmt.executeQuery(sql);
				while(rs2.next()){
					int g1mem=rs2.getInt("memid");
					if(g1mem==mem.getID()){
						newmem=false;
					}
				}
				if(newmem){
					sql="INSERT INTO Group1Members (memid) VALUES("+mem.getID()+")";
					stmt.executeUpdate(sql);
					System.out.println("Welcome newcomer!");
				}
				else
					System.out.println("Welcome back!");
				sql="SELECT memID FROM LEADER WHERE GroupNum=1";
				rs1=stmt.executeQuery(sql);
				while(rs1.next()){
					int id=rs1.getInt("memID");

					if(id==mem.getID()){
						System.out.println("\nYou are a leader for this group\n");
						mem.setMemTypeG1(MemType.LEADER);
						leader=true;
					}
					else
						mem.setMemTypeG1(MemType.MEMBER);
				}
				System.out.println("The top 5 point holders are:");
				sql="SELECT name, mempoints FROM (SELECT name, mempoints FROM MEMBERSHIP ORDER BY MEMPOINTS DESC) "
						+ "WHERE ROWNUM<=5 ORDER BY MEMPOINTS DESC";
				rs3=stmt.executeQuery(sql);
				int i=1;
				while(rs3.next()&&i>0){
					String n=rs3.getString("name");
					int points=rs3.getInt("mempoints");
					System.out.println(i+": "+n+" with "+points+" points");
					i++;
				}
				System.out.println("\nNow listing the 20 most recent restaurants: \n");
				ListRestaurants lr=new ListRestaurants(20);
				lr.List();
				if(leader){
					System.out.println("\nWhat would you like to do? \n1:Post a new restaurant 2:Review a restaurant 3:List all the restaurants 4:Change contribution point values");
				}
				else
					System.out.println("\nWhat would you like to do? \n1:Post a new restaurant 2:Review a restaurant 3:List all the restaurants");
		    	int in1=scan.nextInt();
		    	if(in1==1){
 			    	sql="SELECT MAX(id) AS LastID FROM Group1";
 			    	rs=stmt.executeQuery(sql);
 			    	rs.next();
 			    	int currid=rs.getInt("LastID");
 			    	currid++;
 			    	System.out.println("Please enter the name of the restaurtant: ");
 			    	String name=scan.next();
 			    	sql="INSERT INTO GROUP1 (id,restname) VALUES ("+currid+",'"+name+"')";
 			    	stmt.executeUpdate(sql);
 			    	System.out.println("Successfully added a new restaurant");
			    	Points p=new Points(1);
			    	p.loadContributionValues();
			    	p.UpdateMemPoints(mem.getID(), p.getRest());
					System.out.println("Contribution points were added");
					System.out.println("Your current points are:"+p.getCurrPoints());
					System.out.println("Logging out..");
 					System.exit(0);
 				}
				if(in1==2){
					System.out.println("Please select restaurant id: ");
					int restid=scan.nextInt();
					System.out.println("Outputting the current reviews...");
					sql="SELECT review FROM REVIEW WHERE RestID="+restid;
					rs=stmt.executeQuery(sql);
					while(rs.next()){
						String rev=rs.getString("review");
						System.out.println("Review: "+rev);
					}
					System.out.println("\nWhat would you like to do? 1:Add a review 2:Rate the restaurant");
					int resin=scan.nextInt();
					if(resin==1){
						System.out.println("Please enter a one word review: ");
						String rev=scan.next();
						sql="INSERT INTO REVIEW (RestID,Review) VALUES ("+restid+",'"+rev+"')";
						stmt.executeUpdate(sql);
						System.out.println("Successfully posted review");
				    	Points p=new Points(1);
				    	p.loadContributionValues();
				    	p.UpdateMemPoints(mem.getID(), p.getRev());
						System.out.println("Contribution points were added");
						System.out.println("Your current points are:"+p.getCurrPoints());
						System.out.println("Logging out..");
						System.exit(0);
					}
					if(resin==2){
						System.out.println("Would you like to rate it up or down? Enter 1 to rate up and -1 to rate down");
						int rate=scan.nextInt();
						sql="SELECT RATING FROM GROUP1 WHERE id="+restid;
						rs=stmt.executeQuery(sql);
						rs.next();
						int rating=rs.getInt("rating");
						rating+=rate;
						sql="UPDATE Group1 SET Rating="+rating+" WHERE id="+restid;
						stmt.executeUpdate(sql);
						System.out.println("You have successfully rated the restaurant");
				    	Points p=new Points(1);
				    	p.loadContributionValues();
				    	p.UpdateMemPoints(mem.getID(), p.getRate());
						System.out.println("Contribution points were added");
						System.out.println("Your current points are:"+p.getCurrPoints());
						System.out.println("Logging out..");
						System.exit(0);
					}
				}
				if(in1==3){
					ListRestaurants lr1=new ListRestaurants();
					lr1.List();
				}
				if((in1==4)&&leader){
					Points p=new Points(1);
					p.showContributionValue();
					System.out.println("Chose which value to change. \n1:Adding Restaurant 2:Rating 3:Review 4:Logout");
					int in2=scan.nextInt();
					if(in2==1){
						System.out.println("Enter new value: ");
						int val=scan.nextInt();
						sql="UPDATE Group1Points SET NUMPOINTS="+val+" WHERE Contribution='addRest'";
						System.out.println("");
						stmt.executeUpdate(sql);
						System.out.println("Successfully updated the Add Restaurant contribution points");
					}
					else if(in2==2){
						System.out.println("Enter new value: ");
						int val=scan.nextInt();
						sql="UPDATE Group1Points SET NUMPOINTS="+val+" WHERE Contribution='rate'";
						stmt.executeUpdate(sql);
						System.out.println("Successfully updated the Rating contribution points");
					}
					else if(in2==3){
						System.out.println("Enter new value: ");
						int val=scan.nextInt();
						sql="UPDATE Group1Points SET NUMPOINTS="+val+" WHERE Contribution='review'";
						stmt.executeUpdate(sql);
						System.out.println("Successfully updated the Review contribution points");
					}
					System.out.println("Logging out..");
				}
				else
					System.out.println("Logging out..");
						
			}
			catch(SQLException err){
				System.out.println(err.getMessage());
			}finally{
			      //finally block used to close resources
			      try{
			            stmt.close();
			      }catch(SQLException se){
			      }// do nothing
			      scan.close();
			      try{
			    	  rs1.close();
			 	  }catch(SQLException se){
			 	  }// do nothing
			      }
			      try{
			          con.close();
			      }catch(SQLException se){
			         se.printStackTrace();
			      }//end finally try
	}
	
	
	public static void Laptops()
	{
		System.out.println("You have now been signed into the Laptops Interest Group.");
		Connection con=null;
		String sql=null;
		Scanner scan=new Scanner(System.in);			
		ResultSet rs=null;
		ResultSet rs1=null;
		ResultSet rs2=null;
		Statement stmt=null;
		boolean newmem=true;
		boolean leader=false;
			try{	
				OracleDataSource ds=new OracleDataSource();
				ds.setURL(host);
				con=ds.getConnection(uName,uPass);
				stmt=con.createStatement();
				sql="SELECT * FROM GROUP2MEMBERS";
				rs2=stmt.executeQuery(sql);
				while(rs2.next()){
					int g1mem=rs2.getInt("memid");
					if(g1mem==mem.getID()){
						newmem=false;
					}
				}
				if(newmem){
					sql="INSERT INTO Group2Members (memid) VALUES("+mem.getID()+")";
					stmt.executeUpdate(sql);
					System.out.println("Welcome newcomer!");
				}
				else
					System.out.println("Welcome back!");
				sql="SELECT memID FROM LEADER WHERE GroupNum=2";
				rs1=stmt.executeQuery(sql);
				while(rs1.next()){
					int id=rs1.getInt("memID");

					if(id==mem.getID()){
						System.out.println("\nYou are a leader for this group\n");
						mem.setMemTypeG1(MemType.LEADER);
						leader=true;
					}
					else
						mem.setMemTypeG1(MemType.MEMBER);
				
	    	}
				//SHOULD BE ABLE TO RATE/POST COMMENTS, NARROW COMMENTS BY SPECIFIC BRAND,REVIEW,HELP,SELL,BUY,TRADE. ETC. 
				//ALSO BE ABLE TO BUY,SELL,TRADE.
				//SHOULD HAVE ENOUGH MONEY IN ACCOUNT IN ORDER TO BUY
				//MONEY IS ADDED WHEN SELLING ITEM, TRANSFER MONEY FROM BANK AND CREDIT CARD TO WEB ACCOUNT
	    		System.out.println("Now listing the 20 most recently commented on Laptops: \n");
				ListLaptops ll=new ListLaptops(20);
				ll.List();
				if(leader){
					System.out.println("What would you like to do? \n1:Post a new laptop 2:Review a laptop 3:Change contribution point values");
					
				}
				else
					System.out.println("What would you like to do? \n1:Post a new laptop 2:Review a laptop");
				int in1=scan.nextInt();
				if(in1==1)
				{
					sql="SELECT MAX(id) AS LastID FROM Group2";
 			    	rs=stmt.executeQuery(sql);
 			    	rs.next();
 			    	int currid=rs.getInt("LastID");
 			    	currid++;
 			    	System.out.println("Please enter the name of the Laptop");
 			    	String name=scan.next();
 			    	System.out.println("Please enter the brand of the Laptop");
 			    	String brand=scan.next();
 			    	brand.toUpperCase();
 			    	sql="INSERT INTO GROUP2 (id,laptopname,brand) VALUES ("+currid+",'"+name+"', '"+brand+"')";
 			    	stmt.executeUpdate(sql);
 			    	System.out.println("Successfully added the new Laptop!");
 			    	Points p=new Points(1);
			    	p.loadContributionValues();
			    	p.UpdateMemPoints(mem.getID(), p.getRest());
					System.out.println("Contribution points were added");
					System.out.println("Your current points are:"+p.getCurrPoints());
					System.out.println("Logging out..");
 					System.exit(0);
				}
				if(in1==2)
				{
					System.out.println("You can now search all the comments for a specific brand or you can search for specific types of comments on all computers.\n 1:Brand   2:Type of Comment");
					int in2=scan.nextInt();
					if(in2==1)
					{
						System.out.println("Type in the brand of computer you want to search by:");
						String brand1=scan.next();
						System.out.println("Collecting data");
						sql="SELECT id,laptopname FROM GROUP2 WHERE brand ='"+brand1+"'";
						rs=stmt.executeQuery(sql);
						while(rs.next()){
							int num=rs.getInt("id");
							String name=rs.getString("laptopname");
							System.out.println("ID: "+num+ "Laptop name: "+name);
						}
						
						System.out.println("Please select laptop id: ");
						int id=scan.nextInt();
						System.out.println("Outputting the current comments...");
						sql="SELECT * FROM LAPTOPCOMMENTS WHERE lapid="+id;
						rs=stmt.executeQuery(sql);
						while(rs.next()){
							int comment=rs.getInt("id");
							String rev=rs.getString("Comments");
							String type=rs.getString("Commenttype");
							System.out.println("ID: " +comment+"Comment: "+rev+"  The person who posted this comment is looking to: " +type+ " this laptop.");	
						}
						System.out.println("Please select comment id: ");
						int comid=scan.nextInt();
						sql="SELECT * FROM LAPTOPCOMMENTS WHERE ID="+comid;
						rs=stmt.executeQuery(sql);
						rs.next();
							String com=rs.getString("Comments");
							String type1=rs.getString("Commenttype");
							System.out.println("Comment: "+com+"  Do you want to "+type1+" this laptop?");
						
						System.out.println("1:Yes   2:No");
						int decision=scan.nextInt();
						if(decision==1&&type1=="Buy")
						{
							System.out.println("Redirecting you to the sale screen");
							sell(comid);
						}
						else if(decision==1&&type1=="Sell")
						{
							System.out.println("Redirecting you to the purchase screen");
							buy(comid);
						}
						else if(decision==1&&type1=="Trade")
						{
							System.out.println("Redirecting you to the trade screen");
							trade();
						}
						else if(decision==2)
						{
							Points p=new Points(1);
					    	p.loadContributionValues();
					    	p.UpdateMemPoints(mem.getID(), p.getRest());
							System.out.println("Contribution points were added");
							System.out.println("Your current points are:"+p.getCurrPoints());
							System.out.println("Logging out..");
		 					System.exit(0);
						}
						
					}
					else if(in2==2)
					{
						System.out.println("You can now search specifically for a certain type of comment.");
						System.out.println("1:Review   2:Buy   3:Sell   4:Trade   5:Help   6:Logout");
						int comtype=scan.nextInt();
						if(comtype==1)
						{
							//REVIEW
						}
						else if(comtype==2)
						{
							//BUY
						}
						else if(comtype==3)
						{
							//SELL
						}
						else if(comtype==4)
						{
							//TRADE
						}
						else if(comtype==5)
						{
							//HELP
						}
						else
						{
							System.out.println("Logging out..");
		 					System.exit(0);
						}
					}
					else
					{
						System.out.println("Invalid option\n Logging out...");
						System.exit(0);
					}
				}
				if(in1==3&&leader)
				{
					
				}
				
			}
	    	
			catch(SQLException err){
				System.out.println(err.getMessage());
			}finally{
			      //finally block used to close resources
			      try{
			            stmt.close();
			      }catch(SQLException se){
			      }// do nothing
			      scan.close();
			      try{
			    	 rs2.close();
			 	  }catch(SQLException se){
			 	  }// do nothing
			      }
			      try{
			          con.close();
			      }catch(SQLException se){
			         se.printStackTrace();
			      }//end finally try
		}
	
	public static void buy(int a)
	{
		System.out.println("You are now in the purchase menu all transactions done through here are final.");
		Connection con=null;
		String sql=null;			
		ResultSet rs=null;
		Statement stmt=null;
			try{	
				OracleDataSource ds=new OracleDataSource();
				ds.setURL(host);
				con=ds.getConnection(uName,uPass);
				stmt=con.createStatement();
				sql="SELECT * FROM LAPTOPCOMMENTS WHERE ID ="+a;
				rs=stmt.executeQuery(sql);
				rs.next();
				String com=rs.getString("Comment");
				String type1=rs.getString("Commenttype");
				System.out.println("Comment: "+com+"  Do you want to "+type1+" this laptop?");
			
			    System.out.println("1:Yes   2:No");
			}
			catch(SQLException err){
				System.out.println(err.getMessage());
			}finally{
			      //finally block used to close resources
			      try{
			            stmt.close();
			      }catch(SQLException se){
			      }// do nothing
			      try{
			    	  rs.close();
			 	  }catch(SQLException se){
			 	  }// do nothing
			      
			      try{
			          con.close();
			      }catch(SQLException se){
			         se.printStackTrace();
			      }//end finally try	
			}
	}
	
	public static void sell(int a)
	{
		System.out.println("You are now in the selling screen. Remember: All sales are final!");
	}
	
	public static void trade()
	{
		System.out.println("Succesfully transferred laptops with owner");
		Points p=new Points(1);
    	p.loadContributionValues();
    	p.UpdateMemPoints(mem.getID(), p.getRest());
		System.out.println("Contribution points were added");
		System.out.println("Your current points are:"+p.getCurrPoints());
		System.out.println("Logging out..");
			System.exit(0);
	}
}

