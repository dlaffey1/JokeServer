import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;


public class JokePipe implements Runnable {
    private String pipeName;

    public JokePipe(String pipeName) {
        this.pipeName = pipeName;
    }

    public void run() {
        try {
            BufferedReader pipeIn = new BufferedReader(new InputStreamReader(Files.newInputStream(Paths.get(pipeName))));
            PrintWriter pipeOut = new PrintWriter(Files.newOutputStream(Paths.get(pipeName)), true);
            String inputLine;

            while ((inputLine = pipeIn.readLine()) != null) {
                if (inputLine.equalsIgnoreCase("JOKE")) {
                    pipeOut.println(getRandomJoke());
                } else {
                    pipeOut.println("Type 'JOKE' to get a joke.");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getRandomJoke() {
        ArrayList<String> jokes = readJokesFromCSV("joke.csv");
        Random random = new Random();
        int jokeIndex = random.nextInt(jokes.size());
        return jokes.get(jokeIndex);
    }
    
    private ArrayList<String> readJokesFromCSV(String fileName) {
        ArrayList<String> jokes = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                jokes.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jokes;
    }
   /*This code adds two new methods: getRandomJoke() and readJokesFromCSV(String fileName).
    The getRandomJoke() method now reads jokes from the "joke.csv" file, generates a random index,
    and returns the joke at that index. The readJokesFromCSV() method reads the jokes from the
    specified CSV file and returns them as an ArrayList of strings.*/
    
    
}

