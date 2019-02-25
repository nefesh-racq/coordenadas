package com.example.epsilon.getcoord;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Created by epsilon
 */

public class MainActivity extends AppCompatActivity {
    private TextView txtvCoord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //textview para mostrar los datos
        txtvCoord = (TextView)findViewById(R.id.txtvShowCoord);

        // declaramos una variable para verificar los permisos
        int permissionCheck = ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION);

        /*
        * comparamos si hay permisos para usar el gps si no lo hay
        * pedimos permisos
        * */
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                /*
                 * aca va el codigo para mostrar un mensaje al
                 * usuario apra que acepte los permisos
                */

            } else {
                // pedimos permisos para poder usar el gps
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        1);
            }
        }

        // variable para usar LocationManager
        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        // definicion de nuestro listener para que oiga las actualizaciones del gps
        LocationListener locationListener = new LocationListener() {
            public void onLocationChanged(Location location) {
                // mostramos el mensaje, se llama el metodo cuando hay cambios
                txtvCoord.setText("latitud : [ " + location.getLatitude() + " ]\nlongitud : [ " + location.getLongitude() + " ]");
            }

            public void onStatusChanged(String provider, int status, Bundle extras) {
                txtvCoord.setText("onStatusChanged");
            }

            public void onProviderEnabled(String provider) {
                txtvCoord.setText("onProviderEnabled");
            }

            public void onProviderDisabled(String provider) {
                txtvCoord.setText("onProviderDisabled");
            }
        };

        /*
         *registramos al oyente "Listener" en el administrador de ubicaciones para recibir
         * actualizacioens para nuestras coordenadas del gps
         */
        // comparamos si hay permisos para poder actualizar las coordenadas si no falla
        if (permissionCheck == PackageManager.PERMISSION_GRANTED)
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
            /*
            * minTime(intervalo) tiempo en milisegundos para refrescar el gps
            * minDistance(distancia) distancia en metros,
            * Si intervalo es mayor que 0, el LocationManager podría descansar para intervalo milisegundos entre actualizaciones de ubicación
            * Si distancia es mayor que 0, una ubicación solo se emitirá si el dispositivo se mueve distancia metros
            * */
    }
}
