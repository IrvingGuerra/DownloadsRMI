/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package downloadsrmi.clientes;

import static downloadsrmi.softwareDownload.ANSI_BLUE;
import static downloadsrmi.softwareDownload.ANSI_GREEN;
import static downloadsrmi.softwareDownload.ANSI_RESET;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 *
 * @author irvingguerra
 */
public class ClienteRMI extends Thread{
    
    public ClienteRMI() {
        System.out.println( ANSI_BLUE + "[ CREATED ] "+ANSI_RESET+" Cliente RMI Creado");
    }
    
    public void run(){
        System.out.println( ANSI_GREEN + "[ INIT ] "+ANSI_RESET+" Cliente RMI Iniciado");
    }

    public void searchFile(String text) {
        try {
	    Registry registry = LocateRegistry.getRegistry("localhost",1099);
	    busquedaRMI stub = (busquedaRMI) registry.lookup("Busqueda");
            searchResult response = stub.buscar(text);
            System.out.println( ANSI_GREEN + "[ RESPONSE ] "+ANSI_RESET+" Busqueda name: "+response.getFilename());
            System.out.println( ANSI_GREEN + "[ RESPONSE ] "+ANSI_RESET+" Busqueda path: "+response.getPath());
            System.out.println( ANSI_GREEN + "[ RESPONSE ] "+ANSI_RESET+" Busqueda md5: "+response.getMd5());
	} catch (Exception e) {
	    System.err.println("Client exception: " + e.toString());
	    e.printStackTrace();
	}
    }
}
