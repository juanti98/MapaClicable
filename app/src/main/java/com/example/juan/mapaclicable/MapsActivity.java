package com.example.juan.mapaclicable;

import android.content.DialogInterface;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ConexionBBDD conexion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        conexion = new ConexionBBDD(this);

    }

    private void anadirPunto(final double latitude, final double longitude) {
        AlertDialog.Builder constructor = new AlertDialog.Builder(this);
        constructor.setTitle("Añadir Punto");
        constructor.setMessage("Añade un Punto");

        LayoutInflater inflador = LayoutInflater.from(this);
        View vista = inflador.inflate(R.layout.alert_vista, null);

        constructor.setView(vista);
        final EditText etTitulo;

        etTitulo = vista.findViewById(R.id.etTitulo);

        constructor.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String titulo, lat, lon;
                titulo = etTitulo.getText().toString();
                lat = Double.toString(latitude);
                lon = Double.toString(longitude);
                conexion.insercionBBDD(titulo,lat,lon);
                cargarPuntos();
            }
        });
        constructor.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.v("CancelarBTN", "Se ha cancelado");
            }
        });
        AlertDialog alert = constructor.create();
        alert.show();
    }

    private void cargarPuntos() {
        ArrayList<Puntos> pts = conexion.getPuntos();
        if (pts.size() > 1){
            for (int i = 0; i < pts.size(); i++){
                LatLng punto = new LatLng(Double.parseDouble(pts.get(i).getLat()), Double.parseDouble(pts.get(i).getLon()));
                mMap.addMarker(new MarkerOptions().position(punto).title(pts.get(i).getPuntos()));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(punto));
            }
        }

    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        cargarPuntos();

        GoogleMap.OnMapClickListener oyente = new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                anadirPunto(latLng.latitude, latLng.longitude);
            }
        };

        mMap.setOnMapClickListener(oyente);
    }
}
