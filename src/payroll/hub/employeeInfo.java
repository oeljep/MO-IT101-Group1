/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package payroll.hub;

/**
 *
 * @author rowel
 */
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class employeeInfo {
    
    public static void main(String[] args) {
        String inputCSV = "C:\\Users\\rowel\\OneDrive\\Documents\\NetBeansProjects\\Payroll Hub\\src\\payroll\\hub\\emloyeetest.csv";
        String line;
        String delimiter = ",";

        System.out.println("--- Employee Info ---");
        String format = "%-15s %-15s \n";
        System.out.printf(format, "Employee #", "Name");
        System.out.println("-----------------------------------------------------------------------------------------------------------------------------------------");

        try (BufferedReader br = new BufferedReader(new FileReader(inputCSV))) {
            // Skip the header line
            br.readLine();

            while ((line = br.readLine()) != null) {
                String[] input = line.split(delimiter);

//                if (input.length != 9) {
//                    System.out.println("Invalid record: " + line);
//                    continue;
//                }

                try {
                    int idEmployee = Integer.parseInt(input[0].trim());
                    String name = input[1].trim();
                    
                    System.out.printf(format, idEmployee, name);
                } catch (NumberFormatException e) {
                    System.out.println("Invalid number format in record: " + line);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading the file: " + e.getMessage());
        }
        System.out.println("-----------------------------------------------------------------------------------------------------------------------------------------");
    }
}