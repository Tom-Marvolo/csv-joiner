package models;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class EmployWithDepartureModel {
    private final Integer id;
    private final String name;
    private final String surname;
    private final String departmentName;
}
