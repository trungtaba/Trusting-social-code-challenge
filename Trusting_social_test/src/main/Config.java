package main;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/*
create a singleton config
*/
public class Config {

    private static Config CONFIG_INSTANCE = null;
    private String inputDir;
    private String outputDir;
    private String expected;
    

    public static Config getInstance() {
        if (CONFIG_INSTANCE == null) {
            synchronized (Config.class) {
                if (CONFIG_INSTANCE == null) {
                    CONFIG_INSTANCE = new Config();
                }
            }
        }
        return CONFIG_INSTANCE;
    }

    private Config() {
        
        LoadProperties();
    }

    private void LoadProperties() {
        InputStream input = null;
        Properties properties=new Properties();
        try {
            input = new FileInputStream("src/config.properties");
            properties.load(input);
            inputDir = properties.getProperty("inputDir");
            outputDir = properties.getProperty("outputDir");
            expected=properties.getProperty("expectedDir");
        } catch (IOException ex) {
            System.err.println("Error:..." + ex.getMessage());
        } finally {
            if (input != null) {
            try {
                input.close();
            } catch (IOException ex) {
                System.err.println("Error:" + ex.toString());
            }
        }
        }
    }

    public String getInputDir() {
        return inputDir;
    }

    public String getOutputDir() {
        return outputDir;
    }

    public String getExpected() {
        return expected;
    }
    
    
}