package dev.pmlc.data.nodespec.block.media;

import dev.pp.basics.annotations.NotNull;
import dev.pp.basics.utilities.string.HTextAlign;
import dev.pp.datatype.CommonDataTypes;
import dev.pp.datatype.nonunion.scalar.impls.Enum.EnumDataType;
import dev.pp.parameters.parameterspec.ParameterSpec;

public class SharedMediaNodeSpecAttributes {

    // see https://github.com/pml-lang/converter/issues/73

    public static @NotNull ParameterSpec<String> source ( @NotNull String title, @NotNull String example ) {

        return new ParameterSpec.Builder<> ( "source", CommonDataTypes.STRING )
            .documentation (
                title,
                """
                The source (location) of the media asset.
                The source can be a relative path, an URL, or any other string that denotes a valid source in the generated target code (e.g. HTML).
                Note: The validity/existence of the source is not checked by PML. It is the users responsibility to provide a valid source.
                """,
                example )
            .build();
    }

    public static @NotNull ParameterSpec<Integer> width ( @NotNull String title ) {

        return new ParameterSpec.Builder<> ( "width", CommonDataTypes.INTEGER_32_OR_NULL )
            .defaultValue ( null )
            .documentation (
                title,
                "The width of the media, expressed in pixels.",
                "width = 200" )
            .build();
    }

    public static @NotNull ParameterSpec<Integer> height ( @NotNull String title ) {

        return new ParameterSpec.Builder<> ( "height", CommonDataTypes.INTEGER_32_OR_NULL )
            .defaultValue ( null )
            .documentation (
                title,
                "The height of the media, expressed in pixels.",
                "height = 150" )
            .build();
    }

    public static final @NotNull ParameterSpec<HTextAlign> HORIZONTAL_ALIGNMENT = new ParameterSpec.Builder<> (
        "align", new EnumDataType<> ( HTextAlign.class ) )
        .defaultValue ( HTextAlign.LEFT )
        .documentation ( "Horizontal Alignment",
            "The horizontal alignment of the media. Valid values are: left, center, and right.",
            "align = center" )
        .build();

    public static final @NotNull ParameterSpec<Boolean> BORDER = new ParameterSpec.Builder<> (
        "border", CommonDataTypes.BOOLEAN )
        .defaultValue ( false )
        .documentation ( "Border",
            "This parameter can be set to 'yes' to draw a border around the media.",
            "border = yes" )
        .build();

    public static final @NotNull ParameterSpec<String> LINK = new ParameterSpec.Builder<> (
        "link", CommonDataTypes.STRING_OR_NULL )
        .defaultValue ( null )
        .documentation ( "Link",
            "A link (e.g. URL) to the media. If the user clicks on the media, the specified link will be opened in the browser.",
            "link = http://www.example.com/path/image.png" )
        .build();

}
