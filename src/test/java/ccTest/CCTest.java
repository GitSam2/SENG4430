package ccTest;

import org.example.services.CyclomaticComplexity.CyclomaticComplexity;
import com.github.javaparser.ParseResult;
import com.github.javaparser.ast.CompilationUnit;
import org.example.JavaParserProvider;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import static org.example.Main.parseProject;

public class CCTest {
    @Test
    public void testCC() throws IOException {
        Path projectPath = Path.of("src/main/resources/powsybl-open-loadflow/src");
        JavaParserProvider.initialization(projectPath);
        List<ParseResult<CompilationUnit>> parsedFiles = parseProject();
        CyclomaticComplexity cc = new CyclomaticComplexity();
        cc.caclCyclomaticComplexity(parsedFiles);
    }

}
