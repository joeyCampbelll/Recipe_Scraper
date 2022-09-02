import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;

public class RecipeScraper {
    public static void main(String[] args) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://www.surlatable.com/recipes/?srule=best-matches&start=0&sz=24")) //starting URL...
                .GET() // GET is default
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

		// OLD CODE TO OUTPUT HTML RESPONSE TO FILE
		
		// //output to a file so its easy to mess with (you won’t be for your finished program)
		// FileOutputStream fs = new FileOutputStream("ioFiles\\tempOutput.txt");
		// PrintWriter pw = new PrintWriter(fs);
		// //response.body() is the html source code in a string  format. It outputs to a file so you 
		// // can see it easier right now, but you will ultimately want to just manipulate the strings a lot
        // pw.println(response.body());
        // pw.close();

		// ===================

		String htmlText = response.body();

		ArrayList<String> recipeLinks = new ArrayList<String>();

		Scanner stringReader = new Scanner(htmlText);

		while (stringReader.hasNext()) {
			String result = stringReader.nextLine();
			if (result.startsWith("<a class=\"thumb-link\" href=\"https://www.surlatable.com/")) {
				// System.out.println(result);
				result = result.substring(28);
				result = result.split("\"")[0];
				System.out.println(result);
			}
		}
    }
}