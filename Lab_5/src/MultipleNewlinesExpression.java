public class MultipleNewlinesExpression implements Expression {
    @Override
    public String interpret(String context) {
        return context.replaceAll("\n+", "\n");
    }
}
