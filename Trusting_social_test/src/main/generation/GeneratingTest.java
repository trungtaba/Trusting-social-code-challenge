package main.generation;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import main.Config;
import main.Record;

public class GeneratingTest {

    private ArrayList<Record> records;
    private HashMap<String, Record> results;
    private int totalPhoneNumber;
    private String outputName;
    private Config config=Config.getInstance();
    public GeneratingTest(int totalPhoneNumber, String outputName) {
        records = new ArrayList<>();
        results = new HashMap<>();
        this.totalPhoneNumber = totalPhoneNumber;
        this.outputName = outputName;
    }

    public void generate() {
        for (int i = 1; i <= totalPhoneNumber; i++) {
            generateForPhoneNumber(i + "");
        }

        Collections.shuffle(records);

        writeTestToFile();
        writeResultToFile();
    }

    private void generateForPhoneNumber(String phoneNumber) {
        Date activeDate, deactiveDate;
        activeDate = RandomUtils.randomDate();

        // Will be 5 consecutive blocks
        for (int j = 0; j < 5; j++) {
            activeDate = RandomUtils.randomMonth(activeDate);
            // one consecutive block will have 10 elements
            for (int i = 0; i < 10; i++) {
                deactiveDate = RandomUtils.randomDate(activeDate);
                Record record = new Record(phoneNumber, activeDate, deactiveDate);
                records.add(record);
                activeDate = deactiveDate;
            }
        }

        // generateForPhoneNumber the final block which is the answer
        activeDate = RandomUtils.randomMonth(activeDate);
        deactiveDate = RandomUtils.randomDate(activeDate);
        Record activationRecord = new Record(phoneNumber + "", activeDate, deactiveDate);
        records.add(activationRecord);
        activeDate = deactiveDate;
        for (int i = 0; i < 10; i++) {
            deactiveDate = RandomUtils.randomDate(activeDate);
            Record record = new Record(phoneNumber, activeDate, deactiveDate);
            records.add(record);
            activeDate = deactiveDate;
        }
        Record endRecord = new Record(phoneNumber, activeDate, RandomUtils.randomFutureDate());
        records.add(endRecord);
        results.put(phoneNumber, activationRecord);
    }

    private void writeTestToFile() {
        PrintWriter pr = null;
        try {
            pr = new PrintWriter(new BufferedWriter(new FileWriter(new File(config.getInputDir()+outputName))));
            pr.println("PHONE_NUMBER,ACTIVATION_DATE,DEACTIVATION_DATE");

            for (Record record : records) {
                DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
                String activationDate = format.format(record.getActivationDate());
                String deActivationDate = format.format(record.getDeactivationDate());
                pr.println(record.getPhone() + "," + activationDate + "," + deActivationDate);
            }
        } catch (IOException e) {
            System.out.println("Error: generate test file");
            System.out.println(e.toString());
        } finally {
            if (pr != null) {
                pr.close();
            }
        }

    }

    private void writeResultToFile() {
        PrintWriter pr = null;
        try {
            pr = new PrintWriter(new BufferedWriter(new FileWriter(new File(config.getExpected()+outputName.replace(".csv", "_result.csv")))));
            pr.println("PHONE_NUMBER,REAL_ACTIVATION_DATE");

            SortedSet<String> keys = new TreeSet<>(results.keySet());
            for (String key : keys) {
                Record record = results.get(key);
                pr.println(record.getPrintString());
            }
        } catch (IOException e) {
            System.out.println("Error: generate test file result");
            System.out.println(e.toString());
        } finally {
            if (pr != null) {
                pr.close();
            }
        }
    }

    public static void main(String[] args) {
        new GeneratingTest(100,"test_10.csv").generate();
        new GeneratingTest(1000,"test_11.csv").generate();
    }
}
