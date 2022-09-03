import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.RunnableScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.io.*;

public class RecipeScraper {
    public static void main(String[] args) throws IOException, InterruptedException {
		runScraper();
    }

	private static void runScraper() throws IOException, InterruptedException {
		// String[] urls = { "https://www.surlatable.com/recipes/?srule=best-matches&start=0&sz=24", 
		// 					"https://www.surlatable.com/recipes/?srule=best-matches&start=24&sz=24", 
		// 					"https://www.surlatable.com/recipes/?srule=best-matches&start=48&sz=24", 
		// 					"https://www.surlatable.com/recipes/?srule=best-matches&start=72&sz=24" };
		String[] urls = { "https://www.surlatable.com/recipes/?srule=best-matches&start=0&sz=24" };
		
		ArrayList<String> recipeInformation = new ArrayList<String>();

		for (String url : urls) {
			HttpClient client = HttpClient.newHttpClient();
			HttpRequest request = HttpRequest.newBuilder()
					.uri(URI.create(url)) //starting URL...
					.GET() // GET is default
					.build();
			HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
	
			String htmlText = response.body();
	
			ArrayList<String> recipeLinks = getRecipeLinks(htmlText);

			getRecipeInformation(recipeLinks, recipeInformation);
		}
	}

	private static ArrayList<String> getRecipeLinks(String htmlText) {
		ArrayList<String> recipeLinks = new ArrayList<String>();

		Scanner stringReader = new Scanner(htmlText);

		// This while loop scans the string of recipe tiles and pulls the links to each recipe

		while (stringReader.hasNext()) {
			String result = stringReader.nextLine();
			if (result.startsWith("<a class=\"thumb-link\" href=\"https://www.surlatable.com/")) {
				result = result.substring(28);
				result = result.split("\"")[0];
				// System.out.println(result); // used to debug
				recipeLinks.add(result);
			}
		}

		return recipeLinks;
	}

	private static void getRecipeInformation(ArrayList<String> recipeLinks, ArrayList<String> recipeInformation) throws IOException, InterruptedException{
		
		for (String recipeLink : recipeLinks) {
			// System.out.println(recipeLink);
			HttpClient client = HttpClient.newHttpClient();
			HttpRequest request = HttpRequest.newBuilder()
			.uri(URI.create(recipeLink)) //starting URL...
			.GET() // GET is default
			.build();
			HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
			
			String htmlText = response.body();
			
			getBreadCrumb(htmlText);
			TimeUnit.SECONDS.sleep(10);
		}
	}

	private static String getBreadCrumb(String htmlText) {
		
		Scanner stringReader = new Scanner(htmlText);
		String breadCrumb = "";
		while (stringReader.hasNext()) {
			String result = stringReader.nextLine();
			String temp = "";

			if (result.startsWith("<a class=\"breadcrumb-element\"")) {
				// System.out.println(result);
				temp += result.split("\"")[5];
				breadCrumb += temp + " \\ ";
				breadCrumb = breadCrumb.replaceAll("&amp;", "&");
			} else if (result.startsWith("<h1 class=\"recipe-name\">")) {
				// System.out.println(result);
				temp += result.split(">")[1];
				temp = temp.substring(0, temp.length() - 4);
				breadCrumb += temp;
				breadCrumb = breadCrumb.replaceAll("Quick &#38;", "");
				break;
			}
		}

		System.out.println(breadCrumb);
		return breadCrumb;
	}
}


		// OLD CODE TO OUTPUT HTML RESPONSE TO FILE
		
		// //output to a file so its easy to mess with (you won’t be for your finished program)
		// FileOutputStream fs = new FileOutputStream("ioFiles\\tempOutput.txt");
		// PrintWriter pw = new PrintWriter(fs);
		// //response.body() is the html source code in a string  format. It outputs to a file so you 
		// // can see it easier right now, but you will ultimately want to just manipulate the strings a lot
        // pw.println(response.body());
        // pw.close();

		// ===================