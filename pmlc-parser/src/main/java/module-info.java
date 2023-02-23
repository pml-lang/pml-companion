module dev.pmlc.parser {

    requires dev.pp.basics;
    requires dev.pp.text;
    requires dev.pp.parameters;
    requires dev.pp.datatype;
    requires dev.pp.scripting;

    requires dev.pdml.data;
    requires dev.pdml.reader;
    requires dev.pdml.parser;
    requires dev.pdml.ext;
    requires dev.pdml.extscripting;
    requires dev.pdml.exttypes;
    requires dev.pdml.utils;

    requires dev.pmlc.data;

    exports dev.pmlc.parser;
}
