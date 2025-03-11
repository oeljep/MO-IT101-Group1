/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.comprogt7;

/**
 *
 * @author basil
 */


public class EmployeeT8 {
    int employeeId;
    String lastName;
    String firstName;
    String position;
    double basicSalary;
    double hoursWorked;
    String salaryType;

    public EmployeeT8(int employeeId, String lastName, String firstName, String position, double basicSalary, double hoursWorked, String salaryType) {
        this.employeeId = employeeId;
        this.lastName = lastName;
        this.firstName = firstName;
        this.position = position;
        this.basicSalary = basicSalary;
        this.hoursWorked = hoursWorked;
        this.salaryType = salaryType;
    }

    public double calculateSalary() {
        double calculatedSalary = 0.0;

        switch (salaryType.toLowerCase()) {
            case "monthly":
                double hourlyRateMonthly = basicSalary / 160;
                calculatedSalary = hourlyRateMonthly * hoursWorked;
                break;
            case "hourly":
                calculatedSalary = basicSalary * hoursWorked;
                break;
            case "annual":
                double hourlyRateAnnual = basicSalary / 2080;
                calculatedSalary = hourlyRateAnnual * hoursWorked;
                break;
            default:
                System.out.println("Unknown salary type. Returning basic salary.");
                calculatedSalary = basicSalary;
        }
        return calculatedSalary;
    }

    public void displayEmployeeDetails() {
        System.out.println("ID: " + employeeId);
        System.out.println("Name: " + firstName + " " + lastName);
        System.out.println("Position: " + position);
        System.out.println("Basic Salary: ₱" + basicSalary + " (" + salaryType + ")");
        System.out.println("Hours Worked: " + hoursWorked);
        System.out.println("Calculated Salary: ₱" + calculateSalary());
        System.out.println("-----------------------------");
    }

    public static void main(String[] args) {
        EmployeeT8 empMonthly = new EmployeeT8(1, "Doe", "John", "Developer", 50000, 160, "monthly");
        empMonthly.displayEmployeeDetails();

        EmployeeT8 empHourly = new EmployeeT8(2, "Smith", "Jane", "Designer", 500, 8, "hourly");
        empHourly.displayEmployeeDetails();

        EmployeeT8 empAnnual = new EmployeeT8(3, "Williams", "Mike", "Manager", 1200000, 160, "annual");
        empAnnual.displayEmployeeDetails();
    }
}
