package id.wuff.happypuppy.ui.fragments;

import android.Manifest;
import android.app.DatePickerDialog;
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
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import id.wuff.happypuppy.R;
import id.wuff.happypuppy.model.Pet;
import id.wuff.happypuppy.utils.DatabaseHelper;
/**
 * DetailFragment is the fragment to insert a pet
 */
public class PetFragment extends Fragment {

    private CardView cvProceed;
    private EditText etBirthDate, etName, etAge;
    private Spinner genderSpinner, categorySpinner;
    private ImageView ivPhoto;

    private DatePickerDialog.OnDateSetListener dateSetListener;

    final Calendar calendar = Calendar.getInstance();

    private final static int GALLERY_RC = 48;
    private String photoURI;

    private DatabaseHelper databaseHelper;
    private Pet pet;
    private boolean isUpdating = false;

    public PetFragment() {
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
        View v = inflater.inflate(R.layout.fragment_pet, container, false);

        pet = PetFragmentArgs.fromBundle(getArguments()).getPet();

        cvProceed = v.findViewById(R.id.cv_proceed);
        etBirthDate = v.findViewById(R.id.et_birthdate);
        genderSpinner = v.findViewById(R.id.spinner_gender);
        categorySpinner = v.findViewById(R.id.spinner_category);
        ivPhoto = v.findViewById(R.id.iv_photo);
        etName = v.findViewById(R.id.et_pet_name);
        etAge = v.findViewById(R.id.et_pet_age);

        populateDateListener();

        populateGenderSpinner();

        populateCategorySpinner();

        etBirthDate.setOnClickListener(view -> new DatePickerDialog(getContext(), dateSetListener, calendar
                .get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)).show());

        ivPhoto.setOnClickListener(view -> checkGalleryPermission());

        cvProceed.setOnClickListener(view -> {
            if(etName.getText().equals("")
                    || etAge.getText().equals("")
                    || etBirthDate.getText().equals("")
                    || photoURI == null) {
                Toast.makeText(getContext(), "Please fill in all of the necessities", Toast.LENGTH_LONG).show();
            } else {
                save();
            }
        });

        databaseHelper = new DatabaseHelper(getContext());

        checkUpdating();

        return v;
    }

    /**
     * Method to check if the state of this page is either to update an existing or creating a new pet
     */
    private void checkUpdating() {
        if(pet != null) {
            isUpdating = true;
            etName.setText(pet.getName());
            etAge.setText(pet.getAge() + "");
            etBirthDate.setText(pet.getBirthdate());
            ivPhoto.setImageURI(Uri.parse(pet.getPhoto()));
            photoURI = pet.getPhoto();

            if(pet.getCategory().equals("Cat")) {
                categorySpinner.setSelection(0, true);
            } else {
                categorySpinner.setSelection(1, true);
            }

            if(pet.getGender().equals("Male")) {
                genderSpinner.setSelection(0, true);
            } else {
                genderSpinner.setSelection(1, true);
            }
        }
    }

    /**
     * Method to save or update a pet
     */
    private void save() {
        Pet pet = new Pet();
        pet.setName(etName.getText().toString());
        pet.setAge(Integer.valueOf(etAge.getText().toString()));
        pet.setBirthdate(etBirthDate.getText().toString());
        pet.setCategory(categorySpinner.getSelectedItem().toString());
        pet.setGender(genderSpinner.getSelectedItem().toString());
        pet.setPhoto(photoURI);

        if(isUpdating) {
            pet.setId(this.pet.getId());
            databaseHelper.updatePet(pet);
        } else {
            databaseHelper.insertPet(pet);
        }

        NavDirections action = PetFragmentDirections.actionPetFragmentToHomeFragment();
        Navigation.findNavController(getView()).navigate(action);
    }

    /**
     * Method to check if the gallery permission has already been allowed
     */
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

    /**
     * Method to get the URI of the selected pet's picture
     */
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

    /**
     * Method to intent to the phone's gallery
     */
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

    private void updateLabel() {
        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        etBirthDate.setText(sdf.format(calendar.getTime()));
    }

    private void populateDateListener() {
        dateSetListener = (datePicker, i, i1, i2) -> {
            calendar.set(Calendar.YEAR, i);
            calendar.set(Calendar.MONTH, i1);
            calendar.set(Calendar.DAY_OF_MONTH, i2);
            updateLabel();
        };
    }

    private void populateGenderSpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.gender_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genderSpinner.setAdapter(adapter);
    }

    private void populateCategorySpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.category_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(adapter);
    }


}