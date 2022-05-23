import java.io.FileNotFoundException;
import java.util.Collections;
import java.util.ArrayList;


public class Algo {

    ////////////////////////////////////////////////////////// DATA ARRAYS ////////////////////////////////////////////////////////

    private ArrayList<String> Date = new ArrayList<String>();

    private ArrayList<Double> ClosePr = new ArrayList<Double>();

    private ArrayList<Integer> Volume = new ArrayList<Integer>();

    private ArrayList<Double> Open = new ArrayList<Double>();

    private ArrayList<Double> High = new ArrayList<Double>();

    private ArrayList<Double> Low = new ArrayList<Double>();

    ////////////////////////////////////////////////////////// VARS //////////////////////////////////////////////////////////

    private String ticker;

    private double balance;

    private boolean hasMadeDailyAction = false;

    private int numTransactions = 0;

    private int numShares = 0;

    private double startingFunds;

    //private double volatility;

    //////////////////////////////////////////////////////// METHODS ////////////////////////////////////////////////////////

    public Algo() {

    }

    public void Populate() {
        try {
            CsvParse.Parse(this, ticker);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Collections.reverse(Date);
        Collections.reverse(ClosePr);
        Collections.reverse(Volume);
        Collections.reverse(Open);
        Collections.reverse(High);
        Collections.reverse(Low);
    }

    public void AddDate(String date) {
        this.Date.add(date);
    }

    public void AddClosePr(double closePr) {
        this.ClosePr.add(closePr);
    }

    public void AddVolume(int volume) {
        this.Volume.add(volume);
    }

    public void AddOpen(double open) {
        this.Open.add(open);
    }

    public void AddHigh(double high) {
        this.High.add(high);
    }

    public void AddLow(double low) {
        this.Low.add(low);
    }

    public void PrintDates() {
        for (int i = 0; i < Date.size(); i++) {
            System.out.println(ClosePr.get(i));
        }
    }

    public void Run(String ticker, double startingFunds, int RALowLength, int RAHighLength, int startDate) {
        this.ticker = ticker;
        this.startingFunds = startingFunds;
        this.balance = startingFunds;
        this.Populate();

        double RALow;
        double RAHigh;

        buy(ClosePr.get(0));
        
        for (int i = startDate; i < Date.size(); i++) {
            RALow = this.constructRA(RALowLength,i);
            RAHigh = this.constructRA(RAHighLength,i); 
            hasMadeDailyAction = false;

            if (!hasMadeDailyAction) {
                if ((RALow - RAHigh) > # && numShares == 0) {
                    buy(ClosePr.get(i));
                    hasMadeDailyAction = true;
                }
                else if ((RAHigh - RALow) > # && numShares > 0) {
                    sell(ClosePr.get(i));
                    hasMadeDailyAction = true;
                }
                hasMadeDailyAction = true;
            }
        }
        sell(ClosePr.get(ClosePr.size()-1));
        this.printInfo();
    }

    public void printInfo() {
        System.out.println("\n---------------------------------------------------------------");
        System.out.printf("Ticker: [%s]\nStarting Funds: [%6.2f]\n", ticker,startingFunds);
        System.out.printf("Final Balance: [$%6.2f]\n\nNumber of Transactions: [%d]\nPercent Gain: [%3.2f]\n", balance,numTransactions,((balance - startingFunds) / startingFunds) * 100);
        System.out.println("---------------------------------------------------------------\n");
    }

    public void buy(double price) {
        numShares = (int)(this.balance / price);
        this.balance -= (numShares * price);
        numTransactions++;
    }

    public void sell(double price) {
        this.balance += (numShares * price);
        numShares = 0;
        numTransactions++;
    }

    public double constructRA(int RALength,int currentDay) {
        double avg = 0;
        for (int i = currentDay - RALength; i < currentDay ;i++) {
            avg += ClosePr.get(i);
        }
        avg /= RALength;
        return avg;
    }

    public double getVol(double RALow, double RAHigh) {
        return Math.abs(RAHigh - RALow);
    }
}
