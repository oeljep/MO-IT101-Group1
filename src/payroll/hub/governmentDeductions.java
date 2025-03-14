import java.util.Scanner;

public class governmentDeductions {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter Employee ID: ");
        String employeeID = scanner.nextLine().trim();

        System.out.print("Enter Gross Pay: ");
        double grossPay = scanner.nextDouble();

        // Compute government deductions
        double sss = Math.min(grossPay * 0.045, 900); // Maximum Php 900
        double pagIbig = Math.min(grossPay * 0.02, 100); // Maximum Php 100
        double philHealth = Math.min(grossPay * 0.025, 1200); // Maximum Php 1200

        // Total deductions
        double totalDeductions = sss + pagIbig + philHealth;

        // Display deductions
        System.out.println("----------------------------------------");
        System.out.println("|       GOVERNMENT DEDUCTIONS          |");
        System.out.println("----------------------------------------");
        System.out.printf("| Employee ID: %-25s |\n", employeeID);
        System.out.printf("| Gross Pay: Php %-19.2f |\n", grossPay);
        System.out.println("----------------------------------------");
        System.out.printf("| SSS Deduction: Php %-15.2f |\n", sss);
        System.out.printf("| Pag-IBIG Deduction: Php %-11.2f |\n", pagIbig);
        System.out.printf("| PhilHealth Deduction: Php %-9.2f |\n", philHealth);
        System.out.println("----------------------------------------");
        System.out.printf("| Total Deductions: Php %-13.2f |\n", totalDeductions);
        System.out.println("----------------------------------------");
    }
}
