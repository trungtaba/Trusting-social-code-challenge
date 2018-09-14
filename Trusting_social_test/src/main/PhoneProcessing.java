package main;

import java.io.*;
import java.util.*;

public class PhoneProcessing {

    private String fileName;
    private ArrayList<Record> records;
    private HashMap<String, Record> results;
    private Config config = Config.getInstance();

    public PhoneProcessing(String fileName) {
        this.fileName = fileName;
        records = new ArrayList<>();
        results = new HashMap<>();
    }

    public void processing() {
        System.out.println(fileName);
        readCSVFile();
        Matching();
//        for (String key : results.keySet()) {
//            System.out.println(results.get(key).getPrintString());
//        }
        writeCDSVFile();
        clear();
    }

    private void readCSVFile() {
        BufferedReader br = null;
        try {
            //System.out.println(config.getInputDir() + fileName);
            br = new BufferedReader(new FileReader(config.getInputDir() + fileName));
            String line;
            String cvsSplitBy = ",";
            Record record;

            line = br.readLine();
            while ((line = br.readLine()) != null) {
                //System.out.println(line);
                String[] data = line.split(cvsSplitBy);
                if (data.length == 3) {
                    record = new Record(data[0], data[1], data[2]);
                } else {
                    record = new Record(data[0], data[1]);
                }
                records.add(record);
            }
        } catch (FileNotFoundException ex) {
            System.out.println("Error when reading file");
            System.out.println(ex.toString());
        } catch (IOException ex) {
            System.out.println("Error when reading file");
            System.out.println(ex.toString());
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    System.out.println("Error:" + e.toString());
                }
            }
        }
    }

    private void Matching() {
        Collections.sort(records);

        int index = 0;
        while (index < records.size()) {
            index = MatchingCurrentActiveRecord(index);
        }
    }

    private int findNextPhoneIndex(int index, String phoneNumber) {
        while (index < records.size() && records.get(index).getPhone().equals(phoneNumber)) {
            index++;
        }
        return index;
    }

    private void writeCDSVFile() {
        System.out.println("Write to file");
        SortedSet<String> keys = new TreeSet<>(results.keySet());

        //PrintWriter pr = null;
        FileWriter fileWriter = null;
        try {
            String FILE_HEADER = "PHONE_NUMBER,REAL_ACTIVATION_DATE";
            String fileOutput = fileName.replace(".csv", "_output.csv");
            fileWriter = new FileWriter(config.getOutputDir() + fileOutput);
            fileWriter.append(FILE_HEADER);
            fileWriter.append("\n");
            //pr = new PrintWriter(new BufferedWriter(new FileWriter(new File("src/data/"+fileName.replace(".csv", "_output.csv")))));
            //pr.println("PHONE_NUMBER,REAL_ACTIVATION_DATE");
            for (String key : keys) {
                Record record = results.get(key);
                if (record != null) {
                    fileWriter.append(record.getPrintString());
                    fileWriter.append("\n");
                }
            }
            //pr.close();
        } catch (IOException e) {
            System.out.println(e.toString());
        } finally {
            if (fileWriter != null) {
                try {
                    fileWriter.close();
                } catch (IOException ex) {
                    System.out.println(ex.toString());
                }
            }
        }

    }

    private int MatchingCurrentActiveRecord(int index) {
        
        Record result, nextRecord;
        result = records.get(index);
        int size = records.size();
        while (index < size) {
            if (result.getDeactivationDate() == null) {
                break;
            } else {
                if (index + 1 < size) {
                    nextRecord = records.get(index + 1);
                    if (!result.getPhone().equals(nextRecord.getPhone())) {
                        break;
                    }
                    if (result.getDeactivationDate().compareTo(nextRecord.getActivationDate()) == 0) {
                        result.setDeactivationDate(nextRecord.getDeactivationDate());
                    } else if (result.getDeactivationDate().compareTo(nextRecord.getActivationDate()) < 0) {
                        result.setActivationDate(nextRecord.getActivationDate());
                        result.setDeactivationDate(nextRecord.getDeactivationDate());
                    }
                } else {
                    break;
                }
            }
            index++;
        }
        index = findNextPhoneIndex(index, result.getPhone());
        if (results.containsKey(result.getPhone())) {
            throw new RuntimeException("Algorithm is wrong");
        }
        results.put(result.getPhone(), result);
        return index;
    }

    private void clear() {
        records.clear();
        results.clear();
    }
}
