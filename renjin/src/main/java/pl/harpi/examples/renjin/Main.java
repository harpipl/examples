package pl.harpi.examples.renjin;

import org.renjin.script.RenjinScriptEngine;
import org.renjin.script.RenjinScriptEngineFactory;
import org.renjin.sexp.IntArrayVector;
import org.renjin.sexp.StringArrayVector;

import javax.script.ScriptException;

public class Main {
    public static void main(String[] args) throws ScriptException {
        RenjinScriptEngine engine = new RenjinScriptEngineFactory().getScriptEngine();

        String exp = "(a + b) * c";

        StringArrayVector res = (StringArrayVector) engine.eval(
                "f <- ~" + exp + "; all.vars(f)");

        for (String v: res.toArray()) {
            System.out.println(v);
        }

        engine.put("a", 4);
        engine.put("b", 5);
        engine.put("c", 10);

        IntArrayVector out = (IntArrayVector) engine.eval(exp);

        System.out.println(out.asInt());
    }
}
