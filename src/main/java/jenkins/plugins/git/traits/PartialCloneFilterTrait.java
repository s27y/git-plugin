package jenkins.plugins.git.traits;

import hudson.Extension;
import hudson.plugins.git.extensions.impl.PartialCloneFilter;
import jenkins.scm.api.trait.SCMSourceTrait;
import org.kohsuke.stapler.DataBoundConstructor;

/**
 * Exposes {@link PartialCloneFilterTrait} as a {@link SCMSourceTrait}.
 *
 * @since 4.0.1
 */
public class PartialCloneFilterTrait extends GitSCMExtensionTrait<PartialCloneFilter> {
    /**
     * Stapler constructor.
     *
     * @param extension the {@link SparseCheckoutPaths}
     */
    @DataBoundConstructor
    public PartialCloneFilterTrait(PartialCloneFilter extension) {
        super(extension);
    }

    /**
     * Our {@link hudson.model.Descriptor}
     */
    @Extension
    public static class DescriptorImpl extends GitSCMExtensionTraitDescriptor {
        /**
         * {@inheritDoc}
         */
        @Override
        public String getDisplayName() {
            return "Partial Clone Filter";
        }
    }
}