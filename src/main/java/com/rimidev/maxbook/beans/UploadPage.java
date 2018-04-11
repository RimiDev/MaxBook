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
import java.io.Serializable;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIViewRoot;
import javax.faces.context.ExternalContext;
import javax.imageio.ImageIO;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author 1513733
 */
@ManagedBean
@SessionScoped
public class UploadPage implements Serializable {

  private Part uploadedFile;
  private String folder = "c:\\files";
  private static final Logger LOG = Logger.getLogger("UploadPage.class");

  public Part getUploadedFile() {
    return uploadedFile;
  }

  public void setUploadedFile(Part uploadedFile) {
    this.uploadedFile = uploadedFile;
  }

  public void saveFile(String isbn) {

    LOG.info("ISBN: SAVE FILES: " + isbn);

    try (InputStream input = uploadedFile.getInputStream()) {
      ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();

      folder = ec.getRealPath("/");
      folder += "resources\\images\\books\\";
      LOG.info("FOLDER: " + folder);
      Path path = Paths.get(folder + isbn + ".jpg");
      Files.deleteIfExists(path);
      Files.copy(input, new File(folder, isbn + ".jpg").toPath());
    } catch (IOException e) {
      e.printStackTrace();
      LOG.info("WTF");
    }
  }

}

