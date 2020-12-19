package models;

import com.opencsv.bean.CsvBindByName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeModel {
    @CsvBindByName(column = "id")
    private Integer id;
    @CsvBindByName(column = "name")
    private String name;
    @CsvBindByName(column = "surname")
    private String surname;
    @CsvBindByName(column = "department_id")
    private Integer departmentId;
}
