import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

// This is the main class to handle the entire program
public class RecipeScraper {
    public static void main(String[] args) throws IOException, InterruptedException {
		runScraper();
    }

	private static void runScraper() throws IOException, InterruptedException {
		// String[] urls = { "https://www.surlatable.com/recipes/?srule=best-matches&start=0&sz=24" };
		String[] urls = { "https://www.surlatable.com/recipes/?srule=best-matches&start=0&sz=24", 
							"https://www.surlatable.com/recipes/?srule=best-matches&start=24&sz=24", 
							"https://www.surlatable.com/recipes/?srule=best-matches&start=48&sz=24", 
							"https://www.surlatable.com/recipes/?srule=best-matches&start=72&sz=24" };
		
		// This is the main ArrayList of type Recipe which is an object encapsulating recipe information
		ArrayList<Recipe> recipes = new ArrayList<Recipe>();

		// Loops through the recipe pages and their respective URL's to get information on each recipe
		//   This is the main loop for the program
		int recipeCount = 0;
		for (String url : urls) {
			HttpClient client = HttpClient.newHttpClient();
			HttpRequest request = HttpRequest.newBuilder()
					.uri(URI.create(url)) //starting URL...
					.GET() // GET is default
					.build();
			HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

			// getRecipeLinks is a method that parses the webpage containing multiple recipes, returning a list of links
			ArrayList<String> recipeLinks = getRecipeLinks(response.body());

			// Once the list of recipeLinks is created, the information of each recipe needs to be parsed
			getRecipeInformation(recipeLinks, recipes, recipeCount);
			recipeCount += 24;
		}

		List<String[]> dataLines = new ArrayList<>();
		dataLines.add(new String[] {
			"Name", "Author", "Path", "Servings", "Ingredients", "Instructions"
		});

		for (Recipe recipe : recipes) {
			dataLines.add(new String[] {
				recipe.getName(),
				recipe.getAuthor(),
				recipe.getPath(),
				recipe.getServings(),
				recipe.getIngredients(),
				recipe.getInstructions()
			});
		}

		CSVWriter writer = new CSVWriter(dataLines, "recipes.csv");
		writer.writeCSV();
	}

	// This method parses the webpage of recipe tiles, returning a list of recipe links
	private static ArrayList<String> getRecipeLinks(String htmlString) {
		ArrayList<String> recipeLinks = new ArrayList<String>();

		Scanner stringReader = new Scanner(htmlString);

		// This while loop scans the string of recipe tiles and pulls the links to each recipe
		while (stringReader.hasNext()) {
			String result = stringReader.nextLine();
			if (result.startsWith("<a class=\"thumb-link\" href=\"https://www.surlatable.com/")) {
				result = result.substring(28);
				result = result.split("\"")[0];
				recipeLinks.add(result);
				
			}
		}

		stringReader.close();

		return recipeLinks;
	}

	// This is the method that controls the scraping of each individual recipe 
	private static void getRecipeInformation(ArrayList<String> recipeLinks, ArrayList<Recipe> recipes, int recipeCount) throws IOException, InterruptedException{
		// Loop through each recipe and gather necessary recipe data
		for (String recipeLink : recipeLinks) {
			HttpClient client = HttpClient.newHttpClient();
			HttpRequest request = HttpRequest.newBuilder()
			.uri(URI.create(recipeLink)) //starting URL...
			.GET() // GET is default
			.build();
			HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

			String htmlString = response.body();

			recipes.add(new Recipe(htmlString));
			System.out.println("Completed Parsing Recipe #" + ++recipeCount);
			TimeUnit.SECONDS.sleep(2);
		}
	}
}