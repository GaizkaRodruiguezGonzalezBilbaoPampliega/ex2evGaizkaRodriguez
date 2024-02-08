package com.example.ex2evgaizkarodriguez;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


import android.Manifest;
import android.os.Bundle;
import android.preference.PreferenceManager;

import org.osmdroid.config.Configuration;


public class MainActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        Configuration.getInstance().load(getApplicationContext(), PreferenceManager.getDefaultSharedPreferences(getApplicationContext()));
        Configuration.getInstance().setUserAgentValue("YourCustomUserAgent");
        // Mostrar el fragmento del mapa al iniciar la actividad
        loadFragment(new MapaFragment());
    }

    // Método para cargar un fragmento en el contenedor
    private void loadFragment(Fragment fragment) {
        // Obtener el FragmentManager
        FragmentManager fragmentManager = getSupportFragmentManager();

        // Comenzar una transacción de fragmentos
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        // Reemplazar el contenido del contenedor con el nuevo fragmento
        transaction.replace(R.id.fragment_container, fragment);

        // Agregar la transacción a la pila de retroceso solo si no es el fragmento del mapa
        if (!(fragment instanceof MapaFragment)) {
            transaction.addToBackStack(null);
        }

        // Commit la transacción
        transaction.commit();
    }

    // Método para mostrar el fragmento de información cuando se selecciona un punto en el mapa

    public void onPointSelected() {
        loadFragment(new InfoFragment());
    }

    // Método para volver al fragmento del mapa desde el fragmento de información
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (fragmentManager.getBackStackEntryCount() > 0) {
            fragmentManager.popBackStack();
        } else {
            loadFragment(new MapaFragment());
        }
    }

}
