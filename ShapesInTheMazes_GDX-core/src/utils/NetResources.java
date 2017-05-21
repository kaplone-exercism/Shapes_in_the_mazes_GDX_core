package utils;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.DefaultAsyncHttpClient;
import org.asynchttpclient.Response;

public class NetResources {
	
	public static String getLevels(){
		
		String retour = "Error";
		
		AsyncHttpClient asyncHttpClient = new DefaultAsyncHttpClient();
		
		CompletableFuture<Response> f = asyncHttpClient
	   		     .prepareGet("http://kaplone.fr/assets/confs/niveaux.conf")
	   		     .execute()
	   		     .toCompletableFuture();
		
		try {
			
			retour = f.get().getResponseBody();
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			//lire le fichier de conf local
			e.printStackTrace();
		}
		try {
			asyncHttpClient.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return retour;
	}

}
