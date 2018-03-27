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

  public Part getUploadedFile() {
    return uploadedFile;
  }

  public void setUploadedFile(Part uploadedFile) {
    this.uploadedFile = uploadedFile;
  }

  public void saveFile() {

    try (InputStream input = uploadedFile.getInputStream()) {
      ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();

      folder = ec.getRealPath("/");
      
      Files.copy(input, new File(folder, "978-0060256654" + ".jpg").toPath());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

}
