
/**
 * Servidor de objetos, encargado de instanciar y exportar P2pServerImpl.
 * 99% igual al de la practica anterior. Solo cambiar URL y objetos instanciados
 * @autor Pablo García
 */

package p2pServer;

import java.rmi.*;
import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.net.*;
import java.io.*;

public class ServerP2P{
    public static void main(String args[]){
        /*buffer para leer nº de puerto*/
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int puerto;
        String URL;  //url de registro

        /*try {
            System.setSecurityManager(new
                    RMISecurityManager( ));
        }
        catch(Exception ex){
            ex.printStackTrace();
        }*/
        try{
            System.out.print("Introduce el puerto para el registro RMI: \n");
            puerto = Integer.parseInt((br.readLine()).trim());

            startRegistry(puerto);

            /*instanciamos al objeto*/
            P2pServerImpl exportedObj = new P2pServerImpl();

            URL = "rmi://localhost:" + puerto + "/p2p";
            Naming.rebind(URL, exportedObj);

            listRegistry(URL);
            System.out.println("\n**** P2P Server listo ****\n\n");

        }
        catch (Exception re) {
            System.out.println("!! Exception in ServerP2p.main: " + re.getMessage());
        }
    }

    /**
     * Este método inicia un registro RMI en la máquina actual en caso de que,
     * no exista ya en el puerto indicado.
     * @param puerto
     * @throws RemoteException
     */
    private static void startRegistry(int puerto) throws RemoteException{
        try {
            /*intentamos localizar el registro*/
            Registry registry = LocateRegistry.getRegistry(puerto);
            registry.list( );
            System.out.println("\nEl registro ya existe");
        }
        /*En caso de que el try falle -> no existe registro -> se crea*/
        catch (RemoteException e) {
            Registry registry = LocateRegistry.createRegistry(puerto);
            System.out.println("\nRegistro creado sobre el puerto: " + puerto);
        }
    }

    /**
     * Este método lista los objetos registrados en el RMI Register
     * @param registryURL
     * @throws RemoteException
     * @throws MalformedURLException
     */
    private static void listRegistry(String registryURL) throws RemoteException, MalformedURLException {
        System.out.println("El registro " + registryURL + " contiene: ");
        String [ ] names = Naming.list(registryURL);
        for (int i=0; i < names.length; i++)
            System.out.println("\t-> " + names[i]);
    }
}