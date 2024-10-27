package org.example;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class LegalFileGenerator {
    public static void main(String[] args) {
        String fileName = "forSort.txt";
        int fileSize = 1024 * 1024 * 2024;
        generateLegalFile(fileName, fileSize);
    }

    private static void generateLegalFile(String fileName, int fileSize) {
        List<String> legalTerms = new ArrayList<>();
        legalTerms.add("Истец");
        legalTerms.add("Ответчик");
        legalTerms.add("Судебное разбирательство");
        legalTerms.add("Договор");
        legalTerms.add("Иск");
        legalTerms.add("Исковое заявление");
        legalTerms.add("Доказательства");
        legalTerms.add("Свидетельские показания");
        legalTerms.add("Судебное решение");
        legalTerms.add("Апелляция");

        List<String> legalDefinitions = new ArrayList<>();
        legalDefinitions.add("лицо, обращающееся в суд с требованием о защите нарушенного или оспариваемого права или законного интереса.");
        legalDefinitions.add("лицо, к которому предъявлен иск в суде.");
        legalDefinitions.add("процедура рассмотрения дела в суде с целью установления истины и вынесения справедливого решения.");
        legalDefinitions.add("соглашение двух или более сторон, направленное на установление, изменение или прекращение гражданских прав и обязанностей.");
        legalDefinitions.add("требование истца к ответчику о защите нарушенного или оспариваемого права или законного интереса.");
        legalDefinitions.add("документ, содержащий требование истца к ответчику, который подается в суд для рассмотрения дела.");
        legalDefinitions.add("сведения, на основании которых суд устанавливает наличие или отсутствие обстоятельств, обосновывающих требования и возражения сторон, а также иных обстоятельств, имеющих значение для правильного рассмотрения дела.");
        legalDefinitions.add("сведения, сообщаемые свидетелем суду в установленном законом порядке об известных ему обстоятельствах, имеющих значение для дела.");
        legalDefinitions.add("акт правосудия, которым суд разрешает дело по существу.");
        legalDefinitions.add("обжалование судебного решения, не вступившего в законную силу, в вышестоящий суд.");

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("data/" + fileName))) {
            Random random = new Random();
            int currentSize = 0;
            while (currentSize < fileSize) {
                int termIndex = random.nextInt(legalTerms.size());
                int definitionIndex = random.nextInt(legalDefinitions.size());
                String term = legalTerms.get(termIndex);
                String definition = legalDefinitions.get(definitionIndex);
                writer.write(term + " - " + definition + "\n\n");
                currentSize += (term.length() + definition.length() + 5); // 4 символа для новых строк
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
