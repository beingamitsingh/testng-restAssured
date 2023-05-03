import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import requests.AuthenticateClient;

public class AuthTest {

    private static AuthenticateClient authenticateClient = new AuthenticateClient();

    @BeforeSuite
    public void setUp(ITestContext context) {
        String token = authenticateClient.getToken();
        context.setAttribute("token", token);
    }

    @Test
    public void authenticationTest(ITestContext context)  {
        Assert.assertNotNull(context.getAttribute("token"));
        System.out.println(context.getAttribute("token").toString());
    }
}
