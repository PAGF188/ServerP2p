package p2pServer;
import java.rmi.*;


/**
 * Interfaz remota para el cliente.
 * @autor: Pablo García
 */

public interface P2pClientInterface extends java.rmi.Remote{

    /**
     * Este método es invocado por otro cliente para enviar un mensaje
     * @param message, información que se pasa.
     * @throws java.rmi.RemoteException
     */
    public void mensaje(String message) throws java.rmi.RemoteException;

}
