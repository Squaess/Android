package com.example.aedd.l3z1;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.Toast;


public class MainActivity extends FragmentActivity implements ListFragment.OnFragmentSendText, DescriptionFragment.ratBarChange {

    static final int RETURN_RATING = 1;
    static final String STATE_rating = "rating";
    float[] mCurrentRating = {0,0,0,0,0,0,0,0,0,0};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            if (savedInstanceState == null) {
                getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, new ListFragment()).commit();
            } else {
                ListFragment fragment = (ListFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container);
                fragment.update(savedInstanceState.getFloatArray(STATE_rating));
            }
        } else {

            if(savedInstanceState == null) {
                getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, new ListFragment()).commit();
            } else {
                ListFragment fragment = (ListFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container);
                fragment.update(savedInstanceState.getFloatArray(STATE_rating));
            }

        }
    }

    @Override
    public void onSentText(String msg, float rate) {
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            DescriptionFragment secFrag = (DescriptionFragment) getSupportFragmentManager().findFragmentByTag("fragment2");
            secFrag.setText(msg, rate);
        } else {
            Intent intent = new Intent(this, DescriptionAcitivity.class);
            intent.putExtra("MSG",msg);
            intent.putExtra("RATE",rate);
            Toast.makeText(this, "startuje nowa intetn", Toast.LENGTH_SHORT).show();
            startActivityForResult(intent, RETURN_RATING);

        }
    }

    @Override
    public void setOnChangedRate(float rating, int index) {
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            ListFragment fragment = (ListFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container);
            fragment.changeRating(rating, index);
        } else {
            return;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
       if(requestCode == RETURN_RATING) {
           Toast.makeText(this, "Odbieram rating", Toast.LENGTH_SHORT).show();
           float tosetRate = data.getFloatExtra("RATE",0);
           int index = data.getIntExtra("INDEX",0);
           if(index == -12) {
               return;
           }
           ListFragment fragment = (ListFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container);
           fragment.changeRating(tosetRate, index);
       } else {
           Toast.makeText(this, "Inne zamkniece", Toast.LENGTH_SHORT).show();
       }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {

        ListFragment fragment = (ListFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        mCurrentRating = fragment.getViewRating();

        savedInstanceState.putFloatArray(STATE_rating, mCurrentRating);


        // Always call the superclass so it can save the view hierarchy state
        super.onSaveInstanceState(savedInstanceState);
    }
}
