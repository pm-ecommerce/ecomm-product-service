package com.pm.ecommerce.product_service.notifications.config;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;
import org.springframework.stereotype.Component;

import java.io.StringWriter;

@Component
public class TemplateParser {

    public <T> String parseTemplate(String name, T data) {
        try {
            MustacheFactory mustache = new DefaultMustacheFactory();
            Mustache m = mustache.compile(name + ".html");
            StringWriter writer = new StringWriter();
            m.execute(writer, data).flush();
            return writer.toString();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return null;
    }
}
