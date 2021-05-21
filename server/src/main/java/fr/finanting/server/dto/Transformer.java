package fr.finanting.server.dto;

import java.util.List;

public interface Transformer<I,O> {

    O transform(I input);
    List<O> transformAll(List<I> input);

}
