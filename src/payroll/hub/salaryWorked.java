/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package payroll.hub;

/**
 *
 * @author rowel
 */
import java.time.LocalTime;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class salaryWorked {
    
    /*
    SYNTAX IS NOT YET FINAL FOR CSV AS A DATABASED
    */
    private LocalDateTime clockIn;
    private LocalDateTime clockOut;
    private static final int GRACE_PERIOD_MINUTES = 10;
    
    public salaryWorked(LocalDateTime scheduledClockIn, LocalDateTime actualClockIn, LocalDateTime clockOut) {
        // Apply grace period for clock-in only
        this.clockIn = actualClockIn.isAfter(scheduledClockIn.plusMinutes(GRACE_PERIOD_MINUTES))
                       ? actualClockIn 
                       : scheduledClockIn;
        this.clockOut = clockOut;
    }
    
    public double getTotalWorkedHours() {
        return Duration.between(clockIn, clockOut).toMinutes() / 60.0;
    }
    
    private static final double HOURLY_RATE = 535.71;
    private static final int DAYS_WORKED = 7;
    private static final double DAILY_RATE = HOURLY_RATE * 8;
    
    public static double calculateHoursWorked(LocalTime clockIn, LocalTime clockOut) {
        // Define official start time and grace period
        LocalTime officialStartTime = LocalTime.of(9, 0);
        LocalTime gracePeriodEnd = officialStartTime.plusMinutes(10);
        
        // Adjust clock-in time if within grace period
        if (!clockIn.isBefore(officialStartTime) && !clockIn.isAfter(gracePeriodEnd)) {
            clockIn = officialStartTime;
        }
        
        // Calculate worked hours
        Duration workedDuration = Duration.between(clockIn, clockOut);
        return workedDuration.toMinutes() / 60.0;
    }

    public static double calculateWeeklySalary(double totalHoursWorked) {
        return totalHoursWorked * HOURLY_RATE;
    }

    public static void main(String[] args) {
        
        //Initialized employee info
        int idEmployee = 10001;
        String fullName = "Garcia Manuel III";
        String dateWorked = "06/03/2024";
        String dateWorkedto = "06/12/2024";

        // Example employee work hours
        LocalTime clockIn = LocalTime.of(8, 59);
        LocalTime clockOut = LocalTime.of(18, 31);
        // List to store multiple workdays
        List<hoursWorked> workLog = new ArrayList<>();
        workLog.add(new hoursWorked(LocalDateTime.of(2024, 3, 6, 9, 0), LocalDateTime.of(2024, 3, 6, 8, 59), LocalDateTime.of(2024, 3, 6, 17, 31)));
        workLog.add(new hoursWorked(LocalDateTime.of(2024, 4, 6, 9, 0), LocalDateTime.of(2024, 4, 6, 8, 59), LocalDateTime.of(2024, 4, 6, 19, 7)));
        workLog.add(new hoursWorked(LocalDateTime.of(2024, 5, 6, 9, 0), LocalDateTime.of(2024, 5, 6, 10, 57), LocalDateTime.of(2024, 5, 6, 21, 32)));
        workLog.add(new hoursWorked(LocalDateTime.of(2024, 6, 6, 9, 0), LocalDateTime.of(2024, 6, 6, 9, 32), LocalDateTime.of(2024, 6, 6, 19, 15)));
        workLog.add(new hoursWorked(LocalDateTime.of(2024, 7, 6, 9, 0), LocalDateTime.of(2024, 7, 6, 9, 46), LocalDateTime.of(2024, 7, 6, 19, 15)));
        workLog.add(new hoursWorked(LocalDateTime.of(2024, 10, 6, 9, 0), LocalDateTime.of(2024, 10, 6, 9, 10), LocalDateTime.of(2024, 10, 6, 18, 36)));
        workLog.add(new hoursWorked(LocalDateTime.of(2024, 11, 6, 9, 0), LocalDateTime.of(2024, 11, 6, 10, 30), LocalDateTime.of(2024, 10, 6, 20, 53)));

                
        // Display employee work details
        System.out.println("Employee ID: " + idEmployee);
        System.out.println("Full Name: " + fullName);
        System.out.println("Date Worked: " + dateWorked + " - " + dateWorkedto);
        
        System.out.println("");
        // Display results
        System.out.println("Hourly Rate: " + HOURLY_RATE);
        System.out.println("Daily Rate: " + DAILY_RATE);
        System.out.println("Days Worked: " + DAYS_WORKED);
        System.out.println("");
        
       
        double totalWorkedHours = 0;
        // Loop through the work log and display details
        for (hoursWorked work : workLog) {
            double dailyHours = work.getTotalWorkedHours();
            //totalWorkedHours += dailyHours;
            double weeklyHoursWorked = dailyHours * DAYS_WORKED;
            System.out.println("Total Weekly Hours Worked: " + String.format("%.2f", weeklyHoursWorked));
        }
        // Calculate total hours worked in a day
        double dailyHoursWorked = calculateHoursWorked(clockIn, clockOut);
        double weeklySalary = calculateWeeklySalary(totalWorkedHours);

        System.out.println("\nTotal Weekly Salary: PHP " + String.format("%.2f", weeklySalary));
    }
}

