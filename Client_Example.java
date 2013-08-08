import java.awt.Color;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Observable;
import java.util.Observer;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

/**
 * Implémente le modèle et controleur du client.
 *
 */
public class Client implements Observer {

	private Authentification auth;
	private View view;

	/**
	 * Constructeur par défaut.
	 */
	public Client() {
		view = new vClientLogin(this);
		((Observable) view).addObserver(this);
		
	}
	
	/**
	 * Permet la vérification de l'identité d'un 
	 * d'un utilisateur.
	 * 
	 * @param xmlRequest
	 * @return
	 */
	public String login() {
		boolean success = false;
		String receive = null;
	
		try {
			
			// Envoie une requete post à la servlet
			HttpClient client = new DefaultHttpClient();
			HttpPost post = new HttpPost("http://10.192.80.162:8080/server/home");

			StringEntity entity = new StringEntity(auth.getXMLRequest(), "UTF-8");
			post.setEntity(entity);
			HttpResponse response = client.execute(post);
			
			// Test si la réponse du serveur est positive
			if(response != null && response.getStatusLine() != null
					&& response.getStatusLine().getStatusCode() == 200) {
				
				BufferedReader in = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "UTF-8"));
				StringBuffer buffer = new StringBuffer();
				String line = in.readLine();
				
				while(line != null) {
					buffer.append(line);
					buffer.append("\r\n");
					line = in.readLine();
				}	
				return buffer.toString();
			}
			client.getConnectionManager().shutdown();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			System.err.println("Exception protocole client.");
		} catch (IOException e) {
			System.err.println("Exception IO.");
		}
		
		return null;
		
	}
	
	/**
	 * Permet de lancer la procédure d'authentification.
	 */
	@Override
	public void update(Observable o, Object arg) {
		
		String response;
		
		// Lance la procédure de login
		auth = new Authentification((vClientLogin)o);
		response = login();
		
		// Change la vue si l'authentification réussi
		if(response != null && !response.contains("<ERROR>")) {
			((vClientLogin)view).dispose();
			view = new vClient();
			((vClient)view).setMessage(response);
		} else {
			((vClientLogin)view).getLogin().setBackground(Color.RED);
			((vClientLogin)view).getYourPassword().setBackground(Color.RED);
		}

	}
	
	public static void main(String[] args) {
		Client client = new Client();
		
	}

}
