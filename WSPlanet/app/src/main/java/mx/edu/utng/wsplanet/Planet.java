package mx.edu.utng.wsplanet;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

import java.util.Hashtable;


/**
 * Created by Jorge on 31/03/2017.
 */
public class Planet  implements KvmSerializable{

    private int id;
    private String name;
    private String size;
    private String moons;
    private String diameter;
    private String galaxy;
    private int distance;
    private int gravity;

    public Planet(int id, String name, String size, String moons, String diameter, String galaxy, int distance,
                  int gravity) {
        super();
        this.id = id;
        this.name = name;
        this.size = size;
        this.moons = moons;
        this.diameter = diameter;
        this.galaxy = galaxy;
        this.distance = distance;
        this.gravity = gravity;
    }

    public Planet(){
        this(0,"","","","","",0,0);
    }

    @Override
    public Object getProperty(int i) {
        switch (i){
            case 0:
                return id;
            case 1:
                return name;
            case 2:
                return size;
            case 3:
                return moons;
            case 4:
                return diameter;
            case 5:
                return galaxy;
            case 6:
                return distance;
            case 7:
                return gravity;

        }
        return null;
    }

    @Override
    public int getPropertyCount() {
        return 8;
    }

    @Override
    public void setProperty(int i, Object o) {
        switch (i){
            case 0:
                id = Integer.parseInt(o.toString());
                break;
            case 1:
                name = o.toString();
                break;
            case 2:
                size = o.toString();
                break;
            case 3:
                moons = o.toString();
                break;
            case 4:
                diameter = o.toString();
                break;
            case 5:
                galaxy = o.toString();
                break;
            case 6:
                distance = Integer.parseInt(o.toString());
                break;
            case 7:
                gravity = Integer.parseInt(o.toString());
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
                propertyInfo.name = "name";
                break;
            case 2:
                propertyInfo.type = PropertyInfo.STRING_CLASS;
                propertyInfo.name = "size";
                break;
            case 3:
                propertyInfo.type = PropertyInfo.STRING_CLASS;
                propertyInfo.name = "moons";
                break;
            case 4:
                propertyInfo.type = PropertyInfo.STRING_CLASS;
                propertyInfo.name = "diameter";
                break;
            case 5:
                propertyInfo.type = PropertyInfo.STRING_CLASS;
                propertyInfo.name = "galaxy";
                break;
            case 6:
                propertyInfo.type = PropertyInfo.STRING_CLASS;
                propertyInfo.name = "distance";
                break;
            case 7:
                propertyInfo.type = PropertyInfo.STRING_CLASS;
                propertyInfo.name = "gravity";
                break;
            default:
                break;
        }
    }
}
