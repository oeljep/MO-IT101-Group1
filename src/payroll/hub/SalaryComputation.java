/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/*
package payroll.hub;
import java.util.*;
import java.io.*;
import java.nio.file.*;
import java.time.*;
import java.time.format.*;
import java.io.FileReader;
*/
/**
 *
 * @author Archie
 */
/*public class SalaryComputation { public static void main(String[] args) {
        // 1. Input ng mga detalye ng empleyado
        try (Scanner sc = new Scanner(System.in)) {
            // Kunin ang pangalan ng empleyado
            System.out.print("Enter Employee Name: ");
            String employeeName = sc.nextLine();
            // Kunin ang numero ng empleyado
            System.out.print("Enter Employee Number: ");
            int employeeNumber = sc.nextInt();
            
            // 2. Input ng mga oras ng trabaho kada cutoff
            double totalWorkedHours = 0;
            double hourlyRate = 1500.71; // Halimbawa ng hourly rate
            System.out.println("Enter worked hours for 10 days of work(Monday-Friday for 2 weeks): ");
            for (int i = 1; i <= 10; i++) {
                // Kunin ang oras ng trabaho kada araw
                System.out.print("Day " + i + " Hour: ");
                totalWorkedHours += sc.nextDouble();
            }
            
            // Ibawas ang lunch break (1hr per day)
            totalWorkedHours -= 10; // 1 hour x 10 day change mo nlng if ilang araw
            
            // 3. computation Gross Pay
            double regularHours = Math.min(totalWorkedHours, 80); // Maximum na 80 oras para sa 10 araw
            double overtimeHours = Math.max(totalWorkedHours - 80, 0); // Overtime na oras
            double overtimeRate = 1.25; // 25% dagdag para sa OTY
            
            // Regular sahod
            double regularPay = regularHours * hourlyRate;
            // overtime
            double overtimePay = overtimeHours * hourlyRate * overtimeRate;
            // Total Salary (Gross Pay)
            double grossPay = regularPay + overtimePay;
            
            // 4. computation deduction gov't Para sa TUPAD
            double sss = Math.min(grossPay * 0.045, 900); // Maximum Php 900
            double pagIbig = Math.min(grossPay * 0.02, 100); // Maximum Php 100
            double philHealth = Math.min(grossPay * 0.025, 1200); // Maximum Php 1200
            
            // Income Tax computation
            double incomeTax = 0;
            if (grossPay > 25000) {
                // Kung lampas sa Php 25,000 ang sahod
                incomeTax = (grossPay - 25000) * 0.20 + 2500;
            } else if (grossPay > 20000) {
                // Kung lampas sa Php 20,000 pero di aabot sa Php 25,000
                incomeTax = (grossPay - 20000) * 0.15 + 1000;
            } else if (grossPay > 10000) {
                // Kung lampas sa Php 10,000 pero di aabot sa Php 20,000
                incomeTax = (grossPay - 10000) * 0.10;
            }
            
            // Total deduction
            double totalDeductions = sss + pagIbig + philHealth + incomeTax;
            
            // 5. Net Pay
            double netPay = grossPay - totalDeductions;
            
            // 6. Print out
            System.out.println("----------------------------------------");
            System.out.println("|           PAYROLL DETAILS            |");
            System.out.println("----------------------------------------");
            System.out.printf("| Pangalan: %-30s |\n", employeeName);
            System.out.printf("| Employee No.: %-22d |\n", employeeNumber);
            System.out.println("----------------------------------------");
            System.out.printf("| Total Worked Hours:    %.2f oras    |\n", totalWorkedHours);
            System.out.printf("| Gross Pay:             Php %.2f     |\n", grossPay);
            System.out.println("----------------------------------------");
            // Deduction 
            System.out.printf("| SSS Deduction:         Php %.2f     |\n", sss);
            System.out.printf("| Pag-IBIG Deduction:    Php %.2f     |\n", pagIbig);
            System.out.printf("| PhilHealth Deduction:  Php %.2f     |\n", philHealth);
            System.out.printf("| Income Tax Deduction:  Php %.2f     |\n", incomeTax);
            System.out.println("----------------------------------------");
            // Total Salary 
            System.out.printf("| Net Pay:               Php %.2f     |\n", netPay);
            System.out.println("----------------------------------------");
        }
    }
}


public class SalaryComputation {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        String file = "C:\\Users\\eihci\\Documents\\NetBeansProjects\\MO-IT101-Group1\\src\\payroll\\hub\\Copy of MotorPH Employee Data - Attendance Record.csv";
        BufferedReader reader = null;
        String line = " ";
        String filePath = scanner.nextLine();

        System.out.print("ID: ");
        String employeeName = scanner.nextLine();
        
        try {
            calculateTotalHoursWorked(filePath, employeeName);
        } catch (IOException e) {
            System.out.println("Error reading the file: " + e.getMessage());
        } catch (DateTimeParseException e) {
            System.out.println("Error parsing time data: " + e.getMessage());
        }
    }

    private static void calculateTotalHoursWorked(String filePath, String employeeName) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(filePath));
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        
        double totalHours = 0;

        for (String line : lines) {
            String[] data = line.split(",");
            if (data.length < 3) continue; // Ensure there are enough columns

            String name = data[0].trim();
            if (name.equalsIgnoreCase(employeeName)) {
                LocalTime timeIn = LocalTime.parse(data[4].trim(), timeFormatter);
                LocalTime timeOut = LocalTime.parse(data[5].trim(), timeFormatter);

                Duration duration = Duration.between(timeIn, timeOut);
                totalHours += duration.toMinutes() / 60.0; // Convert minutes to hours
            }
        }

        if (totalHours > 0) {
            System.out.printf("Total hours worked by %s: %.2f hours%n", employeeName, totalHours);
        } else {
            System.out.println("Employee not found or no records available.");
        }
    }
}
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

        // Compute government deductions
        double sss = Math.min(grossPay * 0.045, 900); // Max Php 900
        double pagIbig = Math.min(grossPay * 0.02, 100); // Max Php 100
        double philHealth = Math.min(grossPay * 0.025, 1200); // Max Php 1200

        // Compute income tax
        double incomeTax = 0;
        if (grossPay > 25000) {
            incomeTax = (grossPay - 25000) * 0.20 + 2500;
        } else if (grossPay > 20000) {
            incomeTax = (grossPay - 20000) * 0.15 + 1000;
        } else if (grossPay > 10000) {
            incomeTax = (grossPay - 10000) * 0.10;
        }

        // Total deductions and net pay
        double totalDeductions = sss + pagIbig + philHealth + incomeTax;
        double netPay = grossPay - totalDeductions;

        // Print the payroll details
        System.out.println("----------------------------------------");
        System.out.println("|           PAYROLL DETAILS            |");
        System.out.println("----------------------------------------");
        System.out.printf("| Employee ID: %-25s |\n", employeeID);
        System.out.println("----------------------------------------");
        System.out.printf("| Total Worked Hours:    %.2f hours   |\n", totalWorkedHours);
        System.out.printf("| Gross Pay:             Php %.2f    |\n", grossPay);
        System.out.println("----------------------------------------");
        System.out.printf("| SSS Deduction:         Php %.2f    |\n", sss);
        System.out.printf("| Pag-IBIG Deduction:    Php %.2f    |\n", pagIbig);
        System.out.printf("| PhilHealth Deduction:  Php %.2f    |\n", philHealth);
        System.out.printf("| Income Tax Deduction:  Php %.2f    |\n", incomeTax);
        System.out.println("----------------------------------------");
        System.out.printf("| Total Deductions:      Php %.2f    |\n", totalDeductions);
        System.out.printf("| Net Pay:               Php %.2f    |\n", netPay);
        System.out.println("----------------------------------------");
    }
}
