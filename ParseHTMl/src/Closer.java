
import java.io.Closeable;
import java.io.IOException;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Le Tuan Anh
 */
public class Closer implements Closeable {

    private Closeable closeable;

    public <T extends Closeable> T using(T t) {
        closeable = t;
        return t;
    }

    @Override
    public void close() throws IOException {
        if (closeable != null) {
            closeable.close();
        }
    }
}
