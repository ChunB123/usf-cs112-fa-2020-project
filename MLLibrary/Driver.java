package MLLibrary;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class Driver {

    /*
    1. Generate two sets of random DataPoints, one being the training data set, one being the test data set.
    2. Instantiate a DummyModel class, train and test with your random data and computethe precision and accuracy.
    3. Display the precision and accuracy in a JFrame. Be creative here with your labels and format for printing them.
    We will use this JFrame again and again for next set of milestones.*/

    public static void main(String[] args){
        Random rd = new Random();
        ArrayList<DataPoint> trainingSet=new ArrayList<>();
        trainingSet.add(new DataPoint(0.0001,0.0002,"red",""));
        trainingSet.add(new DataPoint(0.5,0.51,"blue",""));
        trainingSet.add(new DataPoint(0.0001,0.00004,"red",""));

        ArrayList<DataPoint> testingSet=new ArrayList<>();
        testingSet.add(new DataPoint(rd.nextDouble(),rd.nextDouble(),"",""));
        testingSet.add(new DataPoint(rd.nextDouble(),rd.nextDouble(),"",""));
        testingSet.add(new DataPoint(rd.nextDouble(),rd.nextDouble(),"",""));

        Model dummyModel=new DummyModel();
        dummyModel.train(trainingSet);
        System.out.println(dummyModel.test(testingSet));

        double precision=dummyModel.getPrecision(testingSet);
        double accuracy=dummyModel.getAccuracy(testingSet);

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
