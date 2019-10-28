package pl.harpi.samples.jgit.model.builders;

import org.eclipse.jgit.api.Git;
import pl.harpi.samples.jgit.model.Repository;

public class RepositoryBuilder {
    private Git gitHandle;

    public RepositoryBuilder(Git gitHandle) {
        this.gitHandle = gitHandle;
    }

    public Repository build() {
        return new Repository(gitHandle);
    }
}
