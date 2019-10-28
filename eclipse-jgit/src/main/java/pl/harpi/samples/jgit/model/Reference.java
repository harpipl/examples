package pl.harpi.samples.jgit.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Reference {
    private String name;
    private boolean remote;
    private ReferenceType type;
}
