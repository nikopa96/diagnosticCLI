package ee.netgroup.su.diagnostic.cli.database;

public class EmptyDatabaseException extends Exception {

    public EmptyDatabaseException(String message) {
        super(message);
    }
}
