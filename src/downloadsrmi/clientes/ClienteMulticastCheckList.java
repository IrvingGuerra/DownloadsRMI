/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package downloadsrmi.clientes;

import downloadsrmi.clientes.databases.serverData;
import downloadsrmi.clientes.databases.database;
import static downloadsrmi.softwareDownload.ANSI_BLUE;
import static downloadsrmi.softwareDownload.ANSI_GREEN;
import static downloadsrmi.softwareDownload.ANSI_RED;
import static downloadsrmi.softwareDownload.ANSI_RESET;
import static downloadsrmi.softwareDownload.ANSI_YELLOW;
import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author irvingguerra
 */


public class ClienteMulticastCheckList extends Thread{

    private final database db;
    

    public ClienteMulticastCheckList(database db){
        this.db = db;
        System.out.println( ANSI_BLUE + "[ CREATED ] "+ANSI_RESET+" Cliente Multicast CheckList Creado");
    }
    
    public void run(){
        System.out.println( ANSI_GREEN + "[ INIT ] "+ANSI_RESET+" Cliente Multicast CheckList Iniciado");
        while(true){
            try{
                List<serverData> ServersList = db.getServersList();
                if(ServersList.size() != 0){
                    for(int i=0 ; i < ServersList.size() ; i++){
                        System.out.println( ANSI_YELLOW + "[ INFO ] "+ANSI_RESET+" Verificando servidor: "+ServersList.get(i).getAddress()+" con temporizador: "+ServersList.get(i).getTemp());
                        if(ServersList.get(i).getTemp() == 0){
                            //Cuando llega a 0 el servidor no se reporto, asi que lo eliminamos
                            ServersList.remove(i);
                            System.out.println( ANSI_RED + "[ DELETED ] "+ANSI_RESET+" Servidor inactivo, se eliminó de la lista");
                        }else{
                            ServersList.get(i).setTemp(ServersList.get(i).getTemp()-1);
                        }
                        
                    }
                }
                Thread.sleep(1000);
            } 
            catch(InterruptedException ex) 
            {
                Thread.currentThread().interrupt();
            }
        }
    }
   
    
}
