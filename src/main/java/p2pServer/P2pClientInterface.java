package p2pServer;
import java.rmi.*;


/**
 * Interfaz remota para el cliente.
 * @autor: Pablo García
 */

public interface P2pClientInterface extends java.rmi.Remote{

    /**
     * Este método es invocado por otro cliente para enviarme un mensaje.
     * @param message, información que se pasa.
     * @throws java.rmi.RemoteException
     */
    public void mensaje(String message, String emisor) throws java.rmi.RemoteException;

    /**
     * Este método es invocado por el servidor para notificar de la
     * desconexión de un amigo
     * @param nombre identificador del amigo
     */
    public void notificaDesconexion(String nombre) throws java.rmi.RemoteException;

    /**
     * Este método es invocado por el servidor para notificar de la conexión
     * de un nuevo amigo
     * @param cl . Se le pasa está clase que contiene la interfaz remota del amigo
     * y su nombre.
     */
    public void notificaConexion(Cliente cl) throws java.rmi.RemoteException;

    /**
     * Este método es inocado por el servidor para notificar una nueva petición de amistad.
     * Se le pasa el solicitante y solicitado. (solicitado debe ser igual a P2P.yoNombre)
     * @param solicitante
     * @param solicitado
     * @throws java.rmi.RemoteException
     */
    public void notificaPeticionAmistad(String solicitante, String solicitado) throws java.rmi.RemoteException;

}
