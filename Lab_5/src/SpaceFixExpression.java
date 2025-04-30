public class SpaceFixExpression implements Expression {
    @Override
    public String interpret(String context) {
        context = context.replaceAll("(\\()\\s+", "$1");
        context = context.replaceAll("\\s+(\\))", "$1");
        context = context.replaceAll("\\s+([.,])", "$1");
        return context;
    }
}
