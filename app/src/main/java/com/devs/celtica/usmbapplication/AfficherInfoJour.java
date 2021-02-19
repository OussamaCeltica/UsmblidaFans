package com.devs.celtica.usmbapplication;

import android.app.ProgressDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

import static com.devs.celtica.usmbapplication.Accueil.BD;

public class AfficherInfoJour extends AppCompatActivity {

    public  static int pubAfficher=0;
    public static int nbrPub;
    static ProgressDialog dialog;
    public static AfficherInfoJour me;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_afficher_info_jour);
        me=this;


        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.recycler_infj);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(AfficherInfoJour.this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        final InfoJourAdapter mAdapter = new InfoJourAdapter(new ArrayList<Publication>(),getApplicationContext());

        mRecyclerView.setAdapter(mAdapter);
        afficherInfo(mAdapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        pubAfficher=0;
    }

    public static void afficherInfo(final InfoJourAdapter mAdapter){

        //affichage info du jour ..

        Accueil.BD.read("select * from infojour order by jour desc limit 10 offset "+pubAfficher, new MySqlBdServer.doBeforAndAfterGettingData() {
            @Override
            public void before() {
                dialog = new ProgressDialog(me);
                dialog.setMessage("جاري التحميل ..");
                dialog.show();

            }

            @Override
            public void After() throws JSONException {

                dialog.dismiss();

                try {
                    JSONArray r = new JSONArray(Accueil.BD.queryResult);
                    int i = 0;

                    while (i < r.length()) {

                        mAdapter.publications.add(new InfoDuJour(r.getJSONObject(i).getString("info"), r.getJSONObject(i).getString("jour")));
                        i++;
                        mAdapter.notifyDataSetChanged();

                    }
                    //ajouter le button d affichage de plus de publications ..
                    Accueil.BD.read("select count(*) as nbrpub from infojour", new MySqlBdServer.doBeforAndAfterGettingData() {
                        @Override
                        public void before() {

                        }

                        @Override
                        public void After() throws JSONException {
                            JSONArray r=new JSONArray(BD.queryResult);
                            nbrPub=r.getJSONObject(0).getInt("nbrpub");

                            pubAfficher=pubAfficher+10;

                            if(nbrPub>pubAfficher){
                                mAdapter.publications.add(new Publication(-5,"plus","","",""));
                                mAdapter.notifyDataSetChanged();


                            }



                        }
                    });

                }catch (JSONException e){
                    e.printStackTrace();


                }
            }

        });

    }
}
