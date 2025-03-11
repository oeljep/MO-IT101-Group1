/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author basil
 */

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class EmployeeDatabaseSalary {

    public static void main(String[] args) {

        String[][] employeeData = {
                {"10001", "Garcia", "Manuel III", "Regular", "Chief Executive Officer", "50"},  // Hourly Rate
                {"10002", "Lim", "Antonio Jr.", "Regular", "Chief Operating Officer", "60"},   // Hourly Rate
                {"10003", "Aquino", "Bianca Sofia", "Regular", "Chief Finance Officer", "75"}    // Hourly Rate
        };

        System.out.println("--- Employee Database with Weekly Salary (Clock-In/Out, Grace Period, OT) ---");

        String format = "%-10s %-25s %-30s %-15s %-15s %-15s %-15s %-15s\n";

        System.out.printf(format, "Employee #", "Name", "Position", "Clock-In", "Clock-Out", "Regular Hours", "Overtime Hours", "Weekly Salary");
        System.out.println("-----------------------------------------------------------------------------------------------------------------------------------------");

        final int GRACE_PERIOD_MINUTES = 10;

        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("h:mm a");
        Random random = new Random();

        // Define start and end times for randomization
        LocalTime minTime = LocalTime.of(7, 0); // 7:00 AM
        LocalTime maxTime = LocalTime.of(19, 0); // 7:00 PM

        for (String[] emp : employeeData) {
            try {
                String employeeNumber = emp[0];
                String lastName = emp[1];
                String firstName = emp[2];
                String position = emp[4];
                String hourlyRateStr = emp[5];

                // Generate random clock-in time (7:00 AM to 7:10 AM)
                LocalDateTime clockInDateTime = generateClockInDateTime(random);
                LocalTime clockInTime = clockInDateTime.toLocalTime();

                // Generate random clock-out time (8 hours after clock-in up to 7:00 PM)
                LocalDateTime clockOutDateTime = generateClockOutDateTime(clockInDateTime, maxTime, random);
                LocalTime clockOutTime = clockOutDateTime.toLocalTime();

                String clockInTimeStr = clockInTime.format(timeFormatter);
                String clockOutTimeStr = clockOutTime.format(timeFormatter);

                double hourlyRate = Double.parseDouble(hourlyRateStr);

                // Calculate total hours worked
                Duration timeWorked = Duration.between(clockInTime, clockOutTime);
                double totalHours = (double) timeWorked.toMinutes() / 60;

                // Apply grace period
                double adjustedHours = totalHours;
                if (clockInTime.getMinute() > 10 && clockInTime.getHour() == 7) { //Late clock-in

                    adjustedHours = 0;

                }

                // Determine Overtime Threshold based on position
                int OVERTIME_THRESHOLD = 0;  // Initialize
                if (position.equals("Chief Executive Officer") || position.equals("Chief Operating Officer")) {
                    OVERTIME_THRESHOLD = 40;
                } else if (position.equals("Chief Finance Officer")) {
                    OVERTIME_THRESHOLD = 38;
                }

                // Calculate regular and overtime hours
                double regularHours = Math.min(adjustedHours, OVERTIME_THRESHOLD);
                double overtimeHours = 0;

                if (adjustedHours > OVERTIME_THRESHOLD) {
                    overtimeHours = Math.min(2, adjustedHours - OVERTIME_THRESHOLD); //Overtime is 2
                }

                // Calculate weekly salary
                double regularPay = regularHours * hourlyRate;
                double overtimePay = overtimeHours * hourlyRate * 1.5;
                double weeklySalary = regularPay + overtimePay;

                String formattedLine = String.format(format, employeeNumber, firstName + " " + lastName, position, clockInTimeStr, clockOutTimeStr, String.format("%.2f", regularHours), String.format("%.2f", overtimeHours), String.format("%.2f", weeklySalary));
                System.out.print(formattedLine);

            } catch (ArrayIndexOutOfBoundsException e) {
                System.err.println("Error: Incomplete employee data - " + e.getMessage());
            } catch (NumberFormatException e) {
                System.err.println("Error: Invalid number format - " + e.getMessage());
            } catch (NullPointerException e) {
                System.err.println("Error: Null value encountered - " + e.getMessage());
            }
        }
        System.out.println("-----------------------------------------------------------------------------------------------------------------------------------------");

    }

    // Helper function to generate a random LocalTime between two times
    private static LocalDateTime generateClockInDateTime(Random random) {
        LocalDateTime today = LocalDateTime.now().with(LocalTime.of(7, 0)); //Today at 7AM
        int randomMinute = random.nextInt(11); // Minutes 0 to 10
        return today.with(LocalTime.of(7, randomMinute)); // 7:00 AM to 7:10 AM

    }

    private static LocalDateTime generateClockOutDateTime(LocalDateTime clockInDateTime, LocalTime maxTime, Random random) {
        LocalDateTime startDateTime = clockInDateTime.plusHours(8); // At least 8 hours

        // Cap clock-out time at 7:00 PM
        LocalDateTime endDateTime = LocalDateTime.now().with(maxTime);
        if (startDateTime.isAfter(endDateTime)) {
            startDateTime = endDateTime; //Clock out is no later than 7PM
        }

        long startSeconds = startDateTime.toEpochSecond(ZoneOffset.UTC);
        long endSeconds = endDateTime.toEpochSecond(ZoneOffset.UTC);
        long difference = endSeconds - startSeconds;
        long randomEpochSeconds;

        if (difference > 0) {
            randomEpochSeconds = startSeconds + random.nextInt((int) difference);
        } else {
            randomEpochSeconds = startSeconds;
        }

        return LocalDateTime.ofEpochSecond(randomEpochSeconds, 0, ZoneOffset.UTC);
    }
}
