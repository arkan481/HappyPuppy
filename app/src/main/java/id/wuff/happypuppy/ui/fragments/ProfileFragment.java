package id.wuff.happypuppy.ui.fragments;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import id.wuff.happypuppy.R;
import id.wuff.happypuppy.utils.AppSessionManager;

/**
 * Profile fragment is a fragment that will be shown when
 * profile button on the navigation view is clicked
 */
public class ProfileFragment extends Fragment {

    private EditText etFn, etLn;
    private ImageView ivPhoto;
    private CardView cvUpdate;
    private Spinner genderSpinner;

    private String photoURI;

    private final static int GALLERY_RC = 48;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_profile, container, false);

        etFn = v.findViewById(R.id.et_prof_fn);
        etLn = v.findViewById(R.id.et_prof_ln);
        genderSpinner = v.findViewById(R.id.spinner_gender);
        ivPhoto = v.findViewById(R.id.iv_prof_photo);
        cvUpdate = v.findViewById(R.id.cv_prof_update);

        ivPhoto.setOnClickListener(view -> checkGalleryPermission());

        cvUpdate.setOnClickListener(view -> update());

        populateGenderSpinner();

        checkUpdate();

        return v;
    }

    private void populateGenderSpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.gender_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genderSpinner.setAdapter(adapter);
    }

    private void checkUpdate() {
        if(AppSessionManager.getInstance(getContext()).getPhoto() != null) {
            etFn.setText(AppSessionManager.getInstance(getContext()).getFirstName());
            etLn.setText(AppSessionManager.getInstance(getContext()).getLastName());
            if(AppSessionManager.getInstance(getContext()).getGender().equals("male")) {
                genderSpinner.setSelection(0, true);
            } else {
                genderSpinner.setSelection(1, true);
            }
            ivPhoto.setImageURI(Uri.parse(AppSessionManager.getInstance(getContext()).getPhoto()));
        }
    }

    private void update() {
        if(photoURI == null) {
            Toast.makeText(getContext(), "Please change your profile photo!", Toast.LENGTH_LONG).show();
        } else if(etFn.getText().toString().equals("")
                || etLn.getText().toString().equals("")
                || photoURI.equals("")) {
            Toast.makeText(getContext(), "Please fill in all of the necessities!", Toast.LENGTH_LONG).show();
        } else {
            AppSessionManager.getInstance(getContext()).setFirstname(etFn.getText().toString());
            AppSessionManager.getInstance(getContext()).setLastName(etLn.getText().toString());
            AppSessionManager.getInstance(getContext()).setGender(genderSpinner.getSelectedItem().toString());
            AppSessionManager.getInstance(getContext()).setPhoto(photoURI);
            Toast.makeText(getContext(), "Profile updated successfully", Toast.LENGTH_LONG).show();
        }
    }

    private void checkGalleryPermission() {
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_DENIED) {
                //permission denied
                String[] permissions={Manifest.permission.READ_EXTERNAL_STORAGE};
                //show popup
                requestPermissions(permissions, GALLERY_RC);
            }else {
                //permission granted
                intentGallery();

            }
        }else {
            // lower than marshmallow
            intentGallery();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode==getActivity().RESULT_OK&&requestCode==GALLERY_RC){
            Uri uri = data.getData();
            getContext().getContentResolver().takePersistableUriPermission(uri,Intent.FLAG_GRANT_READ_URI_PERMISSION|Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            ivPhoto.setImageURI(data.getData());
            photoURI = uri.toString();

        }else {
            Toast.makeText(getContext(),"Error setting the image",Toast.LENGTH_SHORT).show();
        }
    }

    private void intentGallery() {
        Intent intentToGallery = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intentToGallery.setType("image/*");
        startActivityForResult(intentToGallery, GALLERY_RC);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case GALLERY_RC:
                if(grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    intentGallery();
                } else {
                    Toast.makeText(getContext(), "Gallery Permission Denied!", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }
}