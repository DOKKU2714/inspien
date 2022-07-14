package inspien;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import inspien.vo.JoinVo;


public class DAO {
	static {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	private String dbUrl = "jdbc:oracle:thin:@211.106.171.36:11527:POS";
	private String dbId = "inspien01";
	private String dbPw = "inspien01";
//    private String sql;
//	private String insertSql = "INSERT INTO INSPIEN_XMLDATA_INFO "
//			+ "(ORDER_NUM, ITEM_SEQ, ORDER_ID, ORDER_DATE, ORDER_PRICE,"
//			+ "ORDER_QTY, RECEIVER_NAME, RECEIVER_NO, ETA_DATE, DESCIPTION ,DESTINATION "
//			+ ",ITEM_NAME, ITEM_QTY, ITEM_COLOR, ITEM_PRICE, SENDER)"
//			+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, '김도현')";
//
//	String selectSql = "SELECT * FROM " + "(SELECT * FROM INSPIEN_XMLDATA_INFO " + "WHERE SENDER = '김도현'"
//			+ "ORDER BY CURRENT_DT DESC) " + "WHERE ROWNUM <= 200";
//
//	String tableInfoSql = "SELECT A.COLUMN_ID AS NO" + ", B.COMMENTS AS \"논리명\"" + ", A.COLUMN_NAME AS \"물리명\""
//			+ ", A.DATA_TYPE AS \"자료 형태\"" + ", A.DATA_LENGTH AS \"길이\""
//			+ ", DECODE(A.NULLABLE, 'N', 'No', 'Y', 'Yes') AS \"Null 허용\"" + ", '' AS \"식별자\""
//			+ ", A.DATA_DEFAULT AS \"기본값\"" + ", B.COMMENTS AS \"코멘트\"" + "FROM  ALL_TAB_COLUMNS A " + "LEFT JOIN "
//			+ "ALL_COL_COMMENTS B " + "ON A.OWNER = B.OWNER " + "AND A.TABLE_NAME = B.TABLE_NAME "
//			+ "AND A.COLUMN_NAME = B.COLUMN_NAME " + "WHERE A.TABLE_NAME LIKE " + "'INSPIEN_XMLDATA_INFO' "
//			+ "ORDER BY A.COLUMN_ID";
//
//	String deleteSql = "DELETE FROM INSPIEN_XMLDATA_INFO " + "WHERE SENDER = '김도현'";

	public void select(String sql) throws ClassNotFoundException {

		try (Connection connection = DriverManager.getConnection(dbUrl, dbId, dbPw);
				PreparedStatement preparedStatement = connection.prepareStatement(sql);
				ResultSet resultSet = preparedStatement.executeQuery();) {
			System.out.println(resultSet);
			while (resultSet.next()) {
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
				System.out.println(resultSet.getString(15));
				System.out.println(resultSet.getString(16));
				System.out.println(resultSet.getString(17));
				System.out.println("\n");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void delete(String sql) throws ClassNotFoundException {
		try (Connection connection = DriverManager.getConnection(dbUrl, dbId, dbPw);
				PreparedStatement preparedStatement = connection.prepareStatement(sql);) {
			preparedStatement.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void insert(List<JoinVo> dataList, String sql) throws ClassNotFoundException, SQLException {
		Class.forName("oracle.jdbc.driver.OracleDriver"); // 드라이버 실행
		//try - with -resources 를 사용하면 자동으로 자원을 닫아주지만 try 문에서만 선언을 했기때문에 catch 에서 사용할 수 없어서 한번 더 감쌌다.
		try (Connection connection = DriverManager.getConnection(dbUrl, dbId, dbPw);) {
			try (PreparedStatement preparedStatement = connection.prepareStatement(sql);) {
				connection.setAutoCommit(false);
				for (int i = 0; i < dataList.size(); i++) {
					preparedStatement.setString(1, String.valueOf(dataList.get(i).getOrderNum()));
					preparedStatement.setString(2, String.valueOf(dataList.get(i).getItemSeq()));
					preparedStatement.setString(3, String.valueOf(dataList.get(i).getOrderId()));
					preparedStatement.setString(4, String.valueOf(dataList.get(i).getOrderDate()));
					preparedStatement.setString(5, String.valueOf(dataList.get(i).getOrderPrice()));
					preparedStatement.setString(6, String.valueOf(dataList.get(i).getOrderQty()));
					preparedStatement.setString(7, String.valueOf(dataList.get(i).getReceiverName()));
					preparedStatement.setString(8, String.valueOf(dataList.get(i).getReceiverNo()));
					preparedStatement.setString(9, String.valueOf(dataList.get(i).getEtaDate()));
					preparedStatement.setString(10, String.valueOf(dataList.get(i).getDesciption()));
					preparedStatement.setString(11, String.valueOf(dataList.get(i).getDestination()));
					preparedStatement.setString(12, String.valueOf(dataList.get(i).getItemName()));
					preparedStatement.setString(13, String.valueOf(dataList.get(i).getItemQty()));
					preparedStatement.setString(14, String.valueOf(dataList.get(i).getItemColor()));
					preparedStatement.setString(15, String.valueOf(dataList.get(i).getItemPrice()));
					preparedStatement.addBatch();
					preparedStatement.clearParameters();

					//500개씩 끊어서 execute
	//        		if (i % 500 == 0) {
	//        			preparedStatement.executeBatch();
	//        			preparedStatement.clearBatch();
	//        		}
				}
				preparedStatement.executeBatch();
				preparedStatement.clearBatch();
				connection.commit();
			} catch (Exception e) {
				connection.rollback();
				e.printStackTrace();
			}
		}
	}
}
