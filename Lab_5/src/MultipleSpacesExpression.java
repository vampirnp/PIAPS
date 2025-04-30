public class MultipleSpacesExpression implements Expression {
    @Override
    public String interpret(String context) {
        return context.replaceAll("\\s+", " ");
    }
}

