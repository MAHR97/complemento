package autoCompletar;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class conexion{
	private Connection conexion = null;
	private final String url = "jdbc:mysql://localhost/ejemplo";
	
	public Connection obtenerConexion(){
		try{
			Class.forName("com.mysql.jdbc.Driver");
			this.conexion = DriverManager.getConnection(this.url,"root","");
			return this.conexion;
		}catch(Exception e){
			e.printStackTrace();
			return this.conexion;
		}
	}
        public void cerrarConexion(){
            try {
                conexion.close();
            } catch (SQLException ex) {
                Logger.getLogger(conexion.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
}
