package lk.ijse.palmoilfactory.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Employee {
    String empId;
    String empName;
    String empAddress;
    String empContact;
    double empSalary;
    String empType;
    String empSchId;
}
