import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

class GetPropertyValues {

    private InputStream inputStream;

    List<String> getPropValues() throws IOException {

        List<String> resultList = null;
        try {
            Properties prop = new Properties();
            String propFileName = "config.properties";

            inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);

            if (inputStream != null) {
                prop.load(inputStream);
            } else {
                throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
            }

            // Store each property value
            String downloadPath = prop.getProperty("downloadPath");
            String width = prop.getProperty("width");
            String height = prop.getProperty("height");
            String numImages = prop.getProperty("numImages");
            String category = prop.getProperty("category");

            resultList = new ArrayList<>();
            resultList.add(downloadPath);
            resultList.add(width);
            resultList.add(height);
            resultList.add(numImages);
            resultList.add(category);

        } catch (Exception e) {
            System.out.println("Exception: " + e);
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }

        return resultList;
    }
}