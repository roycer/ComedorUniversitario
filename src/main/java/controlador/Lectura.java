package controlador;

import com.fazecast.jSerialComm.SerialPort;
import java.io.OutputStream;
import java.util.Scanner;
import javax.swing.JFrame;
import vista.Administracion;
import vista.Caja;
import vista.Comedor;

/**
 *
 * @author Roycer
 */
public class Lectura extends Thread{
    
    String puerto;
    SerialPort puertoserial;
    Boolean flagLectura;
    String numeroTarjeta;
    JFrame frame;
    
    public Lectura(String puerto, JFrame frame) {
        this.puerto = puerto;
        this.frame = frame;
        this.flagLectura=false;
        this.puertoserial = SerialPort.getCommPort(puerto);
        
    }
    
    @Override
    public void run(){    
        try {
            flagLectura = false;
            puertoserial.setComPortTimeouts(SerialPort.TIMEOUT_SCANNER, 0, 0);
            puertoserial.openPort();
            
            try {
                OutputStream out = puertoserial.getOutputStream();
                Scanner scanner = new Scanner(puertoserial.getInputStream()); 
                scanner.nextLine();

                Boolean flagTarjLeida=false;

                while(scanner.hasNextLine()) {
                    
                    if(flagLectura){ break;} 

                    String tempTarj = scanner.nextLine();

                    if(flagTarjLeida){ 
                        flagTarjLeida=false;
                        numeroTarjeta = getNumeroTarjeta(tempTarj);
                        System.out.println(numeroTarjeta);
                        enviarMensaje();
                    }
                    else{
                        flagTarjLeida=true; 
                    }  

                    try {
                        out.write('A');
                        out.flush();
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                }
                
                System.out.println("Cerrando Puerto");
                scanner.close();
                
            }
            catch (Exception e) {
                System.out.println(e.getMessage());
                System.out.println("Verifique la conexion con el Lector");
            }  
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void enviarMensaje(){
        switch(frame.getClass().getName()){
            case "vista.Administracion": 
                ((Administracion)frame).respuesta(numeroTarjeta);
                break;
            case "vista.Caja":
                ((Caja)frame).respuesta(numeroTarjeta);
                break;
            case "vista.Comedor":
                ((Comedor)frame).respuesta(numeroTarjeta);
                break;            
        }
    }
    
    public String getNumeroTarjeta(String numeroTarjeta){
        numeroTarjeta = numeroTarjeta.substring(0,numeroTarjeta.length()-14);
        return numeroTarjeta;
    }

    public void cerrarPuerto(){
        flagLectura = true;
        puertoserial.closePort();
    }
    
    public String getPuerto() {
        return puerto;
    }

    public void setPuerto(String puerto) {
        this.puerto = puerto;
    }
  
}
