/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package downloadsrmi;
import static downloadsrmi.DownloadsRMI.ANSI_YELLOW;
import static downloadsrmi.Servidores.ANSI_GREEN;
import static downloadsrmi.Servidores.ANSI_RED;
import static downloadsrmi.Servidores.ANSI_RESET;
import java.io.File;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author irvingguerra
 */
public class ServidorRMI extends Thread implements busquedaRMI {
   
    String path1 = "/Users/irvingguerra/Documents/DownloadsRMI/src/databaseRMI1";
    File folder1 = new File(path1);

    @Override
    public searchResult buscar(String file) throws RemoteException {
        System.out.println( ANSI_YELLOW + "[ SEARCHING ] "+ANSI_RESET+" Buscando file: "+file);
        searchResult resultado = searchFileInFolder(folder1, file);
        return resultado;
    }
    
    public void run(){
        try {
            java.rmi.registry.LocateRegistry.createRegistry(1099);
            System.out.println( ANSI_GREEN + "[ OK ] "+ANSI_RESET+" Servidor RMI inicializado");  
            searchFileInFolder(folder1, "goku.txt");
        } catch (Exception e) {
            System.out.println( ANSI_RED + "[ FAIL ] "+ANSI_RESET+" Error al inicializar Servidor RMI");
            e.printStackTrace();
        }//catch

	try {
            System.setProperty("java.rmi.server.codebase","http://8.25.100.18/clases/"); ///file:///f:\\redes2\\RMI\\RMI2
	    ServidorRMI obj = new ServidorRMI();
	    busquedaRMI stub = (busquedaRMI) UnicastRemoteObject.exportObject(obj, 0);
	    Registry registry = LocateRegistry.getRegistry();
	    registry.bind("Busqueda", stub);   
            
            System.out.println( ANSI_GREEN + "[ OK ] "+ANSI_RESET+" Servidor RMI listo");
	} catch (Exception e) {
            System.out.println( ANSI_RED + "[ FAIL ] "+ANSI_RESET+"Server exception: " + e.toString());
	    e.printStackTrace();
	}
    }
    
    public static void main(String[] args) {
        try{
	    ServidorRMI servidorR = new ServidorRMI();
	    servidorR.start();
	}catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public searchResult searchFileInFolder(File folder, String fileName) {
        searchResult resultado = new searchResult();
        resultado.filename = "unknown";
        
        File[] listOfFiles = folder1.listFiles();
        for (File file : listOfFiles) {
            if (file.isFile()) {
                if(file.getName().equals(fileName)){
                    resultado.filename = file.getName();
                    resultado.path = file.getAbsolutePath();
                    resultado.md5 = getMD5(file.getAbsolutePath().toString());
                }
            }
        }
        return resultado;
    }
    
    public String getMD5(String path){
        try {
            MD5Checksum md5 = new MD5Checksum();
            String resultado = md5.getMD5Checksum(path);;
            return resultado;
        } catch (Exception ex) {
            Logger.getLogger(ServidorRMI.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
