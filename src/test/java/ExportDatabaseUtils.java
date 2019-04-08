import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;

public class ExportDatabaseUtils {
  public static void main(String[] args) throws Exception {
    Properties props = new Properties();
    props.load(new FileInputStream("/datasource.properties"));
    
    Class.forName(props.getProperty("driverClassName"));
    String url = props.getProperty("jdbcUrl");
    String username = props.getProperty("username");
    String password = props.getProperty("password");
    
    Connection jdbcConnection = DriverManager.getConnection(url, username, password);
    IDatabaseConnection connection = new DatabaseConnection(jdbcConnection);

    IDataSet fullDataSet = connection.createDataSet();
    FlatXmlDataSet.write(fullDataSet, new FileOutputStream("test_database.xml"));
  }
}