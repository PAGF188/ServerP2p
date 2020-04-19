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
import java.util.Objects;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * @autor: Pablo García
 * Servidor P2P.
 * Se encarga de:
 * 1) Permitir el registro de nuevos usuarios añadiendolos al archivo usuarios.json
 * 2) Permitir el log-in de un cliente, devolviendole su lista de amigos (interfaces remota y nombre)
 * 3) Manejar el archivo de usuarios
 * 4) Manejar la lista de clientes dinámicos (que hicieron log-in)
 * 5) Log-out y notificacion a amigos
 * 6) Manejo de peticiones de amistad.
 */

public class P2pServerImpl extends UnicastRemoteObject implements P2pServerInterface{

    /**
     *  Usuarios a nivel de archivo. Con nombre, passwd y amigos(nombres).
     *  Son estáticos. TODOS los usuarios registrados en la aplicación
     */
    private ArrayList<Usuario> usuarios;

    /**
     *  Usuarios a nivel de interfaces remotas (aplicacion)
     *  Son dinámicaos. TODOS los clientes logeados y por lo tanto activos
     *  en este momento.
     */
    private ArrayList<Cliente> clientes;

    private String rutaP = "src/main/java/p2pServer/peticiones.json";
    private String rutaU = "src/main/java/p2pServer/usuarios.json";

    /*private String rutaP = "./peticiones.json";
    private String rutaU = "./usuarios.json";*/

    public P2pServerImpl() throws RemoteException {
        super( );
        usuarios= new ArrayList<>();
        clientes= new ArrayList<>();

        /*Cargamos el fichero de usuarios en this.usuarios*/
        this.pasearUsuarios();
    }

    /**
     * Leer el archivo y encapsular cada usuario en una clase Usuario.
     * A mayores cada usuario queda almacenado en el Array usuarios.
     * De esta forma usuarios contiene el archivo estructurado.
     */
    public synchronized void pasearUsuarios(){
        usuarios=null;
        usuarios=new ArrayList<>();
        JSONParser parser = new JSONParser();
        Object obj=null;
        try {
            obj = parser.parse(new FileReader(rutaU));
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
     * Grabar this.usuarios al archivo usuarios
     */
    public synchronized void grabarUsuarios(){
        Gson gson = new Gson();
        String json = "[";

        for(int i=0;i<this.usuarios.size()-1;i++){
            json+=gson.toJson(this.usuarios.get(i));
            json+=",";
        }
        json+=gson.toJson((this.usuarios.get(this.usuarios.size()-1)));
        json+="]";

        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(rutaU));
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
                //el usuario ya esta registrado
                return(false);
        }

        //Si llega hasta aquí el usuario no está registrado
        Usuario aux = new Usuario(nombre,passwd);
        //Lo añadimos el array
        this.usuarios.add(aux);
        //Grabamos a archivo
        this.grabarUsuarios();
        return(true);
    }

    /**
     * Recorre this.usuarios para comprobar nombre y passwd correctos.
     * Recorre this.clientes para ver si no esta ya logeado.
     * Se añade a this.clientes, notificamos a sus amigos la nueva conexión.
     * Devuelve null si fallo credenciales | sus amigos en caso correcto
     */
    @Override
    public synchronized ArrayList<Cliente> log(String nombre, String passwd, P2pClientInterface client) throws Exception {
        Cliente cl_actual = new Cliente(nombre,client);
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
                        cl.getInterfazRemota().notificaConexion(cl_actual);
                    }
                }
                //Creamos al cliente actual y lo añadimos al array de clientes.
                this.clientes.add(cl_actual);

                /*Recorremos archivo de peticiones de amistad, y le notificamos de ellas*/
                /*Lectura archivo*/
                    ArrayList<Peticion> peticiones= new ArrayList<>();
                    JsonParser parser = new JsonParser();
                    Object obj=null;
                    try {
                        obj = parser.parse(new FileReader(rutaP));
                    }catch (IOException e) {
                        e.printStackTrace();
                    }
                    JsonArray data = (JsonArray) obj;
                    for(int i=0;i<data.size();i++){
                        Peticion auxP = new Gson().fromJson(data.get(i), Peticion.class);
                        peticiones.add(auxP);
                    }
                 /*Fin letura archivo*/
                for(Peticion pet: peticiones){
                    if(pet.getSolicitado().equals(cl_actual.getNombre())){
                        cl_actual.getInterfazRemota().notificaPeticionAmistad(pet.getSolicitante(),pet.getSolicitado());
                    }
                }

                return(amigos);
            }
        }
        /*Si llegamos aquí, no existe la pareja usuario-password en el archivo*/
        return(null);
    }

    /**
     * Recorremos clientes. Si es el que hace log-out, lo eliminamos. Si es otro y amigo, notificamos su desconexión
     * @param nombre, del cliente a hacer log-out
     */
    @Override
    public synchronized void desLog(String nombre) {
        /*Buscamos al usuario para obtener su lista de amigos*/
        Usuario a_desconcetar=null;
        for(Usuario aux : this.usuarios){
            if(aux.getNombre().equals(nombre)){
                a_desconcetar=aux;
            }
        }

        /*Recorremos clientes activos en la aplicación
        * Si es el que intenta desconcetarse lo eliminamos.
        * Si es otro y es amigo, le notificamos*/
        ArrayList<Cliente> eliminar = new ArrayList<>();
        for(Cliente cl : this.clientes){
            if(cl.getNombre().equals(nombre)){
                eliminar.add(cl);
            }
            else{
                if(a_desconcetar.getAmigos().contains(cl.getNombre())){
                    try {
                        cl.getInterfazRemota().notificaDesconexion(nombre);
                    } catch (RemoteException e) {
                        System.out.println(e.getMessage());
                        e.printStackTrace();
                    }
                }
            }
        }

        this.clientes.removeAll(eliminar);
    }

    /**
     * Para solicitar una petición de amistad.
     * Si solicitado está concetado, se la envíamos directamente.
     * Si solicitado no está concetado, la almacenamos en el fichero peticiones.json y cuando se concete un nuevo cliente,
     * procesamos el fichero y le envíamos sus peticiones en caso de tener.
     * @param solicitante, usuario que la solicita
     * @param solicitado, usuario a la que va dirigida
     * @throws Exception | Peticion a ti mismo.  |  El solicitado ya tiene una petición tuya |  Ya sois amigos  | No existe el solicitado en el sistema
     */
    @Override
    public synchronized void peticionAmistad(String solicitante, String solicitado) throws Exception {

        /*no te puedes enviar ua peticion a ti mismo*/

        if(solicitado.equals(solicitante)){
            throw new Exception("No te puedes enviar una petición a ti mismo");
        }

        /**
         * Leemos el archivo de peticiones -> Los datos quedan almacenados en ArrayList<> peticiones
         */
        ArrayList<Peticion> peticiones= new ArrayList<>();
        JsonParser parser = new JsonParser();
        Object obj=null;
        try {
            obj = parser.parse(new FileReader(rutaP));
        }catch (IOException e) {
            e.printStackTrace();
        }
        JsonArray data = (JsonArray) obj;
        for(int i=0;i<data.size();i++){
            Peticion aux = new Gson().fromJson(data.get(i), Peticion.class);
            peticiones.add(aux);
        }

        /*Creamos objeto auxiliar con la petición introducida como parámetro*/
        Peticion nueva = new Peticion(solicitante,solicitado);

        /*Primera comprobacion: Que no exista ya la petición: */
        for(Peticion p:peticiones){
            if(p.equals(nueva)){
                throw new Exception(solicitado + " ya tiene una petición tuya.");
            }
        }

        /* Segunda comprobación: Que el solicitante no tenga YA como amigo al solicitado
         * Tercera comprobación: Que el solicitado exista en el sistema.*/
        boolean existe = false;
        for(Usuario u : this.usuarios){
            if(u.getNombre().equals(solicitante) && u.getAmigos().contains(solicitado)){
                throw new Exception("Ya eres amigo de " + solicitado);
            }
            if(u.getNombre().equals(solicitado)){
                existe=true;
            }
        }
        if(!existe){
            throw new Exception(solicitado + " No existe en el sistema");
        }

        /**
         * Comprobaciones finalizadas
         * Dos casos pueden pasar ahora:
         * a) Cliente concetado -> le envíamos directamente la petición
         * b) Cliente no concetado -> almacenamos la petición
         */
         for(Cliente conectado : this.clientes){
             /*esta concetado*/
             if(conectado.getNombre().equals(solicitado)){
                 conectado.getInterfazRemota().notificaPeticionAmistad(solicitante,solicitado);
                 return;
             }
         }

         /* Si llegamos aquí el cliente no está concetado. Almacenamos petición en archivo
          * Esta petición se enviará cuando el cliente se concete*/
        peticiones.add(nueva);
        String json = new Gson().toJson(peticiones);

        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(rutaP));
            bw.write(json);
            bw.close();
        } catch (IOException e) {
            System.out.println("Fallo al escribir archivo de peticiones");
            e.printStackTrace();
        }
    }

    @Override
    public synchronized void aceptarPeticion(boolean aceptar, String solicitante, String solicitado) throws Exception {

        /*Si se acepta. Añadimos la nueva amistad tanto en el solictante como en el solicitado. Y grabamos los datos en archivo*/
        if(aceptar) {
            /*Recorremos usuarios añadiendo las nuevas amistades*/
            for (Usuario aux : this.usuarios) {
                if (aux.getNombre().equals(solicitante)) {
                    aux.getAmigos().add(solicitado);
                }
                if (aux.getNombre().equals(solicitado)) {
                    aux.getAmigos().add(solicitante);
                }
            }
            /*Almacenamos los datos en el archivo*/
            this.grabarUsuarios();
            this.pasearUsuarios();
        }

        /*Borramos la petición del archivo de peticiones -> leemos todas menos la aceptar*/
        ArrayList<Peticion> peticiones= new ArrayList<>();
        JsonParser parser = new JsonParser();
        Object obj=null;
        try {
            obj = parser.parse(new FileReader(rutaP));
        }catch (IOException e) {
            e.printStackTrace();
        }
        JsonArray data = (JsonArray) obj;
        for(int i=0;i<data.size();i++){
            Peticion aux = new Gson().fromJson(data.get(i), Peticion.class);
            if(aux.getSolicitante().equals(solicitante) && aux.getSolicitado().equals(solicitado)){

            }else{
                peticiones.add(aux);
            }
        }

        /*Almacenamos todas las peticiones (menos la que no se leyo)*/
        String json = new Gson().toJson(peticiones);

        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(rutaP));
            bw.write(json);
            bw.close();
        } catch (IOException e) {
            System.out.println("Fallo al escribir archivo de peticiones");
            e.printStackTrace();
        }

        if(aceptar) {
            /*Finalmente tenemos que notificar a los usuarios en caso de que se encuentren concetados*/
            Cliente solicitante_ = null;
            Cliente solicitado_ = null;
            for (Cliente cl : this.clientes) {
                if (cl.getNombre().equals(solicitante))
                    solicitante_ = cl;
                if (cl.getNombre().equals(solicitado))
                    solicitado_ = cl;
            }

            if (solicitante_ != null && solicitado_ != null) {
                solicitado_.getInterfazRemota().notificaConexion(solicitante_);
                solicitante_.getInterfazRemota().notificaConexion(solicitado_);
            }
        }
    }

    /**
     * Encapsula cada elemento del array presente en usuarios.json
     */
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

    /**
     * Encapsula una peticion de amistad
     */
    public class Peticion{
        private String solicitante;
        private String solicitado;

        public Peticion(String a, String b){
            this.solicitante=a;
            this.solicitado=b;
        }

        public String getSolicitante() { return solicitante; }
        public String getSolicitado() { return solicitado; }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Peticion peticion = (Peticion) o;
            return Objects.equals(solicitante, peticion.solicitante) &&
                    Objects.equals(solicitado, peticion.solicitado);
        }

        @Override
        public int hashCode() {
            return Objects.hash(solicitante, solicitado);
        }
    }
}
