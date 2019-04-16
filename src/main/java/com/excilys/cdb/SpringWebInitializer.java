package com.excilys.cdb;

import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

@Configuration
public class SpringWebInitializer implements WebApplicationInitializer {

	@Override
	public void onStartup(ServletContext servletCtx) {
		AnnotationConfigWebApplicationContext ctx = new AnnotationConfigWebApplicationContext();
		ctx.register(AppConfig.class);
		ctx.setServletContext(servletCtx);
		ctx.refresh();

		servletCtx.addListener(new ContextLoaderListener(ctx));
		DispatcherServlet servlet = new DispatcherServlet(ctx);

		ServletRegistration.Dynamic registration = servletCtx.addServlet("app", servlet);
		registration.setLoadOnStartup(1);
		registration.addMapping("/");
	}
}