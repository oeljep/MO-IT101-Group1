import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.text.NumberFormat;
import java.util.Locale;

public class EmployeeDatatxt {

    public static void main(String[] args) {

        String[][] employeeData = readEmployeeDataFromFile("employeeData.txt");

        if (employeeData == null) {
            System.out.println("Failed to read data from file.");
            return;
        }

        System.out.println("--- Employee Database with Weekly Salary (Clock-In/Out, Grace Period, OT) and Government Deductions ---");

        String format = "%-10s %-25s %-30s %-15s %-15s %-15s %-15s %-15s %-15s %-15s %-15s %-15s\n";

        System.out.printf(format, "Employee #", "Name", "Position", "Clock-In", "Clock-Out", "Regular Hours", "Overtime Hours", "Gross Salary", "SSS", "Pag-Ibig", "PhilHealth", "Net Salary");
        System.out.println("-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");

        final int GRACE_PERIOD_MINUTES = 10;

        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("h:mm a");
        Random random = new Random();

        // Define start and end times for randomization
        LocalTime minTime = LocalTime.of(7, 0); // 7:00 AM
        LocalTime maxTime = LocalTime.of(19, 0); // 7:00 PM

        // Use Chinese Yuan currency format
        NumberFormat cnyFormat = NumberFormat.getCurrencyInstance(Locale.CHINA);

        for (String[] emp : employeeData) {
            try {
                String employeeNumber = emp[0];
                String lastName = emp[1];
                String firstName = emp[2];
                String position = emp[4];
                String hourlyRateStr = emp[5];

                // Generate random clock-in time (7:00 AM to 7:10 AM)
                LocalDateTime clockInDateTime = generateClockInDateTime(random);
                LocalTime clockInTime = clockInDateTime.toLocalTime();

                // Generate random clock-out time (8 hours after clock-in up to 7:00 PM)
                LocalDateTime clockOutDateTime = generateClockOutDateTime(clockInDateTime, maxTime, random);
                LocalTime clockOutTime = clockOutDateTime.toLocalTime();

                String clockInTimeStr = clockInTime.format(timeFormatter);
                String clockOutTimeStr = clockOutTime.format(timeFormatter);

                double hourlyRate = Double.parseDouble(hourlyRateStr);

                // Calculate total hours worked
                Duration timeWorked = Duration.between(clockInTime, clockOutTime);
                double totalHours = (double) timeWorked.toMinutes() / 60;

                // Apply grace period
                double adjustedHours = totalHours;
                if (clockInTime.getMinute() > 10 && clockInTime.getHour() == 7) { //Late clock-in

                    adjustedHours = 0;

                }

                // Determine Overtime Threshold based on position
                int OVERTIME_THRESHOLD = 0;  // Initialize
                if (position.equals("Chief Executive Officer") || position.equals("Chief Operating Officer")) {
                    OVERTIME_THRESHOLD = 40;
                } else if (position.equals("Chief Finance Officer")) {
                    OVERTIME_THRESHOLD = 38;
                }

                // Calculate regular and overtime hours
                double regularHours = Math.min(adjustedHours, OVERTIME_THRESHOLD);
                double overtimeHours = 0;

                if (adjustedHours > OVERTIME_THRESHOLD) {
                    overtimeHours = Math.min(2, adjustedHours - OVERTIME_THRESHOLD); //Overtime is 2
                }

                // Calculate weekly salary
                double regularPay = regularHours * hourlyRate;
                double overtimePay = overtimeHours * hourlyRate * 1.5;
                double grossSalary = regularPay + overtimePay;

                // Calculate government deductions
                double sssContribution = calculateSSS(grossSalary);
                double pagIbigContribution = calculatePagIbig(grossSalary);
                double philHealthContribution = calculatePhilHealth(grossSalary);
                double incomeTax = calculateIncomeTax(grossSalary);

                // Calculate net salary
                double netSalary = grossSalary - sssContribution - pagIbigContribution - philHealthContribution - incomeTax;

                String formattedLine = String.format(format,
                        employeeNumber,
                        firstName + " " + lastName,
                        position,
                        clockInTimeStr,
                        clockOutTimeStr,
                        String.format("%.2f", regularHours),
                        String.format("%.2f", overtimeHours),
                        cnyFormat.format(grossSalary),
                        cnyFormat.format(sssContribution),
                        cnyFormat.format(pagIbigContribution),
                        cnyFormat.format(philHealthContribution),
                        cnyFormat.format(netSalary));
                System.out.print(formattedLine);

            } catch (ArrayIndexOutOfBoundsException e) {
                System.err.println("Error: Incomplete employee data - " + e.getMessage());
            } catch (NumberFormatException e) {
                System.err.println("Error: Invalid number format - " + e.getMessage());
            } catch (NullPointerException e) {
                System.err.println("Error: Null value encountered - " + e.getMessage());
            }
        }
        System.out.println("-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");

    }

    // Helper function to generate a random LocalTime between two times
    private static LocalDateTime generateClockInDateTime(Random random) {
        LocalDateTime today = LocalDateTime.now().with(LocalTime.of(7, 0)); //Today at 7AM
        int randomMinute = random.nextInt(11); // Minutes 0 to 10
        return today.with(LocalTime.of(7, randomMinute)); // 7:00 AM to 7:10 AM

    }

    private static LocalDateTime generateClockOutDateTime(LocalDateTime clockInDateTime, LocalTime maxTime, Random random) {
        LocalDateTime startDateTime = clockInDateTime.plusHours(8); // At least 8 hours

        // Cap clock-out time at 7:00 PM
        LocalDateTime endDateTime = LocalDateTime.now().with(maxTime);
        if (startDateTime.isAfter(endDateTime)) {
            startDateTime = endDateTime; //Clock out is no later than 7PM
        }

        long startSeconds = startDateTime.toEpochSecond(ZoneOffset.UTC);
        long endSeconds = endDateTime.toEpochSecond(ZoneOffset.UTC);
        long difference = endSeconds - startSeconds;
        long randomEpochSeconds;

        if (difference > 0) {
            randomEpochSeconds = startSeconds + random.nextInt((int) difference);
        } else {
            randomEpochSeconds = startSeconds;
        }

        return LocalDateTime.ofEpochSecond(randomEpochSeconds, 0, ZoneOffset.UTC);
    }

    // Government Deduction Calculation Methods (Approximation only)
    private static double calculateSSS(double grossSalary) {
        // Sample SSS calculation (replace with actual logic based on salary range)
        return Math.min(grossSalary * 0.045, 500.0); // Example: 4.5% of salary, capped at 500
    }

    private static double calculatePagIbig(double grossSalary) {
        // Sample Pag-Ibig calculation (replace with actual logic, usually a fixed percentage)
        return Math.min(grossSalary * 0.02, 100.0); // Example: 2% of salary, capped at 100
    }

    private static double calculatePhilHealth(double grossSalary) {
        // Sample PhilHealth calculation (replace with actual logic based on salary range)
        return grossSalary * 0.015; // Example: 1.5% of salary
    }

    private static double calculateIncomeTax(double grossSalary) {
        // Sample income tax calculation (replace with actual tax brackets and rates)
        if (grossSalary <= 20833) {
            return 0;
        } else if (grossSalary <= 33333) {
            return (grossSalary - 20833) * 0.20;
        } else {
            return 2500 + (grossSalary - 33333) * 0.25; // Example with simplified tax brackets
        }
    }

    private static String[][] readEmployeeDataFromFile(String filename) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line).append("\n");
            }
            String[] lines = sb.toString().split("\n");
            String[][] data = new String[lines.length][];
            for (int i = 0; i < lines.length; i++) {
                data[i] = lines[i].split(",");
            }
            return data;
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
            return null;
        }
    }
}
