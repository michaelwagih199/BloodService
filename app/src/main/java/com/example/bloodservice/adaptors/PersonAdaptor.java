package com.example.bloodservice.adaptors;


import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.bloodservice.R;
import com.example.bloodservice.activities.EditActivity;
import com.example.bloodservice.constants.Constants;
import com.example.bloodservice.database.AppDatabase;
import com.example.bloodservice.models.Person;

import java.util.List;
import java.util.logging.Filter;

public class PersonAdaptor extends RecyclerView.Adapter<PersonAdaptor.MyViewHolder> {
    private Context context;
    private List<Person> mPersonList;

    public PersonAdaptor(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.person_item, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PersonAdaptor.MyViewHolder myViewHolder, int i) {
        myViewHolder.name.setText(mPersonList.get(i).getName());
        myViewHolder.bloodType.setText(mPersonList.get(i).getBloodType());
        myViewHolder.person_number.setText(mPersonList.get(i).getNumber());
        myViewHolder.tvLastDate.setText(mPersonList.get(i).getLastDate());
        myViewHolder.tvNotes.setText(mPersonList.get(i).getNotes());
    }

    @Override
    public int getItemCount() {
        if (mPersonList == null) {
            return 0;
        }
        return mPersonList.size();

    }

    public void setTasks(List<Person> personList) {
        mPersonList = personList;
        notifyDataSetChanged();
    }

    public List<Person> getTasks() {

        return mPersonList;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name, person_number, bloodType, tvNotes, tvLastDate;
        ImageView editImage,imgFaceBook;
        AppDatabase mDb;

        MyViewHolder(@NonNull final View itemView) {
            super(itemView);
            mDb = AppDatabase.getInstance(context);
            name = itemView.findViewById(R.id.tvPerson_name);
            person_number = itemView.findViewById(R.id.tvPerson_number);
            bloodType = itemView.findViewById(R.id.tvPerson_bloodType);
            tvNotes = itemView.findViewById(R.id.tvNotes);
            tvLastDate = itemView.findViewById(R.id.tvLastDate);
            editImage = itemView.findViewById(R.id.edit_Image);
            imgFaceBook =itemView.findViewById(R.id.imgFaceBook);

            editImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int elementId = mPersonList.get(getAdapterPosition()).getId();
                    Intent i = new Intent(context, EditActivity.class);
                    i.putExtra(Constants.UPDATE_Person_Id, elementId);
                    context.startActivity(i);
                }
            });

            imgFaceBook.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    try {
                        String facebookUrl = mPersonList.get(getAdapterPosition()).getFaceBookLink();

                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(facebookUrl));
                        context.startActivity(browserIntent);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                }
            });
        }
    }

}
