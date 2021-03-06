package com.fatec.itu.santa_luzia01;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.util.Log;

/**
 * @author Bruno
 *   
 */
public class GetAddressTask extends AsyncTask<Location, Void, String>{

	private  Context context;
	private OnTaskCompleted listener;
	

	public GetAddressTask(Context context, OnTaskCompleted listener) {
	 super();
	 this.context = context; 
	 this.listener = listener;
	}	
		
	/**
     * Get a Geocoder instance, get the latitude and longitude
     * look up the address, and return it
     *
     * @params params One or more Location objects
     * @return A string containing the address of the current
     * location, 	 or an empty string if no address can be found,
     * or an error message
     */
	@Override
	protected String doInBackground(Location... params) {
		
		Geocoder geocoder = new Geocoder(context, Locale.getDefault());
			
		// Get the current location from the input parameter list
            Location loc = params[0];
            
            // Create a list to contain the result address
            List<Address> addresses = null;
            
            try {
                /*
                 * Return 1 address.
                 */
            	
                addresses = geocoder.getFromLocation(loc.getLatitude(), loc.getLongitude(), 1);
                System.out.println(addresses.get(0));
           } catch (IOException e1) {
            Log.e("LocationSampleActivity","IO Exception in getFromLocation()");
            e1.printStackTrace();
            return ("IO Exception trying to get address");
            } catch (IllegalArgumentException e2) {
            // Error message to post in the log
            String errorString = "Illegal arguments " + Double.toString(loc.getLatitude()) +  " , " +  Double.toString(loc.getLongitude()) +  " passed to address service";
            Log.e("LocationSampleActivity", errorString);
            e2.printStackTrace();
            return errorString;
            }

            // If the reverse geocode returned an address
		if (addresses != null && addresses.size() > 0) {
			// Get the first address
			Address address = addresses.get(0);
		
			/*
			 * Format the first line of address (if available), city, and
			 * country name.
			 */
			String addressText = String.format(	"%s, %s, %s, %s",
					// If there's a street address, add it
					address.getMaxAddressLineIndex() > 0 ? address
							.getAddressLine(0) : "",
					// Locality is usually a city
					address.getLocality(),
					// The country of the address
					address.getCountryName(),address.getSubLocality());
			// Return the text
			return addressText;
		} else {
			return "No address found";
		}
	}
	
    /**
     * A method that's called once doInBackground() completes. Turn
     * off the indeterminate activity indicator and set
     * the text of the UI element that shows the address. If the
     * lookup failed, display the error message.
     */
    @Override
    protected void onPostExecute(String address) {
      listener.onTaskCompleted(address);
    }

}
