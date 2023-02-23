module dev.pmlc.data {

    requires dev.pp.basics;
    requires dev.pp.text;
    requires dev.pp.parameters;
    requires dev.pp.datatype;

    requires dev.pdml.shared;
    requires dev.pdml.data;
    requires dev.pdml.parser;
    requires dev.pdml.exttypes;

    exports dev.pmlc.data;
    exports dev.pmlc.data.nodespec;
    exports dev.pmlc.data.nodespec.block;
    exports dev.pmlc.data.nodespec.block.chapter;
    exports dev.pmlc.data.nodespec.block.code;
    exports dev.pmlc.data.nodespec.block.footnote;
    exports dev.pmlc.data.nodespec.block.list;
    exports dev.pmlc.data.nodespec.block.media;
    exports dev.pmlc.data.nodespec.block.table;
    exports dev.pmlc.data.nodespec.inline;
    exports dev.pmlc.data.nodespec.inline.font;
    exports dev.pmlc.data.nodespec.inline.footnote;
    exports dev.pmlc.data.node;
    exports dev.pmlc.data.node.block;
    exports dev.pmlc.data.node.block.chapter;
    exports dev.pmlc.data.node.block.code;
    exports dev.pmlc.data.node.block.footnote;
    exports dev.pmlc.data.node.block.list;
    exports dev.pmlc.data.node.block.media;
    exports dev.pmlc.data.node.block.table;
    exports dev.pmlc.data.node.handler;
    exports dev.pmlc.data.node.inline;
    exports dev.pmlc.data.node.inline.font;
    exports dev.pmlc.data.node.inline.footnote;
    exports dev.pmlc.data.node.validator;
    exports dev.pmlc.data.node.block.quote;
    exports dev.pmlc.data.nodespec.block.quote;
    exports dev.pmlc.data.nodespec.block.admonition;
    exports dev.pmlc.data.node.block.admonition;
}
