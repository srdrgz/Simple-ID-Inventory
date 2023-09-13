


import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.util.*;

public class CE203_1905888_Ass1 extends JFrame {
    JPanel panelDisplay = new JPanel();
    JTextField input, remove, red, blue, green;
    ArrayList<Integer> arrayOfIDs = new ArrayList<>();
    public CE203_1905888_Ass1() throws ParseException {
        //Modify window
        setTitle("IDs INVENTORY");
        setSize(600,500);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        //Create panel where adding ids and rgb colors are
        JPanel panel1 = new JPanel();
        JButton butAddID = new JButton("Add ID");
        JLabel labelAdd = new JLabel("Enter ID to add:");
        butAddID.setBackground(Color.green);
        input = new JFormattedTextField(new MaskFormatter("******")); //only allows 6 digits to input
        JLabel colors = new JLabel("Enter RGB values:");
        red = new JTextField(2);
        blue = new JTextField(2);
        green = new JTextField(2);

        panel1.add(labelAdd);
        panel1.add(input);
        panel1.add(butAddID);
        panel1.add(colors);
        panel1.add(red);
        panel1.add(green);
        panel1.add(blue);

        //Create buttons panel
        JPanel panel2 = new JPanel();
        JButton butRemoveID = new JButton("Remove ID");
        butRemoveID.setBackground(Color.orange);
        JLabel labelRemove = new JLabel("Enter ID to remove:");
        remove = new JFormattedTextField(new MaskFormatter("******"));
        JButton butDisplayIDs = new JButton("Display IDs");
        JButton butSortIDs = new JButton("Sort IDs");
        JButton butClearList = new JButton("Clear All");

        panel2.add(butRemoveID);
        panel2.add(labelRemove);
        panel2.add(remove);
        panel2.add(butRemoveID);
        panel2.add(butDisplayIDs);
        panel2.add(butSortIDs);
        panel2.add(butClearList);

        //Add panels to frame
        add(panel1, BorderLayout.NORTH);
        add(panel2, BorderLayout.SOUTH);
        add(panelDisplay, BorderLayout.CENTER);

        //Add action listeners
        butAddID.addActionListener(new ButtonHandler(this, 1) );
        butDisplayIDs.addActionListener(new ButtonHandler(this,2));
        butRemoveID.addActionListener(new ButtonHandler(this,3));
        butSortIDs.addActionListener(new ButtonHandler(this,4));
        butClearList.addActionListener(new ButtonHandler(this,5));
    }

    public static void main(String[] args) throws ParseException {
        CE203_1905888_Ass1 frame = new CE203_1905888_Ass1();
        frame.setVisible(true);
    }
}
class ButtonHandler implements ActionListener {
    CE203_1905888_Ass1 theApp;
    int action;

    ButtonHandler(CE203_1905888_Ass1 theApp, int action) {
        this.theApp = theApp;
        this.action = action;
    }
    public int validateColor(String color){
        int x;
        try {
            int validColor = Integer.parseInt(color);
            if (0 <= validColor && 255 >= validColor) {
                x = 1;
            } else {
                x = 0;
            }
        } catch (NumberFormatException ex) {
            x = 0;
        }
        return x;
    }
    public void DisplayIDs(ArrayList<Integer> myArray) {
        String rInput = theApp.red.getText();
        String gInput = theApp.green.getText();
        String bInput = theApp.blue.getText();

        validateColor(rInput);
        validateColor(gInput);
        validateColor(bInput);
        this.theApp.panelDisplay.removeAll();
        this.theApp.panelDisplay.repaint();
        this.theApp.panelDisplay.setLayout(new GridLayout(myArray.size(), 1, 1, 2));
        if (validateColor(rInput) == 0 || validateColor(gInput) == 0 && validateColor(bInput) == 0 ){
            JOptionPane.showMessageDialog(theApp, "This is not a valid color", "Adding color", JOptionPane.INFORMATION_MESSAGE);
        }
        for (int i = 0; i < myArray.size(); i++) {
            JTextField txt = new JTextField();
            if (validateColor(rInput) == 1 && validateColor(gInput) == 1 && validateColor(bInput) == 1 ){
                txt.setBackground(new Color(Integer.parseInt(rInput),Integer.parseInt(gInput),Integer.parseInt(bInput)));
            }
            else{
                txt.setBackground(Color.BLACK);
            }
            txt.setEditable(false);
            txt.setText(Integer.toString(myArray.get(i)));
            this.theApp.panelDisplay.add(txt);
            this.theApp.panelDisplay.validate(); //show all components of panel
        }
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        //Adds an id input by the user to the arrayList.
        if (this.action == 1) {
            String newID = theApp.input.getText();
            try {
                int validID = Integer.parseInt(newID);
                if (validID <= 0) {
                    JOptionPane.showMessageDialog(theApp, newID + " is not a valid ID", "Adding ID", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(theApp, "ID added successfully", "Adding ID", JOptionPane.INFORMATION_MESSAGE);
                    ID2 ID = new ID2(validID);
                    ID.setID(validID);
                    this.theApp.arrayOfIDs.add(ID.id);
                    //DisplayIDs(this.theApp.arrayOfIDs);
                }

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(theApp, newID + " is not a valid ID", "Adding ID", JOptionPane.INFORMATION_MESSAGE);
            }
            theApp.input.setText(null);
        }
        //Displays all ids currently existing in the arrayList
        if (this.action == 2) {
            DisplayIDs(this.theApp.arrayOfIDs);
        }
        //Removes of the arrayList all occurrences of id input by user and displays IDs again
        if (this.action == 3) {
            String removeID = theApp.remove.getText();
            try {
                int validID = Integer.parseInt(removeID);
                if (validID <= 0) {
                    JOptionPane.showMessageDialog(theApp, "ID " + removeID + " was not removed as it is not a valid ID", "Removing ID", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    for (int k = 0; k < this.theApp.arrayOfIDs.size();k++) {
                        if (validID == this.theApp.arrayOfIDs.get(k)) {
                            this.theApp.arrayOfIDs.removeAll(Collections.singleton(this.theApp.arrayOfIDs.get(k)));
                        }
                    }
                    DisplayIDs(this.theApp.arrayOfIDs);
                    JOptionPane.showMessageDialog(theApp, "All instances of the the ID "+ removeID + " have been removed", "Removing ID", JOptionPane.INFORMATION_MESSAGE);
                }

            }
            catch(NumberFormatException ex){
                JOptionPane.showMessageDialog(theApp, "ID " + removeID + " was not removed as it is not a valid ID", "Removing ID", JOptionPane.INFORMATION_MESSAGE);
            }
            theApp.remove.setText(null);
        }

        //Sort array of IDs in ascending order and display again
        if (this.action == 4){
            Collections.sort(this.theApp.arrayOfIDs);
            DisplayIDs(this.theApp.arrayOfIDs);
            JOptionPane.showMessageDialog(theApp, "The IDs have been sorted in ascending order", "Sorting IDs", JOptionPane.INFORMATION_MESSAGE);
        }
        //Remove all IDs from array and display again
        if (this.action == 5){
            this.theApp.arrayOfIDs.clear();
            this.theApp.panelDisplay.removeAll();
            this.theApp.panelDisplay.repaint();
            JOptionPane.showMessageDialog(theApp, "The list of IDs has been cleared successfully", "Adding ID", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
    class ID2 implements Comparable<ID2> {
    int id;
    public ID2(int id){
        this.id = id;
    }
    public int getID (int validID){
        return id;
    }
    public void setID(int inputID){
        id = inputID;
    }
    @Override
    // method used for comparing ID objects based on stored ids, you need to complete the method
    public int compareTo(ID2 o) {
        if(this.id > o.id){
            return 1;}
        else if(this.id < o.id) {
            return -1;
        }
        else{ return 0;}
    }
    // outputs a string representation of the object
    public String toString()
    {
        return ("ID = " + id);
    }

}