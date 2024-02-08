package com.example.ex2evgaizkarodriguez;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.ex2evgaizkarodriguez.R;

import org.osmdroid.api.IMapController;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.ItemizedIconOverlay;
import org.osmdroid.views.overlay.ItemizedOverlayWithFocus;
import org.osmdroid.views.overlay.OverlayItem;

import java.util.ArrayList;

public class MapaFragment extends Fragment {

    MainActivity mActivity;
    private MapView mapa;
    private IMapController mapController;

    private OnPointSelectedListener mListener;


    public MapaFragment() {
        // Required empty public constructor
    }
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof MainActivity) {
            mActivity = (MainActivity) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement MainActivity");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_mapa, container, false);

        // Inicialización del mapa
        mapa = view.findViewById(R.id.mapaGPS);
        mapa.setTileSource(TileSourceFactory.DEFAULT_TILE_SOURCE);
        mapa.setMultiTouchControls(true);
        mapController = mapa.getController();
        mapController.setZoom(15.5);

        // Ubicación del Museo Guggenheim
        GeoPoint guggenheimPoint = new GeoPoint(43.2681, -2.9340);
        mapController.setCenter(guggenheimPoint);

        // Marcador del Museo Guggenheim
        ArrayList<OverlayItem> items = new ArrayList<>();
        OverlayItem guggenheimMarker = new OverlayItem("Museo Guggenheim", "Bilbao", guggenheimPoint);
        items.add(guggenheimMarker);

        ItemizedOverlayWithFocus<OverlayItem> overlay = new ItemizedOverlayWithFocus<>(
                getContext(), items,
                new ItemizedIconOverlay.OnItemGestureListener<OverlayItem>() {
                    @Override
                    public boolean onItemSingleTapUp(final int index, final OverlayItem item) {
                        GeoPoint targetPoint = new GeoPoint(item.getPoint().getLatitude(), item.getPoint().getLongitude());
                        mActivity.onPointSelected();
                        mapController.animateTo(targetPoint);


                        return false;
                    }

                    @Override
                    public boolean onItemLongPress(final int index, final OverlayItem item) {


                        return false;
                    }
                });

        // Capa de marcadores
        // Ikono pertsonalizatuak ezarteko karga
        Drawable markerPertsonalizatua = ContextCompat.getDrawable(requireContext(), R.drawable.location);

        // Ikono pertsonalizatuak ipini
        items.get(0).setMarker(markerPertsonalizatua);


        overlay.setFocusItemsOnTap(true);
        // "Marker"-en izenburua eta deskribapena agertzen karratuaren kolorea.
        overlay.setMarkerBackgroundColor(getResources().getColor(R.color.white));
        mapa.getOverlays().add(overlay);
        mapa.setUseDataConnection(true);
        return view;





    }



}
