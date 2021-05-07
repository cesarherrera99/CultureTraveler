package com.example.culturetraveler;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

public class CustomInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {

    private final View mWindow;
    private Context mContext;
    private ImageView mClose, mGo;

    public CustomInfoWindowAdapter(Context context) {
        mContext = context;
        mWindow = LayoutInflater.from(context).inflate(R.layout.custom_info_window, null);
    }

    private void renderWindowText(Marker marker, View view){
        String title = marker.getTitle();
        TextView tvTitle = (TextView) view.findViewById(R.id.window_title);

        if (!title.equals("")){
            tvTitle.setText(title);
        }

        String snippet = marker.getSnippet();
        TextView tvSnippet = (TextView) view.findViewById(R.id.window_snippet);

        if (!title.equals("")){
            tvSnippet.setText(snippet);
        }
    }

    @Override
    public View getInfoWindow(Marker marker) {
        renderWindowText(marker, mWindow);
        return mWindow;
    }

    @Override
    public View getInfoContents(Marker marker) {
        renderWindowText(marker, mWindow);
        return mWindow;
    }

    /*public void buttonsFunction(Marker marker, View view){
        mClose = (ImageView) view.findViewById(R.id.ic_close);
        mGo = (ImageView) view.findViewById(R.id.ic_go);
        Marker mMarker = marker;
        mClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mMarker.isInfoWindowShown()){
                    mMarker.hideInfoWindow();
                }
            }
        });
        mGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
    }*/
}