/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package payroll.hub;

/**
 *
 * @author Jomax
 */

import java.io.FileWriter; // Import a class to write to files
import java.io.IOException; // Import a class to handle input/output errors
import java.io.PrintWriter; // Import a class to print to files
import java.util.HashMap; // Import a class for hash map data structure
import java.util.Map; // Import a class for map data structure
import java.util.Scanner; // Import a class to get user input

public class PayrollProgram {

    // Define the file path for hourly rates and allowances
    private static final String EMPLOYEE_INFO = "C:\\Users\\Jomax\\OneDrive\\Documents\\NetBeansProjects\\CompProg1\\CompProgtest2\\CP1Test\\MO-IT101-Group1\\src\\payroll\\hub\\databases\\hourlyrate_allowances.csv";
    
    // Create a map to store employee rates
    private static final Map<String, PayrollProgram> employeeMapRates = new HashMap<>();
    
    // Define the file path for timekeeping data
    private static final String TIMEKEEPING_FILE = "C:\\Users\\Jomax\\OneDrive\\Documents\\NetBeansProjects\\CompProg1\\CompProgtest2\\CP1Test\\MO-IT101-Group1\\src\\payroll\\hub\\databases\\employeeinfo_timekeeping.csv";
    
    // Define the grace period in minutes
    private static final int GRACE_PERIOD_MINUTES = 10;

    public static void main(String[] args) {
        // Create a Scanner object to get input from the user
        Scanner scanner = new Scanner(System.in);

        // Hardcoded file path for timekeeping data
        String timekeepingFilePath = "C:\\Users\\Mico\\Documents\\NetBeansProjects\\Mico's_Branch\\src\\payroll\\hub\\databases\\employeeinfo_timekeeping";

        // Ask the user for their employee ID
        System.out.print("Enter Employee ID: ");
        String employeeID = scanner.nextLine(); // Store the input as a string

        // Ask the user to choose a pay period
        System.out.println("Choose a pay period from 2024-06-03 to 2024-12-31");
        System.out.print("Enter start date (yyyy-MM-dd): ");
        String startDate = scanner.nextLine(); // Store the start date as a string

        System.out.print("Enter end date (yyyy-MM-dd): ");
        String endDate = scanner.nextLine(); // Store the end date as a string

        // Define the employee's details
        String employeeName = "Lim Antonio"; // Employee's name
        String position = "Chief Operating Officer"; // Employee's position
        double hourlyRate = 357.14; // Employee's hourly rate
        int daysLate = 11; // Number of days the employee was late

        // Define the payroll details
        double basicPayHours = 86.88; // Hours worked for basic pay
        double overtimeHours = 7.98; // Hours worked for overtime pay
        double tardinessHours = 19.48; // Hours deducted for tardiness

        double riceSubsidy = 1500.00; // Rice subsidy amount
        double phoneAllowance = 2000.00; // Phone allowance amount
        double clothingAllowance = 1000.00; // Clothing allowance amount

        double sssDeduction = 1125.00; // SSS deduction amount
        double philHealth = 586.40; // PhilHealth deduction amount
        double pagIbig = 100.00; // Pag-IBIG deduction amount
        double withholdingTax = 3487.27; // Withholding tax amount

        // Divide the withholding tax by 2 for the cut-offs
        double adjustedWithholdingTax = withholdingTax / 2.0;

        // Calculate the payroll amounts
        double basicPay = basicPayHours * hourlyRate; // Basic pay amount
        double overtimePay = overtimeHours * hourlyRate * 1.25; // Overtime pay amount
        double tardinessDeduction = tardinessHours * hourlyRate; // Tardiness deduction amount

        double grossPay = basicPay + overtimePay - tardinessDeduction + riceSubsidy + phoneAllowance + clothingAllowance; // Gross pay amount

        double totalDeductions = sssDeduction + philHealth + pagIbig + adjustedWithholdingTax; // Total deductions amount

        double netPay = grossPay - totalDeductions; // Net pay amount

        // Print the payroll statement
        System.out.println("======================================================");
        System.out.println("               MOTORPH PAYROLL STATEMENT              ");
        System.out.println("======================================================");
        System.out.println("  Employee: " + employeeName); // Print employee's name
        System.out.println("  ID: " + employeeID); // Print employee's ID
        System.out.println("  Position: " + position); // Print employee's position
        System.out.println("  Period: " + startDate + " to " + endDate); // Print pay period
        System.out.println("  Hourly Rate: P" + hourlyRate); // Print hourly rate
        System.out.println("  Days Late: " + daysLate); // Print days late
        System.out.println("------------------------------------------------------");
        System.out.println("  Description             Hours          Amount");
        System.out.println("------------------------------------------------------");
        System.out.printf("  Basic Pay                 %.2f       P%.2f%n", basicPayHours, basicPay); // Print basic pay details
        System.out.printf("  Overtime (1.25x rate)      %.2f       P%.2f%n", overtimeHours, overtimePay); // Print overtime pay details
        System.out.printf("  Tardiness               - %.2f      -P%.2f%n", tardinessHours, tardinessDeduction); // Print tardiness deduction details
        System.out.printf("  Rice Subsidy                          P%.2f%n", riceSubsidy); // Print rice subsidy amount
        System.out.printf("  Phone Allowance                       P%.2f%n", phoneAllowance); // Print phone allowance amount
        System.out.printf("  Clothing Allowance                    P%.2f%n", clothingAllowance); // Print clothing allowance amount
        System.out.println("------------------------------------------------------");
        System.out.printf("  GROSS PAY                            P%.2f%n", grossPay); // Print gross pay amount
        System.out.println("------------------------------------------------------");
        System.out.printf("  SSS Deduction                        -P%.2f%n", sssDeduction); // Print SSS deduction amount
        System.out.printf("  PhilHealth                           -P%.2f%n", philHealth); // Print PhilHealth deduction amount
        System.out.printf("  Pag-IBIG                            -P%.2f%n", pagIbig); // Print Pag-IBIG deduction amount
        System.out.printf("  Withholding Tax                     -P%.2f%n", adjustedWithholdingTax); // Print adjusted withholding tax amount
        System.out.println("------------------------------------------------------");
        System.out.printf("  TOTAL DEDUCTIONS                   -P%.2f%n", totalDeductions); // Print total deductions amount
        System.out.println("======================================================");
        System.out.printf("  NET PAY:                            P%.2f%n", netPay); // Print net pay amount
        System.out.println("======================================================");

        // Prepare data to write to the CSV file
        String[] headers = {
            "Employee", // Employee's name
            "ID", // Employee's ID
            "Position", // Employee's position
            "Period", // Pay period
            "Hourly Rate", // Hourly rate
            "Days Late", // Days late
            "Basic Pay", // Basic pay amount
            "Overtime Pay", // Overtime pay amount
            "Tardiness Deduction", // Tardiness deduction amount
            "Rice Subsidy", // Rice subsidy amount
            "Phone Allowance", // Phone allowance amount
            "Clothing Allowance", // Clothing allowance amount
            "Gross Pay", // Gross pay amount
            "SSS Deduction", // SSS deduction amount
            "PhilHealth", // PhilHealth deduction amount
            "Pag-IBIG", // Pag-IBIG deduction amount
            "Withholding Tax", // Withholding tax amount
            "Total Deductions", // Total deductions amount
            "Net Pay" // Net pay amount
        };

        String[] data = {
            employeeName, // Employee's name
            employeeID, // Employee's ID
            position, // Employee's position
            startDate + " to " + endDate, // Pay period
            String.valueOf(hourlyRate), // Hourly rate
            String.valueOf(daysLate), // Days late
            String.valueOf(basicPay), // Basic pay amount
            String.valueOf(overtimePay), // Overtime pay amount
            String.valueOf(tardinessDeduction), // Tardiness deduction amount
            String.valueOf(riceSubsidy), // Rice subsidy amount
            String.valueOf(phoneAllowance), // Phone allowance amount
            String.valueOf(clothingAllowance), // Clothing allowance amount
            String.valueOf(grossPay), // Gross pay amount
            String.valueOf(sssDeduction), // SSS deduction amount
            String.valueOf(philHealth), // PhilHealth deduction amount
            String.valueOf(pagIbig), // Pag-IBIG deduction amount
            String.valueOf(adjustedWithholdingTax), // Adjusted withholding tax amount
            String.valueOf(totalDeductions), // Total deductions amount
            String.valueOf(netPay) // Net pay amount
        };

        // Define the CSV file path
        String filePath = "C:\\Users\\Jomax\\OneDrive\\Documents\\NetBeansProjects\\CompProg1\\CompProgtest2\\CP1Test\\MO-IT101-Group1\\src\\payroll\\hub\\MotorPHPayslip.csv";

        // Call the method to write the payroll data to the CSV file
        writePayrollToCSV(filePath, headers, new String[][]{data});
    }

    /**
     * Method to write payroll data to a CSV file.
     * 
     * @param filePath Path to the CSV file.
     * @param headers Array of column headers.
     * @param data 2D array of payroll data.
     */
    public static void writePayrollToCSV(String filePath, String[] headers, String[][] data) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath, true))) {
            // Check if the file is empty to write the headers
            if (new java.io.File(filePath).length() == 0) {
                // Write the headers to the CSV file
                for (String header : headers) {
                    writer.print(header + ",");
                }
                writer.println(); // Move to the next line after writing headers
            }

            // Write each row of data to the CSV file
            for (String[] row : data) {
                for (String item : row) {
                    writer.print(item + ",");
                }
                writer.println(); // Move to the next line after each row
            }
            System.out.println("Successfully wrote to the CSV file.");
        } catch (IOException e) {
            // Handle any input/output errors during file writing
            System.err.println("Error writing to CSV file: " + e.getMessage());
        }
    }
}
