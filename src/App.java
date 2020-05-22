
public class App {

	public static void main(String[] args) {
		
		//Bestemming zoeken uit binnenkomend JSON bestand uit variable jsonfile
		String destination_country = jsonfile.getJSONObject("header").getString("receiveCountry");
		String destination_bank = jsonfile.getJSONObject("header").getString("receiveBank");
	}
}
