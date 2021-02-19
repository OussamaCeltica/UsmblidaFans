package com.devs.celtica.usmbapplication;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by celtica on 04/07/18.
 */

public class CalendarAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public ArrayList<Calendrier> calendars;
    public AppCompatActivity c;

    public CalendarAdapter(ArrayList<Calendrier> cal,AppCompatActivity c) {
       calendars = cal;
        this.c=c;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.div_calendar,parent,false);

        //findViewById...

        CalendarAdapter.CalendarView vh = new CalendarAdapter.CalendarView(v);
        return vh;

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        ((CalendarView)holder).saison.setText(calendars.get(position).saison);

        int i=0;
        while(i<calendars.get(position).Matches.size()){
            LayoutInflater li=c.getLayoutInflater();
            View v=li.inflate(R.layout.div_match,null);
            TextView club=(TextView)v.findViewById(R.id.clubsscore_match);
            TextView but=(TextView)v.findViewById(R.id.butur_match);
            TextView res=(TextView)v.findViewById(R.id.voir_resultat);
            TextView date=(TextView)v.findViewById(R.id.date_match);
            TextView id_match=(TextView)v.findViewById(R.id.label_id_match);


            date.setVisibility(View.GONE);

            id_match.setText(calendars.get(position).Matches.get(i).id_match+"");
            club.setText(calendars.get(position).Matches.get(i).local+"-"+calendars.get(position).Matches.get(i).visiteur);

            ((CalendarView)holder).div_aff.addView(v);
           //notifyItemChanged(position);

            res.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    LinearLayout parent=(LinearLayout)view.getParent();

                   // Log.e("id=",""+((TextView)parent.getChildAt(5)).getText());
                    Intent i2=new Intent(c,MatchResult.class);
                    i2.putExtra("id_matche",""+((TextView)parent.getChildAt(5)).getText());
                    c.startActivity(i2);

                }
            });

           i++;


        }

    }

    @Override
    public int getItemCount() {
        return calendars.size();
    }

    public static class CalendarView extends RecyclerView.ViewHolder  {
        public TextView saison;
        public  LinearLayout div_aff;
        public CalendarView(View v) {
            super(v);
            saison=(TextView)v.findViewById(R.id.saison);
            div_aff=(LinearLayout)v.findViewById(R.id.affich_match);


        }
    }
}
