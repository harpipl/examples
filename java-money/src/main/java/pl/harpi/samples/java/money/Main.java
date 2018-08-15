package pl.harpi.samples.java.money;

import org.javamoney.moneta.Money;

import javax.money.CurrencyUnit;
import javax.money.Monetary;
import javax.money.MonetaryAmount;

public class Main {
    public static void main(String[] args) {
        CurrencyUnit usd = Monetary.getCurrency("USD");

        MonetaryAmount m = Monetary.getDefaultAmountFactory()
                .setCurrency(usd)
                .setNumber(200)
                .create();


        Money value1 = Money.of(12.31, usd);

        System.out.println(m);
        System.out.println(value1);

    }
}
