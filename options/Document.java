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

    private final String PATH = "src\\pong\\options\\config.properties";

    private Properties PROP;

    public Document() {
        PROP = loadValues();
    }

    public void saveValues(Properties prop) {
        try (OutputStream output = new FileOutputStream(PATH)) {
            prop.store(output, null);
        } catch (IOException io) {
        }
    }

    public Properties loadValues() {
        try (InputStream input = new FileInputStream(PATH)) {
            Properties properties = new Properties();
            properties.load(input);
            return properties;
        } catch (IOException ex) {
        }
        return null;
    }

    public int getWinPoints() {
        return Integer.parseInt(PROP.getProperty("winPoints"));
    }

    public int getBallSpeed() {
        switch (PROP.getProperty("ballSpeed")) {
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
        return Double.parseDouble(PROP.getProperty("musicValue"));
    }

    public boolean getMusic() {
        return Boolean.parseBoolean(PROP.getProperty("music"));
    }

    public double getSoundVolume() {
        return Double.parseDouble(PROP.getProperty("soundValue"));
    }

    public boolean getSound() {
        return Boolean.parseBoolean(PROP.getProperty("sound"));
    }
}
