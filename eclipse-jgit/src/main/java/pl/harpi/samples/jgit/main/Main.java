package pl.harpi.samples.jgit.main;

import org.eclipse.jgit.lib.ObjectLoader;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevTree;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;

import java.io.File;
import java.io.IOException;


public class Main {
    private static Repository openRepository(String repositoryPath) throws IOException {
        FileRepositoryBuilder builder = new FileRepositoryBuilder();
        return builder
                .readEnvironment() // scan environment GIT_* variables
                .setGitDir(new File(repositoryPath))
                .build();
    }

    // Git gitHandle = Git.open(new File("D:\\Projects\\java\\github\\commons\\.git")); }
    public static void main(String[] args) throws IOException {
        try (Repository repository = openRepository("d:\\temp\\git\\.git")) {
            // the Ref holds an ObjectId for any type of object (tree, commit, blob, tree)
            Ref head = repository.exactRef("refs/heads/master");



            System.out.println("Ref of refs/heads/master: " + head);

            System.out.println("\nPrint contents of head of master branch, i.e. the latest commit information");
            ObjectLoader loader = repository.open(head.getObjectId());
            loader.copyTo(System.out);

            System.out.println("\nPrint contents of tree of head of master branch, i.e. the latest binary tree information");

            // a commit points to a tree
            try (RevWalk walk = new RevWalk(repository)) {
                RevCommit commit = walk.parseCommit(head.getObjectId());
                RevTree tree = walk.parseTree(commit.getTree().getId());
                System.out.println("Found Tree: " + tree);
                loader = repository.open(tree.getId());
                loader.copyTo(System.out);

                walk.dispose();
            }
        }
    }
}
