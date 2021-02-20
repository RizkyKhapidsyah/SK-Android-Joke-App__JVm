package com.dmbteam.joke.network;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;

import com.dmbteam.joke.database.DatabaseManager;
import com.dmbteam.joke.settings.AppConstants;
import com.dmbteam.joke.xml.XmlManager;

/**
 * This class is responsible for all networt operations
 * @authorcdmbTeam
 *
 */
public class NetworkManager {

	/**
	 * Executes input stream data
	 */
	public void executeInputStreamData() {
		new GetInputStreamData().execute();
	}

	/**
	 * Class to get input stream data
	 * @author dmbTeam
	 *
	 */
	private class GetInputStreamData extends AsyncTask<Void, Void, Void> {

		/**
		 * returns the XML input string
		 * @return
		 */
		public InputStream getXmlInputString() {

			try {
				DefaultHttpClient client = new DefaultHttpClient();

				HttpGet method = new HttpGet(new URI(AppConstants.XML_URL));

				HttpResponse res = client.execute(method);
				res = client.execute(method);

				return res.getEntity().getContent();

			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (URISyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				System.out.println("");
				// TODO: handle exception
			}

			return null;
		}

		/**
		 * Operation to do in background
		 */
		@Override
		protected Void doInBackground(Void... params) {

			InputStream inputStream = getXmlInputString();

			XmlManager.getInstance().setInsputStream(inputStream);
			XmlManager.getInstance().parseInputStream();

			DatabaseManager.getInstance().updateOrCreateJokesFromXML();
			return null;
		}

	}

	/**
	 * Checks if the device is connected to a network.
	 * 
	 * @param context
	 *            application context
	 * @return true if the device is connected
	 */
	public static boolean isNetworkConnected(Context context) {
		ConnectivityManager cm = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo ni = cm.getActiveNetworkInfo();
		if (ni == null) {
			// There are no active networks.
			return false;
		} else
			return true;
	}

}
