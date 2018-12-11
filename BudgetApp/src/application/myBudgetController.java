package application;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class myBudgetController {

	 @FXML
	 AnchorPane budgetAnchorPane;

    @FXML
    private Rectangle addTransactionsBackground;

    @FXML
    private Label addTransactionLabel;

    @FXML
    private TextField dateTextField;

    @FXML
    private Label transactionDateLabel;

    @FXML
    private ComboBox<String> categoryMenu;
    ObservableList<String> categoryMenuList = FXCollections.observableArrayList("Food", "Fuel", "Rent", "Entertainment", "Other");

    @FXML
    private Label transactionCategoryLabel;

    @FXML
    private TextField transactionTextLabel;

    @FXML
    private Label transactionAmountLabel;

    @FXML
    private Button addButton;

    @FXML
    private Rectangle transactionCover;

    @FXML
    private PieChart budgetChart;

    @FXML
    private Button addTransactionButton;
    
    @FXML
    private TextField commentsTextField;

    @FXML
    private Label commentsLabel;
    
    
    @FXML
    private TableView<ModelTable> table;

    @FXML
    private TableColumn<ModelTable, String> col_date;

    @FXML
    private TableColumn<ModelTable, String> col_description;

    @FXML
    private TableColumn<ModelTable, String> col_amount;
    
    @FXML
    private TableColumn<ModelTable, String> col_comments;

    @FXML
    private Label recentTransactionsLabel;
    
    @FXML
    private Label budgetAmountLabel;
    
    @FXML
    private Button signoutButton;
    
    @FXML
    private Button cancelButton;
    
    @FXML 
    private Slider amountSlider;
    
    String username = loginScreenController.user_name;
    
    //initialize variables to add to pie chart
    double food = 0; double fuel = 0; double rent = 0; double entertainment = 0; double other = 0; double moneySpent = 0; 
    double budgetAmt = 0; double availableMoney = 0;
    
    public void getData() throws ClassNotFoundException, SQLException, FileNotFoundException {
    	
    	
    	DBConntection.DBconnect();

	       //get "food" data to display into pie chart
	       Statement stmt1 = DBConntection.conn.createStatement();
           ResultSet rsFood;
	       rsFood = stmt1.executeQuery("SELECT SUM(transactionAmount) FROM transactions WHERE transactionDescription='food' &&  username ='" + username + "'") ;
           while ( rsFood.next() ) {
        	   food =  ((Number) rsFood.getObject(1)).doubleValue();
           }
        	   //get "fuel" data to display into pie chart
    	       Statement stmt2 = DBConntection.conn.createStatement();
               ResultSet rsFuel;
    	       rsFuel = stmt2.executeQuery("SELECT SUM(transactionAmount) FROM transactions WHERE transactionDescription='fuel' &&  username ='" + username + "'");
               while ( rsFuel.next() ) {
            	   fuel =  ((Number) rsFuel.getObject(1)).doubleValue();
               }
            	   //get "rent" data to display into pie chart
        	       Statement stmt3 = DBConntection.conn.createStatement();
                   ResultSet rsRent;
        	       rsRent = stmt3.executeQuery("SELECT SUM(transactionAmount) FROM transactions WHERE transactionDescription='rent' &&  username ='" + username + "'");
                   while ( rsRent.next() ) {
                	   rent =  ((Number) rsRent.getObject(1)).doubleValue();
                   }
                	   //get "entertainment" data to display into pie chart
            	       Statement stmt4 = DBConntection.conn.createStatement();
                       ResultSet rsEnt;
            	       rsEnt = stmt4.executeQuery("SELECT SUM(transactionAmount) FROM transactions WHERE transactionDescription='entertainment' &&  username='" + username + "'");
                       while ( rsEnt.next() ) {
                    	   entertainment =  ((Number) rsEnt.getObject(1)).doubleValue();
                       }
                       //get "other" data to display into pie chart
            	       Statement stmt5 = DBConntection.conn.createStatement();
                       ResultSet rsOther;
            	       rsOther = stmt5.executeQuery("SELECT SUM(transactionAmount) FROM transactions WHERE transactionDescription='other' && username='" + username + "'");
                       while ( rsOther.next() ) {
                    	   other =  ((Number) rsOther.getObject(1)).doubleValue();
                       }
                       //get "available money" data to display into pie chart
            	       Statement stmt6 = DBConntection.conn.createStatement();
                       ResultSet rsSpent;
            	       rsSpent = stmt6.executeQuery("SELECT SUM(transactionAmount) FROM transactions WHERE username = '" + username + "'");
                       while ( rsSpent.next() ) {
                    	    moneySpent =  ((Number) rsSpent.getObject(1)).doubleValue();
                       }
                      //get "monthly budget allowance" data to display into pie chart
            	       Statement stmt7 = DBConntection.conn.createStatement();
                       ResultSet rsBudget;
            	       rsBudget = stmt7.executeQuery("SELECT monthlyBudgetAmount FROM personalinfo WHERE username = '" + username + "'");
                       while ( rsBudget.next() ) {
                    	    budgetAmt =  ((Number) rsBudget.getObject(1)).doubleValue();
                       }
                       
                       //calculate how much money is still available
                      availableMoney = budgetAmt-moneySpent;
                       
                       DBConntection.conn.close();
    	}
       
   
    public void initialize() throws SQLException, ClassNotFoundException, FileNotFoundException {
    	
    	amountSlider.setVisible(false);
    	transactionTextLabel.textProperty().bind(amountSlider.valueProperty().asString("%.2f"));
    	
    	getData();
    	ObservableList<PieChart.Data> list = FXCollections.observableArrayList(
    			new PieChart.Data("Food", food),
    			new PieChart.Data("Fuel", fuel),
    			new PieChart.Data("Rent", rent),
    			new PieChart.Data("Entertainment", entertainment),
    			new PieChart.Data("Other", other),
    			new PieChart.Data("Available Money", availableMoney)
    			);
    	budgetChart.setData(list);
    	
    	String Spent = Double.toString(moneySpent);
    	String Budget = Double.toString(budgetAmt);
    	String spend = String.format("%.2f", moneySpent);
    	
    	
    	//display how much user has spent from their budget amount
    	if (moneySpent > budgetAmt) {
    		budgetAmountLabel.setStyle("-fx-text-fill: red;");
    		double overBudget = (moneySpent - budgetAmt);
    		budgetAmountLabel.setText(" $" + Spent + " / $" + Budget);
    		
    		//over budget alert
    		 Alert alert = new Alert(AlertType.INFORMATION);
    	 	   alert.setTitle("Budget Buddy");
    	 	   alert.setHeaderText("OVER BUDGET");
    	 	   alert.setContentText("You are over budget by $" + overBudget + "!\nReview your purchase history and try and reduce any unnecessary purchases.");
    	 	   alert.showAndWait();
    	} else {
    	budgetAmountLabel.setText("$" + spend + " / $" + Budget);}
    	
    	
    	//display transaction chart
    	ObservableList<ModelTable> obList = FXCollections.observableArrayList();
    	DBConntection.DBconnect();

    	ResultSet rs = DBConntection.conn.createStatement().executeQuery("SELECT * FROM transactions WHERE username ='" + username + "'" + " ORDER BY transactionDate DESC");
    	while (rs.next()) {
    		obList.add(new ModelTable(rs.getString("transactionDate"), rs.getString("transactionDescription"), rs.getString("transactionAmount"), rs.getString("comments")));
    	} 
    	//set table data
    	col_date.setCellValueFactory(new PropertyValueFactory<>("date"));
    	col_description.setCellValueFactory(new PropertyValueFactory<>("description"));
    	col_amount.setCellValueFactory(new PropertyValueFactory<>("amount"));
    	col_comments.setCellValueFactory(new PropertyValueFactory<>("comments"));
    	table.setItems(obList);
    	
    	categoryMenu.getItems().addAll("Food", "Fuel", "Rent", "Entertainment", "Other");
    	
    	DBConntection.conn.close();
    	}
    
    
    //exit add transaction
    @FXML
    void exitAddTransaction(ActionEvent event) {
    	transactionCover.setVisible(true);
    	addTransactionButton.setVisible(true);
    	table.setVisible(true);
    	recentTransactionsLabel.setVisible(true);
    	amountSlider.setVisible(false);

    }
    
    //function to see if date is in MM/DD/YYYY format, if not return error
   public boolean isInvalid() throws FileNotFoundException  {
       SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
       sdf.setLenient(true);
       String uDate = dateTextField.getText();
       try{
           sdf.parse(uDate);
           return false;
       }
       catch(Exception e)
       {
    	  
       }
	return true;
   }  
   
    //add transactions button
    @FXML
    void add(ActionEvent event) throws SQLException, ClassNotFoundException, IOException, ParseException {
    	
    	//show elements
    	transactionCover.setVisible(true);
    	addTransactionButton.setVisible(true);
    	table.setVisible(true);
    	recentTransactionsLabel.setVisible(true);
    	amountSlider.setVisible(false);
    	
    	Double transAmt = Double.parseDouble(transactionTextLabel.getText());
    	
    	if (isInvalid()) {
    		 Alert alert = new Alert(AlertType.INFORMATION);
	    	   alert.setTitle("Add a transaction");
	    	   alert.setHeaderText("ERROR.");
	    	   alert.setContentText("Please enter the date in MM/DD/YYYY format and ensure all field are filled out before continuing.");
	    	   alert.showAndWait();
    	} 
    	else if (dateTextField.getText().isEmpty() || transAmt <= 0.0 || categoryMenu.getValue().isEmpty()) {
    		 //ensure all fields are filled out 
		       Alert alert = new Alert(AlertType.INFORMATION);
	    	   alert.setTitle("Add a transaction");
	    	   alert.setHeaderText("ERROR.");
	    	   alert.setContentText("No field can be left blank.");
	    	   alert.showAndWait();
    	} else {
        	DBConntection.DBconnect();
        	
		       // create the java statement to add user to database
		       Statement createAccount = DBConntection.conn.createStatement();
		       String monthlyIncome = transactionTextLabel.getText();
		       
		       //convert strings to doubles
		       double amount = Double.parseDouble(monthlyIncome);
		      
		       createAccount.executeUpdate("INSERT INTO transactions (transactionDate, transactionDescription, "
		       		+ "transactionAmount, username, comments)VALUES('"  + dateTextField.getText() + "','" + categoryMenu.getValue() + "','" + amount + "','" + username + "','" + commentsTextField.getText() + "')");
		      
		       //success alert
		       Alert alert = new Alert(AlertType.INFORMATION);
	    	   alert.setTitle("Add a transaction");
	    	   alert.setHeaderText("Success!");
	    	   alert.setContentText("Your transaction has been added.\nPress OK to continue.");
	    	   alert.showAndWait();
	    	   
	    	   //refresh scene to add transactions to pie chart and data table
	    	    Parent budgetParent = FXMLLoader.load(getClass().getResource("myBudget.fxml"));
		    	Scene budgetScene = new Scene (budgetParent);
		    	Stage budgetStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		    	budgetStage.setScene(budgetScene);
		    	budgetStage.show();
		    	budgetStage.setTitle("My Budget");
		    	
		    	DBConntection.conn.close();}
    	}
    
 
    @FXML
    void addTransaction(ActionEvent event) throws ClassNotFoundException, SQLException {
    	//hide elements
    	transactionCover.setVisible(false);
    	addTransactionButton.setVisible(false);
    	table.setVisible(false);
    	recentTransactionsLabel.setVisible(false);
    	amountSlider.setVisible(true);

    }
    
    //user signout
    @FXML
    void signout(ActionEvent event) throws IOException {
    	
    	//logout confirmation
	   Alert alert = new Alert(AlertType.INFORMATION);
 	   alert.setTitle("Budget Buddy");
 	   alert.setHeaderText("Signout Successful.");
 	   alert.setContentText("You will be redirected to the login screen.");
 	   alert.showAndWait();
    	
    	Parent loginScreenParent = FXMLLoader.load(getClass().getResource("loginScreen.fxml"));
       	Scene loginScreenScene = new Scene (loginScreenParent);
       	Stage loginScreenStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
       	loginScreenStage.setScene(loginScreenScene);
       	loginScreenStage.show();
       	loginScreenStage.setTitle("Budget Buddy | Login or Signup");
    } 
}
