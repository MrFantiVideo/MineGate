package fr.minegate.util;

import fr.minegate.MineGate;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Normalizer
{

    public static void init()
    {
        File rootDirectory = new File("Ouhuhuhuhu");
        //normalizeJsonFiles(rootDirectory);
        processFiles(rootDirectory);
        MineGate.log("C'est bon ! :D");
    }

    public static void normalizeJsonFiles(File directory)
    {
        Queue<File> queue = new LinkedList<>();
        queue.add(directory);

        while (!queue.isEmpty())
        {
            File file = queue.poll();

            if (file.isDirectory())
            {
                File[] files = file.listFiles();
                if (files != null)
                {
                    queue.addAll(Arrays.asList(files));
                }
            }
            else if (file.isFile() && file.getName().endsWith(".json"))
            {
                try
                {
                    normalizeJsonFile(file);
                } catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void normalizeJsonFile(File jsonFile) throws IOException
    {
        StringBuilder jsonContent = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(jsonFile)))
        {
            String line;
            while ((line = reader.readLine()) != null)
            {
                jsonContent.append(line.trim());
            }
        }

        String normalizedJson = formatJson(jsonContent.toString());

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(jsonFile)))
        {
            writer.write(normalizedJson);
        }
    }

    public static String formatJson(String jsonString)
    {
        StringBuilder result = new StringBuilder();
        int indentLevel = 0;
        boolean inQuotes = false;

        for (char currentChar : jsonString.toCharArray())
        {
            switch (currentChar)
            {
                case '{':
                case '[':
                    result.append(currentChar);
                    if (!inQuotes)
                    {
                        result.append('\n');
                        indentLevel++;
                        result.append(getIndent(indentLevel));
                    }
                    break;
                case '}':
                case ']':
                    if (!inQuotes)
                    {
                        result.append('\n');
                        indentLevel--;
                        result.append(getIndent(indentLevel));
                    }
                    result.append(currentChar);
                    break;
                case '"':
                    result.append(currentChar);
                    inQuotes = !inQuotes;
                    break;
                case ',':
                    result.append(currentChar);
                    if (!inQuotes)
                    {
                        result.append('\n');
                        result.append(getIndent(indentLevel));
                    }
                    break;
                default:
                    result.append(currentChar);
            }
        }

        return result.toString();
    }

    private static String getIndent(int level)
    {
        StringBuilder indent = new StringBuilder();
        for (int i = 0; i < level; i++)
        {
            indent.append("  ");
        }
        return indent.toString();
    }








    private static void processFiles(File folder) {
        File[] files = folder.listFiles();
        if (files == null) {
            return;
        }

        for (File file : files) {
            if (file.isDirectory()) {
                processFiles(file);
            } else if (file.getName().endsWith(".json")) {
                try {
                    modifyJSONFile(file);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static void modifyJSONFile(File jsonFile) throws IOException {
        Path filePath = jsonFile.toPath();
        List<String> lines = Files.readAllLines(filePath, StandardCharsets.UTF_8);

        boolean modified = false;
        for (int i = 0; i < lines.size(); i++) {
            if (lines.get(i).contains("\"credit\": \"MineGate - Tous droits réservés.\",")) {
                lines.remove(i);
                modified = true;
                break;
            }
        }

        if (modified) {
            Files.write(filePath, lines, StandardCharsets.UTF_8);
            System.out.println("Fichier modifié : " + filePath);
        }
    }
}
