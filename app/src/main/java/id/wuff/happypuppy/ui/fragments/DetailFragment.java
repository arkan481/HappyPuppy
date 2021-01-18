package id.wuff.happypuppy.ui.fragments;

import android.net.Uri;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Calendar;

import id.wuff.happypuppy.R;
import id.wuff.happypuppy.adapters.RVFeedListAdapter;
import id.wuff.happypuppy.adapters.RVPlayListAdapter;
import id.wuff.happypuppy.model.Feed;
import id.wuff.happypuppy.model.Pet;
import id.wuff.happypuppy.model.Play;
import id.wuff.happypuppy.utils.DatabaseHelper;
/**
 * DetailFragment is a fragment that will be displayed when
 * pet item that are shown in the home screen gets clicked
 */
public class DetailFragment extends Fragment {

    private RecyclerView rvFeedList, rvPlayList;
    private RVPlayListAdapter rvPlayListAdapter;
    private RVFeedListAdapter rvFeedListAdapter;
    private FloatingActionButton floatingActionButton;
    private ImageView ivPhoto, ivFeed;
    private TextView tvName, tvAge, tvGender, tvBirthdate, tvCategory;
    private CardView cvPlay;

    private Pet pet;

    private DatabaseHelper databaseHelper;

    private ArrayList<Play> plays;
    private ArrayList<Feed> feeds;

    public DetailFragment() {
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
        View v = inflater.inflate(R.layout.fragment_detail, container, false);

        databaseHelper = new DatabaseHelper(getContext());

        pet = DetailFragmentArgs.fromBundle(getArguments()).getPet();

        rvFeedList = v.findViewById(R.id.rv_feed_list);
        rvPlayList = v.findViewById(R.id.rv_play_list);
        ivPhoto = v.findViewById(R.id.iv_pet_photo);
        tvName = v.findViewById(R.id.tv_pet_name);
        tvAge = v.findViewById(R.id.tv_pet_age);
        tvBirthdate = v.findViewById(R.id.tv_pet_birthdate);
        tvGender = v.findViewById(R.id.tv_pet_gender);
        tvCategory = v.findViewById(R.id.tv_pet_category);
        cvPlay = v.findViewById(R.id.cv_pet_play);
        ivFeed = v.findViewById(R.id.iv_pet_feed);

        floatingActionButton = v.findViewById(R.id.detail_fab);

        populatePet();

        floatingActionButton.setOnClickListener(view -> {
            DetailFragmentDirections.ActionDetailFragmentToPetFragment action = DetailFragmentDirections.actionDetailFragmentToPetFragment();
            action.setPet(pet);
            Navigation.findNavController(view).navigate(action);
        });

        cvPlay.setOnClickListener(view -> {
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(System.currentTimeMillis());

            String splittedDate = calendar.getTime().toString().split(" ")[3];

            Play play = new Play();
            play.setDate(splittedDate);
            play.setPetId(pet.getId());

            databaseHelper.insertPlay(play);

            plays = databaseHelper.getPlays(pet.getId());
            RVPlayListAdapter rvPlayListAdapter = new RVPlayListAdapter(plays);
            rvPlayList.setAdapter(rvPlayListAdapter);
        });

        ivFeed.setOnClickListener(view -> {
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(System.currentTimeMillis());

            String splittedDate = calendar.getTime().toString().split(" ")[3];

            Feed feed = new Feed();
            feed.setDate(splittedDate);
            feed.setPetId(pet.getId());

            databaseHelper.insertFeed(feed);

            feeds = databaseHelper.getFeeds(pet.getId());
            RVFeedListAdapter rvFeedListAdapter = new RVFeedListAdapter(feeds);
            rvFeedList.setAdapter(rvFeedListAdapter);
        });

        return v;
    }

    /**
     * Method to populate pet information in which its pet object
     * gets passed from the home adapter to this fragment using safeargs
     */
    private void populatePet() {
        tvName.setText(pet.getName());
        tvCategory.setText(pet.getCategory());
        tvGender.setText(pet.getGender());
        tvBirthdate.setText("Birthdate: "+ pet.getBirthdate());
        ivPhoto.setImageURI(Uri.parse(pet.getPhoto()));
        tvAge.setText(pet.getAge()+"");

        plays = databaseHelper.getPlays(pet.getId());
        rvPlayListAdapter = new RVPlayListAdapter(plays);
        rvPlayList.setAdapter(rvPlayListAdapter);

        feeds = databaseHelper.getFeeds(pet.getId());
        rvFeedListAdapter = new RVFeedListAdapter(feeds);
        rvFeedList.setAdapter(rvFeedListAdapter);
    }

}