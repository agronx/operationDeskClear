package com.ralphStahp.spring;

import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

import java.time.format.DateTimeFormatter;

public class MessageLayout extends HorizontalLayout {
    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

    public MessageLayout(ChatMessage message) {
        setWidth("100%");
        add(
                new Span(message.getTime().format(formatter)),
                new Span(message.getFrom() + ": "),
                new Span(message.getMessage()));
    }
}
