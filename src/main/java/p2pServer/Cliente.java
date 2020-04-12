/**
 * @autor Pablo García Fernández
 * Clase que encapsula la interfaz remota de un cliente junto con su nombre de usuario
 */

package p2pServer;

public class Cliente{
    private String nombre;
    private P2pClientInterface interfazRemota;

    public Cliente(String nombre, P2pClientInterface interfazRemota){
        this.nombre=nombre;
        this.interfazRemota=interfazRemota;
    }
    public String getNombre() {
        return nombre;
    }
    public P2pClientInterface getInterfazRemota() {
        return interfazRemota;
    }
}