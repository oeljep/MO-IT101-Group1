/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package payroll.hub;

/**
 *
 * @author Jomax
 */

/*
 * This program calculates payroll details for employees, including gross pay, deductions, and net pay,
 * based on their worked hours, allowances, and government-mandated contributions.
 */

import java.io.*;
import java.time.*;
import java.time.format.*;
import java.util.*;
import java.nio.file.*;
import java.nio.file.StandardOpenOption;

public class PayCalculator {

    // Fields for employee allowances, hourly rate, name, and position
    private int riceSubsidy; // Monthly rice subsidy allowance
    private int phoneAllowance; // Monthly phone allowance
    private int clothingAllowance; // Monthly clothing allowance
    private double hourlyRate; // Hourly rate of the employee
    private String name; // Name of the employee
    private String position; // Position of the employee

    // Constructor to initialize PayCalculator with allowances and hourly rate
    public PayCalculator(int riceSubsidy, int phoneAllowance, int clothingAllowance, double hourlyRate) {
        this.riceSubsidy = riceSubsidy;
        this.phoneAllowance = phoneAllowance;
        this.clothingAllowance = clothingAllowance;
        this.hourlyRate = hourlyRate;
    }

    // Getters and setters for employee name and position
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    // File paths for employee data and timekeeping records
    private static final String EMPLOYEE_INFO = "C:\\Users\\Jomax\\OneDrive\\Documents\\NetBeansProjects\\CompProg1\\CompProgtest2\\CP1Test\\MO-IT101-Group1\\src\\payroll\\hub\\databases\\hourlyrate_allowances.csv"; // Path to employee info CSV file
    private static final Map<String, PayCalculator> employeeMapRates = new HashMap<>(); // Stores employee data
    private static final String TIMEKEEPING_FILE = "C:\\Users\\Jomax\\OneDrive\\Documents\\NetBeansProjects\\CompProg1\\CompProgtest2\\CP1Test\\MO-IT101-Group1\\src\\payroll\\hub\\databases\\employeeinfo_timekeeping.csv"; // Path to timekeeping CSV file
    private static final int GRACE_PERIOD_MINUTES = 15; // Grace period for tardiness in minutes

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Load employee information from a CSV file into memory
        loadEmployeeInfo();

        // Prompt user to enter Employee ID and cutoff period dates
        System.out.print("Enter Employee ID: ");
        String employeeID = scanner.nextLine();

        System.out.println("Choose a pay period between 2024-06-03 and 2024-12-31");
        System.out.print("Enter start date (yyyy-MM-dd): ");
        String startDateInput = scanner.nextLine();
        System.out.print("Enter end date (yyyy-MM-dd): ");
        String endDateInput = scanner.nextLine();

        try {
            // Parse the entered dates into LocalDate objects
            LocalDate startDate = LocalDate.parse(startDateInput);
            LocalDate endDate = LocalDate.parse(endDateInput);

            // Check if the Employee ID exists in the loaded data
            if (employeeMapRates.containsKey(employeeID)) {
                computeAndDisplayPayroll(employeeID, startDate, endDate); // Calculate and display payroll details
            } else {
                System.out.println("Employee ID not found."); // Error message if Employee ID is invalid
            }
        } catch (DateTimeParseException e) {
            System.out.println("Error parsing date or time data: " + e.getMessage()); // Error message for invalid date format
        }
    }

    /**
     * Loads employee information from a CSV file into a HashMap for easy access.
     */
    private static void loadEmployeeInfo() {
        try (BufferedReader br = new BufferedReader(new FileReader(EMPLOYEE_INFO))) {
            String line;
            boolean isHeaderRow = true; // Flag to skip the header row

            while ((line = br.readLine()) != null) {
                if (isHeaderRow) {
                    isHeaderRow = false; // Skip the header row
                    continue;
                }

                String[] values = line.split(",");
                if (values.length >= 7) { // Ensure all required columns are present in the row
                    String employeeID = values[0].trim(); // Employee ID column
                    String employeeName = values[1].trim(); // Employee Name column

                    try {
                        int riceSubsidy = Integer.parseInt(values[2].trim()); // Parse rice subsidy allowance
                        int phoneAllowance = Integer.parseInt(values[3].trim()); // Parse phone allowance
                        int clothingAllowance = Integer.parseInt(values[4].trim()); // Parse clothing allowance
                        double hourlyRate = Double.parseDouble(values[5].trim()); // Parse hourly rate
                        String position = values[6].trim(); // Parse position

                        PayCalculator empInfo = new PayCalculator(riceSubsidy, phoneAllowance, clothingAllowance, hourlyRate);
                        empInfo.setName(employeeName); // Set employee name
                        empInfo.setPosition(position); // Set employee position

                        employeeMapRates.put(employeeID, empInfo); // Add employee data to HashMap
                    } catch (NumberFormatException e) {
                        System.err.println("Error parsing number in row: " + line); // Error message for invalid numeric data
                        continue; // Skip to the next row in case of error
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading the CSV file: " + e.getMessage()); // Error message for file reading issues
        }
    }
    //calculate withholding tax
     private static double calculateWithholdingTax(double taxableIncome) {
        if (taxableIncome <= 20832) {
            return 0;
        } else if (taxableIncome <= 33333) {
            return (taxableIncome - 20833) * 0.20;
        } else if (taxableIncome <= 666667) {
            return 2500 + (taxableIncome - 33333) * 0.25;
        } else if (taxableIncome <= 166667) {
            return 10833 + (taxableIncome - 666667) * 0.30;
        } else if (taxableIncome <= 666667) {
            return 40833.33 + (taxableIncome - 166667) * 0.32;
        } else {
            return 200833.33 + (taxableIncome - 666667) * 0.35;
        }
    }
    //calculate Pagibig Contribution
     private static double calculatePagibig(double grossPay) {
        double rate = (grossPay > 1500) ? 0.02 : 0.01;
        return Math.min(grossPay * rate, 100); // Maximum of 100
    }
    //calculate philhealth contribution
     private static double calculatePhilhealth(double grossPay) {
        double rate = 0.03;
        double contribution = grossPay * rate;
        return Math.min(contribution / 2, 900); // Employee pays half, max of 900
    }
    //calculate SSS contribution
     private static double calculateSSS(double grossPay) {
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

    /**
     * Computes payroll details for an employee within a specified cutoff period.
     */
    private static void computeAndDisplayPayroll(String employeeID, LocalDate startDate, LocalDate endDate) {
        String csvFilePath = "C:\\Users\\Jomax\\OneDrive\\Documents\\NetBeansProjects\\CompProg1\\CompProgtest2\\CP1Test\\MO-IT101-Group1\\src\\payroll\\hub\\MotorPHPayslip.csv";
   
        PayCalculator empInfo = employeeMapRates.get(employeeID);
        if (empInfo == null) {
            System.out.println("Employee ID not found in employee data.");
            return;
        }
        double hourlyRate = empInfo.hourlyRate;
        int riceSubsidy = empInfo.riceSubsidy;
        int phoneAllowance = empInfo.phoneAllowance;
        int clothingAllowance = empInfo.clothingAllowance;
        String employeeName = empInfo.getName();
        String position = empInfo.getPosition();
        double totalWorkedHours = 0;
        double totalOvertimeHours = 0;
        double totalTardinessHours = 0;
        int daysLate = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(TIMEKEEPING_FILE))) {
            String line;
            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
             boolean isHeaderRow = true;

            while ((line = br.readLine()) != null) {
                 if (isHeaderRow) {
                    isHeaderRow = false; // Skip the header row
                    continue;
                }
                String[] data = line.split(",");
                if (data.length < 6) continue;

                String id = data[0].trim();
                LocalDate recordDate = LocalDate.parse(data[3].trim(), dateFormatter);

                if (id.equalsIgnoreCase(employeeID) && !recordDate.isBefore(startDate) && !recordDate.isAfter(endDate)) {
                    LocalTime scheduledTimeIn = LocalTime.parse("08:00", timeFormatter); // Assuming 08:00 AM is the scheduled time-in
                    LocalTime timeIn = LocalTime.parse(data[4].trim(), timeFormatter);
                    LocalTime timeOut = LocalTime.parse(data[5].trim(), timeFormatter);
                    Duration workDuration = Duration.between(timeIn, timeOut);
                    double dailyWorkedHours = workDuration.toMinutes() / 60.0;

                    //Tardiness Calculation with Grace Period
                    Duration tardiness = Duration.between(scheduledTimeIn, timeIn);
                    if (tardiness.toMinutes() > GRACE_PERIOD_MINUTES && timeIn.isAfter(scheduledTimeIn)) {
                        totalTardinessHours += (tardiness.toMinutes() - GRACE_PERIOD_MINUTES) / 60.0;
                        daysLate++;
                    }

                    // Overtime Calculation
                    double regularHours = Math.min(dailyWorkedHours, 8);
                    double overtimeHours = Math.max(dailyWorkedHours - 8, 0);

                    totalWorkedHours += regularHours;
                    totalOvertimeHours += overtimeHours;
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading timekeeping file: " + e.getMessage());
            return;
        }

        double overtimeRate = 1.25; // 25% additional rate for OT
        double regularPay = totalWorkedHours * hourlyRate;
        double overtimePay = totalOvertimeHours * hourlyRate * overtimeRate;
        double totalAllowances = riceSubsidy + phoneAllowance + clothingAllowance;
        double grossPay = regularPay + overtimePay + totalAllowances;

        // Government Deductions (applied only on the first cutoff)
        boolean isFirstCutoff = startDate.getDayOfMonth() <= 15;
        double sssDeduction = isFirstCutoff ? calculateSSS(grossPay) : 0;
        double philhealthDeduction = isFirstCutoff ? calculatePhilhealth(grossPay) : 0;
        double pagibigDeduction = isFirstCutoff ? calculatePagibig(grossPay) : 0;

        double taxableIncome = grossPay - (sssDeduction + philhealthDeduction + pagibigDeduction);
        double totalWithholdingTax = calculateWithholdingTax(taxableIncome);
         // Dividing withholding tax into two cutoffs
        double withholdingTaxPerCutoff = totalWithholdingTax / 2;
        double withholdingTax = withholdingTaxPerCutoff;
        double totalDeductions = sssDeduction + philhealthDeduction + pagibigDeduction + withholdingTax;
        double netPay = grossPay - totalDeductions;

        //Tardiness Deduction
        double tardinessDeduction = totalTardinessHours * hourlyRate;

        // Format output for console and CSV
        String payslip = String.format(
    "\n--------------------------------------------\n" +
    "| MOTOR PH PAYROLL DETAILS                   \n" +
    "--------------------------------------------- \n" +
    "| Employee ID: %-30s \n" +
    "| Employee Name: %-28s \n" +
    "| Position: %-30s    \n" +
    "| Cut-off Period: %-28s \n" +
    "| Total Worked Hours: %-10s \n" +
    "| Tardiness: %-8s hours (PHP %-15s)\n" +
    "| Days Late: %-30s \n" +
    "----------------------------------------\n" +
    "| Hourly Rate: %-2s PHP \n" +
    "| Regular Pay: %-10s PHP \n" +
    "| Overtime Pay: %-10s PHP \n" +
    "| Rice Subsidy: %-2s PHP \n" +
    "| Phone Allowance: %-2s PHP \n" +
    "| Clothing Allowance: %-2s PHP \n" +
    "| Total Allowances: %-2s PHP \n" +
    "----------------------------------------\n" +
    "| GROSS PAY: %-2s PHP\n" +
    "----------------------------------------\n" +
    "| SSS Deduction: %-2s PHP\n" +
    "| PhilHealth Deduction: %-2s PHP\n" +
    "| Pag-IBIG Deduction: %-2s PHP\n" +
    "| Withholding Tax: %-2s PHP\n" +
    "----------------------------------------\n" +
    "| TOTAL DEDUCTIONS: %-2s PHP\n" +
    "----------------------------------------\n" +
    "| NET PAY: %-2s PHP\n" +
    "----------------------------------------\n",
    employeeID, employeeName, position,
    startDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) + " to " + endDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
    totalWorkedHours, totalTardinessHours, -tardinessDeduction, daysLate,
    hourlyRate, regularPay, overtimePay,
    riceSubsidy, phoneAllowance, clothingAllowance, totalAllowances,
    grossPay, sssDeduction, philhealthDeduction, pagibigDeduction, withholdingTax,
    totalDeductions, netPay
);

// Print to console
System.out.println(payslip);




    // CSV data
    List<String> csvData = Arrays.asList(
        employeeID, employeeName, position,
        startDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) + " to " + endDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
        String.valueOf(totalWorkedHours),
        String.valueOf(totalTardinessHours),
        String.valueOf(-tardinessDeduction),
        String.valueOf(daysLate),
        String.valueOf(hourlyRate),
        String.valueOf(regularPay),
        String.valueOf(overtimePay),
        String.valueOf(riceSubsidy),
        String.valueOf(phoneAllowance),
        String.valueOf(clothingAllowance),
        String.valueOf(totalAllowances),
        String.valueOf(grossPay),
        String.valueOf(sssDeduction),
        String.valueOf(philhealthDeduction),
        String.valueOf(pagibigDeduction),
        String.valueOf(withholdingTax),
        String.valueOf(totalDeductions),
        String.valueOf(netPay)
    );

    // Write the payroll details to a CSV file
    try {
        Path csvPath = Paths.get(csvFilePath);
        
        // Check if the file exists; if not, create it and write the header row
        if (!Files.exists(csvPath)) {
            List<String> header = Arrays.asList(
                "Employee ID", "Employee Name", "Position", "Cut-off Period",
                "Total Worked Hours", "Tardiness (Hours)", "Tardiness Deduction",
                "Days Late", "Hourly Rate", "Regular Pay", "Overtime Pay",
                "Rice Subsidy", "Phone Allowance", "Clothing Allowance",
                "Total Allowances", "Gross Pay", "SSS Deduction",
                "PhilHealth Deduction", "Pag-IBIG Deduction",
                "Withholding Tax", "Total Deductions", "Net Pay"
            );
            Files.write(csvPath, Collections.singleton(String.join(",", header)), StandardOpenOption.CREATE);
        }

        // Append the employee's payroll details to the CSV file
        Files.write(csvPath, Collections.singleton(String.join(",", csvData)), StandardOpenOption.APPEND);
    } catch (IOException e) {
        System.err.println("Error writing to CSV file: " + e.getMessage());
    }
    }
}
