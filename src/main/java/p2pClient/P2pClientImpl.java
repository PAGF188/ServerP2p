package p2pClient;
import p2pServer.Cliente;
import p2pServer.P2pClientInterface;

import java.rmi.*;
import java.rmi.server.*;


/**
 * Esta clase implementa la interfaz remota del cliente
 * @author Pablo García
 */

public class P2pClientImpl extends UnicastRemoteObject implements P2pClientInterface {

     public Vin vin;

     public P2pClientImpl(Vin vin) throws RemoteException {
        super( );
        this.vin=vin;
     }
    
    @Override
    public void mensaje(String message){

    }
    
    @Override
    public void notificaDesconexion(String nombre){

    }

    @Override
    public void notificaConexion(Cliente cl){
        P2pClient.amigos.add(cl);
        vin.actualizarAmigos();
    }

}
