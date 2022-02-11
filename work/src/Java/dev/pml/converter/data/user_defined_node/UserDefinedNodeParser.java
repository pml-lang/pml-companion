// SPDX-License-Identifier: GPL-2.0-only
// Copyright (C) 2018 - 2021 Christian Neumanns, email: chris@pml-lang.dev

package dev.pml.converter.data.user_defined_node;

import dev.pdml.core.data.AST.ASTNode;
import dev.pdml.core.data.AST.children.ASTNodeChildren;
import dev.pdml.core.data.AST.children.Node_ASTNodeChild;
import dev.pdml.core.data.formalNode.FormalNode;
import dev.pdml.core.data.formalNode.FormalNodes;
import dev.pdml.ext.extensions.types.TextBlockType;
import dev.pdml.core.data.node.name.NodeName;
import dev.pdml.core.PDMLConstants;
import dev.pdml.ext.utilities.parser.PDMLParserUtils;
import dev.pp.basics.annotations.NotNull;
import dev.pp.basics.annotations.Nullable;
import dev.pp.basics.utilities.directory.DirectoryContentUtils;
import dev.pp.text.error.handler.TextErrorHandler;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class UserDefinedNodeParser {


    public static final String UDN_FILE_EXTENSION = "pdml";
    private static final String UDN_FILE_EXTENSION_WITH_DOT = "." + UDN_FILE_EXTENSION;

    public static final String NODES_NODE_NAME = "nodes";
    public static final String NODE_NODE_NAME = "node";
    public static final String NAME_NODE_NAME = "name";
    public static final String ATTRIBUTES_NODE_NAME = "attributes";
    public static final String ATTRIBUTE_NODE_NAME = "attribute";
    public static final String DEFAULT_NODE_NAME = "default";
    public static final String WRITER_NODE_NAME = "writer";
    public static final String SCRIPT_NODE_NAME = "script";

    public static final String DEFAULT_TARGET_CODE_NAME = "HTML";
    public static final String DEFAULT_SCRIPT_LANGUAGE = "Javascript";


    public static @Nullable Map<String, UserDefinedNode> parseDirectories (
        @NotNull List<File> directories,
        @NotNull TextErrorHandler errorHandler ) throws Exception {

        Map<String, UserDefinedNode> UDNs = new HashMap<>();
        for ( File directory : directories ) {

            DirectoryContentUtils.forEachNonExcludedFileWithExtensionInTree ( directory, PDMLConstants.PDML_FILE_EXTENSION, file -> {
                try {
                    addUDNsInFile ( file, UDNs, errorHandler );
                } catch ( Exception e ) {
                    errorHandler.handleError ( "UDN_ERROR", e.getMessage(), null );
                }
            });
        }

        return UDNs.isEmpty() ? null : UDNs;
    }

    public static void addUDNsInFile (
        @NotNull File file,
        @NotNull Map<String, UserDefinedNode> UDNs,
        @NotNull TextErrorHandler errorHandler ) throws Exception {

        FormalNode<String> formalScriptNode = new FormalNode<> ( new NodeName ( SCRIPT_NODE_NAME ) );
        formalScriptNode.setType ( new TextBlockType() );

        FormalNodes formalNodes = new FormalNodes();
        formalNodes.add ( formalScriptNode );

        ASTNode rootNode = PDMLParserUtils.parseFileToAST ( file, errorHandler, formalNodes );

        handleRootNode ( rootNode, UDNs, errorHandler );
    }

    private static void handleRootNode (
        @NotNull ASTNode rootNode,
        @NotNull Map<String, UserDefinedNode> UDNs,
        @NotNull TextErrorHandler errorHandler ) {

        switch ( rootNode.getLocalName() ) {

            case NODE_NODE_NAME -> handleNodeNode ( rootNode, UDNs, errorHandler );

            case NODES_NODE_NAME -> handleNodesNode ( rootNode, UDNs, errorHandler );

            default -> errorHandler.handleError (
                "INVALID_ROOT_NODE",
                "Node '" + rootNode.getName().qualifiedName() + "' is invalid. The root node must be be named '" +
                    NODE_NODE_NAME + "' or '" + NODES_NODE_NAME + "'.",
                rootNode.getName().getToken() );
        }
    }

    private static void handleNodesNode (
        @NotNull ASTNode nodesNode,
        @NotNull Map<String, UserDefinedNode> UDNs,
        @NotNull TextErrorHandler errorHandler ) {

        ASTNodeChildren children = nodesNode.getChildren();
        if ( children == null ) {
            errorHandler.handleError (
                "EMPTY_NODES",
                "Node '" + nodesNode.getName().qualifiedName() + "' must contain one or mode '" + NODE_NODE_NAME + "' nodes.",
                nodesNode.getName().getToken() );
            return;
        }

        children.forEachNodeElement ( nodeNode -> {
            String name = nodeNode.getLocalName();
            if ( name.equals ( NODE_NODE_NAME ) ) {
                handleNodeNode ( nodeNode, UDNs, errorHandler );
            } else {
                errorHandler.handleError (
                    "INVALID_NODE",
                    "Node '" + name + "' is invalid. Node '" + nodesNode.getName().qualifiedName() +
                        "' can only contain nodes with name '" + NODE_NODE_NAME + "'.",
                    nodeNode.getName().getToken() );
            }
        } );
    }

    private static void handleNodeNode (
        @NotNull ASTNode nodeNode,
        @NotNull Map<String, UserDefinedNode> UDNs,
        @NotNull TextErrorHandler errorHandler ) {

        UserDefinedNode UDN = createUDN ( nodeNode, errorHandler );
        if ( UDN == null ) return;

        String name = UDN.name();
        if ( UDNs.containsKey ( name ) ) {
            errorHandler.handleError (
                "DUPLICATE_UDN",
                "The user defined node '" + name + "' has already been defined.",
                nodeNode.getName().getToken() );
           return;
        }

        UDNs.put ( name, UDN );
    }

    private static @Nullable UserDefinedNode createUDN ( @NotNull ASTNode nodeNode, @NotNull TextErrorHandler errorHandler ) {

        String UDNName = nodeNode.getTextOfUniqueTextChildNodeByLocalName ( NAME_NODE_NAME, errorHandler );
        if ( UDNName == null ) return null;

        // TODO get from PDML
        boolean isInlineNode = true;

        List<UserDefinedNodeAttribute> attributes = createAttributes ( nodeNode, errorHandler );

        List<UserDefinedNodeWriter> writers = createWriters ( nodeNode, errorHandler );
        if ( writers == null ) return null;

        return new UserDefinedNode ( UDNName, isInlineNode, attributes, writers );
    }

    private static @Nullable List<UserDefinedNodeAttribute> createAttributes ( @NotNull ASTNode nodeNode, @NotNull TextErrorHandler errorHandler ) {

        ASTNode attributesNode = nodeNode.getUniqueNodeChildByLocalNameOrNull ( ATTRIBUTES_NODE_NAME );
        if ( attributesNode == null ) return null;

        ASTNodeChildren children = attributesNode.getChildren();
        if ( children == null ) return null;

        List<UserDefinedNodeAttribute> attributes = new ArrayList<>();
        children.forEachElement ( element -> {
            if ( element instanceof Node_ASTNodeChild child ) {
                ASTNode attributeNode = child.getNode();
                if ( attributeNode.getLocalName().equals ( ATTRIBUTE_NODE_NAME ) ) {
                    UserDefinedNodeAttribute attribute = createAttribute ( attributeNode, errorHandler );
                    if ( attribute != null ) attributes.add ( attribute );
                } else {
                    errorHandler.handleError (
                        "INVALID_CHILD_NODE",
                        "Node '" + ATTRIBUTES_NODE_NAME + "' can only contain nodes with name '" + ATTRIBUTE_NODE_NAME + "'.",
                        attributeNode.getName().getToken() );
                }
            }
        } );

        return attributes.isEmpty() ? null : attributes;
    }

    private static @Nullable UserDefinedNodeAttribute createAttribute ( @NotNull ASTNode attributeNode, @NotNull TextErrorHandler errorHandler ) {

        String name = attributeNode.getTextOfUniqueTextChildNodeByLocalName ( NAME_NODE_NAME, errorHandler );
        if ( name == null ) return null;

        String defaultValue = attributeNode.getTextOfUniqueTextChildNodeByLocalNameOrNull ( DEFAULT_NODE_NAME );

        return new UserDefinedNodeAttribute ( name, defaultValue );
    }

    private static @Nullable List<UserDefinedNodeWriter> createWriters ( @NotNull ASTNode nodeNode, @NotNull TextErrorHandler errorHandler ) {

        ASTNode writerNode = nodeNode.getUniqueNodeChildByLocalName ( WRITER_NODE_NAME, errorHandler );
        if ( writerNode == null ) return null;

        UserDefinedNodeWriter writer = createWriter ( writerNode, errorHandler );
        if ( writer == null ) return null;

        return List.of ( writer );
    }

    private static @Nullable UserDefinedNodeWriter createWriter ( @NotNull ASTNode writerNode, @NotNull TextErrorHandler errorHandler ) {

        String script = writerNode.getTextOfUniqueTextChildNodeByLocalName ( SCRIPT_NODE_NAME, errorHandler );
        if ( script == null ) return null;

        return new UserDefinedNodeWriter ( DEFAULT_TARGET_CODE_NAME, DEFAULT_SCRIPT_LANGUAGE, script );
    }
}
