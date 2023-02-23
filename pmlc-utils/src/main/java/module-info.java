module dev.pmlc.utils {

    requires dev.pp.basics;
    requires dev.pp.text;
    requires dev.pp.parameters;
    requires dev.pp.datatype;
    requires dev.pp.texttable;
    requires dev.pp.commands;

    requires dev.pdml.shared;
    requires dev.pdml.data;

    requires dev.pmlc.data;
    requires dev.pmlc.parser;
    requires dev.pmlc.converter;

    exports dev.pmlc.utils;
    exports dev.pmlc.utils.nodehandlers;
    exports dev.pmlc.utils.referencemanual;
}
