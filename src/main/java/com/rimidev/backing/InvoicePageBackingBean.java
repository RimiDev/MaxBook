package com.rimidev.backing;

import com.rimidev.maxbook.controller.ClientJpaController;
import com.rimidev.maxbook.controller.InvoiceDetailsJpaController;
import com.rimidev.maxbook.controller.InvoiceJpaController;
import com.rimidev.maxbook.entities.Client;
import com.rimidev.maxbook.entities.Invoice;
import com.rimidev.maxbook.entities.InvoiceDetails;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.File;
import java.util.List;

import javax.mail.Flags;


import jodd.mail.Email;
import jodd.mail.EmailAddress;
import jodd.mail.EmailAttachment;
import jodd.mail.EmailFilter;
import jodd.mail.EmailMessage;
import jodd.mail.ImapSslServer;
import jodd.mail.ReceiveMailSession;
import jodd.mail.ReceivedEmail;
import jodd.mail.SendMailSession;
import jodd.mail.SmtpServer;
import jodd.mail.SmtpSslServer;



/**
 *
 * @author Philippe Langlois-Pedroso, 1542705
 */
@Named("invoiceBacking")
@SessionScoped
public class InvoicePageBackingBean implements Serializable{
    
    @Inject
    private InvoiceJpaController invoiceController;
    @Inject
    private InvoiceDetailsJpaController invoiceDetailsController;
    @Inject
    private ClientJpaController clientController;
    
    private Invoice invoice;
    private InvoiceDetails details;
    // These must be updated to your email accounts
    private final String smtpServerName = "smtp.gmail.com";
    private final String imapServerName = "imap.gmail.com";
    private final String emailSend = "cst.send@gmail.com";
    private final String emailSendPwd = "CatsLikeFood";
    private final String emailReceive = "cst.receive@gmail.com";
    private final String emailReceivePwd = "CatsLikeFood";
    private final String emailCC1 = "";
    private final String emailCC2 = "";

    // You will need a folder with this name or change it to another
    // existing folder
    private final String attachmentFolder = "C:\\Temp\\Attach\\";
    private static final Logger log = Logger.getLogger(InvoicePageBackingBean.class.getName());
    
    public Invoice getInvoice() throws Exception{
        log.info("getInvoice()");
        if(invoice == null){
            invoice = new Invoice();
            invoice.setId(invoiceController.getInvoiceCount() + 1);
            invoice.setDateOfSale(Date.valueOf(LocalDate.now()));
            invoice.setClientId(clientController.findClient(1));
            invoice.setGrossValue(new BigDecimal(1300.00));
            invoice.setNetValue(new BigDecimal(1500.00));
            details = invoiceDetailsController.findInvoiceDetails(1);
        }
        return this.invoice;
    }
    
    public InvoiceDetails getDetails(){
        return details;
    }
    
    public int getInvoiceId(){
        return invoice.getId();
    }
    
    public java.util.Date getDateOfSale(){
        return invoice.getDateOfSale();
    }
    
    public Client getClient(){
        return invoice.getClientId();
    }
   
    public BigDecimal getGross(){
        return invoice.getGrossValue();
    }
    
    public BigDecimal getNet(){
        return invoice.getNetValue();
    }
    
    public String getLocation(){
        String d = invoice.getClientId().getCity();
        d += ", " +invoice.getClientId().getProvince().getProvince();
        d += ", " +invoice.getClientId().getCountry();
        return d;
    }
    
      // Real programmers use logging
    

  

    /**
     * This method is where the different uses of Jodd are exercised
     */
    public void perform() {
        // Send an ordinary text message
        sendEmail();

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            
            System.exit(1);
        }
        // Receive all email
        receiveEmail();
    }

    /**
     * Standard send routine using Jodd. Jodd knows about GMail so no need to
     * include port information
     */
    public void sendEmail() {

        // Create am SMTP server object
        SmtpServer<SmtpSslServer> smtpServer = SmtpSslServer
                .create(smtpServerName)
                .authenticateWith(emailSend, emailSendPwd);

        // Display Java Mail debug conversation with the server
        smtpServer.debug(true);

        // Using the fluent style of coding create a plain text message
        Email email = Email.create().from(emailSend)
                .to(emailReceive)
                .subject("Jodd Test").addText("Hello from plain text email");

        // A session is the object responsible for communicating with the server
        SendMailSession session = smtpServer.createSession();

        // Like a file we open the session, send the message and close the
        // session
        session.open();
        session.sendMail(email);
        session.close();

    }


    /**
     * Standard receive routine for Jodd We use an ImapSSLServer. The class
     * KenImapSslServer extends ImalSslServer and adds a property to display the
     * mail debug conversation
     */
    public void receiveEmail() {

        // Create am IMAP server object
        ImapSslServer imapSslServer = new ImapSslServer(imapServerName,
                emailReceive, emailReceivePwd);

        // Display the converstaion between the application and the imap server
        imapSslServer.setProperty("mail.debug", "true");

        // A session is the object responsible for communicating with the server
        ReceiveMailSession session = imapSslServer.createSession();
        session.open();

        // We only want messages that have not be read yet.
        // Messages that are delivered are then marked as read on the server
        ReceivedEmail[] emails = session.receiveEmailAndMarkSeen(EmailFilter
                .filter().flag(Flags.Flag.SEEN, false));

        // If there are any emails loop through them displaying their contents
        // and saving the attachments.
        if (emails != null) {
            for (ReceivedEmail email : emails) {
                log.info("\n\n===[" + email.getMessageNumber() + "]===");
                // common info
                
                log.info("FROM:" + email.getFrom());
                log.info("TO:" + email.getTo()[0]);
                log.info("SUBJECT:" + email.getSubject());
                log.info("PRIORITY:" + email.getPriority());
                log.info("SENT DATE:" + email.getSentDate());
                log.info("RECEIVED DATE: " + email.getReceiveDate());

                // Messages may be multi-part so they are stored in an array
                List<EmailMessage> messages = email.getAllMessages();
                for (EmailMessage msg : messages) {
                    log.info("------");
                    log.info(msg.getEncoding());
                    log.info(msg.getMimeType());
                    log.info(msg.getContent());
                }

                // There may be multiple arrays so they are stored in an array
                List<EmailAttachment> attachments = email.getAttachments();
                if (attachments != null) {
                    log.info("+++++");
                    for (EmailAttachment attachment : attachments) {
                        log.info("name: " + attachment.getName());
                        log.info("cid: " + attachment.getContentId());
                        log.info("size: " + attachment.getSize());
                        // Write the file to disk
                        // Location hard coded in this example
                        attachment.writeToFile(new File(attachmentFolder, attachment.getName()));
                    }
                }
            }
        }
        session.close();
    }

    /**
     * Here we create an email message that contains html, embedded image, and
     * an attachment
     *
     * @throws Exception In case we don't find the file to attach/embed
     */
    public void sendWithEmbeddedAndAttachment() throws Exception {

        // Create am SMTP server object
        SmtpServer<SmtpSslServer> smtpServer = SmtpSslServer
                .create(smtpServerName).authenticateWith(emailSend,
                emailSendPwd);

        smtpServer.debug(true);

        // Using the fluent style of coding create an html message
        Email email = Email.create().from(emailSend)
                .to(emailReceive)
                .subject("Test With Attachments")
                .addText("Plain text Hello for Email clients that only handle "
                        + "plain text!")
                .addHtml("<html><META http-equiv=Content-Type "
                        + "content=\"text/html; charset=utf-8\">"
                        + "<body><h1>Here is my photograph embedded in "
                        + "this email.</h1><img src='cid:FreeFall.jpg'>"
                        + "<h2>I'm flying!</h2></body></html>")
                .embed(EmailAttachment.attachment()
                        .bytes(new File("FreeFall.jpg")))
                .attach(EmailAttachment.attachment().file("WindsorKen180.jpg"));

        // A session is the object responsible for communicating with the server
        SendMailSession session = smtpServer.createSession();

        // Like a file we open the session, send the mesage and close the
        // session
        session.open();
        session.sendMail(email);
        session.close();
    }
    
}




/**
 * Here is your documentation on Jodd
 * https://jodd.org/util/email.html
 */

/**
 * This is a demo of the code necessary to carry out the following tasks: 1)
 * Send plain text email 2) Receive email including attachments 3) Send html
 * email with an embedded image and an attachment
 *
 * @author Ken
 * @version 2.0
 *
 */

    /**
     * It all begins here
     *
     * @param args
     */
//    public static void main(String[] args) {
//
//        MainApp m = new MainApp();
//        m.perform();
//        System.exit(0);
//    }
//}