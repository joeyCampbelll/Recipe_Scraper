import java.io.*;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

// This class is a CSVWriter which takes in a list of data as well as a filepath.
//   It then uses this data to handle the writing to a .csv file. It manages 
//   the cleaning of special characters as well. It is an opensource example from:
//   https://www.baeldung.com/java-csv
public class CSVWriter {
	private List<String[]> dataLines;
	private String filePath;

	public CSVWriter(List<String[]> dataLines, String filePath) {
		this.dataLines = dataLines;
		this.filePath = filePath;
	}

	public String convertToCSV(String[] data) {
		return Stream.of(data)
			.map(this::escapeSpecialCharacters)
			.collect(Collectors.joining(","));
	}

	public void writeCSV() throws IOException {
		File csvOutputFile = new File(this.filePath);
		try (PrintWriter pw = new PrintWriter(csvOutputFile)) {
			this.dataLines.stream()
			.map(this::convertToCSV)
			.forEach(pw::println);
		}
	}

	public String escapeSpecialCharacters(String data) {
		String escapedData = data.replaceAll("\\R", " ");
		if (data.contains(",") || data.contains("\"") || data.contains("'")) {
			data = data.replace("\"", "\"\"");
			escapedData = "\"" + data + "\"";
		}
		return escapedData;
	}
}
