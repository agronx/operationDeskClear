package com.ralphStahp.spring;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.JavaScript;
import com.vaadin.flow.component.dependency.StyleSheet;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.page.BodySize;
import com.vaadin.flow.component.page.Push;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.InitialPageSettings;
import com.vaadin.flow.server.PageConfigurator;
import com.vaadin.flow.server.VaadinService;
import com.vaadin.flow.server.WebBrowser;
import com.vaadin.flow.shared.BrowserDetails;
import reactor.core.Disposable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.UnicastProcessor;


import javax.swing.*;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.util.Date;
import java.util.SimpleTimeZone;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;

@Route("")
@Push
@StyleSheet("frontend://styles/styles.css")
@JavaScript("src/ios-keyboard-fix.js")
@BodySize
public class ralphsBlog extends VerticalLayout implements PageConfigurator {



    private final UnicastProcessor<ChatMessage> messagePublisher;

    private MessageList messageList;
    private String  username;
    private Disposable messageSubscription;
    private Flux<ChatMessage> messages;





        public ralphsBlog(Flux<ChatMessage> messages, UnicastProcessor<ChatMessage> messagePublisher) throws UnknownHostException {
            this.messages= messages;
            this.messagePublisher = messagePublisher;
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
            
                
            

        setSizeFull();
        addClassName("ralphsBlog");
        H1 header = new H1("Ralph's Blog.");

        header.getElement().getThemeList().add("dark");

        add(header);


        askUsername();
    }


        private void askUsername() throws UnknownHostException {

           final WebBrowser webBrowser = new WebBrowser();
InetAddress ip = InetAddress.getLocalHost();
            HorizontalLayout usernameLayout = new HorizontalLayout();
            TextField usernameField = new TextField();
            usernameField.setValue( "" );
            usernameField.focus();

            final Label ipAddresslabel = new Label(String.valueOf( ip ));




            HorizontalLayout ipTime = new HorizontalLayout();
            ipTime.setWidth( "100%" );

            Button start = new Button( "be ready to blog" );

            usernameLayout.add( usernameField, start, ipAddresslabel );



            start.addClickListener( click -> {
                username = usernameField.getValue();
                remove( usernameLayout );
                showChat();
            } );
            add( usernameLayout );
        }
private void showChat() {
    MessageList messageList = new MessageList();
    add( messageList, createInputLayout() );
    expand( messageList );

    messageSubscription = messages.subscribe( message -> {
        getUI().ifPresent( ui ->
                ui.access( () -> messageList.add( new MessageLayout( message ) ) ) );

        getUI().ifPresent( ui -> ui.addDetachListener( event -> {
            messageSubscription.dispose();
        } ) );


    } );
}
private HorizontalLayout createInputLayout(){
            HorizontalLayout inputLayout = new HorizontalLayout();
            inputLayout.setWidth("100%");
        TextField messageField = new TextField();
        Button sendButton = new Button("Send");
        sendButton.getElement().getThemeList().add("primary");

        inputLayout.add(messageField, sendButton);
        inputLayout.expand(messageField);

        messageField.setPlaceholder("type it");

        sendButton.addClickListener(click -> {
            if (messageField.getValue().isEmpty())
                return;
            messagePublisher.onNext(new ChatMessage(username, messageField.getValue()));
            messageField.clear();
            messageField.focus();

        });
        messageField.focus();
        return inputLayout;
}
@Override
    public void configurePage(InitialPageSettings settings){
    settings.addLink(InitialPageSettings.Position.PREPEND, "manifest", "manifest.webmanifest");
    settings.addLink(InitialPageSettings.Position.PREPEND, "icon", "favicon.png");
    settings.addInlineWithContents(
            "window.addEventListener('load', function() { " + "  if('serviceWorker' in navigator) {"
                    + "    navigator.serviceWorker.register('./sw.js');" + "  }" + "});",
            InitialPageSettings.WrapMode.JAVASCRIPT);
}
}

