package data;

import java.security.NoSuchAlgorithmException;

public interface CryptoModule {
    String hash(String password);
}
