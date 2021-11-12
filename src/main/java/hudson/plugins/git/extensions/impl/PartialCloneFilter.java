package hudson.plugins.git.extensions.impl;

import hudson.EnvVars;
import hudson.Extension;
import hudson.model.Computer;
import hudson.model.Node;
import hudson.model.Run;
import hudson.model.TaskListener;
import hudson.plugins.git.GitException;
import hudson.plugins.git.GitSCM;
import hudson.plugins.git.extensions.GitClientType;
import hudson.plugins.git.extensions.GitSCMExtension;
import hudson.plugins.git.extensions.GitSCMExtensionDescriptor;
import hudson.plugins.git.util.GitUtils;
import hudson.slaves.NodeProperty;
import java.io.IOException;
import java.util.Objects;
import org.jenkinsci.plugins.gitclient.CloneCommand;
import org.jenkinsci.plugins.gitclient.FetchCommand;
import org.jenkinsci.plugins.gitclient.GitClient;
import org.jenkinsci.plugins.scriptsecurity.sandbox.whitelists.Whitelisted;
import org.kohsuke.stapler.DataBoundConstructor;
import edu.umd.cs.findbugs.annotations.NonNull;
import edu.umd.cs.findbugs.annotations.CheckForNull;

/**
 * @author Yang Sun
 */
public class PartialCloneFilter extends GitSCMExtension {

    private final String partialClonefilter;

    @DataBoundConstructor
    public PartialCloneFilter( String partialClonefilter) {

        this.partialClonefilter = partialClonefilter;
    }

    @Whitelisted
    public String getPartialClonefilter() {
        return partialClonefilter;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void decorateCloneCommand(GitSCM scm, Run<?, ?> build, GitClient git, TaskListener listener, CloneCommand cmd) throws IOException, InterruptedException, GitException {

        Node node = GitUtils.workspaceToNode(git.getWorkTree());

        EnvVars env = build.getEnvironment(listener);
        Computer comp = node.toComputer();
        if (comp != null) {
            env.putAll(comp.getEnvironment());
        }
        for (NodeProperty nodeProperty: node.getNodeProperties()) {
            nodeProperty.buildEnvVars(env, listener);
        }
        // do something in cmd so it is set
        cmd.partialCloneFilter(env.expand(partialClonefilter));
    }

    private static String getParameterString(@CheckForNull String original, @NonNull EnvVars env) {
        return env.expand(original);
    }



    /**
     * {@inheritDoc}
     */
    @Override
    @Deprecated // Deprecate because the super implementation is deprecated
    public void decorateFetchCommand(GitSCM scm, GitClient git, TaskListener listener, FetchCommand cmd) throws IOException, InterruptedException, GitException {

        // cmd.timeout(timeout);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GitClientType getRequiredClient() {
        return GitClientType.GITCLI;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PartialCloneFilter that = (PartialCloneFilter) o;

        return Objects.equals(partialClonefilter, that.partialClonefilter);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return Objects.hash( partialClonefilter);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "PartialCloneFilter{" +
                "partialClonefilter='" + partialClonefilter + '\'' +
                '}';
    }

    @Extension
    public static class DescriptorImpl extends GitSCMExtensionDescriptor {
        /**
         * {@inheritDoc}
         */
        @Override
        public String getDisplayName() {
            return "Partical Clone Filter";
        }
    }

}
