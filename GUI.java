//Assignment 1

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Manages the graphical user interface
 * 
 * @author Kamil Markiewicz, R00139423
 * @version 6.1
 */
public class GUI extends Application{

	//Prepare global nodes that will persist throughout the application
	Stage window;
	Scene scene;
	BorderPane bPane;
	HBox top;
	HBox bottom;
	VBox left;
	VBox right;
	VBox center;
	Label logContent;
	ImageView imageView = new ImageView();
	ButtonBar buttonBar;
	Button buttonViewPat;
	Button buttonSave;
	Button buttonProcedures;
	Button buttonReport;
	Button buttonSignOut;
	final String ICON = "icon.png";


	/* (non-Javadoc)
	 * @see javafx.application.Application#start(javafx.stage.Stage)
	 * 
	 * Fills in the global nodes and arranges the main borderpane.
	 */
	@Override
	public void start(Stage stage) throws Exception {
		MainApplication.setGUI(this);	//Establish a link between the GUI and main class

		window = stage;

		bPane = new BorderPane();//Create borderpane which will be the backbone of the entire layout

		//Create a HBox for the top region of the borderpane
		top = new HBox();
		top.setPadding(new Insets(9, 16, 9, 16));
		top.setSpacing(16);
		top.setStyle("-fx-background-color: #0078D7;");

		//Create a HBox for the bottom region of the borderpane
		bottom = new HBox();
		bottom.setPadding(new Insets(9, 16, 9, 16));
		bottom.setSpacing(16);
		bottom.setStyle("-fx-background-color: #E9E9E9;");

		//Create a VBox for the left region of the borderpane
		left = new VBox();
		left.setSpacing(16);
		left.setStyle("-fx-background-color: #F4F4F4;");

		//Create a VBox for the right region of the borderpane
		right = new VBox();
		right.setSpacing(16);
		right.setStyle("-fx-background-color: #F4F4F4;");

		//Create a VBox for the center region of the borderpane
		center = new VBox();
		center.setSpacing(16);
		center.setStyle("-fx-background-color: #FFFFFF;");
		center.setAlignment(Pos.CENTER);

		//Create a system log which will be updated to reflect user actions
		Label log = new Label("System Log:");
		logContent = new Label("System launched successfully.");
		bottom.getChildren().addAll(log, logContent);	//Add the system log to the bottom borderpane

		scene = new Scene(bPane, 854, 480);		//Create new scene in a standard 480p resolution

		loadImages();	//Load images for the main menu icon and window icons

		//Create a main menu button for managing patients
		buttonViewPat = new Button("Manage Patients");
		buttonViewPat.setOnAction(e -> {
			center.getChildren().clear();
			patMenu();		//Display patient menu
			logContent.setText("Manage patients from here.");
		});
		buttonViewPat.setMinWidth(144);

		//Create a main menu button for saving patients
		buttonSave = new Button("Save Patients");
		buttonSave.setOnAction(e -> saveWindow());		//Open save patients window
		buttonSave.setMinWidth(144);

		//Create a main menu button for managing procedures
		buttonProcedures = new Button("Manage Procedures");
		buttonProcedures.setOnAction(e -> {
			center.getChildren().clear();
			procMenu();		//Display procedure menu
			logContent.setText("Manage procedures from here.");
		});
		buttonProcedures.setMinWidth(144);

		//Create a main menu button for generating reports
		buttonReport = new Button("Generate Reports");
		buttonReport.setOnAction(e -> {
			center.getChildren().clear();
			reportMenu();		//Display report menu
			logContent.setText("Generate a report from here.");
		});
		buttonReport.setMinWidth(144);

		//Create a main menu button for signing out
		buttonSignOut = new Button("Sign Out");
		buttonSignOut.setOnAction(e -> {
			center.getChildren().clear();
			top.getChildren().clear();
			left.getChildren().clear();
			right.getChildren().clear();
			loginScreen();		//Return to login screen
			logContent.setText("Signed out.");
		});
		buttonSignOut.setMinWidth(144);

		//Set window details
		window.setTitle("Dentistry App");
		window.setResizable(false);
		window.setScene(scene);
		window.show();

		loginScreen();	//Start application into the login screen
	}

	/**
	 * Sets the system log.
	 * 
	 * @param str	System log message
	 */
	public void setLog(String str){
		logContent.setText(str);
	}

	/**
	 * Loads images for main menu and sets an icon for the window.
	 */
	public void loadImages(){
		//Try to load images
		try {
			//Create a buffered image which will trigger the catch statement if image not found
			BufferedImage img = ImageIO.read(new File(ICON));

			//If image loaded
			if(img != null){
				Image icon = new Image("file:"+ICON, 32, 0, true, false, true);		//Create an image for the main menu
				window.getIcons().add(new Image("file:"+ICON));		//Set an icon for the window
				imageView = new ImageView(icon);	//Place image into an image view node usable in javafx
			}
		} catch (IOException e) {
			setLog("Could not load icon. Please use " + ICON + " as submitted for optimal effect.");
		}
	}

	/**
	 * Displays the login screen in the application.
	 */
	public void loginScreen(){
		//Change to suitable insets
		left.setPadding(new Insets(9, 144, 9, 16));
		right.setPadding(new Insets(9, 144, 9, 16));
		center.setPadding(new Insets(9, 144, 9, 144));

		//Add image to the main menu
		top.getChildren().addAll(imageView);

		//Create text field for inputting dentist name
		TextField nameField = new TextField ();
		nameField.setPromptText("Your name");
		Label nameLabel = new Label("Dentist Name: ");

		//Create text field for inputting dentist address
		TextField addressField = new TextField ();
		addressField.setPromptText("Your address");
		Label addressLabel = new Label("Dentist Address: ");

		//Create password field for inputting dentist password
		PasswordField passField = new PasswordField();
		passField.setPromptText("Your password");
		Label passLabel = new Label("Password: ");

		//Create register button which takes in registration data about dentist and creates a new dentist if valid
		Button buttonRegister = new Button("Register");
		buttonRegister.setOnAction(e -> {
			//Check registration details
			boolean correct = MainApplication.checkReg(nameField.getText(), addressField.getText(), passField.getText());
			if(correct){
				center.getChildren().clear();
				top.getChildren().clear();
				loginScreen();	//Return to login screen
			}
		});
		buttonRegister.setDefaultButton(true);
		buttonRegister.setMinWidth(144);

		//Create button for submitting log in details to log on
		Button buttonSubmit = new Button("Submit");
		buttonSubmit.setOnAction(e -> {
			//Check login details
			boolean correct = MainApplication.checkLogIn(nameField.getText(), passField.getText());
			if(correct){
				center.getChildren().clear();
				mainMenu();		//Display main menu
			}
		});
		buttonSubmit.setDefaultButton(true);
		buttonSubmit.setMinWidth(144);

		//Create button to cancel login or registration
		Button buttonCancel = new Button("Cancel");
		buttonCancel.setOnAction(e -> {
			center.getChildren().clear();
			top.getChildren().clear();
			loginScreen();		//Return to login screen
		});
		buttonCancel.setCancelButton(true);
		buttonCancel.setMinWidth(144);

		//Create button to display registration screen
		Button buttonCreate = new Button("Create Account");
		buttonCreate.setOnAction(e -> {
			//Display registration screen
			center.getChildren().clear();
			center.getChildren().addAll(nameLabel, nameField, addressLabel, addressField, passLabel, passField, buttonRegister, buttonCancel);
		});
		buttonCreate.setMinWidth(144);

		//Create button to display login screen
		Button buttonLogIn = new Button("Log In");
		buttonLogIn.setOnAction(e -> {
			//Display login screen
			center.getChildren().clear();
			center.getChildren().addAll(nameLabel, nameField, passLabel, passField, buttonSubmit, buttonCancel);
		});
		buttonLogIn.setMinWidth(144);

		//Display base login screen
		center.getChildren().addAll(buttonCreate, buttonLogIn);

		//Set the right boxes to their respective borderpane
		bPane.setTop(top);
		bPane.setBottom(bottom);
		bPane.setLeft(left);
		bPane.setRight(right);
		bPane.setCenter(center);
	}

	/**
	 * Populates the main menu with buttons and displays patient menu.
	 */
	public void mainMenu(){
		top.getChildren().addAll(buttonViewPat, buttonSave, buttonProcedures, buttonReport, buttonSignOut);
		patMenu();
	}

	/**
	 * Displays patient menu.
	 */
	public void patMenu(){
		//Change to suitable insets
		left.setPadding(new Insets(9, 16, 9, 16));
		right.setPadding(new Insets(9, 16, 9, 16));
		center.setPadding(new Insets(9, 16, 9, 16));

		Label labelPat = new Label("Patient list");		//Create label for patient menu

		//Create a list view of patients
		ListView<String> listPat = new ListView<String>();
		ObservableList<String> patients =FXCollections.observableArrayList();
		ArrayList<String> patientIds = new ArrayList<String>();
		String name = "";

		//Populate list view
		MainApplication.getPats("");
		while(MainApplication.next()){
			if(MainApplication.activeDentist(MainApplication.getPat("dentist"))){
				name = MainApplication.getPat("name");
				patients.add(name);
				patientIds.add(MainApplication.getPat("id"));
			}
		}
		listPat.setItems(patients);

		//Create button for displaying patient info
		Button buttonInfo = new Button("Display Info");
		buttonInfo.setOnAction(e -> {
			//If a patient is selected
			if(listPat.getSelectionModel().getSelectedIndex() == -1)
				setLog("Click on a patient to display first.");
			else
				displayPatWindow(patientIds.get(listPat.getSelectionModel().getSelectedIndex()));	//Display patient info window
		});
		buttonInfo.setMinWidth(144);

		//Create button to manage patient invoices
		Button buttonInvoice = new Button("Manage Invoices");
		buttonInvoice.setOnAction(e -> {
			//If a patient is selected
			if(listPat.getSelectionModel().getSelectedIndex() == -1)
				setLog("Click on a patient to manage first.");
			else{
				center.getChildren().clear();
				invMenu(patientIds.get(listPat.getSelectionModel().getSelectedIndex()));	//Display invoice menu for this patient
				setLog("Manage patient's invoices from here.");
			}
		});
		buttonInvoice.setMinWidth(144);

		//Create button for adding a patient
		Button buttonAdd = new Button("Add Patient");
		buttonAdd.setOnAction(e -> addPatWindow());		//Display add patient window
		buttonAdd.setMinWidth(144);

		//Create button for removing a patient
		Button buttonRemove = new Button("Remove Patient");
		buttonRemove.setOnAction(e -> {
			//If a patient is selected
			if(listPat.getSelectionModel().getSelectedIndex() == -1)
				setLog("Click on a patient to remove first.");
			else
				removePatWindow(patientIds.get(listPat.getSelectionModel().getSelectedIndex()));	//Display remove patient window
		});
		buttonRemove.setMinWidth(144);

		//Create HBox for patient menu buttons
		HBox patientOptions = new HBox();
		patientOptions.setPadding(new Insets(9, 16, 9, 16));
		patientOptions.setSpacing(16);
		patientOptions.setAlignment(Pos.CENTER);

		//Add patient menu buttons to the HBox
		patientOptions.getChildren().addAll(buttonInfo, buttonInvoice, buttonAdd, buttonRemove);

		//Add nodes to the center borderpane
		center.getChildren().addAll(labelPat, listPat, patientOptions);
	}

	/**
	 * Opens window and displays patient information in it.
	 * 
	 * @param index	Index of patient to display
	 */
	public void displayPatWindow(String id){
		Stage stage = new Stage();	//Create new stage

		//Obtain data
		MainApplication.getPats(id);
		MainApplication.next();
		//Create information labels
		Label labelName = new Label(MainApplication.getPat("name"));
		Label labelAddress = new Label("Address: " + MainApplication.getPat("address"));
		Label labelNum = new Label("Phone number: " + MainApplication.getPat("phoneNO"));

		//Create button to close the window
		Button buttonReturn = new Button("Return");
		buttonReturn.setOnAction(e -> stage.close());	//Closes window
		buttonReturn.setCancelButton(true);
		buttonReturn.setMinWidth(144);

		//Create VBox for the new window layout
		VBox layout = new VBox(9);
		layout.getChildren().addAll(labelName, labelAddress, labelNum, buttonReturn);
		layout.setAlignment(Pos.CENTER);

		//Set a scene and launch window
		Scene scene = new Scene(layout, 384, 216);
		launchWindow(stage, "Patient Information", scene);
	}

	/**
	 * Displays procedure menu.
	 */
	public void procMenu(){
		//Change to suitable insets
		left.setPadding(new Insets(9, 16, 9, 16));
		right.setPadding(new Insets(9, 16, 9, 16));
		center.setPadding(new Insets(9, 16, 9, 16));

		//Create label for procedure menu
		Label labelProc = new Label("Procedure list");

		//Create list view of procedures
		ListView<String> listProc = new ListView<String>();
		ObservableList<String> procedures =FXCollections.observableArrayList();
		ArrayList<String> procedureNames = new ArrayList<String>();
		String name = "";
		String spacing = "";
		double cost = 0;

		//Populate list view with procedures
		MainApplication.getProcs("");
		while(MainApplication.next()){
			name = MainApplication.getProc("name");
			cost = Double.parseDouble(MainApplication.getProc("cost"));
			if(cost < 1000)
				spacing = "\t\t";
			else
				spacing = "\t";
			procedureNames.add(name);
			procedures.add("\u20ac" + cost + spacing + name);
		}
		listProc.setItems(procedures);

		//Create button to add procedure
		Button buttonAdd = new Button("Add Procedure");
		buttonAdd.setOnAction(e -> addProcWindow());	//Display add procedure window
		buttonAdd.setMinWidth(144);

		//Create button to edit procedure
		Button buttonEdit = new Button("Edit Procedure");
		buttonEdit.setOnAction(e -> {
			//If a procedure is selected
			if(listProc.getSelectionModel().getSelectedIndex() == -1)
				setLog("Click on a procedure to remove first.");
			else
				editProcWindow(procedureNames.get(listProc.getSelectionModel().getSelectedIndex()));	//Display edit procedure window
		});
		buttonEdit.setMinWidth(144);

		//Create button to remove procedure
		Button buttonRemove = new Button("Remove Procedure");
		buttonRemove.setOnAction(e -> {
			//If a procedure is selected
			if(listProc.getSelectionModel().getSelectedIndex() == -1)
				setLog("Click on a procedure to remove first.");
			else
				removeProcWindow(procedureNames.get(listProc.getSelectionModel().getSelectedIndex()));	//Display remove procedure window
		});
		buttonRemove.setMinWidth(144);

		//Create HBox for procedure menu buttons
		HBox patientOptions = new HBox();
		patientOptions.setPadding(new Insets(16, 16, 16, 16));
		patientOptions.setSpacing(16);
		patientOptions.setAlignment(Pos.CENTER);

		//Add procedure menu buttons to the HBox
		patientOptions.getChildren().addAll(buttonAdd, buttonEdit, buttonRemove);

		//Add nodes to patient menu
		center.getChildren().addAll(labelProc, listProc, patientOptions);
	}

	/**
	 * Opens a window and prompts to remove a patient in it.
	 * @param index
	 */
	public void removePatWindow(String id){
		Stage stage = new Stage();
		MainApplication.getPats(id);
		MainApplication.next();
		String name = MainApplication.getPat("name");	//Patient name

		//Create label for the patient removal prompt
		Label labelName = new Label("Remove patient " + name + "?");

		//Create button to remove patient
		Button buttonConfirm = new Button("Remove");
		buttonConfirm.setOnAction(e -> {
			MainApplication.removePat(id);	//Remove patient from system
			setLog("Patient " + name + " removed.");
			center.getChildren().clear();
			stage.close();	//Close window
			patMenu();		//Refresh patient menu to reflect changes
		});
		buttonConfirm.setDefaultButton(true);
		buttonConfirm.setMinWidth(144);

		//Create button to cancel patient removal
		Button buttonCancel = new Button("Cancel");
		buttonCancel.setOnAction(e -> stage.close());	//Close window
		buttonCancel.setCancelButton(true);
		buttonCancel.setMinWidth(144);

		//Create VBox for the new window layout
		VBox layout = new VBox(9);

		//Create HBox for button layout
		HBox buttons = new HBox(16);
		buttons.getChildren().addAll(buttonConfirm, buttonCancel);
		buttons.setAlignment(Pos.CENTER);
		layout.getChildren().addAll(labelName, buttons);
		layout.setAlignment(Pos.CENTER);

		//Set a scene and launch window
		Scene scene = new Scene(layout, 384, 216);
		launchWindow(stage, "Remove Patient", scene);
	}

	/**
	 * Opens a window and prompts to remove a procedure in it.
	 * 
	 * @param index	Index of procedure
	 */
	public void removeProcWindow(String name){
		Stage stage = new Stage();

		//Create label for the procedure removal window prompt
		Label labelName = new Label("Remove procedure " + name + "?");

		//Create button for removing procedure
		Button buttonConfirm = new Button("Remove");
		buttonConfirm.setOnAction(e -> {
			//Remove procedure from system
			MainApplication.removeProc(name);	//Remove procedure from system
			setLog("Procedure " + name + " removed.");
			center.getChildren().clear();
			stage.close();	//Close window
			procMenu();		//Refresh procedure menu to reflect changes
		});
		buttonConfirm.setDefaultButton(true);
		buttonConfirm.setMinWidth(144);

		//Create button to cancel procedure removal
		Button buttonCancel = new Button("Cancel");
		buttonCancel.setOnAction(e -> stage.close());	//Close window
		buttonCancel.setCancelButton(true);
		buttonCancel.setMinWidth(144);

		//Create VBox for new window layout
		VBox layout = new VBox(9);

		//Create HBox for button layout
		HBox buttons = new HBox(16);
		buttons.getChildren().addAll(buttonConfirm, buttonCancel);
		buttons.setAlignment(Pos.CENTER);
		layout.getChildren().addAll(labelName, buttons);
		layout.setAlignment(Pos.CENTER);

		//Set scene and launch window
		Scene scene = new Scene(layout, 384, 216);
		launchWindow(stage, "Remove Procedure", scene);
	}

	/**
	 * Opens a new window and prompts to remove procedure from invoice in it.
	 * 
	 * @param id		Id of patient
	 * @param invId		Id of invoice
	 * @param procId	Id of procedure
	 */
	public void removeInvProcWindow(String id, String invId, String procId){
		Stage stage = new Stage();
		MainApplication.getInvProcs(procId, true);
		MainApplication.next();
		String name = MainApplication.getInvProc("name");	//Procedure name

		//Create label for the procedure removal window prompt
		Label labelName = new Label("Remove procedure " + name + " from invoice " + invId + "?");

		//Create button for removing procedure from invoice
		Button buttonConfirm = new Button("Remove");
		buttonConfirm.setOnAction(e -> {
			MainApplication.removeInvProc(invId, procId);	//Remove procedure from invoice
			setLog("Procedure " + name + " removed.");
			center.getChildren().clear();
			stage.close();		//Close window
			editInv(id, invId);	//Refresh invoice to reflect changes
		});
		buttonConfirm.setDefaultButton(true);
		buttonConfirm.setMinWidth(144);

		//Create button to cancel procedure removal
		Button buttonCancel = new Button("Cancel");
		buttonCancel.setOnAction(e -> stage.close());	//Close window
		buttonCancel.setCancelButton(true);
		buttonCancel.setMinWidth(144);

		//Create VBox for new window layout
		VBox layout = new VBox(9);

		//Create HBox for button layout
		HBox buttons = new HBox(16);
		buttons.getChildren().addAll(buttonConfirm, buttonCancel);
		buttons.setAlignment(Pos.CENTER);
		layout.getChildren().addAll(labelName, buttons);
		layout.setAlignment(Pos.CENTER);

		//Set scene and launch window
		Scene scene = new Scene(layout, 384, 216);
		launchWindow(stage, "Remove Invoice Procedure", scene);
	}

	/**
	 * Opens new window and prompts to remove payment in it.
	 * 
	 * @param id		Id of patient
	 * @param invId		Id of invoice
	 * @param procId	Id of payment
	 */
	public void removePayWindow(String id, String invId, String payId){
		Stage stage = new Stage();
		MainApplication.getPays(payId, true);
		MainApplication.next();
		double amount = Double.parseDouble(MainApplication.getPay("amount"));	//Payment amount

		//Create label for payment removal window prompt
		Label labelName = new Label("Remove payment of " + amount + " from invoice " + invId + "?");

		//Create button for removing payment
		Button buttonConfirm = new Button("Remove");
		buttonConfirm.setOnAction(e -> {
			MainApplication.removePay(invId, payId);	//Remove payment from system
			setLog("Payment of " + amount + " removed.");
			center.getChildren().clear();
			stage.close();		//Close window
			editInv(id, invId);	//Refresh invoice to reflect changes
		});
		buttonConfirm.setDefaultButton(true);
		buttonConfirm.setMinWidth(144);

		//Create button to cancel payment removal
		Button buttonCancel = new Button("Cancel");
		buttonCancel.setOnAction(e -> stage.close());	//Close window
		buttonCancel.setCancelButton(true);
		buttonCancel.setMinWidth(144);

		//Create VBox for new window layout
		VBox layout = new VBox(9);

		//Create HBox for button layout
		HBox buttons = new HBox(16);
		buttons.getChildren().addAll(buttonConfirm, buttonCancel);
		buttons.setAlignment(Pos.CENTER);
		layout.getChildren().addAll(labelName, buttons);
		layout.setAlignment(Pos.CENTER);

		//Set scene and launch
		Scene scene = new Scene(layout, 384, 216);
		launchWindow(stage, "Remove Payment", scene);
	}

	/**
	 * Opens a new window and prompts to remove invoice in it.
	 * 
	 * @param index		Index of patient
	 * @param invoice	Index of invoice
	 */
	public void removeInvWindow(String id, String invId){
		Stage stage = new Stage();	//Invoice number

		//Create label for invoice removal window prompt
		Label labelName = new Label("Remove invoice number " + invId + "?");

		//Create buttons for removing invoice
		Button buttonConfirm = new Button("Remove");
		buttonConfirm.setOnAction(e -> {
			MainApplication.removeInv(invId);	//Remove invoice from system
			setLog("Invoice " + invId + " removed.");
			center.getChildren().clear();
			stage.close();		//Close window
			invMenu(id);		//Refresh invoice menu to reflect changes
		});
		buttonConfirm.setDefaultButton(true);
		buttonConfirm.setMinWidth(144);

		//Create button to cancel invoice removal
		Button buttonCancel = new Button("Cancel");
		buttonCancel.setOnAction(e -> stage.close());	//Close window
		buttonCancel.setCancelButton(true);
		buttonCancel.setMinWidth(144);

		//Create VBox for new window layout
		VBox layout = new VBox(9);

		//Create HBox for button layout
		HBox buttons = new HBox(16);
		buttons.getChildren().addAll(buttonConfirm, buttonCancel);
		buttons.setAlignment(Pos.CENTER);
		layout.getChildren().addAll(labelName, buttons);
		layout.setAlignment(Pos.CENTER);

		//Set scene and layout
		Scene scene = new Scene(layout, 384, 216);
		launchWindow(stage, "Remove Invoice", scene);
	}

	/**
	 * Opens a new window and prompts to edit procedure in it.
	 * 
	 * @param index	Index of procedure
	 */
	public void editProcWindow(String name){
		Stage stage = new Stage();	//Procedure name

		//Create label for procedure editing window prompt
		Label labelEdit = new Label("Edit procedure " + name);

		//Create text field for new procedure name
		TextField nameField = new TextField ();
		nameField.setPromptText("Procedure name");

		//Create text field for new procedure cost
		TextField costField = new TextField ();
		MainApplication.getProcs(name);
		MainApplication.next();
		costField.setPromptText("(\u20ac)" + MainApplication.getProc("cost"));
		Label labelCost = new Label("Procedure Cost (\u20ac): ");

		//Create button for editing procedure
		Button buttonConfirm = new Button("Edit");
		buttonConfirm.setOnAction(e -> {
			boolean correct = MainApplication.editProc(name, nameField.getText(), costField.getText());	//Check if new procedure details are valid
			if(correct){
				setLog("Procedure edited.");
				center.getChildren().clear();
				stage.close();		//Close window
				procMenu();
			}
		});
		buttonConfirm.setDefaultButton(true);
		buttonConfirm.setMinWidth(144);

		//Create button to cancel procedure editing
		Button buttonCancel = new Button("Cancel");
		buttonCancel.setOnAction(e -> stage.close());	//Close window
		buttonCancel.setCancelButton(true);
		buttonCancel.setMinWidth(144);

		//Create VBox for new window layout
		VBox layout = new VBox(9);
		layout.setPadding(new Insets(9, 144, 9, 144));

		//Create HBox for button layout
		HBox buttons = new HBox(16);
		buttons.getChildren().addAll(buttonConfirm, buttonCancel);
		buttons.setAlignment(Pos.CENTER);
		layout.getChildren().addAll(labelEdit, nameField, labelCost, costField, buttons);
		layout.setAlignment(Pos.CENTER);

		//Set scene and launch window
		Scene scene = new Scene(layout, 512, 288);
		launchWindow(stage, "Edit Procedure", scene);
	}

	/**
	 * Displays edit invoice menu.
	 * 
	 * @param index		Index of patient
	 * @param invoice	Index of invoice
	 */
	public void editInv(String id, String invId){
		//Change to suitable insets
		left.setPadding(new Insets(9, 16, 9, 16));
		right.setPadding(new Insets(9, 16, 9, 16));
		center.setPadding(new Insets(9, 16, 9, 16));

		//Create label for edit invoice menu
		Label labelInv = new Label("Invoice number " + invId);

		//Create VBox for procedures contained in the invoice
		VBox procBox = createProcBox(id, invId);

		//Create VBox for payments contained in the invoice
		VBox payBox = createPayBox(id, invId);

		//Create button to return to invoice menu
		Button buttonReturn = new Button("Return");
		buttonReturn.setOnAction(e -> {
			center.getChildren().clear();
			invMenu(id);		//Display invoice menu
		});
		buttonReturn.setCancelButton(true);
		buttonReturn.setMinWidth(144);

		//Add both boxes side by side to HBox
		HBox invoiceBoxes = new HBox(16);
		invoiceBoxes.setAlignment(Pos.CENTER);
		invoiceBoxes.getChildren().addAll(procBox, payBox);

		//Display edit invoice menu
		center.getChildren().addAll(labelInv, invoiceBoxes, buttonReturn);
	}

	/**
	 * Creates a VBox of procedures in an invoice.
	 * 
	 * @param index		Index of patient
	 * @param invoice	Index of invoice
	 * @return			VBox of procedures
	 */
	public VBox createProcBox(String id, String invId){
		//Create a label for procedure box
		Label labelProcs = new Label("Procedure list");

		//Create a list view of procedures
		ListView<String> listProcs = new ListView<String>();
		ObservableList<String> procs =FXCollections.observableArrayList();
		ArrayList<String> procedureIds = new ArrayList<String>();

		//Populate list view with procedures
		MainApplication.getInvProcs(invId, false);
		while(MainApplication.next()){
			procs.add("\u20ac" + MainApplication.getInvProc("cost") + "\t" + MainApplication.getInvProc("name"));
			procedureIds.add(MainApplication.getInvProc("id"));
		}
		listProcs.setItems(procs);

		//Create button for adding procedure
		Button buttonAddProc = new Button("Add Procedure");
		buttonAddProc.setOnAction(e -> addInvProcWindow(id, invId));	//Display add procedure window
		buttonAddProc.setMinWidth(144);

		//Create button for removing procedure
		Button buttonRemoveProc = new Button("Remove Procedure");
		buttonRemoveProc.setOnAction(e -> {
			//If procedure selected
			if(listProcs.getSelectionModel().getSelectedIndex() == -1)
				setLog("Click on a procedure to remove first.");
			else
				removeInvProcWindow(id, invId, procedureIds.get(listProcs.getSelectionModel().getSelectedIndex()));	//Display remove procedure window
		});
		buttonRemoveProc.setMinWidth(144);

		//Create HBox for buttons
		HBox procOptions = new HBox();
		procOptions.setPadding(new Insets(9, 16, 9, 16));
		procOptions.setSpacing(16);
		procOptions.setAlignment(Pos.CENTER);
		procOptions.getChildren().addAll(buttonAddProc, buttonRemoveProc);

		//Create a VBox for the layout
		VBox procBox = new VBox();
		procBox.setAlignment(Pos.CENTER);
		procBox.getChildren().addAll(labelProcs, listProcs, procOptions);

		return procBox;
	}

	/**
	 * Creates a VBox of payments in an invoice.
	 * 
	 * @param index		Index of patient
	 * @param invoice	Index of invoice
	 * @return			VBox of payments
	 */
	public VBox createPayBox(String id, String invId){
		//Create label for payment box
		Label labelPays = new Label("Payment list");

		//Create list view of payments
		ListView<String> listPays = new ListView<String>();
		ObservableList<String> pays =FXCollections.observableArrayList();
		ArrayList<String> paymentIds = new ArrayList<String>();

		//Populate list view with payments
		MainApplication.getPays(invId, false);
		while(MainApplication.next()){
			pays.add(MainApplication.getPay("date") + "\t\u20ac" + MainApplication.getPay("amount"));
			paymentIds.add(MainApplication.getInvProc("id"));
		}
		listPays.setItems(pays);

		//Create button to add payment
		Button buttonAddPay = new Button("Add Payment");
		buttonAddPay.setOnAction(e -> addPayWindow(id, invId));	//Display add payment window
		buttonAddPay.setMinWidth(144);

		//Create button to remove payment
		Button buttonRemovePay = new Button("Remove Payment");
		buttonRemovePay.setOnAction(e -> {
			//If payment is selected
			if(listPays.getSelectionModel().getSelectedIndex() == -1)
				setLog("Click on a payment to remove first.");
			else
				removePayWindow(id, invId, paymentIds.get(listPays.getSelectionModel().getSelectedIndex()));	//Display remove payment window
		});
		buttonRemovePay.setMinWidth(144);

		//Create HBox for buttons
		HBox payOptions = new HBox();
		payOptions.setPadding(new Insets(9, 16, 9, 16));
		payOptions.setSpacing(16);
		payOptions.setAlignment(Pos.CENTER);
		payOptions.getChildren().addAll(buttonAddPay, buttonRemovePay);

		//Create a VBox for layout
		VBox payBox = new VBox();
		payBox.setAlignment(Pos.CENTER);
		payBox.getChildren().addAll(labelPays, listPays, payOptions);

		return payBox;
	}

	/**
	 * Opens a new window and prompts to add a patient in it.
	 */
	public void addPatWindow(){
		Stage stage = new Stage();

		//Create label for adding patient prompt
		Label labelAdd = new Label("Add Patient");

		//Create text field for patient name
		TextField nameField = new TextField ();
		nameField.setPromptText("Patient name");
		Label labelName = new Label("Patient name: ");

		//Create text field for patient address
		TextField addressField = new TextField ();
		addressField.setPromptText("Patient address");
		Label labelAddress = new Label("Patient address: ");

		//Create text field for patient phone number
		TextField phoneField = new TextField ();
		phoneField.setPromptText("Patient phone number");
		Label labelPhone = new Label("Patient phone number: ");

		//Create button to confirm patient details
		Button buttonConfirm = new Button("Add Patient");
		buttonConfirm.setOnAction(e -> {
			//Check if patient details are valid and add to system
			boolean correct = MainApplication.addPat(nameField.getText(), addressField.getText(), phoneField.getText());
			if(correct){
				center.getChildren().clear();
				stage.close();	//Close window
				patMenu();		//Refresh patient menu to reflect changes
			}
		});
		buttonConfirm.setDefaultButton(true);
		buttonConfirm.setMinWidth(144);

		//Create button to cancel patient addition
		Button buttonCancel = new Button("Cancel");
		buttonCancel.setOnAction(e -> stage.close());	//Close window
		buttonCancel.setCancelButton(true);
		buttonCancel.setMinWidth(144);

		//Create VBox for layout
		VBox layout = new VBox(9);
		layout.setPadding(new Insets(9, 144, 9, 144));
		
		//Create HBox for buttons
		HBox buttons = new HBox(16);
		buttons.getChildren().addAll(buttonConfirm, buttonCancel);
		buttons.setAlignment(Pos.CENTER);
		layout.getChildren().addAll(labelAdd, labelName, nameField, labelAddress, addressField, labelPhone, phoneField, buttons);
		layout.setAlignment(Pos.CENTER);

		//Set scene and launch window
		Scene scene = new Scene(layout, 512, 288);
		launchWindow(stage, "Add Patient", scene);
	}

	/**
	 * Opens new window and prompts to add procedure in it.
	 */
	public void addProcWindow(){
		Stage stage = new Stage();

		//Create label for add procedure window
		Label labelAdd = new Label("Add Procedure");

		//Create text field for procedure name
		TextField nameField = new TextField ();
		nameField.setPromptText("Procedure name");
		Label labelName = new Label("Procedure name: ");

		//Create text field for procedure cost
		TextField costField = new TextField ();
		costField.setPromptText("Procedure Cost (\u20ac)");
		Label labelAddress = new Label("Procedure Cost (\u20ac): ");

		//Create button for adding procedure
		Button buttonConfirm = new Button("Add Procedure");
		buttonConfirm.setOnAction(e -> {
			//Check if procedure details are valid and add
			boolean correct = MainApplication.addProc(nameField.getText(), costField.getText());
			if(correct){
				setLog("Procedure " + nameField.getText() + " added successfully.");
				center.getChildren().clear();
				stage.close();	//Close window
				procMenu();		//Refresh procedure menu to reflect changes
			}
		});
		buttonConfirm.setDefaultButton(true);
		buttonConfirm.setMinWidth(144);

		//Create button to cancel procedure addition
		Button buttonCancel = new Button("Cancel");
		buttonCancel.setOnAction(e -> stage.close());	//Close window
		buttonCancel.setCancelButton(true);
		buttonCancel.setMinWidth(144);

		//Create VBox for layout
		VBox layout = new VBox(9);
		layout.setPadding(new Insets(9, 72, 9, 72));
		
		//Create HBox for buttons
		HBox buttons = new HBox(16);
		buttons.getChildren().addAll(buttonConfirm, buttonCancel);
		buttons.setAlignment(Pos.CENTER);
		layout.getChildren().addAll(labelAdd, labelName, nameField, labelAddress, costField, buttons);
		layout.setAlignment(Pos.CENTER);

		//Set scene and launch window
		Scene scene = new Scene(layout, 384, 216);
		launchWindow(stage, "Add Procedure", scene);
	}

	/**
	 * Opens new window and prompts to add payment in it.
	 * 
	 * @param index		Index of patient
	 * @param invoice	Index of invoice
	 */
	public void addPayWindow(String id, String invId){
		Stage stage = new Stage();

		//Create label for add payment window prompt
		Label labelAdd = new Label("Add payment to invoice " + invId + "?");

		//Create text field for payment amount
		TextField payField = new TextField ();
		payField.setPromptText("Payment amount (\u20ac)");
		Label labelAddress = new Label("Payment amount (\u20ac): ");

		//Create button for adding payment
		Button buttonConfirm = new Button("Add Payment");
		buttonConfirm.setOnAction(e -> {
			//Check if payment amount is valid
			boolean correct = MainApplication.addPay(invId, payField.getText());
			if(correct){
				setLog("Payment added successfully.");
				center.getChildren().clear();
				stage.close();				//Close window
				editInv(id, invId);	//Refresh invoice to reflect changes
			}
		});
		buttonConfirm.setDefaultButton(true);
		buttonConfirm.setMinWidth(144);

		//Create button to cancel payment addition
		Button buttonCancel = new Button("Cancel");
		buttonCancel.setOnAction(e -> stage.close());	//Close window
		buttonCancel.setCancelButton(true);
		buttonCancel.setMinWidth(144);

		//Create VBox for layout
		VBox layout = new VBox(9);
		layout.setPadding(new Insets(9, 72, 9, 72));
		
		//Create HBox for buttons
		HBox buttons = new HBox(16);
		buttons.getChildren().addAll(buttonConfirm, buttonCancel);
		buttons.setAlignment(Pos.CENTER);
		layout.getChildren().addAll(labelAdd, labelAddress, payField, buttons);
		layout.setAlignment(Pos.CENTER);

		//Set scene and launch window
		Scene scene = new Scene(layout, 384, 216);
		launchWindow(stage, "Add Payment", scene);
	}

	/**
	 * Opens new window and prompts to add new procedure to invoice in it.
	 * 
	 * @param invId	Id of invoice
	 */
	public void addInvProcWindow(String id, String invId){
		Stage stage = new Stage();

		//Create label for procedure addition to invoice prompt
		Label labelAdd = new Label("Add procedure to invoice " + invId + "?");
		Label labelProc = new Label("Choose procedure:");

		//Create a combo box of procedures
		ObservableList<String> procedures =FXCollections.observableArrayList();
		ArrayList<String> procedureNames = new ArrayList<String>();
		String spacing = "";
		String name = "";
		double cost = 0;
		
		//Populate the combo box with procedures on the system
		MainApplication.getProcs("");
		while(MainApplication.next()){
			name = MainApplication.getProc("name");
			cost = Double.parseDouble(MainApplication.getProc("cost"));
			if(cost < 1000)
				spacing = "\t\t";
			else
				spacing = "\t";
			procedureNames.add(name);
			procedures.add("\u20ac" + cost + spacing + name);
		}
		ComboBox<String> procDropDown = new ComboBox<String>(procedures);

		//Create button for adding procedure
		Button buttonConfirm = new Button("Add Procedure");
		buttonConfirm.setOnAction(e -> {
			//If procedure selected
			if(procDropDown.getSelectionModel().getSelectedIndex() == -1)
				setLog("Choose a procedure to add to the invoice first.");
			else{
				MainApplication.addInvProc(invId, procedureNames.get(procDropDown.getSelectionModel().getSelectedIndex()));	//Add procedure to invoice
				setLog("Added procedure " + procedureNames.get(procDropDown.getSelectionModel().getSelectedIndex())
				+ " to invoice " + invId + ".");
				stage.close();			//Close window
				center.getChildren().clear();
				editInv(id, invId);	//Refresh invoice to reflect changes
			}
		});
		buttonConfirm.setDefaultButton(true);
		buttonConfirm.setMinWidth(144);

		//Create button to cancel procedure addition to invoice
		Button buttonCancel = new Button("Cancel");
		buttonCancel.setOnAction(e -> stage.close());	//Close window
		buttonCancel.setCancelButton(true);
		buttonCancel.setMinWidth(144);

		//Create VBox for layout
		VBox layout = new VBox(9);
		layout.setPadding(new Insets(9, 16, 9, 16));
		
		//Create HBox for buttons
		HBox buttons = new HBox(16);
		buttons.getChildren().addAll(buttonConfirm, buttonCancel);
		buttons.setAlignment(Pos.CENTER);
		layout.getChildren().addAll(labelAdd, labelProc, procDropDown, buttons);
		layout.setAlignment(Pos.CENTER);

		//Set scene and launch window
		Scene scene = new Scene(layout, 384, 216);
		launchWindow(stage, "Add Invoice Procedure", scene);
	}

	/**
	 * Opens new window and prompts to add invoice to patient in it.
	 * 
	 * @param index	Index of patient
	 */
	public void addInvWindow(String id){
		Stage stage = new Stage();

		MainApplication.getPats(id);
		MainApplication.next();

		//Create labels for adding invoice window prompt
		String patName = MainApplication.getPat("name");
		Label labelAdd = new Label("Add invoice to " + patName + ".");
		Label labelProc = new Label("Choose procedure:");

		//Create combo box of procedures
		ObservableList<String> procedures =FXCollections.observableArrayList();
		ArrayList<String> procedureNames = new ArrayList<String>();
		String spacing = "";
		String name = "";
		double cost = 0;
		
		
		//Populate combo box with procedures on the system
		MainApplication.getProcs("");
		while(MainApplication.next()){
			name = MainApplication.getProc("name");
			cost = Double.parseDouble(MainApplication.getProc("cost"));
			if(cost < 1000)
				spacing = "\t\t";
			else
				spacing = "\t";
			procedureNames.add(name);
			procedures.add("\u20ac" + cost + spacing + name);
		}
		ComboBox<String> procDropDown = new ComboBox<String>(procedures);

		//Create button for adding invoice
		Button buttonConfirm = new Button("Add Invoice");
		buttonConfirm.setOnAction(e -> {
			//If procedure is selected
			if(procDropDown.getSelectionModel().getSelectedIndex() == -1)
				setLog("Choose a procedure to create an invoice for first.");
			else{
				MainApplication.addInv(id);
				String lastId = "" + MainApplication.getLastId();
				MainApplication.addInvProc(lastId, procedureNames.get(procDropDown.getSelectionModel().getSelectedIndex()));//Add invoice to patient
				setLog("Added invoice for " + procedureNames.get(procDropDown.getSelectionModel().getSelectedIndex()) + " to " + patName + ".");
				stage.close();		//Close window
				center.getChildren().clear();
				invMenu(id);		//Refresh invoice menu to reflect changes
			}
		});
		buttonConfirm.setDefaultButton(true);
		buttonConfirm.setMinWidth(144);

		//Create button to cancel invoice addition
		Button buttonCancel = new Button("Cancel");
		buttonCancel.setOnAction(e -> stage.close());	//Close window
		buttonCancel.setCancelButton(true);
		buttonCancel.setMinWidth(144);

		//Create VBox for layout
		VBox layout = new VBox(9);
		layout.setPadding(new Insets(9, 16, 9, 16));
		
		//Create HBox for buttons
		HBox buttons = new HBox(16);
		buttons.getChildren().addAll(buttonConfirm, buttonCancel);
		buttons.setAlignment(Pos.CENTER);
		layout.getChildren().addAll(labelAdd, labelProc, procDropDown, buttons);
		layout.setAlignment(Pos.CENTER);

		//Set scene and launch window
		Scene scene = new Scene(layout, 384, 216);
		launchWindow(stage, "Add Invoice", scene);
	}

	/**
	 * Opens new window and prompts to save patients in it.
	 */
	public void saveWindow(){
		Stage stage = new Stage();

		//Create label for save patients window prompt
		Label labelAdd = new Label("Save patients");

		//Create button for saving patients
		Button buttonConfirm = new Button("Save");
		buttonConfirm.setOnAction(e -> {
			setLog("Legacy button from file based system.");
			stage.close();		//Close window
		});
		buttonConfirm.setDefaultButton(true);
		buttonConfirm.setMinWidth(144);

		//Create button to cancel saving patients
		Button buttonCancel = new Button("Cancel");
		buttonCancel.setOnAction(e -> stage.close());	//Close window
		buttonCancel.setCancelButton(true);
		buttonCancel.setMinWidth(144);

		//Create VBox for layout
		VBox layout = new VBox(9);
		layout.setPadding(new Insets(9, 72, 9, 72));
		
		//Create HBox for buttons
		HBox buttons = new HBox(16);
		buttons.getChildren().addAll(buttonConfirm, buttonCancel);
		buttons.setAlignment(Pos.CENTER);
		layout.getChildren().addAll(labelAdd, buttons);
		layout.setAlignment(Pos.CENTER);

		//Set scene and launch window
		Scene scene = new Scene(layout, 384, 216);
		launchWindow(stage, "Save Patients", scene);
	}

	/**
	 * Displays report menu.
	 */
	public void reportMenu(){
		//Change to suitable insets
		left.setPadding(new Insets(9, 144, 9, 16));
		right.setPadding(new Insets(9, 144, 9, 16));
		center.setPadding(new Insets(9, 144, 9, 144));

		//Create label for report menu
		Label labelProc = new Label("Generate Reports");

		//Create text field for name of file to write to
		TextField nameField = new TextField ();
		nameField.setPromptText("Report file name");
		Label labelName = new Label("Report file name: ");

		//Create group for radio buttons
		final ToggleGroup group = new ToggleGroup();

		//Create radio button for sorting by name
		RadioButton radioName = new RadioButton("Sort by name");
		radioName.setUserData(0);
		radioName.setToggleGroup(group);
		radioName.setSelected(true);

		//Create radio button for sorting by unpaid
		RadioButton radioPaid = new RadioButton("Sort by unpaid");
		radioPaid.setUserData(1);
		radioPaid.setToggleGroup(group);

		//Create button for generating report
		Button buttonGenerate = new Button("Generate report");
		buttonGenerate.setOnAction(e -> {
			MainApplication.writeReport((int)group.getSelectedToggle().getUserData(), nameField.getText());		//Generate report
			if((int)group.getSelectedToggle().getUserData() == 0)
				setLog("Generated report of all patients sorted by name to " + nameField.getText() + ".");
			else
				setLog("Generated report of patients sorted by amount unpaid to " + nameField.getText() + ".");
		});
		buttonGenerate.setDefaultButton(true);
		buttonGenerate.setMinWidth(144);

		//Display report menu
		center.getChildren().addAll(labelProc, labelName, nameField, radioName, radioPaid, buttonGenerate);
	}

	/**
	 * Displays invoice menu.
	 * 
	 * @param index	Index of patient
	 */
	public void invMenu(String id){
		//Change to suitable insets
		left.setPadding(new Insets(9, 16, 9, 16));
		right.setPadding(new Insets(9, 16, 9, 16));
		center.setPadding(new Insets(9, 16, 9, 16));

		MainApplication.getPats(id);
		MainApplication.next();
		
		//Create label for invoice menu
		Label labelInv = new Label(MainApplication.getPat("name") + "'s invoice list");

		//Create list view of invoices for patient
		ListView<String> listInv = new ListView<String>();
		ObservableList<String> invoices =FXCollections.observableArrayList();
		ArrayList<String> invoiceIds = new ArrayList<String>();
		String spacing = "";
		String invId = "";

		//Populate list view with invoices
		MainApplication.getInvs(id);
		while(MainApplication.next()){
			if(Double.parseDouble(MainApplication.getInv("outstanding")) >= 1000)
				spacing = "";
			else
				spacing = "\t";
			invId = MainApplication.getInv("id");
			invoices.add(invId + "\t\t" + "\u20ac" + MainApplication.getInv("outstanding")
			+ " Outstanding\t\t" + spacing + MainApplication.getInv("date"));
			invoiceIds.add(invId);
		}
		listInv.setItems(invoices);

		//Create button to add invoice
		Button buttonAdd = new Button("Add Invoice");
		buttonAdd.setOnAction(e -> addInvWindow(id));
		buttonAdd.setMinWidth(144);

		//Create button to edit invoice
		Button buttonEdit = new Button("Edit Invoice");
		buttonEdit.setOnAction(e -> {
			//If invoice selected
			if(listInv.getSelectionModel().getSelectedIndex() == -1)
				setLog("Click on an invoice to edit first.");
			else{
				center.getChildren().clear();
				editInv(id, invoiceIds.get(listInv.getSelectionModel().getSelectedIndex()));		//Display edit invoice menu
			}
		});
		buttonEdit.setMinWidth(144);

		//Create button to remove invoice
		Button buttonRemove = new Button("Remove Invoice");
		buttonRemove.setOnAction(e -> {
			//If invoice selected
			if(listInv.getSelectionModel().getSelectedIndex() == -1)
				setLog("Click on an invoice to remove first.");
			else
				removeInvWindow(id, invoiceIds.get(listInv.getSelectionModel().getSelectedIndex()));		//Display remove invoice menu
		});
		buttonRemove.setMinWidth(144);

		//Create button for returning to patient menu
		Button buttonReturn = new Button("Return");
		buttonReturn.setOnAction(e -> {
			center.getChildren().clear();
			patMenu();		//Return to patient menu
		});
		buttonReturn.setCancelButton(true);
		buttonReturn.setMinWidth(144);

		//Create HBox for buttons
		HBox invoiceOptions = new HBox();
		invoiceOptions.setPadding(new Insets(9, 16, 9, 16));
		invoiceOptions.setSpacing(16);
		invoiceOptions.setAlignment(Pos.CENTER);
		invoiceOptions.getChildren().addAll(buttonAdd, buttonEdit, buttonRemove, buttonReturn);

		//Display edit invoice menu
		center.getChildren().addAll(labelInv, listInv, invoiceOptions);
	}

	/**
	 * Opens a new window using the parameters provided.
	 * 
	 * @param stage	Stage of the window
	 * @param title	Title of the window
	 * @param scene	Scene to be displayed
	 */
	public void launchWindow(Stage stage, String title, Scene scene){
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.setResizable(false);
		stage.setTitle(title);
		stage.setScene(scene);
		stage.showAndWait();
	}
}