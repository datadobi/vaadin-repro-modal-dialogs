package com.example.application;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.dependency.NpmPackage;
import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.component.page.Push;
import com.vaadin.flow.server.ServiceInitEvent;
import com.vaadin.flow.server.VaadinServiceInitListener;
import com.vaadin.flow.shared.communication.PushMode;
import com.vaadin.flow.shared.ui.Transport;
import com.vaadin.flow.theme.Theme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * The entry point of the Spring Boot application.
 *
 * Use the @PWA annotation make the application installable on phones, tablets
 * and some desktop browsers.
 *
 */
@SpringBootApplication
@Theme(value = "modal-dialogs")
@NpmPackage(value = "line-awesome", version = "1.3.0")
@Push(value = PushMode.DISABLED, transport = Transport.LONG_POLLING)
public class Application extends SpringBootServletInitializer implements AppShellConfigurator, VaadinServiceInitListener
{
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void serviceInit(ServiceInitEvent serviceInitEvent)
    {
        serviceInitEvent.getSource().addUIInitListener(uiInitEvent -> {
            UI ui = uiInitEvent.getUI();
            ui.setPollInterval(2000);
        });

    }
}
