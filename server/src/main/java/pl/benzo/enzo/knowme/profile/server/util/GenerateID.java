package pl.benzo.enzo.knowme.profile.server.util;

import java.security.SecureRandom;

public final class GenerateID {

    private static final SecureRandom secureRandom = new SecureRandom();
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@";
    private static final int ID_LENGTH = 9;

    public static String create() {
        StringBuilder id = new StringBuilder(ID_LENGTH);
        for (int i = 0; i < ID_LENGTH; i++) {
            int randomIndex = secureRandom.nextInt(CHARACTERS.length());
            id.append(CHARACTERS.charAt(randomIndex));
        }
        return id.toString();
    }
}
