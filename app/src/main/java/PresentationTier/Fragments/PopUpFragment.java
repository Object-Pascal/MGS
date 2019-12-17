package PresentationTier.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.gaanplaatsstaan.R;

public abstract class PopUpFragment extends Fragment {


    public abstract void ShowPopUpFragment(String message);
}
