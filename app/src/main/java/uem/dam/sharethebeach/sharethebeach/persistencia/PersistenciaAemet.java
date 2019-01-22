package uem.dam.sharethebeach.sharethebeach.persistencia;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.res.AssetManager;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;

import uem.dam.sharethebeach.sharethebeach.activities.Beach_List;
import uem.dam.sharethebeach.sharethebeach.activities.Login_Activity;
import uem.dam.sharethebeach.sharethebeach.bean.Playa;

public class PersistenciaAemet extends AsyncTask {
    private static final String FICHERO_PROPERTIES = "database.properties";
    private static final String PROPERTY_URL_AEMET = "urlAemet";
    private static final String PROPERTY_API_KEY_AEMET = "apiKeyAemet";
    private static final String FICHERO_PLAYAS = "Playas_Baleares.csv";

    private String urlAemet;
    private String apiKeyAemet;
    private Activity activity;
    private ArrayList<Playa> listadoPlayas;
    private Playa playaSolicitada;

    public PersistenciaAemet(Activity activity) {
        this.activity = activity;

        //Leemos el fichero csv que contiene los identificadores numericos que asigna aemet a cada playa
        leerFicheroPlayas();

        /*
        Generamos un objeto Properties y AssetManager para cargar el contenido del fichero database.properties
        que nos permite asignar los valores de este a los atributos que nos permiten realizar
        la conexión API-REST con AEMET.
         */
        Properties properties = new Properties();
        AssetManager asset = activity.getAssets();
        try {
            properties.load(new InputStreamReader(asset.open(FICHERO_PROPERTIES)));
            urlAemet = properties.getProperty(PROPERTY_URL_AEMET);
            apiKeyAemet = properties.getProperty(PROPERTY_API_KEY_AEMET);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
    Método doInBackground que realiza la conexión al API para descargar el estado de las playas
    y lo almacena en un arraylist de objeto playa.
     */
    @TargetApi(Build.VERSION_CODES.KITKAT)
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected Playa doInBackground(Object[] objects) {
        Playa aux = null;

        /*
        Iteramos los identificadores obtenidos del fichero de playas y se los vamos pasando al API
        mediante una conexión HTTP. Cada conexión nos devolverá una nueva url a la que deberemos acceder de nuevo
        para obtener el estado de dicha playa. Finalmente le asignamos dichos estados al objeto playa del arraylist.
         */

        if (playaSolicitada != null){
            obtenerEnlacesWebPlaya();

        /*
        Conectamos al API de aemet en cada uno de los enlaces obtenidos previamente y le asignamos los estados
        a cada playa.
         */
            aux = asignarEstadoPlayas();
        }

        return aux;
    }

    private void obtenerEnlacesWebPlaya() {
        //Variables de la conexión HTTP para obtener el estado de cada playa a partir de la dirección
        //suministrada previamente por el API Aemet.
        String datos = "";
        URL urlAemet;
        HttpURLConnection conexionApiAemet;
        InputStream isApiAemet = null;
        BufferedReader brApiAemet = null;
        String linea;
        JSONObject aemetJSON;

        try {
            urlAemet = new URL(this.urlAemet + playaSolicitada.getId() + apiKeyAemet);
            conexionApiAemet = (HttpURLConnection) urlAemet.openConnection();
            conexionApiAemet.setRequestProperty("http.keepAlive", "false");
            isApiAemet = conexionApiAemet.getInputStream();
            brApiAemet = new BufferedReader(new InputStreamReader(isApiAemet));

            while ((linea = brApiAemet.readLine()) != null) {
                datos += linea;
            }

            //Obtenemos la url que nos dará acceso al estado de la playa en cada iteración
            aemetJSON = new JSONObject(datos);

            boolean stop = false;
            for (int i = 0; i < listadoPlayas.size() && !stop; i++) {
                if (listadoPlayas.get(i).getId().equals(playaSolicitada.getId())) {
                    //listadoPlayas.get(i).setWeb((String) aemetJSON.get("datos").toString());
                    playaSolicitada.setWeb((String) aemetJSON.get("datos").toString());
                    System.out.println(listadoPlayas.get(i));
                    stop = true;
                }
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            try {
                if (brApiAemet != null) {
                    brApiAemet.close();
                }

                if (isApiAemet != null) {
                    isApiAemet.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private Playa asignarEstadoPlayas() {
        String datos = "";
        String linea = "";
        HttpURLConnection estadoPlaya = null;
        InputStream isPlaya = null;
        URL urlPlaya;
        BufferedReader brPlaya = null;
        JSONArray arrayPlaya;
        JSONObject playaJSON;
        int index = 0;

        try {
            urlPlaya = new URL(playaSolicitada.getWeb());
            estadoPlaya = (HttpURLConnection) urlPlaya.openConnection();
            estadoPlaya.setRequestProperty("http.keepAlive", "false");
            estadoPlaya.setRequestProperty("Accept-Charset", "UTF-8");
            isPlaya = estadoPlaya.getInputStream();

            brPlaya = new BufferedReader(new InputStreamReader(isPlaya));

            datos = "";

            while ((linea = brPlaya.readLine()) != null) {
                datos += linea;
            }

            //PARSING DE LOS DATOS JSON OBTENIDOS DE LA CONSULTA
            arrayPlaya = new JSONArray(datos);
            playaJSON = (JSONObject) (arrayPlaya.get(0));

            JSONArray dia;
            JSONObject prediccion;
            JSONObject temperatura;
            JSONObject valTemperatura;
            JSONObject valTemperaturaAgua;
            JSONObject valUvMax;
            JSONObject valViento;
            JSONObject valEstadoCielo;
            JSONObject valOleaje;
            int valFecha;

            boolean stop = false;
            //Iteramos el listado de playas hasta obtener la coincidencia mediante el id
            for (int i = 0; i < listadoPlayas.size() && !stop; i++) {

                if (listadoPlayas.get(i).getId().equals(playaSolicitada.getId())) {
                    index = i;

                    //Acceso a los nodos JSON
                    prediccion = playaJSON.getJSONObject("prediccion");
                    dia = prediccion.getJSONArray("dia");
                    temperatura = dia.getJSONObject(0);
                    System.out.println(temperatura.get("fecha"));
                    valTemperatura = (JSONObject) temperatura.get("tMaxima");
                    valTemperaturaAgua = (JSONObject) temperatura.get("tagua");
                    valUvMax = (JSONObject) temperatura.get("uvMax");
                    valViento = (JSONObject) temperatura.get("viento");
                    valEstadoCielo = (JSONObject) temperatura.get("estadoCielo");
                    valOleaje = (JSONObject) temperatura.get("oleaje");
                    valFecha = (int) temperatura.get("fecha");

                    //Adaptamos la fecha que recibimos como un numero entero a DATE
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
                    String fechaString = String.valueOf(valFecha);
                    Date fecha =  sdf.parse(fechaString);

                    //Asignacion de los valores al objeto playa con las referencias obtenidas del JSON
                    listadoPlayas.get(i).setNombre((String) playaJSON.get("nombre"));
                    listadoPlayas.get(i).setTemperatura((Integer) valTemperatura.get("valor1"));
                    listadoPlayas.get(i).setTemperaturaAgua((Integer) valTemperaturaAgua.get("valor1"));
                    listadoPlayas.get(i).setIndiceUV((Integer) valUvMax.get("valor1"));
                    listadoPlayas.get(i).setViento((String) valViento.get("descripcion1"));
                    listadoPlayas.get(i).setEstadoCielo((String) valEstadoCielo.get("descripcion1"));

                    //Parseamos un error que hay de aemet al recibir el oleaje debil
                    if (valOleaje.get("descripcion1").equals("d?bil")) {
                        listadoPlayas.get(i).setOleaje("debil");
                    } else {
                        listadoPlayas.get(i).setOleaje((String) valOleaje.get("descripcion1"));
                    }

                    listadoPlayas.get(i).setFecha_actualizacion(fecha);

                    System.out.println(listadoPlayas.get(i));
                    stop = true;
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } finally {
            try {
                if (brPlaya != null) {
                    brPlaya.close();
                }

                if (isPlaya != null) {
                    isPlaya.close();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return listadoPlayas.get(index);
    }

    /*
    Metodo que carga en un ArrayList el identificador, latitud y longitud de las playas a partir
    del fichero suministrado por Aemet que contiene dicha información en CSV.
     */
    private void leerFicheroPlayas() {
        String linea = "";
        String valor = "0";
        String grados = "";
        String minutos = "";
        String segundos = "";

        Playa playa = null;
        listadoPlayas = new ArrayList<Playa>();
        BufferedReader br = null;

        try {
            br = new BufferedReader(new InputStreamReader
                    (activity.getAssets().open(FICHERO_PLAYAS),"ISO-8859-1"));

            //Primera línea del fichero vacía de contenido relevante, no se procesa.
            br.readLine();

            //Lectura de cada línea del fichero con información de playas.
            while ((linea = br.readLine()) != null) {
                playa = new Playa();
                valor = "0";

                //Lectura de los identificadores de playa
                valor += linea.substring(0, 6);
                playa.setId(valor);

                /*
                Lectura de los nombres de las playas. Termina cuando encuentra un numero,
                ya que este equivale a un municipio.
                 */
                int index = 7;
                valor = "";

                while (!checkNumber(linea.charAt(index))) {
                    index++;
                }
                playa.setNombre(linea.substring(7, index -1));


                //Lectura del Municipio
                index = linea.length() - 30;
                StringBuffer municipio = new StringBuffer();

                while (!checkNumber(linea.charAt(index))) {
                    municipio.append(linea.charAt(index));
                    index --;
                }
                municipio.deleteCharAt(municipio.length() -1);
                playa.setMunicipio(municipio.reverse().toString());

                //Lectura de la Latitud GMS
                System.out.println(linea);
                grados = extraerGMS(linea, 27, 25);
                minutos = extraerGMS(linea, 23, 21);
                segundos = extraerGMS(linea, 19, 17);
                playa.setLatitud(deGMSaGD(Integer.parseInt(grados), Integer.parseInt(minutos), Integer.parseInt(segundos)));

                //Lectura de la Longitud GMS
                grados = extraerGMS(linea, 12, 11);
                minutos = extraerGMS(linea, 9, 7);
                segundos = extraerGMS(linea, 5, 3);
                playa.setLongitud(deGMSaGD(Integer.parseInt(grados), Integer.parseInt(minutos), Integer.parseInt(segundos)));

                listadoPlayas.add(playa);
            }

        } catch (IOException e) {
            e.printStackTrace();

        } finally {
            try {
                if (br != null) {
                    br.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //Metodo que convierte de Grados Minutos y Segundos a grados decimales para su uso en google maps
    private double deGMSaGD(int grados, int minutos, int segundos) {
        double resultado = Math.signum(grados) * (Math.abs(grados) + (minutos / 60.0) + (segundos / 3600.0));
        return resultado;
    }

    //Metodo que extrae las coordenadas GMS de la linea extraida del fichero de playas
    private String extraerGMS(String linea, int indiceComienzo, int indiceFinal) {
        return linea.substring(linea.length() - indiceComienzo, linea.length() - indiceFinal);
    }

    private boolean checkNumber(char i) {
        boolean stop = false;

        for (int j = 0; j < 10 && !stop; j++) {
            if (String.valueOf(i).equals(String.valueOf(j))) {
                stop = true;
            }
        }
        return stop;
    }

    public void obtenerPlaya(Playa playa) {
        this.playaSolicitada = playa;
        execute();
    }

    public ArrayList<Playa> getListadoPlayas() {
        return listadoPlayas;
    }

    @Override
    protected void onPostExecute(Object o) {
        if (activity instanceof Beach_List) {
            ((Beach_List) (activity)).lanzarActivityPlayaCallback(((Playa) o));
        }
    }
}
