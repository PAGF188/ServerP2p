package p2pClient;
import p2pServer.Cliente;
import p2pServer.P2pClientInterface;

import java.rmi.*;
import java.rmi.server.*;
import java.util.ArrayList;


/**
 * Esta clase implementa la interfaz remota del cliente
 * @author Pablo García
 */

public class P2pClientImpl extends UnicastRemoteObject implements P2pClientInterface {

     /*Referencia a la interfaz de mensajes para poder actualizarla con los entrantes*/
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

    /** La entrada es el nombre del cliente a desconcetarse.
     * Recorremos la lsita de amigos, y si el nombre coinicide lo eliminamos.
     */
    @Override
    public void notificaDesconexion(String nombre){
        /*Lo eliminamos de la lista*/
        ArrayList<Cliente> eliminar = new ArrayList<>();
        for(Cliente aux: P2pClient.amigos){
            if(aux.getNombre().equals(nombre)){
                eliminar.add(aux);
                break;
            }
        }
        P2pClient.amigos.removeAll(eliminar);

        /*Actualizamos la interfaz gráfica*/
        vin.actualizarDesconexion(nombre);
    }

    @Override
    public void notificaConexion(Cliente cl){
        P2pClient.amigos.add(cl);
        vin.actualizarAmigos();
    }

    @Override
    public void notificaPeticionAmistad(String solicitante, String solicitado) throws RemoteException {
        /*Si ya existe la petición, no la almacenamos*/
        for(String aux: P2pClient.peticionesAmmistad){
            if(aux.equals(solicitante))
                return;
        }
        P2pClient.peticionesAmmistad.add(solicitante);
    }
}
