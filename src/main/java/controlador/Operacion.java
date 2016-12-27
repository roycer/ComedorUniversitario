package controlador;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import modelo.Consumo;
import modelo.Estudiante;
import modelo.Pago;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

/**
 *
 * @author Roycer
 */
public class Operacion {
    
    SessionFactory sesion;
    public Operacion(){
        sesion = NewHibernateUtil.getSessionFactory();
    }
    
    public void crearEstudiante(Estudiante estudiante){
        Session session = sesion.openSession();
        try {
            Transaction tx = session.beginTransaction();
            session.save(estudiante);
            tx.commit();
        } finally{
            session.close();
        }
    }
    
    public void realizarPago(Pago pago){
        Session session = sesion.openSession();
        try {
            Transaction tx = session.beginTransaction();
            session.save(pago);
            tx.commit();
        } finally{
            session.close();
        }
    }
    
    public void consumirComedor(Consumo consumo){
        Session session = sesion.openSession();
        try {
            Transaction tx = session.beginTransaction();
            session.save(consumo);
            tx.commit();
        } finally{
            session.close();
        }
    }
     
    public void actualizarEstudiante(Estudiante estudiante){
        Session session = sesion.openSession();
        try {
            Transaction tx = session.beginTransaction();
            session.update(estudiante);
            tx.commit();
        } finally{
            session.close();
        }
    }
    
    public int cantidadConsumo(String tarjeta){
        int cont=0;
        Session session = sesion.openSession();
        try {
            Transaction tx = session.beginTransaction();
            Query q = session.createQuery("from Consumo where tarjeta='"+tarjeta+"'");
            List<Consumo> lista = q.list();
            Iterator<Consumo> iter=lista.iterator();
            tx.commit();
            
            DefaultListModel dlm = new DefaultListModel();
            while(iter.hasNext())
            {
                iter.next();
                cont++;  
            }   
        } finally {
            session.close();
        }   
        return cont;
    }
    
    public double saldoTotal(String tarjeta){
        int pagos=0;
        Session session = sesion.openSession();
        try {
            Transaction tx = session.beginTransaction();
            Query q = session.createQuery("from Pago where tarjeta='"+tarjeta+"'");
            List<Pago> lista = q.list();
            Iterator<Pago> iter=lista.iterator();
            tx.commit();
            
            DefaultListModel dlm = new DefaultListModel();
            while(iter.hasNext())
            {
                pagos+=((Pago)iter.next()).getDinero(); 
            }   
        } finally {
            session.close();
        }   
        return pagos;
    }
    
    
    public Boolean buscarConsumo(String numeroTarjeta, Date dia, String comida){
        Boolean flag=false;
        String sdia= new SimpleDateFormat("yyyy-MM-dd").format(dia);        
        Session session = sesion.openSession(); 
        try {
            Transaction tx = session.beginTransaction();        
            Query q = session.createQuery("from Consumo where tarjeta='"+numeroTarjeta+"' AND fecha='"+sdia+"' AND comida='"+comida+"'");
            if(q.list().size()>0){
                flag=true;
            }
            tx.commit();
        } finally {
            session.close(); 
        }
        return flag;
    }
    
    public Estudiante buscarEstudiante(String numeroTarjeta){
        Estudiante estudiante;
        Session session = sesion.openSession();
        try {
            Transaction tx = session.beginTransaction();
            estudiante=(Estudiante)session.get(Estudiante.class,numeroTarjeta);
            tx.commit();
        } finally {
            session.close();
        }  
        return estudiante;
    }
    
    public void cerrar(){
        sesion.close();
    }
    
    public ImageIcon convBytetoImagenIcon(byte[] foto) throws IOException{ 
        InputStream in = new ByteArrayInputStream(foto);
        BufferedImage image = ImageIO.read(in);
        return new ImageIcon(image.getScaledInstance(150, 150, Image.SCALE_DEFAULT));
    }
}
