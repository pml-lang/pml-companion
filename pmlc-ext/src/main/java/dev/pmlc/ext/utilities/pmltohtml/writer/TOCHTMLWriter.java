package dev.pmlc.ext.utilities.pmltohtml.writer;

import dev.pmlc.core.data.node.block.TOCNode;
import dev.pp.basics.annotations.NotNull;
import dev.pp.basics.annotations.Nullable;

import java.io.IOException;
import java.util.List;

public class TOCHTMLWriter {

    public static void writeTOCNode (
        @NotNull TOCNode TOCNode,
        @NotNull HTMLNodesWriterHelper helper,
        @Nullable String TOCTitle ) throws Exception {

        if ( TOCNode.isRootNode() ) {
            writeRootTOCNode ( TOCNode, helper, TOCTitle );
        } else if ( TOCNode.getChildNodes() != null ) {
            writeBranchTOCNode ( TOCNode, helper );
        } else {
            writeLeafTOCNode ( TOCNode, helper );
        }
    }

    private static void writeRootTOCNode (
        @NotNull TOCNode TOCNode,
        @NotNull HTMLNodesWriterHelper helper,
        @Nullable String TOCTitle ) throws Exception {

        /*
        <nav class="pml-toc">
            <h2 class="pml-toc-title">Table of Contents</h2>
            <div class="pml-toc-tree" id="TOCTree">
                <ul>
        */
        helper.writeIndent();
        helper.write ( "<nav class=\"pml-toc\">" );
        helper.writeNewLine();
        helper.increaseIndent();

        if ( TOCTitle != null ) {
            helper.writeIndent();
            helper.write ( "<h2 class=\"pml-toc-title\">" );
            helper.escapeAndWriteText ( TOCTitle );
            helper.writeHTMLEndTag ( "h2" );
            helper.writeNewLine();
        }

        helper.writeIndent();
        helper.write ( "<div class=\"pml-toc-tree\" id=\"TOCTree\">" );
        helper.writeNewLine();
        helper.increaseIndent();

        @Nullable List<TOCNode> childTOCNodes = TOCNode.getChildTOCNodes();
        if ( childTOCNodes != null ) {

            helper.writeIndent();
            helper.write ( "<ul>" );
            helper.writeNewLine();

            helper.increaseIndent();
            for ( TOCNode childTOCNode: childTOCNodes ) {
                writeTOCNode ( childTOCNode, helper, null );
            }
            helper.decreaseIndent();

            helper.writeBlockEndLine ( "ul" );
        }

        /*
                </ul>
            </div>
        </nav>
        */
        helper.decreaseIndent();
        helper.writeBlockEndLine ( "div" );
        helper.decreaseIndent();
        helper.writeBlockEndLine ( "nav" );
    }

    private static void writeBranchTOCNode (
        @NotNull TOCNode TOCNode,
        @NotNull HTMLNodesWriterHelper helper ) throws Exception {

        /*
        <li class="pml-toc-branch-node">
            <details>
                <summary><a href="#ch__7">Anatomy of a PML Document</a></summary>
                <ul>
                    <li class="pml-toc-leaf-node"><a href="#ch__3">Document Tree</a></li>
                    <li class="pml-toc-leaf-node"><a href="#ch__4">Nodes</a></li>
                </ul>
            </details>
        </li>
        */

        helper.writeBlockNodeStartLine ( TOCNode, "li", "pml-toc-branch-node", null,true );

        helper.writeIndent();
        helper.write ( "<details>" );
        helper.writeNewLine();
        helper.increaseIndent();

        helper.writeIndent();
        helper.write ( "<summary><a href=\"#" );
        helper.write ( TOCNode.getChapterId() );
        helper.write ( "\">" );
        helper.escapeAndWriteText ( TOCNode.getChapterTitleText() );
        helper.writeHTMLEndTag ( "a" );
        helper.writeHTMLEndTag ( "summary" );
        helper.writeNewLine();

        helper.writeIndent();
        helper.write ( "<ul>" );
        helper.writeNewLine();

        helper.increaseIndent();
        @Nullable List<TOCNode> childTOCNodes = TOCNode.getChildTOCNodes();
        assert childTOCNodes != null;
        for ( TOCNode childTOCNode: childTOCNodes ) {
            writeTOCNode ( childTOCNode, helper, null );
        }
        helper.decreaseIndent();

        helper.writeBlockEndLine ( "ul" );
        helper.decreaseIndent();
        helper.writeBlockEndLine ( "details" );
        helper.decreaseIndent();
        helper.writeBlockEndLine ( "li" );
    }

    private static void writeLeafTOCNode (
        @NotNull TOCNode TOCNode,
        @NotNull HTMLNodesWriterHelper helper ) throws IOException {

        // <li class="pml-toc-leaf-node"><a href="#ch__3">Document Tree</a></li>
        helper.writeIndent();
        helper.writeHTMLStartTag ( TOCNode, "li", "pml-toc-leaf-node", null );
        helper.write ( "<a href=\"#" );
        helper.write ( TOCNode.getChapterId() );
        helper.write ( "\">" );
        helper.escapeAndWriteText ( TOCNode.getChapterTitleText() );
        helper.writeHTMLEndTag ( "a" );
        helper.writeHTMLEndTag ( "li" );
        helper.writeNewLine();
    }
}
