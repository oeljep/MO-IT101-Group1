import java.util.Scanner;

public class governmentDeductions {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Input employee details
        System.out.print("Enter Employee Name: ");
        String employeeName = scanner.nextLine();
        System.out.print("Enter Employee Number: ");
        String employeeNumber = scanner.nextLine();

        // Input hours worked for 10 days
        double totalHours = 0;
        for (int day = 1; day <= 10; day++) { // Loop through 10 days to get the hours worked for each day
            System.out.printf("Enter hours worked for day %d: ", day);
            double hoursWorked = scanner.nextDouble();
            totalHours += hoursWorked;
        }

        // Find employee's hourly rate
        double hourlyRate = getHourlyRate(employeeNumber); // to get the hourly rate for the employee based on their employee number

        // Calculate Gross Pay
        double grossPay = totalHours * hourlyRate;

        // Calculate Deductions
        double sssDeduction = calculateSSS(grossPay);
        double philhealthDeduction = calculatePhilhealth(grossPay);
        double pagibigDeduction = calculatePagibig(grossPay);
        double withholdingTax = calculateWithholdingTax(grossPay - sssDeduction - philhealthDeduction - pagibigDeduction);

        // Calculate the net pay by subtracting all the deductions from the gross pay
        double netPay = grossPay - sssDeduction - philhealthDeduction - pagibigDeduction - withholdingTax;

        // Display Payroll Details
        System.out.println("\n----------------------------------------");
        System.out.println("|           PAYROLL DETAILS            |");
        System.out.println("----------------------------------------");
        System.out.printf("| Name: %-28s|\n", employeeName);
        System.out.printf("| Employee Number: %-20s|\n", employeeNumber);
        System.out.println("----------------------------------------");
        System.out.printf("| Total Worked Hours:    %.2f Hours    |\n", totalHours);
        System.out.printf("| Gross Pay:             Php %.2f   |\n", grossPay);
        System.out.println("----------------------------------------");
        System.out.printf("| SSS Deduction:         Php %.2f    |\n", sssDeduction);
        System.out.printf("| PhilHealth Deduction:  Php %.2f    |\n", philhealthDeduction);
        System.out.printf("| Pag-IBIG Deduction:    Php %.2f    |\n", pagibigDeduction);
        System.out.printf("| Withholding Tax:       Php %.2f    |\n", withholdingTax);
        System.out.println("----------------------------------------");
        System.out.printf("| Net Pay:               Php %.2f   |\n", netPay);
        System.out.println("----------------------------------------");
    }

    // Hourly rate based on employee number
    public static double getHourlyRate(String employeeNumber) {
        switch (employeeNumber) {  // returns the hourly rate based on the employee number using a switch statement
            case "10001": return 535.71;
            case "10002": case "10003": case "10004": return 357.14;
            case "10005": case "10006": case "10010": case "10032": case "10033": case "10034": return 313.51;
            case "10007": case "10016": return 255.8;
            case "10008": case "10009": case "10018": case "10019": case "10023": case "10024": case "10029": case "10030": case "10031": return 133.93;
            case "10011": return 302.53;
            case "10012": return 229.02;
            case "10013": case "10014": case "10022": case "10025": case "10028": return 142.86;
            case "10015": return 318.45;
            case "10017": return 249.11;
            case "10020": case "10021": return 138.39;
            case "10026": case "10027": return 147.32;
            default: return 0; // Returns 0 if the employee number is not in the list
        }
    }

    // SSS deduction
    public static double calculateSSS(double grossPay) {
    if (grossPay < 3250) return 135.0; // If the gross pay is less than 3250, the deduction is 135.0 
    else if (grossPay <= 3749.99) return 157.5; 
    else if (grossPay <= 4249.99) return 180.0;
    else if (grossPay <= 4749.99) return 202.5;
    else if (grossPay <= 5249.99) return 225.0;
    else if (grossPay <= 5749.99) return 247.5;
    else if (grossPay <= 6249.99) return 270.0;
    else if (grossPay <= 6749.99) return 292.5;
    else if (grossPay <= 7249.99) return 315.0;
    else if (grossPay <= 7749.99) return 337.5;
    else if (grossPay <= 8249.99) return 360.0;
    else if (grossPay <= 8749.99) return 382.5;
    else if (grossPay <= 9249.99) return 405.0;
    else if (grossPay <= 9749.99) return 427.5;
    else if (grossPay <= 10249.99) return 450.0;
    else if (grossPay <= 10749.99) return 472.5;
    else if (grossPay <= 11249.99) return 495.0;
    else if (grossPay <= 11749.99) return 517.5;
    else if (grossPay <= 12249.99) return 540.0;
    else if (grossPay <= 12749.99) return 562.5;
    else if (grossPay <= 13249.99) return 585.0;
    else if (grossPay <= 13749.99) return 607.5;
    else if (grossPay <= 14249.99) return 630.0;
    else if (grossPay <= 14749.99) return 652.5;
    else if (grossPay <= 15249.99) return 675.0;
    else if (grossPay <= 15749.99) return 697.5;
    else if (grossPay <= 16249.99) return 720.0;
    else if (grossPay <= 16749.99) return 742.5;
    else if (grossPay <= 17249.99) return 765.0;
    else if (grossPay <= 17749.99) return 787.5;
    else if (grossPay <= 18249.99) return 810.0;
    else if (grossPay <= 18749.99) return 832.5;
    else if (grossPay <= 19249.99) return 855.0;
    else if (grossPay <= 19749.99) return 877.5;
    else if (grossPay <= 20249.99) return 900.0;
    else if (grossPay <= 20749.99) return 922.5;
    else if (grossPay <= 21249.99) return 945.0;
    else if (grossPay <= 21749.99) return 967.5;
    else if (grossPay <= 22249.99) return 990.0;
    else if (grossPay <= 22749.99) return 1012.5;
    else if (grossPay <= 23249.99) return 1035.0;
    else if (grossPay <= 23749.99) return 1057.5;
    else if (grossPay <= 24249.99) return 1080.0;
    else if (grossPay <= 24749.99) return 1102.5;
    else return 1125.0; // For gross pay of 24750 and above
}       

    // PhilHealth deduction
    public static double calculatePhilhealth(double grossPay) {
        double rate = 0.03;
        double contribution = grossPay * rate;
        return Math.min(contribution / 2, 900); // Employee pays half, max of 900
    }

    // Pag-IBIG deduction
    // If the gross pay is more than 1500, the rate is 2%. Otherwise, 1% lang.
    public static double calculatePagibig(double grossPay) {
        double rate = (grossPay > 1500) ? 0.02 : 0.01;
        return Math.min(grossPay * rate, 100); // Maximum of 100
    }

    // Withholding Tax
    // Calculates the withholding tax based on the taxable income.
    // The taxable income is the gross pay minus SSS, PhilHealth, and Pag-IBIG deductions.
    public static double calculateWithholdingTax(double taxableIncome) {
        if (taxableIncome <= 20832) return 0;
        else if (taxableIncome <= 33333) return (taxableIncome - 20833) * 0.20;
        else if (taxableIncome <= 66667) return 2500 + (taxableIncome - 33333) * 0.25;
        else if (taxableIncome <= 166667) return 10833 + (taxableIncome - 66667) * 0.30;
        else if (taxableIncome <= 666667) return 40833.33 + (taxableIncome - 166667) * 0.32;
        else return 200833.33 + (taxableIncome - 666667) * 0.35;
    }
}
