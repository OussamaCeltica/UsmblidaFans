package com.devs.celtica.usmbapplication;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Build;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;

public class MatchResult extends AppCompatActivity {

    LinearLayout aff;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_match_result);

        aff=(LinearLayout)findViewById(R.id.affich_mj);

        Bundle b=getIntent().getExtras();

        Accueil.BD.read("SELECT * from matches m, matchjouer mj where m.id_matche=mj.id_matche and m.id_matche='"+b.getString("id_matche")+"' order by mj.date_jouer asc", new MySqlBdServer.doBeforAndAfterGettingData() {
            @Override
            public void before() {
                dialog= new ProgressDialog(MatchResult.this);
                dialog.setMessage("جاري التحميل ..");
                dialog.show();

            }

            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
            @Override
            public void After() {
                try {
                    dialog.dismiss();
                    JSONArray r=new JSONArray(Accueil.BD.queryResult);
                    int i=0;

                        while (i < r.length()){
                            LayoutInflater li=getLayoutInflater();
                            View v=li.inflate(R.layout.div_match,null);
                            TextView club=(TextView)v.findViewById(R.id.clubsscore_match);
                            TextView label_but=(TextView)v.findViewById(R.id.label_but);
                            TextView but=(TextView)v.findViewById(R.id.butur_match);
                            TextView res=(TextView)v.findViewById(R.id.voir_resultat);
                            TextView date=(TextView)v.findViewById(R.id.date_match);


                            res.setVisibility(View.GONE);

                            label_but.setVisibility(View.VISIBLE);
                            but.setVisibility(View.VISIBLE);

                            date.setText(r.getJSONObject(i).getString("date_jouer"));
                            but.setText(r.getJSONObject(i).getString("but_usmb"));

                            if(i==0){
                                club.setText(r.getJSONObject(i).getString("local")+" "+r.getJSONObject(i).getString("score")+" "+ r.getJSONObject(i).getString("visiteur"));
                            }else {
                                club.setText(r.getJSONObject(i).getString("visiteur")+" "+r.getJSONObject(i).getString("score")+" "+ r.getJSONObject(i).getString("local"));


                            }

                            aff.addView(v);

                            i++;
                        }




                } catch (JSONException e) {

                    TextView noRes=new TextView(getApplicationContext());
                    noRes.setText("لم تـلعب بعد");
                    noRes.setTextSize(20);
                    noRes.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

                    noRes.setTextColor(Color.parseColor("#ffffff"));
                    aff.addView(noRes);
                    e.printStackTrace();
                }

            }
        });




    }
}
