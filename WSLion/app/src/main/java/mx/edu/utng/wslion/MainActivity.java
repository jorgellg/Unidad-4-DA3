package mx.edu.utng.wslion;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.MarshalFloat;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpResponseException;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

public class MainActivity extends AppCompatActivity  implements View.OnClickListener{

    private EditText etName;
    private EditText etSize;
    private EditText etWeight;
    private EditText etColor;
    private EditText etLocation;
    private EditText etSex;
    private EditText etAge;


    private Button btGuardar;
    private Button btListar;

    private Lion lion = null;

    final String NAMESPACE = "http://ws.utng.edu.mx";
    final SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
    static String URL = "http://192.168.24.195:8080/WSLion/services/LionWS";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startComponentes();
    }


    private void startComponentes() {
        etName = (EditText) findViewById(R.id.et_name);
        etSize = (EditText) findViewById(R.id.et_size);
        etWeight = (EditText) findViewById(R.id.et_weight);
        etColor = (EditText) findViewById(R.id.et_color);
        etLocation = (EditText) findViewById(R.id.et_location);
        etSex = (EditText) findViewById(R.id.et_sex);
        etAge = (EditText) findViewById(R.id.et_age);
        btGuardar = (Button) findViewById(R.id.bt_guardar);
        btListar = (Button) findViewById(R.id.bt_listar);
        btGuardar.setOnClickListener(this);
        btListar.setOnClickListener(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_consume_w, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onClick(View v) {
        String name = etName.getText().toString();
        String size = etSize.getText().toString();
        String weight = etWeight.getText().toString();
        String color = etColor.getText().toString();
        String location = etLocation.getText().toString();
        String sex = etSex.getText().toString();
        String age = etAge.getText().toString();


        if (v.getId() == btGuardar.getId()) {
            if (name != null && !name.isEmpty() &&
                    size != null && !size.isEmpty() &&
                    weight != null && !weight.isEmpty() &&
                    color != null && !color.isEmpty() &&
                    location != null && !location.isEmpty()&&
                    sex != null && !sex.isEmpty()&&
                    age != null && !age.isEmpty()) {
                try {
                    if (getIntent().getExtras().getString("accion")
                            .equals("modificar")) {
                        TaskWSUpdate tarea = new TaskWSUpdate();
                        tarea.execute();
                        cleanEditTex();
                    }

                } catch (Exception e) {
                    //Cuando no se haya mandado una accion por defecto es insertar.
                    TaskWSInsert movie = new TaskWSInsert();
                    movie.execute();
                }
            } else {
                Toast.makeText(this, "llenar todos los campos", Toast.LENGTH_LONG).show();
            }

        }
        if (btListar.getId() == v.getId()) {
            startActivity(new Intent(MainActivity.this, ListLions.class));
        }
    }//fin conClick

    private void cleanEditTex() {
        etName.setText("");
        etSize.setText("");
        etWeight.setText("");
        etColor.setText("");
        etLocation.setText("");
        etSex.setText("");
        etAge.setText("");
    }


    private class TaskWSInsert extends AsyncTask<String, Integer, Boolean> {

        @Override
        protected Boolean doInBackground(String... params) {
            boolean result = true;
            final String METHOD_NAME = "addLion";
            final String SOAP_ACTION = NAMESPACE + "/" + METHOD_NAME;

            SoapObject request =
                    new SoapObject(NAMESPACE, METHOD_NAME);

            lion = new Lion();
            lion.setProperty(0, 0);
            obtenerDatos();

            PropertyInfo info = new PropertyInfo();
            info.setName("lion");
            info.setValue(lion);
            info.setType(lion.getClass());
            request.addProperty(info);
            envelope.setOutputSoapObject(request);
            envelope.addMapping(NAMESPACE, "Lion", Lion.class);

            /* Para serializar flotantes y otros tipos no cadenas o enteros*/
            MarshalFloat mf = new MarshalFloat();
            mf.register(envelope);

            HttpTransportSE transporte = new HttpTransportSE(URL);
            try {
                transporte.call(SOAP_ACTION, envelope);
                SoapPrimitive response =
                        (SoapPrimitive) envelope.getResponse();
                String res = response.toString();
                if (!res.equals("1")) {
                    result = false;
                }

            } catch (Exception e) {
                Log.e("Error ", e.getMessage());
                result = false;
            }

            return result;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            if (result) {
                cleanEditTex();
                Toast.makeText(getApplicationContext(),
                        "Registro exitoso.",
                        Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplicationContext(),
                        "Error al insertar.",
                        Toast.LENGTH_SHORT).show();

            }
        }
    }//fin tarea insertar

    private class TaskWSUpdate extends
            AsyncTask<String, Integer, Boolean> {

        protected Boolean doInBackground(String... params) {

            boolean result = true;

            final String METHOD_NAME = "editLion";
            final String SOAP_ACTION = NAMESPACE + "/" + METHOD_NAME;

            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

            lion = new Lion();
            lion.setProperty(0, getIntent().getExtras().getString("valor0"));
            obtenerDatos();

            PropertyInfo info = new PropertyInfo();
            info.setName("lion");
            info.setValue(lion);
            info.setType(lion.getClass());

            request.addProperty(info);

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                    SoapEnvelope.VER11);

            envelope.setOutputSoapObject(request);

            envelope.addMapping(NAMESPACE, "Lion", lion.getClass());

            MarshalFloat mf = new MarshalFloat();
            mf.register(envelope);

            HttpTransportSE transporte = new HttpTransportSE(URL);

            try {
                transporte.call(SOAP_ACTION, envelope);

                SoapPrimitive resultado_xml = (SoapPrimitive) envelope
                        .getResponse();
                String res = resultado_xml.toString();

                if (!res.equals("1")) {
                    result = false;
                }

            } catch (HttpResponseException e) {
                Log.e("Error HTTP", e.toString());
            } catch (IOException e) {
                Log.e("Error IO", e.toString());
            } catch (XmlPullParserException e) {
                Log.e("Error XmlPullParser", e.toString());
            }

            return result;

        }

        protected void onPostExecute(Boolean result) {

            if (result) {
                Toast.makeText(getApplicationContext(), "Actualizado OK",
                        Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplicationContext(), "Error al actualizar",
                        Toast.LENGTH_SHORT).show();

            }
        }
    }

    public void obtenerDatos(){
        lion.setProperty(1, etName.getText().toString());
        lion.setProperty(2, etSize.getText().toString());
        lion.setProperty(3, etWeight.getText().toString());
        lion.setProperty(4, etColor.getText().toString());
        lion.setProperty(5, etLocation.getText().toString());
        lion.setProperty(6, etSex.getText().toString());
        lion.setProperty(7, etAge.getText().toString());

    }

    @Override
    protected void onResume() {
        super.onResume();
        Bundle datosRegreso = this.getIntent().getExtras();
        try {

            etName.setText(datosRegreso.getString("valor1"));
            etSize.setText(datosRegreso.getString("valor2"));
            etWeight.setText(datosRegreso.getString("valor3"));
            etColor.setText(datosRegreso.getString("valor4"));
            etLocation.setText(datosRegreso.getString("valor5"));
            etSex.setText(datosRegreso.getString("valor6"));
            etAge.setText(datosRegreso.getString("valor7"));

        } catch (Exception e) {
            Log.e("Error al Regargar", e.toString());
        }

    }

}
