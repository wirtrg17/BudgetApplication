package application;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class signupScreenController {

    @FXML
    private Label signupLabel;

    @FXML
    private ImageView piggybankIcon;

    @FXML
    private Label createUsernameLabel;

    @FXML
    public TextField createUsernameField;

    @FXML 
    private Label createPasswordLabel;

    @FXML
    private TextField createPasswordField;

    @FXML
    private Button createAccountButton;

    @FXML
    private Button addInfoButton;

    @FXML
    private Slider monthlyIncomeSlider;
    
    @FXML 
    private Slider budgetSlider;
    
    @FXML
    void addInfo(ActionEvent event) {
    }
    
    @FXML
    private Label firstNameLabel;

    @FXML
    private TextField firstNameField; 

    @FXML
    private Label lastNameLabel;

    @FXML
    private TextField lastNameField;

    @FXML
    private Label incomeSourceLabel;

    @FXML
    private TextField incomeSourceField;

    @FXML
    private Label monthlyIncomeLabel;

    @FXML
    private TextField monthlyIncomeField;

    @FXML
    private Label monthlyBudgetLabel;

    @FXML
    private TextField monthlyBudgetField;
    
    @FXML
    private Button backButton;
    
    String createUser;
    static String user_name;

    
    public void initialize() {
    	monthlyIncomeField.textProperty().bind(monthlyIncomeSlider.valueProperty().asString("%.2f"));
    	monthlyBudgetField.textProperty().bind(budgetSlider.valueProperty().asString("%.2f"));
   
    	//user directions
		 Alert alert = new Alert(AlertType.INFORMATION);
	 	   alert.setTitle("Signup");
	 	   alert.setHeaderText("Welcome to Budget Buddy!");
	 	   alert.setContentText("Please fill out every field in this form to create your account.\nPress OK to continue.");
	 	   alert.showAndWait();

    }
    
   //create new account in mySql database
    @FXML
    void createAccount(ActionEvent event) throws SQLException, ClassNotFoundException, IOException {
    	Double monthlyIn = Double.parseDouble(monthlyIncomeField.getText());
    	Double monthBud = Double.parseDouble(monthlyBudgetField.getText());
    	
    	  if (firstNameField.getText().isEmpty() || lastNameField.getText().isEmpty() || createUsernameField.getText().isEmpty()
	    		   || createPasswordField.getText().isEmpty() || incomeSourceField.getText().isEmpty() 
	    		   || monthlyIn <= 0.0 || monthBud <= 0.0) {
	    	   
	    	  //validate user input to make sure no field is left blank
	    	   Alert alert = new Alert(AlertType.INFORMATION);
	    	   alert.setTitle("Create An Account");
	    	   alert.setHeaderText("Error.");
	    	   alert.setContentText("You must not leave any field blank.");
	    	   alert.showAndWait();
	       } else {
	    	   
	   //if all fields are filled, add user to database
	       	DBConntection.DBconnect();

		       // create the java statement to add user to database
		       Statement createAccount = DBConntection.conn.createStatement();
		       String monthlyIncome = monthlyIncomeField.getText();
		       user_name = createUsernameField.getText();
		      
		       //convert strings to doubles
		       double income = Double.parseDouble(monthlyIncome);
		       String monthlyBudget = monthlyBudgetField.getText();
		       double budget = Double.parseDouble(monthlyBudget);
		       
		       //check to see if the user name they wish to create already exists
		       Statement st = DBConntection.conn.createStatement();
		       String createUsername = "SELECT * FROM personalinfo WHERE username='" + createUsernameField.getText() + "'";
		       createUser = createUsernameField.getText();
		       ResultSet rs = st.executeQuery(createUsername);
		       
		       if (rs.next()) {
		           createUser = rs.getString("username");
		           Alert alert = new Alert(AlertType.INFORMATION);
		    	   alert.setTitle("Create An Account");
		    	   alert.setHeaderText("Username Taken.");
		    	   alert.setContentText("The username " + createUser + " already exists. Please try again.");
		    	   alert.showAndWait();
		       } else {
		       createAccount.executeUpdate("INSERT INTO personalinfo (fName, lName, username, password, "
		       		+ "incomeSource, monthlyIncome, monthlyBudgetAmount)VALUES('"  + 
		    		   firstNameField.getText() + "','" + lastNameField.getText() + "','" + createUsernameField.getText() 
		    		   + "','" + createPasswordField.getText() + "','" + incomeSourceField.getText() + "','" 
		    		   + income + "','" + budget +"')");
		   
		       //success alert
		       Alert alert = new Alert(AlertType.INFORMATION);
	    	   alert.setTitle("Create An Account");
	    	   alert.setHeaderText("Success!");
	    	   alert.setContentText("Your account has been successfully created.\nPress OK to continue.");
	    	   alert.showAndWait();
	    	   
	    		//switch to account info screen
	       	Parent accountInfoParent = FXMLLoader.load(getClass().getResource("accountInfo.fxml"));
	       	Scene accountInfoScene = new Scene (accountInfoParent);
	       	Stage accountInfoStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
	       	accountInfoStage.setScene(accountInfoScene);
	       	accountInfoStage.show();
	       	accountInfoStage.setTitle("Add Account Information");
	       	
	       	//close db connection
	       	DBConntection.conn.close();
		       }   
         }
    
    } 

    //return to home screen
    @FXML
    void goBack(ActionEvent event) throws IOException {
    	Parent loginScreenParent = FXMLLoader.load(getClass().getResource("loginScreen.fxml"));
       	Scene loginScreenScene = new Scene (loginScreenParent);
       	Stage loginScreenStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
       	loginScreenStage.setScene(loginScreenScene);
       	loginScreenStage.show();
       	loginScreenStage.setTitle("Budget Buddy | Login or Signup");
    }

}
