package com.example.recyclerviewexample;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class VillagerAdapter extends RecyclerView.Adapter<VillagerAdapter.ViewHolder> {
    // create the basic adapter extending from RecyclerView.Adapter
    // create an inner/helper class that specify the custom ViewHolder,
    // which gives us access to our views

    // you have a list of data to be populated
    // store an instance vairbale for the list of data to be populated

    private List<Villager> villagers;
    // index to keep track of which data has been changed
    // private int selectedVillager = -1;
    // notify a list of changes
    // create another list to keep track of the changes
    private List<Villager> selectedVillagers; // everytime, when i click on an image view
    // I want to add the villager that is being displayed to this list
    // at the end I want to notify the adapter with this list

    // pass this list into the construtor of the adapter
    public VillagerAdapter(List<Villager> villagers){
        this.villagers = villagers;
        this.selectedVillagers = new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // invloves inflate a layout from xml and return the ViewHolder
        // very standard code/template looking code
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        // inflate the custom layout
        View villagerView = inflater.inflate(R.layout.item_villager, parent, false);
        // return a new ViewHolder
        ViewHolder viewHolder = new ViewHolder(villagerView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // populate data into the item through holder

        // grab the actual data model (aka Villager Object) based on the position
        Villager villager = villagers.get(position);

        // set the view based on the data and the view names
        holder.textView_name.setText(villager.getName());
        holder.textView_birthday.setText(villager.getBirthday());
        holder.textView_phrase.setText(villager.getPhrase());

        // if I have to load in the image, this is where I have to load it in
        // add a onset click listener here
        // based on the changes, I will then load the villager image instead of the house image

        // based on the position, you will load different images
        /**
        if (position == selectedVillager){
            Picasso.get().load(villager.getVillagerUrl()).into(holder.imageView_villager);
        }
        else{
            Picasso.get().load(villager.getHouseUrl()).into(holder.imageView_villager);
        }*/
        // if the current villager is in the selected list
        // display villager url image
        // else
        // display their house image
        if (selectedVillagers.contains(villager)){
            Picasso.get().load(villager.getVillagerUrl()).into(holder.imageView_villager);
        }
        else{
            Picasso.get().load(villager.getHouseUrl()).into(holder.imageView_villager);
        }
        /**
        // lkoad in the image based on the boolean variable
        if (villager.isShowHouse()){
            Picasso.get().load(villager.getHouseUrl()).into(holder.imageView_villager);
        }

        else{
            Picasso.get().load(villager.getVillagerUrl()).into(holder.imageView_villager);
        }

        // in my listener, I only need to update the boolean variable
        holder.imageView_villager.setOnClickListener(v -> {
            if (villager.isShowHouse()){
                villager.setShowHouse(false);
            }
            else {
                villager.setShowHouse(true);
            }
            this.notifyItemChanged(position);
        });
    */
        // notify the changes so that the adapter knows something has been changed
        /**
        holder.imageView_villager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // chaneg the url to the villager image url
                Picasso.get().load(villager.getVillagerUrl()).into(holder.imageView_villager);
            }
        });
         */
    }

    @Override
    public int getItemCount() {
        // return the total number of items in the list
        return villagers.size();
    }

    // provide a driecxt reference to each of the views within the data item
    // used to cache the views within the item layout for fast access

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        // all the views you want to set as you render the row
        // name, birthday, phrase, villager

        TextView textView_name;
        TextView textView_birthday;
        TextView textView_phrase;
        ImageView imageView_villager;

        // we will add the image view later
        // create constructor to set these
        public ViewHolder (View itemView){
            // itemView -> represents the entire view of each row
            super(itemView);
            // look up each views from the custom layout
            textView_name = itemView.findViewById(R.id.textView_name);
            textView_birthday = itemView.findViewById(R.id.textView_birthday);
            textView_phrase = itemView.findViewById(R.id.textView_phrase);
            imageView_villager = itemView.findViewById(R.id.imageView_villager);
            imageView_villager.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            // record the position of this row
            // grab the villager at the position
            // add it to the selected arraylist
            int selected = getAdapterPosition();
            Villager selectedV = villagers.get(selected);
            // if it already contains, i want to remove it from the selected Vilagers list
            // so that we can reload the house image
            if (selectedVillagers.contains(selectedV)){
                selectedVillagers.remove(selectedV);
            }
            else {
                selectedVillagers.add(selectedV);
            }
            notifyDataSetChanged(); // you have a list of items instead of 1
        }
    }
}
