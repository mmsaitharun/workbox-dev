package oneapp.incture.workbox.cmdtools;

import java.io.File;
import java.io.IOException;

public class CommandTools {
	
	final static String USER_DIR = System.getProperty("user.dir");
	final static String NEO_DIR = USER_DIR + "\\tools\\neo_tools";
	final static String NEO_COMMAND = "neo open-db-tunnel -a af91a028a -u p1942289352 -h eu1.hana.ondemand.com --id i4t -p Tha_incture29";

	public static void openDBTunnel() {
		Runtime runtime = Runtime.getRuntime();
		try {
			runtime.exec("cmd.exe /c start "+NEO_COMMAND+"", null, new File(NEO_DIR));
		} catch (IOException e) {
			System.out.println("Exception : "+e.getMessage());
		}
		
		/* Auto Tunnel Opener */
		/*try {
			Class.forName(DatabasePropertyProvider.HANA_JDBC_DRIVER);
			Connection con = DriverManager.getConnection(DatabasePropertyProvider.HANA_JDBC_URL, DatabasePropertyProvider.HANA_JDBC_USER, DatabasePropertyProvider.HANA_JDBC_PASSWORD);
			System.out.println(con.isValid(5000));
			System.out.println("Connected to Database");
		} catch (Exception e) {
			try {
				runtime.exec("cmd.exe /c start "+NEO_COMMAND+"", null, new File(NEO_DIR));
				try {
					Thread.sleep(15000);
				} catch (InterruptedException e1) {
					System.out.println("Exception : "+e1.getMessage());
				}
			} catch (IOException e1) {
				System.out.println("Exception : "+e1.getMessage());
			}
			CommandTools.openDBTunnel();
			System.out.println("Exception : "+e.getMessage());
		}*/
	}
}
