import java.io.*;
import java.util.Scanner;

public class Recipe {
	private String name = "";
	private String path = "";
	private String ingredients = "";
	private String instructions = "";
	private String servings = "";
	private String htmlString;

	public Recipe(String htmlString) throws IOException {
		this.htmlString = htmlString;
		
		parsePathAndName();
		parseServings();
		parseIngredients();
		parseInstructions();

		replaceHtmlEntities();

		// Used for debugging
		System.out.println(this);
	}
	

	private void parsePathAndName() {

		Scanner stringReader = new Scanner(htmlString);

		// This while loop scans the htmlString looking for the path and name of the recipe
		//   It is entirely comprised of string manipulation and scanning
		while (stringReader.hasNext()) {
			String result = stringReader.nextLine();
			String temp = "";

			// This if condition is checking for a prefix found before each path element
			if (result.startsWith("<a class=\"breadcrumb-element\"")) {
				// System.out.println(result);
				temp += result.split("\"")[5];
				temp = temp.substring(6);
				this.path += temp + " \\ ";
				this.path = path.replaceAll("&amp;", "&");

			// This is condition is checking for a prefix found before each name element
			} else if (result.startsWith("<h1 class=\"recipe-name\">")) {
				temp += result.split(">")[1];
				temp = temp.substring(0, temp.length() - 4);

				// The path also containes the name of the recipe
				this.path += temp;
				
				name = temp;
				
				// Once the name of the recipe is retrieved, we can break out of the loop
				break;
			}
		}

		stringReader.close();
	}

	// This method is used to parse the ingredients from the htmlString
	private void parseIngredients() {
		Scanner stringReader = new Scanner(htmlString);

		while (stringReader.hasNext()) {
			String result = stringReader.nextLine();

			// This line marks the beginning of the ingredients div
			if (result.contains("recipe-details-ingredients")) {
				
				// All ingredients start and end with an unordered list tag
				//   Therefore, I read each line until I see the closing tag
				while (!result.startsWith("</div>")) {
					result = stringReader.nextLine();
					ingredients += result + "\n";
				}

				// Gets rid of all html elements
				ingredients = ingredients.replaceAll("</div>|<li>|</li>|<i>|</i>|<ul>|</ul>|<b>|</b>", "");
				ingredients = ingredients.replaceAll("<br>", "\n");

				break;
			}
		}
		stringReader.close();
	}

	// This method is used to parse the instructions from the htmlString
	private void parseInstructions() {
		Scanner stringReader = new Scanner(htmlString);

		while (stringReader.hasNext()) {
			String result = stringReader.nextLine();

			// This line marks the beginning of the ingredients div
			if (result.contains("recipe-details-procedure")) {
				
				// Exits the loop once the first closing div is reached
				while (!result.startsWith("</div>")) {
					result = stringReader.nextLine();
					instructions += result + "\n";
				}

				// Gets rid of all html elements
				instructions = instructions.replaceAll("</div>|<li>|</li>|<i>|</i>|<ul>|</ul>|<b>|</b>", "");
				instructions = instructions.replaceAll("<br>", "");

				break;
			}
		}
		stringReader.close();
	}

	private void parseServings() {
		Scanner stringReader = new Scanner(htmlString);

		// This while loop scans the htmlString looking for the servings of the recipe
		//   It is entirely comprised of string manipulation and scanning
		while (stringReader.hasNext()) {
			String result = stringReader.nextLine();

			// This if condition is checking for a prefix found before each path element
			if (result.startsWith("<div class=\"recipe-details-serves\"")) {
				servings = stringReader.nextLine();
				// System.out.println(result);
				// temp += result.split(">")[0];
				// temp = temp.substring(0, temp.length() - 6);

			// Once the name of the recipe is retrieved, we can break out of the loop
				break;
			}
		}

		if (servings.equals("")) 
			servings = "Not listed";

		stringReader.close();
	}

	private void replaceHtmlEntities() {
		name = RemoveHtmlEntity.replaceHtmlEntities(name);
		path = RemoveHtmlEntity.replaceHtmlEntities(path);
		ingredients = RemoveHtmlEntity.replaceHtmlEntities(ingredients);
		instructions = RemoveHtmlEntity.replaceHtmlEntities(instructions);
		servings = RemoveHtmlEntity.replaceHtmlEntities(servings);
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPath() {
		return this.path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getIngredients() {
		return this.ingredients;
	}

	public void setIngredients(String ingredients) {
		this.ingredients = ingredients;
	}

	public String getInstructions() {
		return this.instructions;
	}

	public void setInstructions(String instructions) {
		this.instructions = instructions;
	}

	public String getServings() {
		return this.servings;
	}

	public void setServings(String servings) {
		this.servings = servings;
	}

	public String getHtmlString() {
		return this.htmlString;
	}

	public void setHtmlString(String htmlString) {
		this.htmlString = htmlString;
	}

	@Override
	public String toString() {
		return "Name:   " + this.name + "\n" +
				"Path:   " + this.path + "\n" +
				"Serves: " + this.servings + "\n\n" +
				"Ingredients: " + this.ingredients + "\n\n" +
				"Instructions: " + this.instructions + "\n\n";
	}
}