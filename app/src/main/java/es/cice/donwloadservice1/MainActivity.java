package es.cice.donwloadservice1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import es.cice.donwloadservice1.services.DownloadIntentService;

public class MainActivity extends AppCompatActivity {

    private EditText downloadET;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        downloadET= (EditText) findViewById(R.id.urlET);
    }

    public void startDownload(View v){
        String url=downloadET.getText().toString();
        Intent intent=DownloadIntentService.makeIntent(this,url);
        startService(intent);
    }
}
