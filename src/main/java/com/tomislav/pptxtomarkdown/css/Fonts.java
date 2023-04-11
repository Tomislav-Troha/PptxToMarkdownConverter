package com.tomislav.pptxtomarkdown.css;

public enum Fonts {

    ARIAL("Arial"),
    CALIBRI("Calibri"),
    CAMBRIA("Cambria"),
    COURIER_NEW("Courier New"),
    GEORGIA("Georgia"),
    HELVETICA("Helvetica"),
    LUCIDA_SANS_UNICODE("Lucida Sans Unicode"),
    TAHOMA("Tahoma"),
    TIMES_NEW_ROMAN("Times New Roman"),
    TREBUCHET_MS("Trebuchet MS"),
    VERDANA("Verdana"),
    COMICSANS("Comic Sans MS");

    private String fontName;

    Fonts(String fontName) {
        this.fontName = fontName;
    }

    public String getFontName() {
        return fontName;
    }

}
