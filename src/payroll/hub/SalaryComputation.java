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
        String filePath = "C:\\Users\\Trapal RYN\\Documents\\NetBeansProjects\\MO-IT101-Group1 Rowel\\src\\payroll\\hub\\employeeinfo&timekeeping";

        System.out.print("Enter Employee ID: ");
        String employeeID = scanner.nextLine();

        // Ask for the cutoff period
        System.out.print("Enter start date (yyyy-MM-dd): ");
        String startDateInput = scanner.nextLine();
        System.out.print("Enter end date (yyyy-MM-dd): ");
        String endDateInput = scanner.nextLine();

        //Calling method
        loadEmployeeInfo();
        
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
        
        if (employeeMapRates.containsKey(employeeID)) {
            SalaryComputation empInfo = employeeMapRates.get(employeeID);
        
            double hourlyRate = empInfo.hourlyRate;
            double riceSubsidy = empInfo.riceSubsidy;
            double phoneAllowance = empInfo.phoneAllowance;
            double clothingAllowance = empInfo.clothingAllowance;
            
            //Can't initialized the total hours hence sample hours
            double sampleHours = 84.31;
            double sampleGrosspay = sampleHours * hourlyRate;
            double totalGross = sampleGrosspay + riceSubsidy + phoneAllowance + clothingAllowance;
            System.out.println("----------------------------------------");
            System.out.println("REAL VALUES");
            System.out.println("Hourly Rate: " + hourlyRate);
            System.out.println("Rice Subsidy: " + riceSubsidy);
            System.out.println("Phone Allowance: " + phoneAllowance);
            System.out.println("Clothing Allowance: " + clothingAllowance);
            System.err.println("GROSS PAY:  " + totalGross);
        } else {
            System.out.println("Employee ID not found.");
        }

    }
    int riceSubsidy;
    int phoneAllowance;
    int clothingAllowance;
    double hourlyRate;
    
    //Initialized a method to load hourly rate into HashMap
    public SalaryComputation(int riceSubsidy, int phoneAllowance, int clothingAllowance, double hourlyRate) {
        this.riceSubsidy = riceSubsidy;
        this.phoneAllowance = phoneAllowance;
        this.clothingAllowance = clothingAllowance;
        this.hourlyRate = hourlyRate;
    }
    
    //File path for hourly rates and allowances
    private static final String EMPLOYEE_INFO = "C:\\Users\\Trapal RYN\\Documents\\NetBeansProjects\\MO-IT101-Group1 Rowel\\src\\payroll\\hub\\emloyeetest.csv"; // Path to your CSV file
    //HashMap to store the hourly rates
    private static final Map<String, SalaryComputation> employeeMapRates = new HashMap<>();
    //putting EMPLOYEE_INFO to hourlyRate 
    private static void loadEmployeeInfo() {
        try (BufferedReader br = new BufferedReader(new FileReader(EMPLOYEE_INFO))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (values.length >= 6) { // Ensure all columns exist
                    String employeeID = values[0].trim();
                    int riceSubsidy = Integer.parseInt(values[2].trim());
                    int phoneAllowance = Integer.parseInt(values[3].trim());
                    int clothingAllowance = Integer.parseInt(values[4].trim());
                    double hourlyRate = Double.parseDouble(values[5].trim());

                    // Store all data inside EmployeeInfo object
                    SalaryComputation empInfo = new SalaryComputation(riceSubsidy, phoneAllowance, clothingAllowance, hourlyRate);
                    employeeMapRates.put(employeeID, empInfo);
                }
            }
//            System.out.println("Hourly rates and allowances loaded successfully.");
        } catch (IOException e) {
            System.err.println("Error reading the CSV file: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("Error parsing number: " + e.getMessage());
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
        double hourlyRate = 535.71; // Example hourly rate
        double regularHours = Math.min(totalWorkedHours, 80); // Maximum of 80 regular hours
        double overtimeHours = Math.max(totalWorkedHours - 80, 0); // Overtime hours
        double overtimeRate = 1.25; // 25% additional rate for OT
        
        //Sample info
        String name = "Manuel III Garcia";
        
        //Sample allowances
        double riceSubsidy = 15000;
        double phoneAllowance = 2000;
        double clothingAllowance = 1000;
        String startDateInput = "2024-07-01";
        String endDateInput = "2024-07-15";
        
        // Compute pay
        double regularPay = regularHours * hourlyRate;
        double overtimePay = overtimeHours * hourlyRate * overtimeRate;
        double totalAllowances = riceSubsidy + phoneAllowance + clothingAllowance;
        double grossPay = regularPay + overtimePay + totalAllowances;

        if(employeeMapRates.containsKey(employeeID)) {
            System.err.println("SAMPLE OUTPUT");
            System.out.println("----------------------------------------");
            System.out.println("|           PAYROLL DETAILS            |");
            System.out.println("----------------------------------------");
            System.out.println("| Employee Info");
            System.out.printf("| ID: %-25s |\n", employeeID);
            System.out.printf("| Name: %-25s |\n", name); //Insert name
            System.out.printf("| Hourly Rate: %-25s |\n", hourlyRate); //Insert hourly rate
            System.out.println("----------------------------------------");
            System.out.printf("| Cut-off Period: %-25s |\n", startDateInput, "-", endDateInput); //Insert hourly rate
            System.out.printf("| Total Worked Hours:    %.2f hours   |\n", totalWorkedHours);
        } else {
            System.out.println("Employee ID not found.");
        }
    }
}
/*
WHAT THE CODE CAN DO RIGHT:
Can properly calculate worked hours in given periods
Can display info
Can calculate grosspay with the given values

UPDATES:
Initialized employee information using HashMap = employeeMapRates
Can call and print the real values on HashMap 
Updated the layout for the output
Add sample allowances, Initialized sample values
Add totalAllowances variable
Creates SalaryComputation method

CURRENT ISSUES:
Unorganize method and printing syntaxes

CURRENT OUTPUT
run-single:
Enter Employee ID: 10001
Enter start date (yyyy-MM-dd): 2024-07-01
Enter end date (yyyy-MM-dd): 2024-07-15
SAMPLE OUTPUT
----------------------------------------
|           PAYROLL DETAILS            |
----------------------------------------
| Employee Info
| ID: 10001                     |
| Name: Manuel III Garcia         |
| Hourly Rate: 535.71                    |
----------------------------------------
| Cut-off Period: 2024 - 07 - 01            |
| Total Worked Hours:    86.43 hours   |
----------------------------------------
----------------------------------------
REAL VALUES
Hourly Rate: 535.71
Rice Subsidy: 1500.0
Phone Allowance: 2000.0
GROSS PAY:  49665.710100000004
Clothing Allowance: 1000.0
BUILD SUCCESSFUL (total time: 12 seconds)

TO DO:
Improve syntaxes
Methods are everywhere
Organize output
*/