package com.devs.celtica.usmbapplication;

import android.app.ProgressDialog;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;

public class MatchDirect extends AppCompatActivity {
    ProgressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_match_direct);

        final LinearLayout aff=(LinearLayout)findViewById(R.id.aff_match_direct);

        Accueil.BD.read("select * from matchdirect md , matches ma where md.id_matche=ma.id_matche", new MySqlBdServer.doBeforAndAfterGettingData() {
            @Override
            public void before() {
                dialog= new ProgressDialog(MatchDirect.this);
                dialog.setMessage("جاري التحميل ..");
                dialog.show();

            }

            @Override
            public void After()  {
                try {
                    dialog.dismiss();
                    JSONArray r=new JSONArray(Accueil.BD.queryResult);
                    LayoutInflater li=getLayoutInflater();
                    View v=li.inflate(R.layout.div_match,null);
                    TextView club=(TextView)v.findViewById(R.id.clubsscore_match);
                    TextView time=(TextView)v.findViewById(R.id.voir_resultat);

                    TextView date=(TextView)v.findViewById(R.id.date_match);


                    //configuration ..
                    date.setVisibility(View.GONE);

                    time.setText(r.getJSONObject(0).getString("time"));

                    if(r.getJSONObject(0).getString("faze").equals("a")){
                        club.setText(r.getJSONObject(0).getString("local")+" "+r.getJSONObject(0).getString("score")+" "+r.getJSONObject(0).getString("visiteur"));
                    }else {
                        club.setText(r.getJSONObject(0).getString("visiteur")+" "+r.getJSONObject(0).getString("score")+" "+r.getJSONObject(0).getString("local"));

                    }


                    aff.addView(v);


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }
}
