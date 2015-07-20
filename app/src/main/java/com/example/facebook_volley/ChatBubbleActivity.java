package com.example.facebook_volley;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.database.DataSetObserver;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnKeyListener;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.facebook_volley.messenger.AwesomeAdapter;
import com.example.facebook_volley.messenger.Message;
import com.example.facebook_volley.messenger.Utility;

import java.util.ArrayList;
import java.util.Random;

public class ChatBubbleActivity extends ListActivity {
    ArrayList<Message> messages;
    AwesomeAdapter adapter;
    EditText text;
    static Random rand = new Random();
    static String sender;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        text = (EditText) this.findViewById(R.id.text);
        TextView tvName =(TextView) findViewById(R.id.name);

        Intent callIntent = getIntent();
        Bundle callBundle = callIntent.getBundleExtra("package");
        tvName.setText(callBundle.getString("name"));
//        String name = intent.getStringExtra("name");
//        Log.e("Name: ", name + "");
//        TextView tvName =(TextView) findViewById(R.id.name);
//        if(name==null){
//            sender = Utility.sender[rand.nextInt( Utility.sender.length-1)];
//            tvName.setText(sender);
//        }
//        else
//            tvName.setText(name);

        messages = new ArrayList<Message>();

        messages.add(new Message("Hello", false));
        adapter = new AwesomeAdapter(this, messages);
        setListAdapter(adapter);
//		addNewMessage(new Message("mmm, well, using 9 patches png to show them.", true));
        ImageView back=(ImageView) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });
    }
    public void sendMessage(View v)
    {
        String newMessage = text.getText().toString().trim();
        if(newMessage.length() > 0)
        {
            text.setText("");
            addNewMessage(new Message(newMessage, true));
            new SendMessage().execute();
        }
    }
    private class SendMessage extends AsyncTask<Void, String, String>
    {
        @Override
        protected String doInBackground(Void... params) {
            try {
                Thread.sleep(1000); //simulate a network call
            }catch (InterruptedException e) {
                e.printStackTrace();
            }

//			this.publishProgress(String.format("da"));
            try {
                Thread.sleep(1000); //simulate a network call
            }catch (InterruptedException e) {
                e.printStackTrace();
            }
//			this.publishProgress(String.format("%s has entered text", sender));
            try {
                Thread.sleep(1000);//simulate a network call
            }catch (InterruptedException e) {
                e.printStackTrace();
            }


            return Utility.messages[rand.nextInt(Utility.messages.length-1)];


        }
        @Override
        public void onProgressUpdate(String... v) {

            if(messages.get(messages.size()-1).isStatusMessage)//check wether we have already added a status message
            {
                messages.get(messages.size()-1).setMessage(v[0]); //update the status for that
                adapter.notifyDataSetChanged();
                getListView().setSelection(messages.size()-1);
            }
            else{
                addNewMessage(new Message(true,v[0])); //add new message, if there is no existing status message
            }
        }
        @Override
        protected void onPostExecute(String text) {
            if(messages.get(messages.size()-1).isStatusMessage)//check if there is any status message, now remove it.
            {
                messages.remove(messages.size()-1);
            }

            addNewMessage(new Message(text, false)); // add the orignal message from server.
        }


    }
    void addNewMessage(Message m)
    {
        messages.add(m);
        adapter.notifyDataSetChanged();
        getListView().setSelection(messages.size()-1);
    }
}