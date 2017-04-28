package com.example.aedd.l3z1;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.Toast;

public class ListFragment extends Fragment {
    public OnFragmentSendText mCallback;
    ListView listView;
    RowAdapter rowAdapter;

    RowBean[] mArray = new RowBean[] {
                new RowBean(R.drawable.androidicon21, "Halo", (float)0.00),
                new RowBean(R.drawable.businesstux,"Bussines",(float)0.00),
                new RowBean(R.drawable.greenandroidicon31, "goGreeen",(float)0.0),
                new RowBean(R.drawable.small,"Small",(float)0.0),
                new RowBean(R.drawable.tonyk,"Warrior",(float)0.00),
                new RowBean(R.drawable.microfinance,"Microfinance",(float)0.0),
                new RowBean(R.drawable.octocat,"Catty",(float)0.00),
                new RowBean(R.drawable.tylerhayestwitter,"Twitter",(float)0.0),
                new RowBean(R.drawable.hand,"Hand",(float)0.0),
                new RowBean(R.drawable.engineering,"Engineering",(float)0.0)
    };
    interface OnFragmentSendText {
        void onSentText(String msg, float rate);
    }

    public ListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        try {
            mCallback = (OnFragmentSendText) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException(getActivity().toString()
                    + " must implement TextClicked");
        }

    }


    @Override
    public void onDetach() {
        mCallback = null;
        super.onDetach();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        return view;
    }

    public void onActivityCreated(Bundle savedState) {
        super.onActivityCreated(savedState);

//        String[] mArray = getActivity().getResources().getStringArray(R.array.names);



         listView = (ListView) getActivity().findViewById(R.id.list_view);
//        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, mArray);

        rowAdapter = new RowAdapter(getActivity(), R.layout.custom_row, mArray);

        listView.setAdapter(rowAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
             //   Toast.makeText(getActivity(), "Click ListItem Number " + position, Toast.LENGTH_LONG).show();
                RatingBar rb = (RatingBar) view.findViewById(R.id.rateBar);

                mCallback.onSentText(Integer.toString(position),rb.getRating());
            }
        });
    }

    void update(float[] rating) {
        int i = 0;
        for(float f : rating) {
            mArray[i].rate = f;
            i++;
        }
    }
    public void changeRating(float rating, int index) {
//        Toast.makeText(getActivity(),"Bede zmianial u siebie "+ rating, Toast.LENGTH_SHORT).show();
        RowBean r = mArray[index];
        r.rate = rating;
        mArray[index] = r;
        refreshRating();
    }

    void refreshRating(){
        rowAdapter.notifyDataSetChanged();
    }

    float[] getViewRating(){
        float[] ret = new float[10];
        int i = 0;
        for(RowBean r : mArray) {
            ret[i] = r.rate;
            i++;
        }
        return ret;
    }
}
