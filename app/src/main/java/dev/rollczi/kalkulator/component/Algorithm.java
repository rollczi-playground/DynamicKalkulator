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
                .peek(Brackets::openBrackets)
                .orElseGet(new Brackets());

        digitComponentOption = Option.of(brackets);
    }

    public void closeBrackets() {
        digitComponentOption
                .is(Brackets.class)
                .peek(Brackets::closeBrackets);
    }

    @Override
    public void setRight(boolean right) {
        digitComponentOption.peek(component -> component.setRight(right));
    }

    @Override
    public boolean isRemoved() {
        return this.calculables.isEmpty() && this.digitComponentOption.isEmpty();
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

    public void percent() {
        digitComponentOption = digitComponentOption.map(Percent::new);
    }

    public void operator(Operator operator) {
        if (digitComponentOption.isEmpty() && !calculables.isEmpty()) {
            int lastIndex = calculables.size() - 1;
            calculables.get(lastIndex).setOperator(operator);
            return;
        }

        digitComponentOption
                .is(Brackets.class)
                .filterNot(Brackets::isClosed)
                .peek(brackets -> brackets.operator(operator))
                .onEmpty(() -> {
                    if (!digitComponentOption.isEmpty()) {
                        CalculablePiece piece = new CalculablePiece(digitComponentOption.get(), operator);
                        calculables.add(piece);
                        digitComponentOption = Option.none();
                    }
                });
    }

    public Result<BigDecimal, String> getBigDecimalOrError() {
        List<Calculable> copyCalculables = new ArrayList<>(this.calculables);
        DigitsAddableComponent copyEndComponent = digitComponentOption.orElseGet(new RawNumber());

        for (int index = 0, priority = 2; index < copyCalculables.size(); index++) {
            if (priority == - 1) {
                break;
            }

            if (copyCalculables.isEmpty()) {
                break;
            }

            Calculable calculable = copyCalculables.get(index);

            if (calculable.getOperator().getPriority() != priority) {
                if (copyCalculables.size() - 1 == index) {
                    index = - 1;
                    priority--;
                }

                continue;
            }

            if (copyCalculables.size() - 1 == index) {
                Operation operation = new Operation(Pair.of(calculable.getComponent(), copyEndComponent), calculable.getOperator());
                Result<RawNumber, String> result = operation.calculate()
                        .flatMap(component -> RawNumber.of(component.getDouble()));

                if (result.isErr()) {
                    return Result.error(result.getError());
                }

                copyCalculables.remove(copyCalculables.size() - 1);
                copyEndComponent = result.get();
                index--;
            }
            else {
                Calculable second = copyCalculables.get(index + 1);
                Operation operation = new Operation(Pair.of(calculable.getComponent(), second.getComponent()), calculable.getOperator());
                Result<RawNumber, String> result = operation.calculate()
                        .flatMap(component -> RawNumber.of(component.getDouble()));

                if (result.isErr()) {
                    return Result.error(result.getError());
                }

                CalculablePiece calculablePiece = new CalculablePiece(result.get(), second.getOperator());
                List<Calculable> cache = new ArrayList<>(copyCalculables.subList(0, index));

                cache.add(calculablePiece);
                cache.addAll(copyCalculables.subList(index + 2, copyCalculables.size()));

                copyCalculables.clear();
                copyCalculables.addAll(cache);
            }

            index--;

            if (copyCalculables.size() <= index) {
                index = - 1;
                priority--;
            }
        }

        return Result.ok(copyEndComponent.getBigDecimal());
    }


    @Override
    public BigDecimal getBigDecimal() {
        return this.getBigDecimalOrError().orElseGet(ignore -> new BigDecimal(- 1));
    }

    public List<Calculable> getCalculables() {
        return calculables;
    }

    public Option<DigitsAddableComponent> getDigitComponentOption() {
        return digitComponentOption;
    }

    @Override
    public void removeLastElement() {
        if (digitComponentOption.isEmpty()) {
            if (calculables.isEmpty()) {
                return;
            }

            Calculable removed = calculables.remove(calculables.size() - 1);
            digitComponentOption = Option.of(removed.getComponent());
            return;
        }

        digitComponentOption.peek(DigitsAddableComponent::removeLastElement);
    }
}
