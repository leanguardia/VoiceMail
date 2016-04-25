package code;

/**
 * Created by Deleguard on 4/25/16.
 */
public interface ConnectionState {
    void handle(String key, Connection c);
    void record(String voice, Connection connection);
    void hangup(Connection connection);
}
