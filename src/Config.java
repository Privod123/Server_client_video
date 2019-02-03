import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Config {

    private static final String PATH_TO_PROPERTIES = "resources/config.properties";

    public static String getProperti(String nameConfig) {
        String readConfig = null;
        Properties prop = new Properties();

        try ( FileInputStream fileInputStream = new FileInputStream(PATH_TO_PROPERTIES)){
            //обращаемся к файлу и получаем данные
            prop.load(fileInputStream);
            readConfig = prop.getProperty(nameConfig);
        } catch (IOException e) {
            System.out.println("Ошибка в программе: файл " + PATH_TO_PROPERTIES + " не обнаружено");
            e.printStackTrace();
        }
        return readConfig;
    }
}
