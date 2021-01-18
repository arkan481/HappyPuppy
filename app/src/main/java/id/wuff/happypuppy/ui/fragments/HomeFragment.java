package id.wuff.happypuppy.ui.fragments;

import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import id.wuff.happypuppy.R;
import id.wuff.happypuppy.adapters.RVHomeAdapter;
import id.wuff.happypuppy.model.Pet;
import id.wuff.happypuppy.utils.AppSessionManager;
import id.wuff.happypuppy.utils.DatabaseHelper;
/**
 * DetailFragment is the main fragment of the app, this
 * fragment will be shown first every time the app is opened.
 */
public class HomeFragment extends Fragment {

    private RecyclerView recyclerView;
    private RVHomeAdapter rvHomeAdapter;
    private ImageView ivPhoto;
    private CardView cvCat1, cvCat2, cvCat3;
    private TextView tvCat1, tvCat2, tvCat3;
    private EditText etSearch;

    private ExtendedFloatingActionButton floatingActionButton;

    private DatabaseHelper databaseHelper;

    public HomeFragment() {
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
        View v = inflater.inflate(R.layout.fragment_home, container, false);

        databaseHelper = new DatabaseHelper(getContext());

        recyclerView = v.findViewById(R.id.rv_home);
        floatingActionButton = v.findViewById(R.id.home_fab);
        tvCat1 = v.findViewById(R.id.tv_cat_1);
        tvCat2 = v.findViewById(R.id.tv_cat_2);
        tvCat3 = v.findViewById(R.id.tv_cat_3);
        cvCat1 = v.findViewById(R.id.cv_cat_1);
        cvCat2 = v.findViewById(R.id.cv_cat_2);
        cvCat3 = v.findViewById(R.id.cv_cat_3);
        etSearch = v.findViewById(R.id.et_search);

        ivPhoto = v.findViewById(R.id.iv_prof_photo);

        checkPhoto();

        operateCategorySelection(1);

        floatingActionButton.setOnClickListener(view -> {
            NavDirections action = HomeFragmentDirections.actionHomeFragmentToPetFragment();
            Navigation.findNavController(view).navigate(action);
        });

        cvCat1.setOnClickListener(view -> operateCategorySelection(1));

        cvCat2.setOnClickListener(view -> operateCategorySelection(2));

        cvCat3.setOnClickListener(view -> operateCategorySelection(3));

        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                rvHomeAdapter.getFilter().filter(editable.toString());
            }
        });

        populateRecyclerview();

        return v;
    }

    /**
     * Method to fetch every pet ignoring its category
     */
    private void populateRecyclerview() {
        ArrayList<Pet> pets = databaseHelper.fetchAllPet();
        rvHomeAdapter = new RVHomeAdapter(pets);
        recyclerView.setAdapter(rvHomeAdapter);
    }

    /**
     * Method to fetch every cats only
     */
    private void populateRecyclerviewCats() {
        ArrayList<Pet> pets = databaseHelper.fetchCats();
        rvHomeAdapter = new RVHomeAdapter(pets);
        recyclerView.setAdapter(rvHomeAdapter);
    }

    /**
     * Method to fetch every dogs only
     */
    private void populateRecyclerviewDogs() {
        ArrayList<Pet> pets = databaseHelper.fetchDogs();
        rvHomeAdapter = new RVHomeAdapter(pets);
        recyclerView.setAdapter(rvHomeAdapter);
    }

    /**
     * Method to operate the category selection button
     */
    private void operateCategorySelection(int position) {
        tvCat1.setTextColor(Color.BLACK);
        tvCat2.setTextColor(Color.BLACK);
        tvCat3.setTextColor(Color.BLACK);
        cvCat1.setCardBackgroundColor(Color.WHITE);
        cvCat2.setCardBackgroundColor(Color.WHITE);
        cvCat3.setCardBackgroundColor(Color.WHITE);
        switch (position) {
            case 1:
                // All
                cvCat1.setCardBackgroundColor(Color.parseColor("#E895A1"));
                tvCat1.setTextColor(Color.WHITE);
                populateRecyclerview();
                break;
            case 2:
                // Cat
                cvCat2.setCardBackgroundColor(Color.parseColor("#E895A1"));
                tvCat2.setTextColor(Color.WHITE);
                populateRecyclerviewCats();
                break;
            case 3:
                // Dog
                cvCat3.setCardBackgroundColor(Color.parseColor("#E895A1"));
                tvCat3.setTextColor(Color.WHITE);
                populateRecyclerviewDogs();
                break;
        }
    }

    /**
     * Method to check if the user has set their photo, if they have, the photo will be displayed
     */
    private void checkPhoto() {
        if(AppSessionManager.getInstance(getContext()).getPhoto() != null) {
            ivPhoto.setImageURI(Uri.parse(AppSessionManager.getInstance(getContext()).getPhoto()));
        }
    }
}