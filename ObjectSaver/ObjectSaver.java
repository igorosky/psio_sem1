package ObjectSaver;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Optional;

public final class ObjectSaver {
    public static <T extends Serializable> boolean serialize(T obj, final String filename) {
        try {
            FileOutputStream fos = new FileOutputStream(filename);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(obj);
            oos.close();
        } catch (final IOException e) {
            return false;
        }
        return true;
    }

    public static <T extends Serializable> boolean serialize(T obj, final File file) {
        try {
            FileOutputStream fos = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(obj);
            oos.close();
        } catch (final IOException e) {
            return false;
        }
        return true;
    }

    public static <T extends Serializable> Optional<T> deserialize(final String filename) {
        try {
            FileInputStream fis = new FileInputStream(filename);
            ObjectInputStream ois = new ObjectInputStream(fis);
            Object obj = ois.readObject();
            ois.close();
            if (obj != null /*&& obj instanceof T*/) {  // foobar
                return Optional.of((T) obj);
            }
            return Optional.empty();
        } catch (final IOException | ClassNotFoundException e) {
            return Optional.empty();
        }
    }

    public static <T extends Serializable> Optional<T> deserialize(final File file) {
        try {
            FileInputStream fis = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(fis);
            Object obj = ois.readObject();
            ois.close();
            if (obj != null /*&& obj instanceof T*/) {  // foobar
                return Optional.of((T) obj);
            }
            return Optional.empty();
        } catch (final IOException | ClassNotFoundException e) {
            return Optional.empty();
        }
    }
}
