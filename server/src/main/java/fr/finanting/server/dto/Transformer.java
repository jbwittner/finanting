package fr.finanting.server.dto;

import java.util.List;

public abstract class Transformer<I,O> {

    public abstract O transform(I input);
    public abstract List<O> transformAll(List<I> input);

}
