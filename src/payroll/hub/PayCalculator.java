/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package payroll.hub;

/**
 *
 * @author rowel
 */

import java.io.*;
import java.nio.file.*;
import java.time.*;
import java.time.format.*;
import java.util.*;

public class PayCalculator {

    // Fields for employee allowances and hourly rate
    private int riceSubsidy;
    private int phoneAllowance;
    private int clothingAllowance;
    private double hourlyRate;

    // Constructor for PayCalculator
    public PayCalculator(int riceSubsidy, int phoneAllowance, int clothingAllowance, double hourlyRate) {
        this.riceSubsidy = riceSubsidy;
        this.phoneAllowance = phoneAllowance;
        this.clothingAllowance = clothingAllowance;
        this.hourlyRate = hourlyRate;
    }

    // File path for hourly rates and allowances
    private static final String EMPLOYEE_INFO = "C:\\Users\\rowel\\OneDrive\\Documents\\NetBeansProjects\\Payroll Hub\\src\\payroll\\hub\\databases\\hourlyrate_allowances.csv";
    private static final Map<String, PayCalculator> employeeMapRates = new HashMap<>();

//MAIN METHOD
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Hardcoded file path for timekeeping data
        String timekeepingFilePath = "C:\\Users\\rowel\\OneDrive\\Documents\\NetBeansProjects\\Payroll Hub\\src\\payroll\\hub\\databases\\employeeinfo_timekeeping";

        // Load employee info from CSV
        loadEmployeeInfo();

        // Get employee ID and cutoff period from user
        System.out.print("Enter Employee ID: ");
        String employeeID = scanner.nextLine();

        //Input pay period
        System.out.println("Choose a pay period from 2024-06-03 to 2024-12-31");
        System.out.print("Enter start date (yyyy-MM-dd): ");
        String startDateInput = scanner.nextLine();
        System.out.print("Enter end date (yyyy-MM-dd): ");
        String endDateInput = scanner.nextLine();

        try {
            // Parse the input dates
            LocalDate startDate = LocalDate.parse(startDateInput);
            LocalDate endDate = LocalDate.parse(endDateInput);

            // Calculate total hours worked
            double totalWorkedHours = calculateTotalHoursWorked(timekeepingFilePath, employeeID, startDate, endDate);

            // Deduct lunch and small breaks
            int workDays = calculateWorkDays(startDate, endDate);
            totalWorkedHours = deductBreaks(totalWorkedHours, workDays);

            if (totalWorkedHours > 0) {
                computeAndDisplayPayroll(employeeID, totalWorkedHours, startDateInput, endDateInput);
            } else {
                System.out.println("No records found for the specified employee ID and cutoff period.");
            }
        } catch (IOException e) {
            System.out.println("Error reading the file: " + e.getMessage());
        } catch (DateTimeParseException e) {
            System.out.println("Error parsing date or time data: " + e.getMessage());
        }
    }

    // Load employee info from CSV into HashMap
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

                    // Store all data in PayCalculator object
                    PayCalculator empInfo = new PayCalculator(riceSubsidy, phoneAllowance, clothingAllowance, hourlyRate);
                    employeeMapRates.put(employeeID, empInfo);
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading the CSV file: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("Error parsing number: " + e.getMessage());
        }
    }

    // Calculate total hours worked by an employee within a cutoff period
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

    // Calculate the number of workdays between two dates
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

    // Deduct lunch and break hours
    private static double deductBreaks(double totalWorkedHours, int workDays) {
        double lunchBreak = 1.0; // 1 hour per workday
        double smallBreaks = 0.5; // 30 minutes per workday
        double totalBreakHours = workDays * (lunchBreak + smallBreaks); // Total break hours
        return totalWorkedHours - totalBreakHours; // Deduct breaks from total worked hours
    }
    
//COMPUTATION METHOD
    // Compute and display payroll details
    private static void computeAndDisplayPayroll(String employeeID, double totalWorkedHours, String startDateInput, String endDateInput) {
        if (employeeMapRates.containsKey(employeeID)) {
            PayCalculator empInfo = employeeMapRates.get(employeeID);

            double hourlyRate = empInfo.hourlyRate;
            double riceSubsidy = empInfo.riceSubsidy;
            double phoneAllowance = empInfo.phoneAllowance;
            double clothingAllowance = empInfo.clothingAllowance;

            // Compute pay
            double regularHours = Math.min(totalWorkedHours, 80); // Maximum of 80 regular hours
            double overtimeHours = Math.max(totalWorkedHours - 80, 0); // Overtime hours
            double overtimeRate = 1.25; // 25% additional rate for OT

            double regularPay = regularHours * hourlyRate;
            double overtimePay = overtimeHours * hourlyRate * overtimeRate;
            double totalAllowances = riceSubsidy + phoneAllowance + clothingAllowance;
            double grossPay = regularPay + overtimePay + totalAllowances;

            // Calculate government deductions
            double sssDeduction = calculateSSS(grossPay);
            double philhealthDeduction = calculatePhilhealth(grossPay);
            double pagibigDeduction = calculatePagibig(grossPay);

            // Calculate taxable income (gross pay minus non-taxable deductions)
            double taxableIncome = grossPay - (sssDeduction + philhealthDeduction + pagibigDeduction);

            // Calculate withholding tax
            double withholdingTax = calculateWithholdingTax(taxableIncome);

            // Calculate total deductions
            double totalDeductions = sssDeduction + philhealthDeduction + pagibigDeduction + withholdingTax;

            // Calculate net pay
            double netPay = grossPay - totalDeductions;
            
            // Display payroll details
            System.out.println("\n----------------------------------------");
            System.out.println("|       MMOTOR PH PAYROLL DETAILS        |");
            System.out.println("----------------------------------------");
            System.out.printf("| Employee ID: %-23s |\n", employeeID);
            System.out.printf("| Cut-off Period: %-10s to %-10s \n", startDateInput, endDateInput);
            System.out.printf("| Total Worked Hours: %-16.2f |\n", totalWorkedHours);
            System.out.println("----------------------------------------");
            System.out.printf("| Hourly Rate: %-23.2f |\n", hourlyRate);
            System.out.printf("| Regular Pay: %-23.2f |\n", regularPay);
            System.out.printf("| Overtime Pay: %-22.2f |\n", overtimePay);
            System.out.printf("| Total Allowances: %-18.2f |\n", totalAllowances);
            System.out.println("----------------------------------------");
            System.out.printf("| GROSS PAY: %-25.2f |\n", grossPay);
            System.out.println("----------------------------------------");
            System.out.printf("| SSS Deduction: %-21.2f |\n", sssDeduction);
            System.out.printf("| PhilHealth Deduction: %-14.2f |\n", philhealthDeduction);
            System.out.printf("| Pag-IBIG Deduction: %-16.2f |\n", pagibigDeduction);
            System.out.printf("| Withholding Tax: %-18.2f |\n", withholdingTax);
            System.out.println("----------------------------------------");
            System.out.printf("| TOTAL DEDUCTIONS: %-17.2f |\n", totalDeductions);
            System.out.println("----------------------------------------");
            System.out.printf("| NET PAY: %-26.2f |\n", netPay);
            System.out.println("----------------------------------------");
        } else {
            System.out.println("Employee ID not found.");
        }
    }
//GOVERNMENT DEDUCTION
    // SSS deduction
    public static double calculateSSS(double grossPay) {
        if (grossPay < 3250) {
            return 135.0;
        } else if (grossPay <= 3749.99) {
            return 157.5;
        } else if (grossPay <= 4249.99) {
            return 180.0;
        } else if (grossPay <= 4749.99) {
            return 202.5;
        } else if (grossPay <= 5249.99) {
            return 225.0;
        } else if (grossPay <= 5749.99) {
            return 247.5;
        } else if (grossPay <= 6249.99) {
            return 270.0;
        } else if (grossPay <= 6749.99) {
            return 292.5;
        } else if (grossPay <= 7249.99) {
            return 315.0;
        } else if (grossPay <= 7749.99) {
            return 337.5;
        } else if (grossPay <= 8249.99) {
            return 360.0;
        } else if (grossPay <= 8749.99) {
            return 382.5;
        } else if (grossPay <= 9249.99) {
            return 405.0;
        } else if (grossPay <= 9749.99) {
            return 427.5;
        } else if (grossPay <= 10249.99) {
            return 450.0;
        } else if (grossPay <= 10749.99) {
            return 472.5;
        } else if (grossPay <= 11249.99) {
            return 495.0;
        } else if (grossPay <= 11749.99) {
            return 517.5;
        } else if (grossPay <= 12249.99) {
            return 540.0;
        } else if (grossPay <= 12749.99) {
            return 562.5;
        } else if (grossPay <= 13249.99) {
            return 585.0;
        } else if (grossPay <= 13749.99) {
            return 607.5;
        } else if (grossPay <= 14249.99) {
            return 630.0;
        } else if (grossPay <= 14749.99) {
            return 652.5;
        } else if (grossPay <= 15249.99) {
            return 675.0;
        } else if (grossPay <= 15749.99) {
            return 697.5;
        } else if (grossPay <= 16249.99) {
            return 720.0;
        } else if (grossPay <= 16749.99) {
            return 742.5;
        } else if (grossPay <= 17249.99) {
            return 765.0;
        } else if (grossPay <= 17749.99) {
            return 787.5;
        } else if (grossPay <= 18249.99) {
            return 810.0;
        } else if (grossPay <= 18749.99) {
            return 832.5;
        } else if (grossPay <= 19249.99) {
            return 855.0;
        } else if (grossPay <= 19749.99) {
            return 877.5;
        } else if (grossPay <= 20249.99) {
            return 900.0;
        } else if (grossPay <= 20749.99) {
            return 922.5;
        } else if (grossPay <= 21249.99) {
            return 945.0;
        } else if (grossPay <= 21749.99) {
            return 967.5;
        } else if (grossPay <= 22249.99) {
            return 990.0;
        } else if (grossPay <= 22749.99) {
            return 1012.5;
        } else if (grossPay <= 23249.99) {
            return 1035.0;
        } else if (grossPay <= 23749.99) {
            return 1057.5;
        } else if (grossPay <= 24249.99) {
            return 1080.0;
        } else if (grossPay <= 24749.99) {
            return 1102.5;
        } else {
            return 1125.0; // For gross pay of 24750 and above
        }
    }

// PhilHealth deduction
    public static double calculatePhilhealth(double grossPay) {
        double rate = 0.03;
        double contribution = grossPay * rate;
        return Math.min(contribution / 2, 900); // Employee pays half, max of 900
    }

// Pag-IBIG deduction
    public static double calculatePagibig(double grossPay) {
        double rate = (grossPay > 1500) ? 0.02 : 0.01;
        return Math.min(grossPay * rate, 100); // Maximum of 100
    }

// Withholding Tax
    public static double calculateWithholdingTax(double taxableIncome) {
        if (taxableIncome <= 20832) {
            return 0;
        } else if (taxableIncome <= 33333) {
            return (taxableIncome - 20833) * 0.20;
        } else if (taxableIncome <= 66667) {
            return 2500 + (taxableIncome - 33333) * 0.25;
        } else if (taxableIncome <= 166667) {
            return 10833 + (taxableIncome - 66667) * 0.30;
        } else if (taxableIncome <= 666667) {
            return 40833.33 + (taxableIncome - 166667) * 0.32;
        } else {
            return 200833.33 + (taxableIncome - 666667) * 0.35;
        }
    }
}