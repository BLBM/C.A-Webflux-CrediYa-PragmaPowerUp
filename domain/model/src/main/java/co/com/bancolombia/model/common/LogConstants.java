package co.com.bancolombia.model.common;

public class LogConstants {

    private LogConstants() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    //PROCESS
    public static final String START_PROCESS = "Starting process: {}";
    public static final String END_PROCESS = "Ending process: {}";
    public static final String ERROR_PROCESS = "Error occurred in process: {}";
    public static final String USER_CREATED = "User created successfully: {}";

    //CONTROLLERS
    public static final String REQUEST_RECEIVED = "Request received: {}";
}
