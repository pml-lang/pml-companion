module dev.pmlc.ext {

    requires java.logging;

    requires dev.pp.basics;
    requires dev.pp.text;
    requires dev.pp.texttable;
    requires dev.pp.parameters;
    requires dev.pp.commands;
    requires dev.pp.datatype;

    requires dev.pdml.core;
    requires dev.pdml.ext;

    requires dev.pmlc.core;

    exports dev.pmlc.ext;
    exports dev.pmlc.ext.nodeHandlers.impls;
    exports dev.pmlc.ext.utilities;
    exports dev.pmlc.ext.utilities.pmltohtml;
    exports dev.pmlc.ext.utilities.pmltohtml.options;
    exports dev.pmlc.ext.utilities.pmltohtml.writer;
    exports dev.pmlc.ext.utilities.referencemanual;
}
