package logfuel;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import logfuel.Supply;
import logfuel.Vehicle;

public class LogCombustivel {

    private static final String CSV_DELIMITER = ",";

    public static void main(String[] args) {
        Scanner fileScanner = null;
        String[] logDetails = null;
        
        /**
         * Map all the vehicles to better manipulation using keys
         */
        String key = null;
        Map<String, Vehicle> vehicles = new HashMap<String, Vehicle>();

        /**
         * Use a pattern to deal with dates from input
         */
        String datePattern = "yyyy-MM-dd";
        SimpleDateFormat dateFormat = new SimpleDateFormat(datePattern);

        try {

        	fileScanner = new Scanner(new File("LogCombustivel.csv"));

        	/**
        	 * Read each line and after split each attribute from the csv
        	 * using scanner class.
        	 */
        	fileScanner.nextLine();	// Ignore first line containing headers 
            while(fileScanner.hasNextLine()) {
            	logDetails = fileScanner.nextLine().split(CSV_DELIMITER);
                
                if(logDetails.length > 0)
                {
                	key = logDetails[0].replaceAll("\"", "").toLowerCase() + '-' + logDetails[1].replaceAll("\"", "").toLowerCase();
                	if(!vehicles.containsKey(key)) {
                		Vehicle newVehicle = new Vehicle();
                		newVehicle.setBrand(logDetails[0].replaceAll("\"", ""));
                		newVehicle.setModel(logDetails[1].replaceAll("\"", ""));
                    	vehicles.put(key, newVehicle);
                	}

                	Supply supply = new Supply();
                	supply.setSupplyDate(dateFormat.parse(logDetails[2].replaceAll("\"", "")));
                	supply.setCurrentMileage(Integer.parseInt(logDetails[3].replaceAll("\"", "")));
                	supply.setGasolineLiters(Float.parseFloat(logDetails[4].replaceAll("\"", "")));
                	supply.setCostPerLiter(Float.parseFloat(logDetails[5].replaceAll("\"", "")));

                	// Retrieve existing vehicle and include a new supply 
                	Vehicle existingVehicle = vehicles.get(key);
                	existingVehicle.updateSupplies(supply);
                    vehicles.put(key, existingVehicle);
                }
            }

        } catch (FileNotFoundException fnfe) {

            System.out.println("Não foi possível encontrar o arquivo 'LogCombustivel.csv'.");
            fnfe.printStackTrace();

        } catch (ParseException pe){

        	System.out.println("Não foi possível ler o seguinte valor de data: " + logDetails[2]);
        	pe.printStackTrace();

        } finally {

        	fileScanner.close();
        	/**
        	 * After csv is read we can manipulate the values
        	 */
        	
        	for (Vehicle v : vehicles.values()) {
        		System.out.println(v.toString());
        	}

        }

    }

}
