package mx.edu.utng.wslion;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

import java.util.Hashtable;

/**
 * Created by Jorge on 31/03/2017.
 */
public class Lion implements KvmSerializable{

    private int id;
    private String name;
    private String size;
    private String weight;
    private String color;
    private String location;
    private String sex;
    private int age;

    public Lion(int id, String name, String size, String weight, String color, String location, String sex, int age) {
        this.id = id;
        this.name = name;
        this.size = size;
        this.weight = weight;
        this.color = color;
        this.location = location;
        this.sex = sex;
        this.age = age;
    }

    public Lion(){
        this(0,"","","","","","",0);
    }

    @Override
    public Object getProperty(int i) {
        switch (i) {
            case 0:
                return id;
            case 1:
                return name;
            case 2:
                return size;
            case 3:
                return weight;
            case 4:
                return color;
            case 5:
                return location;
            case 6:
                return sex;
            case 7:
                return age;
        }

        return  null;
    }

    @Override
    public int getPropertyCount() {
        return 8;
    }

    @Override
    public void setProperty(int i, Object o) {
        switch (i){
            case 0:
                id =Integer.parseInt(o.toString());
                break;
            case 1:
                name = o.toString();
                break;
            case 2:
                size = o.toString();
                break;
            case 3:
                weight = o.toString();
                break;
            case 4:
                color = o.toString();
                break;
            case 5:
                location = o.toString();
                break;
            case 6:
                sex = o.toString();
                break;
            case 7:
                age = Integer.parseInt(o.toString());
                break;
            default:
                break;
        }
    }

    @Override
    public void getPropertyInfo(int i, Hashtable hashtable, PropertyInfo propertyInfo) {
        switch (i) {
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
                propertyInfo.name = "weight";
                break;
            case 4:
                propertyInfo.type = PropertyInfo.STRING_CLASS;
                propertyInfo.name = "color";
                break;
            case 5:
                propertyInfo.type = PropertyInfo.STRING_CLASS;
                propertyInfo.name = "location";
                break;
            case 6:
                propertyInfo.type = PropertyInfo.STRING_CLASS;
                propertyInfo.name = "sex";
                break;
            case 7:
                propertyInfo.type = PropertyInfo.STRING_CLASS;
                propertyInfo.name = "age";
                break;
            default:
                break;
        }
    }
}
