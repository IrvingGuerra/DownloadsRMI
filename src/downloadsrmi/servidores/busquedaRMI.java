package downloadsrmi.servidores;

import java.rmi.Remote;
import java.rmi.RemoteException;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author irvingguerra
 */
public interface busquedaRMI extends Remote{
       searchResult buscar(String file) throws RemoteException;
}
