package com.collegedekho.app.htmlparser;

/**
 * Interface for font-resolving components.
 */
public interface FontResolver {

    FontFamily getDefaultFont();

    FontFamily getSansSerifFont();

    FontFamily getSerifFont();

    FontFamily getMonoSpaceFont();

    FontFamily getFont(String name);

}
