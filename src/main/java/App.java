import services.CsvExtractorService;
import services.CsvJoinerService;

public class App {

    public static void main(String[] args) throws Exception {
        CsvJoinerService joinerService = new CsvJoinerService(new CsvExtractorService());
        joinerService.joinCsv("employee.csv", "departure.csv");
    }
}
