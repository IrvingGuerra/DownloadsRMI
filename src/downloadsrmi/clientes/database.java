/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package downloadsrmi.clientes;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author irvingguerra
 */
public class database {
   
    private List<serverData> ServersList = new ArrayList<>();

    public List<serverData> getServersList() {
        return ServersList;
    }
    
    public void addServer(serverData e) {
        ServersList.add(e);
    }

    public void setServersList(List<serverData> ServersList) {
        this.ServersList = ServersList;
    }

}
