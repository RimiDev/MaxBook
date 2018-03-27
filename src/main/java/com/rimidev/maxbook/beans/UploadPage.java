/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rimidev.maxbook.beans;

import java.awt.image.BufferedImage;
import java.io.File;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import javax.faces.component.UIViewRoot;
import javax.imageio.ImageIO;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author 1513733
 */

@ManagedBean
@ViewScoped
public class UploadPage {

    private Part uploadedFile;
    private String fileContent;
    private UploadedFile file;

    public void validateFile(FacesContext ctx, UIComponent comp, Object value) {
        List<FacesMessage> msgs = new ArrayList<FacesMessage>();
        Part file = (Part) value;
        if (file.getSize() > 1024) {
            msgs.add(new FacesMessage("file too big"));
        }
        if (!"text/plain".equals(file.getContentType())) {
            msgs.add(new FacesMessage("not a text file"));
        }
        if (!msgs.isEmpty()) {
            throw new ValidatorException(msgs);
        }
    }

    public void uploadFile(String isbn) throws IOException {
        Path file = Files.createTempFile(Paths.get("resources/images/books"), isbn, ".jpg");
        try (InputStream input = uploadedFile.getInputStream()) {
//            fileContent = new Scanner(uploadedFile.getInputStream()).useDelimiter("\\A").next()
            Files.copy(input, file, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "error uploading file",
                    null);
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }

        fileContent = file.getFileName().toString();
    }

    public void upload(String isbn) {
        if (file != null) {
            BufferedImage image = null;
            try {
                
                URL url = new URL("http://www.mkyong.com/image/mypic.jpg");
                image = ImageIO.read(url);
                
                ImageIO.write(image,"jpg",new File("resources/images/books/"+isbn+".jpg"));
                
            } catch (IOException e) {
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        "error uploading file",
                        null);
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }
            
            UIViewRoot rootView = FacesContext.getCurrentInstance().getViewRoot();
            

            FacesMessage message = new FacesMessage("Succesful", file.getFileName() + " is uploaded.");
            FacesContext.getCurrentInstance().addMessage(null, message);

        }
    }

    public Part getUploadedFile() {
        return uploadedFile;
    }

    public void setUploadedFile(Part uploadedFile) {
        this.uploadedFile = uploadedFile;
    }

    public String getFileContent() {
        return fileContent;
    }

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

}
