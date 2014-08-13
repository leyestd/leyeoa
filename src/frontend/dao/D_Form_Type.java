package frontend.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import database.ConnectionPool;
import database.DBUtil;
import frontend.javabean.Typeform;


public class D_Form_Type {
	public static HashMap<Integer, Typeform> doSelectAll() {
		ConnectionPool pool = ConnectionPool.getInstance();
		Connection connection = pool.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;

		Typeform tform = new Typeform();
		HashMap<Integer, Typeform> typeforms = new HashMap<Integer, Typeform>();
		HashMap<Integer, String> workform = new HashMap<Integer, String>();
		int typeid = 0;
		int formid = 0;

		String query = "SELECT formtype.id,formtype.name,workform.id,workform.name From formtype INNER JOIN workform ON formtype.id=workform.type_id";

		try {
			ps = connection.prepareStatement(query);
			rs = ps.executeQuery();
			while (rs.next()) {
				if (typeid == 0) {
					typeid = rs.getInt("formtype.id");
				} else if (typeid != rs.getInt("formtype.id")) {
					tform.setWorkforms(workform);
					typeforms.put(typeid, tform);
					typeid = rs.getInt("formtype.id");
					tform = new Typeform();
					workform = new HashMap<Integer, String>();

				}

				formid = rs.getInt("workform.id");

				tform.setTypename(rs.getString("formtype.name"));

				workform.put(formid, rs.getString("workform.name"));
			}
			 if(typeid!=0) {
				 tform.setWorkforms(workform);
				 typeforms.put(typeid, tform);
         }
			return typeforms;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} finally {
			DBUtil.closeResultSet(rs);
			DBUtil.closePreparedStatement(ps);
			pool.freeConnection(connection);
		}

	}
}
