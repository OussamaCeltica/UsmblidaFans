package com.devs.celtica.usmbapplication;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by celtica on 12/07/18.
 */

public class InfoJourAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context c;
    public static ArrayList<Publication> publications;

    public InfoJourAdapter(ArrayList<Publication> publications, Context c) {
        this.c = c;
        this.publications = publications;
    }

    public static class InfoJourView extends RecyclerView.ViewHolder  {
        public TextView infoJour;
        public TextView date;
        public TextView label_pub;
        public InfoJourView(View v) {
            super(v);
            infoJour=(TextView)v.findViewById(R.id.infoJour);
            date=(TextView)v.findViewById(R.id.label_1);
            label_pub=(TextView)v.findViewById(R.id.label_pub);


        }
    }

    public static class ButtPlusView  extends RecyclerView.ViewHolder  {
        public Button plus;
        public ButtPlusView(View v) {
            super(v);
            plus=(Button) v.findViewById(R.id.plus);



        }
    }

    @Override
    public int getItemViewType(int i) {


        switch (publications.get(i).type.toLowerCase()) {

            case "infojour": return 2;

            default:return 0;
        }



    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       switch (viewType){
           case 2:{

                   View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.div_infojour,parent,false);

                   InfoJourView vh = new  InfoJourView(v);
                   return vh;

           }
           default:{
               View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.butt_plus,parent,false);

               //findViewById...

               ButtPlusView vh = new  ButtPlusView(v);
               return vh;
           }
       }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        switch (publications.get(position).type) {
            case "infojour": {
                //((InfoJourView)holder).setIsRecyclable(false);
                (( InfoJourView)holder).label_pub.setVisibility(View.GONE);
                (( InfoJourView)holder).infoJour.setText(publications.get(position).descreption);
                (( InfoJourView)holder).date.setText(((InfoDuJour)publications.get(position)).date_pub);

            }
            break;
            default: {
                ((ArticleAdapter.ButtPlusView)holder).plus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {


                        publications.remove(position);
                        AfficherInfoJour.afficherInfo(InfoJourAdapter.this);

                    }
                });

            }
        }

    }

    @Override
    public int getItemCount() {
        return publications.size();
    }
}
