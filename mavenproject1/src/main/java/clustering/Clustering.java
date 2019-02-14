package clustering;

import static clustering.Clustering.myArray;
import java.io.File;
import static java.lang.Math.abs;
import java.util.ArrayList;
import javax.swing.JFrame;
import jxl.Sheet;
import jxl.Workbook;
import org.math.plot.*;
import java.util.Random;
import java.util.List;

/**
 *
 * @author witko
 */
public class Clustering {

    /**
     * @param args the command line arguments
     */
    static double[][] myArray = new double[2][99];

private static double getRandomNumberInRange(double rangeMin, double rangeMax) {

        if (rangeMin >= rangeMax) {
            throw new IllegalArgumentException("max must be greater than min");
        }

        Random r = new Random();
        double randomValue = rangeMin + (rangeMax - rangeMin) * r.nextDouble();
        return randomValue;
    }

    static double distance(double x1, double y1, double x2, double y2) {
        double d = Math.sqrt(Math.pow(x2 - x1, 2)
                + Math.pow(y2 - y1, 2) * 1.0);
        return d;
    }
    
    public static double getMax(double[] inputArray) {
        double maxValue = inputArray[0];
        for (int i = 1; i < (inputArray.length - 1); i++) {
            if (inputArray[i] > maxValue) {
                maxValue = inputArray[i];
            }
        }
        return maxValue;
    }
    
    // Method for getting the minimum value
    public static double getMin(double[] inputArray) {
        double minValue = inputArray[0];
        for (int i = 1; i < (inputArray.length - 1); i++) {
            if (inputArray[i] < minValue) {
                minValue = inputArray[i];
            }
        }
        return minValue;
    }

    public static void main(String[] args) throws Exception {
        File f = new File("C:\\Users\\witko\\OneDrive\\Pulpit\\docs\\nauka\\java_project\\wifigda.xls");
        Workbook wb = Workbook.getWorkbook(f);
        Sheet s = wb.getSheet(0);
        int row = s.getRows();
        for (int i = 1; i < row - 1; i++) {
            String x = s.getCell(2, i).getContents();
            String y = s.getCell(3, i).getContents();
            myArray[0][i - 1] = Double.parseDouble(x); //xxxxxxxxxxx
            myArray[1][i - 1] = Double.parseDouble(y); //yyyyyyyyyyy
            //System.out.println(myArray[0][i-1]+","+myArray[1][i-1]); //x0,y0            
        }

        //I need random value r to generate centroid c
        double min_x = getMin(myArray[0]);
        double max_x = getMax(myArray[0]);
        double min_y = getMin(myArray[1]);
        double max_y = getMax(myArray[1]);
        
        double r1x = getRandomNumberInRange(min_x, max_x);
        double c1x = (double)Math.round(r1x * 1000d) / 1000d;
        double r1y = getRandomNumberInRange(min_y, max_y);
        double c1y = (double)Math.round(r1y * 1000d) / 1000d;

        double r2x = getRandomNumberInRange(min_x, max_x);
        double c2x = (double)Math.round(r2x * 1000d) / 1000d;
        double r2y = getRandomNumberInRange(min_y, max_y);
        double c2y = (double)Math.round(r2y * 1000d) / 1000d;
        
        double r3x = getRandomNumberInRange(min_x, max_x);
        double c3x = (double)Math.round(r3x * 1000d) / 1000d;
        double r3y = getRandomNumberInRange(min_y, max_y);
        double c3y = (double)Math.round(r3y * 1000d) / 1000d;

        //I have to initilize array, becasue it's plot after for
        double[] arr1x_final = null;
        double[] arr1y_final = null;
        double[] arr2x_final = null;
        double[] arr2y_final = null;
        double[] arr3x_final = null;
        double[] arr3y_final = null;

        //at the beginning real centroid c1x_real=0
        double c1x_real = 0;
        double c1y_real = 0;
        double c2x_real = 0;
        double c2y_real = 0;
        double c3x_real = 0;
        double c3y_real = 0;
        
        //difference c1x_real-c1x is equal to random value
        double difference1x = 1;
        double difference1y = 2;
        double difference2x = 3;
        double difference2y = 4;
        double difference3x = 5;
        double difference3y = 6;

        //to check how many itteration we needed to obtain difference = 0.0
        int itteration = 0;
        
        while((difference1x + difference2x + difference2y + difference3x + difference3y) != 0){ 
            //problem with difference1y, approximately = 0
            
            //To not initialize dimmension of Array in need Dynamic array -> list
            List<Double> cluster1x = new ArrayList();
            List<Double> cluster1y = new ArrayList();
            List<Double> cluster2x = new ArrayList();
            List<Double> cluster2y = new ArrayList();
            List<Double> cluster3x = new ArrayList();
            List<Double> cluster3y = new ArrayList();

            //I need array of differents d
            double[] diff1 = new double[99];
            double[] diff2 = new double[99];
            double[] diff3 = new double[99];

            for (int k = 0; k < myArray[0].length; k++) {
                double d1 = Math.round(distance(myArray[0][k], myArray[1][k], c1x, c1y) * 1000.0) / 1000.0;
                double d2 = Math.round(distance(myArray[0][k], myArray[1][k], c2x, c2y) * 1000.0) / 1000.0;
                double d3 = Math.round(distance(myArray[0][k], myArray[1][k], c3x, c3y) * 1000.0) / 1000.0;
                diff1[k] = d1;
                diff2[k] = d2;
                diff3[k] = d3;
                
                double m = Math.min(diff1[k], Math.min(diff2[k], diff3[k]));
                //if minumum value is equal to different from first centroid then assign to cluster1 
                if (m == diff1[k]) {
                    cluster1x.add(myArray[0][k]);
                    cluster1y.add(myArray[1][k]);
                } else if (m == diff2[k]) {
                    cluster2x.add(myArray[0][k]);
                    cluster2y.add(myArray[1][k]);
                } else if (m == diff3[k]) {
                    cluster3x.add(myArray[0][k]);
                    cluster3y.add(myArray[1][k]);
                }
            }

            //The object plot needs arrays instead of lists with defined size
            double[] arr1x = new double[cluster1x.size()];
            double[] arr1y = new double[cluster1y.size()];
            double[] arr2x = new double[cluster2x.size()];
            double[] arr2y = new double[cluster2y.size()];
            double[] arr3x = new double[cluster3x.size()];
            double[] arr3y = new double[cluster3y.size()];

            //PARTII: I need the real centroid of my cluster
            double sum1x = 0;
            double sum1y = 0;
            double sum2x = 0;
            double sum2y = 0;
            double sum3x = 0;
            double sum3y = 0;

            //PARTII compute real center
            for (int i = 0; i < cluster1x.size(); i++) {
                arr1x[i] = (double) cluster1x.get(i); //mapping, rzutowanie
                sum1x = sum1x + arr1x[i]; //for PARTII
                c1x_real = sum1x / cluster1x.size();

                arr1y[i] = (double) cluster1y.get(i);
                sum1y = sum1y + arr1y[i];
                c1y_real = sum1y / cluster1x.size();
            }
            for (int i = 0; i < cluster2x.size(); i++) {
                arr2x[i] = (double) cluster2x.get(i);
                sum2x = sum2x + arr2x[i];
                c2x_real = sum2x / cluster2x.size();

                arr2y[i] = (double) cluster2y.get(i);
                sum2y = sum2y + arr2y[i];
                c2y_real = sum2y / cluster2x.size();
            }
            for (int i = 0; i < cluster3x.size(); i++) {
                arr3x[i] = (double) cluster3x.get(i);
                sum3x = sum3x + arr3x[i];
                c3x_real = sum3x / cluster3x.size();

                arr3y[i] = (double) cluster3y.get(i);
                sum3y = sum3y + arr3y[i];
                c3y_real = sum3y / cluster3x.size();
            }

            //PARTII: To find final cluster is needed to compute a distance
            //remember: cluster1x.size() = cluster1y.size()
            double[] distance1 = new double[myArray[0].length];
            double[] distance2 = new double[myArray[0].length];
            double[] distance3 = new double[myArray[0].length];

            //PARTII: The new clusters have to be lists, because we don't know them dimmensions
            List<Double> cluster1x_final = new ArrayList();
            List<Double> cluster1y_final = new ArrayList();
            List<Double> cluster2x_final = new ArrayList();
            List<Double> cluster2y_final = new ArrayList();
            List<Double> cluster3x_final = new ArrayList();
            List<Double> cluster3y_final = new ArrayList();

            //----------------finding new cluster---------------------------
            for (int k = 0; k < myArray[0].length; k++) {
                double d1_real = Math.round(distance(myArray[0][k], myArray[1][k], c1x_real, c1y_real) * 1000.0) / 1000.0;
                double d2_real = Math.round(distance(myArray[0][k], myArray[1][k], c2x_real, c2y_real) * 1000.0) / 1000.0;
                double d3_real = Math.round(distance(myArray[0][k], myArray[1][k], c3x_real, c3y_real) * 1000.0) / 1000.0;
                distance1[k] = d1_real;
                distance2[k] = d2_real;
                distance3[k] = d3_real;

                double m_real = Math.min(distance1[k], Math.min(distance2[k], distance3[k]));
                //if minimul value is equal to different from first centroid then assign to cluster1 
                if (m_real == distance1[k]) {
                    cluster1x_final.add(myArray[0][k]);
                    cluster1y_final.add(myArray[1][k]);
                } else if (m_real == distance2[k]) {
                    cluster2x_final.add(myArray[0][k]);
                    cluster2y_final.add(myArray[1][k]);
                } else if (m_real == distance3[k]) {
                    cluster3x_final.add(myArray[0][k]);
                    cluster3y_final.add(myArray[1][k]);
                }
            }

            System.out.println("Elements of final cluster 1 (x) :" + cluster1x_final);
            System.out.println("Elements of final cluster 1 (y) :" + cluster1y_final);
            System.out.println("Elements of final cluster 2 (x) :" + cluster2x_final);
            System.out.println("Elements of final cluster 2 (y) :" + cluster2y_final);
            System.out.println("Elements of final cluster 3 (x) :" + cluster3y_final);
            System.out.println("Elements of final cluster 3 (y) :" + cluster3y_final);
            
            //to plot we need array insted of list
            arr1x_final = new double[cluster1x_final.size()];
            arr1y_final = new double[cluster1y_final.size()];
            arr2x_final = new double[cluster2x_final.size()];
            arr2y_final = new double[cluster2y_final.size()];
            arr3x_final = new double[cluster3x_final.size()];
            arr3y_final = new double[cluster3y_final.size()];

            for (int i = 0; i < cluster1x_final.size(); i++) {
                arr1x_final[i] = cluster1x_final.get(i);
                arr1y_final[i] = cluster1y_final.get(i);
            }
            for (int i = 0; i < cluster2x_final.size(); i++) {
                arr2x_final[i] = cluster2x_final.get(i);
                arr2y_final[i] = cluster2y_final.get(i);
            }
            for (int i = 0; i < cluster3x_final.size(); i++) {
                arr3x_final[i] = cluster3x_final.get(i);
                arr3y_final[i] = cluster3y_final.get(i);
            }

            difference1x = abs(c1x_real - c1x);
            System.out.println("difference1x: " + difference1x);
            difference1y = abs(c1y_real - c1y);
            System.out.println("difference1y: " + difference1y);
            difference2x = abs(c2x_real - c2x);
            System.out.println("difference2x :" + difference2x);
            difference2y = abs(c2y_real - c2y);
            System.out.println("difference2y: " + difference2y);
            difference3x = abs(c3x_real - c3x);
            System.out.println("difference3x " + difference3x);
            difference3y = abs(c3y_real - c3y);
            System.out.println("difference3y :" + difference3y);

            c1x = c1x_real;
            c2x = c2x_real;
            c1y = c2y_real;
            c2y = c2y_real;
            c3x = c3x_real;
            c3y = c3y_real;
            
            itteration = itteration + 1;
            System.out.println("Number of iterations :" + itteration);
        }

        //drawing
        Plot2DPanel plot = new Plot2DPanel();
        plot.addScatterPlot("my plot", arr1x_final, arr1y_final);
        plot.addScatterPlot("my plot", arr2x_final, arr2y_final);
        plot.addScatterPlot("my plot", arr3x_final, arr3y_final);
        JFrame frame = new JFrame("a plot panel");
        frame.setContentPane(plot);
        frame.setVisible(true);
    }
}