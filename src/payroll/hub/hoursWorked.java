/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package payroll.hub;

/**
 *
 * @author rowel
 */
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class hoursWorked {
    private final LocalDateTime clockIn;
    private final LocalDateTime clockOut;
    private String clockingIn;
    private String clockingOut;
    private static final int GRACE_PERIOD_MINUTES = 10;

    //NEED TO REFORMULATE THIS
    public hoursWorked(LocalDateTime scheduledClockIn, LocalDateTime actualClockIn, LocalDateTime clockOut) {
        // Apply grace period for clock-in only
        this.clockIn = actualClockIn.isAfter(scheduledClockIn.plusMinutes(GRACE_PERIOD_MINUTES))
                       ? actualClockIn 
                       : scheduledClockIn;
        this.clockOut = clockOut;
    }
    
    //Get the duration from time in and clock in
    public double getTotalWorkedHours() {
        return Duration.between(clockIn, clockOut).toMinutes() / 60.0;
    }
    
    // Method to get clock-in time as an array of hours and minutes
    public int[] getClockInTime() {
        return splitTime(clockingIn);
    }

    // Method to get clock-out time as an array of hours and minutes
    public int[] getClockOutTime() {
        return splitTime(clockingOut);
    }
    
    public static int[] splitTime(String time) {
        String[] parts = time.split(":");
        return new int[]{Integer.parseInt(parts[0]), Integer.parseInt(parts[1])};
    }    
    
    public static void main(String[] args) {
        
//Running employeeInfo CLASS

//hoursWorked CLASS starts right here
        //Initializing biMonthly cutOffs
        int[] cutOffDays = {15,30}; //flexible cut off day
        LocalDate today = LocalDate.now();
        
        LocalDate firstCutoff = today.withDayOfMonth(cutOffDays[0]);
        LocalDate lastCutoff = today.withDayOfMonth(cutOffDays[1]);
        
        //Insert CSV file
        String inputCSV = "C:\\Users\\rowel\\OneDrive\\Documents\\NetBeansProjects\\Payroll Hub\\src\\payroll\\hub\\attendancetest.csv";
        String line1;
        //Delimiter Identifier
        String delimiter1 = ",";
        
        // Stores {hours, minutes} for clock-in and clock outs
        List<int[]> actualClockIn = new ArrayList<>();  
        List<int[]> clockOut = new ArrayList<>();
        
        try (BufferedReader br = new BufferedReader(new FileReader(inputCSV))) {
            while ((line1 = br.readLine()) != null) {
                //Spilt line into individual values
                String[] input = line1.split(delimiter1);
                
                //Parse different data types
                int idEmployee = Integer.parseInt(input[0].trim());
                String date = input[1].trim();
                String clockingIn = input[2].trim();
                String clockingOut = input[3].trim();
        
                // Split and store clock-in and clock-out times
                actualClockIn.add(splitTime(clockingIn));
                clockOut.add(splitTime(clockingOut));
            }
        } catch (IOException e) {
            System.out.println("Error reading the file: " + e.getMessage()); 
        } catch (NumberFormatException e) {
            System.out.println("Invalid data format: " + e.getMessage());
        }   
        
        LocalTime scheduledClockIn = LocalTime.of(8, 0);
                
        // getting time in and time outs
        for (int i = 0; i < actualClockIn.size(); i++) {
            int[] in = actualClockIn.get(i);
            int[] out = clockOut.get(i);
                        
            //Test print
            System.out.println(in[0] + ":" + in[1] + " | " + out[0] + ":" + out[1] + "");
        }
        
        /**
         *CURRENT STATUS:
         *initialized the clock in and clock out
         * 
         *TO DO:
         *calculation of duration per day
         * *introduce the grace period
         *identify the employee number
         * *then identify the dates
         * *
         */
    }
}

