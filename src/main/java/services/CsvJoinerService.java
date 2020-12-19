package services;

import lombok.RequiredArgsConstructor;
import models.DepartureModel;
import models.EmployWithDepartureModel;
import models.EmployeeModel;

import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Csv joiner service.
 * The current implementation gives the ability to join two files with already predefined structure.
 * As an improvement this class can be extended to accept csv files names and join columns names.
 */
@RequiredArgsConstructor
public class CsvJoinerService {

    private final CsvExtractorService extractorService;

    /**
     * Join two files with existed structure (employee and departure).
     *
     * @param employeeFileName  the file employee file name
     * @param departureFileName the departure file name
     * @throws FileNotFoundException
     * @throws URISyntaxException
     */
    public void joinCsv(String employeeFileName, String departureFileName) throws FileNotFoundException, URISyntaxException {

        List<EmployeeModel> employeeBeans = extractorService.getCsvBeans(employeeFileName, EmployeeModel.class);
        List<DepartureModel> departureBeans = extractorService.getCsvBeans(departureFileName, DepartureModel.class);
        getJoinedCsvModels(departureBeans, employeeBeans).forEach(e ->
                System.out.format("employee_id: %d name: %s surname: %s departure: %s %sn",
                        e.getId(), e.getName(), e.getSurname(), e.getDepartmentName(), System.getProperty("line.separator")));
    }

    public List<EmployWithDepartureModel> getJoinedCsvModels(List<DepartureModel> departureBeans, List<EmployeeModel> employeeBeans) {
        Map<Integer, String> departureMap = departureBeans.stream()
                .collect(Collectors.toMap(DepartureModel::getId, DepartureModel::getName));

        return employeeBeans.stream()
                .filter(ef -> departureMap.get(ef.getDepartmentId()) != null)
                .map(e -> EmployWithDepartureModel.builder()
                        .id(e.getId())
                        .name(e.getName())
                        .surname(e.getSurname())
                        .departmentName(departureMap.get(e.getDepartmentId()))
                        .build())
                .collect(Collectors.toList());
    }
}

