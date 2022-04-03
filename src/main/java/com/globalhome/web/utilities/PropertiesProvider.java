package com.globalhome.web.utilities;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class PropertiesProvider {

    private static Properties props;

    public void loadProperties(){
        if(props != null) return;

        props = new Properties();
        try {
            FileInputStream fIpStr = new FileInputStream(
                    System.getProperty("user.dir") + "//src//main//resources//stage.config.properties");
            props.load(fIpStr);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to load properties");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Unable to load properties");
            e.printStackTrace();
        }
    }

    public static Properties getProperties(){
        return props;
    }
}
