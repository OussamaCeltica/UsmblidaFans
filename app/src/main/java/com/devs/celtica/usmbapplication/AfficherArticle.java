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

public class AfficherArticle extends AppCompatActivity {

    public  static int pubAfficher=0;
    public static int nbrPub;
    static ProgressDialog dialog;
    public static AfficherArticle me;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        me=this;
        setContentView(R.layout.activity_afficher_article);



        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.recycler_article);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(AfficherArticle.this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        final ArticleAdapter mAdapter = new ArticleAdapter(new ArrayList<Publication>(),getApplicationContext());

        mRecyclerView.setAdapter(mAdapter);



         AfficherArticles(mAdapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        pubAfficher=0;
    }

    public static void AfficherArticles(final ArticleAdapter mAdapter){

        Accueil.BD.read("SELECT * FROM publications where type='article' order by date_pub desc limit 10  offset "+pubAfficher, new MySqlBdServer.doBeforAndAfterGettingData() {
            @Override
            public void before() {
                dialog= new ProgressDialog(me);
                dialog.setMessage("جاري التحميل ..");
                dialog.show();

            }

            @Override
            public void After() {
                // mAdapter.publications.remove(1);
                // mAdapter.notifyDataSetChanged();
                dialog.dismiss();

                try {
                    final JSONArray r = new JSONArray(Accueil.BD.queryResult);
                    int i = 0;

                    while (i < r.length()) {

                        Accueil.BD.read("SELECT * FROM publications pub , article ar WHERE pub.id_pub=ar.id_pub and ar.id_pub='" + r.getJSONObject(i).getString("id_pub") + "'", new MySqlBdServer.doBeforAndAfterGettingData() {
                            @Override
                            public void before() {

                            }

                            @Override
                            public void After() {
                                try {

                                    JSONArray r2 = new JSONArray(Accueil.BD.queryResult);
                                    int i2 = 0;

                                    while (i2 < r2.length()) {
                                        mAdapter.publications.add(new Article(r2.getJSONObject(i2).getInt("id_pub"), "article", r2.getJSONObject(i2).getString("date_pub"), r2.getJSONObject(i2).getString("titre"), r2.getJSONObject(i2).getString("descreption"), r2.getJSONObject(i2).getInt("id_article"), r2.getJSONObject(i2).getString("url_img")));
                                        i2++;
                                        mAdapter.notifyDataSetChanged();

                                    }



                                    // Log.e("count:",publications.size()+" ");
                                } catch (JSONException e) {
                                    e.printStackTrace();

                                }


                            }
                        });
                        i++;
                    }

                    //ajouter le button d affichage de plus de publications ..
                    Accueil.BD.read("select count(id_pub) as nbrpub from publications where type='article'", new MySqlBdServer.doBeforAndAfterGettingData() {
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





                } catch (JSONException e) {
                    e.printStackTrace();

                }

            }
        });
}

}
