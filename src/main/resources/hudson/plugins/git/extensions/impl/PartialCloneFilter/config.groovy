package hudson.plugins.git.extensions.impl.PartialCloneFilter

def f = namespace(lib.FormTagLib)

f.entry(title:_("Partial Clone Filter"), field:"partialClonefilter") {
    f.textbox()
}