package yutang.yys.util;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

/**
 * FileVisitorBuilder
 *
 * @param <T> 接受的类型
 * @author yys
 */
public class FileVisitorBuilder<T> {
    private F<T, BasicFileAttributes, FileVisitResult> preVisitDirectory;
    private F<T, BasicFileAttributes, FileVisitResult> visitFile;
    private F<T, IOException, FileVisitResult> visitFileFailed;
    private F<T, IOException, FileVisitResult> postVisitDirectory;

    public FileVisitorBuilder<T> visitFile(F<T, BasicFileAttributes, FileVisitResult> vistFile) {
        this.visitFile = vistFile;
        return this;
    }

    public FileVisitorBuilder<T> visitFileFailed(F<T, IOException, FileVisitResult> visitFileFailed) {
        this.visitFileFailed = visitFileFailed;
        return this;
    }

    public FileVisitorBuilder<T> postVisitDirectory(F<T, IOException, FileVisitResult> postVisitDirectory) {
        this.postVisitDirectory = postVisitDirectory;
        return this;
    }

    public FileVisitorBuilder<T> preVisitDirectory(F<T, BasicFileAttributes, FileVisitResult> preVisitDirectory) {
        this.preVisitDirectory = preVisitDirectory;
        return this;
    }

    public FileVisitor<T> build() {
        return new SimpleFileVisitor<T>() {
            @Override
            public FileVisitResult preVisitDirectory(T dir, BasicFileAttributes attrs) throws IOException {
                return preVisitDirectory != null ? preVisitDirectory.run(dir, attrs) : super.preVisitDirectory(dir, attrs);
            }

            @Override
            public FileVisitResult visitFile(T file, BasicFileAttributes attrs) throws IOException {
                return visitFile != null ? visitFile.run(file, attrs) : super.visitFile(file, attrs);
            }

            @Override
            public FileVisitResult visitFileFailed(T file, IOException exc) throws IOException {
                return visitFileFailed != null ? visitFileFailed.run(file, exc) : super.visitFileFailed(file, exc);
            }

            @Override
            public FileVisitResult postVisitDirectory(T dir, IOException exc) throws IOException {
                return postVisitDirectory != null ? postVisitDirectory.run(dir, exc) : super.postVisitDirectory(dir, exc);
            }
        };
    }

    @FunctionalInterface
    public interface F<T, U, R> {
        R run(T t, U u) throws IOException;
    }
}
