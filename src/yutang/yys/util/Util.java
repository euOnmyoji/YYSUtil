package yutang.yys.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class Util {
    /**
     * @param s 秒数
     * @return 小时 分 秒
     */
    public static long[] parseSeconds(long s) {
        return new long[]{s / 3600, (s % 3600) / 60, s % 60};
    }

    /**
     * @param path  复制到哪个文件夹下的文件夹路径
     * @param files 被复制的文件或文件夹
     */
    public static void copy(Path path, File... files) throws IOException {
        if (files != null) {
            for (File f : files) {
                if (!Files.exists(path)) {
                    Files.createDirectory(path);
                }
                if (f.isDirectory()) {
                    copy(path.resolve(f.getName()), f.listFiles());
                } else {
                    Files.copy(f.toPath(), path.resolve(f.getName()), StandardCopyOption.REPLACE_EXISTING);
                }
            }
        }
    }
}
