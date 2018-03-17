package yutang.yys.util;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

public class FileVisitorBuilder<T> extends SimpleFileVisitor<T> {
    private Function<T, BasicFileAttributes, FileVisitResult> preVisitDirectory;
    private Function<T, BasicFileAttributes, FileVisitResult> visitFile;
    private Function<T, IOException, FileVisitResult> visitFileFailed;
    private Function<T, IOException, FileVisitResult> postVisitDirectory;

    public FileVisitorBuilder<T> visitFile(Function<T, BasicFileAttributes, FileVisitResult> vistFile) {
        this.visitFile = vistFile;
        return this;
    }

    public FileVisitorBuilder<T> visitFileFailed(Function<T, IOException, FileVisitResult> visitFileFailed) {
        this.visitFileFailed = visitFileFailed;
        return this;
    }

    public FileVisitorBuilder<T> postVisitDirectory(Function<T, IOException, FileVisitResult> postVisitDirectory) {
        this.postVisitDirectory = postVisitDirectory;
        return this;
    }

    public FileVisitorBuilder<T> preVisitDirectory(Function<T, BasicFileAttributes, FileVisitResult> preVisitDirectory) {
        this.preVisitDirectory = preVisitDirectory;
        return this;
    }

    public FileVisitor<T> build() {
        return new FileVisitor<T>() {
            @Override
            public FileVisitResult preVisitDirectory(T dir, BasicFileAttributes attrs) throws IOException {
                return preVisitDirectory != null ? preVisitDirectory.run(dir, attrs) : FileVisitorBuilder.super.preVisitDirectory(dir, attrs);
            }

            @Override
            public FileVisitResult visitFile(T file, BasicFileAttributes attrs) throws IOException {
                return visitFile != null ? visitFile.run(file, attrs) : FileVisitorBuilder.super.visitFile(file, attrs);
            }

            @Override
            public FileVisitResult visitFileFailed(T file, IOException exc) throws IOException {
                return visitFileFailed != null ? visitFileFailed.run(file, exc) : FileVisitorBuilder.super.visitFileFailed(file, exc);
            }

            @Override
            public FileVisitResult postVisitDirectory(T dir, IOException exc) throws IOException {
                return postVisitDirectory != null ? postVisitDirectory.run(dir, exc) : FileVisitorBuilder.super.postVisitDirectory(dir, exc);
            }
        };
    }

    @FunctionalInterface
    public interface Function<T, U, R> {
        R run(T t, U u) throws IOException;
    }
}
