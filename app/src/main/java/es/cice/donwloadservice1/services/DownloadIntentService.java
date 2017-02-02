package es.cice.donwloadservice1.services;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.os.IBinder;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import es.cice.donwloadservice1.R;

import static android.provider.ContactsContract.CommonDataKinds.Website.URL;

public class DownloadIntentService extends IntentService {

    public static final String URL_EXTRA="url";

    public DownloadIntentService() {
        super("DownloadIntentService");
    }

    public static Intent makeIntent(URL url){
       return null;
    }

    public static Intent makeIntent(String url){
        return null;
    }

    public static Intent makeIntent(Context ctx, URL url){
        Intent intent=new Intent(ctx,DownloadIntentService.class);
        intent.putExtra(URL_EXTRA,url);
        return intent;
    }

    public static Intent makeIntent(Context ctx,String url){
        Intent intent=new Intent(ctx,DownloadIntentService.class);
        intent.putExtra(URL_EXTRA,url);
        return intent;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String urlStr=intent.getStringExtra(URL_EXTRA);
        OutputStream out=null;
        try{
            URL url=new URL(urlStr);
            URLConnection con=url.openConnection();
            InputStream in=con.getInputStream();
            out=openFileOutput("download",MODE_PRIVATE);

            byte[] buffer=new byte[1024];
            int bytesLeidos;
            do{
                bytesLeidos=in.read(buffer);
                out.write(buffer,0,bytesLeidos);
            }while(bytesLeidos==buffer.length);
        }catch(MalformedURLException e){
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            Notification.Builder builder=new Notification.Builder(this);
            File file=Environment.getDataDirectory();
            builder
                    .setSmallIcon(R.drawable.ic_download)
                    .setContentTitle("download finished")
                    .setContentText("descarga terminada en " +
                    file.getAbsolutePath());


            Notification notification=builder.build();
            NotificationManager nm= (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            nm.notify(11111,notification);
            try {
                if(out!=null)out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
