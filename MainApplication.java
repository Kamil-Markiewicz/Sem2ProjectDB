import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;

import javafx.application.Application;

/**
 * Responsible for main behavior/functionality.
 * 
 * @author Kamil Markiewicz, R00139423
 * @version 10.1
 */
public class MainApplication {

	private static GUI gui;		//Allows for communication back to the GUI
	private static MySQLDB db;
	private static String dentist;

	/**
	 * Initializes ArrayLists, loads data from files and launches GUI.
	 * 
	 * @param args	Unused
	 */
	public static void main(String[] args){
		dentist = "";	
		
		db = new MySQLDB();
		db.connect();

		//Launch GUI
		Application.launch(GUI.class, args);
	}

	/**
	 * Establishes reference to GUI.
	 * 
	 * @param guiRef Reference to GUI
	 */
	public static void setGUI(GUI guiRef){
		gui = guiRef;
	}

	/**
	 * Checks if there is more rows in database result set.
	 * 
	 * @return	If there is another row in database result set.
	 */
	
	public static boolean next(){
		return db.next();
	}
	/**
	 * Check if login is valid and logs errors or success.
	 * 
	 * @param name	Name of dentist
	 * @param pass	Password of dentist
	 * @return		Validity of login
	 */
	
	public static boolean activeDentist(String dent){
		return dent.equals(dentist);
	}
	
	public static int getLastId(){
		return db.getLastId();
	}
	
	public static boolean checkLogIn(String name, String pass){
		boolean valid = false;	//Flag for validity of login
		boolean found = false;	//Flag for found dentist

		//Set variables
		String str = "";
		
		//Check if the dentist name is registered
		db.getDentists(name);
		if(db.next())
			found = true;

		//If name is registered check if password matches
		if(found){
			if(db.getResult(0, "password").equals(pass))
				valid = true;
			if(valid){
				dentist = name;
				str += "Logged in as " + name + ".";
			}
		}

		//If not registered or wrong password, notify user
		if(!found)
			str += "This dentist is not registered.";
		else if(found){
			if(!valid)
				str += "Incorrect Password.";
		}

		//Update log for GUI
		gui.setLog(str);
		return valid;
	}

	/**
	 * Checks validity of dentist registry.
	 * If valid, registers dentist to the system.
	 * 
	 * @param name		Name of dentist
	 * @param address	Address of dentist
	 * @param pass		Password of dentist
	 * @return			Validity of registry
	 */
	public static boolean checkReg(String name, String address, String pass){
		boolean valid = true;	//Flag for registry validity
		String str = "";	//Prepare log String

		//Check if dentist already exists
		db.getDentists(name);
		if(db.next()){
			valid = false;
			str += "This Dentist already exists. ";
		}

		//Check if user has not left any fields blank
		if(valid){
			if(name.length() < 1){
				valid = false;
				str += "Name is mandatory. ";
			}
			if(address.length() < 1){
				valid = false;
				str += "Address is mandatory. ";
			}
			if(pass.length() < 1){
				valid = false;
				str += "Password is mandatory. ";
			}
		}

		//If valid, register dentist
		if(valid)
			db.addDentist(name, address, pass);

		//Update log for GUI
		gui.setLog(str);
		return valid;
	}

	/**
	 * Checks procedure validity.
	 * 
	 * @param name	Name of procedure
	 * @param cost	Cost of procedure
	 * @return		Validity of procedure
	 */
	public static boolean checkProc(String name, String cost){
		boolean valid = true;	//Flag for procedure validity
		String str = "";	//Prepare log String

		//Check if procedure already exists
		db.getProcedures(name);
		if(db.next()){
			valid = false;
			str += "This procedure already exists. ";
		}

		//Check if user has left any fields blank
		if(valid){
			if(name.length() < 1){
				valid = false;
				str += "Name is mandatory. ";
			}
			if(cost.length() < 1){
				valid = false;
				str += "Enter 0 for a free procedure. ";
			}
		}

		//Update log for GUI
		gui.setLog(str);
		return valid;
	}

	/**
	 * Checks payment validity.
	 * @param amount	Amount of payment
	 * @return			Validity of payment
	 */
	public static boolean checkPay(String amount){
		boolean valid = true;	//Flag for payment validity
		String str = "";	//Prepare log String

		//Check if user left field empty
		if(valid){
			if(amount.length() < 1){
				valid = false;
				str += "Payment cannot be 0. ";
			}
		}

		//Update log for GUI
		gui.setLog(str);
		return valid;
	}

	/**
	 * Adds patient if details are valid
	 * 
	 * @param name		Name of patient
	 * @param address	Address of patient
	 * @param phone		Phone number of patient
	 * @return			Validity of patient details
	 */
	public static boolean addPat(String name, String address, String phone){
		boolean valid = true;	//Flag for patient validity
		String str = "";	//Prepare log String

		//Check if user left any empty fields
		if(name.length() < 1){
			valid = false;
			str += "Name is mandatory. ";
		}
		if(address.length() < 1){
			valid = false;
			str += "Address is mandatory. ";
		}
		if(phone.length() < 1){
			valid = false;
			str += "Phone number is mandatory. ";
		}

		//If valid add patient
		if(valid){
			db.addPatient(name, address, phone, dentist);
			str += "Patient " + name + " added successfully.";
		}

		//Update log for GUI
		gui.setLog(str);
		return valid;
	}

	/**
	 * Checks and adds procedure to system if valid.
	 * 
	 * @param name	Name of procedure
	 * @param cost	Cost of procedure
	 * @return		Validity of procedure
	 */
	public static boolean addProc(String name, String cost){
		boolean valid = checkProc(name, cost);	//Check if procedure is valid

		//If valid try to parse cost into double. If parsed, add procedure to system and write to file
		if(valid){
			try{
				double price = Double.parseDouble(cost);
				db.addProcedure(name, price);
			}
			catch(Exception e){
				valid = false;
				gui.setLog("Enter a double value for cost.");
			}
		}
		return valid;
	}

	/**
	 * Checks and adds payment to patient invoice if valid.
	 * 
	 * @param invId		Id of invoice
	 * @param amount	Amount of payment
	 * @return			Validity of payment
	 */
	public static boolean addPay(String invId, String amount){
		boolean valid = checkPay(amount);//Check if payment is valid

		//If valid try to parse amount into double. If parsed, add payment to patient invoice.
		if(valid){
			try{
				double price = Double.parseDouble(amount);
				db.addPayment(invId, price);
				
				db.getInvoices(invId, true);
				db.next();
				double outstanding = Double.parseDouble(db.getResult(0, "outstanding")) - price;
				String out = "" + outstanding;
				db.updateInvoice(invId, out);
			}
			catch(Exception e){
				valid = false;
				gui.setLog("Enter a double value for cost.");
			}
		}
		return valid;
	}

	public static void getPats(String id){
		db.getPatients(id);
	}
	
	public static String getPat(String id){
		return db.getResult(0, id);
	}

	public static void getProcs(String name){
		db.getProcedures(name);
	}
	
	public static String getProc(String column){
		return db.getResult(0, column);
	}
	
	public static void getInvs(String id){
		db.getInvoices(id, false);
	}
	
	public static String getInv(String column){
		return db.getResult(0, column);
	}

	
	public static void getInvProcs(String id, boolean mode){
		db.getInvProcs(id, mode);
	}
	
	public static String getInvProc(String column){
		return db.getResult(0, column);
	}

	public static void getPays(String name, boolean mode){
		db.getPayments(name, mode);
	}
	
	public static String getPay(String column){
		return db.getResult(0, column);
	}

	/**
	 * Writes a report on patients.
	 * Depending on mode writes report in 2 ways:
	 * 0 = Report of patients sorted in ascending order by name.
	 * 1 = Report of patients sorted by amount unpaid over 6 months in descending order.
	 * 
	 * @param mode	Mode of operation
	 * @param file	File name to write to
	 */
	public static void writeReport(int mode, String file){
		//If no name entered ask to enter name
		if(file.length() == 0)
			gui.setLog("Enter file name.");

		//If name is entered
		else{
			String str = "";	//Prepare String for writing

			//Sort patients according to mode
			if(mode == 0){	//Sort by name
				str += "Report of patients sorted by name.\n\n";
				
				db.getPatients("");
				ArrayList<String> patientIds = new ArrayList<String>();
				while(db.next())
					patientIds.add(db.getResult(0, "id"));
				Collections.sort(patientIds);

				//Add patient data to String
				for(int a = 0; a < patientIds.size(); a++){
					db.getPatients(patientIds.get(a));
					db.next();
					str += db.getResult(0, "name");

					//Add newline only if not last patient
					if(a != patientIds.size()-1)
						str += "\n";
				}
			}
			else{	//Sort by unpaid
				str += "Report of patients sorted by unpaid over 6 months.\n\n";
				
				//Did not get to finish in time.
				/*db.getPatients("");
				ArrayList<ArrayList<String>> patients = new ArrayList<ArrayList<String>>();
				ArrayList<String> patientInfo = new ArrayList<String>();
				while(db.next()){
					patientInfo.add(db.getResult(0, "id"));
					patients.add(patientInfo);
					patientInfo.remove(0);
				}
				double outstanding = 0;
				for(int a = 0; a < patients.size(); a++){
					db.getInvoices(patients.get(a).get(0), false);
					while(db.next()){
						outstanding += Double.parseDouble(db.getResult(0, "outstanding"));
					}
					patients.get(a).add("" + outstanding);
				}
				ArrayList<String> outstandings = new ArrayList<String>();
				for(int b = 0; b < patients.size(); b++){
					outstandings.add(patients.get(b).get(1));
				}
				Collections.sort(outstandings);
				

				//Add patient data to String
				for(int a = 0; a < patientInfo.size(); a++){
					db.getPatients(patientInfo.get(a));
					str += db.getResult(0, "name");

					//Add newline only if not last patient
					if(a != patientInfo.size()-1)
						str += "\n";
				}*/
			}

			//Try to write report to the file
			try (Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8))) {
				writer.write(str);
			} 
			catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void addInv(String id){
		db.addInvoice(id, 0);
	}

	/**
	 * Adds procedure to invoice.
	 * 
	 * @param invId		Id of invoice
	 * @param name		Name of procedure
	 */
	public static void addInvProc(String invId, String name){
		db.getProcedures(name);
		db.next();
		double cost = Double.parseDouble(db.getResult(0, "cost"));
		
		db.addInvProc(invId, name, cost);
		db.getInvoices(invId, true);
		db.next();
		double outstanding = Double.parseDouble(db.getResult(0, "outstanding")) + cost;
		String out = "" + outstanding;
		db.updateInvoice(invId, out);
	}

	/**
	 * Remove procedure from invoice.
	 * 
	 * @param index		Index of patient
	 * @param invoice	Index of invoice
	 * @param proc		Index of procedure
	 */
	public static void removeInvProc(String invId, String procId){
		db.getInvProcs(procId, true);
		db.next();
		double cost = Double.parseDouble(db.getResult(0, "cost"));
		db.deleteInvProc(procId);
		
		db.getInvoices(invId, true);
		db.next();
		double outstanding = Double.parseDouble(db.getResult(0, "outstanding")) - cost;
		String out = "" + outstanding;
		db.updateInvoice(invId, out);
	}

	/**
	 * Removes invoice from patient.
	 * 
	 * @param invId	Id of invoice
	 */
	public static void removeInv(String invId){
		db.deleteInvoice(invId);
	}

	/**
	 * Removes payment from invoice.
	 * 
	 * @param invId		Id of invoice
	 * @param payId		Id of payment
	 */
	public static void removePay(String invId, String payId){
		db.getPayments(payId, true);
		db.next();
		double cost = Double.parseDouble(db.getResult(0, "amount"));
		db.deletePayment(payId);
		
		db.getInvoices(invId, true);
		db.next();
		double outstanding = Double.parseDouble(db.getResult(0, "outstanding")) + cost;
		String out = "" + outstanding;
		db.updateInvoice(invId, out);
	}
	
	public static void removePat(String id){
		db.deletePatient(id);
	}
	
	public static void removeProc(String procId){
		db.deleteProcedure(procId);
	}
	
	public static boolean editProc(String oldName, String name, String cost){
		boolean valid = true;
		
		try{
			double price = Double.parseDouble(cost);
			db.updateProcedure(oldName, name, price);
		}
		catch(Exception e){
			valid = false;
			gui.setLog("Enter a double value for cost.");
		}
		return valid;
	}
}