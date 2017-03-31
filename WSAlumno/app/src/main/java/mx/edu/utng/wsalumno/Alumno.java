package mx.edu.utng.wsalumno;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

import java.util.Hashtable;

/**
 * Created by Jorge on 30/03/2017.
 */
public class Alumno implements KvmSerializable {

    private int id;
    private String matricula;
    private String grado;
    private String fecha;
    private String becado;
    private String nota;

    public Alumno(int id, String matricula, String grado, String fecha, String becado, String nota) {
        this.matricula = matricula;
        this.grado = grado;
        this.fecha = fecha;
        this.becado = becado;
        this.nota = nota;
    }

    public Alumno(){
        this(0,"","","","","");
    }


    @Override
    public Object getProperty(int i) {
        switch (i){
            case 0:
                return id;
            case 1:
                return matricula;
            case 2:
                return grado;
            case 3:
                return fecha;
            case 4:
                return becado;
            case 5:
                return nota;

        }
        return null;
    }

    @Override
    public int getPropertyCount() {
        return 6;
    }

    @Override
    public void setProperty(int i, Object o) {
        switch (i){
            case 0:
                id = Integer.parseInt(o.toString());
                break;
            case 1:
                matricula = o.toString();
                break;
            case 2:
                grado = o.toString();
                break;
            case 3:
                fecha = o.toString();
                break;
            case 4:
                becado = o.toString();
                break;
            case 5:
                nota = o.toString();
                break;
        }
    }

    @Override
    public void getPropertyInfo(int i, Hashtable hashtable, PropertyInfo propertyInfo) {
        switch (i){
            case 0:
                propertyInfo.type = PropertyInfo.INTEGER_CLASS;
                propertyInfo.name = "id";
                break;
            case 1:
                propertyInfo.type = PropertyInfo.STRING_CLASS;
                propertyInfo.name = "matricula";
                break;
            case 2:
                propertyInfo.type = PropertyInfo.STRING_CLASS;
                propertyInfo.name = "grado";
                break;
            case 3:
                propertyInfo.type = PropertyInfo.STRING_CLASS;
                propertyInfo.name = "fecha";
                break;
            case 4:
                propertyInfo.type = PropertyInfo.STRING_CLASS;
                propertyInfo.name = "becado";
                break;
            case 5:
                propertyInfo.type = PropertyInfo.STRING_CLASS;
                propertyInfo.name = "nota";
                break;
            default:
                break;


        }
    }
}
