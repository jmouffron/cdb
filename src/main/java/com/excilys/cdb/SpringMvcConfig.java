package com.excilys.cdb;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

@EnableWebMvc
@Configuration
public class SpringMvcConfig implements WebMvcConfigurer {

  @Bean
  public ViewResolver viewResolver() {
    InternalResourceViewResolver bean = new InternalResourceViewResolver();

    bean.setViewClass(JstlView.class);
    bean.setPrefix("/views/");
    bean.setSuffix(".jsp");

    return bean;
  }

  @Bean
  public MessageSource messageSource() {
      ReloadableResourceBundleMessageSource msgSrc = new ReloadableResourceBundleMessageSource();

      msgSrc.setBasename("classpath:locale/messages");
      msgSrc.setCacheSeconds(1);
      msgSrc.setUseCodeAsDefaultMessage(true);
      msgSrc.setDefaultEncoding("UTF-8");

      return msgSrc;
  }
  
  public void addResourceHandlers(final ResourceHandlerRegistry registry) {
    registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
  }
}
