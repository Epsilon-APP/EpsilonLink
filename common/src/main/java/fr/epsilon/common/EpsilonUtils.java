package fr.epsilon.common;

public class EpsilonUtils {
    public static String getServerIdentifier() {
        return System.getenv("IDENTIFIER");
    }

    public static String getServerType() {
        return System.getenv("TYPE");
    }

    public static int getServerPort() {
        return Integer.parseInt(System.getenv("PORT"));
    }

    public static boolean isLinuxHost() {
        return System.getenv("OS").equals("linux");
    }
}
