/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package payroll.hub;
import java.util.*;

/**
 *
 * @author Archie
 */
public class SalaryComputation { public static void main(String[] args) {
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
    

