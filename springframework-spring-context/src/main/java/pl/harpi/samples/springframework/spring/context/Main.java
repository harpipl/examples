package pl.harpi.samples.springframework.spring.context;

import org.springframework.core.annotation.AnnotationUtils;

@Deprecated
public class Main {
    public static void main(String[] args) {
        Deprecated annotation = AnnotationUtils.findAnnotation(Main.class, Deprecated.class);
        System.out.println(annotation);
    }
}
