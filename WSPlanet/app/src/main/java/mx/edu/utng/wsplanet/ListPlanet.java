package mx.edu.utng.wsplanet;

import android.app.ListActivity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpResponseException;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Vector;

/**
 * Created by Jorge on 31/03/2017.
 */
public class ListPlanet extends ListActivity {

    private final String NAMESPACE ="http://ws.utng.edu.mx";

    private final SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);

    private ArrayList<Planet> planets = new ArrayList<Planet>();
    private int idSelected;
    private int positionSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TaskWSConsulted consulted = new TaskWSConsulted();
        consulted.execute();
        registerForContextMenu(getListView());
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_modificar:
                Planet planet = planets.get(positionSelected);
                Bundle bundleLibro = new Bundle();
                for (int i = 0; i < planet.getPropertyCount(); i++) {
                    bundleLibro.putString("valor" + i, planet.getProperty(i)
                            .toString());
                }
                bundleLibro.putString("accion", "modificar");
                Intent intent = new Intent(ListPlanet.this, MainActivity.class);
                intent.putExtras(bundleLibro);
                startActivity(intent);

                return true;
            case R.id.item_eliminar:
                TaskWSDelete eliminar = new TaskWSDelete();
                eliminar.execute();

                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = new MenuInflater(getApplicationContext());
        menuInflater.inflate(R.menu.menu_regresar, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_regresar:
                startActivity(new Intent(ListPlanet.this, MainActivity.class));
                break;
            default:
                break;
        }
        return super.onMenuItemSelected(featureId, item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        menu.setHeaderTitle(getListView().getAdapter().getItem(info.position).toString());
        idSelected = (Integer) planets.get(info.position).getProperty(0);
        positionSelected = info.position;
        inflater.inflate(R.menu.menu_contextual, menu);
    }




    private class TaskWSConsulted extends AsyncTask<String, Integer, Boolean> {

        protected Boolean doInBackground(String... params) {

            boolean result = true;
            final String METHOD_NAME = "getPlanets";
            final String SOAP_ACTION = NAMESPACE + "/" + METHOD_NAME;
            planets.clear();
            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
            envelope.setOutputSoapObject(request);
            HttpTransportSE transporte = new HttpTransportSE(MainActivity.URL);
            try {
                transporte.call(SOAP_ACTION, envelope);
                Vector<SoapObject> response = (Vector<SoapObject>) envelope.getResponse();
                if (response != null) {
                    for (SoapObject objSoap : response) {
                        Planet planet = new Planet();
                        planet.setProperty(0, Integer.parseInt(objSoap.getProperty("id").toString()));
                        planet.setProperty(1, objSoap.getProperty("name").toString());
                        planet.setProperty(2, objSoap.getProperty("size").toString());
                        planet.setProperty(3, objSoap.getProperty("moons").toString());
                        planet.setProperty(4, objSoap.getProperty("diameter").toString());
                        planet.setProperty(5, objSoap.getProperty("galaxy").toString());
                        planet.setProperty(6, Integer.parseInt(objSoap.getProperty("distance").toString()));
                        planet.setProperty(7, Integer.parseInt(objSoap.getProperty("gravity").toString()));


                        planets.add(planet);
                    }
                }

            } catch (XmlPullParserException e) {
                Log.e("Error XMLPullParser", e.toString());
                result = false;
            } catch (HttpResponseException e) {
                Log.e("Error HTTP", e.toString());

                result = false;
            } catch (IOException e) {
                Log.e("Error IO", e.toString());
                result = false;
            } catch (ClassCastException e) {
                try {
                    SoapObject objSoap = (SoapObject) envelope.getResponse();
                    Planet planet = new Planet();
                    planet.setProperty(0, Integer.parseInt(objSoap.getProperty("id").toString()));
                    planet.setProperty(1, objSoap.getProperty("name").toString());
                    planet.setProperty(2, objSoap.getProperty("size").toString());
                    planet.setProperty(3, objSoap.getProperty("moons").toString());
                    planet.setProperty(4, objSoap.getProperty("diameter").toString());
                    planet.setProperty(5, objSoap.getProperty("galaxy").toString());
                    planet.setProperty(6, Integer.parseInt(objSoap.getProperty("distance").toString()));
                    planet.setProperty(7, Integer.parseInt(objSoap.getProperty("gravity").toString()));

                    planets.add(planet);
                } catch (SoapFault e1) {
                    Log.e("Error SoapFault", e.toString());
                    result = false;
                }
            }
            return result;
        }

        protected void onPostExecute(Boolean result) {

            if (result) {
                final String[] datos = new String[planets.size()];
                for (int i = 0; i < planets.size(); i++) {
                    datos[i] = planets.get(i).getProperty(0) + " - "
                            + planets.get(i).getProperty(1)+ " - "
                            + planets.get(i).getProperty(2)+ " - "
                            + planets.get(i).getProperty(3)+ " - "
                            + planets.get(i).getProperty(4)+ " - "
                            + planets.get(i).getProperty(5)+ " - "
                            + planets.get(i).getProperty(6)+ " - "
                            + planets.get(i).getProperty(7);
                }

                ArrayAdapter<String> adaptador = new ArrayAdapter<String>(
                        ListPlanet.this,
                        android.R.layout.simple_list_item_1, datos);
                setListAdapter(adaptador);
            } else {
                Toast.makeText(getApplicationContext(), "No se encontraron datos.",
                        Toast.LENGTH_SHORT).show();
            }
        }
    }

    private class TaskWSDelete extends AsyncTask<String, Integer, Boolean> {

        protected Boolean doInBackground(String... params) {

            boolean result = true;

            final String METHOD_NAME = "removePlanet";
            final String SOAP_ACTION = NAMESPACE + "/" + METHOD_NAME;


            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
            request.addProperty("id", idSelected);

            envelope.setOutputSoapObject(request);
            HttpTransportSE transporte = new HttpTransportSE(MainActivity.URL);
            try {
                transporte.call(SOAP_ACTION, envelope);
                SoapPrimitive resultado_xml = (SoapPrimitive) envelope.getResponse();
                String res = resultado_xml.toString();

                if (!res.equals("0")) {
                    result = true;
                }

            } catch (Exception e) {
                Log.e("Error", e.toString());
                result = false;
            }
            return result;
        }

        protected void onPostExecute(Boolean result) {

            if (result) {
                Toast.makeText(getApplicationContext(),
                        "Eliminado", Toast.LENGTH_SHORT).show();
                TaskWSConsulted consulta = new TaskWSConsulted();
                consulta.execute();
            } else {
                Toast.makeText(getApplicationContext(), "Error al eliminar",
                        Toast.LENGTH_SHORT).show();
            }
        }
    }

}
