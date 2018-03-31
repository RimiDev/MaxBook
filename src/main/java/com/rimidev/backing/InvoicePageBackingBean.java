package com.rimidev.backing;

import com.rimidev.maxbook.controller.ClientJpaController;
import com.rimidev.maxbook.controller.InvoiceDetailsJpaController;
import com.rimidev.maxbook.controller.InvoiceJpaController;
import com.rimidev.maxbook.entities.Client;
import com.rimidev.maxbook.entities.Invoice;
import com.rimidev.maxbook.entities.InvoiceDetails;
import java.io.BufferedReader;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.logging.Logger;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.mail.Flags;
import javax.servlet.http.HttpSession;
import jodd.mail.Email;
import jodd.mail.EmailFilter;
import jodd.mail.EmailMessage;
import jodd.mail.ImapSslServer;
import jodd.mail.ReceiveMailSession;
import jodd.mail.ReceivedEmail;
import jodd.mail.SendMailSession;
import jodd.mail.SmtpServer;
import jodd.mail.SmtpSslServer;

/**
 * Backing Bean for the invoice page to help manage data.
 *
 * @author Philippe Langlois-Pedroso, 1542705
 * @author Erix Hughes
 */
@Named("invoiceBacking")
@SessionScoped
public class InvoicePageBackingBean implements Serializable {

    @Inject
    private InvoiceJpaController invoiceController;
    @Inject
    private InvoiceDetailsJpaController invoiceDetailsController;
    @Inject
    private ClientJpaController clientController;
    private String markup;
    private Invoice invoice;
    private InvoiceDetails details;
    // These must be updated to your email accounts
    private final String smtpServerName = "smtp.gmail.com";
    private final String imapServerName = "imap.gmail.com";
    private final String emailSend = "twinmuscleworkout117.com";
    private final String emailSendPwd = "somepassword";
    private final String emailReceive = "hues.business@gmail.com";
    private final String emailReceivePwd = "somepassword";
    private HttpSession session;
    private BigDecimal subTotal;

    // You will need a folder with this name or change it to another
    // existing folder
    private static final Logger log = Logger.getLogger(InvoicePageBackingBean.class.getName());

    @PostConstruct
    public void init() {
        session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        invoice = (Invoice) session.getAttribute("clientInvoice");
    }

    public String getMarkup() {
        return markup;
    }

    public void setMarkup(String markup) {
        this.markup = markup;
    }

    /**
     * Set the model if the current bean is null.
     *
     * @return
     * @throws Exception
     */
    public Invoice getInvoice() throws Exception {
        log.info("getInvoice()");
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        invoice = (Invoice) session.getAttribute("clientInvoice");
        return this.invoice;
    }

    public InvoiceDetails getDetails() {
        return details;
    }

    public int getInvoiceId() {
        return invoice.getId();
    }

    public java.util.Date getDateOfSale() {
        return invoice.getDateOfSale();
    }

    public Client getClient() {
        return invoice.getClientId();
    }

    public BigDecimal getGross() {
        return invoice.getGrossValue();
    }

    public BigDecimal getNet() {
        return invoice.getNetValue();
    }

    public String getLocation() {
        String d = invoice.getClientId().getCity();
        d += ", " + invoice.getClientId().getProvince().getProvince();
        d += ", " + invoice.getClientId().getCountry();
        return d;
    }

    /**
     * This method is where the different uses of Jodd are exercised
     */
    public String perform() throws Exception {
        // Send an ordinary text message
        log.info("MARKUP>>>>>>>>" + markup);
        sendWithEmbeddedAndAttachment(markup);

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {

            System.exit(1);
        }
        // Receive all email
        receiveEmail();
        return "home?faces-redirect=true";
    }

    /**
     * Standard send routine using Jodd. Jodd knows about GMail so no need to
     * include port information
     */
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
    public void sendWithEmbeddedAndAttachment(String page) throws Exception {

        // Create am SMTP server object
        SmtpServer<SmtpSslServer> smtpServer = SmtpSslServer
                .create(smtpServerName).authenticateWith(emailSend,
                emailSendPwd);

        smtpServer.debug(true);

        // Using the fluent style of coding create an html message
        Email email = Email.create().from(emailSend)
                .to(emailReceive)
                .subject("Invoice")
                .addText("Invoice")
                .addHtml(markup);

        // A session is the object responsible for communicating with the server
        SendMailSession session = smtpServer.createSession();

        // Like a file we open the session, send the mesage and close the
        // session
        session.open();
        session.sendMail(email);
        session.close();
    }

    public String convertHtmlToString() throws MalformedURLException, IOException {

        URL website = new URL("http://localhost:8080/MaxBook/invoice.xhtml");
        URLConnection connection = website.openConnection();
        BufferedReader in = new BufferedReader(
                new InputStreamReader(
                        connection.getInputStream()));

        StringBuilder response = new StringBuilder();
        String inputLine;

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }

        in.close();

        return response.toString();

//        return Jsoup.connect("http://localhost:8080/MaxBook/invoice.xhtml").get().toString();
    }

    public BigDecimal generateSubTotal() {
        log.info("GENERATING SUB TOTAL");
//        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        
        this.subTotal = new BigDecimal(0.0);
        double count = 0.00;
        
        log.info("INVOICE DETAILS LIST SIZE: " +invoice.getInvoiceDetailsList().size());
        for (InvoiceDetails d : invoiceDetailsController.findInvoiceDetailsEntities()
                .stream()
                .filter(i -> i.getInvoiceId().getId().intValue() == invoice.getId().intValue()).collect(Collectors.toList())) {
            count += d.getBookPrice().doubleValue();
        }
        subTotal = new BigDecimal(count);
        return subTotal;
    }
    
    public BigDecimal getHst(){
        log.info("Fetch Invoice HST");
        return new BigDecimal(invoice.getClientId().getProvince().getHSTrate().doubleValue() * subTotal.doubleValue());
    }

}