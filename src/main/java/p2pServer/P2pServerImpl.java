package p2pServer;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import com.google.gson.Gson;
import java.io.*;
import java.rmi.*;
import java.rmi.server.*;
import java.util.ArrayList;

/**
 * @autor: Pablo García
 *
 */


public class P2pServerImpl extends UnicastRemoteObject implements P2pServerInterface{

    /**
     *  Usuarios a nivel de archivo. Con nombre, passwd y amigos(nombres).
     *  Son estáticos. TODOS los usuarios registrados en la aplicación
     */
    private ArrayList<Usuario> usuarios;

    /**
     *  Usuarios a nivel de interfaces remotas(aplicacion)
     *  Son dinámicaos. TODOS los clientes logeados y por lo tanto activos
     *  en este momento.
     */
    private ArrayList<Cliente> clientes;

    public P2pServerImpl() throws RemoteException {
        super( );
        usuarios= new ArrayList<>();
        clientes= new ArrayList<>();
        this.pasearUsuarios();
    }

    /**
     * Leer el archivo y encapsular cada usuario en una clase Usuario.
     * A mayores cada usuario queda almacenado en el Array usuarios.
     * De esta forma usuarios contiene el archivo estructurado.
     */
    public void pasearUsuarios(){
        JSONParser parser = new JSONParser();
        Object obj=null;
        try {
            obj = parser.parse(new FileReader("/home/pablo/usuarios.json"));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        JSONArray usuarios = (JSONArray) obj;

        for(int i=0;i<usuarios.size();i++){
            JSONObject usuario = (JSONObject) usuarios.get(i);
            Usuario aux = new Usuario((String)usuario.get("nombre"),(String)usuario.get("passwd"));
            JSONArray amigos = (JSONArray) usuario.get("amigos");
            for(int j=0;j<amigos.size();j++){
                aux.anhadirAmigo((String)amigos.get(j));
            }
            /*Lo añadimos al array*/
            this.usuarios.add(aux);
        }
    }

    /**
     * Para grabar los usuario contenidos en usuarios al archivo.
     */
    public void grabarUsuarios(){
        Gson gson = new Gson();
        String json = "[";

        for(int i=0;i<this.usuarios.size()-1;i++){
            json+=gson.toJson(this.usuarios.get(i));
            json+=",";
        }
        json+=gson.toJson((this.usuarios.get(this.usuarios.size()-1)));
        json+="]";

        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter("/home/pablo/usuarios.json"));
            bw.write(json);
            bw.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            System.out.println("Fallo al escribir archivo");
        }
    }

    /**
     * Recorremos todos los usuarios (previamente parseados) mirando si
     * el usuario ya está dado de alta.
     * Sincronizazdo para garantizar exclusión mutua de login y registro sobre array de usuarios.
     */
    @Override
    public synchronized boolean registrarse(String nombre, String passwd){

        for(Usuario aux : this.usuarios){
            if(aux.getNombre().equals(nombre))
                return(false);
        }

        //Si llega hasta aquí el usuario no está dado de alta
        Usuario aux = new Usuario(nombre,passwd);
        //Lo añadimos el array
        this.usuarios.add(aux);
        //Grabamos a archivo
        this.grabarUsuarios();

        return(true);
    }

    @Override
    public synchronized ArrayList<Cliente> log(String nombre, String passwd, P2pClientInterface client) throws Exception {

        for(Usuario aux : this.usuarios){
            if(aux.getNombre().equals(nombre) && aux.getPasswd().equals(passwd)){
                /**
                 * Si el logeo es correcto:
                 * 1) Recorremos los clientes activos.
                 * 2) Almacenamos en array aquellos que tengan como amigo al usuario que hizo login.
                 * 3) Si el cliente actual aparece como elemento -> ya está logeado
                 * 5) Añadimos al usuario que hizo login como cliente activo
                 * 4) Se los devolvemos.
                 */
                ArrayList<Cliente> amigos = new ArrayList<>();
                for(Cliente cl : this.clientes){
                    if(cl.getNombre().equals(nombre)){
                        throw new Exception("El usuario ya está logueado");
                    }
                    if(aux.getAmigos().contains(cl.getNombre())){
                        amigos.add(cl);
                    }
                }
                //Creamos al cliente actual y lo añadimos al array de clientes.
                Cliente cl = new Cliente(nombre,client);
                this.clientes.add(cl);
                return(amigos);
            }
        }
        return(null);
    }

    public class Usuario{

        private String nombre;
        private String passwd;
        private ArrayList<String> amigos;

        public Usuario(String nombre, String passwd){
            this.nombre=nombre;
            this.passwd=passwd;
            amigos=new ArrayList<>();
        }
        public String getNombre() {
            return nombre;
        }
        public String getPasswd() {
            return passwd;
        }
        public ArrayList<String> getAmigos() {
            return amigos;
        }
        public void anhadirAmigo(String amigo){
            amigos.add(amigo);
        }
    }
}
