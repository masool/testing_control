
package bccontrol;

import java.sql.*;

public class OffchainDB {
    public  Connection getConnection(){
        Connection connection = null;

        try  {
//          connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/controlx?user=postgres&password=datdyn");
//        	connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/baba?user=baba&password=password");
//          connection = DriverManager.getConnection("jdbc:postgresql://ddcordb01.postgres.database.azure.com:5432/ddbcctrl01?user=ddcordb01@ddcordb01&password=J@urne52020&sslmode=require");
           connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/controlx?user=postgres&password=Admin@123");
            Class.forName("org.postgresql.Driver");
            System.out.println("Connected to PostgreSQL database!");
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Connection failure.");
            e.printStackTrace();
        }
        return connection;
    }

    public ResultSet queryDB(Connection connection,String query) throws AuditTrailException, SQLException {

        ResultSet rs = null;
        int res;
        try{
            Connection conn = null;
            Statement stmt = null;
            if(connection == null) {
                conn = getConnection();
            }
            stmt = conn.createStatement();

            if(!query.startsWith("SELECT")) {
                stmt.execute(query);
                 rs = stmt.getGeneratedKeys();
                if (rs != null && rs.next()) {
                    long key = rs.getLong(1);
                }
            }
            else if (query.startsWith("UPDATE")){
                res = stmt.executeUpdate(query);
            }
            else {
                 rs = stmt.executeQuery(query);
            }
            conn.close();
        }
        catch (SQLException ex) {
            System.out.println("Offchain DB  Catch : Message "+ex.getMessage());
            throw ex;
        }
        return rs;
    }

}
