import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.io.*;

public class RecipeScraper {
    public static void main(String[] args) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://www.surlatable.com/recipes/?srule=best-matches&start=0&sz=24")) //starting URL...
                .GET() // GET is default
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

		//output to a file so its easy to mess with (you won’t be for your finished program)
		FileOutputStream fs = new FileOutputStream("ioFiles\\tempOutput.txt");
		PrintWriter pw = new PrintWriter(fs);
		//response.body() is the html source code in a string  format. It outputs to a file so you 
		// can see it easier right now, but you will ultimately want to just manipulate the strings a lot
        pw.println(response.body()); 
        pw.close();
    }
}