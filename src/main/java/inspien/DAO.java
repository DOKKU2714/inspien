package inspien;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

public class DAO {
    private String dbUrl = "jdbc:oracle:thin:@211.106.171.36:11527:POS";
    private String dbId = "inspien01";
    private String dbPw = "inspien01";
//    private String sql;
    private String sql = "SELECT * FROM cols WHERE TABLE_NAME='INSPIEN_XMLDATA_INFO'";
//    String sql = "SELECT * FROM INSPIEN_XMLDATA_INFO ORDER BY CURRENT_DT DESC";
//    String sql = "INSERT INTO INSPIEN_XMLDATA_INFO (ORDER_NUM, ORDER_ID, ORDER_DATE, ORDER_PRICE, ORDER_QTY, RECEIVER_NAME, RECEIVER_NO, ETA_DATE, RECEIVER_NAME, DESTINATION, DESCIPTION";
    int count = 0;
    
    public void select() throws ClassNotFoundException {
		Class.forName("oracle.jdbc.driver.OracleDriver"); //드라이버 실행
		
        try (Connection connection = DriverManager.getConnection(dbUrl, dbId, dbPw);
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                ResultSet resultSet = preparedStatement.executeQuery();) {
        		System.out.println(resultSet);
        		while(resultSet.next()) {
        			System.out.println(resultSet.getString(1));
        			System.out.println(resultSet.getString(2));
        			System.out.println(resultSet.getString(3));
        			System.out.println(resultSet.getString(4));
        			System.out.println(resultSet.getString(5));
        			System.out.println(resultSet.getString(6));
        			System.out.println(resultSet.getString(7));
        			System.out.println(resultSet.getString(8));
        			System.out.println(resultSet.getString(9));
        			System.out.println(resultSet.getString(10));
        			System.out.println(resultSet.getString(11));
        			System.out.println(resultSet.getString(12));
        			System.out.println(resultSet.getString(13));
        			System.out.println(resultSet.getString(14));
        			System.out.println("\n");
//        			System.out.println(resultSet.getInt("ORDER_NUM") 
//        					+ "\t" 
//        					+ resultSet.getString("ORDER_ID")
//        					+ "\t"
//        					+ resultSet.getString("ORDER_ID")
//        					+ "\t"
//        					+ resultSet.getString("ORDER_DATE")
//        					+ "\t"
//        					+ resultSet.getString("ORDER_PRICE")
//        					+ "\t"
//        					+ resultSet.getString("ORDER_QTY")
//        					+ "\t"
//        					+ resultSet.getString("RECEIVER_NAME")
//        					+ "\t"
//        					+ resultSet.getString("RECEIVER_NO")
//        					+ "\t"
//        					+ resultSet.getString("ETA_DATE")
//        					+ "\t"
//        					+ resultSet.getString("RECEIVER_NAME")
//        					+ "\t"
//        					+ resultSet.getString("DESTINATION")
//        					+ "\t"
//        					+ resultSet.getString("DESCIPTION")
//        					+ "\t"
//        					+ resultSet.getString(12)
//        					+ "\t"
//        					+ resultSet.getString(13)
//        					+ "\t"
//        					+ resultSet.getString(14)
//        					+ "\t"
//        					+ resultSet.getString(15)
//        					+ "\t"
//        					+ resultSet.getString(16)
//        					+ "\t"
//        					+ resultSet.getString(17)
//					);
        		}
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void insert(List<Object> dataList) throws ClassNotFoundException {
		Class.forName("oracle.jdbc.driver.OracleDriver"); //드라이버 실행
		
        try (Connection connection = DriverManager.getConnection(dbUrl, dbId, dbPw);
                PreparedStatement preparedStatement = connection.prepareStatement(sql);) {
        	//list 의 길이 만큼 for문을 돌려서 insert문의 
        	preparedStatement.setString(0, "");
        	preparedStatement.setString(1, "");
        	preparedStatement.setString(2, "");
        	preparedStatement.setString(3, "");
        	preparedStatement.setString(4, "");
        	preparedStatement.setString(5, "");
        	preparedStatement.setString(6, "");
        	preparedStatement.setString(7, "");
        	preparedStatement.setString(8, "");
        	preparedStatement.setString(9, "");
        	preparedStatement.setString(10, "");
        	preparedStatement.setString(11, "");
        	preparedStatement.setString(12, "");
        	preparedStatement.setString(13, "");
        	preparedStatement.setString(14, "");
        	preparedStatement.setString(15, "");
        	preparedStatement.executeUpdate();
        	
        	//그냥 vo 두개 비교해서 detail 이랑 header 랑 ordernum 같은거 합쳐서 header 에 detail list 넣고
			//여기서 addbatch 잘 하면서 하면될듯 for문 2개 돌리면서
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
