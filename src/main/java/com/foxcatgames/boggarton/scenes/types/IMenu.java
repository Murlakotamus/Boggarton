package com.foxcatgames.boggarton.scenes.types;

public interface IMenu<E extends Enum<E>> extends IName {
    E next();
}
