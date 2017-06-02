package cn.jtduan.snippets.pic;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.FileImageInputStream;
import javax.imageio.stream.ImageInputStream;
import javax.imageio.stream.MemoryCacheImageInputStream;

import org.apache.commons.io.IOUtils;
import org.springframework.util.ResourceUtils;

import com.luciad.imageio.webp.WebPReadParam;

/**
 * 转换webp2图片到png格式
 * jar包来自github(https://github.com/qwong/j-webp)
 */
public class Webp2png {
    public static void main(String[] args) throws IOException {
        byte [] webp = IOUtils.toByteArray(new FileInputStream(ResourceUtils.getFile("classpath:webp.webp")));
        String outputPngPath = "test_.png";

        byte[] out = webp2png(webp);

        ByteArrayInputStream in = new ByteArrayInputStream(out);    //将b作为输入流；
        BufferedImage image = ImageIO.read(in);

        ImageIO.write(image, "png", new File(outputPngPath));
    }

    public static byte[] webp2png(byte[] info){
        ImageReader reader = ImageIO.getImageReadersByMIMEType("image/webp").next();
        WebPReadParam readParam = new WebPReadParam();
        readParam.setBypassFiltering(true);
        ImageInputStream imageInputstream = new MemoryCacheImageInputStream(new ByteArrayInputStream(info));
        reader.setInput(imageInputstream);
        try {
            BufferedImage image = reader.read(0, readParam);
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            ImageIO.write(image, "png", os);
            return os.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            return info;
        }
    }

}
