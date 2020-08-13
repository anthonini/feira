package br.com.anthonini.feira.init;

import java.awt.Desktop;
import java.net.URI;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class BrowserInitializer {
	
	@Value("${server.port}")
	private String porta;

	@EventListener(ApplicationReadyEvent.class)
	public void launchBrowser(){
		System.setProperty("java.awt.headless", "false");
		Desktop desktop = Desktop.getDesktop();
		try{
			desktop.browse(new URI("http://localhost:"+porta));
		}catch(Exception e){}
	}
	
}
