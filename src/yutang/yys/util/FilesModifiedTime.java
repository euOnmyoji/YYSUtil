package yutang.yys.util;

import com.sun.istack.internal.NotNull;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.FileTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author yys
 * 一个文件夹以及里面文件的修改时间
 * 仅用于equals 不用于查询
 */
public class FilesModifiedTime {
    private List<FileTime> value;

    public FilesModifiedTime(@NotNull Path path) throws IOException {
        List<FileTime> value = new ArrayList<>();
        Files.walk(path).forEach(p -> {
            try {
                value.add(Files.getLastModifiedTime(p));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FilesModifiedTime that = (FilesModifiedTime) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {

        return Objects.hash(value);
    }
}
