package p2pClient;

import javax.swing.table.AbstractTableModel;


/**
 * @author pablo
 */
public class ModeloTabla extends AbstractTableModel{

    private java.util.List<String> comentarios;

    public ModeloTabla(){
        this.comentarios = new java.util.ArrayList<>();
    }

    //Función para obtener el número de columnas de la tabla
    @Override
    public int getColumnCount(){
        return 2;
    }

    //Función para obtener el número de filas de la tabla
    @Override
    public int getRowCount(){
        return comentarios.size();
    }

    //Función para obter el nombre de las columnas -> null
    @Override
    public String getColumnName(int col){
        return(null);
    }

    //Función para obtener la clase de las celdas
    @Override
    public Class getColumnClass(int col){
        return java.lang.String.class;
    }

    //Función para determinar en que columnas se pueden editar las celdas->ninguna
    @Override
    public boolean isCellEditable(int row, int col){
        return false;
    }

    //Función para obtener el valor de una celda.
    /*Si el comentario empieza por { significa que procede de otra máquina por lo tanto se
    inserta a la izquerda.
    Si el comentario no empieza por {, significa que proviene de la misma máquina y por lo tanto
    se inserta a la derecha*/
    @Override
    public Object getValueAt(int row, int col){
        Object resultado=null;

        if(col==0 && comentarios.get(row).getBytes()[0]!='{' ){
            resultado=comentarios.get(row);
        }
        else if(col==0 && comentarios.get(row).getBytes()[0]=='{' ){
            resultado=null;
        }
        else if(col==1 && comentarios.get(row).getBytes()[0]!='{' ){
            resultado=null;
        }
        else if(col==1 && comentarios.get(row).getBytes()[0]=='{' ){
            resultado=comentarios.get(row).substring(1);
        }
        return resultado;
    }

    public String ultimo(){
        return this.comentarios.get(comentarios.size()-1);
    }

    //Función para añadir un comentario.
    public void addComentario(String comentario){
        this.comentarios.add(comentario);
        fireTableDataChanged();
    }
}
