public class Recipe {
	private String name;
	private String path;
	private String ingredients;
	private String instructions;
	private String servings;
	private String htmlString;

	public Recipe(String htmlString) {
		this.htmlString = htmlString;
		
		this.name = parseName();
		this.path = parsePath();
		this.ingredients = parseIngredients();
		this.instructions = parseInstructions();
		this.servings = parseServings();

		System.out.println(this);
	}

	private String parseName() {
		String name = "";

		return name;
	}

	private String parsePath() {
		String path = "";

		return path;
	}

	private String parseIngredients() {
		String ingredients = "";

		return ingredients;
	}

	private String parseInstructions() {
		String instructions = "";

		return instructions;
	}

	private String parseServings() {
		String servings = "";

		return servings;
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
		return "{" +
			" name='" + getName() + "'" +
			", path='" + getPath() + "'" +
			", ingredients='" + getIngredients() + "'" +
			", instructions='" + getInstructions() + "'" +
			", servings='" + getServings() + "'" +
			", htmlString='" + getHtmlString() + "'" +
			"}";
	}
}