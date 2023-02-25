package games.negative.framework.discord.config.properties;

import com.google.common.io.ByteStreams;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;

public class PropertiesFile {

    private final String path;
    private final String name;

    public PropertiesFile(String path, String name) {
        this.path = path;
        this.name = name;
    }

    public void createFile() throws IOException {
        createFile(false);
    }

    public void createFile(boolean printDefaults) throws IOException {
        File file;
        if (path == null)
            file = new File(name);
        else
            file = new File(path, name);

        if (file.exists())
            return;

        file.createNewFile();
        if (printDefaults) {
            try (InputStream in = this.getClass().getClassLoader().getResourceAsStream(name);
                 OutputStream out = Files.newOutputStream(file.toPath())) {
                assert in != null;
                ByteStreams.copy(in, out);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public File asFile() {
        return new File(path, name);
    }


}
