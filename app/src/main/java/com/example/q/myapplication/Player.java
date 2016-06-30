package com.example.q.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

public class Player extends YouTubeBaseActivity {

    YouTubePlayerView youTubePlayerView;
    String id;
    YouTubePlayer.OnInitializedListener onInitializedListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        youTubePlayerView = (YouTubePlayerView)findViewById(R.id.youtube_view);

        Intent intent = getIntent();
        id = intent.getStringExtra("keyword");
        if(id == null){
            Toast.makeText(this, "관련 동영상이 없습니다.",Toast.LENGTH_LONG).show();
            finish();
            onStop();
        }
        else {
            onInitializedListener = new YouTubePlayer.OnInitializedListener() {

                @Override
                public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                    youTubePlayer.loadVideo(id);
                }

                @Override
                public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

                }


            };
            youTubePlayerView.initialize(PlayerConfig.API_KEY,onInitializedListener);
        }



    }
}
