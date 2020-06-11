/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package downloadsrmi;

import static downloadsrmi.DownloadsRMI.ANSI_BLUE;
import static downloadsrmi.DownloadsRMI.ANSI_GREEN;
import static downloadsrmi.DownloadsRMI.ANSI_PURPLE;
import static downloadsrmi.DownloadsRMI.ANSI_RESET;
import static downloadsrmi.DownloadsRMI.ANSI_YELLOW;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;
import java.util.List;

/**
 *
 * @author irvingguerra
 */


public class ClienteMulticast extends Thread{
    
    public static final String MCAST_ADDR  = "228.1.1.1";
    public static final int MCAST_PORT = 9014;
    public static final int DGRAM_BUF_LEN=1024;
    InetAddress group =null;
    private final database db;

    public ClienteMulticast(database db){
        this.db = db;
        System.out.println( ANSI_BLUE + "[ CREATED ] "+ANSI_RESET+" Cliente Multicast Creado");
        try{
            group = InetAddress.getByName(MCAST_ADDR);
        }catch(UnknownHostException e){
            e.printStackTrace();
            System.exit(1);
        }
    }
    
    public void run(){
        
        System.out.println( ANSI_GREEN + "[ INIT ] "+ANSI_RESET+" Cliente Multicast Iniciado");
        try{
            MulticastSocket socket = new MulticastSocket(MCAST_PORT); 
            socket.joinGroup(group);
            String mensaje="";
            while(true){
                byte[] buf = new byte[DGRAM_BUF_LEN];
                DatagramPacket recv = new DatagramPacket(buf,buf.length);
                socket.receive(recv);
                byte [] data = recv.getData();
                mensaje = new String(data);
                mensaje = mensaje.trim();
                System.out.println( ANSI_PURPLE + "[ MSN ] "+ANSI_RESET+" Mensaje recibido: " + mensaje);
                System.out.println( ANSI_PURPLE + "[ MSN ] "+ ANSI_YELLOW + "[ INFO ] "+ANSI_RESET+" From Address: " + recv.getAddress());
                System.out.println( ANSI_PURPLE + "[ MSN ] "+ ANSI_YELLOW + "[ INFO ] "+ANSI_RESET+" From Port: " + recv.getPort());
                //Necesitamos verificar si ya tenemos esta servidor guardado en la lista
                //Creamos el objeto
                serverData ActualServer = new serverData(recv.getAddress().toString(), recv.getPort(), 6);
                //Lo agregamos a la lista siempre y cuando no exista
                List<serverData> ServersList = db.getServersList();
                int pos = containsList(ServersList, ActualServer);
                if ( pos == -1){
                    db.addServer(ActualServer);
                    System.out.println( ANSI_GREEN + "[ OK ] "+ANSI_RESET+" Server añadido a la lista");
                }else{
                    //Cuando recibamos un mensaje de el mismo, solo sera necesario actualizarlo
                    ServersList.get(pos).setTemp(6);
                    db.setServersList(ServersList);
                }
            }        
        }catch(IOException e){
            e.printStackTrace();
            System.exit(2);
        }
    }
    

    public int containsList(List<serverData> lista, serverData e){
        for(int i = 0 ; i < lista.size() ; i++){
            if(lista.get(i).getAddress().equals(e.getAddress())){
                return i;
            }
        }
        return -1;
    }
    
}
