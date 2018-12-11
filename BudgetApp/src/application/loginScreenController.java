package application;


import java.io.IOException;
import java.sql.*;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.scene.Node;

public class loginScreenController {

	   @FXML
	    private ImageView moneyBackgroundImage;

	    @FXML
	    private Rectangle blueOverlay;

	    @FXML
	    private ImageView piggyBankIcon;

	    @FXML
	    private Label titleLabel2;

	    @FXML
	    private Label titleLabel1;

	    @FXML
	    private Label usernameLabel;

	    @FXML
	    private Label passwordLabel;

	    @FXML
	    private Button loginButton;

	    @FXML
	    private Button signupButton;

	    @FXML
	    private TextField usernameTextField;

	    @FXML
	    private PasswordField passwordField;
   
	    public static String user_name;
    

    // when the login button is pressed
    @FXML
    void login(ActionEvent event) throws ClassNotFoundException, IOException, SQLException {
    	//check to see if their information is in the system
    	DBConntection.DBconnect();
		       Statement st = DBConntection.conn.createStatement();
		       String loginStatement = "SELECT * FROM personalinfo WHERE username='" + usernameTextField.getText() + 
		    		   "' && password='" + passwordField.getText() + "'";
		       
		       String un = usernameTextField.getText();
		       String pw = passwordField.getText();
		       
		       // execute the query, and get a java result set
		       ResultSet rs = st.executeQuery(loginStatement);
		       
		       if (rs.next()) {
		           un = rs.getString("username");
		           pw = rs.getString("password");
		           
		           user_name = rs.getString("username");
		           
		         //switch to my budget screen
			    	Parent budgetParent = FXMLLoader.load(getClass().getResource("myBudget.fxml"));
			    	Scene budgetScene = new Scene (budgetParent);
			    	Stage budgetStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			    	budgetStage.setScene(budgetScene);
			    	budgetStage.show();
			    	budgetStage.setTitle("My Budget");
			    
			    //close MySQL connection
			    DBConntection.conn.close();
			    
		       } else {
		    	   //validate user input
		    	   Alert alert = new Alert(AlertType.INFORMATION);
		    	   alert.setTitle("Login Status");
		    	   alert.setHeaderText("Login Unsuccessful");
		    	   alert.setContentText("Your username or password may be incorrect or you may need to register.");
		    	   alert.showAndWait(); 
		       }             
    }

    // when the sign up button is pushed
    @FXML
    void signup(ActionEvent event) throws IOException {

    	//switch to sign up screen
    	Parent signupParent = FXMLLoader.load(getClass().getResource("signupScreen.fxml"));
    	Scene signupScene = new Scene (signupParent);
    	Stage signupStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    	signupStage.setScene(signupScene);
    	signupStage.show();
    	signupStage.setTitle("Sign Up");
    }

}
