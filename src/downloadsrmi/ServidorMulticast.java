/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package downloadsrmi;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;


/**
 *
 * @author irvingguerra
 */
public class ServidorMulticast extends Thread{
    
    /* Colores para consola */
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";
    
    /* Variables*/
    
    public static final String MCAST_ADDR  = "228.1.1.1";
    public static final int MCAST_PORT = 9014;
    public static final int DGRAM_BUF_LEN = 1024;
    
    InetAddress group =null;

    public void run(){
        System.out.println( ANSI_GREEN + "[OK] "+ANSI_RESET+" Servidor Multicast Iniciado.");
        try{
            group = InetAddress.getByName(MCAST_ADDR);
            MulticastSocket socket = new MulticastSocket(MCAST_PORT); //socket tipo multicast
            socket.joinGroup(group);//se une al grupo
            while(true){
                System.out.println( ANSI_YELLOW + "[SENDING] "+ANSI_RESET+" Enviando mensaje...");
                send("HereIAm");
                try{
                    Thread.sleep(5000);
                } 
                catch(InterruptedException ex) 
                {
                    Thread.currentThread().interrupt();
                }
                
            }        
        }catch(IOException e){
            e.printStackTrace();
            System.exit(2);
        }
    }
     
    public static void main(String[] args) {
        try{
	    ServidorMulticast servidorM = new ServidorMulticast();
	    servidorM.start();
	}catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public Boolean send(String msg){
        try{
            MulticastSocket socketEnvio = new MulticastSocket(MCAST_PORT);
            socketEnvio.joinGroup(group); // se configura para escuchar el paquete
            DatagramPacket packet = new DatagramPacket(msg.getBytes(),msg.length(),group,MCAST_PORT);
            socketEnvio.send(packet);
            System.out.println( ANSI_BLUE + "[SEND]"+ANSI_RESET+" Instruccion enviada: "+msg);
            socketEnvio.close();
            return true;
        }catch(IOException e){
            e.printStackTrace();
            return false;
        }
    }
    
}
