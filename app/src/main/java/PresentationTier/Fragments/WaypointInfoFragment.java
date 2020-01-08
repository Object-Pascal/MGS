package PresentationTier.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.gaanplaatsstaan.R;
import com.squareup.picasso.Picasso;

import LogicTier.RouteManager.Route.Waypoint;

public class WaypointInfoFragment extends Fragment
{

    private Waypoint waypoint;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();

        waypoint = (Waypoint)args.getSerializable("waypoint");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.waypoint_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView textTitle = view.findViewById(R.id.lblWaypointTitle);
        TextView textExplain = view.findViewById(R.id.lblWaypointDescription);
        ImageView imageView = view.findViewById(R.id.imgContent);

        Button btnBack = view.findViewById(R.id.btnBack);
        final Fragment fragment = this;
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                getActivity().getSupportFragmentManager().beginTransaction().remove(fragment).commit();
            }
        });

        textTitle.setText(waypoint.getName());
        textExplain.setText(waypoint.getDescription());

        Picasso.get()
                .load(waypoint.getMultimedia().get(0))
                .into(imageView);
    }
}
