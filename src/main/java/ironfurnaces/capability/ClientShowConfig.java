package ironfurnaces.capability;

public class ClientShowConfig {

    private static int showConfig;

    public static void set(int showConfig) {
        ClientShowConfig.showConfig = showConfig;
    }

    public static int getShowConfig() {
        return showConfig;
    }

}
