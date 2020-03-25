package jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Logger;
import java.util.regex.Pattern;

import org.apache.log4j.BasicConfigurator;
import org.junit.jupiter.api.Assertions;

public class MySqlTest {
	private static Pattern reg_Num = null;
	static Integer id;
	static Long yearOfEnrollmentLong;
	static String fName, mName, lName, borough, region, phoneNum, gender;
	public static void main(String[] args) throws ClassNotFoundException {
		Connection con = null;
		PreparedStatement smt = null;
		ResultSet result = null;
		Logger log = Logger.getLogger(MySqlTest.class.getName());
		BasicConfigurator.configure();
		String url = "jdbc:mysql://localhost:3306/student_db";
		String username = "user";
		String password = "password";
		reg_Num = Pattern.compile("^[-+]?[0-9]*\\.?[0-9]+$");
	
		try {
			con = DriverManager.getConnection(url, username, password);
			if (con != null) {
				log.info("Successfully connected to MySQL database test");
				smt = con.prepareStatement("SELECT * from student_detail");
				result = smt.executeQuery();

				System.out.println("My_Albums:");
				while (result.next()) {
					id = result.getInt("s_num");
					validateID();
					fName = result.getString("s_fName");
					mName = result.getString("s_mName");
					lName = result.getString("s_lName");
					borough = result.getString("s_addressCity");
					region = result.getString("s_addressRegion");
					phoneNum = result.getString("s_phone");
					gender = result.getString("s_gender");
					validateStudentPersonInfo();
					
					yearOfEnrollmentLong = result.getLong("s_yearOfEnrollment");
					validateEnrollmentYear(); 
					
					log.info("ID : " + id + "\n" + "Name : " + fName + " " + mName + " " + lName + "\n" + "Bourough : "
							+ borough + "\n" + "City : " + region + "\n" + "Phone Number : " + phoneNum + "\n"
							+ "Gender : " + gender + "\n" + "Year of Enrollment : " + yearOfEnrollmentLong + "\n");

				}
			}

		} catch (SQLException ex) {
			System.out.println("An error occurred while connecting MySQL databse");
			ex.printStackTrace();
		}
	}

	public static void validateID() {
		Assertions.assertTrue(isNumeric(String.valueOf(id)));
	}
	public static void validateEnrollmentYear() {
		Assertions.assertTrue(isNumeric(String.valueOf(yearOfEnrollmentLong)));
	}
	public static void validateStudentPersonInfo(){
		Assertions.assertNotNull(fName);
		Assertions.assertNotNull(mName);
		Assertions.assertNotNull(lName);
		Assertions.assertNotNull(borough);
		Assertions.assertNotNull(region);
		Assertions.assertNotNull(phoneNum);
		Assertions.assertNotNull(gender);
	}
	public static boolean isNumeric(String str_num) {
		if (str_num == null || str_num.equals("")) {
			return false;
		}
		return reg_Num.matcher(str_num).matches();
	}

}
