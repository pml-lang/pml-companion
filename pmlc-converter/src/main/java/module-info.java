module dev.pmlc.converter {

    requires dev.pp.basics;
    requires dev.pp.text;
    requires dev.pp.texttable;

    requires dev.pdml.utils;

    requires dev.pmlc.data;
    requires dev.pmlc.parser;

    exports dev.pmlc.converter;
    exports dev.pmlc.converter.pmltohtml;
    exports dev.pmlc.converter.pmltohtml.options;
    exports dev.pmlc.converter.pmltohtml.writer;
}
