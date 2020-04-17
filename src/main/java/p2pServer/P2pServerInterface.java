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

    /**
     * Para solicitar una petición de amistad.
     * Si solicitado está concetado, se la envíamos directamente. (Nota: Obligar al cliente a que acepte/rechaze
     * peticiones antes de salir de la aplicación (sino se perderían)).
     * Si solicitado no está concetado, la almacenamos en el fichero peticiones.json y cuando se concete un nuevo cliente,
     * procesamos el fichero y le envíamos sus peticiones en caso de tener.
     * OJO: ES POSIBLE ENVIAR UNA PETICIÓN A ALGUIEN QUE YA ES MI AMIGO O DOS PETICIONES IGUALES. NECESARIO COMPROBACIÓN
     * @param solicitante, usuario que la solicita
     * @param solicitado, usuario a la que va dirigida
     * @throws Exception
     */
    public void peticionAmistad(String solicitante, String solicitado)
            throws Exception;

    /**
     * Para aceptar una petición por el solicitado.
     * Añadimos a usuario el nuevo amigo y grabamos fichero. ADEMÁS invocamos método notificar conexión (si prodece)
     * @param solicitante
     * @param solicitado
     * @throws Exception
     */
    public void aceptarPeticion(String solicitante, String solicitado)
            throws Exception;

}