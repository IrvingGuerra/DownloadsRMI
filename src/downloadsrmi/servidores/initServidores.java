/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package downloadsrmi.servidores;

/**
 *
 * @author irvingguerra
 */
public class initServidores extends Thread{
    
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";
    
    ServidorMulticast ServidorMulticast = new ServidorMulticast();
    ServidorRMI ServidorRMI = new ServidorRMI();
    
    public initServidores() {
        System.out.println( ANSI_GREEN + "[ INIT ] "+ANSI_RESET+" Servidores iniciados");
        System.out.println( ANSI_YELLOW + "[ INFO ] "+ANSI_RESET+" Iniciando Servidor Multicast");
        ServidorMulticast.start();
        ServidorRMI.start();
    }
    
    public static void main(String[] args) {
        try{
	    initServidores servidores = new initServidores();
	    servidores.start();
	}catch(Exception e){
            e.printStackTrace();
        }
    }
    
    
}
