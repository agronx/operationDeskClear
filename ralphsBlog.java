package com.ralphStahp.demo;

import com.vaadin.flow.component.dependency.StyleSheet;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.awt.*;

@SpringBootApplication
@Route("")
@StyleSheet("frontend://styles/styles.css")
public class ralphsBlog extends VerticalLayout {
public ralphsBlog() {
	setDefaultHorizontalComponentAlignment(Alignment.CENTER);
	setSizeFull();
	addClassName("ralphsBlog");
	H1 header = new H1("Ralph's Blog");
	header.getElement().getThemeList().add("dark");
}
	private void askUsername() {
HorizontalLayout usernameLayout = new HorizontalLayout();

TextField usernameField = new TextField();
Button start = new Button("be ready to blog");
usernameLayout.add(usernameField, start);
add(usernameLayout);
	}

	public static void main(String[] args) {
		SpringApplication.run(ralphsBlog.class, args);
	}

}
