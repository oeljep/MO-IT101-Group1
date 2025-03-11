/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author basil
 */
public class EmployeeDatabaseTime {

    public static void main(String[] args) {

        String[][] employeeData = {
                {"10001", "Garcia", "Manuel III", "", "", "", "", "", "", "Regular", "Chief Executive Officer", "40"},
                {"10002", "Lim", "Antonio Jr.", "", "", "", "", "", "", "Regular", "Chief Operating Officer", "45"},
                {"10003", "Aquino", "Bianca Sofia", "", "", "", "", "", "", "Regular", "Chief Finance Officer", "38"}
        };

        System.out.println("--- Employee Database with Hours Worked (with 10-Minute Grace Period & OT Calculation) ---");

        String format = "%-10s %-25s %-30s %-15s %-15s\n";

        System.out.printf(format, "Employee #", "Name", "Position", "Regular Hours", "Overtime Hours");
        System.out.println("---------------------------------------------------------------------------------------------------");

        // Define the grace period in minutes
        final int GRACE_PERIOD_MINUTES = 10;
        // Define the overtime threshold (hours above this are considered OT)
        final int OVERTIME_THRESHOLD = 40;  // Standard 40-hour work week

        for (String[] emp : employeeData) {
            try {
                String employeeNumber = emp[0];
                String lastName = emp[1];
                String firstName = emp[2];
                String position = emp[10];
                String hoursWorkedStr = emp[11];

                int hoursWorked = Integer.parseInt(hoursWorkedStr);

                // Apply the grace period: If hours worked are less than grace period minutes, then hours are 0.
                double adjustedHours = hoursWorked;
                if (hoursWorked * 60 < GRACE_PERIOD_MINUTES) {
                    adjustedHours = 0;
                }

                // Calculate regular hours and overtime hours
                double regularHours = adjustedHours <= OVERTIME_THRESHOLD ? adjustedHours : OVERTIME_THRESHOLD;
                double overtimeHours = adjustedHours > OVERTIME_THRESHOLD ? adjustedHours - OVERTIME_THRESHOLD : 0;

                //Enforce Overtime Rule of it needs to be over an hour
                if(hoursWorked - OVERTIME_THRESHOLD < 1){
                    overtimeHours = 0;
                }


                String formattedLine = String.format(format, employeeNumber, firstName + " " + lastName, position, regularHours, overtimeHours);
                System.out.print(formattedLine);

            } catch (ArrayIndexOutOfBoundsException e) {
                System.err.println("Error: Incomplete employee data - " + e.getMessage());
            } catch (NumberFormatException e) {
                System.err.println("Error: Invalid hours worked format - " + e.getMessage());
            } catch (NullPointerException e) {
                System.err.println("Error: Null value encountered - " + e.getMessage());
            }
        }
        System.out.println("---------------------------------------------------------------------------------------------------");

    }
}

