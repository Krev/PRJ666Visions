package vision;

import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.plaf.FontUIResource;
import javax.swing.plaf.TabbedPaneUI;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import javax.xml.bind.annotation.XmlElementDecl.GLOBAL;

import org.omg.Messaging.SyncScopeHelper;

import vision.SpinnerEditor;

import javax.swing.JTabbedPane;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JDialog;

import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.Vector;
import java.util.regex.Pattern;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.SpinnerListModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.JSeparator;
import javax.swing.JSpinner;
import javax.swing.JTable;
import java.awt.Font;
import java.awt.GraphicsDevice;
import java.awt.KeyboardFocusManager;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.Window;

import javax.swing.border.BevelBorder;
import javax.swing.JTextPane;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;

public class Home extends JFrame implements KeyListener{
	
	//http://www.dreamincode.net/forums/topic/22739-message-dialogs-in-java/
	
	
	//Find Product by ID
	private Cashier cashierProductByID = null;
	private Vector<Product> productBySearch = new Vector<Product>();
	private Vector<Integer> previousValue = new Vector<Integer>();
	private Product tempProductSearch = new Product();
	private Product productByName;// = new Product();
	
	private int idLength = 8;
	
	private JPanel contentPane;
	private Employees employee;
	private JTextField welcome;
	private JTextField productID;
	private JTextField productName;
	private JTextField productPrice;
	private JTextField productQuantityPurchased;
	private JTextField subtotal_textField;
	private JTextField tax_textField;
	private JTextField total_textField;
	private JTextField textField_productID_input;
	private JTextField textField_name_input;
	
	private JTextField textField_price_input;
	private JTextField textField_quantity_input;
	
	//Table
	private JTable table;
	private DefaultTableModel model;
	private Vector<String> columnName = new Vector<String>();
	private Vector<String> data = new Vector<String>();
	private int rowCount = 1;
	private int quantity = 1;
	private int tableListenerCount = 0;
	private JButton btnCancelItem;
	
	//Table columns
	private int indent_column = 0;
	private int id_column = 1;
	private int productName_column = 2;
	private int productQuantity_column = 3;
	private int productPrice_column = 4;
	private int productQuantityAndPrice_column = 5;
	private int productRemove_column = 6;
	
	//Checkbox
	private JCheckBox remove;
	
	//Sales Total
	private double subTotal = 0;
	private double tax = 0;
	private double total = 0;
	private int c = 0;
	private String stringTempPrice = null;
	private double tempTotal = 0;
	
	//Discount
	private Vector<Discount> discount;
	private JTextField textField_discount;
	private boolean oneTimeDiscountCheck = false;
	private JTextField discount_option;
	private JFrame frame_discount;
    
	private JButton keypad_0;
	private JButton keypad_1;
	private JButton keypad_2;
	private JButton keypad_3;
	private JButton keypad_4;
	private JButton keypad_5;
	private JButton keypad_6;
	private JButton keypad_7;
	private JButton keypad_8;
	private JButton keypad_9;
	private JButton keypad_decimal;
	private JButton keypad_enter;
	
	//Screen Dimensions
	private double screenWidth = 0;
	private double screenHeight = 0;
	private int w = (int) screenWidth;
	private int h = (int) screenHeight;
	
	//Background Color
	private String defaultColor = "#eeeeee";
	
	//Cashier Cash
	private JFrame frame_cashCheckout;
	private JTabbedPane tabbedPane_7;
	private JPanel keypad_panel;
	private JTextField checkout_amount_tender_input;
	
	//Inventory
	//private int previousQuantity = 0;
	private int previousRow = 0;
	
	//Runnable
	Runnable run1;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Home frame = new Home();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * @throws Exception 
	 */
	public Home() throws Exception {
		//Getting size of screen/monitor
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		screenWidth = screenSize.getWidth();
		screenHeight = screenSize.getHeight();
		w = (int) screenWidth;
		h = (int) screenHeight;
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0, 0, 1280, 720);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setTitle("Home");
		
/*		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

	      setUndecorated(true);
	      
	      getContentPane().add(new JLabel("A JFrame Kiosk"), BorderLayout.NORTH);
	      JButton closeButton = new JButton("Close");
	      closeButton.addActionListener( new ActionListener()
	          {
	              public void actionPerformed( ActionEvent ae )
	              {
	                  System.out.println("Close button Pressed");
	                  Home.this.setVisible(false);
	                  System.exit(0);
	              }
	          });
	      getContentPane().add(closeButton, BorderLayout.CENTER);
*/		
		//connect = new Connect();
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(10, 11, 1350, 690); //main size
		contentPane.add(tabbedPane);
		
		JPanel panel_home = new JPanel();
		tabbedPane.addTab("Home", null, panel_home, null);
		panel_home.setLayout(null);
		
		welcome = new JTextField();
		welcome.setFont(new Font("Tahoma", Font.PLAIN, 30));
		welcome.setText("Welcome, ");
		welcome.setBounds(6, 6, 492, 43);
		welcome.setEditable(false);
		panel_home.add(welcome);
		welcome.setColumns(10);
		
		JButton btnSignOut = new JButton("Sign Out");
		btnSignOut.setFont(new Font("Tahoma", Font.PLAIN, 30));
		btnSignOut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					dispose();
					Login log = new Login();
					log.setVisible(true);
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		});
		btnSignOut.setBounds(738, 6, 276, 45);
		panel_home.add(btnSignOut);
		
		JPanel panel_cashier = new JPanel();
		tabbedPane.addTab("Cashier", null, panel_cashier, null);
		panel_cashier.setLayout(null);
		
		JTabbedPane tabbedPane_1 = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane_1.setBounds(10, 11, 1340, 643); //cashier tab
		panel_cashier.add(tabbedPane_1);
		
		JPanel cashier_submenu = new JPanel();
		tabbedPane_1.addTab("Cashier", null, cashier_submenu, null);
		cashier_submenu.setLayout(null);
		
		JTabbedPane tabbedPane_2 = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane_2.setBounds(10, 11, 834, 192);
		cashier_submenu.add(tabbedPane_2);
		
		JPanel item_info = new JPanel();
		tabbedPane_2.addTab("Scanned Item Details: ", null, item_info, null);
		item_info.setLayout(null);
		
		productID = new JTextField();
		productID.setText("Product ID: ");
		productID.setEditable(false);
		productID.setBounds(10, 11, 67, 20);
		item_info.add(productID);
		productID.setColumns(10);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(0, 83, 470, 11);
		item_info.add(separator);
		
		productName = new JTextField();
		productName.setText("Product Name / Description:");
		productName.setEditable(false);
		productName.setBounds(10, 52, 168, 20);
		item_info.add(productName);
		productName.setColumns(10);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(0, 42, 470, 11);
		item_info.add(separator_1);
		
		productPrice = new JTextField();
		productPrice.setText("Price: ");
		productPrice.setEditable(false);
		productPrice.setBounds(10, 91, 38, 20);
		item_info.add(productPrice);
		productPrice.setColumns(10);
		
		JSeparator separator_2 = new JSeparator();
		separator_2.setBounds(0, 122, 470, 11);
		item_info.add(separator_2);
		
		productQuantityPurchased = new JTextField();
		productQuantityPurchased.setText("Quantity Remaining: ");
		productQuantityPurchased.setEditable(false);
		productQuantityPurchased.setBounds(10, 133, 119, 20);
		item_info.add(productQuantityPurchased);
		productQuantityPurchased.setColumns(10);
		
		textField_productID_input = new JTextField();
		textField_productID_input.setBounds(87, 11, 141, 20);;
		item_info.add(textField_productID_input);
		textField_productID_input.setColumns(10);
		
		//Places cursor in ID field as soon as page loads, like focus in html
		textField_productID_input.addAncestorListener(new AncestorListener() {
			
			@Override
			public void ancestorRemoved(AncestorEvent event) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void ancestorMoved(AncestorEvent event) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void ancestorAdded(AncestorEvent event) {
				// TODO Auto-generated method stub
				SwingUtilities.invokeLater(new Runnable() {
		            @Override
		            public void run() {
		                textField_productID_input.requestFocusInWindow();
		            }
		        });
			}
		});
		
		//textField_productID_input.addKeyListener(this);		
		//ID listener, anything that gets entered goes here
		Thread t1 = new Thread(new Runnable(){
			public void run(){
				textField_productID_input.getDocument().addDocumentListener(new DocumentListener() {
					@Override
					public void removeUpdate(DocumentEvent e) {
						// TODO Auto-generated method stub
						checkID();
					}
					@Override
					public void insertUpdate(DocumentEvent e) {
						// TODO Auto-generated method stub
						checkID();
					}
					@Override
					public void changedUpdate(DocumentEvent e) {
						// TODO Auto-generated method stub
						checkID();
					}
					public void checkID(){
						String tempInput = textField_productID_input.getText();
						if(validateEmpty(tempInput) == false){
							//JOptionPane.showMessageDialog(null,"Product ID entered cannot be Empty.","Error",JOptionPane.ERROR_MESSAGE);
						}
						else if(checkForNumbers(tempInput) == false){
							JOptionPane.showMessageDialog(null,"Product ID entered cannot contain alphabets.","Error",JOptionPane.ERROR_MESSAGE);
						}
						else if(tempInput.length() > idLength){
							JOptionPane.showMessageDialog(null,"Length of Product ID is not valid.","Error",JOptionPane.ERROR_MESSAGE);
						}
						else if(tempInput.length() == idLength){
							int tempInt = Integer.parseInt(tempInput);
							
							try {
								cashierProductByID = new Cashier();
								tempProductSearch = cashierProductByID.findProductID(tempInt);
								//productBySearch.add(cashierProductByID.findProductID(tempInt));
							} catch (SQLException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							Runnable doHighlight = new Runnable() {
								@Override
								public void run() {
									if(!table.isEditing()){
										//Check if the product exists in table
										//If it does, adjust the quantity and send it
										//Else send it without adjusting
										if(productBySearch.size() > 0){
											for(int j = 0; j < model.getRowCount(); j++){
												if(productBySearch.get(j).getID() == tempProductSearch.getID()){
													//System.out.println(productBySearch.get(j).getName() + " found in vector");
													tempProductSearch.setQuantity(productBySearch.get(j).getQuantity());
													//System.out.println(tempProductSearch.getQuantity());
													break;
												}
												else{
													System.out.println("Not Found in vector");
												}
											}
										}
										
										if(tempProductSearch != null){
											if(tempProductSearch.getQuantity() < 0){
												JOptionPane.showMessageDialog(null,"Inventory quantity for product " + tempProductSearch.getName() + ", has reached 0. This product is no longer in stock.");
											}
											else if(tempProductSearch.getQuantity() > 0){
												boolean qCheck = false;
												if(model.getRowCount() == 0){
													model.addRow(new Object[]{rowCount,tempProductSearch.getID(),tempProductSearch.getName(),quantity,tempProductSearch.getSalePrice(),tempProductSearch.getSalePrice()});
													//System.out.println("Before: " + tempProductSearch.getQuantity());
													tempProductSearch.setQuantity(tempProductSearch.getQuantity()-1);
													//System.out.println("After: " + tempProductSearch.getQuantity());
												}
												else if(model.getRowCount() > 0){
													for (int i = model.getRowCount()-1; i >= 0; --i) {
														//String tName = model.getValueAt(i, productName_column).toString();
														int tID = (int) model.getValueAt(i,id_column);
														if(!table.isEditing()){
															String tQ = model.getValueAt(i, productQuantity_column).toString();
															int tempQ = Integer.parseInt(tQ);
															double tempP = tempProductSearch.getSalePrice();
															if(tID == tempProductSearch.getID()){
																tempProductSearch.setQuantity(tempProductSearch.getQuantity()-1);
																tempQ++;
																tempP = tempP * tempQ;
																tempP = Math.round(tempP * 100.0) / 100.0;
																model.setValueAt(tempQ, i, productQuantity_column);
																model.setValueAt(tempP, i, productQuantityAndPrice_column);
																rowCount--;
																qCheck = true;
																break;
															}
															else{
																qCheck = false;
															}
														}
													}
													if(qCheck == false){
														model.addRow(new Object[]{rowCount,tempProductSearch.getID(),tempProductSearch.getName(),quantity,tempProductSearch.getSalePrice(),tempProductSearch.getSalePrice()});
														//System.out.println("Before: " + tempProductSearch.getQuantity());
														tempProductSearch.setQuantity(tempProductSearch.getQuantity()-1);
														//System.out.println("After: " + tempProductSearch.getQuantity());												
													}
												}
												
												//Setting Checkbox
												TableColumn tc = table.getColumnModel().getColumn(productRemove_column);
												tc.setCellEditor(table.getDefaultEditor(Boolean.class));
												tc.setCellRenderer(table.getDefaultRenderer(Boolean.class));
												
												boolean eCheck = false;
												for(int i = 0; i < productBySearch.size(); i++){
													if(productBySearch.size() > 0){
														if(tempProductSearch.getID() == productBySearch.get(i).getID()){
															productBySearch.get(i).setQuantity(tempProductSearch.getQuantity());
															String tQ = model.getValueAt(i, productQuantity_column).toString();
															int tempQ = Integer.parseInt(tQ);
															previousValue.insertElementAt(tempQ, i);
															eCheck = true;
															break;
														}
														else{
															eCheck = false;
														}
													}
												}
												if(eCheck == false){
													productBySearch.add(tempProductSearch);
													previousValue.add(quantity);
												}
												
												textField_name_input.setText(tempProductSearch.getName());
												textField_price_input.setText("$" + String.valueOf(tempProductSearch.getSalePrice()));		
												textField_quantity_input.setText(String.valueOf(tempProductSearch.getQuantity()));
												
												//Sales Total
												subTotal += tempProductSearch.getSalePrice();
												
												//subTotal += Double.parseDouble(s);
												subTotal = Math.round(subTotal * 100.0) / 100.0;
												subtotal_textField.setText("Subtotal: $" + subTotal);
												
												tax = subTotal * 0.13;
												tax = Math.round(tax * 100.0) / 100.0;
												tax_textField.setText("Tax: $" + tax);
												
												total = subTotal + tax;
												total = Math.round(total * 100.0) / 100.0;
												total_textField.setText("Total: $" + total);									
												
												//Moves table down as scroll bar appears and more items get added
												table.scrollRectToVisible(table.getCellRect(table.getRowCount()-1, 0, true));
												
												//Leave increments at the end
												rowCount++;
												c++;
											}
										}
										else{
											textField_name_input.setText(null);
											textField_price_input.setText(null);		
											textField_quantity_input.setText(null);
										}
									}
								}
							};
							SwingUtilities.invokeLater(doHighlight);
						}
					}
				});
			}
		});
		t1.start();
		
		textField_name_input = new JTextField();
		textField_name_input.setBounds(188, 52, 272, 20);
		item_info.add(textField_name_input);
		textField_name_input.setColumns(10);
		textField_name_input.setEditable(false);
	
		textField_price_input = new JTextField();
		textField_price_input.setBounds(58, 91, 86, 20);
		textField_price_input.setEditable(false);
		item_info.add(textField_price_input);
		textField_price_input.setColumns(10);
		
		textField_quantity_input = new JTextField();
		textField_quantity_input.setBounds(142, 133, 86, 20);
		textField_quantity_input.setEditable(false);
		item_info.add(textField_quantity_input);
		textField_quantity_input.setColumns(10);
		
		JTabbedPane tabbedPane_3 = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane_3.setBounds(854, 11, 198, 259);
		cashier_submenu.add(tabbedPane_3);
		
		JPanel cashier_commands = new JPanel();
		tabbedPane_3.addTab("Cashier Commands: ", null, cashier_commands, null);
		cashier_commands.setLayout(null);
		
		JSeparator separator_4 = new JSeparator();
		separator_4.setBounds(0, 45, 264, 20);
		cashier_commands.add(separator_4);
		
		btnCancelItem = new JButton("Cancel item");
		btnCancelItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Checkbox
				if(model.getRowCount() > 0){
					int items = 0;
					for(int i = 0; i < model.getRowCount(); i++){
						try{
							if((boolean) model.getValueAt(i, productRemove_column) == true){
								items++;
							}
						}catch(Exception e1){}
					}
					if(items > 0){
						String message = "Are you sure you would like to delete the selected items (" + items + ")?";
						Object[] options = {"Yes","No"};
						int n = JOptionPane.showOptionDialog(null,message,"Confirm", 
								JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE,null,options,options[0]);
						
						if (n == JOptionPane.YES_OPTION){
							for (int i = model.getRowCount()-1; i >= 0; --i) {
								try{
									boolean b = (boolean) model.getValueAt(i, productRemove_column);
									if (b == true){
										//Sales Total
										String s = table.getValueAt(i, productPrice_column).toString();
										String qCancel = table.getValueAt(i, productQuantity_column).toString();
										
										double t = Double.parseDouble(s) * Integer.parseInt(qCancel);
										
										subTotal -= t;
										subTotal = Math.round(subTotal * 100.0) / 100.0;
										subtotal_textField.setText("Subtotal: $" + subTotal);
	
										tax = subTotal * 0.13;
										tax = Math.round(tax * 100.0) / 100.0;
										tax_textField.setText("Tax: $" + tax);
											
										total = subTotal + tax;
										total = Math.round(total * 100.0) / 100.0;
										total_textField.setText("Total: $" + total);	
										
										if(productBySearch.size() > 0){
											for(int j = 0; j < productBySearch.size(); j++){
												//Convert to string, other values always cause problem due to object type
												int idValue = (int) model.getValueAt(i, id_column);
												String idValueString = String.valueOf(idValue);
												String productTempID = String.valueOf(productBySearch.get(j).getID());
												if(idValueString.equals(productTempID)){
													//Removes product from vector and its previous value
													productBySearch.remove(j);
													previousValue.remove(j);
													
												}
											}
										}
										//Delete at end & put focus back to ID field
										model.removeRow(i);
										textField_productID_input.requestFocusInWindow();
										
									}
								}catch(Exception e2){}
							}
							//After deleting, fixes the number order in table
							for (int i = model.getRowCount()-1; i >= 0; --i) {
				            	model.setValueAt(i+1, i, 0);
				            }
							if(model.getRowCount() == 0){
								rowCount = 1;
							}
						}
					}
					else{
						JOptionPane.showMessageDialog(null,"Please select atleast one item in purchase list to remove it.");
					}
				}
				else{
					JOptionPane.showMessageDialog(null,"Please add an item to purchase list.");
				}
			}
		});
		btnCancelItem.setBounds(10, 11, 126, 23);
		cashier_commands.add(btnCancelItem);
		
		JButton button_discount = new JButton("Discount");
		button_discount.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(oneTimeDiscountCheck == true){
					JOptionPane.showMessageDialog(null,"One time discount has been already applied for this transaction.");
				}
				else if (model.getRowCount() <= 0){
					JOptionPane.showMessageDialog(null,"Please add an item to purchase list before making a discount.");
					textField_productID_input.requestFocusInWindow();
				}
				else{
					loadDiscountFrame();
				}
			}	
		});
		button_discount.setBounds(10, 52, 126, 23);
		cashier_commands.add(button_discount);
		
		textField_discount = new JTextField();
		textField_discount.setBounds(6, 77, 165, 63);
		cashier_commands.add(textField_discount);
		textField_discount.setColumns(10);
		
		JSeparator separator_3 = new JSeparator();
		separator_3.setBounds(0, 152, 264, 20);
		cashier_commands.add(separator_3);
		
		JButton btnFindByName = new JButton("Find by Name");
		btnFindByName.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Find Product by Name
				try{
					boolean check = false;
					if(!table.isEditing()){
						do{
							String productNameInput = (String)JOptionPane.showInputDialog(null, "Product Name:", "Find product by name",JOptionPane.PLAIN_MESSAGE);
							if(validateEmpty(productNameInput) == false){
								JOptionPane.showMessageDialog(null,"Product name cannot be empty. Please enter a product name.");
								check = false;
							}
							else if(productNameInput.trim().matches("^[0-9A-Za-z]*$")){
								try {
									cashierProductByID = new Cashier();
									productByName = new Product();
									productByName = cashierProductByID.findProductName(productNameInput);
								} catch (Exception e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
								if(productByName != null){
									if(!String.valueOf(productByName.getID()).isEmpty()){
										textField_productID_input.setText(String.valueOf(productByName.getID()));
										check = true;
									}
								}
								else{
									JOptionPane.showMessageDialog(null, productNameInput + " could not be found.");
									check = false;
								}
							}
							else{
								JOptionPane.showMessageDialog(null,"Please enter a valid product name.");
								check = false;
							}
						}while(check == false);
					}
					else{
						JOptionPane.showMessageDialog(null,"Please make sure the table is not in edit mode.");
					}
				}catch(Exception e1){}
			}
		});
		btnFindByName.setBounds(10, 170, 117, 29);
		cashier_commands.add(btnFindByName);
		
		JTabbedPane tabbedPane_5 = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane_5.setBounds(854, 399, 198, 150);
		cashier_submenu.add(tabbedPane_5);
		
		JPanel sales_total = new JPanel();
		tabbedPane_5.addTab("Sales Total: ", null, sales_total, null);
		sales_total.setLayout(null);
		
		subtotal_textField = new JTextField();
		subtotal_textField.setText("Subtotal: $" + subTotal);
		subtotal_textField.setEditable(false);
		subtotal_textField.setBounds(10, 11, 103, 20);
		sales_total.add(subtotal_textField);
		subtotal_textField.setColumns(10);
		
		tax_textField = new JTextField();
		tax_textField.setText("Tax: $" + tax);
		tax_textField.setEditable(false);
		tax_textField.setBounds(10, 50, 103, 20);
		sales_total.add(tax_textField);
		tax_textField.setColumns(10);
		
		total_textField = new JTextField();
		total_textField.setText("Total: $" + total);
		total_textField.setEditable(false);
		total_textField.setBounds(10, 91, 103, 20);
		sales_total.add(total_textField);
		total_textField.setColumns(10);
		
		JSeparator separator_5 = new JSeparator();
		separator_5.setBounds(0, 39, 258, 20);
		sales_total.add(separator_5);
		
		JSeparator separator_6 = new JSeparator();
		separator_6.setBounds(0, 81, 258, 20);
		sales_total.add(separator_6);
		
		JTabbedPane tabbedPane_6 = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane_6.setBounds(1062, 454, 263, 150);
		cashier_submenu.add(tabbedPane_6);
		
		JPanel checkout_panel = new JPanel();
		tabbedPane_6.addTab("Checkout: ", null, checkout_panel, null);
		checkout_panel.setLayout(null);
		
		JButton btnNewButton_1 = new JButton("Checkout");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(model.getRowCount() > 0){ // total > 0
					//Checkout
					//Increases button size
					UIManager.put("OptionPane.buttonFont", new FontUIResource(new Font("ARIAL",Font.PLAIN,35)));
					Object[] options = {"Cancel","Credit","Cash"};
					int cashCredit = JOptionPane.showOptionDialog(null,"Checout using: ","Checkout",
					    JOptionPane.YES_NO_CANCEL_OPTION,JOptionPane.QUESTION_MESSAGE,null,options,options[0]);
					
					//Cancel
					if(cashCredit == JOptionPane.YES_OPTION){
						
					}
					//Credit
					else if(cashCredit == JOptionPane.NO_OPTION){
						
					}
					//Cash
					else if(cashCredit == JOptionPane.CANCEL_OPTION){
						loadCheckoutFrame();
					}
					//tabbedPane_7.setVisible(true);
				}
				else{
					JOptionPane.showMessageDialog(null,"Please add an item to purchase list before doing a checkout.");
				}
				textField_productID_input.requestFocusInWindow();
			}
		});
		btnNewButton_1.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnNewButton_1.setBounds(0, 0, 258, 122);
		checkout_panel.add(btnNewButton_1);
		
		JTabbedPane tabbedPane_4 = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane_4.setBounds(20, 204, 822, 375);
		cashier_submenu.add(tabbedPane_4);
		
		JPanel panel_table = new JPanel(new BorderLayout());
		tabbedPane_4.addTab("List of purchased items, quantity and price: ", null, panel_table, null);
		
		columnName.addElement("#");
		columnName.addElement("Product ID");
		columnName.addElement("Name");
		columnName.addElement("Quantity");
		columnName.addElement("Price");
		columnName.addElement("Quantity * Price");
		columnName.addElement("Remove");
		
		table = new JTable(data, columnName){
			public boolean isCellEditable(int row, int column) {
				//Return true if the column (number) is editable, else false
		        if(column == 3 || column == 6){ 
		        	return true;
		        }
		        else{
		        	return false;
		        }
		    }
		};
		panel_table.add(table.getTableHeader(), BorderLayout.NORTH);
		panel_table.add(table, BorderLayout.CENTER);
		
		//Row Height
	  	table.setRowHeight(30);
	  	
	    //Column Width
	  	TableColumnModel columnModel = table.getColumnModel();
	  	columnModel.getColumn(0).setPreferredWidth(10); //ID
	 	columnModel.getColumn(1).setPreferredWidth(30); //Product ID
	  	columnModel.getColumn(2).setPreferredWidth(200); //Name
        columnModel.getColumn(3).setPreferredWidth(30); //Quantity 
	  	columnModel.getColumn(4).setPreferredWidth(50); //Price
	  	columnModel.getColumn(5).setPreferredWidth(30); //Price * Quantity
	  	columnModel.getColumn(6).setPreferredWidth(10); //Remove

	    //Columns won't be able to moved around
	  	table.getTableHeader().setReorderingAllowed(false);
	  	
	    //Center table data 
	  	DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
	  	centerRenderer.setHorizontalAlignment( SwingConstants.CENTER );
	  	table.getColumnModel().getColumn(0).setCellRenderer( centerRenderer ); //ID
	  	table.getColumnModel().getColumn(1).setCellRenderer( centerRenderer ); //Product ID
	  	table.getColumnModel().getColumn(2).setCellRenderer( centerRenderer ); //Name
	  	table.getColumnModel().getColumn(3).setCellRenderer( centerRenderer ); //Quantity
	  	table.getColumnModel().getColumn(4).setCellRenderer( centerRenderer ); //Price
	  	table.getColumnModel().getColumn(5).setCellRenderer( centerRenderer ); //Quantity * Price
	  	table.getColumnModel().getColumn(6).setCellRenderer( centerRenderer ); //Remove
	  	
	    //Center table column names
	  	centerRenderer = (DefaultTableCellRenderer) table.getTableHeader().getDefaultRenderer();
	  	centerRenderer.setHorizontalAlignment(JLabel.CENTER);
	  	
		JScrollPane jsp = new JScrollPane(table);
		jsp.setBounds(2, 2, 810, 344);
		jsp.setVisible(true);
		panel_table.add(jsp);
	   
		model = (DefaultTableModel) table.getModel();
		
		table.addKeyListener(this);
		table.getModel().addTableModelListener(new TableModelListener() {
			public void tableChanged(TableModelEvent e) {
				if(model.getRowCount() > 0){
					//Sales Total
					int col = table.getColumnModel().getSelectionModel().getLeadSelectionIndex();
			        int row = table.getSelectionModel().getLeadSelectionIndex();
					if(row > -1 && col > -1){
						if(col == productQuantity_column){
							run1 = new Runnable() {
								@Override
								public void run() {
									String tempQuantity = model.getValueAt(row, productQuantity_column).toString();
									if(validateEmpty(tempQuantity) == false){
										JOptionPane.showMessageDialog(null,"Quantity field cannot be left empty. "
												+ "Please enter a quantity above 0 for row #" + (row+1) + ".");
										model.setValueAt(1, row, col);
										table.setRowSelectionInterval(row, row);
										table.setColumnSelectionInterval(0, 0);
									}
									else if(checkForNumbers(tempQuantity) == false){
										JOptionPane.showMessageDialog(null,"Quantity entered for row #" + (row+1) + 
												" cannot contain alphabets.","Error",JOptionPane.ERROR_MESSAGE);
										model.setValueAt(1, row, col);
										table.setRowSelectionInterval(row, row);
										table.setColumnSelectionInterval(0, 0);
									}
									else{
										String tempPrice = model.getValueAt(row, productPrice_column).toString();
										int tQ = Integer.parseInt(tempQuantity);
										if(tQ > 0){
											double tP = Double.parseDouble(tempPrice);
											//tQ = Math.round(tQ * 100.0) / 100.0;
											tP = Math.round(tP * 100.0) / 100.0;
											
											tempTotal = tQ * tP;
											tempTotal = Math.round(tempTotal * 100.0) / 100.0;
											
											model.setValueAt(tempTotal, row, productQuantityAndPrice_column);
										
											if(tableListenerCount == 1){
												calculateSubtotal();
												calculateInventoryQuantity();
											}
											else if(tableListenerCount == 0){
												stringTempPrice = model.getValueAt(row, productQuantityAndPrice_column).toString();
												tableListenerCount = 0;
											}
											else{
												tableListenerCount = 0;
											}
											tableListenerCount++;
											
											//After entering value, movies selection to next column (does not leave user in same colum)
											table.setRowSelectionInterval(row, row);
											table.setColumnSelectionInterval(0, 0);
											textField_productID_input.requestFocusInWindow();
										}
										else if(tQ < 1){
											JOptionPane.showMessageDialog(null,"Please enter a quantity above 0 for row #" + (row+1) + ".");
											model.setValueAt(1, row, col);
											table.setRowSelectionInterval(row, row);
											table.setColumnSelectionInterval(0, 0);
										}
									}
								}
							};
							SwingUtilities.invokeLater(run1);
						}
					}
				}
		    }
		});
		
		// spinner values
        /*spinValues = new String[(int) MAX_SPIN_VALUE];
        for (int i = 0; i < 99; i++) {
                spinValues[i] = new String(Integer.toString(i));
        }*/
        
		tabbedPane_7 = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane_7.setBounds(1062, 11, 263, 399);
		cashier_submenu.add(tabbedPane_7);
		
		keypad_panel = new JPanel();
		tabbedPane_7.addTab("Keypad: ", null, keypad_panel, null);
		keypad_panel.setLayout(null);
		
		keypad_1 = new JButton("1");
		keypad_1.setFocusable(false);
		keypad_1.setFont(new Font("Tahoma", Font.PLAIN, 20));
		keypad_1.setBounds(0, 0, 87, 93);
		keypad_panel.add(keypad_1);
		keypad_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//String i = e.getActionCommand();
				/*
				if(textField_productID_input.hasFocus()){
					String input = textField_productID_input.getText();
					textField_productID_input.setText(input+i);
				}
				*/
				//else{
					/*requestFocusInWindow();
					int col = table.getColumnModel().getSelectionModel().getLeadSelectionIndex();
			        int row = table.getSelectionModel().getLeadSelectionIndex();
					if(row > -1 && col > -1){
						if(col == productQuantity_column){
							System.out.println("TEST");
							String tempStringInput = model.getValueAt(row, col).toString() + i;
							int tempIntegerInput = Integer.parseInt(tempStringInput);
							System.out.println(tempStringInput);
							model.setValueAt(tempIntegerInput, row, col);
						}
					}*/
				//}
				if(productBySearch.size() > 0){
					for(int i = 0; i < productBySearch.size(); i++){
						System.out.println(i + ".)" + productBySearch.get(i).getName());
					}
				}
				
				
				
				
			}
		});
		/*
		keypad_2 = new JButton("2");
		keypad_2.setFocusable(false);
		keypad_2.setFont(new Font("Tahoma", Font.PLAIN, 20));
		keypad_2.setBounds(86, 0, 87, 93);
		keypad_panel.add(keypad_2);
		keypad_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String i = e.getActionCommand();
				String input = textField_productID_input.getText();
				if(textField_productID_input.hasFocus()){
					textField_productID_input.setText(input+i);
				}
			}
		});
		
		keypad_3 = new JButton("3");
		keypad_3.setFocusable(false);
		keypad_3.setFont(new Font("Tahoma", Font.PLAIN, 20));
		keypad_3.setBounds(171, 0, 87, 93);
		keypad_panel.add(keypad_3);
		keypad_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String i = e.getActionCommand();
				String input = textField_productID_input.getText();
				if(textField_productID_input.hasFocus()){
					textField_productID_input.setText(input+i);
				}
			}
		});
		
		keypad_4 = new JButton("4");
		keypad_4.setFocusable(false);
		keypad_4.setFont(new Font("Tahoma", Font.PLAIN, 20));
		keypad_4.setBounds(0, 93, 87, 93);
		keypad_panel.add(keypad_4);
		keypad_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String i = e.getActionCommand();
				String input = textField_productID_input.getText();
				if(textField_productID_input.hasFocus()){
					textField_productID_input.setText(input+i);
				}
			}
		});
		
		keypad_5 = new JButton("5");
		keypad_5.setFocusable(false);
		keypad_5.setFont(new Font("Tahoma", Font.PLAIN, 20));
		keypad_5.setBounds(86, 93, 87, 93);
		keypad_panel.add(keypad_5);
		keypad_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String i = e.getActionCommand();
				String input = textField_productID_input.getText();
				if(textField_productID_input.hasFocus()){
					textField_productID_input.setText(input+i);
				}
			}
		});
		
		keypad_6 = new JButton("6");
		keypad_6.setFocusable(false);
		keypad_6.setFont(new Font("Tahoma", Font.PLAIN, 20));
		keypad_6.setBounds(171, 93, 87, 93);
		keypad_panel.add(keypad_6);
		keypad_6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String i = e.getActionCommand();
				String input = textField_productID_input.getText();
				if(textField_productID_input.hasFocus()){
					textField_productID_input.setText(input+i);
				}
			}
		});
		
		keypad_7 = new JButton("7");
		keypad_7.setFocusable(false);
		keypad_7.setFont(new Font("Tahoma", Font.PLAIN, 20));
		keypad_7.setBounds(0, 186, 87, 93);
		keypad_panel.add(keypad_7);
		keypad_7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String i = e.getActionCommand();
				String input = textField_productID_input.getText();
				if(textField_productID_input.hasFocus()){
					textField_productID_input.setText(input+i);
				}
			}
		});
		
		keypad_8 = new JButton("8");
		keypad_8.setFocusable(false);
		keypad_8.setFont(new Font("Tahoma", Font.PLAIN, 20));
		keypad_8.setBounds(86, 186, 87, 93);
		keypad_panel.add(keypad_8);
		keypad_8.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String i = e.getActionCommand();
				String input = textField_productID_input.getText();
				if(textField_productID_input.hasFocus()){
					textField_productID_input.setText(input+i);
				}
			}
		});
		
		keypad_9 = new JButton("9");
		keypad_9.setFocusable(false);
		keypad_9.setFont(new Font("Tahoma", Font.PLAIN, 20));
		keypad_9.setBounds(171, 186, 87, 93);
		keypad_panel.add(keypad_9);
		keypad_9.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String i = e.getActionCommand();
				String input = textField_productID_input.getText();
				if(textField_productID_input.hasFocus()){
					textField_productID_input.setText(input+i);
				}
			}
		});
		
		keypad_decimal = new JButton(".");
		keypad_decimal.setFocusable(false);
		keypad_decimal.setFont(new Font("Tahoma", Font.PLAIN, 20));
		keypad_decimal.setBounds(0, 278, 87, 93);
		keypad_panel.add(keypad_decimal);
		keypad_decimal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String i = e.getActionCommand();
				String input = textField_productID_input.getText();
				if(textField_productID_input.hasFocus()){
					textField_productID_input.setText(input+i);
				}
			}
		});
		
		keypad_0 = new JButton("0");
		keypad_0.setFocusable(false);
		keypad_0.setFont(new Font("Tahoma", Font.PLAIN, 20));
		keypad_0.setBounds(86, 278, 87, 93);
		keypad_panel.add(keypad_0);
		keypad_0.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String i = e.getActionCommand();
				String input = textField_productID_input.getText();
				if(textField_productID_input.hasFocus()){
					textField_productID_input.setText(input+i);
				}
			}
		});
		
		keypad_enter = new JButton("Enter");
		keypad_enter.setFocusable(false);
		keypad_enter.setFont(new Font("Tahoma", Font.PLAIN, 20));
		keypad_enter.setBounds(171, 278, 87, 93);
		keypad_panel.add(keypad_enter);
		StringBuilder sb = new StringBuilder();
		sb.append("Checkout using Cash: " + "\n");
		sb.append("Total: $" + total + "\n");
		sb.append("Cash Tender: $");
		String message = sb.toString();
		
		
		keypad_enter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String i = e.getActionCommand();
				String input = textField_productID_input.getText();
				if(textField_productID_input.hasFocus()){
					textField_productID_input.setText(input+i);
				}
			}
		});
		*/
		/*ActionClass actionEvent = new ActionClass();
		
		keypad_1.addActionListener(actionEvent);
		keypad_2.addActionListener(actionEvent);
		
		keypad_1.setActionCommand("1");
		keypad_2.setActionCommand("2");
		*/
		Reports report_sec = new Reports(); 
		JPanel panel_reports = report_sec.getWindow();
		tabbedPane.addTab("Reports", null, panel_reports, null);
		panel_reports.setLayout(null);
		
		JPanel panel_staff = new JPanel();
		tabbedPane.addTab("Staff", null, panel_staff, null);
		
		JPanel panel_supplier = new JPanel();
		tabbedPane.addTab("Supplier", null, panel_supplier, null);
		
		JPanel panel_inventory = new JPanel();
		tabbedPane.addTab("Inventory", null, panel_inventory, null);
		
		JPanel panel_payments = new JPanel();
		tabbedPane.addTab("Payments", null, panel_payments, null);
		
		JPanel panel_getHelp = new JPanel();
		tabbedPane.addTab("Get Help", null, panel_getHelp, null);
	
	}
	public void setEmployee(Employees e){
		employee = e;
		welcome.setText("Welcome, " + employee.getFirstName() + " " + employee.getLastName());
	}
	
	public void calculateCashCheckout(){
		String cashTenderInput = checkout_amount_tender_input.getText();
		
		if (checkForNumbers(cashTenderInput) == false){
			String invalidInput = "Please enter numbers only.";
			JOptionPane.showMessageDialog(null,invalidInput,"Cash Tender Error",JOptionPane.ERROR_MESSAGE);
			checkout_amount_tender_input.setFocusable(true);
		}
		else if(validateEmpty(cashTenderInput) == false){
			JOptionPane.showMessageDialog(null,"Amount entered cannot be Empty","Error",JOptionPane.ERROR_MESSAGE);
			checkout_amount_tender_input.setFocusable(true);
		}
		else{
			double cashTender = Double.parseDouble(cashTenderInput);
			cashTender = Math.round(cashTender * 100.0) / 100.0;
			
			if(cashTender < total){
				String attention = "The amount entered for cash tendered ($" + cashTender + ") is not enough to cover total amount ($" + total + ").";       
				JOptionPane.showMessageDialog(null,attention,"Cash Tender Error",JOptionPane.ERROR_MESSAGE);
			}
			else if(cashTender >= total){
				double change = 0;
				change = cashTender - total;
				change = Math.round(change * 100.0) / 100.0;
				
				Object[] optionsTransactionCash = {"Complete Transaction"};
				StringBuilder sb2 = new StringBuilder();
				sb2.append("Total: $" + total + "\n");
				sb2.append("Cash Tender: $" + cashTender + "\n");
				sb2.append("Change: $" + change + "\n");
				sb2.append("Printing receipt.");
				String message2 = sb2.toString();
				int cashTransaction = JOptionPane.showOptionDialog(null,message2,"Checkout using Cash",
				JOptionPane.PLAIN_MESSAGE,JOptionPane.PLAIN_MESSAGE,null,optionsTransactionCash,optionsTransactionCash[0]);
			}
			//frame_cashCheckout.dispose();
		}
	}
	
	public void calculateOneTimeDiscount(){
		String discountInput = discount_option.getText();
		
		if(oneTimeDiscountCheck == true){
			JOptionPane.showMessageDialog(null,"One time discount has been already applied for this transaction.");
		}
		else if (validateEmpty(discountInput) == false){
			JOptionPane.showMessageDialog(null,"Value entered cannot be Empty","Error",JOptionPane.ERROR_MESSAGE);
			discount_option.setFocusable(true);
		}
		else{
			Discount oneTimeDiscount = new Discount();
			double previous = subTotal;
			subTotal = oneTimeDiscount.calculateDiscount(subTotal,discountInput,discount);
				
			subTotal = Math.round(subTotal * 100.0) / 100.0;
			subtotal_textField.setText("Subtotal: $" + subTotal);

			tax = subTotal * 0.13;
			tax = Math.round(tax * 100.0) / 100.0;
			tax_textField.setText("Tax: $" + tax);
					
			total = subTotal + tax;
			total = Math.round(total * 100.0) / 100.0;
			total_textField.setText("Total: $" + total);
				
			oneTimeDiscountCheck = true;
				
			if(subTotal < previous){
				textField_discount.setText("Discount: " + oneTimeDiscount.getType() + ": $" + ((previous - subTotal) * 100.0) / 100.0);
			}
		}
		//textField_productID_input.requestFocusInWindow();
		//frame_discount.dispose();
	}
	
	public boolean checkForNumbers(String input){
		String regexStr = "^[0-9]*$";
		if(input.trim().matches(regexStr)){
			return true;
		}
		else{
			return false;
		}
	}
	public boolean validateEmpty(String temp){
		temp = temp.trim();
		if(temp.isEmpty() || temp == null || temp.length() <= 0){
			return false;
		}
		else{
			return true;
		}
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();
		if(keyCode == KeyEvent.VK_BACK_SPACE){
			//KeyboardFocusManager.getCurrentKeyboardFocusManager().getFocusOwner();
			/*Frame.getFocusOwner() (inherited from Window.getFocusOwner()) 
			ought to return a reference to the component with focus.
			getMostRecentFocusOwner() might also be of interest.
			*/
			btnCancelItem.doClick();
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

	@Override
	public void keyTyped(KeyEvent e) {	
	}
	
	public void loadDiscountFrame(){
		JPanel panel_discount = new JPanel();
		panel_discount.setLayout(null);
		
		JButton discount_keypad_1 = new JButton("1");
		discount_keypad_1.setBounds(208, 0, 87, 93);
		discount_keypad_1.setFont(new Font("Tahoma", Font.PLAIN, 20));
		discount_keypad_1.setFocusable(false);
		panel_discount.add(discount_keypad_1);
		discount_keypad_1.addActionListener(new ActionListener() {
		
			@Override
			public void actionPerformed(ActionEvent e) {
				String i = e.getActionCommand();
				String input = discount_option.getText();
				discount_option.setText(input+i);
			}
		});
		
		
		JButton discount_keypad_2 = new JButton("2");
		discount_keypad_2.setBounds(294, 0, 87, 93);
		discount_keypad_2.setFont(new Font("Tahoma", Font.PLAIN, 20));
		discount_keypad_2.setFocusable(false);
		panel_discount.add(discount_keypad_2);
		discount_keypad_2.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String i = e.getActionCommand();
				String input = discount_option.getText();
				discount_option.setText(input+i);
			}
		});
		
		JButton discount_keypad_3 = new JButton("3");
		discount_keypad_3.setBounds(379, 0, 87, 93);
		discount_keypad_3.setFont(new Font("Tahoma", Font.PLAIN, 20));
		discount_keypad_3.setFocusable(false);
		panel_discount.add(discount_keypad_3);
		discount_keypad_3.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String i = e.getActionCommand();
				String input = discount_option.getText();
				discount_option.setText(input+i);
			}
		});
		
		JButton discount_keypad_4 = new JButton("4");
		discount_keypad_4.setBounds(208, 93, 87, 93);
		discount_keypad_4.setFont(new Font("Tahoma", Font.PLAIN, 20));
		discount_keypad_4.setFocusable(false);
		panel_discount.add(discount_keypad_4);
		discount_keypad_4.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String i = e.getActionCommand();
				String input = discount_option.getText();
				discount_option.setText(input+i);
			}
		});
		
		JButton discount_keypad_5 = new JButton("5");
		discount_keypad_5.setBounds(294, 93, 87, 93);
		discount_keypad_5.setFont(new Font("Tahoma", Font.PLAIN, 20));
		discount_keypad_5.setFocusable(false);
		panel_discount.add(discount_keypad_5);
		discount_keypad_5.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String i = e.getActionCommand();
				String input = discount_option.getText();
				discount_option.setText(input+i);
			}
		});
		
		JButton checkout_keypad_6 = new JButton("6");
		checkout_keypad_6.setBounds(379, 93, 87, 93);
		checkout_keypad_6.setFont(new Font("Tahoma", Font.PLAIN, 20));
		checkout_keypad_6.setFocusable(false);
		panel_discount.add(checkout_keypad_6);
		checkout_keypad_6.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String i = e.getActionCommand();
				String input = discount_option.getText();
				discount_option.setText(input+i);
			}
		});
		
		JButton discount_keypad_7 = new JButton("7");
		discount_keypad_7.setBounds(208, 186, 87, 93);
		discount_keypad_7.setFont(new Font("Tahoma", Font.PLAIN, 20));
		discount_keypad_7.setFocusable(false);
		panel_discount.add(discount_keypad_7);
		discount_keypad_7.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String i = e.getActionCommand();
				String input = discount_option.getText();
				discount_option.setText(input+i);
			}
		});
		
		JButton discount_keypad_8 = new JButton("8");
		discount_keypad_8.setBounds(294, 186, 87, 93);
		discount_keypad_8.setFont(new Font("Tahoma", Font.PLAIN, 20));
		discount_keypad_8.setFocusable(false);
		panel_discount.add(discount_keypad_8);
		discount_keypad_8.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String i = e.getActionCommand();
				String input = discount_option.getText();
				discount_option.setText(input+i);
			}
		});
		
		JButton discount_keypad_9 = new JButton("9");
		discount_keypad_9.setBounds(379, 186, 87, 93);
		discount_keypad_9.setFont(new Font("Tahoma", Font.PLAIN, 20));
		discount_keypad_9.setFocusable(false);
		panel_discount.add(discount_keypad_9);
		discount_keypad_9.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String i = e.getActionCommand();
				String input = discount_option.getText();
				discount_option.setText(input+i);
			}
		});
		
		
		JButton discount_keypad_decimal = new JButton(".");
		discount_keypad_decimal.setBounds(208, 278, 87, 93);
		discount_keypad_decimal.setFont(new Font("Tahoma", Font.PLAIN, 20));
		discount_keypad_decimal.setFocusable(false);
		panel_discount.add(discount_keypad_decimal);
		discount_keypad_decimal.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String i = e.getActionCommand();
				String input = discount_option.getText();
				discount_option.setText(input+i);
			}
		});
		
		JButton discount_keypad_0 = new JButton("0");
		discount_keypad_0.setBounds(294, 278, 87, 93);
		discount_keypad_0.setFont(new Font("Tahoma", Font.PLAIN, 20));
		discount_keypad_0.setFocusable(false);
		panel_discount.add(discount_keypad_0);
		discount_keypad_0.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String i = e.getActionCommand();
				String input = discount_option.getText();
				discount_option.setText(input+i);
			}
		});
		
		JButton discount_keypad_enter = new JButton("Enter");
		discount_keypad_enter.setBounds(379, 278, 87, 93);
		discount_keypad_enter.setFont(new Font("Tahoma", Font.PLAIN, 20));
		discount_keypad_enter.setFocusable(false);
		panel_discount.add(discount_keypad_enter);
		discount_keypad_enter.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				calculateOneTimeDiscount();
			}
		});
		
		JTextPane discount_message1 = new JTextPane();
		discount_message1.setFocusable(false);
		
		discount_message1.setBounds(6, 6, 170, 95);
		discount_message1.setBackground(Color.decode(defaultColor));
		panel_discount.add(discount_message1);

		discount_option = new JTextField();
		discount_option.setBounds(6, 104, 190, 26);
		panel_discount.add(discount_option);
		discount_option.setColumns(10);
		discount_option.setFocusable(true);
		
		JButton discountOption_btnEnter = new JButton("Enter");
		discountOption_btnEnter.setBounds(6, 132, 190, 29);
		panel_discount.add(discountOption_btnEnter);
		discountOption_btnEnter.requestFocusInWindow();
		discountOption_btnEnter.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				calculateOneTimeDiscount();
			}
		});
		
		frame_discount = new JFrame();
		frame_discount.getContentPane().add(panel_discount);
		//frame_discount.setSize(w/2, h/2);
		frame_discount.setSize(475, 400);
		frame_discount.setVisible(true);
		frame_discount.setLocationRelativeTo(null);
		
		//selects default button
		frame_discount.getRootPane().setDefaultButton(discountOption_btnEnter);
		
		//setEnabled(false);
		//panel_discount.setEnabled(true);
		
		Discount d = new Discount();
		int count = d.getPromotionCount();
		discount = new Vector<Discount>();
		for(int i = 1; i <= count; i++){
			Discount temp = new Discount();
			temp.getPromotion(i);
			discount.add(temp);
		}
		String[] options = new String[count];
		for(int i = 0; i < count; i++){
			if(discount.get(i).getDiscountValue() > 0){
				options[i] = i+1 + ".) " + discount.get(i).getType() + discount.get(i).getDiscountValue();
			}
			else if(discount.get(i).getDiscountPercent() > 0){
				options[i] = i+1 + ".) " + discount.get(i).getDiscountPercent() + discount.get(i).getType();
			}
		}
		StringBuilder sb1 = new StringBuilder();
		sb1.append("Current promotions: " + "\n");
		for(int i = 0; i < count; i++){
			sb1.append(options[i] + "\n");
		}
		String toDisplay=sb1.toString();
		discount_message1.setText(toDisplay);
	}
	
	public void loadCheckoutFrame(){
		JPanel panel_checkout = new JPanel();
		panel_checkout.setLayout(null);
		
		JButton checkout_keypad_1 = new JButton("1");
		checkout_keypad_1.setBounds(208, 0, 87, 93);
		checkout_keypad_1.setFont(new Font("Tahoma", Font.PLAIN, 20));
		checkout_keypad_1.setFocusable(false);
		panel_checkout.add(checkout_keypad_1);
		checkout_keypad_1.addActionListener(new ActionListener() {
		
			@Override
			public void actionPerformed(ActionEvent e) {
				String i = e.getActionCommand();
				String input = checkout_amount_tender_input.getText();
				checkout_amount_tender_input.setText(input+i);
			}
		});
		
		
		JButton checkout_keypad_2 = new JButton("2");
		checkout_keypad_2.setBounds(294, 0, 87, 93);
		checkout_keypad_2.setFont(new Font("Tahoma", Font.PLAIN, 20));
		checkout_keypad_2.setFocusable(false);
		panel_checkout.add(checkout_keypad_2);
		checkout_keypad_2.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String i = e.getActionCommand();
				String input = checkout_amount_tender_input.getText();
				checkout_amount_tender_input.setText(input+i);
			}
		});
		
		JButton checkout_keypad_3 = new JButton("3");
		checkout_keypad_3.setBounds(379, 0, 87, 93);
		checkout_keypad_3.setFont(new Font("Tahoma", Font.PLAIN, 20));
		checkout_keypad_3.setFocusable(false);
		panel_checkout.add(checkout_keypad_3);
		checkout_keypad_3.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String i = e.getActionCommand();
				String input = checkout_amount_tender_input.getText();
				checkout_amount_tender_input.setText(input+i);
			}
		});
		
		JButton checkout_keypad_4 = new JButton("4");
		checkout_keypad_4.setBounds(208, 93, 87, 93);
		checkout_keypad_4.setFont(new Font("Tahoma", Font.PLAIN, 20));
		checkout_keypad_4.setFocusable(false);
		panel_checkout.add(checkout_keypad_4);
		checkout_keypad_4.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String i = e.getActionCommand();
				String input = checkout_amount_tender_input.getText();
				checkout_amount_tender_input.setText(input+i);
			}
		});
		
		JButton checkout_keypad_5 = new JButton("5");
		checkout_keypad_5.setBounds(294, 93, 87, 93);
		checkout_keypad_5.setFont(new Font("Tahoma", Font.PLAIN, 20));
		checkout_keypad_5.setFocusable(false);
		panel_checkout.add(checkout_keypad_5);
		checkout_keypad_5.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String i = e.getActionCommand();
				String input = checkout_amount_tender_input.getText();
				checkout_amount_tender_input.setText(input+i);
			}
		});
		
		JButton checkout_keypad_6 = new JButton("6");
		checkout_keypad_6.setBounds(379, 93, 87, 93);
		checkout_keypad_6.setFont(new Font("Tahoma", Font.PLAIN, 20));
		checkout_keypad_6.setFocusable(false);
		panel_checkout.add(checkout_keypad_6);
		checkout_keypad_6.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String i = e.getActionCommand();
				String input = checkout_amount_tender_input.getText();
				checkout_amount_tender_input.setText(input+i);
			}
		});
		
		JButton checkout_keypad_7 = new JButton("7");
		checkout_keypad_7.setBounds(208, 186, 87, 93);
		checkout_keypad_7.setFont(new Font("Tahoma", Font.PLAIN, 20));
		checkout_keypad_7.setFocusable(false);
		panel_checkout.add(checkout_keypad_7);
		checkout_keypad_7.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String i = e.getActionCommand();
				String input = checkout_amount_tender_input.getText();
				checkout_amount_tender_input.setText(input+i);
			}
		});
		
		JButton checkout_keypad_8 = new JButton("8");
		checkout_keypad_8.setBounds(294, 186, 87, 93);
		checkout_keypad_8.setFont(new Font("Tahoma", Font.PLAIN, 20));
		checkout_keypad_8.setFocusable(false);
		panel_checkout.add(checkout_keypad_8);
		checkout_keypad_8.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String i = e.getActionCommand();
				String input = checkout_amount_tender_input.getText();
				checkout_amount_tender_input.setText(input+i);
			}
		});
		
		JButton checkout_keypad_9 = new JButton("9");
		checkout_keypad_9.setBounds(379, 186, 87, 93);
		checkout_keypad_9.setFont(new Font("Tahoma", Font.PLAIN, 20));
		checkout_keypad_9.setFocusable(false);
		panel_checkout.add(checkout_keypad_9);
		checkout_keypad_9.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String i = e.getActionCommand();
				String input = checkout_amount_tender_input.getText();
				checkout_amount_tender_input.setText(input+i);
			}
		});
		
		
		JButton checkout_keypad_decimal = new JButton(".");
		checkout_keypad_decimal.setBounds(208, 278, 87, 93);
		checkout_keypad_decimal.setFont(new Font("Tahoma", Font.PLAIN, 20));
		checkout_keypad_decimal.setFocusable(false);
		panel_checkout.add(checkout_keypad_decimal);
		checkout_keypad_decimal.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String i = e.getActionCommand();
				String input = checkout_amount_tender_input.getText();
				checkout_amount_tender_input.setText(input+i);
			}
		});
		
		JButton checkout_keypad_0 = new JButton("0");
		checkout_keypad_0.setBounds(294, 278, 87, 93);
		checkout_keypad_0.setFont(new Font("Tahoma", Font.PLAIN, 20));
		checkout_keypad_0.setFocusable(false);
		panel_checkout.add(checkout_keypad_0);
		checkout_keypad_0.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String i = e.getActionCommand();
				String input = checkout_amount_tender_input.getText();
				checkout_amount_tender_input.setText(input+i);
			}
		});
		
		JButton checkout_keypad_enter = new JButton("Enter");
		checkout_keypad_enter.setBounds(379, 278, 87, 93);
		checkout_keypad_enter.setFont(new Font("Tahoma", Font.PLAIN, 20));
		checkout_keypad_enter.setFocusable(false);
		panel_checkout.add(checkout_keypad_enter);
		
		checkout_keypad_enter.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				calculateCashCheckout();
			}
		});
		
		JTextPane checkout_message1 = new JTextPane();
		StringBuilder sb = new StringBuilder();
		sb.append("Checkout using Cash: " + "\n");
		sb.append("Total: $" + total + "\n");
		sb.append("Cash Tender: $");
		String message = sb.toString();
		checkout_message1.setText(message);
		checkout_message1.setFocusable(false);
		
		checkout_message1.setBounds(6, 6, 190, 50);
		checkout_message1.setBackground(Color.decode(defaultColor));
		panel_checkout.add(checkout_message1);

		checkout_amount_tender_input = new JTextField();
		checkout_amount_tender_input.setBounds(6, 56, 190, 26);
		panel_checkout.add(checkout_amount_tender_input);
		checkout_amount_tender_input.setColumns(10);
		checkout_amount_tender_input.setFocusable(true);
		
		JButton checkoutCash_btnEnter = new JButton("Enter");
		checkoutCash_btnEnter.setBounds(6, 82, 190, 29);
		panel_checkout.add(checkoutCash_btnEnter);
		checkoutCash_btnEnter.requestFocusInWindow();
		checkoutCash_btnEnter.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				calculateCashCheckout();
			}
		});
		
		frame_cashCheckout = new JFrame();
		frame_cashCheckout.getContentPane().add(panel_checkout);
		//frame_cashCheckout.setSize(w/2, h/2);
		frame_cashCheckout.setSize(475, 400);
		frame_cashCheckout.setVisible(true);
		frame_cashCheckout.setLocationRelativeTo(null);
		
		//setEnabled(false);
		//frame_cashCheckout.setEnabled(true);
		
		//selects default button
		frame_cashCheckout.getRootPane().setDefaultButton(checkoutCash_btnEnter);
	}
	public void calculateSubtotal(){
		double sub = 0;
		for(int i = 0; i < model.getRowCount(); i++){
			sub += Double.parseDouble(model.getValueAt(i, productQuantityAndPrice_column).toString());
		}
		subTotal = sub;
		subTotal = Math.round(subTotal * 100.0) / 100.0;
		subtotal_textField.setText("Subtotal: $" + subTotal);
		
		tax = subTotal * 0.13;
		tax = Math.round(tax * 100.0) / 100.0;
		tax_textField.setText("Tax: $" + tax);
			
		total = subTotal + tax;
		total = Math.round(total * 100.0) / 100.0;
		total_textField.setText("Total: $" + total);	
	}
	public void calculateInventoryQuantity(){
		if(model.getRowCount() > 0 && productBySearch.size() > 0 && !table.isEditing()){
			for(int i = 0; i < model.getRowCount(); i++){
				for(int j = 0; j < productBySearch.size(); j++){
					int proID = (int) model.getValueAt(i, id_column);
					if(proID == productBySearch.get(j).getID()){
						//System.out.println(productBySearch.get(j).getQuantity());
						/*if(productBySearch.get(j).getQuantity() > 0){
							//System.out.println("Above 0");
							int onHand = productBySearch.get(j).getQuantity();
							
							String tempNeed = model.getValueAt(i, productQuantity_column).toString(); 
							int need = Integer.parseInt(tempNeed);
							
							int difference = need - previousValue.get(j);
							int newQuantity = onHand - difference;
							productBySearch.get(j).setQuantity(newQuantity);
							
							previousValue.remove(j);
							previousValue.insertElementAt(need, j);
							
							System.out.println("After: " + productBySearch.get(j).getQuantity());
						}
						else{
							//System.out.println("Quantity has reached under 0");
							//productBySearch.get(j).setQuantity(0);
							
							//System.out.println("Product Quantity: " + productBySearch.get(j).getQuantity());
							//System.out.println("PreviousValue: " + previousValue.get(j).toString());
						}*/
							
							int onHand = productBySearch.get(j).getQuantity();
							
							String tempNeed = model.getValueAt(i, productQuantity_column).toString(); 
							int need = Integer.parseInt(tempNeed);
							
							int difference = need - previousValue.get(j);
							int newQuantity = onHand - difference;
							productBySearch.get(j).setQuantity(newQuantity);
							
							previousValue.remove(j);
							previousValue.insertElementAt(need, j);
							
							int row = table.getSelectionModel().getLeadSelectionIndex();
							textField_name_input.setText(productBySearch.get(row).getName());
							textField_price_input.setText("$" + String.valueOf(productBySearch.get(row).getSalePrice()));		
							textField_quantity_input.setText(String.valueOf(productBySearch.get(row).getQuantity()));
							break;	
					}	
				}
			}
		}
	}
}