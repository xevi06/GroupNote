package com.example.propietario.groupnote;

import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.DeleteCallback;
import com.parse.ParseACL;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class Data_Fragment extends Fragment {

    private String group;
    private Button button;
    private EditText editText;
    private ListView listView;
    private ArrayAdapter adapter;
    private ArrayList<String> data = new ArrayList<String>();
    private List<ParseObject> onlineData;

    public Data_Fragment(String group)
    {
        this.group = group;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_data, container, false);
        button = (Button) view.findViewById(R.id.button);
        editText = (EditText) view.findViewById(R.id.EditText);
        listView = (ListView) view.findViewById(R.id.ListView);

        adapter = new ArrayAdapter<String>(getActivity().getApplicationContext(),R.layout.fragmentlistitem, data);
        listView.setAdapter(adapter);
        fetchOnlineData();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String s = editText.getText().toString();
                if(s.equals("")){

                    Toast.makeText(getActivity().getApplicationContext(),"Write your note", Toast.LENGTH_SHORT).show();

                }else{
                    final ParseObject privateNote = new ParseObject("Note");
                    privateNote.put("note",s);
                    privateNote.setACL(new ParseACL(ParseUser.getCurrentUser()));
                    privateNote.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            adapter.add(s);
                            editText.setText("");
                            onlineData.add(privateNote);
                        }
                    });
                }
            }
        });

        SwipeListViewTouchListener touchListener =new SwipeListViewTouchListener(listView,new SwipeListViewTouchListener.OnSwipeCallback() {
            @Override
            public void onSwipeLeft(ListView listView, int [] reverseSortedPositions) {
                //Aqui ponemos lo que hara el programa cuando deslizamos un item ha la izquierda
                final int p = reverseSortedPositions[0];
                Log.i("aaaaaaaaaaaaaa",p + "");
                ParseObject toDelete = onlineData.get(p);
                data.remove(p);
                adapter.notifyDataSetChanged();
                toDelete.deleteInBackground(new DeleteCallback() {
                    @Override
                    public void done(ParseException e) {
                        onlineData.remove(p);
                        data.remove(p);
                        adapter.notifyDataSetChanged();
                    }
                });
            }

            @Override
            public void onSwipeRight(ListView listView, int [] reverseSortedPositions) {
                //Aqui ponemos lo que hara el programa cuando deslizamos un item ha la derecha
                final int p = reverseSortedPositions[0];
                Log.i("aaaaaaaaaaaaaa",p + "");
                ParseObject toDelete = onlineData.get(p);
                data.remove(p);
                adapter.notifyDataSetChanged();
                toDelete.deleteInBackground(new DeleteCallback() {
                    @Override
                    public void done(ParseException e) {
                        onlineData.remove(p);
                    }
                });
            }
        },true, false);

        //Escuchadores del listView
        listView.setOnTouchListener(touchListener);
        listView.setOnScrollListener(touchListener.makeScrollListener());


        return view;
    }

    private void fetchOnlineData()  {
        onlineData = new ArrayList<>();
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Note");
        try {
            List<ParseObject> data = query.find();
            for(ParseObject o: data) {
                adapter.add(o.get("note"));
                onlineData.add(o);
            }
        } catch (ParseException e) {
            Toast.makeText(getActivity().getApplicationContext(), "Something was wrong", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }

    }
}
