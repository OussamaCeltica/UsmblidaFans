package com.devs.celtica.usmbapplication;

import android.app.ProgressDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class Afficher_Calendrier extends AppCompatActivity {
    ProgressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_afficher__calendrier);


        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.aff_calendar);
        mRecyclerView.setHasFixedSize(true);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

        final CalendarAdapter mAdapter = new CalendarAdapter(new ArrayList<Calendrier>(),this);

        mRecyclerView.setAdapter(mAdapter);



        Accueil.BD.read("SELECT * from calendrier c , matches m where c.saison=m.saison order by c.saison desc", new MySqlBdServer.doBeforAndAfterGettingData() {
            @Override
            public void before() {
                 dialog= new ProgressDialog(Afficher_Calendrier.this);
                dialog.setMessage("جاري التحميل ..");
                dialog.show();

            }

            @Override
            public void After()   {
                try {
                    dialog.dismiss();
                    JSONArray r=new JSONArray(Accueil.BD.queryResult);
                    int i=0;
                    int i2;
                    Calendrier c;
                    while(i<r.length()){
                        c=new Calendrier(r.getJSONObject(i).getString("saison"));
                        i2=i;
                        while( i2<r.length() && r.getJSONObject(i).getString("saison").equals(r.getJSONObject(i2).getString("saison"))){
                           c.Matches.add(new Match(r.getJSONObject(i2).getInt("id_matche"),r.getJSONObject(i2).getString("local"),r.getJSONObject(i2).getString("visiteur"),r.getJSONObject(i2).getString("saison")));
                           i2++;


                        }
                        mAdapter.calendars.add(c);
                        mAdapter.notifyItemInserted(mAdapter.getItemCount());
                        i=i2;


                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });

        /*

        Accueil.BD.read("select * from calendrier order by  saison DESC", new MySqlBdServer.doBeforAndAfterGettingData() {
            @Override
            public void before() {

            }

            @Override
            public void After() {
                try {
                    JSONArray r=new JSONArray(Accueil.BD.queryResult);
                    int i=0;

                    while(i<r.length()){

                        mAdapter.calendars.add(new Calendrier(r.getJSONObject(i).getString("saison")));
                        mAdapter.notifyItemInserted(mAdapter.getItemCount());
                        //recuperation des matches ..
                        Log.e("calendeer count:",mAdapter.calendars.size()+"");
                        final int finalI = i;
                        Accueil.BD.read("select * from matches where saison='"+r.getJSONObject(i).getString("saison")+"'", new MySqlBdServer.doBeforAndAfterGettingData() {
                            @Override
                            public void before() {

                            }

                            @Override
                            public void After() throws JSONException {

                                JSONArray r2=new JSONArray(Accueil.BD.queryResult);

                                int i2=0;

                                while(i2<r2.length()){
                                    mAdapter.calendars.get(finalI).Matches.add(new Match(r2.getJSONObject(i2).getInt("id_matche"),r2.getJSONObject(i2).getString("local"),r2.getJSONObject(i2).getString("visiteur"),r2.getJSONObject(i2).getString("buts_loc"),r2.getJSONObject(i2).getString("buts_visit"),r2.getJSONObject(i2).getString("saison")));
                                    mAdapter.notifyItemInserted(mAdapter.getItemCount());
                                    Log.e("match","kayen");


                                    i2++;

                                }



                            }
                        });
                       i++;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();

                }

            }
        });
        */
    }
}
