package fr.finanting.server.dto;

public abstract class Transformer<I,O> {

    public abstract O transform(I input);

}
