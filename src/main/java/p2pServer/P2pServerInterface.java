package p2pServer;
import java.rmi.*;
import java.util.ArrayList;

/**
 * Interfaz para objeto P2pServer
 * @autor: Pablo García
 * solo muestra operaciones útiles para los clientes.
 */

public interface P2pServerInterface extends Remote {


    /**
     * El objetivo es registrarse con un nombre y una contraseña en el servidor.
     * @param nombre, nombre de usuario ÚNICO
     * @param passwd, contraseña.
     * @return booleam, False si el usuario ya está registrado
     * @throws java.lang.Exception
     */
    public boolean registrarse(String nombre, String passwd)
                throws Exception;

    /**
     * Permite a un cliente concetarse a la red P2P, previa autenticación, y
     * obtener las referencias remotas a sus amigos
     * @param nombre, identificador único del usuario
     * @param passwd, contraseña
     * @param client, referencia remota a la interfaz del cliente que hizo log
     * @return,  referencias remotas a las interfaces de sus amigos
     * @throws Exception
     * @throws java.rmi.RemoteException
     */
    public ArrayList<Cliente> log(String nombre, String passwd, P2pClientInterface client)
            throws Exception,java.rmi.RemoteException;

    /**
     * Eliminar al cliente actual del servidor y notificar a todos los clientes, que el cliente que hizo
     * log-out ya no esta activo.
     * @param nombre, del cliente a hacer log-out
     * @throws java.lang.Exception
     */
    public void desLog(String nombre)
            throws Exception;

}