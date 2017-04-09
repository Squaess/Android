package com.example.aedd.l3z1;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;


public class DescriptionFragment extends Fragment {
    TextView tv;
    ImageView iv;
    int[] ImgRes;
    String[] descr;
    RatingBar rb;
    public ratBarChange mCallback;
    int lastIndx;

    interface ratBarChange {
        void setOnChangedRate (float rating, int index);
    }
    public void setText(String msg, float rate) {
        int index = Integer.parseInt(msg);
        lastIndx = index;
        tv.setText(descr[index]);
        iv.setImageResource(ImgRes[index]);
        rb.setRating(rate);
    }
    public DescriptionFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        try {
            mCallback = (ratBarChange) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException(getActivity().toString()
                    + " must implement TextClicked");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_description, container, false);

        tv = (TextView) view.findViewById(R.id.tv_desc);

        iv = (ImageView) view.findViewById(R.id.descriptionImage);
        rb = (RatingBar) view.findViewById(R.id.rateing_bar);

        descr = getResources().getStringArray(R.array.descri);
        final TypedArray typedArray = getResources().obtainTypedArray(R.array.ResIds);

        ImgRes = new int[10];
        for (int i = 0; i < ImgRes.length; i++) {
            ImgRes[i] = typedArray.getResourceId(i, 0);
        }
        typedArray.recycle();

        rb.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                Toast.makeText(getActivity(),"zmiania rate "+rating, Toast.LENGTH_SHORT).show();
                mCallback.setOnChangedRate(rating,lastIndx);
            }
        });
        return view;
    }
}
