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
        //Insert CSV file
        String inputCSV1 = "C:\\Users\\rowel\\OneDrive\\Documents\\NetBeansProjects\\Payroll Hub\\src\\payroll\\hub\\emloyeetest.csv"; 
        String line;
        //Delimiter Identifier
        String delimiter = ","; 
        
        //Header
        System.out.println("--- Employee Info ---");
        
        String format = "%-15s %-15s %-15s %-15s %-15s %-15s %-15s\n";
        System.out.printf(format, "Employee #", "Name", "Hours Worked", "Gross Pay", "SSS", "PhilHealth", "Pag-Ibig", "Withholding Tax", "Net Pay");
        System.out.println("-----------------------------------------------------------------------------------------------------------------------------------------");

        try (BufferedReader br = new BufferedReader(new FileReader(inputCSV1))) {
            while ((line = br.readLine()) != null) {
                //Spilt line into individual values
                String[] input = line.split(delimiter);
                
                //Parse different data types
                int idEmployee = Integer.parseInt(input[0].trim());
                String name = input[1].trim();

                //Print parsed data                
                String formattedLine = String.format(format, idEmployee, name);
                System.out.println(formattedLine);
            }
        } catch (IOException e) {
            System.out.println("Error reading the file: " + e.getMessage()); 
        } catch (NumberFormatException e) {
            System.out.println("Invalid data format: " + e.getMessage());
        }   
        System.out.println("-----------------------------------------------------------------------------------------------------------------------------------------");
    }
}
