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
    public void mensaje(String message, String emisor){
        ModeloTabla aux = (ModeloTabla) vin.getUsuario_tabla().get(emisor).getModel();
        String color="#6d0086";
        String comentario = "<html><span style='color:"+color+"; font-size:10pt;'>"+emisor+"</span>"
                + "<br>"
                + "<span style='color:white;font-weight:bold;background-color:"
                +color+ ";'>"+message+"</span></html>";
        aux.addComentario(comentario);
    }
    
    @Override
    public void notificaDesconexion(String nombre){

    }

    @Override
    public void notificaConexion(Cliente cl){
        P2pClient.amigos.add(cl);
        vin.actualizarAmigos();
        System.out.println("Notificacion entrante de "+ cl.getNombre() + " . Amigos actuales: ");
        for(Cliente cl2 : P2pClient.amigos){
            System.out.println(cl2.getNombre());
        }
    }

}
