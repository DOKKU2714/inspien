package inspien;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DAO {
//    String dbUrl = "jdbc:oracle:thin:@localhost:1521:xe";
    String dbUrl;
    String dbId;
    String dbPw;
    String sql;
    int count = 0;
    
    public void insert() throws ClassNotFoundException {
		Class.forName("oracle.jdbc.driver.OracleDriver"); //드라이버 실행
		
        try (Connection connection = DriverManager.getConnection(dbUrl, dbId, dbPw);
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                ResultSet resultSet = preparedStatement.executeQuery();) {
//            whlie(resultSet.next()) {
//                   //todo
//            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
