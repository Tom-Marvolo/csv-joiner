package services;

import com.opencsv.bean.CsvToBeanBuilder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;

/**
 * Reads file convert the data to list of objects
 */
public class CsvExtractorService {

    /**
     * Use this method to get a list of objects from csv file.
     * The current implementation will work with small size files.
     * It might cause OutOfMemoryError we should keep that in mind during processing large CSV files as it will
     * uploaded into memory.
     *
     * @param csvFileName the name of csv file from. Should be places into resource folder
     * @param clazz       the class that will be used to map csv file
     * @param <T>
     * @return
     * @throws FileNotFoundException
     * @throws URISyntaxException
     */
    public <T> List<T> getCsvBeans(String csvFileName, Class<T> clazz) throws FileNotFoundException, URISyntaxException {
        URL resourcePath = getResourcePath(csvFileName);
        getFileReader(resourcePath);

        return new CsvToBeanBuilder<T>(getFileReader(resourcePath))
                .withType(clazz)
                .withIgnoreLeadingWhiteSpace(true)
                .withSeparator(';')
                .build()
                .parse();
    }

    private URL getResourcePath(String resourceName) throws FileNotFoundException {
        URL resource = Thread.currentThread().getContextClassLoader().getResource(resourceName);
        if (resource == null) {
            throw new FileNotFoundException("There is no file with name: " + resourceName);
        }
        return resource;
    }

    private FileReader getFileReader(URL pathToFile) throws URISyntaxException, FileNotFoundException {
        File file = new File(pathToFile.toURI());
        return new FileReader(file);
    }
}
