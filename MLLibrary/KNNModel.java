package MLLibrary;

import javax.swing.*;
import javax.xml.crypto.Data;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public class KNNModel extends Model {

    private ArrayList<DataPoint> trainSet;
    private int K;
    private int surviveT = 0;
    private int surviveF = 0;

    private double getDistance(DataPoint p1, DataPoint p2) {
        return Math.sqrt(Math.pow((p1.getF1() - p2.getF1()), 2) + Math.pow((p1.getF2() - p2.getF2()), 2));
    }

    ;


    public KNNModel(int k) {
        K = k;
        trainSet = new ArrayList<>();
    }

    @Override
    void train(ArrayList<DataPoint> data) {
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i).getType().equals("train")) {
                if (data.get(i).getLabel().equals(1)) {
                    surviveT++;
                }
                if (data.get(i).getLabel().equals(0)) {
                    surviveF++;
                }
                trainSet.add(data.get(i));
            }
        }
    }

    @Override
    String test(ArrayList<DataPoint> data) {
        DataPoint testPoint=null;
        for(DataPoint dataPoint:data){
            if(dataPoint.getType().equals("test")){
                testPoint=dataPoint;
                break;
            }
        }
        if (testPoint.getType().equals("test")) {
            Double[][] array = new Double[data.size()][2];
            for (int i = 0; i < trainSet.size(); i++) {
                array[i][0] = getDistance(testPoint, trainSet.get(i));
                array[i][1] = Double.valueOf(trainSet.get(i).getLabel());
            }
            Arrays.sort(array, new java.util.Comparator<Double[]>() {
                public int compare(Double[] a, Double[] b) {
                    return a[0].compareTo(b[0]);
                }
            });
            //find first k elements
            int labelZero = 0;
            int labelOne = 0;
            for (int i = 0; i < K; i++) {
                if (array[i][1] == 0) {
                    labelZero++;
                }
                if (array[i][1] == 1) {
                    labelOne++;
                }
            }
            if (labelZero > labelOne) {
                return "0";
            } else {
                return "1";
            }
        }

        //f.v
        return "0";
    }

    @Override
    Double getAccuracy(ArrayList<DataPoint> data) {
        String testLabel = test(data);
        double truePositive = 0;
        double falsePositive = 0;
        double falseNegative = 0;
        double trueNegative = 0;
        for (int i = 0; i < data.size(); i++) {
            if (testLabel.equals("1")) {
                if (data.get(i).getLabel().equals("1")) {
                    truePositive++;
                }
                if (data.get(i).getLabel().equals("0")) {
                    falsePositive++;
                }
            }
            if (testLabel.equals("0")) {
                if (data.get(i).getLabel().equals("1")) {
                    falseNegative++;
                }
                if (data.get(i).getLabel().equals("0")) {
                    trueNegative++;
                }
            }
        }
        return (truePositive + trueNegative) / (truePositive + trueNegative + falsePositive + falseNegative);
    }

    @Override
    Double getPrecision(ArrayList<DataPoint> data) {
        String testLabel = test(data);
        double truePositive = 0;
        double falsePositive = 0;
        double falseNegative = 0;
        double trueNegative = 0;
        for (int i = 0; i < data.size(); i++) {
            if (testLabel.equals("1")) {
                if (data.get(i).getLabel().equals("1")) {
                    truePositive++;
                }
                if (data.get(i).getLabel().equals("0")) {
                    falsePositive++;
                }
            }
            if (testLabel.equals("0")) {
                if (data.get(i).getLabel().equals("1")) {
                    falseNegative++;
                }
                if (data.get(i).getLabel().equals("0")) {
                    trueNegative++;
                }
            }
        }
        return truePositive / (truePositive + falseNegative);
    }


}


