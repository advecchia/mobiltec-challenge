package logfuel;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import logfuel.Vehicle;

public class LogCombustivel {

    private static final String CSV_DELIMITER = ",";
    private static final String SUMMARY_READER = "\"MARCA\",\"MODELO\",\"KM\",\"R$\",\"LITROS\",\"DATAINI\",\"DIAS\",\"MEDIAKM/L\",\"PIORKM/L\",\"MELHORKM/L\",\"R$/KM\"";
    private static final Long MILISECONDS_PER_DAY = 60 * 60 * 24 * 1000L;

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
            fileScanner.nextLine();    // Ignore first line containing headers 
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
            FileWriter fileWriter = null;
            try {

                fileWriter = new FileWriter("RelatorioConsumo.csv");
                fileWriter.append(SUMMARY_READER);
                fileWriter.write(System.lineSeparator());

                StringBuilder builder;
                Supply firstCheck;
                Supply lastCheck;

                for (Vehicle v : vehicles.values()) {

                    // Calculates the mileage traveled
                    /*Comparator <Supply> mileageComparator = Comparator.comparing(
                        Supply::getCurrentMileage
                    );*/
                    firstCheck = v.getSupplies().get(v.getSupplies().firstKey());
                    lastCheck = v.getSupplies().get(v.getSupplies().lastKey());
                    //firstCheck = v.getSupplies().stream().min(mileageComparator).get();
                    //lastCheck = v.getSupplies().stream().max(mileageComparator).get();
                    Integer mileage = lastCheck.getCurrentMileage() - firstCheck.getCurrentMileage();
                    
                    // Calculates the time pass
                    Date initialDate = firstCheck.getSupplyDate();
                    Calendar dateStartCal = Calendar.getInstance();
                    dateStartCal.setTime(lastCheck.getSupplyDate());
                    dateStartCal.set(Calendar.HOUR_OF_DAY, 0);
                    dateStartCal.set(Calendar.MINUTE, 0);
                    dateStartCal.set(Calendar.SECOND, 0);
                    dateStartCal.set(Calendar.MILLISECOND, 0);
                    Calendar dateEndCal = Calendar.getInstance();
                    dateEndCal.setTime(initialDate);
                    dateEndCal.set(Calendar.HOUR_OF_DAY, 0);
                    dateEndCal.set(Calendar.MINUTE, 0);
                    dateEndCal.set(Calendar.SECOND, 0);
                    dateEndCal.set(Calendar.MILLISECOND, 0);
                    Long periodDays = (dateStartCal.getTimeInMillis() - dateEndCal.getTimeInMillis()) / MILISECONDS_PER_DAY;
                    
                    // Calculates the rest of data
                    Float totalCost = new Float(0.0);
                    Float totalGasolineLiters = new Float(0.0);
                    Float worstGasolineConsume = new Float(0.0);
                    Float bestGasolineConsume = Float.MAX_VALUE;
                    Float consume;
                    Integer distance;
                    for (Map.Entry<Integer, Supply> entry : v.getSupplies().entrySet()) {
                        totalCost += ((Supply) entry.getValue()).getGasolineLiters() * ((Supply) entry.getValue()).getCostPerLiter();
                        totalGasolineLiters += ((Supply) entry.getValue()).getGasolineLiters();
                        
                        distance = ((Supply) entry.getValue()).getCurrentMileage() - lastCheck.getCurrentMileage();
                        lastCheck = ((Supply) entry.getValue());
                        if(distance > 0) {
                        	consume = distance / ((Supply) entry.getValue()).getGasolineLiters();
                        	if(consume < bestGasolineConsume) {
                        		bestGasolineConsume = consume;
                        	}
                        	if(consume > worstGasolineConsume) {
                        		worstGasolineConsume = consume;
                        	}
                        }
                    }
                    Float averageGasolineConsume = mileage / totalGasolineLiters;
                    Float averageCost = totalCost / mileage;

                    builder = new StringBuilder();
                    builder.append("\"" + v.getBrand() + "\"").append(CSV_DELIMITER);
                    builder.append("\"" + v.getModel() + "\"").append(CSV_DELIMITER);
                    builder.append("\"" + String.valueOf(mileage) + "\"").append(CSV_DELIMITER);
                    builder.append("\"" + String.format("%.2f", totalCost) + "\"").append(CSV_DELIMITER);
                    builder.append("\"" + String.format("%.2f", totalGasolineLiters) + "\"").append(CSV_DELIMITER);
                    builder.append("\"" + dateFormat.format(initialDate) + "\"").append(CSV_DELIMITER);
                    builder.append("\"" + String.valueOf(periodDays.intValue()) + "\"").append(CSV_DELIMITER);
                    builder.append("\"" + String.format("%.2f", averageGasolineConsume) + "\"").append(CSV_DELIMITER);
                    builder.append("\"" + String.format("%.2f", worstGasolineConsume) + "\"").append(CSV_DELIMITER);
                    builder.append("\"" + String.format("%.2f", bestGasolineConsume) + "\"").append(CSV_DELIMITER);
                    builder.append("\"" + String.format("%.2f", averageCost) + "\"");

                    fileWriter.write(builder.toString());
                    fileWriter.write(System.lineSeparator());
                }

            } catch (IOException ie) {

                System.out.println("Não foi possível adicionar dados ao arquivo RelatorioConsumo.csv");
                ie.printStackTrace();

            } catch (Exception e) {

                System.out.println("Ocorreu um erro desconhecido.");
                e.printStackTrace();

            } finally {

                try {

                    fileWriter.flush();
                    fileWriter.close();

                } catch (IOException ie) {

                    System.out.println("Não foi possível fechar o arquivo RelatorioConsumo.csv");
                    ie.printStackTrace();

                }
            }

        }

    }

}
