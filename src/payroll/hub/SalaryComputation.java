/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package payroll.hub;

/**
 *
 * @author Nichie
 */

import java.io.*;
import java.nio.file.*;
import java.time.*;
import java.time.format.*;
import java.util.*;

public class SalaryComputation {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Hardcoded file path
        String filePath = "C:\\Users\\eihci\\Documents\\NetBeansProjects\\MO-IT101-Group1\\src\\payroll\\hub\\Copy of MotorPH Employee Data - Attendance Record (1).csv";

        System.out.print("Enter Employee ID: ");
        String employeeID = scanner.nextLine();

        // Ask for the cutoff period
        System.out.print("Enter start date (yyyy-MM-dd): ");
        String startDateInput = scanner.nextLine();
        System.out.print("Enter end date (yyyy-MM-dd): ");
        String endDateInput = scanner.nextLine();

        try {
            // Parse the input dates
            LocalDate startDate = LocalDate.parse(startDateInput);
            LocalDate endDate = LocalDate.parse(endDateInput);

            // Pass the dates to calculate total hours worked
            double totalWorkedHours = calculateTotalHoursWorked(filePath, employeeID, startDate, endDate);

            // Deduct lunch and small breaks
            int workDays = calculateWorkDays(startDate, endDate);
            totalWorkedHours = deductBreaks(totalWorkedHours, workDays); // Deduct breaks

            if (totalWorkedHours > 0) {
                computeAndDisplayPayroll(employeeID, totalWorkedHours);
            } else {
                System.out.println("No records found for the specified employee ID and cutoff period.");
            }
        } catch (IOException e) {
            System.out.println("Error reading the file: " + e.getMessage());
        } catch (DateTimeParseException e) {
            System.out.println("Error parsing date or time data: " + e.getMessage());
        }
    }

    private static double calculateTotalHoursWorked(String filePath, String employeeID, LocalDate startDate, LocalDate endDate) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(filePath));
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        double totalHours = 0;

        for (String line : lines) {
            String[] data = line.split(",");
            if (data.length < 6) continue; // Ensure there are enough columns

            String id = data[0].trim();
            if (id.equalsIgnoreCase(employeeID)) {
                // Parse and filter by date
                LocalDate recordDate = LocalDate.parse(data[3].trim(), dateFormatter);
                if (recordDate.isBefore(startDate) || recordDate.isAfter(endDate)) {
                    continue; // Skip records outside the cutoff period
                }

                LocalTime timeIn = LocalTime.parse(data[4].trim(), timeFormatter);
                LocalTime timeOut = LocalTime.parse(data[5].trim(), timeFormatter);

                Duration duration = Duration.between(timeIn, timeOut);
                totalHours += duration.toMinutes() / 60.0; // Convert minutes to hours
            }
        }

        return totalHours;
    }

    // **New method to calculate the number of workdays between two dates**
    private static int calculateWorkDays(LocalDate startDate, LocalDate endDate) {
        int workDays = 0;
        for (LocalDate date = startDate; !date.isAfter(endDate); date = date.plusDays(1)) {
            DayOfWeek dayOfWeek = date.getDayOfWeek();
            if (dayOfWeek != DayOfWeek.SATURDAY && dayOfWeek != DayOfWeek.SUNDAY) {
                workDays++; // Count only weekdays
            }
        }
        return workDays;
    }

    // **New method to deduct lunch and break hours**
    private static double deductBreaks(double totalWorkedHours, int workDays) {
        double lunchBreak = 1.0; // 1 hour per workday
        double smallBreaks = 0.5; // 30 minutes per workday
        double totalBreakHours = workDays * (lunchBreak + smallBreaks); // Total break hours
        return totalWorkedHours - totalBreakHours; // Deduct breaks from total worked hours
    }

    private static void computeAndDisplayPayroll(String employeeID, double totalWorkedHours) {
        double hourlyRate = 1500.71; // Example hourly rate
        double regularHours = Math.min(totalWorkedHours, 80); // Maximum of 80 regular hours
        double overtimeHours = Math.max(totalWorkedHours - 80, 0); // Overtime hours
        double overtimeRate = 1.25; // 25% additional rate for OT

        // Compute pay
        double regularPay = regularHours * hourlyRate;
        double overtimePay = overtimeHours * hourlyRate * overtimeRate;
        double grossPay = regularPay + overtimePay;

        // Print the payroll details
        System.out.println("----------------------------------------");
        System.out.println("|           PAYROLL DETAILS            |");
        System.out.println("----------------------------------------");
        System.out.printf("| Employee ID: %-25s |\n", employeeID);
        System.out.println("----------------------------------------");
        System.out.printf("| Total Worked Hours:    %.2f hours   |\n", totalWorkedHours);
        System.out.printf("| Gross Pay:             Php %.2f    |\n", grossPay);
        System.out.println("----------------------------------------");
    }
}
