package us.ridiculousbakery.imagesearch;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;

public class SettingsFragment extends DialogFragment {

    private Spinner spImageSize;
    private Spinner spColorFilter;
    private Spinner spImageType;
    private EditText etSiteFilter;

    public SettingsFragment() {
    }

    public static SettingsFragment newInstance(String title) {

        SettingsFragment frag = new SettingsFragment();
        Bundle args = new Bundle();
        args.putString("title", "Advanced Filters");
        frag.setArguments(args);
        return frag;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container);
        etSiteFilter = (EditText) view.findViewById(R.id.etSiteFilter);
        String title = getArguments().getString("title", "Advanced Filters");
        getDialog().setTitle(title);
        spImageSize = (Spinner) view.findViewById(R.id.spImageSize);
        spColorFilter = (Spinner) view.findViewById(R.id.spColorFilter);
        spImageType = (Spinner) view.findViewById(R.id.spImageType);


        return view;
    }
}
