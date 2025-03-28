# **PAYROLL HUB**

## 📌 Project Overview  
Payroll Hub is a simple payroll system that:  
- Calculates work hours and applies a grace period  
- Computes government deductions  
- Determines net pay based on worked hours  
- Reads employee data from CSV files for processing  

This project is implemented in **Java**.

---

## 📊 **Expected Output**  

```java
run:
Enter Employee ID: 10001
Choose a pay period from 2024-06-03 to 2024-12-31
Enter start date (yyyy-MM-dd): 2024-06-16
Enter end date (yyyy-MM-dd): 2024-06-28

======================================================
               MOTORPH PAYROLL STATEMENT              
======================================================
  Employee: Garcia Manuel III                   
  ID: 10001                                    
  Position: Chief Executive Officer              
  Period: 2024-06-16 to 2024-06-28              
  Hourly Rate: P535.71
  Days Late: 10
------------------------------------------------------
  Description             Hours          Amount
------------------------------------------------------
  Basic Pay                 77.80       P   41678.24
  Overtime (1.25x rate)     13.52       P    9051.27
  Tardiness               -  9.07      -P    4857.10
  Rice Subsidy                          P    1500.00
  Phone Allowance                       P    2000.00
  Clothing Allowance                    P    1000.00
------------------------------------------------------
  GROSS PAY                            P    55229.50
------------------------------------------------------
  SSS Deduction                        -P       0.00
  PhilHealth                           -P       0.00
  Pag-IBIG                             -P       0.00
  Withholding Tax                      -P    7974.13
------------------------------------------------------
  TOTAL DEDUCTIONS                  -P      7974.13
======================================================
  NET PAY:                           P     47255.38
======================================================

Payroll data saved to C:\Users\rowel\OneDrive\Documents\NetBeansProjects\Payroll Hub\src\payroll\hub\MotorPHPayslip.csv
BUILD SUCCESSFUL (total time: 18 seconds)
```

## ⚙️ **Features**  
✅ Calculates worked hours per cut-off (for allowances)  
✅ Computes gross pay based on worked hours  
✅ Applies government deductions based on a given matrix  
✅ Determines **net pay** (after deductions)  

---

## 📂 **File Handling**  
The program reads input from:  
📌 `employeeinfo_timekeeping.csv` (Timekeeping records)  input
📌 `hourlyrate_allowances.csv` (Hourly rate & allowances)  input

📌 `src/MotorPHPayslip.csv` (Writes the output into csv format) 

---

## 🏗 **File Structure**  
```sh
/PayrollHub
├── src/
│   ├── MotorPHPayslip.csv
│   ├── PayCalculator.java
├── payroll.hub.databeses/
│   ├── MotorPHPayslip.csv
│   ├── employeeinfo_timekeeping.csv
│   ├── hourlyrate_allowances.csv
├── README.md
├── .gitignore
└── LICENSE
```
## 🚧 **Project Status**  
🔧 **Completed**  

### 🔍 **Things to Improve**  
-

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
| Jose Maximo E. Ronquillo | [@joma001](https://github.com/joma001) |
| James Angeles | [@Jamesangeles-byte](https://github.com/Jamesangeles-byte) |
| Edward Joseph Basilonia | [@EJB0624](https://github.com/EJB0624) |

---

🎯 **How to Use:**  

1️⃣ Clone this repository  
```sh
git clone https://github.com/your-repo/payroll-hub.git
```
2️⃣ Run **Payroll Hub**
