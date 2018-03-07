package yutang.yys.util;

import com.sun.istack.internal.NotNull;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.FileTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/**
 * 一个文件夹以及里面文件的修改时间
 * 仅用于equals 不用于查询
 */
public class FilesModifiedTime {
    private FileTime time;
    private List<FilesModifiedTime> list = new ArrayList<>();

    @SuppressWarnings("WeakerAccess")
    public FilesModifiedTime(@NotNull Path path) throws IOException {
        time = Files.getLastModifiedTime(path);
        if (Files.isDirectory(path)) {
            Iterator<Path> i = Files.list(path).iterator();
            while (i.hasNext()) {
                Path p = i.next();
                list.add(new FilesModifiedTime(p));
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FilesModifiedTime that = (FilesModifiedTime) o;
        return Objects.equals(time, that.time) &&
                Objects.equals(list, that.list);
    }

    @Override
    public int hashCode() {
        return Objects.hash(time, list);
    }
}
