package pl.harpi.samples.jgit.model;

import lombok.Data;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.lib.AnyObjectId;
import org.eclipse.jgit.lib.Ref;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Data
public class Repository {
    private String name;
    private List<Reference> refs;

    public Repository(Git gitHandle) {
        name = gitHandle.getRepository().getDirectory().getAbsolutePath();

        org.eclipse.jgit.lib.Repository repo = gitHandle.getRepository();

        Map<AnyObjectId, Set<Ref>> allRefs = repo.getAllRefsByPeeledObjectId();

        refs = new ArrayList<>();
        for (Map.Entry<AnyObjectId, Set<Ref>> entry : allRefs.entrySet()) {
            for (Ref r : entry.getValue()) {
                Reference ref = null;
                if (r.getName().startsWith("refs/heads") || r.getName().startsWith("HEAD")) {
                    ref = new Reference(r.getName(), false, ReferenceType.TAG);
                } else if (r.getName().startsWith("refs/tags")) {
                    ref = new Reference(r.getName(), false, ReferenceType.BRANCH);
                } else if (r.getName().startsWith("refs/remotes/origin")) {
                    ref = new Reference(r.getName(), true, ReferenceType.BRANCH);
                }

                // System.out.println(r.getName());

                if (ref == null) {
                    System.out.println("WRONG! " + r.getName());
                } else {
                    refs.add(ref);
                }
            }
        }

    }
}
