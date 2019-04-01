package helpers;


import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import org.imgscalr.Scalr;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Properties;


public class FileHelper {

    public static ArrayList<String> readLocations() {
        try {
            BufferedReader CSVFile = new BufferedReader(new FileReader("./src/main/resources/locations.csv"));
            ArrayList<String> rows = new ArrayList<String>();
            String dataRow;
            int i = 0;
            while ((dataRow = CSVFile.readLine()) != null) {
                i++;
                rows.add(dataRow.substring(0,dataRow.length()-1));
            }
            return rows;
        } catch (IOException ex) {
            System.out.println("");
            return null;
        }
    }



    public static File loadFile() {
        /*FileDialog fdlg = new FileDialog(new JFrame(), "Open file...", FileDialog.LOAD);
        fdlg.setVisible(true);

        return fdlg.getFiles()[0];
*/
        Properties properties = new Properties();
        try{
            FileInputStream fis = new FileInputStream("src/main/resources/config.properties");
            properties.load(fis);
            String path = properties.getProperty("path");
            fis.close();
            JFileChooser chooser = new JFileChooser(path);
            int r = chooser.showOpenDialog(chooser.getParent());
            return chooser.getSelectedFile();
        } catch(IOException e) {
            System.out.println("Property file not found");
            return new File("");
        }
    }

    public static void savePath(String path) {
        Properties properties = new Properties();
        try{
            FileOutputStream fos = new FileOutputStream("src/main/resources/config.properties");
            properties.setProperty("path",path);
            properties.store(fos,"");
            fos.close();
        } catch(IOException e) {
            System.out.println("Property file not found");
        }

    }

    public static Image extractImage(File file) {

        try {
            Image image = new Image(file.toURI().toURL().toString());
            //PixelReader reader = image.getPixelReader();
            //WritableImage newImage = new WritableImage(reader, (int) image.getWidth() - 380, 215, 290, (int) image.getHeight() - 430);
            return image;
        }catch (IOException e) {
            System.out.println("File not found");
            return new Image("");
        }
    }

    public static File cropFile(File orig_file) {
        File crop_file = new File("temp.img");
        BufferedImage image = new BufferedImage(290,650,BufferedImage.TYPE_INT_RGB);
        try {
            java.awt.Image img = ImageIO.read(orig_file);
            BufferedImage orig_image = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_RGB);
            orig_image.getGraphics().drawImage(img, 0, 0, null);
            //обрезка в зависимости от разрешения монитора/скриншота
            int im_height = orig_image.getHeight();
            int im_width = orig_image.getWidth();
            if (im_height == 1080) {
                if (im_width == 1920) { // соотношение 16:9
                    image = orig_image.getSubimage(orig_image.getWidth() - 380, 215, 290, orig_image.getHeight() - 430);
                } else if (im_width == 2560) { // соотношение 21:9
                    image = orig_image.getSubimage(orig_image.getWidth() - 380 - 320, 215, 290, orig_image.getHeight() - 430);
                } else if (im_width == 3840) { // соотношение 32:9
                    image = orig_image.getSubimage(orig_image.getWidth() - 380 - 960, 215, 290, orig_image.getHeight() - 430);
                }
            } else if (im_height == 1440) {
                if (im_width == 2560) { // соотношение 16:9

                    BufferedImage temp_image;// = new BufferedImage(1920, 1080, BufferedImage.TYPE_INT_RGB);
                    temp_image = Scalr.resize(orig_image, Scalr.Method.BALANCED, 1920, 1080);
                    image = temp_image.getSubimage(temp_image.getWidth() - 380, 215, 290, temp_image.getHeight() - 430);
                } else if (im_width == 3440) { // соотношение 21:9
                    BufferedImage temp_image;// = new BufferedImage(2560, 1080, BufferedImage.TYPE_INT_RGB);
                    temp_image = Scalr.resize(orig_image, Scalr.Method.BALANCED, 2580, 1080);
                    image = temp_image.getSubimage(temp_image.getWidth() - 380 - 330, 215, 290, temp_image.getHeight() - 430);
                }

            }
            ImageIO.write(image, "jpg", crop_file);

        } catch (IOException e){
            System.out.println("Image not found");
        }
        return crop_file;
    }
}
