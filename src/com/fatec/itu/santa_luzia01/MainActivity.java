package com.fatec.itu.santa_luzia01;

import java.util.Locale;

import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity implements OnTaskCompleted, LocationListener {

	private TextToSpeech tts;
	private Button bCidade;
	private Button bRua;
	private Button bBairro;

	private Localidade localidade;
	
    private LocationManager locationManager;
	private String provider;
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		tts = new TextToSpeech(getApplicationContext(), new OnInitListener() {
			@Override
			public void onInit(int status) {
				if(status != TextToSpeech.ERROR){
					tts.setLanguage(Locale.ENGLISH);
				}
			}
		});
		
		bBairro = (Button) findViewById(R.id.btnBairro);
		bCidade = (Button) findViewById(R.id.btnCidade);
		bRua = (Button) findViewById(R.id.btnRua);
		
		
		bBairro.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				speaktext(localidade.getBairro(), 0);
			}
		});
		
		bCidade.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				speaktext(localidade.getCidade(), 0);
			}
		});
		
		bRua.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				speaktext(localidade.getRua(), 0);
			}
		});
	
		//Get the location manager
		locationManager =  (LocationManager) getSystemService(LOCATION_SERVICE);
		
		
		Criteria criteria = new Criteria();
		criteria.setAccuracy(Criteria.ACCURACY_FINE);
		criteria.setCostAllowed(false);
		provider = locationManager.getBestProvider(criteria, true);

		Location location = locationManager.getLastKnownLocation(provider);

		// Initialize the location fields
	    if (location != null) {
	      System.out.println("Provider " + provider + " has been selected.");
	      onLocationChanged(location);
	    } else {
	    }

	
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	
	public void speaktext(String text,int type){
		Log.d("Text:",text);
		Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
		Log.d("Text:",text);
		tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
	}

	@Override
	public void onResume() {
		locationManager.requestLocationUpdates(provider,400, 1, this);
		super.onResume();
	}

	/* Remove the locationlistener updates when Activity is paused */
	  @Override
	  protected void onPause() {
	    locationManager.removeUpdates(this);
	      if(tts !=null){
        	  tts.stop();
         	  tts.shutdown();
              }
	     super.onPause();
	 }
	  
	
	  	@Override
	  public void onLocationChanged(Location location) {
	        /*
             * Reverse geocoding is long-running and synchronous.
             * Run it on a background thread.
             * Pass the current location to the background task.
             * When the task finishes,
             * onPostExecute() displays the address.
             */
        (new GetAddressTask(this,this)).execute(location);
     }
	     
	@Override
	public void onProviderDisabled(String provider) {
		Toast.makeText(this, "Disabled provider " + provider,
		        Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onProviderEnabled(String provider) {
		 Toast.makeText(this, "Enabled new provider " + provider,
			        Toast.LENGTH_SHORT).show();	
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTaskCompleted(String address) {
    	 this.localidade = new Localidade(address);
	}
	
}
