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
public class DepartureModel {
    @CsvBindByName(column = "id")
    private Integer id;
    @CsvBindByName(column = "name")
    private String name;
}
