import java.util.Arrays;
import java.util.List;

public class TextEditor {
    private List<Expression> expressions = Arrays.asList(
            new MultipleSpacesExpression(),
            new HyphenToDashExpression(),
            new QuoteReplacementExpression(),
            new TabFixExpression(),
            new SpaceFixExpression(),
            new MultipleNewlinesExpression()
    );

    public String correctText(String text) {
        for (Expression expr : expressions) {
            text = expr.interpret(text);
        }
        return text;
    }

    public static void main(String[] args) {
        String input = "Привет   ,  warthunder – это “фигня”  (  пример ) ...\n\n\n";
        String output = new TextEditor().correctText(input);
        System.out.println("До:\n" + input);
        System.out.println("После:\n" + output);
    }
}