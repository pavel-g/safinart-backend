package net.safinart.app.services;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import net.coobird.thumbnailator.Thumbnails;

@Service
public class ImageService {

    @Value("${app.images_dir}")
    private String imagesDir;

    @Value("${app.images_preview_dir}")
    private String imagesPreviewDir;

    @Value("${app.image_preview_width}")
    private int previewWidth = 50;

    @Value("${app.image_preview_height}")
    private int previewHeight = 50;

    public void saveImage(String fileName, byte[] data) throws IOException {
        var fullFileName = this.getImageFullPath(fileName);
        this.writeImageToFile(fullFileName, data);
        this.resizeImage(fileName);
    }

    public void saveImage(String fileName, String base64Data) throws IOException {
        var decoder = Base64.getDecoder();
        var data = decoder.decode(base64Data.getBytes());
        this.saveImage(fileName, data);
    }

    public byte[] loadImage(String fileName) throws IOException {
        var fullFileName = this.getImageFullPath(fileName);
        return this.loadImageFromFile(fullFileName);
    }

    public byte[] loadMinImage(String fileName) throws IOException {
        var fullFileName = this.getPreviewImageFullPath(fileName);
        return this.loadImageFromFile(fullFileName);
    }

    public String getImageFullPath(String fileName) {
        return this.imagesDir + "/" + fileName;
    }

    public String getPreviewImageFullPath(String fileName) {
        return this.imagesPreviewDir + "/" + fileName;
    }

    protected void writeImageToFile(String fullFileName, byte[] data) throws IOException {
        var fileWriter = new FileOutputStream(fullFileName);
        fileWriter.write(data);
        fileWriter.close();
    }

    protected void resizeImage(String origImageFileName) throws IOException {
        var origImageFullFileName = this.getImageFullPath(origImageFileName);
        var previewImageFullFileName = this.getPreviewImageFullPath(origImageFileName);
        Thumbnails.of(origImageFullFileName).size(this.previewWidth, this.previewHeight)
                .toFile(previewImageFullFileName);
    }

    protected byte[] loadImageFromFile(String fullFileName) throws IOException {
        var fileReader = new FileInputStream(fullFileName);
        var data = fileReader.readAllBytes();
        fileReader.close();
        return data;
    }
    
}