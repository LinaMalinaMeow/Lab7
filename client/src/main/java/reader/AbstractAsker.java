package reader;

import object.Vehicle;

import java.util.Scanner;

public abstract class AbstractAsker implements Asker{
    protected Scanner userScanner;

    public AbstractAsker(Scanner userScanner) {
        this.userScanner = userScanner;
    }
}
