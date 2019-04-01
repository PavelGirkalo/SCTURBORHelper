package helpers;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

import javax.imageio.ImageIO;
import java.awt.color.ColorSpace;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class RecognHelper {



    public static String[] recognImages(ArrayList<File> files) {
        ITesseract instance = new Tesseract();
        instance.setDatapath("src/main/resources/tessdata");
        String[] nicknameList;

        //считывание и файлов изображений
        ArrayList<BufferedImage> images = new ArrayList<>(files.size());
        for(int i = 0; i < files.size(); i++){
            try {
                BufferedImage image = ImageIO.read(files.get(i));
                images.add(image);
            } catch (IOException e){
                System.out.println("Image not found");
            }
        }

        //обработка изображений
        ArrayList<File> processed_files = processingImage(images);

        //распознавание текста
        String result = null;
        try {
            result = instance.doOCR(processed_files.get(0));
        } catch (TesseractException e1) {
            e1.printStackTrace();
        }
        //files.get(0).delete();
        nicknameList = result.split("\\n");
        return nicknameList;
    }

    private static ArrayList<File> processingImage(ArrayList<BufferedImage> images) {
        ArrayList<File> processed_files = new ArrayList<>();

        for (BufferedImage crop_image : images){
            //негатив
            short[] negative = new short[256 * 1];
            for (int i = 0; i < 256; i++) negative[i] = (short) (255 - i);
            ShortLookupTable table = new ShortLookupTable(0, negative);
            LookupOp op2 = new LookupOp(table, null);
            crop_image = op2.filter(crop_image, crop_image);

            //ч-б преобразование
            ColorConvertOp grayOp = new ColorConvertOp(ColorSpace.getInstance(ColorSpace.CS_GRAY), null);
            crop_image = grayOp.filter(crop_image, crop_image);

            //осветление
            float a = 1.3f;
            RescaleOp op1 = new RescaleOp(a, 0, null);
            crop_image = op1.filter(crop_image, crop_image);
        }

        //преобразование фрагмента в файл
        for (int i = 0; i < images.size(); i++) {
            File file = new File("temp.jpg");
            if (file.delete()) file = new File("temp.jpg");
            try {
                ImageIO.write(images.get(i), "jpg", file);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            processed_files.add(file);
        }
        return processed_files;

    }


}
