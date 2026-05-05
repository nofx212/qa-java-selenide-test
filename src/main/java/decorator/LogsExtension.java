package decorator;

import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.BeforeTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

public class LogsExtension implements BeforeTestExecutionCallback, AfterTestExecutionCallback {

    private static final ThreadLocal<String> TEST_NAME = new ThreadLocal<>();
    private static final ThreadLocal<String> PROJECT_NAME = new ThreadLocal<>();
    private static final ThreadLocal<Boolean> SUPPRESS_PREFIX = new ThreadLocal<>();
    private static final AtomicBoolean INSTALLED = new AtomicBoolean(false);

    public static void installSystemOutOnce() {
        if (INSTALLED.compareAndSet(false, true)) {
            System.setOut(new PrefixedPrintStream(System.out));
            System.setErr(new PrefixedPrintStream(System.err));
        }
    }

    public static void setTestName(String testName) {
        TEST_NAME.set(testName);
    }

    public static void setProjectName(String projectName) {
        PROJECT_NAME.set(projectName);
    }

    public static void clear() {
        TEST_NAME.remove();
    }

    public static String buildTestName(TestInfo testInfo) {
        String methodName = testInfo.getTestMethod()
                .map(method -> method.getName())
                .orElse("unknownTest");
        String displayName = testInfo.getDisplayName();
        String label = stripIndex(displayName);
        if (label.isEmpty() || isDefaultDisplayName(label, methodName)) {
            return methodName;
        }
        return methodName + ". " + label;
    }

    public static String buildProjectName(TestInfo testInfo) {
        if (testInfo.getTags().isEmpty()) {
            return "";
        }
        String projectTag = selectProjectTag(testInfo.getTags());
        return projectTag == null ? "" : projectTag;
    }

    @Override
    public void beforeTestExecution(ExtensionContext context) {
        String methodName = context.getRequiredTestMethod().getName();
        String displayName = context.getDisplayName();
        runWithoutPrefix(() -> System.out.println(
                "------------------------------------------------------------------------------------------------------"
        ));
        runWithoutPrefix(() -> System.out.println(
                "Test: " + buildTestDisplayLabel(methodName, displayName) + ". Started successful on " + context.getTags()
        ));
        runWithoutPrefix(() -> System.out.println(
                "------------------------------------------------------------------------------------------------------"
        ));
    }

    @Override
    public void afterTestExecution(ExtensionContext context) {
        String methodName = context.getRequiredTestMethod().getName();
        String displayName = context.getDisplayName();
        runWithoutPrefix(() -> System.out.println(
                "------------------------------------------------------------------------------------------------------"
        ));
        runWithoutPrefix(() -> System.out.println(
                "Test: " + buildTestDisplayLabel(methodName, displayName) + ". Finished on " + context.getTags()
        ));
        runWithoutPrefix(() -> System.out.println(
                "------------------------------------------------------------------------------------------------------"
        ));
    }

    private static String prefix() {
        if (Boolean.TRUE.equals(SUPPRESS_PREFIX.get())) {
            return "";
        }
        String name = Optional.ofNullable(TEST_NAME.get()).orElse("unknown-test");
        String project = Optional.ofNullable(PROJECT_NAME.get()).orElse("");
        if (project.isEmpty()) {
            return "[" + name + "] ";
        }
        return "[" + name + "] [" + project + "] ";
    }

    private static void runWithoutPrefix(Runnable action) {
        Boolean previous = SUPPRESS_PREFIX.get();
        SUPPRESS_PREFIX.set(true);
        try {
            action.run();
        } finally {
            if (previous == null) {
                SUPPRESS_PREFIX.remove();
            } else {
                SUPPRESS_PREFIX.set(previous);
            }
        }
    }

    private static String buildTestDisplayLabel(String methodName, String displayName) {
        String label = stripIndex(displayName);
        if (label.isEmpty() || isDefaultDisplayName(label, methodName)) {
            return methodName;
        }
        return methodName + ". " + label;
    }

    private static boolean isDefaultDisplayName(String label, String methodName) {
        return label.equals(methodName) || label.equals(methodName + "()");
    }

    private static String selectProjectTag(java.util.Set<String> tags) {
        if (tags.contains("epicvin")) {
            return "epicvin";
        }
        if (tags.contains("vininspect")) {
            return "vininspect";
        }
        if (tags.contains("vingurus")) {
            return "vingurus";
        }
        if (tags.contains("epicvinUK")) {
            return "epicvinUK";
        }
        if (tags.contains("scaLive")) {
            return "scaLive";
        }
        if (tags.contains("sca")) {
            return "sca";
        }
        if (tags.contains("parsers")) {
            return "parsers";
        }
        return tags.iterator().next();
    }

    private static String extractIndex(String displayName) {
        if (displayName == null) {
            return "";
        }
        String trimmed = displayName.trim();
        int dotIndex = trimmed.indexOf('.');
        if (dotIndex <= 0) {
            return "";
        }
        String prefix = trimmed.substring(0, dotIndex + 1);
        if (isIndexPrefix(prefix)) {
            return prefix;
        }
        return "";
    }

    private static boolean isIndexPrefix(String prefix) {
        String value = prefix.trim();
        if (value.startsWith("#")) {
            value = value.substring(1);
        }
        if (!value.endsWith(".")) {
            return false;
        }
        String digits = value.substring(0, value.length() - 1);
        if (digits.isEmpty()) {
            return false;
        }
        for (int i = 0; i < digits.length(); i++) {
            if (!Character.isDigit(digits.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    private static String stripIndex(String displayName) {
        if (displayName == null) {
            return "";
        }
        String index = extractIndex(displayName);
        String trimmed = displayName.trim();
        if (index.isEmpty()) {
            return trimmed;
        }
        return trimmed.substring(index.length()).trim();
    }


    private static class PrefixedPrintStream extends PrintStream {
        PrefixedPrintStream(OutputStream original) {
            super(new PrefixingOutputStream(original), true);
        }
    }

    private static class PrefixingOutputStream extends OutputStream {
        private final OutputStream delegate;
        private final ByteArrayOutputStream lineBuffer = new ByteArrayOutputStream();
        private boolean pendingTestHeader = false;

        PrefixingOutputStream(OutputStream delegate) {
            this.delegate = delegate;
        }

        @Override
        public void write(int b) throws IOException {
            lineBuffer.write(b);
            if (b == '\n') {
                flushLine();
            }
        }

        @Override
        public void write(byte[] b, int off, int len) throws IOException {
            for (int i = off; i < off + len; i++) {
                write(b[i]);
            }
        }

        private void flushLine() throws IOException {
            byte[] lineBytes = lineBuffer.toByteArray();
            lineBuffer.reset();
            String lineText = new String(lineBytes);
            String lineWithoutNewline = stripLineBreak(lineText);
            if (isSimpleReportHeader(lineWithoutNewline)) {
                pendingTestHeader = true;
                return;
            }
            if (pendingTestHeader) {
                writeTableTestHeader();
                pendingTestHeader = false;
            }
            if (shouldSuppressPrefix(lineWithoutNewline)) {
                delegate.write(lineBytes);
                return;
            }
            byte[] prefixBytes = prefix().getBytes();
            delegate.write(prefixBytes);
            delegate.write(lineBytes);
        }

        private String stripLineBreak(String line) {
            if (line.endsWith("\r\n")) {
                return line.substring(0, line.length() - 2);
            }
            if (line.endsWith("\n")) {
                return line.substring(0, line.length() - 1);
            }
            return line;
        }

        private boolean shouldSuppressPrefix(String line) {
            if (line.isEmpty()) {
                return true;
            }
            if (pendingTestHeader && line.startsWith("+")) {
                return true;
            }
            if (line.startsWith("+") || line.startsWith("|")) {
                return true;
            }
            if (line.startsWith("Report for ")) {
                return true;
            }
            return line.contains("SimpleReport - Report for");
        }

        private boolean isSimpleReportHeader(String line) {
            return line.contains("SimpleReport - Report for");
        }

        private void writeTableTestHeader() throws IOException {
            String testName = Optional.ofNullable(TEST_NAME.get()).orElse("unknown-test");
            String header = "Table with steps for test " + testName + System.lineSeparator();
            delegate.write(header.getBytes());
        }
    }
}
