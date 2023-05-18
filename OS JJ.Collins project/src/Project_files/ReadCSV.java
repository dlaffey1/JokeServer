package Project_files;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;

public class ReadCSV {
    
    static String ReadJokeFromCSV(){
    
        String jokerow;
        String path = "jokes.csv";
        String RowArray[] = new String[2];
        Random random = new Random();
        int randomIndex = random.nextInt(5);

        try(BufferedReader buf = new BufferedReader(new FileReader(path))){
            //either use output of first as input of second
            //or have a function call in this class itself
            for (int i = 0; i < randomIndex; i++) {
                //Skip lines before the row
                buf.readLine();
            }
            //Read the desired row
            jokerow = buf.readLine();
            
            if(jokerow != null) {
                //System.out.println(jokerow);
                return jokerow;
            }
            else{
                System.out.println("Row not found.");
            }
        }
        catch(Exception notfindcsv){

        }
        return null;
    }

    /*public static void main(String[] args) {
        System.out.println(ReadJokeFromCSV());
    }*/

}
