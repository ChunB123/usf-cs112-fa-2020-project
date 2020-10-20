package MLLibrary;

import java.util.ArrayList;

public class DummyModel extends Model {
    //difference between f1 and f2 is close to zero
    private double red;
    //difference between f1 and f2 is close to one
    private double blue;


    @Override
    void train(ArrayList<DataPoint> data) {
        int redCount=0;
        double redDifference=0;
        int blueCount=0;
        double blueDifference=0;
        for(int i=0;i<data.size();i++){

            if(data.get(i).getLabel()=="red"){
                redDifference=redDifference+Math.abs(data.get(i).getF1()-data.get(i).getF2())/2.0;
                redCount++;
            }


            if(data.get(i).getLabel()=="blue"){
                blueDifference=blueDifference+Math.abs(data.get(i).getF1()-data.get(i).getF2())/2.0;
                blueCount++;
            }
        }
        red=redDifference/redCount;
        blue=blueDifference/blueCount;

    }

    @Override
    String test(ArrayList<DataPoint> data) {
        for(int i=0;i<data.size();i++){
            double difference=Math.abs(data.get(i).getF1()-data.get(i).getF2())/2.0;
            if(Math.abs(difference-red)>=Math.abs(difference-blue)){
                data.get(i).setLabel("blue");
            }else{
                data.get(i).setLabel("red");
            }
        }

        return data.toString();
    }

    //correct red/all prediction about red
    @Override
    Double getPrecision(ArrayList<DataPoint> data) {
        double correct=0;
        double redCount=0;
        for(int i=0;i<data.size();i++){
            if(data.get(i).getLabel()=="red"){
                if((Math.abs(data.get(i).getF1()-data.get(i).getF2())/2.0)<0.5){
                    correct++;
                }
                redCount++;
            }
        }
        if(redCount==0){
            return 0.0;
        }
        return Double.valueOf(correct/redCount);
    }

    //all predictions/whole data set
    @Override
    Double getAccuracy(ArrayList<DataPoint> data) {
        double count=0;
        for(DataPoint dataPoint:data){
            if(dataPoint.getLabel().equals("red")||dataPoint.getLabel().equals("blue")){
                count++;
            }
        }
        return Double.valueOf(count/data.size());
    }
}
