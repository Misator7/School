/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pong.options;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

/**
 *
 * @author User
 */
public class Document {

    private final String CONFIG = "rsrc\\config.properties";
    private final String SCOREBOARD = "rsrc\\scoreboard.properties";

    private final Properties CONFIGPROP;
    private final Properties SCOREPROP;

    public Document() {
        CONFIGPROP = loadValues(CONFIG);
        SCOREPROP = loadValues(SCOREBOARD);
    }

    private void saveValues(Properties prop, String path) {
        try (OutputStream output = new FileOutputStream(path)) {
            prop.store(output, null);
        } catch (IOException io) {
        }
    }
    
    public void saveConfig(Properties prop) {
        saveValues(prop, CONFIG);
    }
    
    public void saveScoreboard(Properties prop) {
        saveValues(prop, SCOREBOARD);
    }

    private Properties loadValues(String path) {
        try (InputStream input = new FileInputStream(path)) {
            Properties properties = new Properties();
            properties.load(input);
            return properties;
        } catch (IOException ex) {
        }
        return null;
    }
    
    public Properties loadConfig() {
        return loadValues(CONFIG);
    }
    
    public Properties loadScoreboard() {
        return loadValues(SCOREBOARD);
    }
    
    public String[][] getScoreboard() {
        String[][] scoreboard = new String[10][2];
        for (int i = 0; i < 10; i++) {
            scoreboard[i][0] = SCOREPROP.getProperty(Integer.toString(i) + "name");
            scoreboard[i][1] = SCOREPROP.getProperty(Integer.toString(i) + "time");
        }
        return scoreboard;
    }

    public int getWinPoints() {
        return Integer.parseInt(CONFIGPROP.getProperty("winPoints"));
    }

    public int getBallSpeed() {
        switch (CONFIGPROP.getProperty("ballSpeed")) {
            case "very slow":
                return 3;
            case "slow":
                return 4;
            case "normal":
                return 5;
            case "fast":
                return 6;
            case "very fast":
                return 7;
            default:
                return 4;
        }
    }

    public double getMusicVolume() {
        return Double.parseDouble(CONFIGPROP.getProperty("musicValue"));
    }

    public boolean getMusic() {
        return Boolean.parseBoolean(CONFIGPROP.getProperty("music"));
    }

    public double getSoundVolume() {
        return Double.parseDouble(CONFIGPROP.getProperty("soundValue"));
    }

    public boolean getSound() {
        return Boolean.parseBoolean(CONFIGPROP.getProperty("sound"));
    }
    
    public double getHeight() {
        String a = CONFIGPROP.getProperty("resolution");
        a = a.replace(" ", "");
        String[] b = a.split("x");
        return Double.parseDouble(b[1]);
    }
    
    public double getWidth() {
        String a = CONFIGPROP.getProperty("resolution");
        a = a.replace(" ", "");
        String[] b = a.split("x");
        return Double.parseDouble(b[0]);
    }
    
    public boolean isAutoResolution() {
        return "auto".equals(CONFIGPROP.getProperty("resolution")); 
    }
    
    public Properties getPROP() {
        return CONFIGPROP;
    }
}
