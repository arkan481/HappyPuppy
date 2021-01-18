package id.wuff.happypuppy.adapters;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import id.wuff.happypuppy.R;
import id.wuff.happypuppy.model.Pet;
import id.wuff.happypuppy.ui.fragments.HomeFragment;
import id.wuff.happypuppy.ui.fragments.HomeFragmentDirections;
import id.wuff.happypuppy.utils.DatabaseHelper;

public class RVHomeAdapter extends RecyclerView.Adapter<RVHomeAdapter.ViewHolder> implements Filterable {

    private ArrayList<Pet> pets;
    private ArrayList<Pet> filteredPets;
    private CustomFilter customFilter;

    public RVHomeAdapter(ArrayList<Pet> pets) {
        this.pets = pets;
        filteredPets = pets;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pet, parent, false);
        return new RVHomeAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.itemView.setOnClickListener(view -> {
            HomeFragmentDirections.ActionHomeFragmentToDetailFragment action = HomeFragmentDirections.actionHomeFragmentToDetailFragment();
            action.setPet(pets.get(position));
            Navigation.findNavController(view).navigate(action);
        });

        holder.itemView.setOnLongClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
            builder.setIcon(R.drawable.ic_baseline_warning_24);
            builder.setTitle("Remove Pet");
            builder.setMessage("Are you sure want to remove this pet?");
            builder.setPositiveButton("Confirm", (dialogInterface, i) -> {
                new DatabaseHelper(view.getContext()).deletePet(pets.get(position));
                pets.remove(position);
                notifyDataSetChanged();
            });
            builder.setNegativeButton("Cancel", (dialogInterface, i) -> {
                // Negative
            });
            builder.create().show();
            return true;
        });

        holder.tvName.setText(pets.get(position).getName());
        holder.tvAge.setText(pets.get(position).getAge() + " Months of age");
        holder.tvGender.setText(pets.get(position).getGender());
        holder.ivPhoto.setImageURI(Uri.parse(pets.get(position).getPhoto()));
    }

    @Override
    public int getItemCount() {
        return pets.size();
    }

    @Override
    public Filter getFilter() {
        if (customFilter==null) {
            customFilter=new CustomFilter();
        }
        return customFilter;
    }

    class CustomFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults filterResults = new FilterResults();
            if (constraint!=null&&constraint.length()>0) {
                constraint=constraint.toString().toUpperCase();
                ArrayList<Pet> pets = new ArrayList<>();
                for (int i=0;i<filteredPets.size();i++) {
                    if (filteredPets.get(i).getName().toUpperCase().contains(constraint)) {
                        Pet pet = filteredPets.get(i);
                        pets.add(pet);
                    }
                }
                filterResults.count=pets.size();
                filterResults.values=pets;
            }else {
                filterResults.count = filteredPets.size();
                filterResults.values = filteredPets;
            }

            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            pets=(ArrayList<Pet>) results.values;
            notifyDataSetChanged();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvName, tvAge, tvGender;
        private ImageView ivPhoto;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.tv_pet_name);
            tvAge = itemView.findViewById(R.id.tv_age);
            tvGender = itemView.findViewById(R.id.tv_gender);
            ivPhoto = itemView.findViewById(R.id.iv_pet_photo);
        }
    }

}
