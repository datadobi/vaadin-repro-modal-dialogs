package com.example.application.views;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.progressbar.ProgressBar;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;

import java.time.Instant;
import java.util.concurrent.CompletableFuture;

@PageTitle("Modal Dialogs")
@Route(value = "modal-dialogs", layout = MainLayout.class)
@RouteAlias(value = "", layout = MainLayout.class)
@Uses(Icon.class)
public class ModalDialogs extends VerticalLayout
{
    public ModalDialogs()
    {

        add(new Button("Start operation", clicked -> {
            UI ui = UI.getCurrent();

            // create a dialog, but don't show it immediately
            ModalDialog initialDialog = new ModalDialog(new Div(new Span("This one should remain open")));
            initialDialog.setWidth("40ch");

            ProgressBar progressBar = new ProgressBar();
            progressBar.setIndeterminate(true);
            ModalDialog progressDialog = new ModalDialog(progressBar);
            progressDialog.setWidth("50ch");
            progressDialog.open();

            CompletableFuture<Instant> completableFuture = CompletableFuture.supplyAsync(() -> {

                // "heavy calculation"
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }

                return Instant.now();
            });

            completableFuture.whenComplete((instant, e) -> {
                ui.accessSynchronously(() -> {
                    progressDialog.close();

                    ModalDialog resultDialog = new ModalDialog(
                            new Div(new Span(instant.toString())),
                            new Div(new Span("This one should only close when pushing the Close button"))
                    );

                    resultDialog.setWidth("40ch");

                    resultDialog.add(new Button("Close", c -> resultDialog.close()));

                    resultDialog.open();
                });
            });

            initialDialog.open();
        }));

    }

    private static class ModalDialog extends Dialog {
        public ModalDialog()
        {
            setCloseOnEsc(false);
            setCloseOnOutsideClick(false);
            setModal(true);
        }

        public ModalDialog(Component... components)
        {
            super(components);
            setCloseOnEsc(false);
            setCloseOnOutsideClick(false);
            setModal(true);
        }

        @Override
        public void close()
        {
            super.close();
        }
    }
}
