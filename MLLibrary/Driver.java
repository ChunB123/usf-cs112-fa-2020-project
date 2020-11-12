package MLLibrary;


import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Driver {

    private static List<String> getRecordFromLine(String line) {
        List<String> values = new ArrayList<String>();
        try (Scanner rowScanner = new Scanner(line)) {
            rowScanner.useDelimiter(",");
            while (rowScanner.hasNext()) {
                values.add(rowScanner.next());
            }
        }
        return values;
    }

    public static void main(String[] args){
        ArrayList<DataPoint> dataPoints=new ArrayList<>();
        try {
            Scanner scanner = new Scanner(new File("titanic.csv"));
            while(scanner.hasNextLine()){
                List<String> records=getRecordFromLine(scanner.nextLine());
                // TODO: Select the columns from the records and create a DataPoint object
                // TODO: Store the DataPoint object in a collection
                if(!records.get(1).equals("survived")) {

                    Random random = new Random();
                    double randNum = random.nextDouble();
                    //90% data is reserved for training
                    if(records.size()==7){
                        if(!records.get(5).equals("")) {
                            if (randNum < 0.9) {
                                dataPoints.add(new DataPoint(Double.valueOf(records.get(5)), Double.valueOf(records.get(6)), records.get(1), "train"));
                            } else {
                                dataPoints.add(new DataPoint(Double.valueOf(records.get(5)), Double.valueOf(records.get(6)), records.get(1), "test"));
                            }
                        }
                    }
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        KNNModel knnModel=new KNNModel(0);
        knnModel.train(dataPoints);

        Double precision=knnModel.getPrecision(dataPoints);
        Double accuracy=knnModel.getAccuracy(dataPoints);

        //display
        SwingUtilities.invokeLater(
                new Runnable() { public void run() { initAndShowGUI(precision,accuracy); } }
        );
    }

    private static void initAndShowGUI(double precision,double accuracy) {
        JFrame myFrame = new JFrame("MLLibrary");
        Container contentPane = myFrame.getContentPane();
        contentPane.setLayout(new GridLayout(1,2));
        contentPane.add(new JButton("Precision:"+Double.toString(precision)));
        contentPane.add(new JButton("Accuracy:"+Double.toString(accuracy)));
        myFrame.pack();
        myFrame.setVisible(true);
    }



}
