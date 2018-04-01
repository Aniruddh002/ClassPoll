package com.example.aniruddh.classpoll;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by hp on 01-04-2018.
 */

    public class QuestionList extends ArrayAdapter<PollInformation> {

    private Activity context;
    private List<PollInformation> questionList;

    public QuestionList(Activity context, List<PollInformation> questionList){
        super(context, R.layout.list_layout,questionList);
        this.context=context;
        this.questionList=questionList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater= context.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.list_layout,null,true);

        TextView textViewQuestion= (TextView)listViewItem.findViewById(R.id.textViewQuestion);
        TextView textViewDescription= (TextView)listViewItem.findViewById(R.id.textViewDescription);
        TextView textViewOption1= (TextView)listViewItem.findViewById(R.id.textViewOption1);
        TextView textViewOption2= (TextView)listViewItem.findViewById(R.id.textViewOption2);



        PollInformation pollInformation = questionList.get(position);
        textViewQuestion.setText(pollInformation.getQuestion());
        textViewDescription.setText(pollInformation.getDescription());
        textViewOption1.setText(pollInformation.getOption1());
        textViewOption2.setText(pollInformation.getOption2());


        return listViewItem;

    }
}
