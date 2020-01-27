package userenergyinterfacegui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.Document;


/**
 *
 * @author chiamakaibeme
 */
public class UserEnergyInterfaceGUI extends JFrame {
    private final static String password= "1234";
    
    private JPanel topPanel; 
    private JPanel topPanelOne;
    private JPanel topPanelTwo;
    private JPanel topPanelThree;
        
    private JPanel middlePanel;
    private JPanel bottomPanel;
    
    private final JLabel nameLabel;
    private final JLabel pwordLabel;
    private final JLabel heightLabel;
    private final JLabel weightLabel;
    private final JLabel genderLabel;
    private final JLabel palLabel;
    private final JLabel ageGroupLabel;
   
    
    private final JTextField nameField;
    private final JTextField heightField;
    double htVal;
    private final JTextField weightField;
    double wtVal;
    private final JTextField palValue;
    
    private final JPasswordField pwordField;
    
    private final JComboBox <String> palComboBox;
    private static String[] palDescription = {" "};
    String palDesc;
    
    private final JComboBox <String> ageGroupComboBox;
    private static String[] ageGroupOpt = {" "};
    String ageGp;
    
    private final JRadioButton maleRadio;
    private final JRadioButton femaleRadio;
    private final ButtonGroup radioGroup;
    String genderSel;
    
    private final JButton loginButton;
    private final JButton loadButton;
    private final JButton displayButton;
    private final JButton saveButton;
    private final JButton displayAllButton;
    private final JButton clearButton;
    private final JButton quitButton;
    
    private final JTextArea textDisplayArea;
    
    private String[][] fileData;
    private String[] eachCol;
    
    EnergyData[] edList;
    
    
    
    
    
    // constructor sets up GUI
    public UserEnergyInterfaceGUI(){

        super ("User Energy GUI");
        setLayout(new BorderLayout());
        
        //Build top panel
        
        topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout());
        
        topPanelOne = new JPanel();
        topPanelTwo = new JPanel();
        topPanelThree = new JPanel();
        
        topPanel.add( topPanelOne, BorderLayout.NORTH );
        topPanel.add( topPanelTwo, BorderLayout.CENTER );
        topPanel.add( topPanelThree, BorderLayout.SOUTH );
        
        //First row components
        nameLabel = new JLabel("Username: ");
        nameField = new JTextField(10);
        pwordLabel = new JLabel("Password: ");
        pwordField = new JPasswordField(10);
        loginButton = new JButton("Login");
        
        //Populate first row of the top panel
        topPanelOne.add(nameLabel);
        topPanelOne.add(nameField);
        topPanelOne.add(pwordLabel);
        topPanelOne.add(pwordField);
        topPanelOne.add(loginButton);
        
        
        //Second row components
        heightLabel = new JLabel("Height: ");
        heightField = new JTextField(10);
        
        weightLabel = new JLabel("Weight: ");
        weightField = new JTextField(10);
        weightField.setEditable(false); // disable editing
                
        genderLabel = new JLabel("Gender:");
        
        maleRadio = new JRadioButton("Male", false);
        femaleRadio = new JRadioButton("Female", false);
        
        
        
        //create logical relationships between JRadioButtons
        radioGroup = new ButtonGroup(); 
        radioGroup.add(maleRadio);
        radioGroup.add(femaleRadio);
        
        
        //Populate second row of the top panel 
        topPanelTwo.add(heightLabel);
        topPanelTwo.add(heightField);
        topPanelTwo.add(weightLabel);
        topPanelTwo.add(weightField);
        topPanelTwo.add(genderLabel);
        topPanelTwo.add(maleRadio);
        topPanelTwo.add(femaleRadio);
               
        
        //Third row components
        palLabel = new JLabel("PAL: ");
        palComboBox = new JComboBox<>(palDescription);
        palValue = new JTextField(10);
        palValue.setEditable(false); //disable editing
        ageGroupLabel = new JLabel("Age group: ");
        ageGroupComboBox = new JComboBox(ageGroupOpt);
                
        //Populate third row of the top panel 
        topPanelThree.add(palLabel);
        topPanelThree.add(palComboBox);
        topPanelThree.add(palValue);
        topPanelThree.add(ageGroupLabel);
        topPanelThree.add(ageGroupComboBox);
        
        
        //Build middle panel
        textDisplayArea = new JTextArea(25, 40);
        
        //initialize and populate middle panel
        middlePanel = new JPanel();
        middlePanel.add(textDisplayArea);
        
        
        
        //Build bottom panel
        loadButton = new JButton("Load Data");
        loadButton.setEnabled(false);
        displayButton = new JButton("Display Energy");
        saveButton = new JButton("Save");
        displayAllButton = new JButton("Display All");
        clearButton = new JButton("Clear Display");
        quitButton = new JButton("Quit");
        
        //initialize and populate bottom panel
        bottomPanel = new JPanel();
        bottomPanel.add(loadButton);
        bottomPanel.add(displayButton);
        bottomPanel.add(saveButton);
        bottomPanel.add(displayAllButton);
        bottomPanel.add(clearButton);
        bottomPanel.add(quitButton);
        
        
        // add components to (default) border layout
        add( topPanel, BorderLayout.NORTH );
        add( middlePanel, BorderLayout.CENTER );
        add( bottomPanel, BorderLayout.SOUTH );
        
        
        //listen for Login
        loginButton.addActionListener(new ActionListener() {
            
            public void actionPerformed(ActionEvent event) {
                
                String pwd = new String(pwordField.getPassword());
//                String userN = nameField.getText().toLowerCase();
                // Check user login
                if (pwd.compareTo(password) == 0) /*&& userN.compareTo(username.toLowerCase()) == 0)*/ {
                    loadButton.setEnabled(true);
                    nameField.setText("");
                    pwordField.setText("");
                } 
            }
        });
        
        //listen for height input
        Document doc = heightField.getDocument();
        doc.addDocumentListener(new DocumentListener() {
            
            public void insertUpdate(DocumentEvent e) {
                if (heightField.getText().equals("")) {
                    return;
                }

                htVal = Double.parseDouble(heightField.getText());
                wtVal = 0.0;
                
                for (EnergyData ed : edList) {
                    if (htVal == ed.getBMRdata().getHeight()) {
                        wtVal = ed.getBMRdata().getWeight();
                        break;
                    }
                }
                weightField.setText(Double.toString(wtVal));
            }

            
            public void removeUpdate(DocumentEvent e) {
                if (heightField.getText().equals("")) {
                    return;
                }

                htVal = Double.parseDouble(heightField.getText());

                wtVal = 0.0;
                for (EnergyData ed : edList) {
                    if (htVal == ed.getBMRdata().getHeight()) {
                        wtVal = ed.getBMRdata().getWeight();
                        break;
                    }
                }
                weightField.setText(Double.toString(wtVal));
            }

            
            public void changedUpdate(DocumentEvent e) {
                if (heightField.getText().equals("")) {
                    return;
                }

                htVal = Double.parseDouble(heightField.getText());

                wtVal = 0.0;
                for (EnergyData ed : edList) {
                    if (htVal == ed.getBMRdata().getHeight()) {
                        wtVal = ed.getBMRdata().getWeight();
                        break;
                    }
                }
                weightField.setText(Double.toString(wtVal));
            }
        });
        
        //Listen for selection of male radio button
        maleRadio.addActionListener(new ActionListener(){

            public void actionPerformed(ActionEvent e) {
                //set state of radio buttons
                maleRadio.setSelected(true);
                femaleRadio.setSelected(false);
                genderSel = "Male";
            }
        });
        
        //Listen for selection of female radio button
        femaleRadio.addActionListener(new ActionListener() {
            
            public void actionPerformed(ActionEvent e) {
                //set state of radio buttons
                femaleRadio.setSelected(true);
                maleRadio.setSelected(false);
                genderSel = "Female";
            }
        });
        
        //Listen for selected PAL description
        palComboBox.addItemListener(new ItemListener() {
            
            public void itemStateChanged(ItemEvent e) {
                for (PAL temp : PAL.values()) {
                    if (temp.getDescription().equalsIgnoreCase(e.getItem().toString())) {
                        palValue.setText(temp.getValue());
                    }
                }
            }
        });
        
        
        //Listen for selection of Age group
        ageGroupComboBox.addItemListener(new ItemListener() { //anonymous inner class
            
            //handle combo box event
            public void itemStateChanged(ItemEvent e){
                
                //determine whether item is selected
                if(e.getStateChange() == ItemEvent.SELECTED){
                    if(ageGroupComboBox.getSelectedIndex() == 0){
                        ageGp = ageGroupOpt[0];
                    } else if(ageGroupComboBox.getSelectedIndex() == 1){
                        ageGp = ageGroupOpt[1];
                    } 
                }
            }
        }); //end anonymous inner class call to addItemListener
                
        
        //listen for load button click
        loadButton.addActionListener(new ActionListener(){
            
            public void actionPerformed(ActionEvent e){
                readFileAndLoadData();
                populateComboBox();
            }
        });
        
        
        
        displayButton.addActionListener(new ActionListener() {
            
            public void actionPerformed(ActionEvent event) {
                if (heightField.getText().equals("")) {
                   textDisplayArea.setText("Please input height between 1.5 and 2.0");
                    return;
                }
                PALdata pd = null;
                double bmr = 0.0;
                // search the energy need
                for (EnergyData ed : edList) {
                    if (ed.getBMRdata().getHeight() == Double.parseDouble(heightField.getText()) && ageGroupComboBox.getSelectedItem().toString().equals(ed.getBMRdata().getAgeGroup()) && genderSel.equalsIgnoreCase(ed.getBMRdata().getGender())) {
                        bmr = ed.getBMRdata().getBMR();
                        for (PALdata p : ed.getPalData()) {
                            if (p.getPal().getDescription().equalsIgnoreCase(palComboBox.getSelectedItem().toString())) {
                                pd = p;
                                break;
                            }
                        }
                    }
                }
                if (pd == null) {
                    textDisplayArea.setText("No result to display.");
                    return;
                }
                try {
                    UserEnergyNeeds userEnergy = new UserEnergyNeeds(nameField.getText(), genderSel, ageGp, Double.parseDouble(heightField.getText()), Double.parseDouble(weightField.getText()), bmr, pd);
                    
                    String display = "A " + userEnergy.getGenderInput() + " between the ages of " + userEnergy.getAgeGroupInput() + " \nwith height of " +
                            userEnergy.getHeightInput() + " and a weight of " + userEnergy.getWeight() + " \nand has a physical activity level description and value of \n" +
                            pd.getPal().getDescription() + " and " + pd.getPal().getValue() + " respectively, will require energy of " + userEnergy.getPalData().getEnergy() + " joules.";
                     
                                
                    
                    textDisplayArea.setText(display);
                    
                } catch (IllegalArgumentException e) {
                    textDisplayArea.setText("Error! check values.");
                }
            }
        });
        
        //listen for clear button click
        clearButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent event) {
                heightField.setText("");
                weightField.setText("");
                textDisplayArea.setText("");
                palComboBox.setSelectedIndex(0);
                ageGroupComboBox.setSelectedIndex(0);
                maleRadio.setSelected(false);
                femaleRadio.setSelected(false);
            }
        });
        
        //Listen for quit button click
        quitButton.addActionListener(new ActionListener() {
            
            public void actionPerformed(ActionEvent event) {
                System.exit(0); //exit application
            }
        });
        
        
        
   
    }
       
    public void populateComboBox(){
        //populate PAL combo box
        
        ArrayList <String> palDesc = new ArrayList<>();//array list to store pal descriptions
        
                
        for (PAL pal : PAL.values()){
            palDesc.add(pal.getDescription());
            
            
        }
        //convert pal description array list to arrays
        palDescription = palDesc.toArray(new String[palDesc.size()]);
        
        for (String Desc : palDescription) {
            palComboBox.addItem(Desc);
        }
        
        
        //populate Age group combo box
        ArrayList <String> ageVal = new ArrayList<>();//array list to store age groups
        
        
        for(int i = 1; i < fileData.length; i++){
            ageVal.add(fileData[i][0]);
        }
        
        //convert to arrays
        ageGroupOpt = ageVal.toArray(new String[ageVal.size()]);
        
        
        ageGroupComboBox.addItem(ageGroupOpt[0]);
        //sort age group to avoid repeated values in the drop down list
        for(int i = 1; i < ageGroupOpt.length; i++){
            if(!ageGroupOpt[i].equals(ageGroupOpt[i - 1])){
                
                ageGroupComboBox.addItem(ageGroupOpt[i]);
            }
                
        }
        
    }
    
    public void readFileAndLoadData(){
        //parameters for BMRdata
        BMRdata bmrDataObj;
        
        //parameters for PALdata
        PALdata palDataObj;
        ArrayList<String> descCol = new ArrayList<>();
        
        //dynamic arrays used
        ArrayList<String> data = new ArrayList<>();
        ArrayList<String[]> dataArr = new ArrayList<>();
        ArrayList<PALdata> palDataList = new ArrayList<>();
        ArrayList<EnergyData> energyData = new ArrayList<>();

        
        
        try{
            
            Scanner fileInput = new Scanner(new FileReader("/Users/chiamakaibeme/Desktop/COIT20256Ass1Data.csv"));//read file
            
            fileInput.nextLine();
            while(fileInput.hasNextLine()){
                
                
                data.add(fileInput.nextLine());       
            }//end of while loop
            
            fileInput.close();//close file
            
            
            //convert ArrayList of strings to Arraylist of Arrays
            for(int i = 0; i < data.size(); i++){
                String[] rec = data.get(i).split(",");
                dataArr.add(rec);
            }
            
            //convert arraylist to array 
            fileData = dataArr.toArray(new String[dataArr.size()][]);
            
            
            for(int i = 0; i < fileData.length; i++){
                eachCol = fileData[i]; //set variable to get each column
                
                if(i == 0){
                    // set variable keys to get description
                    for(int j = 0; j < eachCol.length; j++){
                        descCol.add(eachCol[j]);
                    }
                    continue;
                        
                }

                //add male bmr object values
                bmrDataObj = new BMRdata(eachCol[0], "Male", Double.parseDouble(eachCol[1]), Double.parseDouble(eachCol[2]), Double.parseDouble(eachCol[3]));
                
                
                //add pal data for male
                for(int k = 4; k < 10; k++){
                    palDataObj = new PALdata(descCol.get(k), Float.parseFloat(eachCol[k]));
                    palDataList.add(palDataObj);
                }
                
                energyData.add(new EnergyData(bmrDataObj, palDataList));
                
                
                
                //add female bmr object values
                bmrDataObj = new BMRdata(eachCol[0], "Female", Double.parseDouble(eachCol[1]), Double.parseDouble(eachCol[2]), Double.parseDouble(eachCol[10]));
                
                
                //add pal data for female
                for(int k = 11; k < 17; k++){
                    palDataObj = new PALdata(descCol.get(k), Float.parseFloat(eachCol[k]));
                    palDataList.add(palDataObj);
                }
                
                energyData.add(new EnergyData(bmrDataObj, palDataList));
                
            }
            
             edList = energyData.toArray(new EnergyData[energyData.size()]);
            
            
//                System.out.println(edList[0].getPalData());
//                System.out.println(edList[0].getPalData());
            
            


        } catch(IOException e){
           
           
           System.out.println("File loading failed");
           
           System.out.println("EXCEPTION: " + e);
    
        } 
         
    }
    
    
    public static void main(String[] args) {
        UserEnergyInterfaceGUI Interface = new UserEnergyInterfaceGUI();
        Interface.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        Interface.setSize( 700, 500 ); // set frame size
        Interface.setVisible(true); //display frame
        
        DatabaseUtility users = new DatabaseUtility();
        users.createDBtables();
//        ReadFileAndLoadData();
    }

   
    
}
