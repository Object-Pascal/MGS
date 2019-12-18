package PresentationTier.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.gaanplaatsstaan.R;

/**
 * De juiste tekst moet nog toegevoegd worden in de strings.xml
 */
public class Error extends PopUpFragment {

    private String error;

    private String errorMessage;
    private String errorFix;

    private TextView txt_Titel;
    private TextView txt_Information;
    private Button btn_OK;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.popup_fragment, parent, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        this.error = "Foutmelding";

        this.txt_Titel = (TextView) view.findViewById(R.id.txt_titel);
        this.txt_Information = (TextView) view.findViewById(R.id.txt_information);
        this.btn_OK = (Button) view.findViewById(R.id.btn_ok);

        ShowPopUpFragment(getString(R.string.error));

        this.btn_OK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });
    }

    @Override
    public void ShowPopUpFragment(String message) {

        this.txt_Titel.setText(this.error);
        this.txt_Information.setText(message);
    }
}
