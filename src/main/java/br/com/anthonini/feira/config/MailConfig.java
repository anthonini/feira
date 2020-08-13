package br.com.anthonini.feira.config;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
@PropertySource(value = { "file:./.feira-mail.properties" }, ignoreResourceNotFound = true)
public class MailConfig {
	
	@Value("${external.feira.mail.username:feira.mail.username}")
	private String username;
	
	@Value("${external.feira.mail.password:feira.mail.password}")
	private String password;
	
	@Value("${external.feira.from.mail:feira.from.mail}")
	private String fromEmail;
	
	@Value("${external.feira.to.mail:feira.to.mail}")
	private String toEmail;

	@Bean
	public JavaMailSender mailSender() {
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		mailSender.setHost("smtp.sendgrid.net");
		mailSender.setPort(587);
		mailSender.setUsername(username);
		mailSender.setPassword(password);
		
		Properties props = new Properties();
		props.put("mail.transport.protocol", "smtp");
		props.put("mail.smtp.auth", true);
		props.put("mail.smtp.starttls.enable", true);
		props.put("mail.debug", false);
		props.put("mail.smtp.connectiontimeout", 10000); // miliseconds

		mailSender.setJavaMailProperties(props);
		
		return mailSender;
	}

	public String getFromEmail() {
		return fromEmail;
	}

	public String getToEmail() {
		return toEmail;
	}
}
