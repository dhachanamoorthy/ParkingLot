import java.util.*;
import java.io.*;

class FileService {
    public List<String> readInputs() { // To read inputs from 'input.txt'
        List<String> inputs = new ArrayList<>();
        File file = new File(System.getProperty("user.dir") + "/resources/input.txt");
        try {
            Scanner reader = new Scanner(file);
            while (reader.hasNextLine()) {
                String dataString = reader.nextLine();
                inputs.add(dataString);
            }
            reader.close();
        } catch (Exception e) {
            System.out.println("Failed to read input.txt" + e);
        }
        return inputs;
    }

    public void writeOutput(List<String> outputs){
        try{
            String data = "";
            String path=System.getProperty("user.dir") + "/resources/output.txt";
            FileWriter writer = new FileWriter(path);
            for(String line:outputs){
                data +=line+"\n";
            };
            writer.write(data);
            writer.close();
        }catch(Exception e){
            System.out.println("Failed to write ," + e);
        }
    }
}