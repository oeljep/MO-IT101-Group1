# **PAYROLL HUB**

## 📌 Project Overview  
Payroll Hub is a simple payroll system that:  
- Calculates work hours and applies a grace period  
- Computes government deductions  
- Determines net pay based on worked hours  
- Reads employee data from CSV files for processing  

This project is implemented in **Java**, using `BufferedReader` for input processing. 

---

For
## 📊 **Sample Output**  

```java
Enter Employee ID: 10001
Enter start date (yyyy-MM-dd): 2024-07-01
Enter end date (yyyy-MM-dd): 2024-07-15
-------------------------------------------
|           PAYROLL DETAILS               |
-------------------------------------------
| Employee Info                           |
| ID: 10001                               |
| Name: Manuel III Garcia                 |
| Hourly Rate: 535.71                     |
-------------------------------------------
| Cut-off Period: 2024-07-01 - 2024-07-15 |
| Total Worked Hours:    86.43 hours      |
| Rice Subsidy:          1,500.00         |
| Phone Allowance        2,000.00         |
| Clothing Allowance     1,000.00         |
|                                         |
| GROSS PAY             50,801.42         |
-------------------------------------------
| Social Security System:  900.00	    |
| Philhealth:	        450.00         |		
| Pag-Ibig:			     100.00         |		
| Withholding Tax			  0.00         |		
|                                         |
| TOTAL DEDUCTIONS			   1,450.00       |		
-------------------------------------------
| NET PAY                 49,351.42       |
-------------------------------------------
```

## ⚙️ **Features**  
✅ Calculates worked hours per cut-off (for allowances)  
✅ Computes gross pay based on worked hours  
✅ Applies government deductions based on a given matrix  
✅ Determines **net pay** (after deductions)  

---

## 📂 **File Handling**  
The program reads input from:  
📌 `employeetest.csv` (Employee details)  
📌 `attendancetest.csv` (Attendance records)  

These files are parsed and stored in the following **ArrayLists**:  
📌 `actualClockin`  
📌 `clockOut`  

---

## 🏗 **File Structure**  

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

## 🚧 **Project Status**  
🔧 **In Progress**  

### 🔍 **Things to Improve**  
- Fix syntax errors across files  
- User doesn't have any idea what are the available cut off periods.
- Hourly rate is absolute
- Allowances are not yet introduce
- Government deduction are not yet introduce

---

## 👥 **Contributors**  
| Name | GitHub Handle |
|------|--------------|
| Rowel Jepsani | [@oeljep](https://github.com/oeljep) |
| Juan Paolo Dente | [@Betalogs](https://github.com/Betalogs) |
| Nichie Tolentino | [@Archie0405](https://github.com/Archie0405) |
| Danielle Sophia Pasion | [@DanielleSophiaFP](https://github.com/DanielleSophiaFP) |
| Mico Angelo Uy | [@ocims7](https://github.com/ocims7) |
| Maila Yruma | [@maila02](https://github.com/maila02) |
| Ericson Renion | - |
| Jose Maximo E. Ronquillo | [@joma001](https://github.com/joma001) |
| James Angeles | [@Jamesangeles-byte](https://github.com/Jamesangeles-byte) |
| Edward Joseph Basilonia | [@EJB0624](https://github.com/EJB0624) |

---

🎯 **How to Use:**  
1️⃣ Clone this repository  
```sh
git clone https://github.com/your-repo/payroll-hub.git
