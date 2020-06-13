package br.com.anthonini.feira;

import java.util.Locale;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.FixedLocaleResolver;

import br.com.anthonini.arquitetura.controller.SairController;
import br.com.anthonini.arquitetura.thymeleaf.ArquiteturaDialect;
import br.com.anthonini.feira.storage.FotoStorage;
import br.com.anthonini.feira.storage.local.FotoStorageLocal;

@SpringBootApplication
public class FeiraApplication {
	
	private static ApplicationContext APPLICATION_CONTEXT;

	public static void main(String[] args) {
		APPLICATION_CONTEXT = SpringApplication.run(FeiraApplication.class, args);
	}
	
	public static <T> T getBean(Class<T> type) {
		return APPLICATION_CONTEXT.getBean(type);
	}
	
	@Bean
	public LocaleResolver localeResolver() {
		return new FixedLocaleResolver(new Locale("pt", "BR"));
	}
	
	@Bean
	public ArquiteturaDialect arquiteturaDialect() {
		return new ArquiteturaDialect();
	}
	
	@Configuration
	@ComponentScan(basePackageClasses = { SairController.class })
	public static class MvcConfig implements WebMvcConfigurer {

		@Override
		public void addViewControllers(ViewControllerRegistry registry) {
			registry.addRedirectViewController("/", "/feira");
		}
	}
	
	@Bean
	public FotoStorage fotoStorage() {
		return new FotoStorageLocal();
	}
}
