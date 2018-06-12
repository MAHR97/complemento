package autoCompletar;

import com.mxrck.autocompleter.TextAutoCompleter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

class ventanaFrame extends JFrame implements Serializable {

    private conexion conexion = new conexion();
    private Connection con;
    private PreparedStatement pst = null;
    private CompletarTexto caja;
    private JPanel panelContenedor;
    
    public ventanaFrame() {
        this.con = this.conexion.obtenerConexion();
        if(this.con != null) {
        	System.out.println("Conexion exitosa");
        }
        this.init();
        this.cargarDatos();
    }

    public void cargarDatos() {
    	try {
    		String sql = "SELECT * FROM busqueda;";
        	PreparedStatement pst = this.con.prepareStatement(sql);
        	pst.executeQuery();
        	ResultSet rs = pst.executeQuery();
        	while (rs.next()) {
        		this.caja.aniadirItem(rs.getString("datosBusqueda"));
        	}
    	}catch(Exception e) {}
    }
    
    
    public void init() {
        this.setTitle("Caja de busqueda");
        this.setSize(500, 150);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setLayout(null);
        this.panelContenedor = new JPanel();
        this.panelContenedor.setSize(500, 300);
        this.panelContenedor.setLayout(null);

        this.caja = new CompletarTexto();
        this.caja.setBounds(50, 25, 400, 30);
        this.caja.addKeyListener(new KeyAdapter(){
            public void keyPressed(java.awt.event.KeyEvent evt){
                    agregarBaseDatos(evt);
                
                
            }
            
        });
        
        this.panelContenedor.add(caja);
        this.add(this.panelContenedor);
        new Repintado (this.panelContenedor).start();
}
    
    private void agregarBaseDatos(java.awt.event.KeyEvent evt){
        
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            this.caja.aniadirItem(this.caja.getText());
            try {
            	String sql = "INSERT INTO busqueda (datosBusqueda) VALUES (?);";
            	
            	PreparedStatement pst = this.con.prepareStatement(sql);
            	pst.setString(1,this.caja.getText());
            	pst.executeUpdate();
            	
            }catch(Exception e) {
            	
            }
            
        }
        
    }

}

public class AutoCompletar {

    public static void main(String[] args) {
        new ventanaFrame().setVisible(true);
    }

}

class CompletarTexto extends JTextField implements Serializable {

    private TextAutoCompleter autoCompletar;
    private ArrayList<Object> lista = new ArrayList<>();
    private String[] datos1;

    public CompletarTexto() {
        this.autoCompletar = new TextAutoCompleter(this);
    }

    public void setDatos(String[] datos) {
        this.datos1 = datos;
        this.autoCompletar.addItems(datos);
    }

    public String[] getDatos() {
        return this.datos1;
    }

    public void setListaDatos(ArrayList<Object> lista) {
        this.lista = lista;
        this.autoCompletar.addItems(this.lista);
    }

    public ArrayList<Object> getTotalDatos() {
        return this.lista;
    }

    public void eliminarDatos() {
        this.autoCompletar.removeAllItems();
    }

    public void aniadirItem(String item) {
        this.autoCompletar.addItem(item);
    }

    public void eliminarItem(String item) {
        this.autoCompletar.removeItem(item);
    }
    
   

}


class Repintado extends Thread{
	private JPanel frame;
	
	public Repintado (JPanel frame) {
		this.frame = frame;
	}
	public void run() {
		
		try {
		while (true) {
			this.frame.repaint();
			Thread.sleep(50);
		}
	}catch(Exception e) {
		e.printStackTrace();}
	}
}
