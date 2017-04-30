package com.example.bartosz.l4;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by aedd on 4/29/17
 */

public class MyListViewAdapter extends ArrayAdapter<String> {

    private static class MyViewHolder{
        TextView title;
        Button b;
    }


    static final int REQ_CODE = 123;
    private final Context context;
    private final ArrayList<String> values;

    public MyListViewAdapter(Context context, ArrayList<String> values){
        super(context,-1,values);
        this.context = context;
        this.values = values;
    }


    @NonNull
    @Override
    public View getView(final int position, View convertView, @NonNull ViewGroup parent) {

        final MyViewHolder viewHolder;

        if(convertView == null) {
            final LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            convertView = inflater.inflate(R.layout.row,parent,false);
            viewHolder = new MyViewHolder();
            viewHolder.title = (TextView) convertView.findViewById(R.id.row_title);
            viewHolder.title.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "Editable", Toast.LENGTH_SHORT).show();
                    String title = viewHolder.title.getText().toString();
                    Intent intent = new Intent(context, EditActivity.class);
                    intent.putExtra("TITLE", title);
                    ((Activity) context).startActivityForResult(intent, REQ_CODE);
                }
            });
            viewHolder.b = (Button) convertView.findViewById(R.id.row_button);
            viewHolder.b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String deleteFile = viewHolder.title.getText().toString();

                    Toast.makeText(context,deleteFile, Toast.LENGTH_SHORT ).show();
                    //usunac plik o nazwie takiej jak tytul i usunac tytul z pliku main.filename
                    context.deleteFile(deleteFile);

                    try {

                        PrintStream out = new PrintStream(context.openFileOutput("temp", Context.MODE_PRIVATE));
                        Scanner input = new Scanner(getContext().openFileInput(MainActivity.FILENAME));

                        String currentLine;

                        while(input.hasNextLine()) {
                            currentLine = input.nextLine();
                            if(currentLine.equals(deleteFile)) continue;
                            out.println(currentLine);
                        }
                        out.close();
                        input.close();
                        context.deleteFile(MainActivity.FILENAME);

                        PrintStream output = new PrintStream(context.openFileOutput(MainActivity.FILENAME, Context.MODE_PRIVATE));
                        Scanner in = new Scanner(context.openFileInput("temp"));

                        while(in.hasNextLine()) {
                            output.println(in.nextLine());
                        }

                        output.close();
                        in.close();
                        context.deleteFile("temp");

                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    remove(deleteFile);
                    notifyDataSetChanged();

                }
            });
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (MyViewHolder) convertView.getTag();
        }
        viewHolder.title.setText(values.get(position));
        return convertView;

    }
}

