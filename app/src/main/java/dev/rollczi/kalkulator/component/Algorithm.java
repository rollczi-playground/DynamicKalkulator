package dev.rollczi.kalkulator.component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import dev.rollczi.kalkulator.Operator;
import panda.std.Option;
import panda.std.Pair;
import panda.std.Result;

public class Algorithm implements DigitsAddableComponent {

    protected final List<Calculable> calculables = new ArrayList<>();
    protected Option<DigitsAddableComponent> digitComponentOption = Option.none();

    public Result<Double, String> calculateResult() {
        Result<BigDecimal, String> result = this.getBigDecimalOrError();

        if (result.isErr()) {
            calculables.clear();
            digitComponentOption = Option.none();
            return Result.error(result.getError());
        }

        calculables.clear();
        digitComponentOption = RawNumber.of(result.get().doubleValue())
                .toOption()
                .is(DigitsAddableComponent.class);

        return Result.ok(result.get().doubleValue());
    }

    public void openBrackets() {
        Brackets brackets = digitComponentOption
                .is(Brackets.class)
                .peek(Algorithm::openBrackets)
                .orElseGet(new Brackets());

        digitComponentOption = Option.of(brackets);
    }

    public void closeBrackets() {
        digitComponentOption
                .is(Brackets.class)
                .peek(Algorithm::closeBrackets);
    }

    @Override
    public void setRight(boolean right) {
        digitComponentOption.peek(component -> component.setRight(right));
    }

    @Override
    public String render() {
        StringBuilder builder = new StringBuilder();

        calculables.forEach(calculable -> {
            Operator operator = calculable.getOperator();
            Component component = calculable.getComponent();

            builder.append(component.render());
            builder.append(" ");
            builder.append(operator.getIcon());
            builder.append(" ");
        });

        digitComponentOption.peek(component -> builder.append(component.render()));

        return builder.toString();
    }

    @Override
    public Algorithm appendDigit(int digit) {
        digitComponentOption = digitComponentOption
                .peek(component -> component.appendDigit(digit))
                .orElse(new RawNumber().appendDigit(digit));
        return this;
    }

    public void operator(Operator operator) {
        if (digitComponentOption.isEmpty() && !calculables.isEmpty()) {
            int lastIndex = calculables.size() - 1;
            calculables.get(lastIndex).setOperator(operator);
            return;
        }

        if (!digitComponentOption.isEmpty()) {
            CalculablePiece piece = new CalculablePiece(digitComponentOption.get(), operator);
            calculables.add(piece);
            digitComponentOption = Option.none();
        }
    }

    public Result<BigDecimal, String> getBigDecimalOrError() {
        List<Calculable> copyCalculables = new ArrayList<>(this.calculables);
        DigitsAddableComponent copyEndComponent = digitComponentOption.orElseGet(new RawNumber());

        List<Calculable> cacheCalculables = new ArrayList<>();
        Option<DigitsAddableComponent> cacheEnd = Option.none();

        int buffer = 0, buffer2 = 0;
        for (int index = 0, priority = 2; index < copyCalculables.size(); index++) {
            if (priority == - 1) {
                break;
            }

            if (copyCalculables.isEmpty()) {
                break;
            }

            Calculable calculable = cacheCalculables.size() < 1 ? copyCalculables.get(index + buffer) : cacheCalculables.get(0);

            if (calculable.getOperator().getPriority() != priority) {
                if (copyCalculables.size() - 1 == index) {
                    index = - 1;
                    priority--;
                }

                continue;
            }

            if (copyCalculables.size() - 1 == index + buffer2) {
                Operation operation = new Operation(Pair.of(calculable.getComponent(), cacheEnd.orElseGet(copyEndComponent)), calculable.getOperator());
                Result<RawNumber, String> result = RawNumber.of(operation.getDouble());

                if (result.isErr()) {
                    return Result.error(result.getError());
                }

                cacheEnd = Option.of(result.get());
            }
            else {
                Calculable second = copyCalculables.get(index + 1);
                Operation operation = new Operation(Pair.of(calculable.getComponent(), second.getComponent()), calculable.getOperator());

                cacheCalculables.clear();
                cacheCalculables.add(new CalculablePiece(operation, second.getOperator()));
                buffer++;
            }
            buffer2++;


            if (copyCalculables.size() - 1 == index) {
                index = - 1;
                priority--;
            }
        }

        return Result.ok(cacheEnd.orElseGet(copyEndComponent).getBigDecimal());
    }


    @Override
    public BigDecimal getBigDecimal() {
        return this.getBigDecimalOrError().orElseGet(ignore -> new BigDecimal(- 1));
    }

}
