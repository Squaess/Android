package com.example.bartosz.l2z1;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;



public class MainActivity extends AppCompatActivity {

    /**
     * Size of square board
     */
    public static final int N = 4;

    /**
     *  main layout
     */
    private RelativeLayout root;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        root = new RelativeLayout(this);
        setContentView(root);

        GridLayout.LayoutParams gridParams = new GridLayout.LayoutParams(root.getLayoutParams());
        gridParams.rowSpec = GridLayout.spec(N,N);
        gridParams.columnSpec = GridLayout.spec(N,N);

        GridLayout board = new GridLayout(this);
        board.setLayoutParams(gridParams);
        board.setColumnCount(N);
        board.setRowCount(N);

        root.addView(board);

        Toast.makeText(this, "OnCreate", Toast.LENGTH_LONG).show();

        for(int i=0; i<N*N; i++) {
            Button b = new Button(this);
            b.setText(R.string.b1);
            board.addView(b);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Toast.makeText(this, "OnStart", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Toast.makeText(this, "OnResume", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Toast.makeText(this, "OnRestart", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Toast.makeText(this, "OnPause", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Toast.makeText(this, "OnStart", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onStop() {
        super.onStop();
        Toast.makeText(this, "OnStart", Toast.LENGTH_LONG).show();
    }
}
