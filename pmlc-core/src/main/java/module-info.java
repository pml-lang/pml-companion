module dev.pmlc.core {

    requires dev.pp.basics;
    requires dev.pp.text;
    requires dev.pp.datatype;
    requires dev.pp.parameters;
    requires dev.pp.texttable;
    requires dev.pp.scripting;

    requires dev.pdml.core;
    requires dev.pdml.ext;

    exports dev.pmlc.core;
    exports dev.pmlc.core.data.formalnode;
    exports dev.pmlc.core.data.formalnode.block;
    exports dev.pmlc.core.data.formalnode.block.chapter;
    exports dev.pmlc.core.data.formalnode.block.list;
    exports dev.pmlc.core.data.formalnode.block.media;
    exports dev.pmlc.core.data.formalnode.block.table;
    exports dev.pmlc.core.data.formalnode.inline;
    exports dev.pmlc.core.data.formalnode.inline.font;
    exports dev.pmlc.core.data.node;
    exports dev.pmlc.core.data.node.block;
    exports dev.pmlc.core.data.node.block.chapter;
    exports dev.pmlc.core.data.node.block.list;
    exports dev.pmlc.core.data.node.block.media;
    exports dev.pmlc.core.data.node.block.table;
    exports dev.pmlc.core.data.node.inline;
    exports dev.pmlc.core.data.node.inline.font;
    exports dev.pmlc.core.data.node.validation;
    exports dev.pmlc.core.parser;
    exports dev.pmlc.core.nodeshandler;
    exports dev.pmlc.core.data.node.block.code;
    exports dev.pmlc.core.data.formalnode.block.code;
}
