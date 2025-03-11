/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author basil
 */


public class EmployeeDatabase {

    public static void main(String[] args) {

        // Simulated Data
        String[][] employeeData = {
                {"10001", "Garcia", "Manuel III", "", "", "", "", "", "", "Regular", "Chief Executive Officer"},
                {"10002", "Lim", "Antonio Jr.", "", "", "", "", "", "Regular", "Chief Operating Officer"},
                {"10003","Aquino","Bianca Sofia","","","","","","","Regular","Chief Finance Officer"}
        };

        // Display Employee Information in Console

        System.out.println("--- Employee Database ---");
        
        for (String[] emp : employeeData) {
            if(emp.length >= 10){
                System.out.println("Employee #: \t" + emp[0]);
                System.out.println("Name: \t\t" + emp[2] + ", "+emp[1]);
                System.out.println("Position: \t" +emp[9]);
                
                try{
                    String format = "%-10s %-25s %-30s\n";
                    String formattedLine = 
                            String.format(format, 
                                    emp[0], 
                                    emp[2] + ", "+emp[1], 
                                    emp[9]
                            );
                    
                    System.out.print(formattedLine);
                    
                    // Moved this line inside the try block for consistency
                    System.out.println("----------------------");
                    
                }catch(Exception e){
                    System.err.println("An error occurred while formatting data: "+e.getMessage());
                }
            } else {
                System.err.println("Incomplete data found.");
            }
            
        
    }
    
}
    
}