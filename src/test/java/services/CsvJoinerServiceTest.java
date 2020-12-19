package services;

import models.DepartureModel;
import models.EmployWithDepartureModel;
import models.EmployeeModel;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class CsvJoinerServiceTest {

    private CsvJoinerService joinerService;

    @Before
    public void setUp() {
        joinerService = new CsvJoinerService(new CsvExtractorService());
    }

    @Test
    public void joinExistedCsvFiles() throws FileNotFoundException, URISyntaxException {
        joinerService.joinCsv("departure.csv", "employee.csv");
    }

    @Test(expected = FileNotFoundException.class)
    public void joinExistedCsvNotExistedTests() throws FileNotFoundException, URISyntaxException {
        joinerService.joinCsv("not_existed_file.csv", "employee.csv");
    }

    @Test
    public void joinAllEmployeesTest() {
        List<DepartureModel> departureModels = List.of(new DepartureModel(1, "Finance"), new DepartureModel(2, "Medical"));

        List<EmployeeModel> employeeModels = List.of(new EmployeeModel(1, "test-finance", "test", 1),
                new EmployeeModel(2, "test-medical", "test", 2));
        List<EmployWithDepartureModel> joinedCsvModels = joinerService.getJoinedCsvModels(departureModels, employeeModels);

        Assert.assertEquals(2, joinedCsvModels.size());

        EmployWithDepartureModel employeeWithFinanceDeparture = joinedCsvModels.stream()
                .filter(j -> j.getName().equals("test-finance"))
                .findAny()
                .orElseThrow(() -> new IllegalStateException("list should have employee with name: test-finance"));

        Assert.assertEquals("Finance", employeeWithFinanceDeparture.getDepartmentName());

        EmployWithDepartureModel employeeWithMedical = joinedCsvModels.stream()
                .filter(j -> j.getName().equals("test-medical"))
                .findAny()
                .orElseThrow(() -> new IllegalStateException("list should have employee with name: test-finance"));

        Assert.assertEquals("Medical", employeeWithMedical.getDepartmentName());
    }

    @Test
    public void doNotJoinEmployeeWithNotExistedDeparture() throws FileNotFoundException, URISyntaxException {
        List<DepartureModel> departureModels = List.of(new DepartureModel(1, "Finance"), new DepartureModel(2, "Medical"));

        List<EmployeeModel> employeeModels = List.of(new EmployeeModel(1, "test-finance", "test", 1),
                new EmployeeModel(2, "test-Production", "test", 999));
        List<EmployWithDepartureModel> joinedCsvModels = joinerService.getJoinedCsvModels(departureModels, employeeModels);

        Assert.assertEquals(1, joinedCsvModels.size());

        EmployWithDepartureModel employeeWithFinanceDeparture = joinedCsvModels.stream()
                .filter(j -> j.getName().equals("test-finance"))
                .findAny()
                .orElseThrow(() -> new IllegalStateException("list should have employee with name: test-finance"));

        EmployWithDepartureModel employeeWithProductionDeparture = joinedCsvModels.stream()
                .filter(j -> j.getName().equals("test-Production"))
                .findAny()
                .orElse(null);

        Assert.assertNull(employeeWithProductionDeparture);
    }
}