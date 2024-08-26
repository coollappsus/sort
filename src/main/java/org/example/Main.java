package org.example;

import org.example.model.LineReader;

import java.io.*;
import java.util.*;

public class Main {

    public static final String defaultInputPath = "data/forSort.txt";
    public static final String defaultOutputPath = "data/result.txt";

    public static void main(String[] args) throws IOException {
        long before = System.currentTimeMillis();
        String inputPath = getInputPath();
        String outputPath = getOutputPath();

        splitFile(inputPath, 100 * 1024 * 1024); // частями по 100 МБ
        List<File> sortedFiles = sortParts(inputPath);
        mergeFiles(sortedFiles, outputPath);
        clean(inputPath);

        long after = System.currentTimeMillis();
        System.out.println("Sort is finish. Duration - " + (after - before) / 1000 + " s");
    }

    private static String getOutputPath() {
        Scanner input = new Scanner(System.in);
        System.out.println("Введи путь, куда необходимо сохранить отсортированный файл");
        String outputPath = input.nextLine();
        if (outputPath.isBlank()) {
            outputPath = defaultOutputPath;
        }
        return outputPath;
    }

    private static String getInputPath() {
        Scanner input = new Scanner(System.in);
        System.out.println("Введи путь к файлу, который необходимо отсортировать");
        String inputPath = input.nextLine();
        if (inputPath.isBlank()) {
            inputPath = defaultInputPath;
        }
        return inputPath;
    }

    private static void splitFile(String inputFile, int partSize) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {
            int partIndex = 0;
            String line;
            while ((line = reader.readLine()) != null) {
                File partFile = new File(inputFile + ".part" + partIndex++);
                try (PrintWriter writer = new PrintWriter(new FileWriter(partFile))) {
                    int partBytes = 0;
                    while (partBytes < partSize && (line = reader.readLine()) != null) {
                        if (line.isEmpty()) {
                            continue; // если попались пустые строки, пропустим
                        }
                        writer.println(line);
                        partBytes += line.length() + 1; // учитываем перевод строки
                    }
                }
            }
        }
    }

    private static List<File> sortParts(String inputFile) throws IOException {
        List<File> sortedFiles = new ArrayList<>();
        File[] partFiles = new File(inputFile).getParentFile().listFiles((dir, name) -> name.contains(".part"));
        for (File partFile : partFiles) {
            List<String> lines = new ArrayList<>();
            try (BufferedReader reader = new BufferedReader(new FileReader(partFile))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    lines.add(line);
                }
            }
            Collections.sort(lines);
            File sortedFile = new File(partFile.getPath() + ".sorted");
            try (PrintWriter writer = new PrintWriter(new FileWriter(sortedFile))) {
                for (String l : lines) {
                    writer.println(l);
                }
            }
            sortedFiles.add(sortedFile);
        }
        return sortedFiles;
    }

    private static void mergeFiles(List<File> sortedFiles, String outputFile) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(outputFile))) {
            PriorityQueue<LineReader> pq = new PriorityQueue<>(Comparator.comparing(LineReader::getLine));
            for (File file : sortedFiles) {
                pq.offer(new LineReader(file));
            }
            while (!pq.isEmpty()) {
                LineReader lr = pq.poll();
                writer.println(lr.getLine());
                if (lr.readNextLine()) {
                    pq.offer(lr);
                }
            }
        }
    }

    private static void clean(String inputFile) {
        File files = new File(inputFile).getParentFile();
        for (File file : Objects.requireNonNull(files.listFiles())) {
            if (file.getName().contains("part")) {
                file.delete();
            }
        }
    }
}