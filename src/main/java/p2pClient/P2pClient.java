/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package p2pClient;

import p2pServer.Cliente;
import p2pServer.P2pClientInterface;
import p2pServer.P2pServerInterface;

import java.rmi.Naming;
import java.util.ArrayList;

/**
 *
 * @author pablo
 */
public class P2pClient {
    
    public static P2pClientInterface yo;
    public static P2pServerInterface server;
    public static ArrayList<Cliente> amigos;
    
    public static void main(String[] args) {
        
        String registryURL = "rmi://localhost:1099/p2p";
        
        try{
            //miramos si existe server en URL indicada
            server = (P2pServerInterface)Naming.lookup(registryURL);
            Vprincipal vp = new Vprincipal(null);
            vp.setVisible(true);
            
        }catch(Exception e){
            System.out.println("!!!! El servidor indicado no es acesible: " + e.getMessage());
            System.exit(-1);
        }
    }
    
}
