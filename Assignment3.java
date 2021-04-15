import java.util.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
/**
 * 
 * Assignment #3
 * @author JONGHYUN PARK
 * @version Nov 2019
 */
public class Assignment3
 {
    public static void main (String [] args) throws IOException {

    	  //Create placeholders for the arrays here but don't allocate any space
        Scanner in = new Scanner (System.in);

        //Create placeholders for the arrays here
        final int MAX_DATA_POINTS = 356;
        String [] dates = new String [MAX_DATA_POINTS];
        double [] tempMax = new double [MAX_DATA_POINTS];
        double [] tempMin = new double [MAX_DATA_POINTS];
        double [] tempMean = new double [MAX_DATA_POINTS];

        int numOfElements = 0;
        int selection = 0;
        String filename = "";

        //Enter directory and filename
        System.out.println ("Enter the filename to be analyzed (e.g. c:/temp/Calgary2013.csv):");
        filename = in. nextLine();

        //Load the data into the arrays
        String date = "";
        double max = 0, min = 0, mean = 0;
        int i = 0;
        int numOfDays = 0;
        String line = "";

        //Open my the filename in the baseDirectory where all the data is stored

        String baseDirectory = "C:/CMPP269G/Assignment/Datafiles";
        filename = "Calgary 2013.csv";
        int years = 2009;
        filename = String.format ("%s/%s", baseDirectory, filename);
        Scanner input = new Scanner(new FileInputStream(filename),"UTF-8");

        boolean isFirstLineOfData = false;

        while(input.hasNextLine() && !isFirstLineOfData) 
        {
            line = input.nextLine();
            if (line.startsWith("Date/Time,Year")) 
            {
                isFirstLineOfData = true;
            }
        }   

        // change delimiter to be a comma
        input.useDelimiter(","); 

        // process the data here
        while (input.hasNextLine()) 
        {
            date = input.next();

            //ignore next 3 values: year, month, day
            input.nextInt();
            input.nextInt();
            input.nextInt();
            input.next();

            // is there a max temp? 
            String maxString = input.next();
            if (!maxString.equals ("")) 
            {

                // There is a max temp, convert it
                max = Double.parseDouble(maxString);
                input.next();

                String minString = input.next();
                
                // is there a min temp?
                if (!minString.equals("")) 
                {

                    //there is a min temp, convert it
                    //only do the average if there is a max and min
                    numOfDays++;
                    
                    min = Double.parseDouble(minString);

                    mean = (max+min)/2;

                }
                else {

                    //no min 

                }
                //discard the rest of the data
                input.nextLine();

                //all the data is avialable here, save to array
                //load arrrays here
                dates[i] = date;
                tempMax[i] = max;
                tempMin[i] = min;
                tempMean[i] = mean;

                // Advance i to the next position int array
                i++;

            }
            else{

                //if no max temperature probably no other data, ignore rest of line
                max = 0;
                input.nextLine();

            }
        }
        input.close();        

        //Remeber the number of days of data
        numOfElements = i;

        //Print the Top N hottest days
        System.out.println ("Top N Hottest Days");
        System.out.print ("Input Value for N (e.g. Top 10 Hottest Days): ");
        int numOfDaysToReport = in. nextInt();

        ArrayList<String>hottestDates = new ArrayList<String>();
        ArrayList<Double>hottestTemps = new ArrayList<Double>();

        //add a dummy header and footer node
        //impossibility large value, all temperatures will be less than this
        hottestDates.add("99/99/99");
        hottestTemps.add(-999.0);

        //impossibility small value, all temperatures will be greater than this

        hottestDates.add("01/01/01");
        hottestTemps.add(999.0);

        for (i = 0; i < numOfElements; i++) {

            // look at the temp and insert it in sorted order within the list
            int j = 0;

            while (tempMax[i] <= hottestTemps.get(j))
            {
                j++;
            }

            //insert at position j+1
            hottestTemps.add(j+1, tempMax[i]); 
            hottestDates.add(j+1, dates[i]);
              
        }

        System.out.printf ("%20sHottest Top %d Days%n%n", "", numOfDaysToReport);
        System.out.printf ("%5s %-10s %10s%n", "#", "Date", "Max Temp");

        for (i = 1; i < numOfDaysToReport +1; i++)
        {

            System.out.printf ("%5d %10s %10.1f%n", i, hottestDates.get(i), hottestTemps.get(i));

        }

        //comuteAnnualData - computes and prints the average max, min and mean for the dataset
        double sumMax = 0, sumMin = 0, sumMean = 0;
        double avgMax = 0, avgMin = 0, avgMean = 0;

        //Go through all elements and compute the sum
        for (i = 0; i < numOfDaysToReport; i++){

            sumMax += tempMax[i];
            sumMin += tempMin[i];
            sumMean += tempMean[i];

        }

        //compute averages and print them

        avgMax = sumMax / numOfElements;
        avgMin = sumMin / numOfElements;
        avgMean = sumMean / numOfElements;

        // Print the averages

        System.out.println ("\n\nSummary data for the year");
        System.out.printf ("%10s %10s %10s %15s%n", "Avg.Max", "Avg.Min", "Avg.Mean", "Days of Data");
        System.out.printf ("%10.2f %10.2f %10.2f %15d", avgMax, avgMin, avgMean, numOfDays );

    }
}

