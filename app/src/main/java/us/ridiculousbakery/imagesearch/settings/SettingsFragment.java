package us.ridiculousbakery.imagesearch.settings;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import us.ridiculousbakery.imagesearch.search.ImageSearchActivity;
import us.ridiculousbakery.imagesearch.R;

public class SettingsFragment extends DialogFragment {
//    public interface  SettingsModalListener{
//        void onFinishSettingsFragment(Settings more_settings);
//    }
    private Settings settings;


    private Spinner spImageSize;
    private Spinner spColorFilter;
    private Spinner spImageType;
    private Button btnOK;
    private EditText etSiteFilter;

    public SettingsFragment() {
    }


    public static SettingsFragment newInstance(Settings args) {

        SettingsFragment frag = new SettingsFragment();
        frag.setArguments(args.toBundle());
        return frag;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container);
        etSiteFilter = (EditText) view.findViewById(R.id.etSiteFilter);
        settings = (Settings) getArguments().getSerializable("data");
        getDialog().setTitle( "Advanced Filters");
        spImageSize = (Spinner) view.findViewById(R.id.spImageSize);
        spColorFilter = (Spinner) view.findViewById(R.id.spColorFilter);
        spImageType = (Spinner) view.findViewById(R.id.spImageType);
        btnOK = (Button) view.findViewById(R.id.btn_OK);
        spImageSize.setSelection(settings.get_selectedSize());
        spColorFilter.setSelection(settings.get_selectedColor());
        spImageType.setSelection(settings.get_selectedType());
        etSiteFilter.setText(settings.get_selectedSite());


        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ((ImageSearchActivity)getActivity()).onFinishSettingsFragment(settings);
                dismiss();
            }
        });
        return view;
    }

}
