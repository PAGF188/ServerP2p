package p2pServer;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.google.gson.Gson;

import java.io.IOException;
import java.rmi.*;
import java.rmi.server.*;
import java.util.ArrayList;

import java.io.FileReader;

/**
 * Objeto servidor que implementa la interfaz remota CoronarioServerInterface
 * Es el encargado de:
 * 1)leer el fichero (con un hilo)
 * 2) enviar los datos a todos los clientes subscritos a través de callback.
 * 3) cesar las suscripciones.
 * Define una nueva clase Cliente que contiene el tiempo y la interfaz remota al cliente.
 * @autor: Pablo García
 */


public class P2pServerImpl extends UnicastRemoteObject implements P2pServerInterface{


    private ArrayList<Usuario> usuarios;

    public P2pServerImpl() throws RemoteException {
        super( );
        usuarios= new ArrayList<>();
        this.pasearUsuarios();
    }

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
            this.usuarios.add(aux);
        }
    }

    public void grabarUsuarios(){
        Gson gson = new Gson();
        String jsonString="";

        for(Usuario aux:this.usuarios){
            jsonString += gson.toJson(aux);
        }

        System.out.println(jsonString);
    }


    @Override
    public synchronized boolean registrarse(String nombre, String passwd){

        for(Usuario aux : this.usuarios){
            if(aux.getNombre().equals(nombre))
                return(false);
        }
        this.grabarUsuarios();
        return(true);
    }

    @Override
    public synchronized P2pClientInterface[] log(String nombre, String passwd, P2pClientInterface client) {
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
