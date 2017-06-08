import java.sql.*;

/**
 * Responsible for communicating with the database.
 * 
 * @author Kamil Markiewicz, R00139423
 * @version 10.1
 */
public class MySQLDB {

	Connection connection;
	Statement statement;
	ResultSet result;
	
	public MySQLDB() {
		
	}
	
	public void connect(){
		try{
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/dentistry", "root", "");
			statement = connection.createStatement();
			}
			catch(Exception e){
				System.out.println("Could not connect to database.");
			}
	}
	
	public String getResult(int index, String column){
		String str = "";
		try {
			if(index != 0)
				result.absolute(index);
			str = result.getString(column);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return str;
	}
	
	public boolean isLast(){
		boolean last = true;
		try {
			last = result.isLast();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return last;
	}
	
	public boolean next(){
		boolean next = false;
		try {
			next = result.next();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return next;
	}
	
	public int getLastId(){
		String query = "SELECT LAST_INSERT_ID()";
		try{
			result = null;
			result = statement.executeQuery(query);
		}
		catch(Exception e){
			System.out.println("Could not get dentists");
		}
		next();
		return Integer.parseInt(getResult(0, "LAST_INSERT_ID()"));
	}
	
	public void getDentists(String name){
		String query = "SELECT * FROM Dentist";
		if(!(name.equals("")))
			query += " WHERE name = '" + name + "'";
		try{
			result = null;
			result = statement.executeQuery(query);
		}
		catch(Exception e){
			System.out.println("Could not get dentists");
		}
	}
	
	public void addDentist(String name, String address, String pass){
		String query = "INSERT INTO Dentist\n"
				+ "VALUES('" + name + "', '" + address + "', '" + pass + "')";
		try{
			statement.executeUpdate(query);
		}
		catch(Exception e){
			System.out.println("Could not add dentist");
		}
	}
	
	public void getProcedures(String name){
		String query = "SELECT * FROM Procedures";
		if(!(name.equals("")))
			query += " WHERE name = '" + name + "'";
		try{
			result = null;
			result = statement.executeQuery(query);
		}
		catch(Exception e){
			System.out.println("Could not get procedures");
		}
	}
	
	public void addProcedure(String name, double cost){
		String query = "INSERT INTO Procedures\n"
				+ "VALUES('" + name + "', " + cost + ")";
		try{
			statement.executeUpdate(query);
		}
		catch(Exception e){
			System.out.println("Could not add procedure");
		}
	}
	
	public void deleteProcedure(String name){
		String query = "DELETE FROM Procedures\n"
				+ "WHERE name = '" + name + "'";
		
		try{
			statement.executeUpdate(query);
		}
		catch(Exception e){
			System.out.println("Could not remove procedure");
		}
	}
	
	public void updateProcedure(String oldName, String name, double cost){
		String query = "UPDATE Procedures"
				+ " SET name = '" + name + "', cost = " + cost
				+ " WHERE name = '" + oldName + "'";
		try{
			statement.executeUpdate(query);
		}
		catch(Exception e){
			System.out.println("Could not update procedure");
			e.printStackTrace();
		}
	}
	
	public void getPatients(String id){
		String query = "SELECT * FROM Patient";
		if(!(id.equals("")))
			query += " WHERE id = '" + id + "'";
		try{
			result = null;
			result = statement.executeQuery(query);
		}
		catch(Exception e){
			System.out.println("Could not get patients");
		}
	}
	
	public void addPatient(String name, String address, String phone, String dentist){
		String query = "INSERT INTO Patient\n"
				+ "VALUES(default, '" + name + "', '" + address + "', '" + phone + "', '" + dentist + "')";
		try{
			statement.executeUpdate(query);
		}
		catch(Exception e){
			System.out.println("Could not add patient");
		}
	}
	
	public void deletePatient(String id){
		String query = "DELETE FROM Patient\n"
				+ "WHERE id = '" + id + "'";
		
		try{
			statement.executeUpdate(query);
		}
		catch(Exception e){
			System.out.println("Could not remove patient");
		}
	}
	
	public void getInvoices(String id, boolean mode){
		String query = "SELECT * FROM Invoice";
		if(!(id.equals("")))
			if(!mode)
				query += " WHERE patId = '" + id + "'";
			else
				query += " WHERE id = '" + id + "'";
		try{
			result = null;
			result = statement.executeQuery(query);
		}
		catch(Exception e){
			System.out.println("Could not get invoices");
		}
	}
	
	public void addInvoice(String id, double cost){
		String query = "INSERT INTO Invoice\n"
				+ "VALUES(default, " + id + ", " + cost + ", CURDATE())";
		try{
			statement.executeUpdate(query);
		}
		catch(Exception e){
			System.out.println("Could not add invoice");
		}
	}
	
	public void updateInvoice(String id, String outstanding){
		String query = "UPDATE Invoice"
				+ " SET outstanding = " + outstanding
				+ " WHERE id = " + id;
		try{
			statement.executeUpdate(query);
		}
		catch(Exception e){
			System.out.println("Could not update procedure");
			e.printStackTrace();
		}
	}
	
	public void deleteInvoice(String id){
		String query = "DELETE FROM Invoice\n"
				+ "WHERE id = '" + id + "'";
		
		try{
			statement.executeUpdate(query);
		}
		catch(Exception e){
			System.out.println("Could not remove invoice");
		}
	}
	
	public void getInvProcs(String id, boolean mode){
		String query = "SELECT * FROM InvProc";
		if(!(id.equals("")))
			if(!mode)
				query += " WHERE invId = '" + id + "'";
			else
				query += " WHERE id = '" + id + "'";
		try{
			result = null;
			result = statement.executeQuery(query);
		}
		catch(Exception e){
			System.out.println("Could not get procedures from invoice");
		}
	}
	
	public void addInvProc(String id, String name, double cost){
		String query = "INSERT INTO InvProc\n"
				+ "VALUES(default, " + id + ", '"  + name + "', " +  cost + ", CURDATE())";
		try{
			statement.executeUpdate(query);
		}
		catch(Exception e){
			System.out.println("Could not add procedure to invoice");
		}
	}
	
	public void deleteInvProc(String id){
		String query = "DELETE FROM InvProc\n"
				+ "WHERE id = '" + id + "'";
		try{
			statement.executeUpdate(query);
		}
		catch(Exception e){
			System.out.println("Could not remove procedure from invoice");
		}
	}
	
	public void getPayments(String id, boolean mode){
		String query = "SELECT * FROM Payment";
		if(!(id.equals("")))
			if(!mode)
				query += " WHERE invId = '" + id + "'";
			else
				query += " WHERE id = '" + id + "'";
		System.out.println(query);
		try{
			result = null;
			result = statement.executeQuery(query);
		}
		catch(Exception e){
			System.out.println("Could not get payments from invoice");
		}
	}
	
	public void addPayment(String id, double amount){
		String query = "INSERT INTO Payment\n"
				+ "VALUES(default, " + id + ", " +  amount + ", CURDATE())";
		try{
			statement.executeUpdate(query);
		}
		catch(Exception e){
			System.out.println("Could not add payment to invoice");
		}
	}
	
	public void deletePayment(String id){
		String query = "DELETE FROM Payment\n"
				+ "WHERE id = '" + id + "'";
		
		try{
			statement.executeUpdate(query);
		}
		catch(Exception e){
			System.out.println("Could not remove payment from invoice");
		}
	}
}