package com.leetcode.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.*;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

/**
 * Simple Problem Generator - Clean Version
 */
public class SimpleProblemGenerator {

    private static final String RESOURCES_PATH = "src/main/resources/problems/";
    private static final String TYPES_PATH = "src/main/resources/metadata/types.yml";
    private static final String JAVA_SRC_PATH = "src/main/java/com/leetcode/problems/";
    private static final String TEST_PATH = "src/test/java/com/leetcode/problems/";
    private static final String DOCS_PATH = "docs/practice/";

    private static final String PROTECTED_START = "// ========== PROTECTED CODE START ==========";
    private static final String PROTECTED_END = "// ========== PROTECTED CODE END ==========";
    private static final String TEMPLATE_MARKER = "# ==================== TEMPLATE ====================";

    private final ObjectMapper yamlMapper;
    private Map<String, TypeInfo> typeMetadata;

    public SimpleProblemGenerator() {
        this.yamlMapper = new ObjectMapper(new YAMLFactory());
        loadTypes();
    }

    private void loadTypes() {
        try {
            File typesFile = new File(TYPES_PATH);
            if (typesFile.exists()) {
                TypesConfig config = yamlMapper.readValue(typesFile, TypesConfig.class);
                this.typeMetadata = config.getTypes();
                System.out.println("✅ Loaded type metadata");
            } else {
                this.typeMetadata = new HashMap<>();
                System.out.println("⚠️  No types.yml found, using defaults");
            }
        } catch (Exception e) {
            this.typeMetadata = new HashMap<>();
            System.err.println("❌ Error loading types: " + e.getMessage());
        }
    }

    public void generateProblems() {
        try {
            System.out.println("🚀 Starting Problem Generation...");
            Files.createDirectories(Paths.get(RESOURCES_PATH));

            for (Difficulty difficulty : Difficulty.values()) {
                String configFile = RESOURCES_PATH + difficulty.name().toLowerCase() + ".yml";

                if (!Files.exists(Paths.get(configFile))) {
                    createEmptyYamlFile(configFile);
                    System.out.println("📝 Created: " + configFile);
                } else {
                    processExistingFile(configFile, difficulty);
                    // updateYamlFile is called inside processExistingFile now
                }
            }

            System.out.println("✅ Generation completed!");

        } catch (Exception e) {
            System.err.println("❌ Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void createEmptyYamlFile(String filePath) throws IOException {
        String content = "problems:\n  # Add your problems here\n\n" + getTemplate();
        Files.write(Paths.get(filePath), content.getBytes());
    }

    private void processExistingFile(String filePath, Difficulty difficulty) throws Exception {
        System.out.println("📁 Processing: " + filePath);

        try {
            String content = Files.readString(Paths.get(filePath));

            // Parse entire file including template
            ProblemConfig config = yamlMapper.readValue(new File(filePath), ProblemConfig.class);

            if (config.getProblems() != null) {
                List<Problem> realProblems = new ArrayList<>();
                Problem templateProblem = null;

                // Separate real problems from template
                for (Problem problem : config.getProblems()) {
                    if (problem.getNumber() == 99999) {
                        templateProblem = problem;
                    } else {
                        realProblems.add(problem);
                    }
                }

                // Check if template was edited (has real problem number)
                if (templateProblem != null && hasRealProblemData(templateProblem)) {
                    System.out.println("  🎯 Detected edited template - converting to real problem");
                    realProblems.add(templateProblem);
                    templateProblem = null; // Will create new template
                }

                // Generate files for all real problems
                for (Problem problem : realProblems) {
                    generateFiles(problem, difficulty);
                    System.out.println("  ✅ Generated: " + problem.getTitle());
                }

                // Update YAML file with real problems + fresh template
                updateYamlFile(filePath, realProblems);
            }
        } catch (Exception e) {
            System.err.println("  ⚠️  Error processing file: " + e.getMessage());
        }
    }

    private boolean hasRealProblemData(Problem problem) {
        // Check if template has been edited with real data
        return problem.getNumber() != 99999 ||
                (problem.getTitle() != null && !problem.getTitle().equals("Problem Title")) ||
                (problem.getDescription() != null && !problem.getDescription().equals("Problem Description"));
    }

    private void updateYamlFile(String filePath, List<Problem> realProblems) throws IOException {
        StringBuilder sb = new StringBuilder();

        sb.append("problems:\n");
        if (realProblems.isEmpty()) {
            sb.append("  # Add your problems here\n");
        } else {
            sb.append("  # Your problems:\n");
            for (Problem problem : realProblems) {
                sb.append("  - number: ").append(problem.getNumber()).append("\n");
                sb.append("    title: \"").append(problem.getTitle()).append("\"\n");
                sb.append("    description: \"").append(problem.getDescription()).append("\"\n");
                sb.append("    returnType: \"").append(problem.getReturnType()).append("\"\n");
                sb.append("    parameters:\n");
                if (problem.getParameters() != null) {
                    for (Parameter param : problem.getParameters()) {
                        sb.append("      - name: \"").append(param.getName()).append("\"\n");
                        sb.append("        type: \"").append(param.getType()).append("\"\n");
                    }
                }
                sb.append("\n");
            }
        }

        sb.append("\n").append(getTemplate());

        Files.write(Paths.get(filePath), sb.toString().getBytes());
        System.out.println("  📝 Updated YAML with fresh template");
    }

    private void generateFiles(Problem problem, Difficulty difficulty) throws IOException {
        Problem oldProblem = findExistingProblemByNumber(problem.getNumber(), difficulty);
        enrichProblem(problem, difficulty);

        // Handle renamed files if metadata changed
        if (oldProblem != null && !oldProblem.getClassName().equals(problem.getClassName())) {
            handleRenamedFiles(oldProblem, problem, difficulty);
        }

        generateJavaFile(problem, difficulty);
        generateTestFile(problem, difficulty);
        generateMarkdownFile(problem, difficulty);
    }

    private Problem findExistingProblemByNumber(int number, Difficulty difficulty) {
        try {
            String packagePath = JAVA_SRC_PATH + difficulty.name().toLowerCase() + "/";
            File dir = new File(packagePath);
            if (!dir.exists()) return null;

            File[] files = dir.listFiles((d, name) -> name.endsWith("_" + number + ".java"));
            if (files != null && files.length > 0) {
                String fileName = files[0].getName();
                String className = fileName.substring(0, fileName.lastIndexOf(".java"));
                Problem existing = new Problem();
                existing.setNumber(number);
                existing.setClassName(className);

                // Extract title from className
                String title = className.substring(0, className.lastIndexOf("_" + number));
                // Convert PascalCase to Title Case
                title = title.replaceAll("([A-Z])", " $1").trim();
                existing.setTitle(title);
                existing.setId(title.toLowerCase().replaceAll("\\s+", "-"));

                return existing;
            }
        } catch (Exception e) {
            // Ignore errors, continue with normal flow
        }
        return null;
    }

    private void handleRenamedFiles(Problem oldProblem, Problem newProblem, Difficulty difficulty) throws IOException {
        System.out.println("  🔄 Detected metadata change for problem " + newProblem.getNumber());
        System.out.println("    📝 Title: \"" + oldProblem.getTitle() + "\" → \"" + newProblem.getTitle() + "\"");

        String packagePath = JAVA_SRC_PATH + difficulty.name().toLowerCase() + "/";
        String testPackagePath = TEST_PATH + difficulty.name().toLowerCase() + "/";
        String docsPath = DOCS_PATH + difficulty.name().toLowerCase() + "/";

        // Old file paths
        String oldJavaFile = packagePath + oldProblem.getClassName() + ".java";
        String oldTestFile = testPackagePath + oldProblem.getClassName() + "_Test.java";
        String oldMdFile = docsPath + oldProblem.getId() + ".md";

        // Extract protected code from old files before renaming
        String protectedCode = "";
        if (Files.exists(Paths.get(oldJavaFile))) {
            protectedCode = extractProtectedCode(oldJavaFile);
            if (!protectedCode.isEmpty()) {
                System.out.println("    🛡️  Preserving implementation from old file");
            }
        }

        // Backup old files
        if (Files.exists(Paths.get(oldJavaFile))) {
            backupFile(oldJavaFile, oldProblem);
            Files.delete(Paths.get(oldJavaFile));
            System.out.println("    🗑️  Removed old Java file: " + oldProblem.getClassName() + ".java");
        }

        if (Files.exists(Paths.get(oldTestFile))) {
            backupFile(oldTestFile, oldProblem);
            Files.delete(Paths.get(oldTestFile));
            System.out.println("    🗑️  Removed old test file: " + oldProblem.getClassName() + "_Test.java");
        }

        if (Files.exists(Paths.get(oldMdFile))) {
            backupFile(oldMdFile, oldProblem);
            Files.delete(Paths.get(oldMdFile));
            System.out.println("    🗑️  Removed old documentation: " + oldProblem.getId() + ".md");
        }

        // Store preserved code for use in new file generation
        newProblem.setPreservedCode(protectedCode);
    }

    private void enrichProblem(Problem problem, Difficulty difficulty) {
        if (problem.getId() == null) {
            problem.setId(problem.getTitle().toLowerCase().replaceAll("[^a-zA-Z0-9\\s]", "").replaceAll("\\s+", "-"));
        }
        if (problem.getClassName() == null) {
            String name = problem.getTitle().replaceAll("[^a-zA-Z0-9\\s]", "").replaceAll("\\s+", " ").trim();
            String[] words = name.split("\\s+");
            StringBuilder className = new StringBuilder();
            for (String word : words) {
                if (!word.isEmpty()) {
                    className.append(word.substring(0, 1).toUpperCase()).append(word.substring(1).toLowerCase());
                }
            }
            problem.setClassName(className.toString() + "_" + problem.getNumber());
        }
        problem.setDifficulty(difficulty.name());
    }

    private void generateJavaFile(Problem problem, Difficulty difficulty) throws IOException {
        String packagePath = JAVA_SRC_PATH + difficulty.name().toLowerCase() + "/";
        String fileName = problem.getClassName() + ".java";
        String filePath = packagePath + fileName;

        Files.createDirectories(Paths.get(packagePath));

        String protectedCode = "";

        // First check if we have preserved code from rename operation
        if (problem.getPreservedCode() != null && !problem.getPreservedCode().isEmpty()) {
            protectedCode = problem.getPreservedCode();
            System.out.println("    🔄 Using preserved code from renamed file");
        } else if (Files.exists(Paths.get(filePath))) {
            // Extract from existing file with same name
            protectedCode = extractProtectedCode(filePath);
            if (containsManualCode(filePath) && protectedCode.isEmpty()) {
                backupFile(filePath, problem);
                System.out.println("    💾 Backed up manual implementation");
            } else if (!protectedCode.isEmpty()) {
                System.out.println("    🛡️  Preserved existing implementation");
            }
        }

        String content = generateJavaContent(problem, difficulty, protectedCode);
        Files.write(Paths.get(filePath), content.getBytes());

        if (Files.exists(Paths.get(filePath))) {
            System.out.println("    ✨ Created: " + fileName);
        }
    }

    private void generateTestFile(Problem problem, Difficulty difficulty) throws IOException {
        String packagePath = TEST_PATH + difficulty.name().toLowerCase() + "/";
        String fileName = problem.getClassName() + "_Test.java";
        String filePath = packagePath + fileName;

        Files.createDirectories(Paths.get(packagePath));

        if (Files.exists(Paths.get(filePath)) && containsCustomTests(filePath)) {
            backupFile(filePath, problem);
        }

        String content = generateTestContent(problem, difficulty);
        Files.write(Paths.get(filePath), content.getBytes());
    }

    private void generateMarkdownFile(Problem problem, Difficulty difficulty) throws IOException {
        String dirPath = DOCS_PATH + difficulty.name().toLowerCase() + "/";
        String fileName = problem.getId() + ".md";
        String filePath = dirPath + fileName;

        Files.createDirectories(Paths.get(dirPath));

        StringBuilder sb = new StringBuilder();
        sb.append("# ").append(problem.getNumber()).append(". ").append(problem.getTitle()).append("\n\n");
        sb.append("**Difficulty:** ").append(difficulty.name()).append("\n\n");
        sb.append("## Description\n\n").append(problem.getDescription()).append("\n\n");
        sb.append("## Method Signature\n\n```java\npublic ").append(problem.getReturnType()).append(" solve(");

        StringJoiner params = new StringJoiner(", ");
        for (Parameter param : problem.getParameters()) {
            params.add(param.getType() + " " + param.getName());
        }
        sb.append(params.toString()).append(")\n```\n\n## Solution\n\nTODO: Add solution explanation\n");

        Files.write(Paths.get(filePath), sb.toString().getBytes());
    }

    private String generateJavaContent(Problem problem, Difficulty difficulty, String protectedCode) {
        StringBuilder sb = new StringBuilder();

        sb.append("package com.leetcode.problems.").append(difficulty.name().toLowerCase()).append(";\n\n");
        sb.append("import java.util.*;\n");

        // Add type-specific imports
        Set<String> imports = getImports(problem);
        for (String imp : imports) {
            sb.append("import ").append(imp).append(";\n");
        }
        sb.append("\n");

        sb.append("/**\n * LeetCode ").append(problem.getNumber()).append(": ").append(problem.getTitle());
        sb.append("\n * \n * ").append(problem.getDescription()).append("\n */\n");
        sb.append("public class ").append(problem.getClassName()).append(" {\n\n");

        sb.append("    public ").append(problem.getReturnType()).append(" solve(");
        StringJoiner params = new StringJoiner(", ");
        for (Parameter param : problem.getParameters()) {
            params.add(param.getType() + " " + param.getName());
        }
        sb.append(params.toString()).append(") {\n");

        sb.append("        ").append(PROTECTED_START).append("\n");
        if (!protectedCode.isEmpty()) {
            sb.append(protectedCode);
        } else {
            sb.append("        // TODO: Implement solution\n");
            sb.append("        throw new UnsupportedOperationException(\"Not implemented yet\");\n");
        }
        sb.append("        ").append(PROTECTED_END).append("\n");
        sb.append("    }\n\n}\n");

        return sb.toString();
    }

    private String generateTestContent(Problem problem, Difficulty difficulty) {
        StringBuilder sb = new StringBuilder();

        sb.append("package com.leetcode.problems.").append(difficulty.name().toLowerCase()).append(";\n\n");
        sb.append("import org.junit.jupiter.api.*;\n");
        sb.append("import org.junit.jupiter.params.ParameterizedTest;\n");
        sb.append("import org.junit.jupiter.params.provider.*;\n");
        sb.append("import static org.junit.jupiter.api.Assertions.*;\n");
        sb.append("import java.util.*;\n");
        sb.append("import java.util.stream.Stream;\n");

        Set<String> imports = getImports(problem);
        for (String imp : imports) {
            sb.append("import ").append(imp).append(";\n");
        }
        sb.append("\n");

        sb.append("@DisplayName(\"LC ").append(problem.getNumber()).append(": ").append(problem.getTitle()).append("\")\n");
        sb.append("class ").append(problem.getClassName()).append("_Test {\n\n");
        sb.append("    private ").append(problem.getClassName()).append(" solution;\n\n");
        sb.append("    @BeforeEach\n    void setUp() {\n        solution = new ").append(problem.getClassName()).append("();\n    }\n\n");

        // Basic test
        sb.append("    @Test\n    @DisplayName(\"Basic functionality test\")\n    void testBasic() {\n");
        sb.append("        // TODO: Add test cases\n");
        sb.append("        // Example: ").append(getAssertionMethod(problem.getReturnType())).append("(expected, solution.solve(");
        StringJoiner sampleArgs = new StringJoiner(", ");
        for (Parameter param : problem.getParameters()) {
            sampleArgs.add(getSampleValue(param.getType()));
        }
        sb.append(sampleArgs.toString()).append("));\n    }\n\n");

        // Edge cases test
        sb.append("    @Test\n    @DisplayName(\"Edge cases test\")\n    void testEdgeCases() {\n");
        sb.append("        // Test edge cases for types:\n");
        for (Parameter param : problem.getParameters()) {
            TypeInfo typeInfo = typeMetadata.get(param.getType());
            if (typeInfo != null && typeInfo.getTestCases() != null) {
                sb.append("        // ").append(param.getName()).append(" (").append(param.getType()).append("): ");
                sb.append(String.join(", ", typeInfo.getTestCases())).append("\n");
            }
        }
        sb.append("    }\n\n");

        sb.append("}\n");
        return sb.toString();
    }

    // Helper methods
    private String removeTemplate(String content) {
        int templateStart = content.indexOf(TEMPLATE_MARKER);
        return templateStart != -1 ? content.substring(0, templateStart).trim() : content.trim();
    }

    private void appendTemplate(String filePath) throws IOException {
        String content = Files.readString(Paths.get(filePath));
        String contentWithoutTemplate = removeTemplate(content);
        String newContent = contentWithoutTemplate + "\n\n" + getTemplate();
        Files.write(Paths.get(filePath), newContent.getBytes());
    }

    private String getTemplate() {
        return TEMPLATE_MARKER + "\n" +
                "  - number: 99999\n" +
                "    title: \"Problem Title\"\n" +
                "    description: \"Problem Description\"\n" +
                "    returnType: \"int\"\n" +
                "    parameters:\n" +
                "      - name: \"param1\"\n" +
                "        type: \"int[]\"\n" +
                "      - name: \"param2\"\n" +
                "        type: \"int\"\n";
    }

    private String extractProtectedCode(String filePath) throws IOException {
        String content = Files.readString(Paths.get(filePath));
        Pattern pattern = Pattern.compile(Pattern.quote(PROTECTED_START) + "(.*?)" + Pattern.quote(PROTECTED_END), Pattern.DOTALL);
        Matcher matcher = pattern.matcher(content);
        return matcher.find() ? matcher.group(1) : "";
    }

    private boolean containsManualCode(String filePath) throws IOException {
        String content = Files.readString(Paths.get(filePath));
        return content.contains("return ") && !content.contains("throw new UnsupportedOperationException") && !content.contains("TODO: Implement");
    }

    private boolean containsCustomTests(String filePath) throws IOException {
        String content = Files.readString(Paths.get(filePath));
        return content.contains("@Test") && !content.contains("TODO: Add test case") &&
                (content.contains("assertEquals(") || content.contains("assertTrue(") || content.contains("assertArrayEquals("));
    }

    private void backupFile(String filePath, Problem problem) throws IOException {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        String backupDir = ".backups/" + problem.getClassName() + "_" + timestamp + "/";
        Files.createDirectories(Paths.get(backupDir));
        String fileName = Paths.get(filePath).getFileName().toString();
        Files.copy(Paths.get(filePath), Paths.get(backupDir + fileName));
        System.out.println("    💾 Backup created: " + backupDir + fileName);
    }

    private Set<String> getImports(Problem problem) {
        Set<String> imports = new HashSet<>();
        addImportsForType(imports, problem.getReturnType());
        for (Parameter param : problem.getParameters()) {
            addImportsForType(imports, param.getType());
        }
        return imports;
    }

    private void addImportsForType(Set<String> imports, String type) {
        TypeInfo typeInfo = typeMetadata.get(type);
        if (typeInfo != null && typeInfo.getImports() != null) {
            imports.addAll(typeInfo.getImports());
        }
    }

    private String getAssertionMethod(String type) {
        TypeInfo typeInfo = typeMetadata.get(type);
        return typeInfo != null ? typeInfo.getAssertion() : "assertEquals";
    }

    private String getSampleValue(String type) {
        TypeInfo typeInfo = typeMetadata.get(type);
        if (typeInfo != null && typeInfo.getTestCases() != null && !typeInfo.getTestCases().isEmpty()) {
            return typeInfo.getTestCases().get(0);
        }
        switch (type) {
            case "int": return "1";
            case "boolean": return "true";
            case "String": return "\"test\"";
            default: return "null";
        }
    }

    // Data classes
    public static class TypesConfig {
        private Map<String, TypeInfo> types;
        public Map<String, TypeInfo> getTypes() { return types; }
        public void setTypes(Map<String, TypeInfo> types) { this.types = types; }
    }

    public static class TypeInfo {
        private List<String> testCases;
        private String assertion;
        private List<String> imports;

        public List<String> getTestCases() { return testCases; }
        public void setTestCases(List<String> testCases) { this.testCases = testCases; }
        public String getAssertion() { return assertion; }
        public void setAssertion(String assertion) { this.assertion = assertion; }
        public List<String> getImports() { return imports; }
        public void setImports(List<String> imports) { this.imports = imports; }
    }

    public static class ProblemConfig {
        @JsonProperty("problems")
        private List<Problem> problems;
        public List<Problem> getProblems() { return problems; }
        public void setProblems(List<Problem> problems) { this.problems = problems; }
    }

    public static class Problem {
        private int number;
        private String title;
        private String description;
        private String returnType;
        private List<Parameter> parameters;
        private String id;
        private String className;
        private String difficulty;
        private String preservedCode; // For handling renames

        // Getters and setters
        public int getNumber() { return number; }
        public void setNumber(int number) { this.number = number; }
        public String getTitle() { return title; }
        public void setTitle(String title) { this.title = title; }
        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }
        public String getReturnType() { return returnType; }
        public void setReturnType(String returnType) { this.returnType = returnType; }
        public List<Parameter> getParameters() { return parameters; }
        public void setParameters(List<Parameter> parameters) { this.parameters = parameters; }
        public String getId() { return id; }
        public void setId(String id) { this.id = id; }
        public String getClassName() { return className; }
        public void setClassName(String className) { this.className = className; }
        public String getDifficulty() { return difficulty; }
        public void setDifficulty(String difficulty) { this.difficulty = difficulty; }
        public String getPreservedCode() { return preservedCode; }
        public void setPreservedCode(String preservedCode) { this.preservedCode = preservedCode; }
    }

    public static class Parameter {
        private String name;
        private String type;

        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public String getType() { return type; }
        public void setType(String type) { this.type = type; }
    }

    public enum Difficulty {
        EASY, MEDIUM, HARD
    }

    public static void main(String[] args) {
        SimpleProblemGenerator generator = new SimpleProblemGenerator();
        generator.generateProblems();
    }
}