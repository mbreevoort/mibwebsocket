package nl.mib.websocket;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URI;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

@RestController
public class CamImagesController {
    @RequestMapping("/cam1/listdirs")
    public List<String> listDirs() {
        List<String> dirs = new ArrayList<>();
        Path path = Paths.get(URI.create(MibwebsocketApplication.FILE_MNT_SHARE_DATA_MOTION_CAM1));
        try (DirectoryStream<Path> paths = Files.newDirectoryStream(path)) {
            paths.forEach(p -> dirs.add(p.getFileName().toString()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return dirs;
    }

    @RequestMapping("/cam1/list/{dir}")
    public Map<String, List<String>> listdir(@PathVariable("dir") String startdir) throws IOException {
        Path rootDir = Paths.get(URI.create(MibwebsocketApplication.FILE_MNT_SHARE_DATA_MOTION_CAM1 + startdir));
        List<Path> dirs = Collections.singletonList(rootDir);

        Map<String, List<String>> dirToPath = dirs.stream()//
                .collect(Collectors.toMap(dir -> dir.getFileName().toString(), this::listFilesSorted));

        return dirToPath;
    }

    @RequestMapping("/cam1/listall")
    public Map<String, List<String>> listall() throws IOException {
        Path rootDir = Paths.get(URI.create(MibwebsocketApplication.FILE_MNT_SHARE_DATA_MOTION_CAM1));
        List<Path> dirs = listFiles(rootDir);

        Map<String, List<String>> dirToPath = dirs.stream()//
                .collect(Collectors.toMap(dir -> dir.getFileName().toString(), this::listFilesSorted));

        return dirToPath;
    }

    private List<String> listFilesSorted(Path dir) {
        return listFiles(dir).stream().map(p -> p.getFileName().toString()).sorted(String::compareTo).collect(Collectors.toList());
    }

    private List<Path> listFiles(Path path) {
        List<Path> dirs = new ArrayList();

        try (DirectoryStream<Path> paths = Files.newDirectoryStream(path)) {
            paths.forEach(dirs::add);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return dirs;
    }
}
