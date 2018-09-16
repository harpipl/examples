package pl.harpi.samples.powermock.usage;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.lang.reflect.Method;

@RunWith(PowerMockRunner.class)
@PrepareForTest(TestedClass.class)
public class FirstTest {
    private static final Method testedClass_privateMethod = Whitebox.getMethod(TestedClass.class,
            "privateMethod");

    @Test
    public void test() throws Exception {
        // given
        TestedClass spyInstance = PowerMockito.spy(new TestedClass());
        PowerMockito.doReturn("mockedValue").when(spyInstance, "privateMethod");

        // when
        System.out.println("TEST: " + spyInstance.publicMethod());

        // then
    }
}
