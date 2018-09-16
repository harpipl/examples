package pl.harpi.samples.powermock.usage;

public class TestedClass {
    private String privateMethod() {
        System.out.println("invoke: TestedClass.privateMethod()");
        return "realValue";
    }

    public String publicMethod() {
        return privateMethod();
    }
}
