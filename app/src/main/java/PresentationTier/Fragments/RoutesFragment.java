package PresentationTier.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.gaanplaatsstaan.R;

import java.util.Objects;
import java.util.zip.Inflater;

public class RoutesFragment extends Fragment
{
    private LayoutInflater inflater;
    private boolean isPaused=false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        this.inflater = inflater;
        return inflater.inflate(R.layout.routes_fragment, container, false);
    }
}
