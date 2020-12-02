package MLLibrary;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import javax.swing.*;

public class Graph extends JPanel {

    private static final long serialVersionUID = 1L;
    private int labelPadding = 40;
    private Color lineColor = new Color(255, 255, 254);

    private Color gridColor = new Color(200, 200, 200, 200);

    private Color pointColor = new Color(255, 0, 255);

    // TODO: Add point colors for each type of data point
    private Color blueColor = new Color(0, 0, 255);

    private Color cyanColor = new Color(0, 255, 255);

    private Color yellowColor = new Color(255, 255, 0);

    private Color redColor = new Color(255, 0, 0);

    private static final Stroke GRAPH_STROKE = new BasicStroke(2f);

    // TODO: Change point width as needed
    private static int pointWidth = 10;

    // Number of grids and the padding width
    private int numXGridLines = 6;
    private int numYGridLines = 6;
    private int padding = 40;

    private List<DataPoint> data;

    // TODO: Add a private KNNModel variable
    private static KNNModel knnModel;

    /**
	 * Constructor method
	 */
    public Graph(List<DataPoint> testData, List<DataPoint> trainData) {
        this.data = testData;
        // TODO: instantiate a KNNModel variable
        knnModel=new KNNModel(15);
        // TODO: Run train with the trainData
        knnModel.train((ArrayList<DataPoint>) trainData);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        double minF1 = getMinF1Data();
        double maxF1 = getMaxF1Data();
        double minF2 = getMinF2Data();
        double maxF2 = getMaxF2Data();

        g2.setColor(Color.WHITE);
        g2.fillRect(padding + labelPadding, padding, getWidth() - (2 * padding) - 
        		labelPadding, getHeight() - 2 * padding - labelPadding);
        g2.setColor(Color.BLUE);

        double yGridRatio = (maxF2 - minF2) / numYGridLines;
        for (int i = 0; i < numYGridLines + 1; i++) {
            int x0 = padding + labelPadding;
            int x1 = pointWidth + padding + labelPadding;
            int y0 = getHeight() - ((i * (getHeight() - padding * 2 -
            		labelPadding)) / numYGridLines + padding + labelPadding);
            int y1 = y0;
            if (data.size() > 0) {
                g2.setColor(gridColor);
                g2.drawLine(padding + labelPadding + 1 + pointWidth, y0, getWidth() - padding, y1);
                g2.setColor(Color.BLACK);
                String yLabel = String.format("%.2f", (minF2 + (i * yGridRatio)));
                FontMetrics metrics = g2.getFontMetrics();
                int labelWidth = metrics.stringWidth(yLabel);
                g2.drawString(yLabel, x0 - labelWidth - 6, y0 + (metrics.getHeight() / 2) - 3);
            }
            g2.drawLine(x0, y0, x1, y1);
        }

        double xGridRatio = (maxF1 - minF1) / numXGridLines;
        for (int i = 0; i < numXGridLines + 1; i++) {
            int y0 = getHeight() - padding - labelPadding;
            int y1 = y0 - pointWidth;
            int x0 = i * (getWidth() - padding * 2 - labelPadding) / (numXGridLines) + padding + labelPadding;
            int x1 = x0;
            if (data.size() > 0) {
                g2.setColor(gridColor);
                g2.drawLine(x0, getHeight() - padding - labelPadding - 1 - pointWidth, x1, padding);
                g2.setColor(Color.BLACK);
                String xLabel = String.format("%.2f", (minF1 + (i * xGridRatio)));
                FontMetrics metrics = g2.getFontMetrics();
                int labelWidth = metrics.stringWidth(xLabel);
                g2.drawString(xLabel, x0 - labelWidth / 2, y0 + metrics.getHeight() + 3);
            }
            g2.drawLine(x0, y0, x1, y1);
        }

        // Draw the main axis
        g2.drawLine(padding + labelPadding, getHeight() - padding - labelPadding, padding + labelPadding, padding);
        g2.drawLine(padding + labelPadding, getHeight() - padding - labelPadding, getWidth() -
        		padding, getHeight() - padding - labelPadding);

        // Draw the points
        paintPoints(g2, minF1, maxF1, minF2, maxF2);
    }

    private void paintPoints(Graphics2D g2, double minF1, double maxF1, double minF2, double maxF2) {
        Stroke oldStroke = g2.getStroke();
        g2.setColor(lineColor);
        g2.setStroke(GRAPH_STROKE);
        double xScale = ((double) getWidth() - (3 * padding) - labelPadding) /(maxF1 - minF1);
        double yScale = ((double) getHeight() - 2 * padding - labelPadding) / (maxF2 - minF2);
        g2.setStroke(oldStroke);
        for (int i = 0; i < data.size(); i++) {
            int x1 = (int) ((data.get(i).getF1() - minF1) * xScale + padding + labelPadding);
            int y1 = (int) ((maxF2 - data.get(i).getF2()) * yScale + padding);
            int x = x1 - pointWidth / 2;
            int y = y1 - pointWidth / 2;
            int ovalW = pointWidth;
            int ovalH = pointWidth;

            // TODO: Depending on the type of data and how it is tested, change color here.
            // You need to test your data here using the model to obtain the test value 
            // and compare against the true label.
            ArrayList<DataPoint> testArrayList=new ArrayList<>();
            testArrayList.add(data.get(i));
            String testLabel = knnModel.test(testArrayList);
            if (testLabel.equals("1")) {
                if (data.get(i).getLabel().equals("1")) {
                    g2.setColor(blueColor);
                }
                if (data.get(i).getLabel().equals("0")) {
                    g2.setColor(cyanColor);
                }
            }
            if (testLabel.equals("0")) {
                if (data.get(i).getLabel().equals("1")) {
                    g2.setColor(yellowColor);
                }
                if (data.get(i).getLabel().equals("0")) {
                    g2.setColor(redColor);
                }
            }


            //g2.setColor(pointColor);
            g2.fillOval(x, y, ovalW, ovalH);
        }

    }

    /*
     * @Return the min values
     */
    private double getMinF1Data() {
        double minData = Double.MAX_VALUE;
        for (DataPoint pt : this.data) {
            minData = Math.min(minData, pt.getF1());
        }
        return minData;
    }

    private double getMinF2Data() {
        double minData = Double.MAX_VALUE;
        for (DataPoint pt : this.data) {
            minData = Math.min(minData, pt.getF2());
        }
        return minData;
    }


    /*
     * @Return the max values;
     */
    private double getMaxF1Data() {
        double maxData = Double.MIN_VALUE;
        for (DataPoint pt : this.data) {
            maxData = Math.max(maxData, pt.getF1());
        }
        return maxData;
    }

    private double getMaxF2Data() {
        double maxData = Double.MIN_VALUE;
        for (DataPoint pt : this.data) {
            maxData = Math.max(maxData, pt.getF2());
        }
        return maxData;
    }

    /* Mutator */
    public void setData(List<DataPoint> data) {
        this.data = data;
        invalidate();
        this.repaint();
    }

    /* Accessor */
    public List<DataPoint> getData() {
        return data;
    }

    /*  Run createAndShowGui in the main method, where we create the frame too and pack it in the panel*/
    private static void createAndShowGui(List<DataPoint> testData, List<DataPoint> trainData) {


	    /* Main panel */
        Graph mainPanel = new Graph(testData, trainData);

        // Feel free to change the size of the panel
        mainPanel.setPreferredSize(new Dimension(700, 600));

        /* creating the frame */
        JFrame frame = new JFrame("CS 112 Lab Part 3");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(mainPanel);

        Container contentPane = frame.getContentPane();
        contentPane.setLayout(new GridLayout(1,2));
        Double precision=knnModel.getPrecision((ArrayList<DataPoint>) testData);
        Double accuracy=knnModel.getAccuracy((ArrayList<DataPoint>) testData);
        contentPane.add(new JButton("Precision:"+Double.toString(precision)));
        contentPane.add(new JButton("Accuracy:"+Double.toString(accuracy)));

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

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
      
    /* The main method runs createAndShowGui*/
    public static void main(String[] args) {
      SwingUtilities.invokeLater(new Runnable() {
         public void run() {
            // Generate random data point
            List<DataPoint> data = new ArrayList<>();
            //Random random = new Random();
            int maxDataPoints = 20;
            // Max value of f1 and f2 that is arbitrarily set
            int maxF1 = 8;
            int maxF2 = 300;

            // Generates random DataPoints
            /*for (int i = 0; i < maxDataPoints; i++) {
                double f1 = (random.nextDouble() * maxF1);
                double f2 = (random.nextDouble() * maxF2);
                data.add(new DataPoint(f1, f2, "Random", "Random"));
            }*/

            // TODO: Change the above logic retrieve the data from titanic.csv
            // Split the data to test and training
             ArrayList<DataPoint> trainSet=new ArrayList<>();
             ArrayList<DataPoint> testSet=new ArrayList<>();

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
                                     trainSet.add(new DataPoint(Double.valueOf(records.get(5)), Double.valueOf(records.get(6)), records.get(1), "train"));
                                 } else {
                                     testSet.add(new DataPoint(Double.valueOf(records.get(5)), Double.valueOf(records.get(6)), records.get(1), "test"));
                                 }
                             }
                         }
                     }
                 }
             } catch (FileNotFoundException e) {
                 e.printStackTrace();
             }
            
            // TODO: Pass in the testData and trainData separately 
            // Be careful with the order of the variables.
             createAndShowGui(testSet,trainSet);
         }
      });
    }
}
