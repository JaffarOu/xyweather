package com.jf.xyweather.lifesuggestion;

import android.os.Bundle;
import android.app.Activity;
import android.widget.ListView;

import com.jf.xyweather.R;
import com.jf.xyweather.model.LifeSuggestion;

public class LifeSuggestionActivity extends Activity {

    public static final String KEY_LIFE_SUGGESTION = "keyLifeSuggestion";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_life_suggestion);
        //Get the data of life suggestion and set it to adapter of ListView
        LifeSuggestion lifeSuggestion = (LifeSuggestion)getIntent().getSerializableExtra(KEY_LIFE_SUGGESTION);
        if(lifeSuggestion == null) return;
        ((ListView)findViewById(R.id.lv_life_suggestion)).setAdapter(new LifeSuggestionListAdapter(this, lifeSuggestion));
    }

}
