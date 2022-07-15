package inspien;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import inspien.vo.JoinVo;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Builder
/*DB접근을 위한 클래스*/
public class DAO {
	/*드라이버 실행*/
	static {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	private String dbUrl;
	private String dbId;
	private String dbPw;

	/*데이터를 제대로 넣었는지 확인하기 위한 select 쿼리 실행해주는 메소드*/
	public void select(String sql) throws ClassNotFoundException {

		try (Connection connection = DriverManager.getConnection(dbUrl, dbId, dbPw);
				PreparedStatement preparedStatement = connection.prepareStatement(sql);
				ResultSet resultSet = preparedStatement.executeQuery();) {
			while (resultSet.next()) {
				for (int i = 1 ; i < 18 ; i++) {
					System.out.print(resultSet.getString(i)+ "\t|\t");
				}
				System.out.println("\n");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*핸들링 한 xml 데이터를 insert 해주는 메소드*/
	public void insert(List<JoinVo> dataList, String sql) throws ClassNotFoundException, SQLException {
		//try - with -resources 를 사용하면 자동으로 자원을 닫아주지만 try 문에서만 선언을 했기때문에 catch 에서 사용할 수 없어서 한번 더 감쌌다.
		try (Connection connection = DriverManager.getConnection(dbUrl, dbId, dbPw);) {
			try (PreparedStatement preparedStatement = connection.prepareStatement(sql);) {
				connection.setAutoCommit(false);
				for (int i = 0; i < dataList.size(); i++) {
					JoinVo joinVo = dataList.get(i);
					preparedStatement.setString(1, String.valueOf(joinVo.getOrderNum()));
					preparedStatement.setString(2, String.valueOf(joinVo.getItemSeq()));
					preparedStatement.setString(3, String.valueOf(joinVo.getOrderId()));
					preparedStatement.setString(4, String.valueOf(joinVo.getOrderDate()));
					preparedStatement.setString(5, String.valueOf(joinVo.getOrderPrice()));
					preparedStatement.setString(6, String.valueOf(joinVo.getOrderQty()));
					preparedStatement.setString(7, String.valueOf(joinVo.getReceiverName()));
					preparedStatement.setString(8, String.valueOf(joinVo.getReceiverNo()));
					preparedStatement.setString(9, String.valueOf(joinVo.getEtaDate()));
					preparedStatement.setString(10, String.valueOf(joinVo.getDesciption()));
					preparedStatement.setString(11, String.valueOf(joinVo.getDestination()));
					preparedStatement.setString(12, String.valueOf(joinVo.getItemName()));
					preparedStatement.setString(13, String.valueOf(joinVo.getItemQty()));
					preparedStatement.setString(14, String.valueOf(joinVo.getItemColor()));
					preparedStatement.setString(15, String.valueOf(joinVo.getItemPrice()));
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
	
	/*직접 작성한 xmlData_SQL.xml 파일에서 쿼리를 읽어오는 메소드*/
	public String getQuery(String sqlId) throws ParserConfigurationException, SAXException, IOException {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();

		Document document = builder.parse("src/main/resource/sql/xmlData_SQL.xml");

		Element root = document.getDocumentElement();
		NodeList rootNodeList = root.getChildNodes();
		for (int i = 0; i < rootNodeList.getLength(); i++) {
			if (rootNodeList.item(i).getNodeType() == Node.ELEMENT_NODE) {
				Element sqlElement = (Element) rootNodeList.item(i);
				if ((sqlId.equals(sqlElement.getAttribute("id")))) {
					return sqlElement.getTextContent();
				}
			}
		}
		return null;
	}
}
