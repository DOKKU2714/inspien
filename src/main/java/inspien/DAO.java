package inspien;

public class DAO {
    String dbUrl = "jdbc:oracle:thin:@localhost:1521:xe";
    String dbId = "hr";
    String dbPw = "hr";
    String sql = "TODO";
    int count = 0;
    
    public void insert() {
        Class.forName("oracle.jdbc.driver.OracleDriver"); //드라이버 로드
        
        try (Connection connection = DriverManager.getConnection(dbUrl, dbId, dbPw);
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                ResultSet resultSet = preparedStatement.executeQuery();) {
            whlie(resultSet.hasNext()) {
                   //todo
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
