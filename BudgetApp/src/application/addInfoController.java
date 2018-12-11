package application;

import java.io.FileNotFoundException;
import java.io.IOException;
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
import javafx.stage.Stage;

public class addInfoController {

    @FXML
    private Label accountTypeLabel;

    @FXML
    private TextField accountTypeField;

    @FXML
    private Label usersLabel;

    @FXML
    private TextField usersField;

    @FXML
    private Label balanceLabel;

    @FXML
    private TextField balanceField;

    @FXML
    private Button myBudgetButton;
    
    @FXML
    private Slider balanceSlider;
    
    String username = signupScreenController.user_name;

    public void initialize() throws FileNotFoundException {
    	
    	//user directions
		 Alert alert = new Alert(AlertType.INFORMATION);
	 	   alert.setTitle("Account Information");
	 	   alert.setHeaderText("Add Account Information.");
	 	   alert.setContentText("Please fill out every field in this form to add your account details. All fields must be filled out to create your account.\nPress OK to continue.");
	 	   alert.showAndWait();
	 	   
	 	  balanceField.textProperty().bind(balanceSlider.valueProperty().asString("%.2f"));
    	} 
    
    
    @FXML
    void myBudget(ActionEvent event) throws SQLException, ClassNotFoundException, IOException {
    
    
    	Double accBalance = Double.parseDouble(balanceField.getText());
    	//validate user input to make sure no field is left blank
    	if (accountTypeField.getText().isEmpty() || accBalance <= 0.0 || usersField.getText().isEmpty()) {
    		  Alert alert = new Alert(AlertType.INFORMATION);
	    	   alert.setTitle("Create An Account");
	    	   alert.setHeaderText("Error.");
	    	   alert.setContentText("You must not leave any field blank.");
	    	   alert.showAndWait();
	       } 
    	else {
	    	   
	   //if all fields are filled, add additional info to database
	       	DBConntection.DBconnect();

		       // create the java statement to add user to database
		       Statement addInfo = DBConntection.conn.createStatement();
		       
		       //convert strings to doubles
		       String accountBalance = balanceField.getText();
		       double balance = Double.parseDouble(accountBalance);
		       addInfo.executeUpdate("INSERT INTO account (accountType, accountUsers, accountTotalBalance, username)VALUES('"  + accountTypeField.getText() + "','" + usersField.getText() + "','" + balance  + "','" +  username + "')");
		      
		       //add phantom transactions to user
		       String date = "12-5-2018"; String amount = "0.0"; String f = "Food"; String r = "Rent"; String fl = "Fuel"; String e = "Entertainment"; String o = "Other";
		       addInfo.executeUpdate("INSERT INTO transactions (transactionDate, transactionDescription, transactionAmount, username) VALUES('"  + date + "','" + f + "','" + amount  + "','" +  username + "')");		     
		       addInfo.executeUpdate("INSERT INTO transactions (transactionDate, transactionDescription, transactionAmount, username) VALUES('"  + date + "','" + fl + "','" + amount  + "','" +  username + "')");
		       addInfo.executeUpdate("INSERT INTO transactions (transactionDate, transactionDescription, transactionAmount, username) VALUES('"  + date + "','" + r + "','" + amount  + "','" +  username + "')");
		       addInfo.executeUpdate("INSERT INTO transactions (transactionDate, transactionDescription, transactionAmount, username) VALUES('"  + date + "','" + e + "','" + amount  + "','" +  username + "')");
		       addInfo.executeUpdate("INSERT INTO transactions (transactionDate, transactionDescription, transactionAmount, username) VALUES('"  + date + "','" + o + "','" + amount  + "','" +  username + "')");
		       
		       //success alert
		       Alert alert = new Alert(AlertType.INFORMATION);
	    	   alert.setTitle("Create An Account");
	    	   alert.setHeaderText("Success!");
	    	   alert.setContentText("Your account has been successfully updated.\nYou will be redirected to the login page.");
	    	   alert.showAndWait();
	    	   
	    		//go back to homescreen
	    	 	Parent loginScreenParent = FXMLLoader.load(getClass().getResource("loginScreen.fxml"));
		       	Scene loginScreenScene = new Scene (loginScreenParent);
		       	Stage loginScreenStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		       	loginScreenStage.setScene(loginScreenScene);
		       	loginScreenStage.show();
		       	loginScreenStage.setTitle("Budget Buddy | Login or Signup");
		       	
		       	DBConntection.conn.close();
		       	
	   } 	
    } 
}
