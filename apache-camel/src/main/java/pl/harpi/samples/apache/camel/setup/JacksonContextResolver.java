package pl.harpi.samples.apache.camel.setup;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;

import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;

@Provider
@Produces(MediaType.APPLICATION_JSON)
public class JacksonContextResolver implements ContextResolver<ObjectMapper> {
    private final ObjectMapper mapper;

    public JacksonContextResolver() {
        mapper = createDefaultMapper();
    }

    private static ObjectMapper createDefaultMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationConfig.Feature.INDENT_OUTPUT, true);

        return mapper;
    }

    @Override
    public ObjectMapper getContext(Class<?> aClass) {
        return mapper;
    }

/*
    @Override
    public ObjectMapper getContext(Class<?> cls) {
        return mapper;
    }

    public static class ObjectIdSerializerModule extends SimpleModule {

        public ObjectIdSerializerModule() {
            super("ObjectIdSerializerModule", new Version(0, 1, 0, "alpha"));
            this.addSerializer(new ObjectIdSerializer());
        }

        public class ObjectIdSerializer extends JsonSerializer<Object> {

            @Override
            public void serialize(Object o, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
                jsonGenerator.writeString(o.toString());
            }
        }
    }
*/
}
