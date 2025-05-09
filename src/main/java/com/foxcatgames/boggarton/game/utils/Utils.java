package com.foxcatgames.boggarton.game.utils;

import static com.foxcatgames.boggarton.Const.CURRENT_SET;

import java.util.ArrayList;
import java.util.List;

import com.foxcatgames.boggarton.scenes.types.IName;

public class Utils {

    static public int getBrickType(final int i) {
        return i + CURRENT_SET * 10 + 1;
    }

    static public int random(final int number) {
        return (int) (Math.random() * number);
    }

    static public int getBrick(final int difficulty, final int[] probabilities) {
        int total = 0;
        for (int i = 0; i < difficulty; i++)
            total += probabilities[i];

        final double quantile = 1d / total;
        final double currentPercenthile = Math.random();

        double percenthile = 0;
        for (int i = 0; i < difficulty; i++) {
            percenthile += (quantile * probabilities[i]);
            if (currentPercenthile <= percenthile)
                return getBrickType(i);
        }
        return getBrickType(difficulty);
    }

    static public int parseBrick(final String str, final int index) {
        return parseBrick(str.charAt(index));
    }

    static public int parseBrick(final char c) {
        final int i = c - 64;
        return i + CURRENT_SET * 10;
    }

    public static <E extends Enum<E>> E nextEnumValue(final E currentValue, final Class<E> enumData) {
        if (currentValue.ordinal() >= enumData.getEnumConstants().length - 1)
            return enumData.getEnumConstants()[0];

        return enumData.getEnumConstants()[currentValue.ordinal() + 1];
    }

    public static <E extends Enum<E> & IName> String[] getNames(final Class<E> enumData) {
        final List<String> list = new ArrayList<>();
        for (final E enumVal : enumData.getEnumConstants())
            if (enumVal.getName() != null)
                list.add(enumVal.getName());

        final String[] result = new String[list.size()];
        return list.toArray(result);
    }

    public static <E extends Enum<E>> E getValue(final Class<E> enumType, final int position) {
        return enumType.getEnumConstants()[position];
    }
}
