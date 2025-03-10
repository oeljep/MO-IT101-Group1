PAYROLL HUB
This project is a simple payroll system that calculates work hours, applies a grace period, calculates government deduction, and net pay. It is implemented in Java using BufferedReader for input processing. The sample output is shown below:

--- Employee Database with Weekly Salary (Clock-In/Out, Grace Period, OT) ---
Employee # Name                      Position                       Clock-In        Clock-Out       Regular Hours   Overtime Hours  Weekly Salary  
-----------------------------------------------------------------------------------------------------------------------------------------
10001      Manuel III Garcia         Chief Executive Officer        7:08 AM         6:57 PM         11.82           0.00            590.83         
10002      Antonio Jr. Lim           Chief Operating Officer        7:02 AM         3:24 PM         8.37            0.00            502.00         
10003      Bianca Sofia Aquino       Chief Finance Officer          7:01 AM         3:54 PM         8.88            0.00            666.25         
-----------------------------------------------------------------------------------------------------------------------------------------

Program that:
- calculates the worked hours per cut-off (specifically to apply allowances)
- calculates gross pay based on the worked hours
- calculates the government deduction based on the provided matrix
- calculates netpay (or after government deduction)

Reads input from the following file:
- employeetest.csv
- attendancetest.csv

Parsing the files and storing them in the following array list:
-   actualClockin
-   clockOut

File Structure:

/PayrollHub
├── src/
│   ├── SAMPLE.java
│   ├── attendancetest.csv
│   ├── employeetest.csv
│   ├── employeeInfo.java
│   ├── governmentDeductions.java
│   ├── hoursWorked.java
│   └── salaryWorked.java
├── README.md
├── .gitignore
└── LICENSE

STATUS: In progress

THINGS TO IMPROVE:
- Syntax error is evident across files
- Improve file structuring
- 

CONTRIBUTORS:
Rowel Jepsani	              - oeljep
Juan Paolo Dente	          - Betalogs
Nichie Tolentino            -	Archie0405
Danielle Sophia Pasion      - DanielleSophiaFP
Mico Angelo Uy	            - ocims7
Maila Yruma	                - maila02
Ericson Renion	
Jose Maximo E. Ronquillo	  - joma001
James Angeles	              - Jamesangeles-byte
Edward Joseph Basilonia	    - EJB0624


