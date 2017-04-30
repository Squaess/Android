package layout;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.bartosz.l4.MainActivity;
import com.example.bartosz.l4.MyListViewAdapter;
import com.example.bartosz.l4.R;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class MyFragment extends Fragment {

    private ArrayList<String> arrayList = new ArrayList<>();

    public MyFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list, container, false);
    }

    public void onActivityCreated(Bundle savedState){

        super.onActivityCreated(savedState);
        readTitleToList();

    }

    private void readTitleToList() {
        arrayList.clear();

        try {
            Scanner input = new Scanner(getActivity().getApplicationContext().openFileInput(MainActivity.FILENAME));
            while(input.hasNextLine()) {
                arrayList.add(input.nextLine());
            }
            input.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        MyListViewAdapter adapter = new MyListViewAdapter(getActivity(), arrayList);
        ListView listView = (ListView) getActivity().findViewById(R.id.list_view);
        listView.setAdapter(adapter);
    }
}
