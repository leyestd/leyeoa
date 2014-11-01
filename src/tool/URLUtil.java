package tool;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

//页面抓取，用于取得待签工作表单数量

public class URLUtil {
	public static String getHtml(String urlString) {
		
		if (urlString.contains("id")) {
			StringBuilder html = new StringBuilder();
			InputStreamReader isr = null;
			BufferedReader br = null;
			try {

				URL url = new URL(urlString);
				HttpURLConnection conn = (HttpURLConnection) url
						.openConnection();
				isr = new InputStreamReader(conn.getInputStream());
				br = new BufferedReader(isr);
				String temp;
				while ((temp = br.readLine()) != null) {
					html.append(temp);
				}
			} catch (Exception e) {
				e.printStackTrace();
				return "";
			} finally {
				if (br != null) {
					try {
						br.close();
					} catch (IOException ioe) {
						ioe.printStackTrace();
					}
				}
				if (isr != null) {
					try {
						isr.close();
					} catch (IOException ioe) {
						ioe.printStackTrace();
					}
				}
			}
			return html.toString();
		} else {
			return "";
		}
	}
}