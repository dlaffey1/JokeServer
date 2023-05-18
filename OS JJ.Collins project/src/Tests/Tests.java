import Project_files.ServerHost;
import org.junit.jupiter.api.*;

public class Tests{

    private ServerHost server;
    private final String host = "localhost";
    private final int port = 1234;

    @BeforeEach
    public void setup() {
        server = new ServerHost(host, port);
    }

    @AfterEach
    public void tearDown() {
        // Stop the server after each test
        server.stopServer();
    }

    @Test
    public void testServerStartup() {
        // Start the server
        server.startServer();

        // Check if the server is running
        boolean isRunning = server.isRunning();

        // Assert that the server is running
        Assertions.assertTrue(isRunning, "Server should be running");
    }
}
