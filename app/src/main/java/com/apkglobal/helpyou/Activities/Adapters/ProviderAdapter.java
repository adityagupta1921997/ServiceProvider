package com.apkglobal.helpyou.Activities.Adapters;

/**
 * Created by Mayank on 1/26/2018.
 */

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.speech.tts.TextToSpeech;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.apkglobal.helpyou.Activities.Helper.ProviderModel;
import com.apkglobal.helpyou.R;

import java.util.Collections;
import java.util.List;
import java.util.Locale;

import static com.facebook.FacebookSdk.getApplicationContext;

public class ProviderAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private LayoutInflater inflater;
    List<ProviderModel> data= Collections.emptyList();
    ProviderModel current;
    int currentPos=0;

    // create constructor to innitilize context and data sent from MainActivity
    public ProviderAdapter(Context context, List<ProviderModel> data){
        this.context=context;
        inflater= LayoutInflater.from(context);
        this.data=data;
    }

    // Inflate the layout when viewholder created
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=inflater.inflate(R.layout.provider_details, parent,false);
        MyHolder holder=new MyHolder(view);
        return holder;
    }

    // Bind data
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        // Get current position of item in recyclerview to bind data and assign values from list
        MyHolder myHolder= (MyHolder) holder;
        ProviderModel current=data.get(position);
        myHolder.textprovidername.setText(current.provider_name);
        myHolder.textprovidercontact.setText("Contact: " + current.provider_contact);
        myHolder.textproviderexperience.setText(current.provider_experience);
        myHolder.textprovideraddress.setText("Address: " + current.provider_address);

    }

    // return total item from List
    @Override
    public int getItemCount() {
        return data.size();
    }


    class MyHolder extends RecyclerView.ViewHolder{

        TextView textprovidername;
        TextView textprovidercontact;
        TextView textproviderexperience;
        TextView textprovideraddress;
        private TextToSpeech tts;

        // create constructor to get widget reference
        public MyHolder(View itemView) {
            super(itemView);
            textprovidername= (TextView) itemView.findViewById(R.id.show_name);
            textprovidercontact = (TextView) itemView.findViewById(R.id.show_contact);
            textproviderexperience = (TextView) itemView.findViewById(R.id.show_experience);
            textprovideraddress=itemView.findViewById(R.id.show_address);
            tts=new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
                @Override
                public void onInit(int status) {
                    if(status != TextToSpeech.ERROR) {
                        tts.setLanguage(Locale.ENGLISH);
                    }
                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String toSpeak=textprovidername.getText().toString();
                    String call=textprovidercontact.getText().toString();
                    tts.speak("Hello this is "+ toSpeak+" Call me up to help you.", TextToSpeech.QUEUE_FLUSH, null);
                    Intent dial = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:(+91)"+call));
                    context.startActivity(dial);
                }
            });
        }
    }

}