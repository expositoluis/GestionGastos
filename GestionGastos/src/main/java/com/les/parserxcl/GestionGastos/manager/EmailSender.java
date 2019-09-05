/**
 * 
 */
package com.les.parserxcl.GestionGastos.manager;

import java.io.IOException;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.aop.ThrowsAdvice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

/**
 * @author cg00046
 *
 */
@Component
public class EmailSender {

	
	@Autowired
    private JavaMailSender javaMailSender;
	
	void sendEmail() {

        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setFrom("mendel@telefonicacompraselectronicas.com");
        msg.setTo("exposito.luis@gmail.com");

        msg.setSubject("Testing from Spring Boot");
        msg.setText("Hello World \n Spring Boot Email");
try{
        javaMailSender.send(msg);

    }catch(MailException me){
    	System.out.println(me);
    	throw me;
    }
    }
	
	void sendEmailWithAttachment() throws MessagingException, IOException {

        MimeMessage msg = javaMailSender.createMimeMessage();

        // true = multipart message
        MimeMessageHelper helper = new MimeMessageHelper(msg, true);
		
        helper.setTo("to_@email");

        helper.setSubject("Testing from Spring Boot");

        // default = text/plain
        //helper.setText("Check attachment for image!");

        // true = text/html
        helper.setText("<h1>Check attachment for image!</h1>", true);

		// hard coded a file path
        //FileSystemResource file = new FileSystemResource(new File("path/android.png"));

        helper.addAttachment("my_photo.png", new ClassPathResource("android.png"));

        javaMailSender.send(msg);

    }
}
