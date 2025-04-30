public class QuoteReplacementExpression implements Expression {
    @Override
    public String interpret(String context) {
        return context.replace("“", "«").replace("”", "»");
    }
}
