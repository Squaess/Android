package com.example.aedd.l3z1;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.Toast;


public class DescriptionAcitivity extends FragmentActivity implements DescriptionFragment.ratBarChange {
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description_acitivity);

        Intent intent = getIntent();
        Bundle b = intent.getExtras();
        String msg = (String) b.get("MSG");
        float rate = (float) b.get("RATE");
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            DescriptionFragment frag = (DescriptionFragment) getSupportFragmentManager().findFragmentById(R.id.description_fragment);
            frag.setText(msg, rate);
        } else {
            Toast.makeText(this, "Zmiana  na landscape", Toast.LENGTH_SHORT).show();
            setResult(3,new Intent());
            finish();
        }
    }

    @Override
    public void setOnChangedRate(float rating, int index) {
        intent = new Intent();
        intent.putExtra("RATE",rating);
        intent.putExtra("INDEX",index);
    }

    public void onBackPressed() {
        if(intent != null) {
            setResult(MainActivity.RETURN_RATING, intent);
        } else {
            intent = new Intent();
            intent.putExtra("RATE", 0);
            intent.putExtra("INDEX", -12);
            setResult(MainActivity.RETURN_RATING, intent);
        }
        finish();
    }
}
