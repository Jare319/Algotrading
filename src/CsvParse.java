import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class CsvParse {
    
    static public void Parse(Algo algo, String ticker) throws FileNotFoundException {

        FileReader fileReader = new FileReader("src\\HistoricalData\\" + ticker + "_5yr.csv");
        
        Scanner scnr = new Scanner(fileReader);
        String temp;
        scnr.nextLine(); //skip headers

        while (scnr.hasNext()) {
            temp = scnr.nextLine();

            algo.AddDate(temp.split(",",-1)[0]);
            algo.AddClosePr(Double.parseDouble(temp.split(",",-1)[1].replace("$", "")));
            algo.AddVolume(Integer.parseInt(temp.split(",",-1)[2]));
            algo.AddOpen(Double.parseDouble(temp.split(",",-1)[3].replace("$", "")));
            algo.AddHigh(Double.parseDouble(temp.split(",",-1)[4].replace("$", "")));
            algo.AddLow(Double.parseDouble(temp.split(",",-1)[5].replace("$", "")));
        }

        scnr.close();
    }

}
